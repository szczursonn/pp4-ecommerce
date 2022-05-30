package pl.mszcz.sales.purchase;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class JpaPurchaseStorage implements PurchaseStorage {
    @Autowired
    PurchaseRepository repo;

    @Override
    public Optional<Purchase> getPurchaseById(Long purchaseId) {
        return repo.findById(purchaseId);
    }

    @Override
    public Purchase save(Purchase purchase) {
        return repo.save(purchase);
    }

    @Override
    public void remove(Long purchaseId) {
        repo.deleteById(purchaseId);
    }
}
