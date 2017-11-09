package com.example.zzz89.howmuchdidyoufindout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zzz89 on 2017-11-07.
 */

public class SearchResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String item_name;
    private String price_info;

    private Retrofit retrofit;
    private DanawaRetroInterface danawaRetroInterface;

    private int search_count;
    private int size = 1;

    private boolean loading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    private ArrayList<SearchResultCardItem> arrayList;
    private SearchResultCardAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        Intent intent = getIntent();
        item_name = intent.getStringExtra("item");
        price_info = intent.getStringExtra("price");
        search_count = 0;

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView = (RecyclerView)findViewById(R.id.search_result_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<SearchResultCardItem>();
        adapter = new SearchResultCardAdapter(getApplicationContext(), arrayList, R.layout.search_result, Integer.parseInt(price_info));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0){//if scroll
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if(loading){
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            Log.d("asdasd", String.valueOf(search_count));
                            //Do pagination.. i.e. fetch new data
                            retrofit_danawa();
                        }
                    }
                }
            }
        });

        retrofit_danawa();
    }

    private void retrofit_danawa(){
        retrofit = new Retrofit.Builder().baseUrl(DanawaRetroInterface.API_URL).build();
        danawaRetroInterface = retrofit.create(DanawaRetroInterface.class);
        int limit_no = 20;
        Call<ResponseBody> searchList = danawaRetroInterface.getSearchList("2b850ad7121cabc2be1777b11218b68b", item_name, search_count*limit_no+1, limit_no);
        searchList.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    parsing_Danawa(response.body().string());
                    search_count++;
                    adapter.notifyDataSetChanged();
                    loading = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    // 없다는 표시를 추가.
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    private String[] parsing_Danawa(String body){

        try {
            JSONObject jsonObject = new JSONObject(body);
            JSONArray jsonArray = jsonObject.getJSONArray("productList");

            for(int i = 0; i < jsonArray.length(); i++){
                Log.d("prod_name", jsonArray.getJSONObject(i).getString("prod_name"));
                arrayList.add(new SearchResultCardItem(jsonArray.getJSONObject(i).getString("image_url").replace("?shrink=80:80", "")
                        , jsonArray.getJSONObject(i).getString("prod_name"), jsonArray.getJSONObject(i).getString("min_price")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
