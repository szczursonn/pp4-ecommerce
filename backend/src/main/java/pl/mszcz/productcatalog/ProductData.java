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
    private boolean archived;

    // constructor for JPA
    public ProductData() {}

    public ProductData(Long productId, String productName, BigDecimal price) {
        this.id = productId;
        this.name = productName;
        this.price = price;
        this.archived = false;
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

    public boolean isArchived() {
        return this.archived;
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

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
