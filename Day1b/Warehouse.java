package Day1b;
import java.util.*;
public class Warehouse {
    private Map<Long, List<SubProduct>> inventory = new HashMap<>();

    public void addStock(Product product, List<SubProduct> newStock) {
        inventory.putIfAbsent(product.getProductId(), new ArrayList<>());
        inventory.get(product.getProductId()).addAll(newStock);
    }

    public int getAvailableStockCount(Long productId) {
        if (!inventory.containsKey(productId)) return 0;
        
        return (int) inventory.get(productId).stream()
                .filter(item -> item.getStatus() == SubProduct.ItemStatus.AVAILABLE)
                .count();
    }

    // Retrieves an available physical item for checkout/cart
    public SubProduct reserveAvailableItem(Long productId) {
        if (!inventory.containsKey(productId)) return null;

        for (SubProduct item : inventory.get(productId)) {
            if (item.getStatus() == SubProduct.ItemStatus.AVAILABLE) {
                item.setStatus(SubProduct.ItemStatus.IN_CART);
                return item;
            }
        }
        return null; // Out of stock
    }
}