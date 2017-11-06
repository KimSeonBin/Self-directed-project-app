package com.example.zzz89.howmuchdidyoufindout;

import android.content.Context;
import android.os.AsyncTask;

import com.nhn.android.naverlogin.OAuthLogin;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zzz89 on 2017-10-28.
 */

public class NaverAsync extends AsyncTask<String, Void, String> {
    private OAuthLogin mOAuthLoginModule;
    private static Context mContext;
    public NaverAsync(OAuthLogin mOAuthLoginModule, Context context){
        this.mOAuthLoginModule = mOAuthLoginModule;
        this.mContext = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = "https://openapi.naver.com/v1/nid/me";

        String resultApi = mOAuthLoginModule.requestApi(mContext, strings[0], url);

        return parsing_Api(resultApi);
    }

    private String parsing_Api(String result_Api){
        if(result_Api != null){
            try {
                JSONObject jsonObject = new JSONObject(result_Api).getJSONObject("response");
                String e_mail = jsonObject.getString("email");
                String id = jsonObject.getString("id");
                return e_mail + " " + id;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
