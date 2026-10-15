package Day1b;

import java.util.*;
public class Main {
    public static void main(String[] args) {
        // 1. Initialize the central Warehouse
        Warehouse warehouse = new Warehouse();

        // 2. Admin logs in and creates a new product listing
        Admin admin = new Admin(101L, "SuperAdmin");
        Product ushaFan = admin.createProductListing(1L, "Usha Fan", 1500.00);
        
        System.out.println("--- Admin Actions ---");
        System.out.println("Product Created: " + ushaFan.getProductId());

        // 3. Admin adds physical stock to the warehouse (e.g., 5 Usha Fans)
        admin.addItemsToWarehouse(warehouse, ushaFan, 5);
        System.out.println("Initial Available Stock for Usha Fan: " + 
                warehouse.getAvailableStockCount(ushaFan.getProductId()));

        // 4. A User registers and logs in
        User alice = new User(501L, "Alice", "alice@example.com");
        
        System.out.println("\n--- User Actions ---");
        System.out.println("User " + alice.getName() + " is browsing...");

        // 5. User wants to buy an Usha Fan. System tries to reserve one from the warehouse.
        SubProduct reservedItem = warehouse.reserveAvailableItem(ushaFan.getProductId());

        if (reservedItem != null) {
            System.out.println("Successfully reserved physical item with Serial: " + reservedItem.getSerialNumber());
            
            // 6. Add the specific reserved item to the User's cart
            alice.getCart().addItem(ushaFan, reservedItem);
            System.out.println("Item added to Alice's cart.");
        } else {
            System.out.println("Sorry, Usha Fan is out of stock!");
        }

        // 7. Check the warehouse stock again to verify it decreased
        System.out.println("\n--- System Status ---");
        System.out.println("Remaining Available Stock for Usha Fan: " + 
                warehouse.getAvailableStockCount(ushaFan.getProductId()));
    }
}