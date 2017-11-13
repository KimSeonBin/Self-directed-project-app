package com.example.zzz89.howmuchdidyoufindout.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zzz89 on 2017-11-09.
 */

public class HowMuchSQLHelper extends SQLiteOpenHelper {
    public static final int versionNumber = 1;
    public static final String DB_name = "HowMuch.db";
    public HowMuchSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_create = "CREATE TABLE ITEM_INFO (ITEM_NAME TEXT, WANTED_PRICE INTEGER, ITEM_IMAGE_URL TEXT)";

        db.execSQL(sql_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addITEM_INFO(String item_name, int item_price, String img_url){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ITEM_NAME", item_name);
        values.put("WANTED_PRICE", item_price);
        values.put("ITEM_IMAGE_URL", img_url);
        sqLiteDatabase.insert("ITEM_INFO", null, values);
    }

    public void updateITEM_INFO(String item_name, int item_price){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("WANTED_PRICE", item_price);
        sqLiteDatabase.update("ITEM_INFO", values, "ITEM_NAME = ?", new String[]{item_name});
    }

    public String[] selectITEM_INFO(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql_select_item = "select * from ITEM_INFO";
        String sql_select_count = "select count(*) from ITEM_INFO";
        int size = 0, i = 0;
        String result[];
        Cursor cursor = sqLiteDatabase.rawQuery(sql_select_count, null);
        while(cursor.moveToNext()){
            size = cursor.getInt(0);
        }
        result = new String[size];

        cursor = sqLiteDatabase.rawQuery(sql_select_item, null);
        while(cursor.moveToNext() && i < size){
            result[i++] = cursor.getString(0) + " " + cursor.getInt(1) + " " + cursor.getString(2);
        }
        return result;
    }

    public void deleteITEM_INFO(String item_name, int item_price){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("ITEM_INFO", "ITEM_NAME = ? AND WANTED_PRICE = ?", new String[] {item_name, String.valueOf(item_price)});
    }
}
