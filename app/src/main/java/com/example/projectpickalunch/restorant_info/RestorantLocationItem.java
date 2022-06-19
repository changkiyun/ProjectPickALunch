package com.example.projectpickalunch.restorant_info;

public class RestorantLocationItem {

    public String restorant_location_latitude; //위도
    public String restorant_location_longitude; //경도
    public String restorant_location_id;
    public String restorant_name;

    public RestorantLocationItem(){}

    public String getRestorant_location_latitude() {
        return restorant_location_latitude;
    }

    public void setRestorant_location_latitude(String restorant_location_latitude) {
        this.restorant_location_latitude = restorant_location_latitude;
    }

    public String getRestorant_location_longitude() {
        return restorant_location_longitude;
    }

    public void setRestorant_location_longitude(String restorant_location_longitude) {
        this.restorant_location_longitude = restorant_location_longitude;
    }

    public String getRestorant_location_id() {
        return restorant_location_id;
    }

    public void setRestorant_location_id(String restorant_location_id) {
        this.restorant_location_id = restorant_location_id;
    }

    public String getRestorant_name() {
        return restorant_name;
    }

    public void setRestorant_name(String restorant_name) {
        this.restorant_name = restorant_name;
    }
}