package pl.mszcz.sales;

import java.util.List;

public interface CartItemStorage {
    List<CartItem> getAllCustomerItems(String customerId);
    void save(CartItem item);
    Long remove(Long productId, String customerId);
}
