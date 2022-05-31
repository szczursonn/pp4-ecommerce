package pl.mszcz.sales.cart;

import java.math.BigDecimal;
import java.util.List;

public record Offer(List<OfferItem> items, BigDecimal total, int size) {
    public Offer(List<OfferItem> items) {
        this(
                items,
                items
                        .stream()
                        .map(item->item.product().getPrice().multiply(BigDecimal.valueOf(item.quantity())))
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO),
                items.size()
        );
    }
}
