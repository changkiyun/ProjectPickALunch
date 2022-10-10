package com.example.projectpickalunch.restorant_add;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectpickalunch.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RestorantAdd extends AppCompatActivity {
    //뷰
    EditText restorant_name_edit_text, restorant_address_edit_text, restorant_tel_edit_text;
    ImageButton restorant_add_return_button;
    Button restorant_add_registration_button;

    //RealtimeDatabase 선언
    private DatabaseReference RestorantAddReference;

    //앱에서 받아올 식당정보
    public String restorant_name;
    public String restorant_address;
    public String restorant_tel;
    public String restorant_category;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restorant_add);

        //뷰 인플레이팅
        restorant_name_edit_text = findViewById(R.id.restorant_name_edit_text);
        restorant_address_edit_text = findViewById(R.id.restorant_address_edit_text);
        restorant_tel_edit_text = findViewById(R.id.restorant_tel_edit_text);
        restorant_add_registration_button = findViewById(R.id.restorant_add_registration_button);

        restorant_category = "카테고리테스트";

        //RealtimeDatabase 초기화
        RestorantAddReference = FirebaseDatabase.getInstance().getReference();

        restorant_add_registration_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //앱에서 식당 정보 받아오기
                restorant_name = restorant_name_edit_text.getText().toString();
                restorant_address = restorant_address_edit_text.getText().toString();
                restorant_tel = restorant_tel_edit_text.getText().toString();
                //DB에 올릴 Map 선언
                Map<String, Object> toRestornatAddInformation = new HashMap<>();
                //RestorantAddInformation 객체 생성
                RestorantAddInformation restorantAddInformation = new RestorantAddInformation(restorant_name, restorant_address, restorant_tel, restorant_category);
                //Map객체 toRestorantAddInformation에 RestorantAddInformation에서 생성한 Map객체 저장
                toRestornatAddInformation = restorantAddInformation.toMap();
                //"식당" restorant_name으로 참조한 위치 아래 정보 저장
                RestorantAddReference.child("식당").child(restorant_name).updateChildren(toRestornatAddInformation);
            }
        });

        //돌아가기버튼
        restorant_add_return_button = findViewById(R.id.restorant_add_return_button);
        restorant_add_return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
