package pl.mszcz.productcatalog;

import java.util.List;
import java.util.Optional;

public interface ProductStorage {
    ProductData save(ProductData productData);

    Optional<ProductData> load(Long productId);

    List<ProductData> allPublished();
}
