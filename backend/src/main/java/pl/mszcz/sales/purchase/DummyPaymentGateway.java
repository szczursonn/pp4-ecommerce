package pl.mszcz.sales.purchase;

import java.util.UUID;

public class DummyPaymentGateway implements PaymentGateway {
    @Override
    public RegisterPaymentResponse handle(RegisterPaymentRequest request) {
        return new RegisterPaymentResponse(UUID.randomUUID().toString(), "http://gigaprzelewy.pl/"+request.purchaseId());
    }
}
