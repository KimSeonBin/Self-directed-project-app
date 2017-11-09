package com.example.zzz89.howmuchdidyoufindout;

/**
 * Created by zzz89 on 2017-11-07.
 */

public class SearchResultCardItem {
    private String imageReso;
    private String itemName;
    private String priceInfo;

    public SearchResultCardItem(String imageReso, String itemName, String priceInfo) {
        this.imageReso = imageReso;
        this.itemName = itemName;
        this.priceInfo = priceInfo;
    }

    public String getPriceInfo() {

        return priceInfo;
    }

    public void setPriceInfo(String priceInfo) {
        this.priceInfo = priceInfo;
    }

    public String getImageReso() {

        return imageReso;
    }

    public void setImageReso(String imageReso) {
        this.imageReso = imageReso;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
