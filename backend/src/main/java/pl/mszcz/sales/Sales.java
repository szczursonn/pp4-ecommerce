package pl.mszcz.sales;

import pl.mszcz.productcatalog.ProductData;

import java.util.ArrayList;

public class Sales {

    private CartItemStorage cartItemStorage;
    private ProductDetailsProvider productDetailsProvider;
    private PurchaseStorage purchaseStorage;

    public Sales(ProductDetailsProvider productDetailsProvider, CartItemStorage cartItemStorage, PurchaseStorage purchaseStorage) {
        this.productDetailsProvider = productDetailsProvider;
        this.cartItemStorage = cartItemStorage;
        this.purchaseStorage = purchaseStorage;
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

    public Purchase createPurchase(String customerId) {
        Cart cart = getCustomerCart(customerId);

        Purchase purchase = new Purchase(null, customerId, new ArrayList<>());

        cart
                .getOffer()
                .getItems()
                .forEach(ci->purchase.addItem(
                            ci.getProduct().getName(),
                            ci.getProduct().getPrice(),
                            ci.getQuantity()
                        )
                );

        return purchaseStorage.save(purchase);
    }
}
