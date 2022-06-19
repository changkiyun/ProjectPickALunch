package com.example.projectpickalunch.restorant_info;

import android.graphics.drawable.Drawable;

public class RecyclerViewItem {

    String detail_image_1;
    String detail_image_2;
    String detail_image_3;
    String restorant_name;

    public RecyclerViewItem(){}

    public String getDetail_image_1() {
        return detail_image_1;
    }

    public void setDetail_image_1(String detail_image_1) {
        this.detail_image_1 = detail_image_1;
    }

    public String getDetail_image_2() {
        return detail_image_2;
    }

    public void setDetail_image_2(String detail_image_2) {
        this.detail_image_2 = detail_image_2;
    }

    public String getDetail_image_3() {
        return detail_image_3;
    }

    public void setDetail_image_3(String detail_image_3) {
        this.detail_image_3 = detail_image_3;
    }

    public String getRestorant_name() {
        return restorant_name;
    }

    public void setRestorant_name(String restorant_name) {
        this.restorant_name = restorant_name;
    }

    //trash
    private Drawable iconDrawable;

    public RecyclerViewItem(String restorant_name){

    }

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

}