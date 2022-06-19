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

import com.example.projectpickalunch.Main.MainGridAdapter;
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
    ArrayList<MenuPickerItem> pickedMenuList;
    CheckBox kFoodButton, jFoodButton, cFoodButton, aFoodButton, fastFoodButton, etcFoodButton;
    ImageButton menuPickerApplyButton;
    String[] category = {"한식", "중식", "일식", "양식", "간편식", "기타"};

    //그리드 아이템 별로 다른 정보를 표시하기위한 String형 ArrayList
    ArrayList<String> itemname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu_picker_after_selected_fragment, container, false);
        View parentView = inflater.inflate(R.layout.menu_picker, container, false);

        //Bundle값 받아오기
        Bundle bundle = getArguments();
        boolean[] selectCategoryCheck = bundle.getBooleanArray("selectCategoryCheck");

        kFoodButton = (CheckBox) parentView.findViewById(R.id.k_food_button);
        jFoodButton = (CheckBox) parentView.findViewById(R.id.j_food_button);
        cFoodButton = (CheckBox) parentView.findViewById(R.id.c_food_button);
        aFoodButton = (CheckBox) parentView.findViewById(R.id.a_food_button);
        fastFoodButton = (CheckBox) parentView.findViewById(R.id.fast_food_button);
        etcFoodButton = (CheckBox) parentView.findViewById(R.id.etc_food_button);

        menuPickerApplyButton = parentView.findViewById(R.id.menuPickerApplyButton);

        //Firebase
        arrayList = new ArrayList<>();
        pickedMenuList = new ArrayList<>();
        itemname = new ArrayList<>();


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("식당");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    MenuPickerItem menuPickerItem = snapshot.getValue(MenuPickerItem.class);
                    arrayList.add(menuPickerItem);
                }


                // 카테고리 선택 알고리즘 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                // 카테고리의 개수만큼 반복
                for(int i=0; i<selectCategoryCheck.length; i++) {
                    if (selectCategoryCheck[i]) {
                        //arrayList 식당 갯수만큼 반복
                        for (int j = 0; j < arrayList.size(); j++) {
                            if (arrayList.get(j).getRestorant_category().equals(category[i])) {
                                pickedMenuList.add(arrayList.get(j));

                            }
                        }
                    }
                }

                

                for(int i=0; i<pickedMenuList.size(); i++){
                    //아이템 별 다른 식당 출력을 위한 식당이름 입력
                    itemname.add(arrayList.get(i).getRestorant_name());
                    //식당이름에 순위 출력
                    pickedMenuList.get(i).setRestorant_name(i+1 + ". " + pickedMenuList.get(i).getRestorant_name());
                }
                menuPickerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MenuPicker", String.valueOf(error.toException()));
            }
        });


        menuPickerGridView = (GridView) rootView.findViewById(R.id.menu_picker_grid_view);
        menuPickerAdapter = new MenuPickerAdapter(rootView.getContext(), pickedMenuList);

        menuPickerGridView.setAdapter(menuPickerAdapter);

        //그리드뷰 아이템 리스너
        menuPickerGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),itemname.get(i), Toast.LENGTH_SHORT).show();
                Intent restorant_information = new Intent(getContext(), Sickdang_Jeongbo.class);
                restorant_information.putExtra("itemname.get(i)",itemname.get(i));
                startActivity(restorant_information);
            }
        });

        return rootView;

    }
}


