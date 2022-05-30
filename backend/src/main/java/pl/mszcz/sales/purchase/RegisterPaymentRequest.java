package pl.mszcz.sales.purchase;

import java.math.BigDecimal;

public record RegisterPaymentRequest (Long purchaseId, BigDecimal total, String firstName, String lastName, String email) {

}
