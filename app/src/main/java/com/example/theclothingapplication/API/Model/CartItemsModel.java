package com.example.theclothingapplication.API.Model;

public class CartItemsModel
{
    private String quantity;
    private ProductModel product;

    public CartItemsModel(ProductModel product, String quantity) {
        this.quantity = quantity;
        this.product = product;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }
}
