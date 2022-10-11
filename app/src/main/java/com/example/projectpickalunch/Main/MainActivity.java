package com.example.projectpickalunch.Main;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectpickalunch.R;
//import com.example.projectpickalunch.loginpage.UserModel;
import com.example.projectpickalunch.menu_picker.MenuPicker;
import com.example.projectpickalunch.restaurant_search.Search;
import com.example.projectpickalunch.restorant_add.RestorantAdd;
import com.example.projectpickalunch.restorant_info.ImageRecyclerAdapter;
import com.example.projectpickalunch.restorant_info.Sickdang_Jeongbo;
import com.example.projectpickalunch.user_information.NickName;
import com.example.projectpickalunch.user_information.UserInformationAfterConfirm;
import com.example.projectpickalunch.user_information.UserInformationBeforeConfirm;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    ImageButton searchButton; //검색버튼
    ImageButton userInfoButton;//내 정보 전환버튼
    public static String confirmChecked; //학생 인증 완료여부 변수

    //메뉴피커 전환버튼
    Button menuPicker;
    FloatingActionButton mainFloatingButton;

    //FireBase
    private FirebaseDatabase database;
    public static DatabaseReference databaseReference;

    ArrayList<RankingGridItem> arrayList;
    RankingGridAdapter rankingGridAdapter;
    RecyclerView rankingGridView;

    //그리드 아이템 별로 다른 정보를 표시하기위한 String형 ArrayList
    ArrayList<String> itemname;

    //ArrayList<UserModel> permission;

    //test
    String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        confirmChecked = "1";

        //메인화면 그리드뷰
        rankingGridView = findViewById(R.id.main_grid_view);

        getHashKey();

        //Firebase
        arrayList = new ArrayList<>();
        itemname = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("식당");
        //YJW
        rankingGridAdapter = new RankingGridAdapter(this, arrayList);
        rankingGridView.setAdapter(rankingGridAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rankingGridView.setLayoutManager(layoutManager);
        rankingGridView.setHasFixedSize(true);
        rankingGridView.setItemViewCacheSize(100);

        databaseReference.orderByChild("restorant_score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear(); //기존 배열리스트 초기화
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    RankingGridItem rankingGridItem = dataSnapshot.getValue(RankingGridItem.class);
                    arrayList.add(rankingGridItem);
                }
                Collections.reverse(arrayList);
                rankingGridAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", String.valueOf(error.toException()));
            }
        });

        FirebaseDatabase confirmDatabase =  FirebaseDatabase.getInstance();
//        permission = new ArrayList<>();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String uid = user.getUid();
        databaseReference = confirmDatabase.getReference("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                permission.clear();
//                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
//                    UserModel userModel = snapshot.getValue(UserModel.class);
//                    permission.add(userModel);
//
//                }
//                for(int i=0; i<permission.size(); i++){
//                    confirmChecked = String.valueOf(permission.get(i).getConfirmCheck());
//                }

                //내 정보 액티비티
                //내 정보 인증유무에 따라 출력화면이 다름
                userInfoButton = (ImageButton) findViewById(R.id.userInfoButton);
                userInfoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (confirmChecked.equals("1")) {
                            Intent user_information_after_confirm = new Intent(getApplicationContext(), UserInformationAfterConfirm.class);
                            startActivity(user_information_after_confirm);
                        } else {
                            Intent user_information_before_confirm = new Intent(getApplicationContext(), UserInformationBeforeConfirm.class);
                            startActivity(user_information_before_confirm);
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
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
        searchButton = (ImageButton) findViewById(R.id.serchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent search = new Intent(getApplicationContext(), Search.class);
                startActivity(search);
            }
        });

        //식당 추가버튼 (플로팅 액션 버튼)
        mainFloatingButton = findViewById(R.id.main_floating_button);
        mainFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent restorantAdd = new Intent(getApplicationContext(), RestorantAdd.class);
                startActivity(restorantAdd);
            }
        });

    }



    //해시 키 구하기
    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }


}