package com.example.zzz89.howmuchdidyoufindout.server_api;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.zzz89.howmuchdidyoufindout.AppMainActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zzz89 on 2017-11-25.
 */

public class collection_rest_api {
    private ServerRetroInterface retroInterface;
    private Retrofit retrofit;
    private Application application;
    private Context context;
    private Intent intent;

    public void retrofit_setting() {
        retrofit = new Retrofit.Builder().baseUrl(ServerRetroInterface.API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retroInterface = retrofit.create(ServerRetroInterface.class);
    }

    public void retrofit_post_user(final user userr){
        Call<user> call = retroInterface.postUserLogin(userr);
        Log.d("start post user", "sss");

        call.enqueue(new Callback<user>() {
            @Override
            public void onResponse(Call<user> call, Response<user> response) {
                if(response.isSuccessful()){
                    Log.d("username create", String.valueOf(response.code()));
                    retrofit_post_online_mall_filter(new mallfilter(userr.getUsername(), true, true, true, true, true, true, true));
                }
            }
            @Override
            public void onFailure(Call<user> call, Throwable t) {
                Log.d("fail", "not response");
            }
        });
    }
    public void retrofit_post_user_setting(usersetting setting){
        Call<usersetting> call = retroInterface.postUserSetting(setting);
        Log.d("start post usersetting", "sss");
        call.enqueue(new Callback<usersetting>() {
            @Override
            public void onResponse(Call<usersetting> call, Response<usersetting> response) {
                if(response.isSuccessful()){
                    Log.d("setting create", String.valueOf(response.code()));
                    //최종 성공 후 intent start
                    start_intent();
                }
            }
            @Override
            public void onFailure(Call<usersetting> call, Throwable t) {
                Log.d("fail", "not response");
            }
        });
    }
    public void retrofit_post_online_mall_filter(final mallfilter filter){
        Log.d("start post filter", "sss");
        Call<mallfilter> call = retroInterface.postMallFilter(filter);
        call.enqueue(new Callback<mallfilter>() {
            @Override
            public void onResponse(Call<mallfilter> call, Response<mallfilter> response) {
                Log.d("filter create", String.valueOf(response.code()));
                retrofit_post_user_setting(new usersetting(filter.getUser_id(), true, true));
            }

            @Override
            public void onFailure(Call<mallfilter> call, Throwable t) {
                Log.d("fail", "not response");
            }
        });
    }
    public void retrofit_post_item(item items){
        Log.d("start post item", "start");
        Call<item> call = retroInterface.postItem(items);
        call.enqueue(new Callback<item>() {
            @Override
            public void onResponse(Call<item> call, Response<item> response) {
                Log.d("item status", "create");
            }
            @Override
            public void onFailure(Call<item> call, Throwable t) {
                Log.d("fail", "not response");
            }
        });
    }
    public void retrofit_put_online_mall_filter(mallfilter filter){
        Call<mallfilter> call = retroInterface.putMallFilter(filter.getUser_id(), filter);
        call.enqueue(new Callback<mallfilter>() {
            @Override
            public void onResponse(Call<mallfilter> call, Response<mallfilter> response) {
                Log.d("put mallfilter code", String.valueOf(response.code()));
                if(response.isSuccessful()){
                    Log.d("success auction", String.valueOf(response.body().isAuction()));
                }
            }
            @Override
            public void onFailure(Call<mallfilter> call, Throwable t) {
                Log.d("fail", "not response");
            }
        });
    }
    public void retrofit_put_usersetting(usersetting setting){
        Call<usersetting> call = retroInterface.putUserSetting(setting.getUser_id(), setting);
        call.enqueue(new Callback<usersetting>() {
            @Override
            public void onResponse(Call<usersetting> call, Response<usersetting> response) {
                Log.d("put setting code", String.valueOf(response.code()));
                if(response.isSuccessful()){
                    Log.d("successs", String.valueOf(response.body().isAgree_mail_transfer()));
                }
            }
            @Override
            public void onFailure(Call<usersetting> call, Throwable t) {
                Log.d("fail", "not response");
            }
        });
    }
    public void retrofit_put_item(item items){
        Call<item> call = retroInterface.putItem(items.getUser(), items.getItem_name(), items);
        call.enqueue(new Callback<item>() {
            @Override
            public void onResponse(Call<item> call, Response<item> response) {
                Log.d("item update status", "success");
            }
            @Override
            public void onFailure(Call<item> call, Throwable t) {
                Log.d("item update status", "fail");
            }
        });
    }
    public void retrofit_delete_item(item items){
        Call<item> call = retroInterface.deleteItem(items.getUser(), items.getItem_name());
        call.enqueue(new Callback<item>() {
            @Override
            public void onResponse(Call<item> call, Response<item> response) {
                Log.d("item delete status", String.valueOf(response.code()));
            }
            @Override
            public void onFailure(Call<item> call, Throwable t) {
                Log.d("item delete status", "fail");
            }
        });

    }
    public void start_intent_setting(Context context){
        this.context = context;
        this.intent = new Intent(context, AppMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
    public void start_intent(){
        context.startActivity(intent);
    }
}
