package com.example.theclothingapplication.API.Model;

public class ProductModel
{
    private String name, price, description, productImage, parentId ;

    public ProductModel(String name, String price, String description, String productImage, String parentId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.productImage = productImage;
        this.parentId = parentId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
