package com.example.projectpickalunch.Main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class MainGridAdapter extends BaseAdapter {
        Context context;
        ArrayList<MainGridItem> mainGridItems = new ArrayList<>();


        public MainGridAdapter(Context _context){
            context = _context;
        }

    @Override
    public int getCount() {
        return mainGridItems.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    //사용자 지정 함수
    public void addItem(MainGridItem item){
        mainGridItems.add(item);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        MainGridItem_View mainGridItem_view = null;
        if(convertView == null){
            mainGridItem_view = new MainGridItem_View(context.getApplicationContext());
        }
        else{
            mainGridItem_view = (MainGridItem_View) convertView;
        }

        MainGridItem mainGridItem = mainGridItems.get(position);
        mainGridItem_view.setRestorant_name(mainGridItem.getRestorant_name());
        mainGridItem_view.setRestorant_score((mainGridItem.getRestorant_score()));
        mainGridItem_view.setRestorant_image(mainGridItem.getRestorant_image_src());

        return mainGridItem_view;


    }
}
