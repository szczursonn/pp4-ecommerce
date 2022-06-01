package pl.mszcz.sales.purchase;

public record RegisterPaymentResponse(
        String paymentId,
        String url
) {
}
