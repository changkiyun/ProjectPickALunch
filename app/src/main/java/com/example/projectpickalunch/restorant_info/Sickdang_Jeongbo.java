package com.example.projectpickalunch.restorant_info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;

import com.example.projectpickalunch.R;

import java.util.ArrayList;

public class Sickdang_Jeongbo extends AppCompatActivity {
    RecyclerView mRecyclerView = null;
    RecyclerViewAdapter mAdapter = null;
    ArrayList<RecyclerViewItem> mList;
    int[] imgCount = {R.drawable.sample_image1, R.drawable.sample_image2,R.drawable.sample_image3,
            R.drawable.sample_image4,R.drawable.sample_image5, R.drawable.sample_image6};
    private Drawable mImageDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sicdangjeongbo);

        mRecyclerView = findViewById(R.id.recycler_view);
        mList = new ArrayList<>();

        mAdapter = new RecyclerViewAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        for (int i=0;i<imgCount.length;i++){
            mImageDrawable = ResourcesCompat.getDrawable(getResources(),imgCount[i], null);
            addItem(mImageDrawable);
        }

        mAdapter.notifyDataSetChanged();
        //뒤로가기

    }

    private void addItem(Drawable icon) {
        RecyclerViewItem item = new RecyclerViewItem();
        item.setIcon(icon);
        mList.add(item);
    }
}
