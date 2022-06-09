package com.example.projectpickalunch.menu_picker.menu_picker_fragment;

public class MenuPickerItem {
    String menu_picker_restorant_name;
    String menu_picker_restorant_score;
    int menu_picker_restorant_image_src;

    public MenuPickerItem(String name, String score, int imageSrc){
        menu_picker_restorant_name = name;
        menu_picker_restorant_score = score;
        menu_picker_restorant_image_src = imageSrc;
    }

    public String getMenu_picker_restorant_name() {
        return menu_picker_restorant_name;
    }

    public void setMenu_picker_restorant_name(String menu_picker_restorant_name) {
        this.menu_picker_restorant_name = menu_picker_restorant_name;
    }

    public String getMenu_picker_restorant_score() {
        return menu_picker_restorant_score;
    }

    public void setMenu_picker_restorant_score(String menu_picker_restorant_score) {
        this.menu_picker_restorant_score = menu_picker_restorant_score;
    }

    public int getMenu_picker_restorant_image_src() {
        return menu_picker_restorant_image_src;
    }

    public void setMenu_picker_restorant_image_src(int menu_picker_restorant_image_src) {
        this.menu_picker_restorant_image_src = menu_picker_restorant_image_src;
    }
}
