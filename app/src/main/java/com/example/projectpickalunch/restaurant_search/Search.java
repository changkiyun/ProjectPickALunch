package com.example.projectpickalunch.restaurant_search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
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

    GridView g_view = null;

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
        g_view = findViewById(R.id.g_view);
        g_view.setAdapter(adapter);
        g_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent restorant_information = new Intent(getApplicationContext(), Sickdang_Jeongbo.class);
                startActivity(restorant_information);
            }
        });


        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.gata1),
                "가타쯔무리") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.gata2),
                "Bryan Adams") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.gata3),
                "Eric Clapton") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.gata4),
                "Gary Moore") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.gata5),
                "Helloween") ;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.gata6),
                "Adele") ;

        //입력받은 값 filter처리해서 자동완성 기능
        EditText editTextFilter = (EditText)findViewById(R.id.editTextFilter) ;
        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                String filterText = edit.toString() ;
                ((ListViewAdapter)g_view.getAdapter()).getFilter().filter(filterText) ;
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

