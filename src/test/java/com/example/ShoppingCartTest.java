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
}
