package com.example.theclothingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.theclothingapplication.API.ApiClient;
import com.example.theclothingapplication.API.Model.CategoryApiModel;
import com.example.theclothingapplication.API.Model.ProductModel;
import com.example.theclothingapplication.Adapters.ProductsAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private String slug ;
    private ArrayList<ProductModel> productArrayList ;
    private ArrayList<CategoryApiModel> list;
    private RecyclerView productsRecyclerView;
    private ProductsAdapter productsAdapter;

    // Drawer
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    // Drawer
    
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
        list = intent.getParcelableArrayListExtra("list");

        // Drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.home_nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        // Drawer

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

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_logout:
                SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Remember", "false");
                editor.putString("token", null);
                editor.apply();

                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;

            case R.id.nav_men:
                /*
                for (CategoryApiModel api : apiModelArrayList) {
                    if (api.getName().equals("Mens")) {
                        Intent intent = new Intent(this, SubCategoryActivity.class);
                        intent.putExtra("subCategory", api);
                        startActivity(intent);
                    }
                }
                 */

                Intent menIntent = new Intent(this, SubCategoryActivity.class);
                menIntent.putExtra("list", list);
                menIntent.putExtra("CategoryName", "Mens");
                startActivity(menIntent);


                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_women:
                /*
                for (CategoryApiModel api : apiModelArrayList) {
                    if (api.getName().equals("Womens")) {
                        Intent intent = new Intent(this, SubCategoryActivity.class);
                        intent.putExtra("subCategory", api);
                        startActivity(intent);
                    }
                }
                 */

                Intent womenIntent = new Intent(this, SubCategoryActivity.class);
                womenIntent.putExtra("list", list);
                womenIntent.putExtra("CategoryName", "Womens");
                startActivity(womenIntent);

                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_kids:
                /*
                for (CategoryApiModel api : apiModelArrayList) {
                    if (api.getName().equals("Kids")) {
                        Intent intent = new Intent(this, SubCategoryActivity.class);
                        intent.putExtra("subCategory", api);
                        startActivity(intent);
                    }
                }
                */

                Intent kidsIntent = new Intent(this, SubCategoryActivity.class);
                kidsIntent.putExtra("list", list);
                kidsIntent.putExtra("CategoryName", "Kids");
                startActivity(kidsIntent);

                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_new_born:
                /*
                for (CategoryApiModel api : apiModelArrayList) {
                    if (api.getName().equals("New Born")) {
                        Intent intent = new Intent(this, SubCategoryActivity.class);
                        intent.putExtra("subCategory", api);
                        startActivity(intent);
                    }
                }
                 */

                Intent newBornIntent = new Intent(this, SubCategoryActivity.class);
                newBornIntent.putExtra("list", list);
                newBornIntent.putExtra("CategoryName", "New Born");
                startActivity(newBornIntent);

                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_cart:
                Intent intent = new Intent(this, CartActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
        return true;
    }
}