package com.example.projectpickalunch.menu_picker.menu_picker_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.projectpickalunch.R;
import com.example.projectpickalunch.menu_picker.MenuPicker;

import java.util.ArrayList;

public class MenuPickerAfterSelectedFragment extends Fragment {

    ArrayList<MenuPickerItem> menuPickerItems;
    GridView menuPickerGridView;
    private static MenuPickerAdapter menuPickerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu_picker_after_selected_fragment, container, false);

        menuPickerItems = new ArrayList<MenuPickerItem>();
        menuPickerItems.add(new MenuPickerItem("1. 고릴라떡볶이", "5.0", R.drawable.sample_image2));
        menuPickerItems.add(new MenuPickerItem("1. 가타쯔무리", "5.0", R.drawable.sample_image1));
        menuPickerItems.add(new MenuPickerItem("1. 가타쯔무리", "5.0", R.drawable.sample_image1));
        menuPickerItems.add(new MenuPickerItem("1. 가타쯔무리", "5.0", R.drawable.sample_image1));
        menuPickerItems.add(new MenuPickerItem("1. 가타쯔무리", "5.0", R.drawable.sample_image1));
        menuPickerItems.add(new MenuPickerItem("1. 가타쯔무리", "5.0", R.drawable.sample_image1));


        menuPickerGridView = (GridView) rootView.findViewById(R.id.menu_picker_grid_view);
        menuPickerAdapter = new MenuPickerAdapter(getContext(), menuPickerItems);
        menuPickerGridView.setAdapter(menuPickerAdapter);
        menuPickerGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),"테스트",Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;

    }
}


