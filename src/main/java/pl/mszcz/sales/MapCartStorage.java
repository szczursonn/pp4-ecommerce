package pl.mszcz.sales;

import java.util.HashMap;
import java.util.Optional;

public class MapCartStorage implements CartStorage {
    HashMap<String, Cart> carts;

    public MapCartStorage() {
        this.carts = new HashMap<>();
    }

    public Optional<Cart> getForCustomer(String customerId) {
        return Optional.ofNullable(carts.get(customerId));
    }

    public void save(String customerId, Cart cart) {
        carts.put(customerId, cart);
    }
}
