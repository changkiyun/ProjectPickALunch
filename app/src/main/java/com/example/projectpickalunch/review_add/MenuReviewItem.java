package com.example.projectpickalunch.review_add;

public class MenuReviewItem {

    String menuName;
    String menuReview;

    MenuReviewItem(String menuName, String menuReview){
        this.menuName = menuName;
        this.menuReview = menuReview;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuReview(String menuReview) {
        this.menuReview = menuReview;
    }

    public String getMenuReview() {
        return menuReview;
    }
}
