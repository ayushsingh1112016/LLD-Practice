package Day1b;

import java.util.*;
public class Product {
  private final Long productId;
  private String name;
  private String description;
  private double price;
  private Long adminId;

  public Product(Long productId, String name, double price, Long adminId) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.adminId = adminId;
    }
    
  public Long getProductId() { return productId; }
  public String getName() { return name; }
  public double getPrice() { return price; }
  public Long getAdminId() { return adminId; }
}