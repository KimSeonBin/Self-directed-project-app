package com.example.zzz89.howmuchdidyoufindout.app_main.setting;

/**
 * Created by zzz89 on 2017-11-03.
 */

public class SettingMailItem {
    private String sentence;
    private boolean check;
    public SettingMailItem(String sentence, boolean check) {
        this.sentence = sentence;
        this.check = check;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
