package pl.mszcz.payu;

import java.util.List;

public record OrderCreateRequest(
        String notifyUrl,
        String merchantPosId,
        String description,
        String currencyCode,
        int totalAmount,
        Buyer buyer,
        String customerIp,
        List<Product> products,
        String continueUrl
) {
}
