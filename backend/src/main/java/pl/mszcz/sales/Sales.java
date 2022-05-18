package pl.mszcz.sales;

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

        return cart.getOffer();
    }

    public void addToCart(String customerId, Long productId, int quantity) throws ProductNotAvailableException {
        Cart cart = cartStorage.getForCustomer(customerId)
                .orElse(Cart.empty());

        ProductDetails details = productDetailsProvider.findById(productId)
                .orElseThrow(ProductNotAvailableException::new);

        cart.add(new CartItem(productId, details.getName(), details.getPrice(), quantity));

        cartStorage.save(customerId, cart);
    }

    public void removeFromCart(String customerId, Long productId) throws ProductNotAvailableException {
        Cart cart = cartStorage.getForCustomer(customerId)
                .orElse(Cart.empty());

        Long id = cart.remove(productId);

        if (id == null) throw new ProductNotAvailableException();

        cartStorage.save(customerId, cart);
    }
}
