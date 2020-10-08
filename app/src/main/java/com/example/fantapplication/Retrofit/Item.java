package com.example.fantapplication.Retrofit;

import java.util.List;


public class Item {
    String id;
    String title;
    String description;
    int price;

    List<Photo> itemImage;

    User itemOwner;
    User itemBuyer;

    public String getId(){ return id; }

    public String getTitle() {
        return title;
    }


    public String getDesciption() {
        return description;
    }


    public int getPrice() {
        return price;
    }

}
