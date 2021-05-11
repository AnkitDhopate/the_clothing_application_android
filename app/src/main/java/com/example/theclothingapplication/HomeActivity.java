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
import com.example.theclothingapplication.API.Model.HomeRecyclerViewModel;
import com.example.theclothingapplication.API.Model.ProductModel;
import com.example.theclothingapplication.Adapters.HomeRecyclerViewAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<CategoryApiModel> apiModelArrayList;
    private RecyclerView homeRecyclerView;
    private HomeRecyclerViewAdapter homeAdapter;
    private Map<String, Integer> hashMap;
    private static int countTotalProducts = 0;

    private ArrayList<HomeRecyclerViewModel> homeRecyclerViewModelList;

    // Drawer
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    // Drawer

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        apiModelArrayList = new ArrayList<>();
        homeRecyclerViewModelList = new ArrayList<>();
        hashMap = new HashMap<>();

        homeRecyclerView = findViewById(R.id.home_recycler_view);
        homeRecyclerView.setHasFixedSize(true);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadingBar = new ProgressDialog(this);
        loadingBar.setMessage("Loading products");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


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
        navigationView.setCheckedItem(R.id.nav_home);
        // Drawer

        Call<ArrayList<CategoryApiModel>> call = ApiClient.getInstance().getApi().getCategories();
        final Call<ArrayList<ProductModel>> productModelCall = ApiClient.getInstance().getApi().getAllProducts();

        call.enqueue(new Callback<ArrayList<CategoryApiModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryApiModel>> call, Response<ArrayList<CategoryApiModel>> response) {
                if (response.code() == 200) {
                    for (CategoryApiModel categoryApiModel : response.body()) {
                        apiModelArrayList.add(categoryApiModel);
                    }
                    homeRecyclerViewModelList.add(new HomeRecyclerViewModel(apiModelArrayList));

                    homeRecyclerViewModelList.add(new HomeRecyclerViewModel("All Products"));

                    productModelCall.enqueue(new Callback<ArrayList<ProductModel>>() {
                        @Override
                        public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                            if (response.code() == 201) {
                                for (ProductModel productModel : response.body()) {
                                    if (hashMap.containsKey(productModel.getParentId()) && hashMap.get(productModel.getParentId()) < 3 && countTotalProducts <= 12) {
                                        homeRecyclerViewModelList.add(new HomeRecyclerViewModel(productModel));
                                        hashMap.put(productModel.getParentId(), hashMap.get(productModel.getParentId()) + 1);
                                        countTotalProducts++;
                                    } else if (countTotalProducts <= 12) {
                                        if (!hashMap.containsKey(productModel.getParentId())) {
                                            hashMap.put(productModel.getParentId(), 1);
                                        } else {
                                            hashMap.put(productModel.getParentId(), hashMap.get(productModel.getParentId()) + 1);
                                        }
                                    }
                                }

                                loadingBar.dismiss();
                                homeAdapter = new HomeRecyclerViewAdapter(homeRecyclerViewModelList, HomeActivity.this);
                                homeRecyclerView.setAdapter(homeAdapter);
                            } else {
                                loadingBar.dismiss();
                                Toast.makeText(HomeActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                            loadingBar.dismiss();
                            Toast.makeText(HomeActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    loadingBar.dismiss();
                    Toast.makeText(HomeActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryApiModel>> call, Throwable t) {
                loadingBar.dismiss();
                Toast.makeText(HomeActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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

            case R.id.nav_home:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_men:
                Intent menIntent = new Intent(this, SubCategoryActivity.class);
                menIntent.putExtra("list", apiModelArrayList);
                menIntent.putExtra("CategoryName", "Mens");
                startActivity(menIntent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_women:
                Intent womenIntent = new Intent(this, SubCategoryActivity.class);
                womenIntent.putExtra("list", apiModelArrayList);
                womenIntent.putExtra("CategoryName", "Womens");
                startActivity(womenIntent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_kids:
                Intent kidsIntent = new Intent(this, SubCategoryActivity.class);
                kidsIntent.putExtra("list", apiModelArrayList);
                kidsIntent.putExtra("CategoryName", "Kids");
                startActivity(kidsIntent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_new_born:
                Intent newBornIntent = new Intent(this, SubCategoryActivity.class);
                newBornIntent.putExtra("list", apiModelArrayList);
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