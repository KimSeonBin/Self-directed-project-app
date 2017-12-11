package com.example.zzz89.howmuchdidyoufindout.app_main.setting.alarm;

/**
 * Created by zzz89 on 2017-12-08.
 */

public class Alarm_Click_Item {
    private String searched_name;
    private String item_url;
    private String item_image_url;
    private int price;
    private String mall;

    public String getSearched_name() {
        return searched_name;
    }

    public String getItem_image_url() {
        return item_image_url;
    }

    public void setItem_image_url(String item_image_url) {
        this.item_image_url = item_image_url;
    }

    public void setSearched_name(String searched_name) {
        this.searched_name = searched_name;
    }

    public String getItem_url() {
        return item_url;
    }

    public void setItem_url(String item_url) {
        this.item_url = item_url;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMall() {
        return mall;
    }

    public void setMall(String mall) {
        this.mall = mall;
    }

    public Alarm_Click_Item(String searched_name, String item_url, String item_image_url, int price, String mall) {
        this.searched_name = searched_name;
        this.item_url = item_url;
        this.item_image_url = item_image_url;
        this.price = price;
        this.mall = mall;
    }
}
