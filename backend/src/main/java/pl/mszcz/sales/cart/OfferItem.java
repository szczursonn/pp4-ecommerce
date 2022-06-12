package pl.mszcz.sales.cart;

import pl.mszcz.productcatalog.ProductData;

import java.io.Serializable;

public record OfferItem(ProductData product, int quantity) implements Serializable {
}
