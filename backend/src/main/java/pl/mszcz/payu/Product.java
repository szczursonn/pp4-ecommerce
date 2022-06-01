package pl.mszcz.payu;

public record Product(
        String name,
        int unitPrice,
        int quantity
) {
}
