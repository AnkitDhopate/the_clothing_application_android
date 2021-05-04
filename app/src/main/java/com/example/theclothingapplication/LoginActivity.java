package com.example.theclothingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity
{
    private TextView login_btn, signup_btn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn = findViewById(R.id.login);
        signup_btn = findViewById(R.id.signup);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadLoginFragment();
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSignUpFragment();
            }
        });

        loadLoginFragment();
    }

    private void loadLoginFragment()
    {
        LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.log_sign_layout, loginFragment);
        transaction.commit();
    }

    private void loadSignUpFragment()
    {
        SignUpFragment signUpFragment = new SignUpFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.log_sign_layout, signUpFragment);
        transaction.commit();
    }
}