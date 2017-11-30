package com.example.zzz89.howmuchdidyoufindout;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.zzz89.howmuchdidyoufindout.db.SaveSharedPreference;
import com.example.zzz89.howmuchdidyoufindout.login.LoginActivity;
import com.example.zzz89.howmuchdidyoufindout.server_api.ServerRetroInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private ServerRetroInterface retroInterface;
    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        final Intent intent;
        final String username = SaveSharedPreference.getUserName(MainActivity.this);

        retrofit = new Retrofit.Builder().baseUrl(ServerRetroInterface.API_URL).build();
        retroInterface = retrofit.create(ServerRetroInterface.class);
        Call<ResponseBody> usernamesearch = retroInterface.getUserLogin(username);
        usernamesearch.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    if (!username.equals(parseUsername(response.body()))) {
                        //No Login
                        Log.d("name", username);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        //already Login
                        Log.d("name2", username);
                        Intent intent = new Intent(MainActivity.this, AppMainActivity.class);
                        startActivity(intent);
                    }
                }
                else{
                    first_fail();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("select user","fail");
                first_fail();
            }
        });
    }

    private String parseUsername(ResponseBody resposebody){
        try {
            JSONArray array = new JSONArray(resposebody.string());
            JSONObject object = array.getJSONObject(0);
            Log.d("username", String.valueOf(object.getString("user")));
            return object.getString("user");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void first_fail(){
        Log.d("dialog show", "show");
        new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.warning)).setMessage(getResources()
                .getString(R.string.not_online)).setNegativeButton(getResources().getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        }).show();
    }
}
