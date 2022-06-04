package com.example.projectpickalunch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {
    //그리드뷰

    Integer[] sampleImage = {R.drawable.sample_image1, R.drawable.sample_image2,R.drawable.sample_image3,
            R.drawable.sample_image4,R.drawable.sample_image5, R.drawable.sample_image6,R.drawable.sample_image1, R.drawable.sample_image2,R.drawable.sample_image3,
            R.drawable.sample_image4,R.drawable.sample_image5, R.drawable.sample_image6,R.drawable.sample_image1, R.drawable.sample_image2,R.drawable.sample_image3,
            R.drawable.sample_image4,R.drawable.sample_image5, R.drawable.sample_image6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //오늘 뭐먹지 버튼 테스트용 임시 클릭리스너
        Button whatToEat = (Button) findViewById(R.id.menu_picker);
        whatToEat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Sickdang_Jeongbo.class);
                startActivity(intent);
            }
        });

        final GridView mainGridView = (GridView) findViewById(R.id.main_grid_view);
        //그리드뷰
        MainGridAdapter mainGridAdapter = new MainGridAdapter(this);
        mainGridView.setAdapter(mainGridAdapter);
    }
}