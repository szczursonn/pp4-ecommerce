package pl.mszcz.sales;

import java.util.ArrayList;
import java.util.Optional;

public class ListProductDetailsProvider implements ProductDetailsProvider {

    ArrayList<ProductDetails> productDetails;

    ListProductDetailsProvider(ArrayList<ProductDetails> productDetails) {
        this.productDetails = productDetails;
    }

    public Optional<ProductDetails> findById(String productId) {
        return productDetails.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst();
    }
}
