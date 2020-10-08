package com.example.fantapplication.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.fantapplication.CurrentUser;
import com.example.fantapplication.R;
import com.example.fantapplication.Retrofit.ApiClient;
import com.example.fantapplication.Retrofit.JsonPlaceHolderApi;
import com.example.fantapplication.Retrofit.RetrofitService;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.xml.transform.Result;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {
    JsonPlaceHolderApi api = ApiClient.getClient().create(JsonPlaceHolderApi.class);
    TextView mRegisterText;
    EditText mFirstname, mLastname, mUsername, mPassword, mEmail;
    Button mRegisterButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mFirstname = view.findViewById(R.id.registerFirstname);
        mLastname = view.findViewById(R.id.registerLastname);
        mUsername = view.findViewById(R.id.registerUsername);
        mPassword = view.findViewById(R.id.registerPassword);
        mEmail = view.findViewById(R.id.registerEmail);

        mRegisterText = view.findViewById(R.id.registerText);
        mRegisterButton = view.findViewById(R.id.registerButton);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = mFirstname.getText().toString();
                String lastname = mLastname.getText().toString();
                String uname = mUsername.getText().toString();
                String pwd = mPassword.getText().toString();
                String email = mEmail.getText().toString();

                createUser(uname, pwd, firstname, lastname, email);

            }
        });
        return view;
    }

    public void createUser(String uname, String password, String fname, String lname, String email){
        Call call = api.createUser(uname, password, fname, lname, email);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call call, Response response) {
                if(!response.isSuccessful()){
                    System.out.println("code: "+ response.code());
                    mRegisterText.setText("Register failed");
                    return;
                }
                if(response.isSuccessful()){
                    try {
                        String json = new Gson().toJson(response.body());
                        JSONObject jsonObject = new JSONObject(json);
                        String username = jsonObject.getString("username");
                        String email = jsonObject.getString("email");
                        CurrentUser.getInstance().setEmail(email);
                        CurrentUser.getInstance().setUsername(username);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mRegisterText.setText("Register successfully, please login");
                    mFirstname.setVisibility(View.GONE);
                    mLastname.setVisibility(View.GONE);
                    mEmail.setVisibility(View.GONE);
                    mPassword.setVisibility(View.GONE);
                    mUsername.setVisibility(View.GONE);
                    mRegisterButton.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
            }
        });

    }
}

