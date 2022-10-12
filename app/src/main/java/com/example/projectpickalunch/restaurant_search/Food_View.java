package com.example.projectpickalunch.restaurant_search;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.projectpickalunch.R;

public class Food_View extends LinearLayout {
    ImageView menu_picker_restorant_image;
    TextView menu_picker_restorant_name;
    TextView menu_picker_restorant_score;

    public Food_View(Context context) {
        super(context);
        init(context);
    }

    public Food_View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.menu_picker_item, this, true);
        menu_picker_restorant_image =findViewById(R.id.menu_picker_restorant_image);
        menu_picker_restorant_name = findViewById(R.id.menu_picker_restorant_name);
        menu_picker_restorant_score = findViewById(R.id.menu_picker_restorant_score);
    }

    public void setImageView(int imgId){
        menu_picker_restorant_image.setImageResource(imgId);
    }

    public void setNameView(String name){
        menu_picker_restorant_name.setText(name);
    }

    public void setNumberView(String number){
        menu_picker_restorant_score.setText(number);
    }
}
