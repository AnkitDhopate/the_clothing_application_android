package com.example.theclothingapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theclothingapplication.API.Model.CategoryApiModel;
import com.example.theclothingapplication.GlobalVariables;
import com.example.theclothingapplication.R;
import com.example.theclothingapplication.SubCategoryActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private ArrayList<CategoryApiModel> categoryList;
    private Context context;
//    private String IP = "http://192.168.43.249:" ;
    private String IP = GlobalVariables.IP;

    public CategoryAdapter(ArrayList<CategoryApiModel> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder holder, final int position) {
        holder.category_name.setText(categoryList.get(position).getName());
        Picasso.get().load(IP+categoryList.get(position).getCategoryImage().split(":", 3)[2]).into(holder.category_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.loadSubCategories(categoryList, context, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView category_image;
        private TextView category_name;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            category_image = itemView.findViewById(R.id.category_image);
            category_name = itemView.findViewById(R.id.category_name);
        }

        public void loadSubCategories(ArrayList<CategoryApiModel> list, Context context, int position)
        {
            Intent intent = new Intent(context, SubCategoryActivity.class);
            intent.putExtra("list", list);
            intent.putExtra("CategoryName", list.get(position).getName());
            itemView.getContext().startActivity(intent);
        }
    }
}
