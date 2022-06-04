package com.example.projectpickalunch.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.projectpickalunch.R;
import com.example.projectpickalunch.restorant_info.Sickdang_Jeongbo;

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

        //메인화면 그리드뷰
        GridView mainGridView = (GridView) findViewById(R.id.main_grid_view);
        MainGridAdapter mainGridAdapter = new MainGridAdapter(this);

        //아이템 추가
        mainGridAdapter.addItem(new MainGridItem("1.가타쯔무리", "5.0", R.drawable.sample_image1));
        mainGridAdapter.addItem(new MainGridItem("1.가타쯔무리", "5.0", R.drawable.sample_image1));
        mainGridAdapter.addItem(new MainGridItem("1.가타쯔무리", "5.0", R.drawable.sample_image1));
        mainGridAdapter.addItem(new MainGridItem("1.가타쯔무리", "5.0", R.drawable.sample_image1));
        mainGridAdapter.addItem(new MainGridItem("1.가타쯔무리", "5.0", R.drawable.sample_image1));
        mainGridAdapter.addItem(new MainGridItem("1.가타쯔무리", "5.0", R.drawable.sample_image1));
        mainGridAdapter.addItem(new MainGridItem("1.가타쯔무리", "5.0", R.drawable.sample_image1));
        mainGridAdapter.addItem(new MainGridItem("1.가타쯔무리", "5.0", R.drawable.sample_image1));
        mainGridAdapter.addItem(new MainGridItem("1.가타쯔무리", "5.0", R.drawable.sample_image1));
        mainGridAdapter.addItem(new MainGridItem("1.가타쯔무리", "5.0", R.drawable.sample_image1));
        mainGridAdapter.addItem(new MainGridItem("1.가타쯔무리", "5.0", R.drawable.sample_image1));

        //어텝터 설정
        mainGridView.setAdapter(mainGridAdapter);

        //그리드뷰 리스너
        mainGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Sickdang_Jeongbo.class);
                startActivity(intent);
            }
        });



    }
}