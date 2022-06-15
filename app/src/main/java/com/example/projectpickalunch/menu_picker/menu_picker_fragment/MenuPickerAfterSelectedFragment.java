package com.example.projectpickalunch.menu_picker.menu_picker_fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.projectpickalunch.R;
import com.example.projectpickalunch.menu_picker.MenuPicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuPickerAfterSelectedFragment extends Fragment {

    GridView menuPickerGridView;
    private static MenuPickerAdapter menuPickerAdapter;

    //Firebase 데이터베이스 변수
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<MenuPickerItem> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu_picker_after_selected_fragment, container, false);

        arrayList = new ArrayList<>();
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
                menuPickerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MenuPicker", String.valueOf(error.toException()));
            }
        });
        menuPickerGridView = (GridView) rootView.findViewById(R.id.menu_picker_grid_view);
        menuPickerAdapter = new MenuPickerAdapter(rootView.getContext(), arrayList);

        menuPickerGridView.setAdapter(menuPickerAdapter);


//        menuPickerItems = new ArrayList<MenuPickerItem>();
//        menuPickerItems.add(new MenuPickerItem("1. 가타쯔무리", "5.0", R.drawable.sample_image1));
//        menuPickerItems.add(new MenuPickerItem("1. 가타쯔무리", "5.0", R.drawable.sample_image1));
//        menuPickerItems.add(new MenuPickerItem("1. 가타쯔무리", "5.0", R.drawable.sample_image1));
//        menuPickerItems.add(new MenuPickerItem("1. 가타쯔무리", "5.0", R.drawable.sample_image1));
//        menuPickerItems.add(new MenuPickerItem("1. 가타쯔무리", "5.0", R.drawable.sample_image1));
//
//
//        menuPickerGridView = (GridView) rootView.findViewById(R.id.menu_picker_grid_view);
//        menuPickerAdapter = new MenuPickerAdapter(getContext(), menuPickerItems);
//        menuPickerGridView.setAdapter(menuPickerAdapter);

        menuPickerGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),"테스트",Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;

    }
}


