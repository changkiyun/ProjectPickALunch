package com.example.projectpickalunch.review_add;

public class MenuRatingItem {

    String rateName;
    String rating;

    MenuRatingItem(String rateName, String rating){
        this.rateName = rateName;
        this.rating = rating;
    }

    public void setRateName(String rateName) {
        this.rateName = rateName;
    }

    public String getRateName() {
        return rateName;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }
}
