package com.example.projectpickalunch.restorant_info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projectpickalunch.R;

import java.util.ArrayList;

public class ListItemAdapter extends BaseAdapter {
    ArrayList<ListItem> items = new ArrayList<ListItem>();
    Context context;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        ListItem listItem = items.get(position);


        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item,parent,false);
        }
        TextView reviewName = convertView.findViewById(R.id.tV1);
        TextView reviewText = convertView.findViewById(R.id.reviewText);
        TextView reviewScore = convertView.findViewById(R.id.reviewScore);

        reviewName.setText(listItem.getName());
        reviewText.setText(listItem.getReviewText());
        reviewScore.setText(listItem.getReviewScore());

        return convertView;
    }

    public void addItems(ListItem item){
        items.add(item);
    }
}