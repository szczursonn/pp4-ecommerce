package pl.mszcz.sales.purchase;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

// Can't name it Order because order is a sql keyword
@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;    //purchaseId

    @OneToMany(mappedBy = "purchase", cascade = {CascadeType.PERSIST})
    private List<PurchaseItem> items;

    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;
    private String customerId;  // TODO: FK of Customer

    public Purchase() {}

    public Purchase(Long id, List<PurchaseItem> items, String customerId, CustomerInfo customerInfo) {
        this.id = id;
        this.items = items;
        this.customerFirstName = customerInfo.getFirstName();
        this.customerLastName = customerInfo.getLastName();
        this.customerEmail = customerInfo.getEmail();
        this.customerId = customerId;
    }

    public Long getId() {
        return this.id;
    }

    public List<PurchaseItem> getItems() {
        return this.items;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerFirstName() {
        return this.customerFirstName;
    }

    public void setCustomerFirstName(String firstName) {
        this.customerFirstName = firstName;
    }

    public String getCustomerLastName() {
        return this.customerLastName;
    }

    public void setCustomerLastName(String lastName) {
        this.customerLastName = lastName;
    }

    public String getCustomerEmail() {
        return this.customerEmail;
    }

    public void setCustomerEmail(String email) {
        this.customerEmail = email;
    }

    public void addItem(String name, BigDecimal price, int quantity) {
        PurchaseItem item = new PurchaseItem();
        item.setName(name);
        item.setPrice(price);
        item.setQuantity(quantity);
        item.setPurchase(this);

        this.items.add(item);
    }

    private BigDecimal getTotal() {
        return items
                .stream()
                .map(item->item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public PaymentData registerPayment(PaymentGateway paymentGateway) {
        RegisterPaymentResponse response = paymentGateway.handle(new RegisterPaymentRequest(
                id,
                getTotal(),
                customerFirstName,
                customerLastName,
                customerEmail
        ));

        return new PaymentData(response.paymentId(), id, response.url());
    }

}
