package com.example.projectpickalunch.menu_picker.menu_picker_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.projectpickalunch.R;
import com.example.projectpickalunch.restorant_info.Sickdang_Jeongbo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuPickerAfterSelectedFragment extends Fragment {

    GridView menuPickerGridView;
    private MenuPickerAdapter menuPickerAdapter;

    //Firebase 데이터베이스 변수
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    ArrayList<MenuPickerItem> arrayList;

    //랜덤 추천 알고리즘
    ArrayList<MenuPickerItem> pickedMenuList; //카테고리 선택된 식당 리스트
    ArrayList<MenuPickerItem> keywordPickedList;
    CheckBox kFoodButton, jFoodButton, cFoodButton, aFoodButton, fastFoodButton, etcFoodButton, EjrButton, xkdButton, ghlButton;
    CheckBox aozhaButton, emsemsButton, zkfzkfButton, djfzmsButton, rhthButton, ekfzhaButton, tldtldButton, RnejrButton,
            qktkrButton, rksvusButton, wjfuaButton, tldnjsButton;
    CheckBox[] checkBoxes = {aozhaButton, emsemsButton, zkfzkfButton, djfzmsButton, rhthButton, ekfzhaButton, tldtldButton, RnejrButton,
            qktkrButton, rksvusButton, wjfuaButton, tldnjsButton};
    ImageButton menuPickerApplyButton;
    String[] category = {"밥", "면", "찌개", "튀김", "빵", "구이", "떡", "빵", "회"};
    String[] keyword = {"매콤함", "든든함", "칼칼함", "얼큰함", "고소함", "달콤함", "싱싱함", "꾸덕함", "바삭함", "간편함", "가성비", "시원함"};
    int keywordCount = 0;

    //그리드 아이템 별로 다른 정보를 표시하기위한 String형 ArrayList
    ArrayList<String> itemname;

    //Fragment
    FragmentManager menuPickerFragmentManager;
    FragmentTransaction menuPickerFragmentTransaction;

    String key3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu_picker_after_selected_fragment, container, false);
        View parentView = inflater.inflate(R.layout.menu_picker, container, false);

        //Bundle값 받아오기
        Bundle bundle = getArguments();
        boolean[] selectCategoryCheck = bundle.getBooleanArray("selectCategoryCheck");
        boolean[] selectKeywordCheck = bundle.getBooleanArray("selectKeywordCheck");

        //카테고리버튼 인플레이팅
        kFoodButton = (CheckBox) parentView.findViewById(R.id.k_food_button);
        jFoodButton = (CheckBox) parentView.findViewById(R.id.j_food_button);
        cFoodButton = (CheckBox) parentView.findViewById(R.id.c_food_button);
        aFoodButton = (CheckBox) parentView.findViewById(R.id.a_food_button);
        fastFoodButton = (CheckBox) parentView.findViewById(R.id.fast_food_button);
        etcFoodButton = (CheckBox) parentView.findViewById(R.id.etc_food_button);
        EjrButton = (CheckBox) parentView.findViewById(R.id.EjrButton);
        xkdButton = (CheckBox) parentView.findViewById(R.id.xkdButton);
        ghlButton = parentView.findViewById(R.id.ghlButton);

        //키워드 버튼 인플레이팅
        aozhaButton = (CheckBox) parentView.findViewById(R.id.aozhaButton);
        emsemsButton = (CheckBox) parentView.findViewById(R.id.emsemsButton);
        zkfzkfButton = (CheckBox) parentView.findViewById(R.id.zkfzkfButton);
        djfzmsButton = (CheckBox) parentView.findViewById(R.id.djfzmsButton);
        rhthButton = (CheckBox) parentView.findViewById(R.id.rhthButton);
        ekfzhaButton = (CheckBox) parentView.findViewById(R.id.ekfzhaButton);
        tldtldButton = (CheckBox) parentView.findViewById(R.id.tldtldButton);
        RnejrButton = (CheckBox) parentView.findViewById(R.id.RnejrButton);
        qktkrButton = (CheckBox) parentView.findViewById(R.id.qktkrButton);
        rksvusButton = (CheckBox) parentView.findViewById(R.id.rksvusButton);
        wjfuaButton = (CheckBox) parentView.findViewById(R.id.wjfuaButton);
        tldnjsButton = (CheckBox) parentView.findViewById(R.id.tldnjsButton);

//        menuPickerApplyButton = parentView.findViewById(R.id.menuPickerApplyButton);

        //Firebase
        arrayList = new ArrayList<>();
        pickedMenuList = new ArrayList<>(); // 카테고리 선택된 식당 리스트
        keywordPickedList = new ArrayList<>(); // 카테고리 + 키워드 선택된 식당 리스트
        itemname = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("식당");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MenuPickerItem menuPickerItem = snapshot.getValue(MenuPickerItem.class);
                    arrayList.add(menuPickerItem);
                }
                // 카테고리의 개수만큼 반복
                for (int i = 0; i < selectCategoryCheck.length; i++) {
                    if (selectCategoryCheck[i]) {
                        //arrayList 식당 갯수만큼 반복
                        for (int j = 0; j < arrayList.size(); j++) {
                            if (arrayList.get(j).getRestorant_category().equals(category[i])) {
                                pickedMenuList.add(arrayList.get(j));
                            }
                        }
                    }
                }

                if (selectKeywordCheck[0] || selectKeywordCheck[1] || selectKeywordCheck[2] || selectKeywordCheck[3] || selectKeywordCheck[4] ||
                        selectKeywordCheck[5] || selectKeywordCheck[6] || selectKeywordCheck[7] ||
                        selectKeywordCheck[8] || selectKeywordCheck[9] || selectKeywordCheck[10] || selectKeywordCheck[11]) {
                    for (int i = 0; i < selectKeywordCheck.length; i++) {
                        if (selectKeywordCheck[i]) {
//                            keywordPickedList.clear();
                            for(int k=0; k <pickedMenuList.size(); k++) {
                                String restorantName = pickedMenuList.get(k).getRestorant_name();
                                for (DataSnapshot snapshot : dataSnapshot.child(restorantName).child("restorant_review").getChildren()) {
                                    for (DataSnapshot snapshot1 : snapshot.child("menu_review").getChildren()) { //menu_review 하위 메뉴1, 메뉴2 등
                                        for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                                            key3 = snapshot2.getKey();
                                            if (key3.equals(keyword[i])) {
                                                keywordPickedList.add(pickedMenuList.get(k));
                                                break;
                                            }
                                        }
                                        if (keywordPickedList.size() > keywordCount) {
                                            break;
                                        }
                                    }
                                    if (keywordPickedList.size() > keywordCount) {
                                        keywordCount++;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }


                for (int i = 0; i < pickedMenuList.size(); i++) {
                    //아이템 별 다른 식당 출력을 위한 식당이름 입력
                    itemname.add(arrayList.get(i).getRestorant_name());
                }

                menuPickerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MenuPicker", String.valueOf(error.toException()));
            }
        });

        menuPickerGridView = (GridView) rootView.findViewById(R.id.menu_picker_grid_view);
        if (selectKeywordCheck[0] || selectKeywordCheck[1] || selectKeywordCheck[2] || selectKeywordCheck[3] || selectKeywordCheck[4] ||
                selectKeywordCheck[5] || selectKeywordCheck[6] || selectKeywordCheck[7] ||
                selectKeywordCheck[8] || selectKeywordCheck[9] || selectKeywordCheck[10] || selectKeywordCheck[11]) {
            menuPickerAdapter = new MenuPickerAdapter(rootView.getContext(), keywordPickedList);
        } else {
            menuPickerAdapter = new MenuPickerAdapter(rootView.getContext(), pickedMenuList);
        }
        menuPickerGridView.setAdapter(menuPickerAdapter);

        //그리드뷰 아이템 리스너
        menuPickerGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), itemname.get(i), Toast.LENGTH_SHORT).show();
                Intent restorant_information = new Intent(getContext(), Sickdang_Jeongbo.class);
                restorant_information.putExtra("itemname.get(i)", itemname.get(i));
                startActivity(restorant_information);
            }
        });

        menuPickerGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        return rootView;

    }
}


