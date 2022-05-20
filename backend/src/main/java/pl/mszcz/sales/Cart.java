package pl.mszcz.sales;

public class Cart {
    private CartItemStorage cartItemStorage;
    private String customerId;

    public Cart(String customerId, CartItemStorage cartItemStorage) {
        this.cartItemStorage = cartItemStorage;
        this.customerId = customerId;
    }

    public void add(CartItem cartItem) {
        cartItemStorage.save(cartItem);
    }

    public Long remove(Long productId) {
        cartItemStorage.remove(productId, customerId);
        return productId;
    }

    public Offer getOffer() {
        return new Offer(
                cartItemStorage.getAllCustomerItems(customerId)
        );
    }

    public String getCustomerId() {
        return customerId;
    }
}
