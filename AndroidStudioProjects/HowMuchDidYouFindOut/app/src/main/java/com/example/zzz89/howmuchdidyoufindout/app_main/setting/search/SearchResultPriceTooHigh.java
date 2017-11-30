package com.example.zzz89.howmuchdidyoufindout.app_main.setting.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.zzz89.howmuchdidyoufindout.R;
import com.example.zzz89.howmuchdidyoufindout.db.HowMuchSQLHelper;
import com.example.zzz89.howmuchdidyoufindout.db.SaveSharedPreference;
import com.example.zzz89.howmuchdidyoufindout.server_api.collection_rest_api;
import com.example.zzz89.howmuchdidyoufindout.server_api.item;
import com.gc.materialdesign.views.ButtonRectangle;

/**
 * Created by zzz89 on 2017-11-09.
 */

public class SearchResultPriceTooHigh extends AppCompatActivity {
    private String item_name;
    private int wanted_price;
    private String img_url;
    private TextView textView;
    private ButtonRectangle buttonRectangle;
    private Toolbar toolbar;
    private collection_rest_api rest_api;

    private String sentence1 = "찾으시는 ";
    private String sentence2 = " 을(를)\n";
    private String sentence3 = " 메일로";
    private String sentence4 = " 알려드리겠습니다.";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_too_much);
        Intent intent = getIntent();
        item_name = intent.getStringExtra("item_name");
        img_url = intent.getStringExtra("img_url");
        wanted_price = intent.getIntExtra("wanted_price", 0);

        textView = (TextView)findViewById(R.id.search_result_too_much_text);
        buttonRectangle = (ButtonRectangle)findViewById(R.id.search_result_too_much_button);
        toolbar = (Toolbar)findViewById(R.id.search_result_too_much_toolbar);

        setting_textview();
        setting_button();
        setting_toolbar();
        setting_collection_rest_api();
    }

    private void setting_textview(){
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        stringBuilder.append(item_name);
        stringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, item_name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(sentence1);
        textView.append(stringBuilder);
        textView.append(sentence2);

        stringBuilder.clear();
        stringBuilder.append(sentence3);
        stringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, sentence3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(stringBuilder);
        textView.append(sentence4);
    }

    private void setting_button(){
        buttonRectangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeinSQL();
                finish();
            }
        });
    }

    private void setting_toolbar(){
        toolbar.setTitle("알람(메일) 등록");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void setting_collection_rest_api() {
        rest_api = new collection_rest_api();
        rest_api.retrofit_setting(getApplicationContext());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void storeinSQL(){
        Log.d("item name", item_name);
        Log.d("wanted_price", String.valueOf(wanted_price));
        Log.d("url", img_url);

        HowMuchSQLHelper sqLiteDatabase = new HowMuchSQLHelper(getApplicationContext(), HowMuchSQLHelper.DB_name, null, HowMuchSQLHelper.versionNumber);
        String username = SaveSharedPreference.getUserName(SearchResultPriceTooHigh.this);
        rest_api.retrofit_post_item(new item(username, item_name, username, 1, wanted_price));
        sqLiteDatabase.addITEM_INFO(item_name, wanted_price, img_url);
    }
}
