package pl.mszcz.sales;

import java.io.Serializable;

public class CartItemId implements Serializable {
    private Long productId;
    private String customerId;

    public CartItemId() {}

    public CartItemId(Long productId, String customerId) {
        this.productId = productId;
        this.customerId = customerId;
    }
}
