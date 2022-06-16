package com.example.projectpickalunch.menu_picker;

import static com.example.projectpickalunch.Main.MainActivity.confirmCheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.projectpickalunch.R;
import com.example.projectpickalunch.menu_picker.menu_picker_fragment.MenuPickerAfterSelectedFragment;
import com.example.projectpickalunch.menu_picker.menu_picker_fragment.MenuPickerItem;
import com.example.projectpickalunch.restaurant_search.Search;
import com.example.projectpickalunch.user_information.UserInformationAfterConfirm;
import com.example.projectpickalunch.user_information.UserInformationBeforeConfirm;

import java.util.ArrayList;

public class MenuPicker extends AppCompatActivity {

    ImageButton searchButton; //검색버튼
    ImageButton userInfoButton; //내정보 버튼
    ImageButton menuPickerReturnButton; //돌아가기 버튼

    //sortingTest
    MenuPickerAfterSelectedFragment menuPickerAfterSelectedFragment = new MenuPickerAfterSelectedFragment();
    ArrayList<MenuPickerItem> pickerItems = new ArrayList<>();

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

        //sortingTest

        //적용 버튼 클릭 시 프래그먼트 교체
        ImageButton menuPickerApplyButton = (ImageButton) findViewById(R.id.menuPickerApplyButton);
        menuPickerApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchFragment();
            }
        });

        //검색 버튼
        searchButton = (ImageButton) findViewById(R.id.serchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent search = new Intent(getApplicationContext(), Search.class);
                startActivity(search);
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
    }

    //프래그먼트 변경 함수
    public void switchFragment(){
        Fragment fr;
        //Bundle로 Fragment에 값 전달을 위한 boolean변수
       boolean[] selectCategoryCheck = {kFoodButton.isChecked(),jFoodButton.isChecked(),cFoodButton.isChecked(),aFoodButton.isChecked(),fastFoodButton.isChecked(),etcFoodButton.isChecked()};

        if(kFoodButton.isChecked() || jFoodButton.isChecked() || cFoodButton.isChecked() ||
                aFoodButton.isChecked() || fastFoodButton.isChecked() || etcFoodButton.isChecked()){
            fr = new MenuPickerAfterSelectedFragment();
        }
        else{
            fr = new MenuPickerBeforeSelectedFragment();
        }

        menuPickerFragmentManager = getSupportFragmentManager();
        menuPickerFragmentTransaction = menuPickerFragmentManager.beginTransaction();
        //Bundle로 값 전달
        Bundle bundle = new Bundle();
        bundle.putBooleanArray("selectCategoryCheck", selectCategoryCheck);
        fr.setArguments(bundle);
        menuPickerFragmentTransaction.replace(R.id.menuPickerFragment, fr);
        menuPickerFragmentTransaction.commit();

    }
}

