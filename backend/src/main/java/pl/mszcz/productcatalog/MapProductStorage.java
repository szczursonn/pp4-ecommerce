package pl.mszcz.productcatalog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
        this.products.put(product.getId(), product);
        return product;
    }

    @Override
    public List<ProductData> allPublished() {
        return this.products.values()
                .stream()
                .filter((productData -> productData.isPublished()))
                .collect(Collectors.toList());
    }
}
