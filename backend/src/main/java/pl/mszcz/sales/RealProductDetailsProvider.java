package pl.mszcz.sales;

import pl.mszcz.productcatalog.ProductCatalog;
import pl.mszcz.productcatalog.ProductData;

import java.util.Optional;

public class RealProductDetailsProvider implements ProductDetailsProvider {
    private ProductCatalog catalog;

    public RealProductDetailsProvider(ProductCatalog catalog) {
        this.catalog = catalog;
    }

    public Optional<ProductData> findById(Long productId) {
        return catalog.getProductById(productId);
    };
}
