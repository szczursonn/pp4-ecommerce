package pl.mszcz.sales.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.mszcz.productcatalog.ProductData;

import javax.persistence.*;

@Entity
@IdClass(CartItemId.class)
public class CartItem {
    @Id
    @JsonIgnore // who cares
    private String customerId;

    @Id
    private Long productId;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "productId", nullable = false)
    private ProductData product;
    private Integer quantity;

    private CartItem() {}

    public CartItem(String customerId, ProductData product, Integer quantity) {
        this.customerId = customerId;
        this.product = product;
        this.productId = product.getId();
        this.quantity = quantity;
    }

    public ProductData getProduct() {
        return product;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setProduct(ProductData product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
