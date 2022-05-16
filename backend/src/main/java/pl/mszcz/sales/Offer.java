package pl.mszcz.sales;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Offer {
    private final BigDecimal total;
    private final int size;
    private final List<CartItem> items;

    public Offer() {
        this(new ArrayList<>());
    }

    public Offer(List<CartItem> items) {
        this.items = items;
        this.total = items
                .stream()
                .map(item->item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        this.size = items.size();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public int getItemsCount() {
        return size;
    }
}
