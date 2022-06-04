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
        ImageButton btnReturn = (ImageButton) findViewById(R.id.returnBtn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
         //리뷰 텍스트
        final ArrayList<String> reviewWriten = new ArrayList<String>();
        ListView list1 = (ListView) findViewById(R.id.listView1);
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,reviewWriten);
        list1.setAdapter(adapter1);
        final EditText reviewWrite = (EditText) findViewById(R.id.reviewWrite);

        //리뷰 평점
        final ArrayList<String> reviewRegis = new ArrayList<String>();
        ListView list2 = (ListView) findViewById(R.id.listView2);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,reviewRegis){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView)view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.RED);
                return view;
            }
        };;
        list2.setAdapter(adapter2);
        RatingBar rB = (RatingBar) findViewById(R.id.ratingBar);
        float rate = rB.getRating();
        Button btnRegis = (Button) findViewById(R.id.reviewRegis);
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewWriten.add("신준한 : " + reviewWrite.getText().toString());
                reviewRegis.add("" + rB.getRating());
                adapter1.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
            }
        });

    }

    private void addItem(Drawable icon) {
        RecyclerViewItem item = new RecyclerViewItem();
        item.setIcon(icon);
        mList.add(item);
    }
}
