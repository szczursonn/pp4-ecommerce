package pl.mszcz.sales;

import pl.mszcz.productcatalog.ProductData;

import java.util.Optional;

public interface ProductDetailsProvider {
    Optional<ProductData> findById(Long productId);
}
