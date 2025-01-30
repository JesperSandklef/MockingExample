package com.example;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private List<Item> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public List<Item> getItems(){
        return items;
    }

    public void addItem(Item item){
        items.add(item);
    }

    public void changeQuantity(Item item, int quantity) {
        item.setQuantity(quantity);
    }

    public void addDiscount(double percentage) {
        for (Item item : items) {
            double discount = item.getPrice() * (percentage / 100);
            item.setPrice(item.getPrice() - discount);
        }
    }

    public void removeItem(Item item){
        items.remove(item);
    }

    public double getTotalPrice() {
        return items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }
}
