package com.example.zzz89.howmuchdidyoufindout.server_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zzz89 on 2017-11-25.
 */

public class usersetting {
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("agree_mail_transfer")
    @Expose
    private boolean agree_mail_transfer;
    @SerializedName("daily_mail_transfer")
    @Expose
    private boolean daily_mail_transfer;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public boolean isAgree_mail_transfer() {
        return agree_mail_transfer;
    }

    public void setAgree_mail_transfer(boolean agree_mail_transfer) {
        this.agree_mail_transfer = agree_mail_transfer;
    }

    public boolean isDaily_mail_transfer() {
        return daily_mail_transfer;
    }

    public void setDaily_mail_transfer(boolean daily_mail_transfer) {
        this.daily_mail_transfer = daily_mail_transfer;
    }

    public usersetting(String user_id, boolean agree_mail_transfer, boolean daily_mail_transfer) {
        this.user_id = user_id;
        this.agree_mail_transfer = agree_mail_transfer;
        this.daily_mail_transfer = daily_mail_transfer;
    }
    public usersetting(String user_id, boolean setting_value[]){
        this.user_id = user_id;
        this.agree_mail_transfer = setting_value[0];
        this.daily_mail_transfer = setting_value[1];
    }
}
