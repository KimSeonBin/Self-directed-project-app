package com.example.zzz89.howmuchdidyoufindout;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

/**
 * Created by zzz89 on 2017-10-28.
 */

public class AppMainActivity extends AppCompatActivity {
    BottomBar bottomBar;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main);
        LoginActivity loginActivity = (LoginActivity)LoginActivity.activity;

        if(loginActivity != null) {
            loginActivity.finish();
        }

        toolbar = (Toolbar)findViewById(R.id.app_main_toolbar);
        toolbar.setTitle("검색");
        setSupportActionBar(toolbar);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout, SearchFragment.newInstance());
        fragmentTransaction.commit();

        bottomBar = (BottomBar)findViewById(R.id.main_bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment;
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if(tabId == R.id.search){
                    fragment = SearchFragment.newInstance();
                    toolbar.setTitle("검색");
                }
                else if(tabId == R.id.content){
                    fragment = SavedlistFragment.newInstance();
                    toolbar.setTitle("예약 목록");
                }
                else if(tabId == R.id.alarm){
                    fragment = AlarmFragment.newInstance();
                    toolbar.setTitle("알람");
                }
                else if(tabId == R.id.setting){
                    fragment = SettingFragment.newInstance();
                    toolbar.setTitle("환경 설정");
                }
                else{
                }
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_layout, fragment);
                fragmentTransaction.commit();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finishAffinity();
    }

}
