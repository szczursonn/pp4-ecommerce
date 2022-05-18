package pl.mszcz.sales;

import pl.mszcz.productcatalog.ProductCatalog;
import pl.mszcz.productcatalog.ProductData;

import java.util.Optional;

public class RealProductDetailsProvider implements ProductDetailsProvider {
    private ProductCatalog catalog;

    public RealProductDetailsProvider(ProductCatalog catalog) {
        this.catalog = catalog;
    }

    public Optional<ProductDetails> findById(Long productId) {
        ProductData productData = catalog.getProductById(productId)
                .orElse(null);

        if (productData == null) return Optional.empty();

        return Optional.of(new ProductDetails(productId, productData.getName(), productData.getPrice()));
    };
}
