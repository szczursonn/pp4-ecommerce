package pl.mszcz.productcatalog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapProductStorage implements ProductStorage {
    private final Map<String, ProductData> products;

    public MapProductStorage() {
        this.products = new HashMap<>();
    }

    @Override
    public ProductData load(String productId) {
        return this.products.get(productId);
    }

    @Override
    public void save(ProductData product) {
        this.products.put(product.getId(), product);
    }

    @Override
    public List<ProductData> allPublished() {
        return this.products.values()
                .stream()
                .filter((productData -> productData.isPublished()))
                .collect(Collectors.toList());
    }
}
