package pl.mszcz.sales;

import pl.mszcz.productcatalog.ProductData;
import pl.mszcz.sales.cart.Cart;
import pl.mszcz.sales.cart.CartItem;
import pl.mszcz.sales.cart.CartItemStorage;
import pl.mszcz.sales.cart.Offer;
import pl.mszcz.sales.exceptions.CantRegisterPaymentException;
import pl.mszcz.sales.exceptions.EmptyPurchaseException;
import pl.mszcz.sales.exceptions.ProductNotAvailableException;
import pl.mszcz.sales.purchase.*;

import java.util.ArrayList;

public class Sales {

    private CartItemStorage cartItemStorage;
    private ProductDetailsProvider productDetailsProvider;
    private PurchaseStorage purchaseStorage;
    private PaymentGateway paymentGateway;

    public Sales(ProductDetailsProvider productDetailsProvider, CartItemStorage cartItemStorage, PurchaseStorage purchaseStorage, PaymentGateway paymentGateway) {
        this.productDetailsProvider = productDetailsProvider;
        this.cartItemStorage = cartItemStorage;
        this.purchaseStorage = purchaseStorage;
        this.paymentGateway = paymentGateway;
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

    public PaymentData createPurchase(String customerId, CustomerInfo customerInfo, String customerIp) throws EmptyPurchaseException, CantRegisterPaymentException {
        Cart cart = getCustomerCart(customerId);

        if (cart.getOffer().size() == 0) {
            throw new EmptyPurchaseException();
        }

        Purchase purchase = new Purchase(null, new ArrayList<>(), customerId, customerInfo);

        cart
                .getOffer()
                .items()
                .forEach(item->purchase.addItem(
                            item.product().getName(),
                            item.product().getPrice(),
                            item.quantity()
                        )
                );

        Purchase saved = purchaseStorage.save(purchase);

        // Empty the cart
        cart
                .getOffer()
                .items()
                .forEach(item->cartItemStorage.remove(item.product().getId(), customerId));

        return saved.registerPayment(paymentGateway, customerIp);
    }
}
