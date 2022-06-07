package com.example.projectpickalunch.restorant_info;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectpickalunch.R;

import org.w3c.dom.Text;

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
        ImageButton btnReturn = (ImageButton) findViewById(R.id.returnBtn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
         //리뷰 텍스트
        ListView listView = (ListView) findViewById(R.id.listView);
        ListItemAdapter adapter = new ListItemAdapter();

        EditText reviewWriten =(EditText) findViewById(R.id.reviewWrite);
        String rT = reviewWriten.getText().toString();

        RatingBar rB = (RatingBar)findViewById(R.id.ratingBar);
        float rate = rB.getRating();
        String reviewScore = String.valueOf(rate);

        Button reviewRegis = (Button) findViewById(R.id.reviewRegis);
        reviewRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.addItems(new ListItem("신준한 : ", rT , reviewScore));
                listView.setAdapter(adapter);
            }
        });


    }

    private void addItem(Drawable icon) {
        RecyclerViewItem item = new RecyclerViewItem();
        item.setIcon(icon);
        mList.add(item);
    }
}
