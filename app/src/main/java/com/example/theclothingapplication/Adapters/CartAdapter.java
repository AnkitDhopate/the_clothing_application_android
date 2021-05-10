package com.example.theclothingapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theclothingapplication.API.Model.CartItemsModel;
import com.example.theclothingapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>
{
    private ArrayList<CartItemsModel> cartItemsList;
    private Context context;
    private String IP = "http://192.168.43.249:";

    public CartAdapter(ArrayList<CartItemsModel> cartItemsList, Context context) {
        this.cartItemsList = cartItemsList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        String rupee = holder.itemView.getContext().getResources().getString(R.string.Rs);
        holder.productName.setText(cartItemsList.get(position).getProduct().getName());
        holder.productPrice.setText(rupee + " " + cartItemsList.get(position).getProduct().getPrice());
        holder.productQuantity.setText("Qty: " +  cartItemsList.get(position).getQuantity());
        Picasso.get().load(IP + cartItemsList.get(position).getProduct().getProductImage().split(":", 3)[2]).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return cartItemsList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView productImage;
        private TextView productName, productPrice, productQuantity ;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.cart_product_image);
            productName = itemView.findViewById(R.id.cart_product_name);
            productPrice = itemView.findViewById(R.id.cart_product_price);
            productQuantity = itemView.findViewById(R.id.cart_product_quantity);
        }
    }
}
