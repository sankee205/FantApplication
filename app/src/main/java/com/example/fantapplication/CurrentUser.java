package com.example.fantapplication;

public class CurrentUser {
    private static CurrentUser instance = null;

    private static String username;
    private static String email;
    private static String token;
    private static boolean userLogedIn;

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();

        }

        return instance;
    }


    public boolean isUserLoggedIn(){
        return userLogedIn;
    }

    public void setUserLoggedIn(boolean state){
        userLogedIn = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
