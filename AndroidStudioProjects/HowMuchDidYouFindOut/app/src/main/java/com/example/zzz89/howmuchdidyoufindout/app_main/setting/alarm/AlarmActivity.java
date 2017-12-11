package com.example.zzz89.howmuchdidyoufindout.app_main.setting.alarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.zzz89.howmuchdidyoufindout.R;
import com.example.zzz89.howmuchdidyoufindout.db.HowMuchSQLHelper;

import java.util.ArrayList;

/**
 * Created by zzz89 on 2017-12-01.
 */

public class AlarmActivity extends AppCompatActivity {
    private String keyword;
    private int search_count;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Alarm_Click_Item> arrayList;
    private AlarmClickAdapter alarmClickAdapter;

    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_click);
        loadIntentValue();
        assign_value();
        loadAlarmClickItem();
        setRecyclerView();
    }


    private void loadAlarmClickItem(){
        HowMuchSQLHelper sqlHelper = new HowMuchSQLHelper(getApplicationContext(), HowMuchSQLHelper.DB_name, null, HowMuchSQLHelper.versionNumber);
        Alarm_Click_Item[] items = sqlHelper.select_Alarm_Click_items(keyword, search_count);
        if(items != null){
            search_count += items.length;
            //adapter 추가하는 부분 해야됨
            for(int i = 0; i < items.length; i++){
                arrayList.add(items[i]);
            }
            alarmClickAdapter.notifyDataSetChanged();
        }
    }

    private void assign_value() {
        recyclerView = (RecyclerView)findViewById(R.id.alarm_click_recyclerview);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<Alarm_Click_Item>();
        alarmClickAdapter = new AlarmClickAdapter(getApplicationContext(), arrayList);
        recyclerView.setAdapter(alarmClickAdapter);

        toolbar = (Toolbar)findViewById(R.id.alarm_click_toolbar);
        setToolbar();

        search_count = 0;
    }
    private void setRecyclerView(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0){
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if((visibleItemCount + pastVisiblesItems) >= totalItemCount){
                        //load more data
                        loadAlarmClickItem();
                    }
                }
            }
        });
    }
    private void setToolbar(){
        toolbar.setTitle(keyword + " 최저가 검색 결과");
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

    private void loadIntentValue() {
        Intent intent = getIntent();
        keyword = intent.getStringExtra("keyword");
    }

}
