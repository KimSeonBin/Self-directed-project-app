package com.example.zzz89.howmuchdidyoufindout;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zzz89 on 2017-11-07.
 */

public class DanawaAsync extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("http://api.danawa.com/api/search/product/info?key=");
        buffer.append("2b850ad7121cabc2be1777b11218b68b&keyword=");
        buffer.append(strings[0]);
        buffer.append("&shortageYN=N&mediatype=json&charset=utf8");

        try {
            URL url = new URL(buffer.toString());
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            InputStream inputStream = new BufferedInputStream(connection.getInputStream());

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private String parsing_danawa_json(String api_sentence){
        try {
            JSONObject jsonObject = new JSONObject(api_sentence).getJSONObject("response");
            int count = jsonObject.getInt("totalCount");
            String urltag = "image_url";
            String nametag = "prod_name";

            for(int i = 0; i < count; i++){

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
