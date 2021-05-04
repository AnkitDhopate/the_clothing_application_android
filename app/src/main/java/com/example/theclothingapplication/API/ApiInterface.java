package com.example.theclothingapplication.API;

import com.example.theclothingapplication.API.Model.CategoryApiModel;
import com.example.theclothingapplication.API.Model.LoginApiModel;
import com.example.theclothingapplication.API.Model.ProductModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface
{
    @POST("api/signin")
    Call<LoginApiModel> userSignIn(@Body LoginApiModel loginApiModel);

    @POST("api/signup")
    Call<LoginApiModel> userSignUp(@Body LoginApiModel loginApiModel);

    @GET("api/category/getcategorymob")
    Call<ArrayList<CategoryApiModel>> getCategories();

    @GET("api/products/{slug}")
    Call<ArrayList<ProductModel>> getProductsBySlug(@Path("slug") String slug);

    @GET("api/product/getallproductsmob")
    Call<ArrayList<ProductModel>> getAllProducts();

}

//    Call<ArrayList<CategoryApiModel>> getCategories();