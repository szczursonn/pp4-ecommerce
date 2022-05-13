package pl.mszcz.productcatalog;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductCatalog {

    private final ProductStorage productStorage;

    public ProductCatalog(ProductStorage productStorage) {
        this.productStorage = productStorage;
    }

    public String addProduct(String productId, String name) {
        ProductData newProduct = new ProductData(productId, name);
        this.productStorage.save(newProduct);

        return productId;
    }

    public ProductData getProductById(String productId) {
        return this.productStorage.load(productId);
    }

    public void publishProduct(String productId) throws CantPublishProductException {
        ProductData product = this.getProductById(productId);

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
    }

    public List<ProductData> getPublishedProducts() {
        return this.productStorage.allPublished();
    }

    public void setPrice(String productId, BigDecimal price) {
        ProductData product = this.productStorage.load(productId);

        if (product == null) {
            throw new InvalidProductIdException();
        }

        product.setPrice(price);
    }

    public void setImageUrl(String productId, String imageUrl) {
        ProductData product = this.productStorage.load(productId);

        if (product == null) {
            throw new InvalidProductIdException();
        }

        product.setImageUrl(imageUrl);
    }

}
