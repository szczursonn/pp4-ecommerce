package pl.mszcz.sales.purchase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.mszcz.sales.purchase.Purchase;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // TODO: better primary key
    private Long id;

    //@Column(name = "purchaseId", insertable = false, updatable = false)
    //private Long purchaseId;

    @ManyToOne
    @JoinColumn(name = "purchaseId", nullable = false)
    @JsonIgnore // prevent infinite loop on http req
    private Purchase purchase;
    private String name;
    private BigDecimal price;
    private int quantity;

    public PurchaseItem() {}

    public PurchaseItem(Long itemId, String name, BigDecimal price, int quantity) {
        this.id = itemId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return this.id;
    }

    public Purchase getPurchase() {
        return this.purchase;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
