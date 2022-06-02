package com.example.projectpickalunch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {
    private GridView m_grid;
    private MainGridAdapter m_gridAdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //오늘 뭐먹지 버튼 테스트용 임시 클릭리스너
        Button whatToEat = (Button) findViewById(R.id.menu_picker);
        whatToEat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Sickdang_Jeongbo.class);
                startActivity(intent);
            }
        });

        m_grid = (GridView)findViewById(R.id.grid_test);
        m_gridAdt = new MainGridAdapter(getApplicationContext() );

        for (int i = 0 ; i < 100 ; i++ ) {
            String strNo = "Num : " + i;
            m_gridAdt.setItem(strNo);
        }
    }
}