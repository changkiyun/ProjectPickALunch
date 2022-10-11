package com.example.projectpickalunch.restorant_info;

public class RecyclerImageItem {
    private String imageUrl;

    RecyclerImageItem(){

    }

    public RecyclerImageItem(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
