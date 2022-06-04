package com.example.projectpickalunch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainGridAdapter extends BaseAdapter {
        Context context;
        Integer[] sampleImage;
        public MainGridAdapter(Context context){
            this.context = context;
            sampleImage = ((MainActivity)context).sampleImage;
        }

    @Override
    public int getCount() {
        return sampleImage.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(300,300));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setPadding(5,5,5,5);

        imageView.setImageResource(sampleImage[i]);

        return imageView;
    }
}
