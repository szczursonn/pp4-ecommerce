package pl.mszcz.sales.purchase;

import pl.mszcz.payu.*;
import pl.mszcz.sales.exceptions.CantRegisterPaymentException;

import java.math.BigDecimal;

public class PayUPaymentGateway implements PaymentGateway {

    private final PayU payU;

    public PayUPaymentGateway(PayU payU) {
        this.payU = payU;
    }

    @Override
    public RegisterPaymentResponse handle(RegisterPaymentRequest request) throws CantRegisterPaymentException {
        try {
            OrderCreateResponse res = payU.handle(
                    request.customerIp(),
                    request.purchaseDescription(),
                    request.total().multiply(BigDecimal.valueOf(100)).intValue(),
                    new Buyer(
                            request.customerInfo().getFirstName(),
                            request.customerInfo().getLastName(),
                            request.customerInfo().getEmail(),
                            "pl"
                    ),
                    request
                            .products()
                            .stream()
                            .map(
                                    item->new Product(
                                            item.getName(),
                                            item.getPrice().multiply(BigDecimal.valueOf(100)).intValue(),
                                            item.getQuantity()))
                            .toList()
            );



            return new RegisterPaymentResponse(
                    res.orderId(),
                    res.redirectUri()
            );
        } catch (CantCreateOrderException e) {
            throw new CantRegisterPaymentException();
        }

    }
}
