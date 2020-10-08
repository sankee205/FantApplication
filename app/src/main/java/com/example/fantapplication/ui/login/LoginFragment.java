package com.example.fantapplication.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fantapplication.CurrentUser;
import com.example.fantapplication.FragmentListener;
import com.example.fantapplication.NavigationActivity;
import com.example.fantapplication.R;
import com.example.fantapplication.RegisterItemActivity;
import com.example.fantapplication.Retrofit.ApiClient;
import com.example.fantapplication.Retrofit.JsonPlaceHolderApi;
import com.example.fantapplication.Retrofit.RetrofitService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    private JsonPlaceHolderApi api = ApiClient.getClient().create(JsonPlaceHolderApi.class);
    private EditText mUsername, mPassword;
    private Button mLoginButton;
    private FragmentListener mFragmentListener;

    private TextView mLoginText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mUsername = view.findViewById(R.id.loginUsername);
        mPassword = view.findViewById(R.id.loginPassword);

        mLoginButton = view.findViewById(R.id.loginButton);
        mLoginText = view.findViewById(R.id.loginText);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = mUsername.getText().toString();
                String pwd = mPassword.getText().toString();
                loginUser(uname, pwd);
            }
        });
        return view;
    }
    public void loginUser(String uname, String password){
        Call<ResponseBody> call = api.loginUser(uname, password);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    mLoginText.setText("Login failed");
                    System.out.println("code: "+ response.code());
                }
                if(response.isSuccessful()){
                    try {
                        CurrentUser.getInstance().setUserLoggedIn(true);
                        String jwtoken = "Bearer "+response.body().string();
                        CurrentUser.getInstance().setToken(jwtoken);
                        mLoginText.setText("Login successful");
                        mUsername.setVisibility(View.GONE);
                        mPassword.setVisibility(View.GONE);
                        mLoginButton.setVisibility(View.GONE);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
}
