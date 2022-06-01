package pl.mszcz.sales.purchase;

import pl.mszcz.sales.exceptions.CantRegisterPaymentException;

public interface PaymentGateway {
    RegisterPaymentResponse handle(RegisterPaymentRequest request) throws CantRegisterPaymentException;
}
