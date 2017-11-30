package com.example.zzz89.howmuchdidyoufindout.app_main.setting.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.zzz89.howmuchdidyoufindout.R;
import com.gc.materialdesign.widgets.Dialog;

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
    private Toolbar toolbar;
    private TextView textView;
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

        textView = (TextView)findViewById(R.id.search_result_textview);
        toolbar = (Toolbar)findViewById(R.id.search_result_toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.search_result_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<SearchResultCardItem>();
        adapter = new SearchResultCardAdapter(getApplicationContext(), arrayList, R.layout.search_result, Integer.parseInt(price_info));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {//if scroll
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
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
        search_toolbar();
        retrofit_danawa();
    }

    private void retrofit_danawa() {
        retrofit = new Retrofit.Builder().baseUrl(DanawaRetroInterface.API_URL).build();
        danawaRetroInterface = retrofit.create(DanawaRetroInterface.class);
        int limit_no = 20;
        Call<ResponseBody> searchList = danawaRetroInterface.getSearchList("2b850ad7121cabc2be1777b11218b68b", item_name, search_count * limit_no + 1, limit_no);
        searchList.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("response code", String.valueOf(response.code()));
                    if (response.isSuccessful()) {
                        parsing_Danawa(response.body().string());
                        search_count++;
                        adapter.notifyDataSetChanged();
                        loading = true;
                    } else {
                        Log.d("fail", "fail");
                        search_fail_textview_text();
                    }
                } catch (IOException e) {
                    //e.printStackTrace();
                    // 없다는 표시를 추가.
                    Log.d("fail", "fail text");
                    //search_fail_textview_text();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("fail", "fail");
                search_fail_textview_text();
            }
        });
    }

    private String[] parsing_Danawa(String body) {

        try {
            JSONObject jsonObject = new JSONObject(body);
            JSONArray jsonArray = jsonObject.getJSONArray("productList");

            for (int i = 0; i < jsonArray.length(); i++) {
                Log.d("prod_name", jsonArray.getJSONObject(i).getString("prod_name"));
                arrayList.add(new SearchResultCardItem(jsonArray.getJSONObject(i).getString("image_url").replace("?shrink=80:80", "")
                        , jsonArray.getJSONObject(i).getString("prod_name"), jsonArray.getJSONObject(i).getString("min_price")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void search_fail_textview_text(){
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        textView.setText(getResources().getString(R.string.not_found1) + " ");
        stringBuilder.append(item_name);
        stringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, item_name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(stringBuilder);
        textView.append(getResources().getString(R.string.not_found2) + "\n");
        textView.append(getResources().getString(R.string.not_found3) + " ");
        stringBuilder.clear();
        stringBuilder.append(getResources().getString(R.string.not_found4));
        stringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 0, getResources().getString(R.string.not_found4).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(stringBuilder);
    }
    private void search_toolbar(){
        toolbar.setTitle(item_name + " 검색결과");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.item_icon){
            finish();
        }
        else{
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}