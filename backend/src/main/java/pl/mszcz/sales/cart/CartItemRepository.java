package pl.mszcz.sales.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mszcz.sales.cart.CartItem;
import pl.mszcz.sales.cart.CartItemId;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
    List<CartItem> findByCustomerId(String customerId);
}
