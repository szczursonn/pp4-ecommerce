package pl.mszcz.sales;

import java.util.ArrayList;
import java.util.List;

public class MapCartItemStorage implements CartItemStorage {

    private List<CartItem> items;

    public MapCartItemStorage() {
        this.items = new ArrayList<>();
    }

    public List<CartItem> getAllCustomerItems(String customerId) {
        return items
                .stream()
                .filter(item->item.getCustomerId().equals(customerId))
                .toList();
    }

    public void save(CartItem item) {
        this.remove(item.getProduct().getId(), item.getCustomerId());
        items.add(item);
    }

    public Long remove(Long productId, String customerId) {
        this.items = new ArrayList<>(items.stream().filter(item->!(item.getCustomerId().equals(customerId) && item.getProduct().getId().equals(productId))).toList());
        return productId;
    }
}
