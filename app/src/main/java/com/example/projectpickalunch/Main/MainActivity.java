package com.example.projectpickalunch.Main;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.projectpickalunch.R;
import com.example.projectpickalunch.menu_picker.MenuPicker;
import com.example.projectpickalunch.restaurant_search.Search;
import com.example.projectpickalunch.restorant_info.Sickdang_Jeongbo;
import com.example.projectpickalunch.user_information.UserInformationAfterConfirm;
import com.example.projectpickalunch.user_information.UserInformationBeforeConfirm;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    //내 정보 전환버튼
    ImageButton userInfoButton;
    public static boolean confirmCheck = false; //학생 인증 완료여부 변수

    //메뉴피커 전환버튼
    Button menuPicker;

    //FireBase
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<MainGridItem> arrayList;
    MainGridAdapter mainGridAdapter;
    GridView mainGridView;
    MainGridItem mainGridItem;


    //식당이름 어레이
    ArrayList<String> itemname;
    //식당평점 어레이
    ArrayList<MainGridItem> itemScrore=  new ArrayList<>(); // 점수어레이

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //메인화면 그리드뷰
        mainGridView = (GridView) findViewById(R.id.main_grid_view);

        //Firebase
        arrayList = new ArrayList<>();
        itemname = new ArrayList<>();

        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference("식당");

        Log.d("mytest", "firebase 시작");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear(); //기존 배열리스트 초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){ //반복문으로 데이터 리스트를 추출
                    MainGridItem mainGridItem = snapshot.getValue(MainGridItem.class); //MainGridItem 객체에 데이터 담는다
                    arrayList.add(mainGridItem); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                    itemname.add(mainGridItem.getRestorant_name()); // 식당이름
                }
                Log.d("mytest", "솔팅전");

//                arrayList.sort(new Comparator<MainGridItem>() {
//                    @Override
//                    public int compare(MainGridItem mainGridItem0, MainGridItem mainGridItem1) {
//                        Float item0 = Float.valueOf(mainGridItem0.getRestorant_score());
//                        Float item1 = Float.valueOf(mainGridItem1.getRestorant_score());
//
//                        if(item0 == item1) return 0;
//                        else if (item0 < item1) return 1;
//                        else return -1;
//                    }
//                });

                Log.d("mytest", "솔팅후");

                for(int i=0; i<arrayList.size(); i++){
                    Log.d("mytest", "for1");
                    //itemScrore.add(arrayList.get(i));
                }
                Log.d("mytest", "for후");
                mainGridAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", String.valueOf(error.toException()));
            }
        });

        mainGridAdapter = new MainGridAdapter(arrayList,this);
        mainGridView.setAdapter(mainGridAdapter);


        //식당 상세정보 액티비티
        //그리드뷰 리스너
        mainGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),itemname.get(i), Toast.LENGTH_SHORT).show();
                Intent restorant_information = new Intent(getApplicationContext(), Sickdang_Jeongbo.class);
                restorant_information.putExtra("itemname.get(i)",itemname.get(i));
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



        //검색 버튼
        ImageButton searchButton = (ImageButton) findViewById(R.id.serchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent search = new Intent(getApplicationContext(), Search.class);
                startActivity(search);
            }
        });

    }


}