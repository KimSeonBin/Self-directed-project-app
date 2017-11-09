package com.example.zzz89.howmuchdidyoufindout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;

/**
 * Created by zzz89 on 2017-11-09.
 */

public class SearchResultPriceTooHigh extends AppCompatActivity {
    private String item_name;
    private int item_price;
    private TextView textView;
    private ButtonRectangle buttonRectangle;
    private Toolbar toolbar;

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
        item_price = intent.getIntExtra("item_price", 0);
        textView = (TextView)findViewById(R.id.search_result_too_much_text);
        buttonRectangle = (ButtonRectangle)findViewById(R.id.search_result_too_much_button);
        toolbar = (Toolbar)findViewById(R.id.search_result_too_much_toolbar);

        setting_textview();
        setting_button();
        setting_toolbar();
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

            }
        });
    }

    private void setting_toolbar(){
        toolbar.setTitle("알람(메일) 등록");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
