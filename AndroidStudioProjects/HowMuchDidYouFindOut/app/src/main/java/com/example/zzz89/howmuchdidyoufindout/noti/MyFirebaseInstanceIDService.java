package com.example.zzz89.howmuchdidyoufindout.noti;

import android.util.Log;

import com.example.zzz89.howmuchdidyoufindout.db.SaveSharedPreference;
import com.example.zzz89.howmuchdidyoufindout.server_api.ServerRetroInterface;
import com.example.zzz89.howmuchdidyoufindout.server_api.user;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zzz89 on 2017-11-30.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static String TAG = "";
    private String username;
    private static Retrofit retrofit;
    private ServerRetroInterface retroInterface;
    public MyFirebaseInstanceIDService(){
    }
    public MyFirebaseInstanceIDService(String username){
        this.username = username;
    }
    @Override
    public void onTokenRefresh() {
        username = SaveSharedPreference.getUserName(MyFirebaseInstanceIDService.this);
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("refresh token", refreshToken);
        SaveSharedPreference.setToken(MyFirebaseInstanceIDService.this, refreshToken);
        //sendRegistrationServer(refreshToken);
    }

    private void sendRegistrationServer(String refreshToken){
        retrofit = new Retrofit.Builder().baseUrl(ServerRetroInterface.API_URL).build();
        retroInterface = retrofit.create(ServerRetroInterface.class);
        Call<user> call = retroInterface.putUserLogin(username, new user(username, refreshToken));
        call.enqueue(new Callback<user>() {
            @Override
            public void onResponse(Call<user> call, Response<user> response) {
                if(response.isSuccessful()){
                    Log.d("fb token update", "success");
                }
            }
            @Override
            public void onFailure(Call<user> call, Throwable t) {

            }
        });
    }
}
