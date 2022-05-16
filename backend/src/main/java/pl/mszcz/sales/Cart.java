package pl.mszcz.sales;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public static Cart empty() {
        return new Cart();
    }

    public int getItemsCount() {
        return items.size();
    }

    public BigDecimal getTotal() {
        BigDecimal total = items
                .stream()
                .map(this::calculateLineTotal)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        return total;
    }

    private BigDecimal calculateLineTotal(CartItem cartItem) {
        return cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
    }

    public void add(CartItem item) {
        items.add(item);
    }
}
