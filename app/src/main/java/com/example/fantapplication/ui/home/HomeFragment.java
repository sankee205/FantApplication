package com.example.fantapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fantapplication.R;
import com.example.fantapplication.Retrofit.ApiClient;
import com.example.fantapplication.Retrofit.Item;
import com.example.fantapplication.Retrofit.JsonPlaceHolderApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment{

    private JsonPlaceHolderApi api = ApiClient.getClient().create(JsonPlaceHolderApi.class);
    private ArrayList<Item> items = new ArrayList<>();


    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private View listView;
    private ItemAdapter.OnItemListener onItemListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = listView.findViewById(R.id.itemsRecyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        setItemsList();
        adapter = new ItemAdapter(items, onItemListener);
        recyclerView.setAdapter(adapter);
        return listView;
    }

    public void setItemsList()
    {
        Call<ArrayList<Item>> call = api.listItems();

        call.enqueue(new Callback<ArrayList<Item>>(){
            @Override
            public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response){
                if(!response.isSuccessful()){
                    System.out.println("code:"+response.code());
                    return;
                }
                items = response.body();
                adapter = new ItemAdapter(items, onItemListener);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ArrayList<Item>> call,Throwable t){
            }
        });
    }



}