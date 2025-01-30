package com.example;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private List<Item> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item){
        items.add(item);
    }

    public List<Item> getItems(){
        return items;
    }

    public void removeItem(Item item){
        items.remove(item);
    }

    public double getTotalPrice() {
        return items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }
}
