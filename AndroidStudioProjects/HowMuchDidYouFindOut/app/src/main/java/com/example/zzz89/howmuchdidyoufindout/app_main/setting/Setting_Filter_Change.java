package com.example.zzz89.howmuchdidyoufindout.app_main.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.zzz89.howmuchdidyoufindout.R;
import com.example.zzz89.howmuchdidyoufindout.db.SaveSharedPreference;

/**
 * Created by zzz89 on 2017-11-02.
 */

public class Setting_Filter_Change extends AppCompatActivity {
    private ListView listView;
    private Toolbar toolbar;
    private SettingFilterAdapter adapter;
    private String onlineMall[] = {"옥션", "11번가", "G마켓", "쿠팡", "SSG.COM", "인터파크", "Cj몰"};
    private boolean onlineMall_check[];

    public Setting_Filter_Change(){

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_filter_layout);
        listView = (ListView)findViewById(R.id.setting_filter_listview);
        toolbar = (Toolbar)findViewById(R.id.setting_filter_toolbar);
        toolbar.setTitle("쇼핑몰 필터 변경");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        call_filter_value();

        adapter = new SettingFilterAdapter();
        listView.setAdapter(adapter);

        for(int i = 0; i < onlineMall.length; i++){
            adapter.addItem(onlineMall[i], onlineMall_check[i]);
        }
    }

    private void call_filter_value(){
        onlineMall_check = new boolean[onlineMall.length];

        if(SaveSharedPreference.getOnlineFilter(Setting_Filter_Change.this).length == 0){
            SaveSharedPreference.setOnlineFilter(Setting_Filter_Change.this, onlineMall_check);
        }
        else {
            boolean temp[] = SaveSharedPreference.getOnlineFilter(Setting_Filter_Change.this);
            for (int i = 0; i < temp.length; i++) {
                onlineMall_check[i] = temp[i];
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_filter_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        adapter.notifyDataSetChanged();
        if(id == R.id.item_icon){
            SaveSharedPreference.setOnlineFilter(Setting_Filter_Change.this, adapter.getSwitchValue());
            finish();
        }
        else{
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
