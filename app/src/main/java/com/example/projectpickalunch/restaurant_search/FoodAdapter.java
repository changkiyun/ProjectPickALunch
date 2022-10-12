package com.example.projectpickalunch.restaurant_search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends BaseAdapter {


    ArrayList<Food> items = new ArrayList<>();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(Food food){
        this.items.add(food);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Food_View food_view = null;
        if(convertView == null){
            food_view = new Food_View(food_view.getContext());  //다시 확인
        }else{
            food_view=(Food_View) convertView;
        }

        Food friend = items.get(position);
        food_view.setNameView(friend.getName());
        food_view.setNumberView(friend.getNumber());
        food_view.setImageView(friend.getImgId());

        return food_view;
    }
}
