package pl.mszcz.sales;

import java.math.BigDecimal;

public class ProductDetails {
    private final Long productId;
    private final String name;
    private final BigDecimal price;

    public ProductDetails(Long productId, String name, BigDecimal price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

}
