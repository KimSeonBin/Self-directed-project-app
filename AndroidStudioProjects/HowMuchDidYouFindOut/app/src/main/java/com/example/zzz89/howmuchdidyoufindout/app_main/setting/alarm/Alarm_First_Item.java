package com.example.zzz89.howmuchdidyoufindout.app_main.setting.alarm;

/**
 * Created by zzz89 on 2017-12-01.
 */

public class Alarm_First_Item {
    private String origin_keyword;
    private int count;
    private String Img_url;

    public String getOrigin_keyword() {
        return origin_keyword;
    }

    public void setOrigin_keyword(String origin_keyword) {
        this.origin_keyword = origin_keyword;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getImg_url() {
        return Img_url;
    }

    public void setImg_url(String img_url) {
        Img_url = img_url;
    }

    public Alarm_First_Item(String origin_keyword, int count, String img_url) {

        this.origin_keyword = origin_keyword;
        this.count = count;
        Img_url = img_url;
    }
}
