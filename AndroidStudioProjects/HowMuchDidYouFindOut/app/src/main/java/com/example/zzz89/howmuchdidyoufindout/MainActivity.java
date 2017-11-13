package com.example.zzz89.howmuchdidyoufindout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zzz89.howmuchdidyoufindout.db.SaveSharedPreference;
import com.example.zzz89.howmuchdidyoufindout.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Intent intent;

        if(SaveSharedPreference.getUserName(MainActivity.this).length() == 0){
            //No Login
            intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else{
            //already Login
            intent = new Intent(MainActivity.this, AppMainActivity.class);
            startActivity(intent);
        }
    }
}
