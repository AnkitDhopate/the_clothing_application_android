package com.example.theclothingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.theclothingapplication.API.ApiClient;
import com.example.theclothingapplication.API.Model.ProductModel;
import com.example.theclothingapplication.Adapters.ProductsAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity
{
    private String slug ;
    private ArrayList<ProductModel> productArrayList ;
    private RecyclerView productsReyclerView;
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        productArrayList = new ArrayList<>();

        productsReyclerView = findViewById(R.id.products_recycler_view);
        productsReyclerView.setHasFixedSize(true);
        productsReyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        slug = intent.getStringExtra("slug");

        Call<ArrayList<ProductModel>> productsCall = ApiClient.getInstance().getApi().getProductsBySlug(slug);
        productsCall.enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                if(response.code() == 200)
                {
                    for(ProductModel productModel:response.body())
                    {
                        productArrayList.add(productModel);
                    }
                    productsAdapter = new ProductsAdapter(productArrayList, ProductsActivity.this);
                    productsReyclerView.setAdapter(productsAdapter);
                }else
                {
                    Toast.makeText(ProductsActivity.this, "Error while fetching products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(ProductsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}