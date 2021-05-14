package com.example.theclothingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
    private Button proceedBtn;
    private int totalPrice ;

    private ProgressDialog loadingBar ;

    private LinearLayout cart_empty_layout, linearLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        totalText = findViewById(R.id.total_price);
        cartItemsRecyclerView = findViewById(R.id.cart_items_recycler_view);
        cart_empty_layout = findViewById(R.id.cart_empty_layout);
        proceedBtn = findViewById(R.id.containedButton);
        linearLayout = findViewById(R.id.linearLayout);
        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartItemsRecyclerView.setHasFixedSize(true);
        cartItemsList = new ArrayList<>();

        loadingBar = new ProgressDialog(this);
        loadingBar.setMessage("Fetching Cart Items");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

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
                    if(!cartItemsList.isEmpty()) {
                        loadingBar.dismiss();
                        cart_empty_layout.setVisibility(View.GONE);
                        adapter = new CartAdapter(cartItemsList, CartActivity.this);
                        cartItemsRecyclerView.setAdapter(adapter);
                        totalText.setText("Total: " + getResources().getString(R.string.Rs) + " " + totalPrice);
                    }else
                    {
                        loadingBar.dismiss();
                        cartItemsRecyclerView.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.GONE);
                        cart_empty_layout.setVisibility(View.VISIBLE);
                    }
                }else
                {
                    loadingBar.dismiss();
                    Toast.makeText(CartActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CartParentModel> call, Throwable t) {
                loadingBar.dismiss();
                Toast.makeText(CartActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}