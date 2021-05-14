package com.example.theclothingapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theclothingapplication.API.Model.CategoryApiModel;
import com.example.theclothingapplication.API.Model.HomeRecyclerViewModel;
import com.example.theclothingapplication.ProductDetailsActivity;
import com.example.theclothingapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<HomeRecyclerViewModel> homeRecyclerViewModelList;
    private Context context;

    private String IP = "http://192.168.43.249:" ;

    public HomeRecyclerViewAdapter(ArrayList<HomeRecyclerViewModel> homeRecyclerViewModelList, Context context) {
        this.homeRecyclerViewModelList = homeRecyclerViewModelList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        switch (homeRecyclerViewModelList.get(position).getType()) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                View category_view = LayoutInflater.from(context).inflate(R.layout.category_recycler_view, parent, false);
                return new CategoryViewHolder(category_view);

            case 1:
                View all_products_view = LayoutInflater.from(context).inflate(R.layout.home_product_layout, parent, false);
                return new AllProductsViewHolder(all_products_view);

            case 2:
                View all_products_title = LayoutInflater.from(context).inflate(R.layout.home_all_products_title, parent, false);
                return new AllProductsTitleViewHolder(all_products_title);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position)
    {
        switch (homeRecyclerViewModelList.get(position).getType())
        {
            case 0 :
                ((CategoryViewHolder)holder).setCategory(homeRecyclerViewModelList.get(position).getCategoryApiModelList(), context);
                break;

            case 1 :
                String rupee = holder.itemView.getContext().getResources().getString(R.string.Rs);
                ((AllProductsViewHolder)holder).p_name.setText(homeRecyclerViewModelList.get(position).getProductModel().getName());
                ((AllProductsViewHolder)holder).p_price.setText(rupee + " " + homeRecyclerViewModelList.get(position).getProductModel().getPrice());
                Picasso.get().load(IP+homeRecyclerViewModelList.get(position).getProductModel().getProductImage().split(":", 3)[2]).into(((AllProductsViewHolder)holder).p_image);

                ((AllProductsViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ProductDetailsActivity.class);
                        intent.putExtra("productName", homeRecyclerViewModelList.get(position).getProductModel().getName());
                        intent.putExtra("productImage", IP+homeRecyclerViewModelList.get(position).getProductModel().getProductImage().split(":", 3)[2]);
                        intent.putExtra("productPrice", context.getResources().getString(R.string.Rs)+" "+homeRecyclerViewModelList.get(position).getProductModel().getPrice());
                        intent.putExtra("productDesc", homeRecyclerViewModelList.get(position).getProductModel().getDescription());
                        intent.putExtra("productId", homeRecyclerViewModelList.get(position).getProductModel().get_id());
                        intent.putExtra("parentId", homeRecyclerViewModelList.get(position).getProductModel().getParentId());
                        context.startActivity(intent);
                    }
                });
                break ;

            case 2 :
                ((AllProductsTitleViewHolder)holder).title.setText(homeRecyclerViewModelList.get(position).getAll_products_title());
                break;

            default:
                break ;
        }
    }

    @Override
    public int getItemCount() {
        return homeRecyclerViewModelList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView category_recycler_view;
        private CategoryAdapter categoryAdapter;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            category_recycler_view = itemView.findViewById(R.id.category_recycler_view);
        }

        public void setCategory(ArrayList<CategoryApiModel> categoryList, Context context) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            category_recycler_view.setLayoutManager(linearLayoutManager);
            category_recycler_view.setHasFixedSize(true);

            categoryAdapter = new CategoryAdapter(categoryList, context);
            category_recycler_view.setAdapter(categoryAdapter);
        }
    }

    public class AllProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView p_name, p_price;
        private ImageView p_image;

        public AllProductsViewHolder(@NonNull View itemView) {
            super(itemView);

            p_name = itemView.findViewById(R.id.product_name);
            p_price = itemView.findViewById(R.id.product_price);
            p_image = itemView.findViewById(R.id.product_image);
        }
    }

    public class AllProductsTitleViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title;

        public AllProductsTitleViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.txt_title);
        }
    }
}