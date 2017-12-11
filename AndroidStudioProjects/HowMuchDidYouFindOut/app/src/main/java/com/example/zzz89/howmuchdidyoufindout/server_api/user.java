package com.example.zzz89.howmuchdidyoufindout.server_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zzz89 on 2017-11-19.
 */

public class user {
    @SerializedName("user")
    @Expose
    private String username;
    @SerializedName("fb_token")
    @Expose
    private String fbtoken;

    public user(String username) {
        this.username = username;
        fbtoken = "a";
    }

    public user(String username, String fbtoken) {
        this.username = username;
        this.fbtoken = fbtoken;
    }

    public String getFbtoken() {
        return fbtoken;
    }

    public void setFbtoken(String fbtoken) {
        this.fbtoken = fbtoken;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
