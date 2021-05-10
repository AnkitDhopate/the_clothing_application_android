package com.example.theclothingapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theclothingapplication.API.Model.ProductModel;
import com.example.theclothingapplication.ProductDetailsActivity;
import com.example.theclothingapplication.ProductsActivity;
import com.example.theclothingapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {
    private ArrayList<ProductModel> productsList;
    private Context context;
    // IP 18->2
    private String IP = "http://192.168.43.249:";
    // IP

    public ProductsAdapter(ArrayList<ProductModel> productsList, Context context) {
        this.productsList = productsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_layout, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, final int position) {
        holder.productName.setText(productsList.get(position).getName());
        String rupee = holder.itemView.getContext().getResources().getString(R.string.Rs);
        holder.productPrice.setText(rupee + " " + productsList.get(position).getPrice());
        holder.productDesc.setText(productsList.get(position).getDescription());
        Picasso.get().load(IP + productsList.get(position).getProductImage().split(":", 3)[2]).into(holder.productImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("productName", productsList.get(position).getName());
                intent.putExtra("productImage", IP+productsList.get(position).getProductImage().split(":", 3)[2]);
                intent.putExtra("productPrice", context.getResources().getString(R.string.Rs)+" "+productsList.get(position).getPrice());
                intent.putExtra("productDesc", productsList.get(position).getDescription());
                intent.putExtra("productId", productsList.get(position).get_id());
                intent.putExtra("parentId", productsList.get(position).getParentId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName, productPrice, productDesc;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productDesc = itemView.findViewById(R.id.product_description);
        }
    }
}
