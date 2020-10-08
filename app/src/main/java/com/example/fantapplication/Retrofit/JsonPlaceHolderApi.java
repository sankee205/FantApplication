package com.example.fantapplication.Retrofit;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @FormUrlEncoded
    @POST("auth/create")
    Call<Object> createUser(@Field("uname") String username,
                            @Field("password") String password,
                            @Field("fname") String fname,
                            @Field("lname") String lname,
                            @Field("email") String email);

    @Headers("Content-Type: application/json")
    @GET("auth/login")
    Call<ResponseBody> loginUser(@Query("username") String username,
                                @Query("password") String password);


    @Headers("Content-Type:application/json")
    @GET("auth/currentuser")
    Call<Object>currentUser(@Header("Authorization")String token);


    @Headers("Content-Type:application/json")
    @PUT("auth/addrole")
    Call<ResponseBody>addRole(@Header("Authorization")String token,
                              @Query("uid")String username,
                              @Query("role")String role);

    @Headers("Content-Type:application/json")
    @PUT("auth/removerole")
    Call<ResponseBody>removeRole(@Header("Authorization")String token,
                                 @Query("uid")String username,
                                 @Query("role")String role);

    @Headers("Content-Type:application/json")
    @PUT("auth/changepassword")
    Call<ResponseBody>changePassword(@Header("Authorization")String token,
                                     @Query("uid")String username,
                                     @Query("pwd")String newPassword);



    @Headers("Content-Type:application/json")
    @GET("fant/items")
    Call<ArrayList<Item>>listItems();



    @Headers("Content-Type:application/json")
    @PUT("fant/purchase")
    Call<ResponseBody>purchaseItem(@Header("Authorization")String token,
                                   @Query("itemid")String itemid);

    @Headers("Content-Type:application/json")
    @DELETE("fant/delete")
    Call<ResponseBody>deleteItem(@Header("Authorization")String token,
                                 @Query("itemid")String itemid);



    @Multipart
    @POST("fant/add-item")
    Call<ResponseBody> addItems(@Header("Authorization")String token,
                               @PartMap Map<String, RequestBody> data,
                               @Part MultipartBody.Part image
    );
    @Multipart
    @POST("fant/add-item")
    Call<ResponseBody> addItem(@Header("Authorization")String token,
                                @PartMap Map<String, RequestBody> data
    );


    @Multipart
    @POST("fant/image{name}")
    Call<Photo>getPhoto(@Query("name")String name);

}
