package com.example.theclothingapplication;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

public class SignUpFragment extends Fragment {

    private TextInputEditText firstName, lastName, email, phone, password, confirmPassword;
    private Button sign_up_btn;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_sign_up, container, false);

        firstName = viewGroup.findViewById(R.id.signup_first_name);
        lastName = viewGroup.findViewById(R.id.signup_last_name);
        email = viewGroup.findViewById(R.id.signup_email);
        phone = viewGroup.findViewById(R.id.signup_phone);
        password = viewGroup.findViewById(R.id.signup_password);
        confirmPassword = viewGroup.findViewById(R.id.signup_confirm_password);
        sign_up_btn = viewGroup.findViewById(R.id.signup_btn);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Signing up");
        progressDialog.setMessage("Checking credentials please wait ...");
        progressDialog.setCanceledOnTouchOutside(false);

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(firstName.getText())) {
                    firstName.setError("First Name is required");
                } else if (TextUtils.isEmpty(email.getText())) {
                    email.setError("Email is required");
                } else if (TextUtils.isEmpty(phone.getText())) {
                    phone.setError("Phone no. is required");
                } else if (TextUtils.isEmpty(password.getText()) || password.getEditableText().length()<6) {
                    password.setError("Password is required & must be atleast 6 digits long");
                } else if (TextUtils.isEmpty(confirmPassword.getText())) {
                    confirmPassword.setError("Re-enter the password");
                } else if (password.getEditableText().toString().compareTo(confirmPassword.getEditableText().toString()) != 0) {
                    confirmPassword.setError("Password doesn't match");
                } else {
                    progressDialog.show();
                    userSignUp(firstName.getEditableText().toString(), lastName.getEditableText().toString(), email.getEditableText().toString(), phone.getEditableText().toString(), password.getEditableText().toString());
                }
            }
        });

        return viewGroup;
    }

    private void userSignUp(String fName, String lName, String email, String phone, String pass) {
        LoginApiModel loginApiModel = new LoginApiModel(fName, lName, email, phone, pass);

        try {
            Call<LoginApiModel> apiModelCall = ApiClient.getInstance().getApi().userSignUp(loginApiModel);
            apiModelCall.enqueue(new Callback<LoginApiModel>() {
                @Override
                public void onResponse(Call<LoginApiModel> call, Response<LoginApiModel> response) {
                    if (response.code() == 201) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                        LoginFragment loginFragment = new LoginFragment();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.log_sign_layout, loginFragment);
                        transaction.commit();
                    } else {
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
        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }
}