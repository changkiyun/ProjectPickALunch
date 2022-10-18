package com.example.projectpickalunch.review_add;

public class ReviewItem {

    private String user_name;
    private String restaurant_review;
    private String review_rate;
    private String review_date;

    ReviewItem(String user_name, String restaurant_review, String review_rate, String review_date){
        this.user_name = user_name;
        this.restaurant_review = restaurant_review;
        this.review_rate = review_rate;
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

    public String getReview_rate() {
        return review_rate;
    }

    public void setReview_rate(String review_rate) {
        this.review_rate = review_rate;
    }

    public String getReview_date() { return review_date; }

    public void setReview_date(String review_date) {
        this.review_date = review_date;
    }
}
