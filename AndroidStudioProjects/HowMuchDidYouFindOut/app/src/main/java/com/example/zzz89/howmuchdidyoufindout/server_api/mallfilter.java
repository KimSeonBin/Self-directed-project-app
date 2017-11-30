package com.example.zzz89.howmuchdidyoufindout.server_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zzz89 on 2017-11-25.
 */

public class mallfilter {
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("auction")
    @Expose
    private boolean auction;
    @SerializedName("elevenst")
    @Expose
    private boolean elevenst;
    @SerializedName("g_market")
    @Expose
    private boolean g_market;
    @SerializedName("coupang")
    @Expose
    private boolean coupang;
    @SerializedName("ssg_com")
    @Expose
    private boolean ssg_com;
    @SerializedName("interpark")
    @Expose
    private boolean interpark;
    @SerializedName("cjmall")
    @Expose
    private boolean cjmall;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public boolean isAuction() {
        return auction;
    }

    public void setAuction(boolean auction) {
        this.auction = auction;
    }

    public boolean isElevenst() {
        return elevenst;
    }

    public void setElevenst(boolean elevenst) {
        this.elevenst = elevenst;
    }

    public boolean isG_market() {
        return g_market;
    }

    public void setG_market(boolean g_market) {
        this.g_market = g_market;
    }

    public boolean isCoupang() {
        return coupang;
    }

    public void setCoupang(boolean coupang) {
        this.coupang = coupang;
    }

    public boolean isSsg_com() {
        return ssg_com;
    }

    public void setSsg_com(boolean ssg_com) {
        this.ssg_com = ssg_com;
    }

    public boolean isInterpark() {
        return interpark;
    }

    public void setInterpark(boolean interpark) {
        this.interpark = interpark;
    }

    public boolean isCjmall() {
        return cjmall;
    }

    public void setCjmall(boolean cjmall) {
        this.cjmall = cjmall;
    }

    public mallfilter(String userName, boolean[] switchValue) {
        this.user_id = userName;
        this.auction = switchValue[0];
        this.elevenst = switchValue[1];
        this.g_market = switchValue[2];
        this.coupang = switchValue[3];
        this.ssg_com = switchValue[4];
        this.interpark = switchValue[5];
        this.cjmall = switchValue[6];
    }
    public mallfilter(String user_id, boolean auction, boolean elevenst, boolean g_market, boolean coupang, boolean ssg_com, boolean interpark, boolean cjmall) {

        this.user_id = user_id;
        this.auction = auction;
        this.elevenst = elevenst;
        this.g_market = g_market;
        this.coupang = coupang;
        this.ssg_com = ssg_com;
        this.interpark = interpark;
        this.cjmall = cjmall;
    }
}
