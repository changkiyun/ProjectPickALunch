package com.example.projectpickalunch.menu_picker;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectpickalunch.R;

public class MenuPicker extends AppCompatActivity {

    ImageButton menuPickerReturnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_picker);

        menuPickerReturnButton = (ImageButton) findViewById(R.id.menuPickerReturnButton);
        menuPickerReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
