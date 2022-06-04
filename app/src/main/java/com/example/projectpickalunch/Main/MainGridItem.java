package com.example.projectpickalunch.Main;

public class MainGridItem {
    String restorant_name;
    String restorant_score;
    int restorant_image_src;

    public MainGridItem(String name, String score, int imageSrc){
        restorant_name = name;
        restorant_score = score;
        restorant_image_src = imageSrc;
    }

    public String getRestorant_name() {
        return restorant_name;
    }

    public void setRestorant_name(String restorant_name) {
        this.restorant_name = restorant_name;
    }

    public String getRestorant_score() {
        return restorant_score;
    }

    public void setRestorant_score(String restorant_score) {
        this.restorant_score = restorant_score;
    }

    public int getRestorant_image_src() {
        return restorant_image_src;
    }

    public void setRestorant_image_src(int restorant_image_src) {
        this.restorant_image_src = restorant_image_src;
    }
}
