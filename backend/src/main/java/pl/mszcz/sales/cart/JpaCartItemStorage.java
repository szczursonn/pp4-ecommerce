package pl.mszcz.sales.cart;

import org.springframework.beans.factory.annotation.Autowired;
import pl.mszcz.sales.cart.CartItem;
import pl.mszcz.sales.cart.CartItemId;
import pl.mszcz.sales.cart.CartItemRepository;
import pl.mszcz.sales.cart.CartItemStorage;

import java.util.List;

public class JpaCartItemStorage implements CartItemStorage {
    @Autowired
    CartItemRepository repo;

    public List<CartItem> getAllCustomerItems(String customerId) {
        return repo.findByCustomerId(customerId);
    }

    public void save(CartItem item) {
        repo.save(item);
    }

    public Long remove(Long productId, String customerId) {
        repo.deleteById(new CartItemId(productId, customerId));
        return productId;
    }
}
