package com.example.theclothingapplication.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theclothingapplication.API.ApiClient;
import com.example.theclothingapplication.API.Model.CartItemsModel;
import com.example.theclothingapplication.API.Model.ProductModel;
import com.example.theclothingapplication.GlobalVariables;
import com.example.theclothingapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private ArrayList<CartItemsModel> cartItemsList;
    private Context context;
    //    private String IP = "http://192.168.43.249:";
    private String IP = GlobalVariables.IP;
    private String token;

    public CartAdapter(ArrayList<CartItemsModel> cartItemsList, Context context) {
        this.cartItemsList = cartItemsList;
        this.context = context;

        SharedPreferences sharedPreferences = context.getSharedPreferences("Login", MODE_PRIVATE);
        token = "Bearer " + sharedPreferences.getString("token", "");
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, final int position) {
        String rupee = holder.itemView.getContext().getResources().getString(R.string.Rs);
        holder.productName.setText(cartItemsList.get(position).getProduct().getName());
        holder.productPrice.setText(rupee + " " + cartItemsList.get(position).getProduct().getPrice());
        holder.productQuantity.setText("Qty: " + cartItemsList.get(position).getQuantity());
        Picasso.get().load(IP + cartItemsList.get(position).getProduct().getProductImage().split(":", 3)[2]).into(holder.productImage);

        holder.cartRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartItemsModel cartItem = new CartItemsModel(cartItemsList.get(position).get_id());
                Call<CartItemsModel> call = ApiClient.getInstance().getApi().deleteCartItem(token, cartItem);
                call.enqueue(new Callback<CartItemsModel>() {
                    @Override
                    public void onResponse(Call<CartItemsModel> call, Response<CartItemsModel> response) {
                        if (response.code() == 201) {
                            Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
                            ((Activity) context).finish();
                            ((Activity) context).startActivity(((Activity) context).getIntent());
                        } else {
                            Toast.makeText(context, "error: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CartItemsModel> call, Throwable t) {
                        Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemsList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName, productPrice, productQuantity;
        private Button cartRemoveBtn;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.cart_product_image);
            productName = itemView.findViewById(R.id.cart_product_name);
            productPrice = itemView.findViewById(R.id.cart_product_price);
            productQuantity = itemView.findViewById(R.id.cart_product_quantity);
            cartRemoveBtn = itemView.findViewById(R.id.cart_remove);
        }
    }
}
