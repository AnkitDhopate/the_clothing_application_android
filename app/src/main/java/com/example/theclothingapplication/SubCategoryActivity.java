package com.example.theclothingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.theclothingapplication.API.Model.CategoryApiModel;
import com.example.theclothingapplication.Adapters.SubCategoryAdapter;

import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity
{
    private RecyclerView subCategoryRecyclerView ;
    private SubCategoryAdapter subCategoryAdapter;
    private ArrayList<CategoryApiModel> subCategoryList ;
    private String categoryName, categoryImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        Intent intent = getIntent();
        CategoryApiModel categoryApiModel = intent.getParcelableExtra("subCategory");
        subCategoryList = categoryApiModel.getChildrenCategory();
        categoryName = categoryApiModel.getName();
        categoryImage = categoryApiModel.getCategoryImage();

        subCategoryRecyclerView = findViewById(R.id.sub_category_recycler_view);
        subCategoryRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        subCategoryRecyclerView.setLayoutManager(gridLayoutManager);
        subCategoryAdapter = new SubCategoryAdapter(subCategoryList, this);
        subCategoryRecyclerView.setAdapter(subCategoryAdapter);
    }
}