package com.example.zzz89.howmuchdidyoufindout.app_main.setting.saved;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.zzz89.howmuchdidyoufindout.R;
import com.example.zzz89.howmuchdidyoufindout.db.HowMuchSQLHelper;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.Dialog;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by zzz89 on 2017-11-11.
 */

public class SavedlistModify extends AppCompatActivity {
    private String item_name;
    private static String item_price;
    private String img_url;
    private static int item_index = 0;
    private TextView textView;
    private MaterialEditText editText;
    private ButtonRectangle buttonmodify;
    private ButtonRectangle buttondelete;
    private Toolbar toolbar;
    private static int resultCode = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_list_modify);

        getIntentString(getIntent());
        bindComponent();

        set_textandedit();
        set_button_clicklistener();
        set_toolbar();
    }

    private void getIntentString(Intent intent){
        item_name = intent.getStringExtra("item_name");
        item_price = intent.getStringExtra("item_price");
        img_url = intent.getStringExtra("img_url");
        item_index = intent.getIntExtra("item_index", 0);
    }

    private void bindComponent(){
        textView = (TextView)findViewById(R.id.savedlist_change_item_title);
        editText = (MaterialEditText)findViewById(R.id.savedlist_change_item_price);
        buttonmodify = (ButtonRectangle)findViewById(R.id.savedlist_change);
        buttondelete = (ButtonRectangle)findViewById(R.id.savedlist_delete);
        toolbar = (Toolbar)findViewById(R.id.savedlist_toolbar);
    }

    private void set_textandedit(){
        textView.setText(item_name);
        editText.setText(item_price);
    }
    private void set_button_clicklistener() {
        final HowMuchSQLHelper sqLiteDatabase = new HowMuchSQLHelper(getApplicationContext(), HowMuchSQLHelper.DB_name, null, HowMuchSQLHelper.versionNumber);
        buttonmodify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_price = editText.getText().toString();
                if(item_price.equals("")){
                    Dialog dialog = new Dialog(getApplicationContext(), "주의", "가격을 제대로 입력해주세요");
                    dialog.show();
                    return;
                }
                int parse_updated_price = Integer.parseInt(item_price);
                sqLiteDatabase.updateITEM_INFO(item_name, parse_updated_price);
                resultCode = 1;
                setupresult();
                finish();
            }
        });
        buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int parse_updated_price = Integer.parseInt(editText.getText().toString());
                sqLiteDatabase.deleteITEM_INFO(item_name, parse_updated_price);
                resultCode = 2;
                setupresult();
                finish();
            }
        });
    }
    private void set_toolbar(){
        toolbar.setTitle(item_name + " 수정");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void setupresult(){
        Log.d("resultvale", String.valueOf(resultCode));
        Intent intent = getIntent();
        intent.putExtra("item_index2", item_index);
        intent.putExtra("item_price2", item_price);
        setResult(resultCode, intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        resultCode = 0;
        setupresult();
        if(id == R.id.item_icon){
            finish();
        }
        else{
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
