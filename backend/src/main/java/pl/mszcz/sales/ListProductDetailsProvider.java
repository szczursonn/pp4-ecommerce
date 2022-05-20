package pl.mszcz.sales;

import pl.mszcz.productcatalog.ProductData;

import java.util.ArrayList;
import java.util.Optional;

public class ListProductDetailsProvider implements ProductDetailsProvider {

    ArrayList<ProductData> productDetails;

    ListProductDetailsProvider(ArrayList<ProductData> productDetails) {
        this.productDetails = productDetails;
    }

    public Optional<ProductData> findById(Long productId) {
        return productDetails.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst();
    }
}
