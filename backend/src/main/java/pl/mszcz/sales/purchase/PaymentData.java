package pl.mszcz.sales.purchase;

public record PaymentData(
        String paymentId,
        Long purchaseId,
        String url
) {
}
