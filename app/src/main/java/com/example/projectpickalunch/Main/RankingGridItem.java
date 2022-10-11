package com.example.projectpickalunch.Main;

public class RankingGridItem {
    String restorant_name;
    String restorant_score;
    String restorant_image_src;

    RankingGridItem() {

    }

    public RankingGridItem(String restorant_name, String restorant_score, String restorant_image_src){
        this.restorant_name = restorant_name;
        this.restorant_image_src = restorant_image_src;
        this.restorant_score = restorant_score;
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
}
