package com.example.projectpickalunch.Main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.projectpickalunch.R;

public class MainGridItem_View extends ConstraintLayout {
    ImageView restorant_image;
    TextView restorant_name;
    TextView restorant_score;


    MainGridItem_View(Context context){
        super(context);
        init(context);
    }

    public MainGridItem_View(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.main_grid_item, this, true);
        restorant_image = findViewById(R.id.restorant_image);
        restorant_name = findViewById(R.id.restorant_name);
        restorant_score = findViewById(R.id.restorant_score);
    }

    public void setRestorant_image(int resImg){
        restorant_image.setImageResource(resImg);
    }
    public void setRestorant_name(String name){
        restorant_name.setText(name);
    }
    public void setRestorant_score(String score){
        restorant_score.setText(score);
    }

}
