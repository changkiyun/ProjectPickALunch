package com.example.projectpickalunch.restorant_info;

import android.media.Image;

import java.util.ArrayList;

public class ReviewImageItem {
    String key;
    ArrayList<RecyclerImageItem> imageList;
    ImageRecyclerAdapter adapter;

    ReviewImageItem() {}

    ReviewImageItem(String UID, ArrayList<RecyclerImageItem> imageList, ImageRecyclerAdapter adapter) {
        this.key = UID;
        this.imageList = imageList;
        this.adapter = adapter;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setImageList(ArrayList<RecyclerImageItem> imageList) {
        this.imageList = imageList;
    }

    public ArrayList<RecyclerImageItem> getImageList() {
        return imageList;
    }

    public void setAdapter(ImageRecyclerAdapter adapter) {
        this.adapter = adapter;
    }

    public ImageRecyclerAdapter getAdapter() {
        return adapter;
    }
}
