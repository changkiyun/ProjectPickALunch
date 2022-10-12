package com.example.projectpickalunch.restaurant_search;

public class Food {
    String name;
    String number;
    int imgId;

    public Food(String name, String number, int imgId) {
        this.name = name;
        this.number = number;
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}