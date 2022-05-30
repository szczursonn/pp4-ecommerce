package pl.mszcz.sales;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // TODO: better primary key
    private Long id;

    @Column(name = "purchaseId", insertable = false, updatable = false)
    private Long purchaseId;

    @ManyToOne
    @JoinColumn(name = "purchaseId", nullable = false)
    private Purchase purchase;
    private String name;
    private BigDecimal price;
    private int quantity;

    public PurchaseItem() {}

    public PurchaseItem(Long purchaseId, String name, BigDecimal price, int quantity) {
        this.purchaseId = purchaseId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getPurchaseId() {
        return this.purchaseId;
    }

    public Purchase getOrder() {
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
}
