package com.example.fantapplication.Retrofit;

import com.google.gson.annotations.SerializedName;

public class User {
    String uname;
    String password;
    String fname;
    String lname;
    String email;
    String token;

    @SerializedName("body")
    private String text;

    public String getUname() {
        return uname;
    }

    public String getPassword() {
        return password;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
