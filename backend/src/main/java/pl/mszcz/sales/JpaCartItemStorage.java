package pl.mszcz.sales;

import org.springframework.beans.factory.annotation.Autowired;

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
