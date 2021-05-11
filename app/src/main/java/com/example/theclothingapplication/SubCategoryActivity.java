package com.example.theclothingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.theclothingapplication.API.Model.CategoryApiModel;
import com.example.theclothingapplication.Adapters.SubCategoryAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private RecyclerView subCategoryRecyclerView ;
    private SubCategoryAdapter subCategoryAdapter;
    private ArrayList<CategoryApiModel> subCategoryList ;
    private String categoryName, categoryImage;
    private ArrayList<CategoryApiModel> apiModelArrayList;

    // Drawer
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    // Drawer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        /*
        Intent intent = getIntent();
        CategoryApiModel categoryApiModel = intent.getParcelableExtra("subCategory");
        subCategoryList = categoryApiModel.getChildrenCategory();
        categoryName = categoryApiModel.getName();
        categoryImage = categoryApiModel.getCategoryImage();
        */

        Intent intent = getIntent();
        apiModelArrayList = intent.getParcelableArrayListExtra("list");
        categoryName = intent.getStringExtra("CategoryName");

        for (CategoryApiModel api : apiModelArrayList) {
            if (api.getName().equals(categoryName)) {
                subCategoryList = api.getChildrenCategory();
            }
        }

        subCategoryRecyclerView = findViewById(R.id.sub_category_recycler_view);
        subCategoryRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        subCategoryRecyclerView.setLayoutManager(gridLayoutManager);
        subCategoryAdapter = new SubCategoryAdapter(subCategoryList, this, apiModelArrayList);
        subCategoryRecyclerView.setAdapter(subCategoryAdapter);


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
                menIntent.putExtra("list", apiModelArrayList);
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
                womenIntent.putExtra("list", apiModelArrayList);
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
                kidsIntent.putExtra("list", apiModelArrayList);
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