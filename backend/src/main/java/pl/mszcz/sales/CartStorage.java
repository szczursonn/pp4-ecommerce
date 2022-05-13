package pl.mszcz.sales;

import java.util.Optional;

public interface CartStorage {
    Optional<Cart> getForCustomer(String customerId);
    void save (String customerId, Cart cart);
}
