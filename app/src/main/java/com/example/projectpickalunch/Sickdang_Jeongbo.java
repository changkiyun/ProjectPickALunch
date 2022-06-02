package com.example.projectpickalunch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Sickdang_Jeongbo extends AppCompatActivity {
    RecyclerView mRecyclerView = null;
    RecyclerViewAdapter mAdapter = null;
    ArrayList<RecyclerViewItem> mList;
    int[] imgCount = {R.drawable.sample_image1, R.drawable.sample_image2,R.drawable.sample_image3,
            R.drawable.sample_image4,R.drawable.sample_image5, R.drawable.sample_image6};
    private Drawable mImageDrawable,mImageDrawable2,mImageDrawable3,mImageDrawable4,mImageDrawable5,mImageDrawable6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sicdangjeongbo);

        mRecyclerView = findViewById(R.id.recycler_view);
        mList = new ArrayList<>();

        mAdapter = new RecyclerViewAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));


        mImageDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.sample_image1, null);
        addItem(mImageDrawable);
        mImageDrawable2 = ResourcesCompat.getDrawable(getResources(), R.drawable.sample_image2, null);
        addItem(mImageDrawable2);
        mImageDrawable3 = ResourcesCompat.getDrawable(getResources(), R.drawable.sample_image3, null);
        addItem(mImageDrawable3);
        mImageDrawable4 = ResourcesCompat.getDrawable(getResources(), R.drawable.sample_image4, null);
        addItem(mImageDrawable4);
        mImageDrawable5 = ResourcesCompat.getDrawable(getResources(), R.drawable.sample_image5, null);
        addItem(mImageDrawable5);
        mImageDrawable6 = ResourcesCompat.getDrawable(getResources(), R.drawable.sample_image6, null);
        addItem(mImageDrawable6);




        mAdapter.notifyDataSetChanged();
        //뒤로가기
        Button btnReturn = (Button) findViewById(R.id.button);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addItem(Drawable icon) {
        RecyclerViewItem item = new RecyclerViewItem();
        item.setIcon(icon);
        mList.add(item);
    }
}
