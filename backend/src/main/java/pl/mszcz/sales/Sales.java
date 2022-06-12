package pl.mszcz.sales;

import pl.mszcz.productcatalog.ProductData;
import pl.mszcz.sales.cart.*;
import pl.mszcz.sales.exceptions.*;
import pl.mszcz.sales.purchase.*;

import java.util.ArrayList;

public class Sales {

    private final CartItemStorage cartItemStorage;
    private final ProductDetailsProvider productDetailsProvider;
    private final PurchaseStorage purchaseStorage;
    private final PaymentGateway paymentGateway;
    private final OfferChecksumGenerator offerChecksumGenerator;

    public Sales(
            ProductDetailsProvider productDetailsProvider,
            CartItemStorage cartItemStorage,
            PurchaseStorage purchaseStorage,
            PaymentGateway paymentGateway,
            OfferChecksumGenerator offerChecksumGenerator
    ) {
        this.productDetailsProvider = productDetailsProvider;
        this.cartItemStorage = cartItemStorage;
        this.purchaseStorage = purchaseStorage;
        this.paymentGateway = paymentGateway;
        this.offerChecksumGenerator = offerChecksumGenerator;
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

    public PaymentData createPurchase(String customerId, CustomerInfo customerInfo, String customerIp, String offerChecksum) throws EmptyPurchaseException, CantRegisterPaymentException, CantGenerateOfferChecksumException, InvalidOfferChecksumException {
        Cart cart = getCustomerCart(customerId);

        if (cart.getOffer().size() == 0) {
            throw new EmptyPurchaseException();
        }

        if (!this.getOfferChecksum(cart.getOffer()).equals(offerChecksum)) {
            throw new InvalidOfferChecksumException();
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

    public String getOfferChecksum(Offer offer) throws CantGenerateOfferChecksumException {
        return offerChecksumGenerator.getChecksum(offer);
    }
}
