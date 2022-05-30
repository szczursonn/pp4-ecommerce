package pl.mszcz.sales.purchase;

import java.util.Optional;

public interface PurchaseStorage {
    Purchase save(Purchase purchase);
    void remove(Long purchaseId);
    Optional<Purchase> getPurchaseById(Long purchaseId);
}
