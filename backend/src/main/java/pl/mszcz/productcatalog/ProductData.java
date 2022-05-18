package pl.mszcz.productcatalog;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class ProductData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private BigDecimal price;
    private String imageUrl;
    private boolean published;

    // constructor for JPA
    public ProductData() {}

    public ProductData(Long productId, String productName) {
        this.id = productId;
        this.name = productName;
        this.published = false;
    }

    public Long getId() {
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

    public void setName(String newName) {
        this.name = newName;
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
