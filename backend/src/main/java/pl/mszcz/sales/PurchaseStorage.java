package pl.mszcz.sales;

import java.util.List;
import java.util.Optional;

public interface PurchaseStorage {
    Purchase save(Purchase purchase);
    void remove(Long purchaseId);
    List<Purchase> getAllCustomerPurchases(String customerId);
    Optional<Purchase> getPurchaseById(Long purchaseId);
}
