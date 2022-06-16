package com.example.projectpickalunch.restorant_info;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.style.UpdateAppearance;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projectpickalunch.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Sickdang_Jeongbo extends AppCompatActivity {
    //상단 리사이클러뷰
    RecyclerView mRecyclerView = null;
    //상단 리사이클러뷰 어뎁터
    RecyclerViewAdapter mAdapter = null;
    //상단 리사이클러뷰 값 저장할 ArrayList
    ArrayList<RecyclerViewItem> mList;
    //리뷰에 쓰이는 리스트아이템 어뎁터
    ListItemAdapter adapter;
    //리스트뷰 값 저장할 ArrayLiset
    ArrayList<ListItem> arrayList;

    ArrayList<String> rate_array;
    //리사이클러뷰에 들어갈 이미지
    int[] imgCount = {R.drawable.gata1, R.drawable.gata2,R.drawable.gata3,
            R.drawable.gata4,R.drawable.gata5, R.drawable.gata6};
    //이미지 저장하는 Drawble
    private Drawable mImageDrawable;
    //파이어베이스 연동
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sicdangjeongbo);
        //인텐트 받아오기 가게 이름
        Intent intent = getIntent();
        String sickdang_title = intent.getStringExtra("itemname.get(i)");

        //리사이클러 뷰로 가게 상세 사진 보여주기
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

        //뒤로가기 버튼
        ImageButton btnReturn = (ImageButton) findViewById(R.id.returnBtn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //가게 이름
        TextView sickdanTitle = (TextView)findViewById(R.id.sicdangTitle);
        sickdanTitle.setText(sickdang_title); //여기에 받아온 가게 이름 입력


         //리뷰 텍스트
        ListView listView = (ListView) findViewById(R.id.listView);
        EditText reviewWriten =(EditText) findViewById(R.id.reviewWrite);
        RatingBar rB = (RatingBar)findViewById(R.id.ratingBar);

        //파이어 베이스에서 리뷰 가져와서 리스트뷰에 출력하기
        arrayList = new ArrayList<>();
        rate_array = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        //데베에서 리뷰 가져오기
        databaseReference.child("식당").child(sickdang_title).child("restorant_review").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    ListItem listItem = snapshot.getValue(ListItem.class);
                    arrayList.add(listItem); // 리뷰 가져옴
                    Review_Rate listItem1 = snapshot.getValue(Review_Rate.class);
                    rate_array.add(listItem1.getReview_rate());

                    String[] rate_string = new String[rate_array.size()];
                    rate_string = rate_array.toArray(rate_string);

                    float rate_hap=0;
                    for(int i=0;i<rate_string.length;i++){
                        rate_hap += Float.parseFloat(rate_string[i]);
                    }
                    float rate_avg = rate_hap/Float.parseFloat(String.valueOf(rate_array.size()));
                    addScore(String.valueOf(rate_avg));
                    TextView pyeongJeom = (TextView)findViewById(R.id.pyeongJeom);
                    pyeongJeom.setText(String.valueOf(rate_avg));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력

            }

        });
        adapter = new ListItemAdapter(arrayList, this);
        listView.setAdapter(adapter);



        //float rate_avg = rate_hap/Float.parseFloat(String.valueOf(rate_array.size()));
        //Toast.makeText(getApplicationContext(), rate_array.get(0), Toast.LENGTH_SHORT).show();

        //가게 평점
         //여기에 받아온 가게 평점 입력



        //리뷰 평점과 리뷰 내용 작성 후 리뷰등록 버튼 클릭시 하단 리스트 뷰에 리뷰 등록
        Button reviewRegis = (Button) findViewById(R.id.reviewRegis);
        reviewRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.addItems(new ListItem("신준한", reviewWriten.getText().toString() , "" + rB.getRating()));
                addReview(reviewWriten.getText().toString(),"신준한", String.valueOf(rB.getRating()));
                listView.setAdapter(adapter);
            }
        });




    }

    //리사이클러 뷰에 이미지 넣기
    private void addItem(Drawable icon) {
        RecyclerViewItem item = new RecyclerViewItem();
        item.setIcon(icon);
        mList.add(item);
    }
    //리뷰 파이어베이스에 저장
    public void addReview(String restaurant_review, String user_name, String review_rate){
        Intent intent = getIntent();
        String sickdang_title = intent.getStringExtra("itemname.get(i)");
        ListItem listItem = new ListItem(user_name,restaurant_review,review_rate);
        databaseReference.child("식당").child(sickdang_title).child("restorant_review").child(user_name).setValue(listItem);
    }
    public void addScore(String restorant_score){
        Intent intent = getIntent();
        String sickdang_title = intent.getStringExtra("itemname.get(i)");
        UpdateScore updateScore = new UpdateScore(restorant_score);
        databaseReference.child("식당").child(sickdang_title).child("restorant_score").setValue(restorant_score);
    }
}
