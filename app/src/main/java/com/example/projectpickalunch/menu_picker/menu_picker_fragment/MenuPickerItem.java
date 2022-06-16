package com.example.projectpickalunch.menu_picker.menu_picker_fragment;

public class MenuPickerItem {
    String restorant_name;
    String restorant_score;
    String restorant_image_src;
    String restorant_category;


    public MenuPickerItem(){

    }

    //name
    public String getRestorant_name() {
        return restorant_name;
    }

    public void setRestorant_name(String restorant_name) {
        this.restorant_name = restorant_name;
    }

    //score
    public String getRestorant_score() {
        return restorant_score;
    }

    public void setRestorant_score(String restorant_score) {
        this.restorant_score = restorant_score;
    }

    //imageSrc
    public String getRestorant_image_src() {
        return restorant_image_src;
    }

    public void setRestorant_image_src(String restorant_image_src) {
        this.restorant_image_src = restorant_image_src;
    }

    public String getRestorant_category() {
        return restorant_category;
    }

    public void setRestorant_category(String restorant_category) {
        this.restorant_category = restorant_category;
    }
}
