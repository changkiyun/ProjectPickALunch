package com.example.projectpickalunch.restorant_info;

public class ReviewRecyclerItem {

    String user_name;
    String review_rate;
    String restaurant_review;
    String review_date;

    ReviewRecyclerItem() {

    }

    public ReviewRecyclerItem(String user_name, String review_rate, String restaurant_review, String review_date){
        this.user_name = user_name;
        this.review_rate = review_rate;
        this.restaurant_review = restaurant_review;
        this.review_date = review_date;
    }

    //USER_NAME
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    //REVIEW_RATE
    public String getReview_rate() {
        return review_rate;
    }

    public void setReview_rate(String review_rate) {
        this.review_rate = review_rate;
    }

    //RESAURANT_REVIEW

    public String getRestaurant_review() {
        return restaurant_review;
    }

    public void setRestaurant_review(String restaurant_review) {
        this.restaurant_review = restaurant_review;
    }

    //REVIEW_DATE
    public String getReview_date() {
        return review_date;
    }

    public void setReview_date(String review_date) {
        this.review_date = review_date;
    }
}
