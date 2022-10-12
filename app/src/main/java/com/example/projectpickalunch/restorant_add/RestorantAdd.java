package com.example.projectpickalunch.restorant_add;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectpickalunch.R;
import com.example.projectpickalunch.restaurant_search.Search;

public class RestorantAdd extends AppCompatActivity {

    ImageButton add_map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restorant_add);

        add_map = (ImageButton) findViewById(R.id.add_map);
        add_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RestorantMap = new Intent(getApplicationContext(), RestorantMap.class);
                startActivity(RestorantMap);
            }
        });




    }
}
