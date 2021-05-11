package com.example.theclothingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theclothingapplication.API.ApiClient;
import com.example.theclothingapplication.API.Model.CartItemsModel;
import com.example.theclothingapplication.API.Model.CartParentModel;
import com.example.theclothingapplication.Adapters.CartAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity
{
    private RecyclerView cartItemsRecyclerView ;
    private CartAdapter adapter;
    private ArrayList<CartItemsModel> cartItemsList ;
    private TextView totalText ;
    private int totalPrice ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        totalText = findViewById(R.id.total_price);
        cartItemsRecyclerView = findViewById(R.id.cart_items_recycler_view);
        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartItemsRecyclerView.setHasFixedSize(true);
        cartItemsList = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        String token = "Bearer "+sharedPreferences.getString("token", "");

        Call<CartParentModel> cartParentModelCall = ApiClient.getInstance().getApi().getCartItems(token);
        cartParentModelCall.enqueue(new Callback<CartParentModel>() {
            @Override
            public void onResponse(Call<CartParentModel> call, Response<CartParentModel> response) {
                if(response.code() == 200)
                {
                    for(CartItemsModel cartItems : response.body().getCartItems())
                    {
                        cartItemsList.add(cartItems);
                        totalPrice += (Integer.parseInt(cartItems.getQuantity())*Integer.parseInt(cartItems.getProduct().getPrice())) ;
                    }
                    adapter = new CartAdapter(cartItemsList, CartActivity.this);
                    cartItemsRecyclerView.setAdapter(adapter);
                    totalText.setText("Total: " + getResources().getString(R.string.Rs) + " " + totalPrice);
                }else
                {
                    Toast.makeText(CartActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CartParentModel> call, Throwable t) {
                Toast.makeText(CartActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}