package com.example.zzz89.howmuchdidyoufindout.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by zzz89 on 2017-10-21.
 */

public class SaveSharedPreference {
    static private final String PREF_USER_NAME = "username";
    static private final String ONLINE_FILTER[] = {"Auction", "11st", "Gmarket", "Coupang", "SSG_COM", "Interpark", "Cjmall"};
    static private final String MAIL_SETTING_CHECK[] = {"예약 목록에서 아이템을 제거하기 전까지 메일을 받습니다", "알람을 최초 한번만 전송합니다"};

    static SharedPreferences getSharedPreferences(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static void setOnlineFilter(Context ctx, boolean istrue[]){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        for(int i = 0; i < ONLINE_FILTER.length; i++){
            editor.putBoolean(ONLINE_FILTER[i], istrue[i]);
            editor.commit();
        }
    }

    public static boolean[] getOnlineFilter(Context ctx){
        boolean temp[] = new boolean[ONLINE_FILTER.length];
        for(int i = 0; i < temp.length; i++){
            temp[i] = getSharedPreferences(ctx).getBoolean(ONLINE_FILTER[i], true);
        }
        return temp;
    }

    public static void setMailSettingCheck(Context ctx, boolean check[]){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        for(int i = 0; i < MAIL_SETTING_CHECK.length; i++){
            editor.putBoolean(MAIL_SETTING_CHECK[i], check[i]);
            editor.commit();
        }
    }

    public static boolean[] getMailSettingCheck(Context ctx){
        boolean temp[] = new boolean[MAIL_SETTING_CHECK.length];
        for(int i = 0; i < temp.length; i++){
            temp[i] = getSharedPreferences(ctx).getBoolean(MAIL_SETTING_CHECK[i], true);
        }
        return temp;
    }

    public static boolean getClickItem(Context ctx, String keyword){
        return getSharedPreferences(ctx).getBoolean(keyword, true);
    }

    public static void setClickItem(Context ctx, String keyword, boolean click){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(keyword, click);
        editor.commit();
    }

    public static void setToken(Context ctx, String token){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("FCM token", token);
        editor.commit();
    }

    public static String getToken(Context ctx){
        return getSharedPreferences(ctx).getString("FCM token", "a");
    }
}
