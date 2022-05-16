package pl.mszcz.sales;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private Map<String, CartItem> items;

    public Cart() {
        this.items = new HashMap<>();
    }

    public static Cart empty() {
        return new Cart();
    }

    public int getItemsCount() {
        return items.size();
    }

    private boolean isAlreadyInCart(CartItem cartItem) {
        return items.get(cartItem.getProductId()) != null;
    }

    public void add(CartItem cartItem) {
        if (isAlreadyInCart(cartItem)) {
            items.get(cartItem.getProductId()).increaseQuantity();
            return;
        }
        items.put(cartItem.getProductId(), cartItem);
    }

    public Offer getOffer() {
        return new Offer(
                items.values().stream().toList()
        );
    }
}
