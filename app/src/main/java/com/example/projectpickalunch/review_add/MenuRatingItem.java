package com.example.projectpickalunch.review_add;

public class MenuRatingItem {

    String rateName;
    float rate;

    MenuRatingItem(String rateName){
        this.rateName = rateName;
    }

    public MenuRatingItem(String rateName, float rate){
        this.rateName = rateName;
        this.rate = rate;
    }

    public void setRateName(String rateName) {
        this.rateName = rateName;
    }

    public String getRateName() {
        return rateName;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getRate() {
        return rate;
    }
}
