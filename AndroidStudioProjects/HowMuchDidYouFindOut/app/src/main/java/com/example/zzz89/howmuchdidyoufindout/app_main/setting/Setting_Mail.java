package com.example.zzz89.howmuchdidyoufindout.app_main.setting;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.zzz89.howmuchdidyoufindout.R;
import com.example.zzz89.howmuchdidyoufindout.db.SaveSharedPreference;
import com.example.zzz89.howmuchdidyoufindout.server_api.collection_rest_api;
import com.example.zzz89.howmuchdidyoufindout.server_api.usersetting;

/**
 * Created by zzz89 on 2017-11-03.
 */

public class Setting_Mail extends AppCompatActivity {
    public static Activity activity;
    private ListView listView;
    private Toolbar toolbar;
    private SettingMailAdapter adapter;
    private String sentence_setting_mail[] = {"예약 목록에서 아이템을 제거하기 전까지 메일을 받습니다", "알람을 최초 한번만 전송합니다"};
    private boolean setting_mail_check[];
    private collection_rest_api rest_api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_filter_layout);
        activity = Setting_Mail.this;

        adapter = new SettingMailAdapter();
        listView = (ListView)findViewById(R.id.setting_filter_listview);
        listView.setAdapter(adapter);

        toolbar = (Toolbar)findViewById(R.id.setting_filter_toolbar);
        toolbar.setTitle("자동 메일 설정");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        call_mail_check_value();
        for(int i = 0; i < sentence_setting_mail.length; i++){
            adapter.addItem(sentence_setting_mail[i], setting_mail_check[i]);
        }
        rest_api = new collection_rest_api();
        rest_api.retrofit_setting(this);
    }

    private void call_mail_check_value(){
        setting_mail_check = new boolean[sentence_setting_mail.length];
        if(SaveSharedPreference.getMailSettingCheck(Setting_Mail.this).length == 0){
            SaveSharedPreference.setMailSettingCheck(Setting_Mail.this, setting_mail_check);
        }
        else {
            boolean temp[] = SaveSharedPreference.getMailSettingCheck(Setting_Mail.this);
            for (int i = 0; i < temp.length; i++) {
                setting_mail_check[i] = temp[i];
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
            boolean temp[] = adapter.getSwitchvalue();
            rest_api.retrofit_put_usersetting(new usersetting(SaveSharedPreference.getUserName(Setting_Mail.this), temp));
            SaveSharedPreference.setMailSettingCheck(Setting_Mail.this, temp);
            //finish();
        }
        else{
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
