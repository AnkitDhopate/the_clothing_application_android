package com.example.theclothingapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.theclothingapplication.API.ApiClient;
import com.example.theclothingapplication.API.Model.LoginApiModel;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {

    private TextInputEditText email, password ;
    private Button login_btn ;
    private ProgressDialog progressDialog ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);
         email = viewGroup.findViewById(R.id.login_email);
         password = viewGroup.findViewById(R.id.login_password);
         login_btn = viewGroup.findViewById(R.id.login_btn);
         progressDialog = new ProgressDialog(getContext());
         progressDialog.setTitle("Logging in");
         progressDialog.setMessage("Checking credentials please wait ...");
         progressDialog.setCanceledOnTouchOutside(false);

         login_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view)
             {
                 if(TextUtils.isEmpty(email.getText()))
                 {
                     email.setError("Enter email");
                     return;
                 }else if(TextUtils.isEmpty(password.getText()))
                 {
                     password.setError("Password must be atleast 6 characters long");
                     return;
                 }else
                 {
                     progressDialog.show();
                     userLogin(email.getEditableText().toString(), password.getEditableText().toString());
                 }
             }
         });

         return viewGroup;
    }

    private void userLogin(final String email, String password)
    {
        LoginApiModel loginApiModel = new LoginApiModel(email, password, null);

        try {
            Call<LoginApiModel> apiModelCall = ApiClient.getInstance().getApi().userSignIn(loginApiModel);
            apiModelCall.enqueue(new Callback<LoginApiModel>() {
                @Override
                public void onResponse(Call<LoginApiModel> call, Response<LoginApiModel> response) {
                    if(response.code() == 200)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Logged in successfully ... ", Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Remember", "true") ;
                        editor.putString("token", response.body().getToken());
                        editor.apply();

                        startActivity(new Intent(getContext(), HomeActivity.class));
                        getActivity().finish();
                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginApiModel> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e)
        {
            progressDialog.dismiss();
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}