package pl.mszcz.sales.cart;

import pl.mszcz.productcatalog.ProductData;

public record OfferItem(ProductData product, int quantity) {
}
