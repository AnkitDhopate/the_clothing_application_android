package com.example.theclothingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theclothingapplication.API.ApiClient;
import com.example.theclothingapplication.API.Model.CartItemsModel;
import com.example.theclothingapplication.API.Model.CartParentModel;
import com.example.theclothingapplication.API.Model.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity
{
    private ImageView productImage;
    private TextView productName, productPrice, productDesc, productQuantity ;
    private Button addToCart;
    private ImageButton minus, plus ;
    private String productId, parentId, productImg ;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Intent intent = getIntent();

        SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        final String token = "Bearer "+sharedPreferences.getString("token", "");

        productImage = findViewById(R.id.product_details_img);
        productName = findViewById(R.id.product_details_name);
        productPrice = findViewById(R.id.product_details_price);
        productDesc = findViewById(R.id.product_details_description);
        productQuantity = findViewById(R.id.product_qty);
        addToCart = findViewById(R.id.add_to_cart_btn);
        minus = findViewById(R.id.decrease_qty_btn);
        plus = findViewById(R.id.increase_qty_btn);
        loadingBar = new ProgressDialog(this);
        loadingBar.setMessage("adding product to cart ...");
        loadingBar.setCanceledOnTouchOutside(false);

        Picasso.get().load(intent.getStringExtra("productImage")).into(productImage);
        productName.setText(intent.getStringExtra("productName"));
        productPrice.setText(intent.getStringExtra("productPrice"));
        productDesc.setText("Description: "+intent.getStringExtra("productDesc"));
        productId = intent.getStringExtra("productId");
        parentId = intent.getStringExtra("parentId");
        productImg = intent.getStringExtra("productImage");

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increaseQty(productQuantity, Integer.parseInt(productQuantity.getText().toString()));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decreaseQty(productQuantity, Integer.parseInt(productQuantity.getText().toString()));
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loadingBar.show();
                ProductModel productModel = new ProductModel(productId, productName.getText().toString(), productPrice.getText().toString(), productDesc.getText().toString(), productImg, parentId);
                ArrayList<CartItemsModel> cartItemsModelList = new ArrayList<>();
                cartItemsModelList.add(new CartItemsModel(productModel, productQuantity.getText().toString()));
                CartParentModel cartParentModel = new CartParentModel(cartItemsModelList);
                Call<CartParentModel> call = ApiClient.getInstance().getApi().addToCart(token, cartParentModel);
                call.enqueue(new Callback<CartParentModel>() {
                    @Override
                    public void onResponse(Call<CartParentModel> call, Response<CartParentModel> response) {
                        if(response.code() == 201)
                        {
                            loadingBar.dismiss();
                            Toast.makeText(ProductDetailsActivity.this, "Product Added to cart", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(ProductDetailsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CartParentModel> call, Throwable t) {
                        loadingBar.dismiss();
                        Toast.makeText(ProductDetailsActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void increaseQty(TextView qtyTxt, int currVal)
    {
        currVal += 1 ;
        qtyTxt.setText(Integer.toString(currVal));
    }

    public void decreaseQty(TextView qtyTxt, int currVal)
    {
        if(currVal > 1) {
            currVal -= 1 ;
            qtyTxt.setText(Integer.toString(currVal));
        }
    }
}