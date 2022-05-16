package pl.mszcz.sales;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MapProductDetailsProvider implements ProductDetailsProvider {

    HashMap<String, ProductDetails> productDetails = new HashMap<>();

    public MapProductDetailsProvider() {

    }

    public MapProductDetailsProvider(List<ProductDetails> productDetails) {
        productDetails.forEach((pd)->this.productDetails.put(pd.getProductId(), pd));
    }

    public Optional<ProductDetails> findById(String productId) {
        return Optional.ofNullable(productDetails.get(productId));
    }
}
