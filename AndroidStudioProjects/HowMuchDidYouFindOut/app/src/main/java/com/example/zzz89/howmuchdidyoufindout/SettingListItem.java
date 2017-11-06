package com.example.zzz89.howmuchdidyoufindout;

/**
 * Created by zzz89 on 2017-10-31.
 */

public class SettingListItem {
    private String Primary_sentence;
    private String Secondary_sentence;

    public SettingListItem(String Primary_sentence, String Secondary_sentence){
        this.Primary_sentence = Primary_sentence;
        this.Secondary_sentence = Secondary_sentence;
    }

    public String getPrimary_sentence() {
        return Primary_sentence;
    }

    public void setPrimary_sentence(String primary_sentence) {
        Primary_sentence = primary_sentence;
    }

    public String getSecondary_sentence() {
        return Secondary_sentence;
    }

    public void setSecondary_sentence(String secondary_sentence) {
        Secondary_sentence = secondary_sentence;
    }
}
