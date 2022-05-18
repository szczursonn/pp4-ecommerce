package pl.mszcz.sales;

import java.math.BigDecimal;

public class CartItem {
    private Long productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;

    private CartItem() {}

    public CartItem(Long productId, String name, BigDecimal price, Integer quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
