package pl.mszcz.sales;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Long, CartItem> items;

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
            items.get(cartItem.getProductId()).setQuantity(cartItem.getQuantity());
            return;
        }
        items.put(cartItem.getProductId(), cartItem);
    }

    public Long remove(Long productId) {
        CartItem item = items.remove(productId);
        if (item == null) return null;
        return productId;
    }

    public Offer getOffer() {
        return new Offer(
                items.values().stream().toList()
        );
    }
}
