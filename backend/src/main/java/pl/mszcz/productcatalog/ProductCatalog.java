package pl.mszcz.productcatalog;

import pl.mszcz.productcatalog.exceptions.InvalidProductIdException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProductCatalog {

    private final ProductStorage productStorage;

    public ProductCatalog(ProductStorage productStorage) {
        this.productStorage = productStorage;
    }

    public ProductData addProduct(String name, BigDecimal price) {
        ProductData product = new ProductData(null, name, price);

        return productStorage.save(product);
    }

    public Optional<ProductData> getProductById(Long productId) {
        return this.productStorage.load(productId);
    }

    public List<ProductData> getPublishedProducts() {
        return this.productStorage.allPublished();
    }

    public void setPrice(Long productId, BigDecimal price) {
        ProductData product = productStorage.load(productId)
                .orElseThrow(InvalidProductIdException::new);

        product.setPrice(price);

        productStorage.save(product);
    }

    public void setImageUrl(Long productId, String imageUrl) {
        ProductData product = productStorage.load(productId)
                .orElseThrow(InvalidProductIdException::new);

        product.setImageUrl(imageUrl);

        productStorage.save(product);
    }

    public void setArchive(Long productId, boolean archived) {
        ProductData product = productStorage.load(productId)
                .orElseThrow(InvalidProductIdException::new);

        product.setArchived(archived);

        productStorage.save(product);
    }

}
