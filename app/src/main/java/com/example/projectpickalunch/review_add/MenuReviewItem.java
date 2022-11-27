package com.example.projectpickalunch.review_add;

import java.util.ArrayList;

public class MenuReviewItem {

    String menuName;
    ArrayList<MenuRatingItem> menuRatingItems;

    MenuReviewItem(){}

    MenuReviewItem(String menuName, ArrayList<MenuRatingItem> menuRatingItems){
        this.menuName = menuName;
        this.menuRatingItems = menuRatingItems;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuName() {
        return menuName;
    }

    public ArrayList<MenuRatingItem> getMenuRatingItems() {
        return menuRatingItems;
    }

    public void setMenuRatingItems(ArrayList<MenuRatingItem> menuRatingItems) {
        this.menuRatingItems = menuRatingItems;
    }
}
