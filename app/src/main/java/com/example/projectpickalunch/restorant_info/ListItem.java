package com.example.projectpickalunch.restorant_info;

import android.widget.TextView;

public class ListItem {
    private String name;
    private String reviewText;
    private String reviewScore;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(String reviewScore) {
        this.reviewScore = reviewScore;
    }

    ListItem(String name,String reviewText, String reviewScore){
        this.name = name;
        this.reviewText = reviewText;
        this.reviewScore = reviewScore;
    }
}
