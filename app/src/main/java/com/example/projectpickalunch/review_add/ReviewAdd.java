package com.example.projectpickalunch.review_add;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectpickalunch.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.willy.ratingbar.ScaleRatingBar;

public class ReviewAdd extends AppCompatActivity {

    //뷰 선언
    ImageButton review_add_return_button;
    ScaleRatingBar review_ratingBar;
    EditText review_text;
    ImageView review_image;
    TextView review_please;
    Button review_add_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_add);




        //돌아가기버튼
        review_add_return_button = findViewById(R.id.review_add_return_button);
        review_add_return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
