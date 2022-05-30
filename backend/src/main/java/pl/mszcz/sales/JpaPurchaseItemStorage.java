package pl.mszcz.sales;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class JpaPurchaseItemStorage implements PurchaseItemStorage {
    @Autowired
    PurchaseItemRepository repo;

    @Override
    public List<PurchaseItem> getAllItems(Long purchaseId) {
        return repo.findByPurchaseId(purchaseId);
    }

    @Override
    public void save(PurchaseItem purchaseItem) {
        repo.save(purchaseItem);
    }

    @Override
    public void remove(Long itemId) {
        repo.deleteById(itemId);
    }
}
