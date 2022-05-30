package pl.mszcz.sales.purchase;

import pl.mszcz.sales.purchase.RegisterPaymentRequest;
import pl.mszcz.sales.purchase.RegisterPaymentResponse;

public interface PaymentGateway {
    RegisterPaymentResponse handle(RegisterPaymentRequest request);
}
