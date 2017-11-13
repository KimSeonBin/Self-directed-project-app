package com.example.zzz89.howmuchdidyoufindout.app_main.setting.search;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zzz89 on 2017-11-07.
 */

public interface DanawaRetroInterface {
    public static final String API_URL = "http://api.danawa.com/";

    @GET("api/search/product/info?charset=utf8&mediatype=json&shortageYN=N")
    Call<ResponseBody>getSearchList(@Query("key") String key, @Query("keyword") String keyword, @Query("start_no") int start_no, @Query("limit_no") int limit_no);

}
