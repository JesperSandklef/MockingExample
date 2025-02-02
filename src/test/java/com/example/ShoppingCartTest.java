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
    //Test för att kolla så man kan lägga till ett item
    public void testAddItem() {
        Item item = new Item("banana", 10, 1);
        shoppingCart.addItem(item);
        assertThat(shoppingCart.getItems()).contains(item);
    }

    @Test
    //Test för att kolla så man kan ändra itemets kvantite
    public void testChangeItemQuantity(){
        Item item = new Item("banana", 10, 1);
        shoppingCart.addItem(item);
        shoppingCart.changeQuantity(item, 2);
        assertThat(item.getQuantity()).isEqualTo(2);
    }

    @Test
    //Test för att se så man kan lägga till rabbatt på sitt item
    public void testAddDiscountToItem(){
        Item item = new Item("banana", 10, 1);
        shoppingCart.addItem(item);
        shoppingCart.addDiscount(10);
        assertThat(shoppingCart.getTotalPrice()).isEqualTo(9);
    }

    @Test
    //Test för att se så att man kan ta bort ett item från varukorgen
    public void testRemoveItem() {
        Item item = new Item("banana", 10, 1);
        shoppingCart.addItem(item);
        shoppingCart.removeItem(item);
        assertThat(shoppingCart.getItems()).doesNotContain(item);
    }

    @Test
    //Test för att se så man kan räkna ut det totala priset för varukorgen
    public void testCalculateTotalPrice(){
        Item item = new Item("banana", 10, 1);
        shoppingCart.addItem(item);
        Item item1 = new Item("apple", 5, 1);
        shoppingCart.addItem(item1);
        assertThat(shoppingCart.getTotalPrice()).isEqualTo(15);
    }

    @Test
    //Kanttest för att se om man kan lägga till items med negativ kvantite
    public void testAddItemWithZeroOrNegativeQuantity() {
        Item item = new Item("banana", 10, 0);
        shoppingCart.addItem(item);
        assertThat(shoppingCart.getItems()).contains(item);

        item = new Item("apple", 5, -1);
        shoppingCart.addItem(item);
        assertThat(shoppingCart.getItems()).contains(item);
    }

    @Test
    //Kanttest för att kolla så man kan lägga till items med extrema priser
    public void testAddItemWithExtremePrice() {
        Item item = new Item("banana", Integer.MAX_VALUE, 1);
        shoppingCart.addItem(item);
        assertThat(shoppingCart.getTotalPrice()).isEqualTo(Integer.MAX_VALUE);

        item = new Item("apple", 1, Integer.MAX_VALUE);
        shoppingCart.addItem(item);
        assertThat(shoppingCart.getTotalPrice()).isEqualTo((long) Integer.MAX_VALUE + (long) Integer.MAX_VALUE);
    }

    @Test
    //Kanttest för att se om man kan lägga till rabatter större än det totala priset på ett item
    public void testApplyDiscountGreaterThanTotalPrice() {
        Item item = new Item("banana", 10, 1);
        shoppingCart.addItem(item);
        shoppingCart.addDiscount(110);
        assertThat(shoppingCart.getTotalPrice()).isEqualTo(-1);
    }

    @Test
    //Kanttest för att se om man kan lägga till kvantite som är noll eller negativt
    public void testChangeQuantityToZeroOrNegative() {
        Item item = new Item("banana", 10, 1);
        shoppingCart.addItem(item);
        shoppingCart.changeQuantity(item, 0);
        assertThat(item.getQuantity()).isEqualTo(0);

        shoppingCart.changeQuantity(item, -1);
        assertThat(item.getQuantity()).isEqualTo(-1);
    }

    @Test
    //Kanttest för att se om man kan ta bort ett icke existerande item
    public void testRemoveNonExistingItem() {
        Item item = new Item("banana", 10, 1);
        shoppingCart.addItem(item);
        Item nonExistingItem = new Item("apple", 5, 1);
        shoppingCart.removeItem(nonExistingItem);
        assertThat(shoppingCart.getItems()).contains(item);
        assertThat(shoppingCart.getItems()).doesNotContain(nonExistingItem);
    }
}
