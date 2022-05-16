package pl.mszcz.sales;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Offer {
    private BigDecimal total;
    private int size;
    private ArrayList<OfferItem> items;

    public Offer() {
        this.total = BigDecimal.ZERO;
        this.items = new ArrayList<>();
        this.size = 0;
    }

    public Offer(BigDecimal total, int size) {
        this.total = total;
        this.items = new ArrayList<>();
        this.size = size;
    }

    public static Offer blank() {
        return new Offer();
    }

    public static Offer of(BigDecimal total, int cartSize) {
        return new Offer(total, cartSize);
    }

    public BigDecimal getTotal() {
        return total;
    }
}
