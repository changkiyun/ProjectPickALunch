package com.example.projectpickalunch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {
    private GridView m_grid;
    private MainGridAdapter m_gridAdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_grid = (GridView)findViewById(R.id.grid_test);
        m_gridAdt = new MainGridAdapter(getApplicationContext() );

        for (int i = 0 ; i < 100 ; i++ ) {
            String strNo = "Num : " + i;
            m_gridAdt.setItem(strNo);
        }
    }
}