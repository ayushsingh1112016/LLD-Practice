package Day1b;

import java.util.*;
public class User {
    private final Long userId;
    private String name;
    private String email;
    private Cart cart;

    public User(Long userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.cart = new Cart(userId, userId); // 1-to-1 relation for simplicity
    }

    public Cart getCart() { return cart; }
    public Long getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}