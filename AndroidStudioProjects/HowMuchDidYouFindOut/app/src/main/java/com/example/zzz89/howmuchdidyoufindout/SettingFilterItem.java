package com.example.zzz89.howmuchdidyoufindout;

import com.gc.materialdesign.views.Switch;

/**
 * Created by zzz89 on 2017-11-02.
 */

public class SettingFilterItem {
    private String onlinemall;
    private boolean istrue;


    public SettingFilterItem(String onlinemall, boolean istrue) {
        this.onlinemall = onlinemall;
        this.istrue = istrue;
    }
    public String getOnlinemall() {
        return onlinemall;
    }

    public void setOnlinemall(String onlinemall) {
        this.onlinemall = onlinemall;
    }

    public boolean getistrue() {
        return istrue;
    }

    public void setIstrue(boolean check) {
        this.istrue = check;
    }
}
