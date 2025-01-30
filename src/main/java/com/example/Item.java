package com.example;

public class Item {

    private String name;
    private double price;
    private int quantity;

    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price) {
        this.price = (int) price;
    }

    public int getQuantity(){
        return quantity;
    }


}
