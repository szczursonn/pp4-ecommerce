package pl.mszcz.sales.purchase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MapPurchaseStorage implements PurchaseStorage {
    private Map<Long, Purchase> orders;

    public MapPurchaseStorage() {
        this.orders = new HashMap<>();
    }

    @Override
    public Purchase save(Purchase purchase) {
        Long id = purchase.getId();
        if (id == null) {
            id = generateId();
        }
        List<PurchaseItem> purchaseItems = purchase.
                getItems()
                .stream()
                .map(item->{
                    if (item.getId() == null) {
                        return new PurchaseItem(
                                generateId(),
                                item.getName(),
                                item.getPrice(),
                                item.getQuantity()
                        );
                    }
                    return item;
                })
                .toList();

        return new Purchase(
                id,
                purchaseItems,
                purchase.getCustomerId(),
                new CustomerInfo(
                        purchase.getCustomerFirstName(),
                        purchase.getCustomerLastName(),
                        purchase.getCustomerEmail()
                )
        );
    }

    @Override
    public void remove(Long purchaseId) {
        orders.remove(purchaseId);
    }

    @Override
    public Optional<Purchase> getPurchaseById(Long purchaseId) {
        return Optional.ofNullable(orders.get(purchaseId));
    }

    private Long generateId() {
        Long id = 1L;
        while (orders.get(id) != null) {
            id++;
        }
        return id;
    }
}
