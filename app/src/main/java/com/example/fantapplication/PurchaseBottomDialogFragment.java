package com.example.fantapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.fantapplication.Retrofit.ApiClient;
import com.example.fantapplication.Retrofit.JsonPlaceHolderApi;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseBottomDialogFragment extends BottomSheetDialogFragment {
    private JsonPlaceHolderApi api = ApiClient.getClient().create(JsonPlaceHolderApi.class);
    private CurrentUser currentUser = CurrentUser.getInstance();
    private TextView mItemPrice, mItemTitle, mItemDescription;
    private Button mBuyButton;

    private String id;
    private String title;
    private String description;
    private String price;

    public PurchaseBottomDialogFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase, container, false);
        mItemTitle = view.findViewById(R.id.popUpItemName);
        mItemPrice = view.findViewById(R.id.popUpItemPrice);
        mItemDescription = view.findViewById(R.id.popUpItemDescription);
        mBuyButton = view.findViewById(R.id.buyButton);

        mItemTitle.setText(title);
        mItemDescription.setText(description);
        mItemPrice.setText(price);

        mBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentUser.isUserLoggedIn()){
                    purchaseItem(id);
                    Toast.makeText(getActivity(),"Email Sent to owner", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(),"Please login or register", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;

    }
    public void setParameters(String title, String description, String price, String id){
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
    }
    public boolean purchaseItem(String itemid){
        String userToken = CurrentUser.getInstance().getToken();
        Call<ResponseBody> call = api.purchaseItem(userToken,itemid);

        call.enqueue(new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody>call, Response<ResponseBody> response){
                if(!response.isSuccessful()){
                    System.out.println("code:"+response.code());
                    return;
                }
                if(response.isSuccessful()){
                    mBuyButton.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody>call,Throwable t){
            }
        });

        return true;
    }

}
