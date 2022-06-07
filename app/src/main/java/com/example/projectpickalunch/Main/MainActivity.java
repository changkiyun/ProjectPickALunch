package com.example.projectpickalunch.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.projectpickalunch.R;
import com.example.projectpickalunch.menu_picker.MenuPicker;
import com.example.projectpickalunch.menu_picker.MenuPickerAfterSelectedFragment;
import com.example.projectpickalunch.menu_picker.MenuPickerBeforeSelectedFragment;
import com.example.projectpickalunch.restorant_info.Sickdang_Jeongbo;
import com.example.projectpickalunch.user_information.UserInformationAfterConfirm;
import com.example.projectpickalunch.user_information.UserInformationBeforeConfirm;
import com.example.projectpickalunch.user_information.UserInformationConfirm;

public class MainActivity extends AppCompatActivity {
    //그리드뷰
    Integer[] sampleImage = {R.drawable.sample_image1, R.drawable.sample_image2, R.drawable.sample_image3,
            R.drawable.sample_image4, R.drawable.sample_image5, R.drawable.sample_image6, R.drawable.sample_image1, R.drawable.sample_image2, R.drawable.sample_image3,
            R.drawable.sample_image4, R.drawable.sample_image5, R.drawable.sample_image6, R.drawable.sample_image1, R.drawable.sample_image2, R.drawable.sample_image3,
            R.drawable.sample_image4, R.drawable.sample_image5, R.drawable.sample_image6};

    //내 정보 전환버튼
    ImageButton userInfoButton;
    final boolean confirmCheck = false; //학생 인증 완료여부 변수

    //메뉴피커 전환버튼
    Button menuPicker;
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
        mainGridAdapter.addItem(new MainGridItem("1.가타쯔무리", "5.0", R.drawable.sample_image2));
        mainGridAdapter.addItem(new MainGridItem("1.가타쯔무리", "5.0", R.drawable.sample_image2));
        mainGridAdapter.addItem(new MainGridItem("1.가타쯔무리", "5.0", R.drawable.sample_image2));
        mainGridAdapter.addItem(new MainGridItem("1.가타쯔무리", "5.0", R.drawable.sample_image2));
        mainGridAdapter.addItem(new MainGridItem("1.가타쯔무리", "5.0", R.drawable.sample_image2));

        //어텝터 설정
        mainGridView.setAdapter(mainGridAdapter);

        //식당 상세정보 액티비티
        //그리드뷰 리스너
        mainGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent restorant_information = new Intent(getApplicationContext(), Sickdang_Jeongbo.class);
                startActivity(restorant_information);
            }
        });


        //내 정보 액티비티
        //내 정보 인증유무에 따라 출력화면이 다름
        userInfoButton = (ImageButton) findViewById(R.id.userInfoButton);
        userInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (confirmCheck == true) {
                    Intent user_information_after_confirm = new Intent(getApplicationContext(), UserInformationAfterConfirm.class);
                    startActivity(user_information_after_confirm);
                } else {
                    Intent user_information_before_confirm = new Intent(getApplicationContext(), UserInformationBeforeConfirm.class);
                    startActivity(user_information_before_confirm);
                }

            }
        });

        //메뉴피커 액티비티
        menuPicker = (Button) findViewById(R.id.menuPicker);
        menuPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu_picker = new Intent(getApplicationContext(), MenuPicker.class);
                startActivity(menu_picker);
            }
        });

    }
}