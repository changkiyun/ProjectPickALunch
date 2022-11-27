package com.example.projectpickalunch.review_add;

import android.net.Uri;

import java.util.ArrayList;

public class ReviewItem {

    private String user_name;
    private String restaurant_review;
    private String tasteRate;
    private String kindRate;
    private String cleanRate;
    private String priceRate;
    private String avgRate;
    private String review_date;

    ReviewItem(String user_name, String restaurant_review, String review_date,
               String tasteRate, String kindRate, String cleanRate, String priceRate, String avgRate)
    {
        this.user_name = user_name;
        this.restaurant_review = restaurant_review;
        this.tasteRate = tasteRate;
        this.cleanRate = cleanRate;
        this.kindRate = kindRate;
        this.priceRate = priceRate;
        this.avgRate = avgRate;
        this.review_date = review_date;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getRestaurant_review() {
        return restaurant_review;
    }

    public void setRestaurant_review(String restaurant_review) {
        this.restaurant_review = restaurant_review;
    }

    public String getTasteRate() {
        return tasteRate;
    }

    public void setTasteRate(String tasteRate) {
        this.tasteRate = tasteRate;
    }

    public String getKindRate() {
        return kindRate;
    }

    public void setKindRate(String kindRate) {
        this.kindRate = kindRate;
    }

    public String getCleanRate() {
        return cleanRate;
    }

    public void setCleanRate(String cleanRate) {
        this.cleanRate = cleanRate;
    }

    public String getPriceRate() {
        return priceRate;
    }

    public void setPriceRate(String priceRate) {
        this.priceRate = priceRate;
    }

    public String getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(String avgRate) {
        this.avgRate = avgRate;
    }

    public String getReview_date() {
        return review_date;
    }

    public void setReview_date(String review_date) {
        this.review_date = review_date;
    }
}
