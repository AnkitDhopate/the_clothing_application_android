package com.example.theclothingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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
    private RecyclerView productsRecyclerView;
    private ProductsAdapter productsAdapter;
    
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        productArrayList = new ArrayList<>();

        productsRecyclerView = findViewById(R.id.products_recycler_view);
        productsRecyclerView.setHasFixedSize(true);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadingBar = new ProgressDialog(this);
        loadingBar.setMessage("Loading products");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

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
                    loadingBar.dismiss();
                    productsAdapter = new ProductsAdapter(productArrayList, ProductsActivity.this);
                    productsRecyclerView.setAdapter(productsAdapter);
                }else
                {
                    loadingBar.dismiss();
                    Toast.makeText(ProductsActivity.this, "Error while fetching products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                loadingBar.dismiss();
                Toast.makeText(ProductsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}