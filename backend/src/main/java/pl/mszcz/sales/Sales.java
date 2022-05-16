package pl.mszcz.sales;

import java.math.BigDecimal;

public class Sales {

    CartStorage cartStorage;
    ProductDetailsProvider productDetailsProvider;

    public Sales(CartStorage cartStorage, ProductDetailsProvider productDetailsProvider) {
        this.cartStorage = cartStorage;
        this.productDetailsProvider = productDetailsProvider;
    }

    public Offer getCurrentOffer(String customerId) {
        Cart cart = cartStorage.getForCustomer(customerId)
                .orElse(Cart.empty());

        return Offer.of(cart.getTotal(), cart.getItemsCount());
    }

    public void addToCart(String customerId, String productId) throws ProductNotAvailableException {
        Cart cart = cartStorage.getForCustomer(customerId)
                .orElse(Cart.empty());

        ProductDetails details = productDetailsProvider.findById(productId)
                .orElseThrow(() -> new ProductNotAvailableException());

        cart.add(CartItem.of(productId, details.getName(), details.getPrice()));

        cartStorage.save(customerId, cart);
    }
}
