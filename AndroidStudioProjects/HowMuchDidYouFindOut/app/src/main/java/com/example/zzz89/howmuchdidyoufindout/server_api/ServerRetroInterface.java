package com.example.zzz89.howmuchdidyoufindout.server_api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by zzz89 on 2017-11-18.
 */

public interface ServerRetroInterface {
    public static final String API_URL = "https://mysterious-taiga-72148.herokuapp.com/";

    @GET("user/?format=json")
    Call<ResponseBody> getUserLogin(@Query("username") String username);
    @Headers("Content-Type: application/json")
    @POST("user/?format=json")
    Call<user> postUserLogin(@Body user userr);
    @Headers("Content-Type: application/json")
    @PUT("user/?format=json")
    Call<user> putUserLogin(@Query("user_id") String user_id, @Body user userr);

    @GET("mallfilter/?format=json")
    Call<ResponseBody> getMallFilter(@Query("user_id") String user_id);
    @Headers("Content-Type: application/json")
    @POST("mallfilter/?format=json")
    Call<mallfilter> postMallFilter(@Body mallfilter filter);
    @Headers("Content-Type: application/json")
    @PUT("mallfilter/?format=json")
    Call<mallfilter> putMallFilter(@Query("user_id") String user_id, @Body mallfilter filter);

    @GET("usersetting/?format=json")
    Call<ResponseBody> getUserSetting(@Query("user_id") String user_id);
    @Headers("Content-Type: application/json")
    @POST("usersetting/?format=json")
    Call<usersetting> postUserSetting(@Body usersetting setting);
    @Headers("Content-Type: application/json")
    @PUT("usersetting/?format=json")
    Call<usersetting> putUserSetting(@Query("user_id") String user_id, @Body usersetting setting);

    @GET("item/?format=json")
    Call<ResponseBody> getItem(@Query("user_id") String user_id, @Query("item_name") String item_name);
    @Headers("Content-Type: application/json")
    @POST("item/?format=json")
    Call<item> postItem(@Body item itemss);
    @Headers("Content-Type: application/json")
    @PUT("item/?format=json")
    Call<item> putItem(@Query("user_id") String user_id, @Query("item_name") String item_name, @Body item items);
    @DELETE("item/?format=json")
    Call<item> deleteItem(@Query("user_id") String user_id, @Query("item_name") String item_name);

    @GET("searchitem/?format=json")
    Call<ResponseBody> getSearchItem(@Query("user_id") String user_id, @Query("item_name") String item_name);
}
