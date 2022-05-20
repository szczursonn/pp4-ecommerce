package pl.mszcz.sales;

import pl.mszcz.productcatalog.ProductData;

public class Sales {

    private CartItemStorage cartItemStorage;
    private ProductDetailsProvider productDetailsProvider;

    public Sales(ProductDetailsProvider productDetailsProvider, CartItemStorage cartItemStorage) {
        this.productDetailsProvider = productDetailsProvider;
        this.cartItemStorage = cartItemStorage;
    }

    private Cart getCustomerCart(String customerId) {
        return new Cart(customerId, cartItemStorage);
    }

    public Offer getCurrentOffer(String customerId) {
        Cart cart = getCustomerCart(customerId);

        return cart.getOffer();
    }

    public void addToCart(String customerId, Long productId, int quantity) throws ProductNotAvailableException {
        Cart cart = getCustomerCart(customerId);

        ProductData product = productDetailsProvider.findById(productId)
                .orElseThrow(ProductNotAvailableException::new);

        cart.add(new CartItem(customerId, product, quantity));
    }

    public void removeFromCart(String customerId, Long productId) {
        Cart cart = getCustomerCart(customerId);

        cart.remove(productId);
    }
}
