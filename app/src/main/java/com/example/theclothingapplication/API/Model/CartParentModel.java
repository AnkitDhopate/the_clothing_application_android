package com.example.theclothingapplication.API.Model;

import java.util.ArrayList;

public class CartParentModel
{
    private ArrayList<CartItemsModel> cartItems;

    public CartParentModel(ArrayList<CartItemsModel> cartItems) {
        this.cartItems = cartItems;
    }

    public ArrayList<CartItemsModel> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItemsModel> cartItems) {
        this.cartItems = cartItems;
    }
}
