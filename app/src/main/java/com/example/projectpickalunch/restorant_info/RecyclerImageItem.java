package com.example.projectpickalunch.restorant_info;

public class RecyclerImageItem {
    private String imageUrl;
    private String fileName;

    RecyclerImageItem(){

    }

    public RecyclerImageItem(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
