package Day1b;

import java.util.*;
public class SubProduct {
    private final Long subProductId; // Unique ID for this specific fan
    private final Long productId;    // Reference to the parent catalog item
    private String serialNumber;
    private ItemStatus status;

    public enum ItemStatus {
        AVAILABLE, IN_CART, SOLD
    }

    public SubProduct(Long subProductId, Long productId, String serialNumber) {
        this.subProductId = subProductId;
        this.productId = productId;
        this.serialNumber = serialNumber;
        this.status = ItemStatus.AVAILABLE;
    }
    
    // Getters and Setters...
    public void setStatus(ItemStatus status) { this.status = status; }
    public Long getProductId() { return productId; }
    public Long getSubProductId() { return subProductId; }
    public String getSerialNumber() { return serialNumber; }
    public ItemStatus getStatus() { return status; }
}