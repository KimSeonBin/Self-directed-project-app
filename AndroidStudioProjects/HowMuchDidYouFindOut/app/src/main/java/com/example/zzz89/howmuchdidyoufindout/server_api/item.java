package com.example.zzz89.howmuchdidyoufindout.server_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zzz89 on 2017-11-25.
 */

public class item {
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("item_name")
    @Expose
    private String item_name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("category")
    @Expose
    private int category;
    @SerializedName("hoped_price")
    @Expose
    private int hoped_price;

    public item(String user, String item_name, String email, int category, int hoped_price) {
        this.user = user;
        this.item_name = item_name;
        this.email = email;
        this.category = category;
        this.hoped_price = hoped_price;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getHoped_price() {
        return hoped_price;
    }

    public void setHoped_price(int hoped_price) {
        this.hoped_price = hoped_price;
    }
}
