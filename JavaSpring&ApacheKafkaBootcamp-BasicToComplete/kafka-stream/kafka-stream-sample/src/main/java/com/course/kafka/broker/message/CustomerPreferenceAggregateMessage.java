package com.course.kafka.broker.message;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Section 15. Cart & Wishlist
 */
public class CustomerPreferenceAggregateMessage {

    private Map<String, String> wishlistItems;
    private Map<String, String> shoppingCartItems;

    public CustomerPreferenceAggregateMessage() {
        this.wishlistItems = new HashMap<String, String>();
        this.shoppingCartItems = new HashMap<>();
    }

    public void putShoppingCartItem(String itemName, LocalDateTime lastDateTime){
        this.shoppingCartItems.put(itemName, DateTimeFormatter.ISO_DATE_TIME.format(lastDateTime));
    }

    public void putWishlistItem(String itemName, LocalDateTime lastDateTime){
        this.wishlistItems.put(itemName, DateTimeFormatter.ISO_DATE_TIME.format(lastDateTime));
    }

    public Map<String, String> getWishlistItems() {
        return wishlistItems;
    }

    public void setWishlistItems(Map<String, String> wishlistItems) {
        this.wishlistItems = wishlistItems;
    }

    public Map<String, String> getShoppingCartItems() {
        return shoppingCartItems;
    }

    public void setShoppingCartItems(Map<String, String> shoppingCartItems) {
        this.shoppingCartItems = shoppingCartItems;
    }

    @Override
    public String toString() {
        return "CustomerPreferencesAggregateMessage{" +
                "wishListItems=" + wishlistItems +
                ", shoppingCartItems=" + shoppingCartItems +
                '}';
    }
}
