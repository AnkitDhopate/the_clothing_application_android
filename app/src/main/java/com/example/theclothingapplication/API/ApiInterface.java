package com.example.theclothingapplication.API;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.example.theclothingapplication.API.Model.CartItemsModel;
import com.example.theclothingapplication.API.Model.CartParentModel;
import com.example.theclothingapplication.API.Model.CategoryApiModel;
import com.example.theclothingapplication.API.Model.LoginApiModel;
import com.example.theclothingapplication.API.Model.ProductModel;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static android.content.Context.MODE_PRIVATE;

public interface ApiInterface {
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

    @GET("api/user/cart/getcartitems")
    Call<CartParentModel> getCartItems(@Header("Authorization") String authHeader);

    @POST("api/user/cart/addtocart")
    Call<CartParentModel> addToCart(@Header("Authorization") String authHeader, @Body CartParentModel cartItemsModel);
}