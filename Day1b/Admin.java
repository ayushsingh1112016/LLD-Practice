package Day1b;

import java.util.*;
public class Admin {
    private final Long adminId;
    private String name;

    public Admin(Long adminId, String name) {
        this.adminId = adminId;
        this.name = name;
    }

    // Admin creates a new catalog product
    public Product createProductListing(Long productId, String name, double price) {
        return new Product(productId, name, price, this.adminId);
    }

    // Admin adds physical items to the warehouse
    public void addItemsToWarehouse(Warehouse warehouse, Product product, int quantity) {
        List<SubProduct> newStock = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            // Generating unique sub-product IDs
            Long uniqueId = System.nanoTime(); 
            String serial = "SN-" + uniqueId;
            newStock.add(new SubProduct(uniqueId, product.getProductId(), serial));
        }
        warehouse.addStock(product, newStock);
    }
}