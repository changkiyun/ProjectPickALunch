package com.example.projectpickalunch.review_add;

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
    private ArrayList<MenuReviewItem> menuReviewItems;
    private ArrayList<String> imgUris;

    ReviewItem(String user_name, String restaurant_review, String review_date,
               String tasteRate, String kindRate, String cleanRate, String priceRate, String avgRate,
               ArrayList<MenuReviewItem> menuReviewItems, ArrayList<String> imgUris)
    {
        this.user_name = user_name;
        this.restaurant_review = restaurant_review;
        this.tasteRate = tasteRate;
        this.cleanRate = cleanRate;
        this.kindRate = kindRate;
        this.priceRate = priceRate;
        this.avgRate = avgRate;
        this.review_date = review_date;
        this.menuReviewItems = menuReviewItems;
        this.imgUris = imgUris;
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


    public String getReview_date() {
        return review_date;
    }

    public void setReview_date(String review_date) {
        this.review_date = review_date;
    }
}
