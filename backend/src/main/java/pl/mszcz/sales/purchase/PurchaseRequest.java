package pl.mszcz.sales.purchase;

public record PurchaseRequest(CustomerInfo customerInfo, String checksum) {
}
