package pl.mszcz.sales;

import java.util.List;

public interface PurchaseItemStorage {
    void save(PurchaseItem purchaseItem);
    void remove(Long itemId);
    List<PurchaseItem> getAllItems(Long purchaseId);
}
