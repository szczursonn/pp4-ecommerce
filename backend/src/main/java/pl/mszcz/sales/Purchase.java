package pl.mszcz.sales;

import javax.persistence.*;
import java.util.List;

// Can't name it Order because order is a sql keyword
@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String customerId;
    @OneToMany(mappedBy = "purchase")
    private List<PurchaseItem> items;

    public Purchase() {}

    public Purchase(Long id, String customerId, List<PurchaseItem> items) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
    }

    public Long getId() {
        return this.id;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public List<PurchaseItem> getItems() {
        return this.items;
    }
}
