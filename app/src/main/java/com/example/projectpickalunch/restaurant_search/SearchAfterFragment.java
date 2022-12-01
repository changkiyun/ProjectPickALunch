package com.example.projectpickalunch.restaurant_search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projectpickalunch.R;
import com.example.projectpickalunch.restorant_info.Sickdang_Jeongbo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchAfterFragment extends Fragment {
    GridView searchGridView;
    private SearchAdapter searchAdapter;

    //Firebase 데이터베이스 변수
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    ArrayList<SearchItem> arrayList;

    //그리드 아이템 별로 다른 정보를 표시하기위한 String형 ArrayList
    ArrayList<String> itemname;
    ArrayList<SearchItem> searchResult;
    ArrayList<String> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_after_fragment, container, false);
        View parentView = inflater.inflate(R.layout.search, container, false);

        //Bundle값 받아오기
        Bundle bundle = getArguments();
        list = new ArrayList<>();
        list = bundle.getStringArrayList("selectedRestorantName");

        //Firebase
        arrayList = new ArrayList<>();
        itemname = new ArrayList<>();
        searchResult = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("식당");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    SearchItem searchItem = snapshot.getValue(SearchItem.class);
                    arrayList.add(searchItem);
                }

                for(int i=0; i<list.size(); i++){
                    for(int j=0; j<arrayList.size(); j++){
                        if(arrayList.get(j).getRestorant_name().equals(list.get(i))){
                            searchResult.add(arrayList.get(j));
                            break;
                        }
                    }

                }
                for(int i=0; i<searchResult.size(); i++){
                    //아이템 별 다른 식당 출력을 위한 식당이름 입력
                    itemname.add(arrayList.get(i).getRestorant_name());
                }
                searchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("search", String.valueOf(error.toException()));
            }
        });

        searchGridView = rootView.findViewById(R.id.search_after_gridView);
        searchAdapter = new SearchAdapter(rootView.getContext(), searchResult);

        searchGridView.setAdapter(searchAdapter);

        searchGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("fragTest", "touched");
                Toast.makeText(getContext(),itemname.get(i), Toast.LENGTH_SHORT).show();
                Intent restorant_information = new Intent(getContext(), Sickdang_Jeongbo.class);
                restorant_information.putExtra("restorant_name",itemname.get(i));
                startActivity(restorant_information);
            }
        });

        return rootView;
    }
}
