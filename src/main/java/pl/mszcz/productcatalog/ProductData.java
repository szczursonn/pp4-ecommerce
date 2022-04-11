package pl.mszcz.productcatalog;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class ProductData {
    @Id
    private String id;
    private String name;
    private BigDecimal price;
    private String imageUrl;
    private boolean published;

    public ProductData() {}

    public ProductData(String productId, String productName) {
        this.id = productId;
        this.name = productName;
        this.published = false;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public boolean isPublished() {
        return this.published;
    }

    public void setPrice(BigDecimal newPrice) {
        this.price = newPrice;
    }

    public void setImageUrl(String newImageUrl) {
        this.imageUrl = newImageUrl;
    }

    public void setPublished(boolean newState) {
        this.published = newState;
    }
}
