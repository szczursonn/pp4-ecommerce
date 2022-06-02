package pl.mszcz.sales;

import org.junit.jupiter.api.Test;
import pl.mszcz.productcatalog.ProductData;
import pl.mszcz.sales.cart.CartItem;
import pl.mszcz.sales.cart.CartItemStorage;
import pl.mszcz.sales.cart.MapCartItemStorage;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class MapCartItemStorageTest {

    @Test
    void itAllowsToAddCartItem() {
        CartItemStorage storage = thereIsCartItemStorage();
        CartItem item = thereIsCartItem("abc", 1L);

        storage.save(item);

        assertEquals(1, storage.getAllCustomerItems("abc").size());
    }

    @Test
    void itAllowsToRemoveCartItem() {
        CartItemStorage storage = thereIsCartItemStorage();
        CartItem item = thereIsCartItem("abc", 1L);

        storage.save(item);
        storage.remove(1L, "abc");

        assertEquals(0, storage.getAllCustomerItems("abc").size());
    }

    private CartItemStorage thereIsCartItemStorage() {
        return new MapCartItemStorage();
    }

    private CartItem thereIsCartItem(String customerId, Long productId) {
        return new CartItem(customerId, new ProductData(productId, "product-"+productId, BigDecimal.TEN), 1);
    }

}
