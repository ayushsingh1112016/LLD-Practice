package Day1b;
import java.util.*;
public class Cart {
    private final Long cartId;
    private final Long userId;
    // Maps a Product to the specific SubProducts reserved for the user
    private Map<Product, List<SubProduct>> items = new HashMap<>();

    public Cart(Long cartId, Long userId) {
        this.cartId = cartId;
        this.userId = userId;
    }

    public void addItem(Product product, SubProduct physicalItem) {
        items.putIfAbsent(product, new ArrayList<>());
        items.get(product).add(physicalItem);
    }
    
    public void clearCart() {
        items.clear();
    }
}