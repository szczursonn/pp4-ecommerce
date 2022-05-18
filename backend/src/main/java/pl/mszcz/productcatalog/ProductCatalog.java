package pl.mszcz.productcatalog;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProductCatalog {

    private final ProductStorage productStorage;

    public ProductCatalog(ProductStorage productStorage) {
        this.productStorage = productStorage;
    }

    public ProductData addProduct(String name) {
        ProductData product = new ProductData();
        product.setName(name);

        return productStorage.save(product);
    }

    public Optional<ProductData> getProductById(Long productId) {
        return this.productStorage.load(productId);
    }

    public void publishProduct(Long productId) throws CantPublishProductException {
        ProductData product = this.getProductById(productId)
                .orElseThrow(CantPublishProductException::new);

        if (product == null) {
            throw new InvalidProductIdException();
        }

        if (product.getPrice() == null) {
            throw new CantPublishProductException();
        }

        if (product.getImageUrl() == null) {
            throw new CantPublishProductException();
        }

        product.setPublished(true);

        productStorage.save(product);
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

}
