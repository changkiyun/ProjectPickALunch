package com.example.projectpickalunch.restaurant_search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.projectpickalunch.R;
import com.example.projectpickalunch.restorant_info.Sickdang_Jeongbo;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Search extends AppCompatActivity {
    ListView listview = null ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        //뒤로가기
        ImageButton btnReturn = (ImageButton) findViewById(R.id.returnBtn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent restorant_information = new Intent(getApplicationContext(), Sickdang_Jeongbo.class);
                startActivity(restorant_information);
            }
        });


        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.search_sample),
                "가타쯔무리") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.search_sample),
                "Bryan Adams") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.search_sample),
                "Eric Clapton") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.search_sample),
                "Gary Moore") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.search_sample),
                "Helloween") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.search_sample),
                "Adele") ;

        EditText editTextFilter = (EditText)findViewById(R.id.editTextFilter) ;
        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                String filterText = edit.toString() ;
                ((ListViewAdapter)listview.getAdapter()).getFilter().filter(filterText) ;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        }) ;
    }
}

