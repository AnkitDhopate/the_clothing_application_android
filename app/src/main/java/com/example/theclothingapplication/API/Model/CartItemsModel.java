package com.example.theclothingapplication.API.Model;

public class CartItemsModel
{
    private String quantity, _id;
    private ProductModel product;

    public CartItemsModel(ProductModel product, String quantity, String _id) {
        this.quantity = quantity;
        this.product = product;
        this._id = _id;
    }

    public CartItemsModel(ProductModel product, String quantity ) {
        this.quantity = quantity;
        this.product = product;
    }

    public CartItemsModel(String _id) {
        this._id = _id;
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
