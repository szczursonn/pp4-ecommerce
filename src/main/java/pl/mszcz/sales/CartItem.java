package pl.mszcz.sales;

import java.math.BigDecimal;

public class CartItem {
    public static CartItem of(String productId, String name, BigDecimal price) {
        return new CartItem();
    }
}
