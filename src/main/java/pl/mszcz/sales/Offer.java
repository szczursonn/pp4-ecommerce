package pl.mszcz.sales;

import java.math.BigDecimal;

public class Offer {
    BigDecimal total;

    public Offer() {
        this.total = BigDecimal.ZERO;
    }

    public static Offer blank() {
        return new Offer();
    }

    public BigDecimal getTotal() {
        return total;
    }
}
