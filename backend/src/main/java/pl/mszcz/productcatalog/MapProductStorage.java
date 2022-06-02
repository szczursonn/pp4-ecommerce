package pl.mszcz.productcatalog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MapProductStorage implements ProductStorage {
    private final Map<Long, ProductData> products;

    public MapProductStorage() {
        this.products = new HashMap<>();
    }

    @Override
    public Optional<ProductData> load(Long productId) {
        return Optional.ofNullable(this.products.get(productId));
    }

    @Override
    public ProductData save(ProductData product) {
        Long productId = (product.getId() == null) ? generateId() : product.getId();

        ProductData newProduct = new ProductData(productId, product.getName(), product.getPrice());
        newProduct.setImageUrl(product.getImageUrl());
        newProduct.setArchived(product.isArchived());

        this.products.put(productId, newProduct);
        return newProduct;
    }

    @Override
    public List<ProductData> allPublished() {
        return this.products.values()
                .stream()
                .filter((pd -> !pd.isArchived()))
                .toList();
    }

    private Long generateId() {
        Long id = 1L;
        while(products.get(id) != null) {
            id++;
        }
        return id;
    }
}
