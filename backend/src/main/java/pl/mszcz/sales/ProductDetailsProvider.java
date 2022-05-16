package pl.mszcz.sales;

import java.util.Optional;

public interface ProductDetailsProvider {
    public Optional<ProductDetails> findById(String productId);
}
