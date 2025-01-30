package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ShoppingCartTest {

    private ShoppingCart shoppingCart;

    @BeforeEach
    public void setUp() {
        shoppingCart = new ShoppingCart();
    }

    @Test
    public void testAddItem() {
        Item item = new Item("banana", 10, 1);
        shoppingCart.addItem(item);
        assertThat(shoppingCart.getItems()).contains(item);
    }

    @Test
    public void testRemoveItem() {
        Item item = new Item("banana", 10, 1);
        shoppingCart.addItem(item);
        shoppingCart.removeItem(item);
        assertThat(shoppingCart.getItems()).doesNotContain(item);
    }

    @Test
    public void testCalculateTotalPrice(){
        Item item = new Item("banana", 10, 1);
        shoppingCart.addItem(item);
        Item item1 = new Item("apple", 5, 1);
        shoppingCart.addItem(item1);
        assertThat(shoppingCart.getTotalPrice()).isEqualTo(15);
    }

    @Test
    public void addDiscountToItem(){
        Item item = new Item("banana", 10, 1);
        shoppingCart.addItem(item);
        shoppingCart.addDiscount(10);
        assertThat(shoppingCart.getTotalPrice()).isEqualTo(9);
    }
}
