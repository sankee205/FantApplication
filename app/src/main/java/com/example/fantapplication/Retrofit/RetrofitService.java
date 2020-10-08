package com.example.fantapplication.Retrofit;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.fantapplication.CurrentUser;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Result;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;


public class RetrofitService{

    private static ArrayList<Item> items;
    public static RetrofitService instance = null;
    private JsonPlaceHolderApi jsonPlaceHolderApi = ApiClient.getClient().create(JsonPlaceHolderApi.class);
    private static String token;
    private static boolean addedItem;




    public static RetrofitService getInstance(){
        if(instance == null){
            instance = new RetrofitService();
        }
        return instance;
    }





    /*************************** AUTHENTICATION METHODS *********************************/
    /**
     * GET
     * sends a request to login a user by username and password
     * receives a token if the user exists
     * @param uname
     * @param password
     * @return
     */
    public boolean loginUser(String uname, String password){
        Call<ResponseBody> call = jsonPlaceHolderApi.loginUser(uname, password);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    return;
                }
                try {
                    CurrentUser.getInstance().setUserLoggedIn(true);
                    token = "Bearer "+response.body().string();
                    CurrentUser.getInstance().setToken(token);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
        return true;
    }

    /**
     * POST
     * creates a user by username, password, first and last name and an email.
     * receives back the user as an json object if the user is created
     * @param uname
     * @param password
     * @param fname
     * @param lname
     * @param email
     * @return
     */
    public boolean createUser(String uname, String password, String fname, String lname, String email){
        boolean state = false;
        Call call = jsonPlaceHolderApi.createUser(uname, password, fname, lname, email);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call call, Response response) {
                if(!response.isSuccessful()){
                    System.out.println("code: "+ response.code());
                    return;
                }
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
            }

            @Override
            public void onFailure(Call call, Throwable t) {
            }
        });
        if (!call.isCanceled()){
            state = true;
        }
        return state;
    }

    /**
     *GET
     *sendsrequestwithtokenforauthorizationand
     *receivesthecurrentuserasajsonobject
     *@return
     */
    public boolean currentUser(){
        String userToken = CurrentUser.getInstance().getToken();
        Call<Object>call=jsonPlaceHolderApi.currentUser(userToken);

        call.enqueue(new Callback<Object>(){
            @Override
                    public void onResponse(Call<Object>call,Response<Object>response){
                if(!response.isSuccessful()){
                    System.out.println("code:"+response.code());
                    return;
                }
                String json=new Gson().toJson(response.body());
                try{
                    JSONObject jsonObject=new JSONObject(json);
                }catch(JSONException e){
                    e.printStackTrace();
                }


            }

            @Override
                    public void onFailure(Call<Object>call,Throwable t){
            }
        });

        return true;
    }

    /**
     *
     *@paramusername
     *@paramrole
     *@return
     */
    public boolean addRole(String username,String role){
        String userToken = CurrentUser.getInstance().getToken();
        Call<ResponseBody>call=jsonPlaceHolderApi.addRole(userToken,username,role);

        call.enqueue(new Callback<ResponseBody>(){
            @Override
                    public void onResponse(Call<ResponseBody>call,Response<ResponseBody>response){
                if(!response.isSuccessful()){
                    System.out.println("code:"+response.code());
                    return;
                }
                String json=new Gson().toJson(response.body());
                try{
                    JSONObject jsonObject=new JSONObject(json);
                }catch(JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
                    public void onFailure(Call<ResponseBody>call,Throwable t){
            }
        });

        return true;
    }

    /**
     *
     *@paramusername
     *@paramrole
     *@return
     */
    public boolean removeRole(String username,String role){
        String userToken = CurrentUser.getInstance().getToken();
        Call<ResponseBody>call=jsonPlaceHolderApi.removeRole(userToken,username,role);

        call.enqueue(new Callback<ResponseBody>(){
            @Override
                    public void onResponse(Call<ResponseBody>call,Response<ResponseBody>response){
                if(!response.isSuccessful()){
                    System.out.println("code:"+response.code());
                    return;
                }
                String json=new Gson().toJson(response.body());
                try{
                    JSONObject jsonObject=new JSONObject(json);
                }catch(JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
                    public void onFailure(Call<ResponseBody>call,Throwable t){
            }
        });

        return true;
    }


    /**
     *
     *@paramusername
     *@parampassword
     *@return
     */
    public boolean changePassword(String username,String password){
        String userToken = CurrentUser.getInstance().getToken();
        Call<ResponseBody>call=jsonPlaceHolderApi.changePassword(userToken,username,password);

        call.enqueue(new Callback<ResponseBody>(){
            @Override
                    public void onResponse(Call<ResponseBody>call,Response<ResponseBody>response){
                if(!response.isSuccessful()){
                    System.out.println("code:"+response.code());
                    return;
                }
                String json=new Gson().toJson(response.body());
                try{
                    JSONObject jsonObject=new JSONObject(json);
                }catch(JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
                    public void onFailure(Call<ResponseBody>call,Throwable t){
            }
        });

        return true;
    }

/***************************FANTSERVICEMETHODS*********************************/

    /**
     *
     *@paramtitle
     *@paramdescription
     *@paramprice
     *@return
     */
    public boolean addItem(String title,String description,String price, String imagePath){
        Call<ResponseBody> call;
        if(imagePath == null){
            Map<String, RequestBody> itemsData = new HashMap<>();

            itemsData.put("title", createPartFromString(title));
            itemsData.put("desc", createPartFromString(description));
            itemsData.put("price", createPartFromString(price));
            call = jsonPlaceHolderApi.addItem(token,itemsData);
        }
        else{
            File file = new File(imagePath);
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);

            Map<String, RequestBody> itemsData = new HashMap<>();

            itemsData.put("title", createPartFromString(title));
            itemsData.put("desc", createPartFromString(description));
            itemsData.put("price", createPartFromString(price));
            call = jsonPlaceHolderApi.addItems(token,itemsData, body);
        }



        call.enqueue(new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call,Response<ResponseBody> response){
                if(!response.isSuccessful()){
                    System.out.println("code:"+response.code());
                    return;
                }
                String somResponse = response.body().toString();
                addedItem = true;

            }

            @Override
            public void onFailure(Call<ResponseBody> call,Throwable t){
            }
        });
        return true;
    }

    /**
     * returns a list of all items
     * @return
     */
    public ArrayList<Item> listItems(){
        Call<ArrayList<Item>> call=jsonPlaceHolderApi.listItems();

        call.enqueue(new Callback<ArrayList<Item>>(){
            @Override
                    public void onResponse(Call<ArrayList<Item>> call,Response<ArrayList<Item>> response){
                if(!response.isSuccessful()){
                    System.out.println("code:"+response.code());
                    return;
                }
                items = response.body();

            }

            @Override
                    public void onFailure(Call<ArrayList<Item>> call,Throwable t){
            }
        });

        return items;
    }

    /**
     *
     *@paramitemid
     *@return
     */
    public boolean purchaseItem(String itemid){
        String userToken = CurrentUser.getInstance().getToken();
        Call<ResponseBody>call=jsonPlaceHolderApi.purchaseItem(userToken,itemid);

        call.enqueue(new Callback<ResponseBody>(){
            @Override
                    public void onResponse(Call<ResponseBody>call,Response<ResponseBody>response){
                if(!response.isSuccessful()){
                    System.out.println("code:"+response.code());
                    return;
                }
                String json = new Gson().toJson(response.body());
                try{
                    JSONObject jsonObject=new JSONObject(json);
                }catch(JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
                    public void onFailure(Call<ResponseBody>call,Throwable t){
            }
        });

        return true;
    }

    /**
     *
     *@paramitemid
     *@return
     */
    public boolean deleteItem(String itemid){
        String userToken = CurrentUser.getInstance().getToken();
        Call<ResponseBody>call=jsonPlaceHolderApi.deleteItem(userToken,itemid);

        call.enqueue(new Callback<ResponseBody>(){
            @Override
                    public void onResponse(Call<ResponseBody>call,Response<ResponseBody>response){
                if(!response.isSuccessful()){
                    System.out.println("code:"+response.code());
                    return;
                }
                String json=new Gson().toJson(response.body());
                try{
                    JSONObject jsonObject=new JSONObject(json);
                }catch(JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
                    public void onFailure(Call<ResponseBody>call,Throwable t){
            }
        });

        return true;
    }




    /**
     *
     *@paramname
     *@return
     */
    public boolean getPhoto(String name){
        Call<Photo>call=jsonPlaceHolderApi.getPhoto(name);

        call.enqueue(new Callback<Photo>(){
            @Override
                    public void onResponse(Call<Photo>call,Response<Photo>response){
                if(!response.isSuccessful()){
                    System.out.println("code:"+response.code());
                    return;
                }
                String json=new Gson().toJson(response.body());
                try{
                    JSONObject jsonObject=new JSONObject(json);
                }catch(JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
                    public void onFailure(Call<Photo>call,Throwable t){
            }
        });

        return true;
    }

    private RequestBody createPartFromString (String partString) {
        return RequestBody.create(MultipartBody.FORM, partString);
    }


}

