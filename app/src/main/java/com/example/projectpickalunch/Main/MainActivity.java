package com.example.projectpickalunch.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.projectpickalunch.R;
import com.example.projectpickalunch.restorant_info.Sickdang_Jeongbo;
import com.example.projectpickalunch.user_information.UserInformationAfterConfirm;
import com.example.projectpickalunch.user_information.UserInformationBeforeConfirm;

public class MainActivity extends AppCompatActivity {
    //그리드뷰

    Integer[] sampleImage = {R.drawable.sample_image1, R.drawable.sample_image2,R.drawable.sample_image3,
            R.drawable.sample_image4,R.drawable.sample_image5, R.drawable.sample_image6,R.drawable.sample_image1, R.drawable.sample_image2,R.drawable.sample_image3,
            R.drawable.sample_image4,R.drawable.sample_image5, R.drawable.sample_image6,R.drawable.sample_image1, R.drawable.sample_image2,R.drawable.sample_image3,
            R.drawable.sample_image4,R.drawable.sample_image5, R.drawable.sample_image6};

    //내 정보
    ImageButton userInfoButton;
    final boolean confirmCheck = false;

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
                Intent restorant_information = new Intent(getApplicationContext(), Sickdang_Jeongbo.class);
                startActivity(restorant_information);
            }
        });

        //내 정보 인증유무에 따라 출력화면이 다름
        userInfoButton = (ImageButton) findViewById(R.id.userInfoButton);
        userInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(confirmCheck == true) {
                    Intent user_information_after_confirm = new Intent(getApplicationContext(), UserInformationAfterConfirm.class);
                    startActivity(user_information_after_confirm);
                }
                else{
                    Intent user_information_before_confirm = new Intent(getApplicationContext(), UserInformationBeforeConfirm.class);
                    startActivity(user_information_before_confirm);
                }

            }
        });

    }
}