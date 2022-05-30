package pl.mszcz.sales;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class JpaPurchaseStorage implements PurchaseStorage {
    @Autowired
    PurchaseRepository repo;

    @Override
    public List<Purchase> getAllCustomerPurchases(String customerId) {
        return repo.findByCustomerId(customerId);
    }

    @Override
    public Optional<Purchase> getPurchaseById(Long purchaseId) {
        return repo.findById(purchaseId);
    }

    @Override
    public void save(Purchase purchase) {
        repo.save(purchase);
    }

    @Override
    public void remove(Long purchaseId) {
        repo.deleteById(purchaseId);
    }
}
