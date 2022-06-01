package pl.mszcz.sales.purchase;

import pl.mszcz.productcatalog.ProductData;
import pl.mszcz.sales.cart.OfferItem;

import java.math.BigDecimal;
import java.util.List;

public record RegisterPaymentRequest (
        Long purchaseId,
        BigDecimal total,
        String currencyCode,
        String purchaseDescription,
        CustomerInfo customerInfo,
        String customerIp,
        List<PurchaseItem> products
) {
}
