package com.example.projectpickalunch.menu_picker;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.projectpickalunch.R;
import com.example.projectpickalunch.menu_picker.menu_picker_fragment.MenuPickerAfterSelectedFragment;

public class MenuPicker extends AppCompatActivity {

    ImageButton menuPickerReturnButton;

    //메뉴피커
    FragmentManager menuPickerFragmentManager;
    FragmentTransaction menuPickerFragmentTransaction;
    private boolean isMenuSelected = false; //메뉴선택 변수

    //카테고리 버튼
    CheckBox kFoodButton, jFoodButton, cFoodButton, aFoodButton, fastFoodButton, etcFoodButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_picker);

        kFoodButton = (CheckBox) findViewById(R.id.k_food_button);
        jFoodButton = (CheckBox) findViewById(R.id.j_food_button);
        cFoodButton = (CheckBox) findViewById(R.id.c_food_button);
        aFoodButton = (CheckBox) findViewById(R.id.a_food_button);
        fastFoodButton = (CheckBox) findViewById(R.id.fast_food_button);
        etcFoodButton = (CheckBox) findViewById(R.id.etc_food_button);


        //메인화면으로 돌아가기
        menuPickerReturnButton = (ImageButton) findViewById(R.id.menuPickerReturnButton);
        menuPickerReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //메뉴피커 프래그먼트
        menuPickerFragmentManager = getSupportFragmentManager();
        menuPickerFragmentTransaction = menuPickerFragmentManager.beginTransaction();
        menuPickerFragmentTransaction.add(R.id.menuPickerFragment, new MenuPickerBeforeSelectedFragment());
        menuPickerFragmentTransaction.commit();

        //적용 버튼 클릭 시 프래그먼트 교체
        ImageButton menuPickerApplyButton = (ImageButton) findViewById(R.id.menuPickerApplyButton);
        menuPickerApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchFragment();
            }
        });
    }

    //프래그먼트 변경 함수
    public void switchFragment(){
        Fragment fr;

        if(kFoodButton.isChecked() || jFoodButton.isChecked() || cFoodButton.isChecked() ||
                aFoodButton.isChecked() || fastFoodButton.isChecked() || etcFoodButton.isChecked()){
            fr = new MenuPickerAfterSelectedFragment();
        }
        else{
            fr = new MenuPickerBeforeSelectedFragment();
        }

        menuPickerFragmentManager = getSupportFragmentManager();
        menuPickerFragmentTransaction = menuPickerFragmentManager.beginTransaction();
        menuPickerFragmentTransaction.replace(R.id.menuPickerFragment, fr);
        menuPickerFragmentTransaction.commit();

    }

    }

