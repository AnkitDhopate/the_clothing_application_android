package com.example.theclothingapplication.API.Model;

import java.util.ArrayList;

public class HomeRecyclerViewModel
{
    private static final int CATEGORIES_RECYCLER_VIEW = 0 ;
    private static final int ALL_PRODUCTS = 1 ;
    private static final int PRODUCT_TITLE_LAYOUT = 2 ;

    private int type;

    // Categories Recy
    private ArrayList<CategoryApiModel> categoryApiModelList ;

    public HomeRecyclerViewModel(ArrayList<CategoryApiModel> categoryApiModelList)
    {
        this.categoryApiModelList = categoryApiModelList;
        this.type = CATEGORIES_RECYCLER_VIEW ;
    }

    public static int getCategoriesRecyclerView() {
        return CATEGORIES_RECYCLER_VIEW;
    }

    public static int getAllProducts() {
        return ALL_PRODUCTS;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<CategoryApiModel> getCategoryApiModelList() {
        return categoryApiModelList;
    }

    public void setCategoryApiModelList(ArrayList<CategoryApiModel> categoryApiModelList) {
        this.categoryApiModelList = categoryApiModelList;
    }

    // Categories Recy

    // All Products

    private ProductModel productModel;

    public HomeRecyclerViewModel(ProductModel productModel) {
        this.productModel = productModel;
        this.type = 1 ;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }

    // All Products

    private String all_products_title ;

    public HomeRecyclerViewModel(String all_products_title) {
        this.all_products_title = all_products_title;
        this.type = PRODUCT_TITLE_LAYOUT ;
    }

    public static int getProductTitleLayout() {
        return PRODUCT_TITLE_LAYOUT;
    }

    public String getAll_products_title() {
        return all_products_title;
    }

    public void setAll_products_title(String all_products_title) {
        this.all_products_title = all_products_title;
    }
}
