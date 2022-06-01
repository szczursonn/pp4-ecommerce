package pl.mszcz.payu;

public record OrderCreateResponse(
        Status status,
        String redirectUri,
        String orderId
) {
}
