package com.example.fantapplication;

import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fantapplication.Retrofit.ApiClient;
import com.example.fantapplication.Retrofit.JsonPlaceHolderApi;
import com.example.fantapplication.Retrofit.RetrofitService;
import com.example.fantapplication.ui.home.ItemAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationActivity extends AppCompatActivity  {
    JsonPlaceHolderApi api = ApiClient.getClient().create(JsonPlaceHolderApi.class);
    TextView mEmail, mUsername;
    View hview;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        hview = navigationView.getHeaderView(0);
        mUsername = (TextView) hview.findViewById(R.id.navUsername);
        mEmail = (TextView) hview.findViewById(R.id.navEmail);
        setHeader();



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterItemActivity.class));
                finish();
            }
        });


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_login, R.id.nav_register)
                .setDrawerLayout(drawer)
                .build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        NavigationUI.setupWithNavController(navigationView, navController);

    }
    private void setHeader(){
        if(CurrentUser.getInstance().isUserLoggedIn()){
            currentUser();
        }
        else {
            mUsername.setText("Username");
            mEmail.setText("Email");
        }
    }

    private void currentUser(){
        String userToken = CurrentUser.getInstance().getToken();
        Call<Object> call= api.currentUser(userToken);

        call.enqueue(new Callback<Object>(){
            @Override
            public void onResponse(Call<Object>call, Response<Object> response){
                if(!response.isSuccessful()){
                    System.out.println("code:"+response.code());
                    return;
                }
                String json=new Gson().toJson(response.body());
                try{
                    JSONObject jsonObject=new JSONObject(json);
                    String uname = jsonObject.getString("username");
                    String email = jsonObject.getString("email");
                    mUsername.setText(uname);
                    mEmail.setText(email);
                }catch(JSONException e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<Object>call,Throwable t){
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



}