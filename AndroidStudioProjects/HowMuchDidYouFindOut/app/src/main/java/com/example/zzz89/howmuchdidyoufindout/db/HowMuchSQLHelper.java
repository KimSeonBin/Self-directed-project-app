package com.example.zzz89.howmuchdidyoufindout.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.zzz89.howmuchdidyoufindout.app_main.setting.alarm.Alarm_Click_Item;
import com.example.zzz89.howmuchdidyoufindout.app_main.setting.alarm.Alarm_First_Item;
import com.example.zzz89.howmuchdidyoufindout.app_main.setting.search.SearchResultCardItem;
import com.example.zzz89.howmuchdidyoufindout.server_api.search_info;

/**
 * Created by zzz89 on 2017-11-09.
 */

public class HowMuchSQLHelper extends SQLiteOpenHelper {
    public static final int versionNumber = 2;
    public static final String DB_name = "HowMuch.db";
    public HowMuchSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_create = "CREATE TABLE ITEM_INFO (ITEM_NAME TEXT, WANTED_PRICE INTEGER, ITEM_IMAGE_URL TEXT)";
        String sql_create2 = "CREATE TABLE SEARCH_INFO (FOUND_ITEM_NAME TEXT, ITEM_URL TEXT, SEARCH_PRICE INTEGER, ITEM_IMAGE_URL TEXT, FOUND_MALL TEXT, ITEM_NAME TEXT, READ_COUNT INTEGER)";
        db.execSQL(sql_create);
        db.execSQL(sql_create2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ITEM_INFO");
        db.execSQL("DROP TABLE IF EXISTS SEARCH_INFO");
        Log.d("version change", String.valueOf(newVersion));
        onCreate(db);
    }

    public void addITEM_INFO(String item_name, int item_price, String img_url){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ITEM_NAME", item_name);
        values.put("WANTED_PRICE", item_price);
        values.put("ITEM_IMAGE_URL", img_url);
        sqLiteDatabase.insert("ITEM_INFO", null, values);
        sqLiteDatabase.close();
    }

    public void updateITEM_INFO(String item_name, int item_price){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("WANTED_PRICE", item_price);
        sqLiteDatabase.update("ITEM_INFO", values, "ITEM_NAME = ?", new String[]{item_name});
        sqLiteDatabase.close();
    }

    public SearchResultCardItem[] selectITEM_INFO(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql_select_item = "select ITEM_IMAGE_URL, ITEM_NAME, WANTED_PRICE from ITEM_INFO";
        String sql_select_count = "select count(*) from ITEM_INFO";
        int size = 0, i = 0;
        SearchResultCardItem result[];
        Cursor cursor = sqLiteDatabase.rawQuery(sql_select_count, null);
        while(cursor.moveToNext()){
            size = cursor.getInt(0);
        }
        result = new SearchResultCardItem[size];
        cursor = sqLiteDatabase.rawQuery(sql_select_item, null);
        while(cursor.moveToNext() && i < size){
            result[i++] = new SearchResultCardItem(cursor.getString(0), cursor.getString(1), String.valueOf(cursor.getInt(2)));
        }

        sqLiteDatabase.close();
        return result;
    }

    public void deleteITEM_INFO(String item_name, int item_price){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("ITEM_INFO", "ITEM_NAME = ? AND WANTED_PRICE = ?", new String[] {item_name, String.valueOf(item_price)});
        sqLiteDatabase.close();
    }

    public search_info[] selectSearch_Info(String origin_keyword){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql_select_where_orikeyword = "select FOUND_ITEM_NAME, ITEM_URL, SEARCH_PRICE, ITEM_IMAGE_URL, FOUND_MALL, ITEM_NAME, READ_COUNT FROM SEARCH_INFO WHERE ITEM_NAME = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql_select_where_orikeyword, new String[]{origin_keyword});

        int i = 0, size = cursor.getCount();
        if(size > 0){
            search_info info[] = new search_info[size];

            while(cursor.moveToNext() && i < size){
                info[i++] = new search_info(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getInt(6));
            }
            sqLiteDatabase.close();
            return info;
        }
        else{
            sqLiteDatabase.close();
            return null;
        }
    }

    public String[] select_ori_keyword_from_FOUND_ITEM(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql_select_only_orikeyword = "SELECT DISTINCT ITEM_NAME FROM SEARCH_INFO";
        Cursor cursor = sqLiteDatabase.rawQuery(sql_select_only_orikeyword, null);
        int size = cursor.getCount(), i = 0;
        if (size > 0){
            String result[] = new String[size];
            while(cursor.moveToNext() && i < size){
                result[i++] = cursor.getString(0);
            }
            sqLiteDatabase.close();
            return result;
        }
        else{
            sqLiteDatabase.close();
            return null;
        }
    }

    public void delete_All_Item(String keyword){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("ITEM_INFO", "item_name=?", new String[]{keyword});
    }

    public Alarm_First_Item select_Alarm_First(String ori_keyword){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql_select_Alarm_First = "select count(*), ITEM_IMAGE_URL, MAX(SEARCH_PRICE) FROM SEARCH_INFO WHERE ITEM_NAME = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql_select_Alarm_First, new String[]{ori_keyword});
        int size = cursor.getCount(), i = 0;
        if (size > 0){
            Alarm_First_Item result = null;
            while (cursor.moveToNext() && i < size){
                result = new Alarm_First_Item(ori_keyword, cursor.getInt(0), cursor.getString(1));
            }
            cursor.close();
            sqLiteDatabase.close();
            return result;
        }
        else{
            sqLiteDatabase.close();
            return null;
        }
    }

    public Alarm_Click_Item[] select_Alarm_Click_items(String keyword, int count){

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql_select_Alarm_Click_item = "select FOUND_ITEM_NAME, ITEM_URL, ITEM_IMAGE_URL, SEARCH_PRICE, FOUND_MALL FROM SEARCH_INFO WHERE ITEM_NAME = ? LIMIT ?, ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql_select_Alarm_Click_item, new String[]{keyword, String.valueOf(count), String.valueOf(count+10)});
        int size = cursor.getCount(), i = 0;
        if(size > 0){
            Alarm_Click_Item[] items = new Alarm_Click_Item[size];

            while(cursor.moveToNext() && i < size){
                items[i++] = new Alarm_Click_Item(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),
                        cursor.getString(4));
            }
            sqLiteDatabase.close();
            return items;
        }
        else{
            sqLiteDatabase.close();
            return null;
        }
    }
    public void addSearch_Info(String found_item_name[], String item_url[], int search_price[], String item_image_url[], String found_mall[], String item_name[], int read_count[]){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        for(int i = 0; i < found_item_name.length; i++){
            ContentValues values = new ContentValues();
            values.put("FOUND_ITEM_NAME", found_item_name[i]);
            values.put("ITEM_URL", item_url[i]);
            values.put("SEARCH_PRICE", search_price[i]);
            values.put("ITEM_IMAGE_URL", item_image_url[i]);
            values.put("FOUND_MALL", found_mall[i]);
            values.put("ITEM_NAME", item_name[i]);
            values.put("READ_COUNT", read_count[i]);
            sqLiteDatabase.insert("SEARCH_INFO", null , values);
        }
        sqLiteDatabase.close();
    }

    public void deleteSearch_Info(String keyword){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("SEARCH_INFO", "item_name=?", new String[]{keyword});
        sqLiteDatabase.close();
    }

}
