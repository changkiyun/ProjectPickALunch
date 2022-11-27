package com.example.projectpickalunch.restorant_info;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectpickalunch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FullReviewActivity extends AppCompatActivity {

    private final FirebaseDatabase root = FirebaseDatabase.getInstance();
    private final DatabaseReference restaurantReference = root.getReference().child("식당");
    private DatabaseReference detail_images;

    ImageButton returnBtn;
    ImageButton deleteBtn;
    RecyclerView imageRecyclerView;
    TextView userNameView;
    TextView userScoreView;
    TextView mainTextView;
    TextView reviewDateView;

    ArrayList<String> scoreList = new ArrayList<String>();
    ArrayList<RecyclerImageItem> imageList;
    ImageRecyclerAdapter imageAdapter;

    String userName;
    String score;
    String mainText;
    String reviewDate;
    String restaurantName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_review);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        score = intent.getStringExtra("score");
        mainText = intent.getStringExtra("mainText");
        reviewDate = intent.getStringExtra("reviewDate");
        restaurantName = intent.getStringExtra("restaurantName");

        userNameView = findViewById(R.id.userName);
        userScoreView = findViewById(R.id.userScore);
        mainTextView = findViewById(R.id.mainText);
        reviewDateView = findViewById(R.id.reviewDate);

        userNameView.setText(userName);
        userScoreView.setText(score);
        mainTextView.setText(mainText);
        reviewDateView.setText(reviewDate.substring(0, reviewDate.indexOf(":")));

        LinearLayoutManager layoutManager4 = new LinearLayoutManager(this);
        layoutManager4.setOrientation(RecyclerView.HORIZONTAL);
        imageRecyclerView = findViewById(R.id.recycler_view); //
        imageRecyclerView.setLayoutManager(layoutManager4);
        imageRecyclerView.setHasFixedSize(true);

        imageList = new ArrayList<>();

        imageAdapter = new ImageRecyclerAdapter(FullReviewActivity.this, imageList);
        imageRecyclerView.setAdapter(imageAdapter);

        detail_images = restaurantReference.child(restaurantName).child("restorant_detail_image").getRef();
        detail_images.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imageList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    RecyclerImageItem model = dataSnapshot.getValue(RecyclerImageItem.class);
                    imageList.add(model);
                }
                imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        returnBtn = findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        
        //TODO : 유저정보와 일치할경우에만 삭제 가능하도록 변경예정
        deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //삭제시 재확인 다이얼로그 출력
                AlertDialog.Builder builder = new AlertDialog.Builder(FullReviewActivity.this);
                builder.setNegativeButton("취소", null);
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //리뷰 삭제및 토스트메시지 출력
                        Toast.makeText(FullReviewActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        restaurantReference.child(restaurantName).child("restorant_review").child(userName).setValue(null);
                        getAvgScore();
                        //isNoReview();
                        finish();
                    }
                });
                final AlertDialog dlg = builder.create();
                dlg.setTitle("리뷰 삭제");
                dlg.setMessage("삭제하시겠습니까?");
                dlg.setOnShowListener( new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface args) {
                        dlg.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
                        dlg.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                    }
                });
                dlg.show();
            }
        });
    }

    public void getAvgScore() {
        restaurantReference.child(restaurantName).child("restorant_review").getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String score = dataSnapshot.child("review_rate").getValue(String.class);
                    scoreList.add(score);

                        float avgScore;
                        float totRate = 0;

                        for(int i=0; i<scoreList.size(); i++){
                            totRate += Float.parseFloat(scoreList.get(i));
                            Log.v("avgScore", scoreList.get(i));
                        }

                        avgScore = totRate/Float.parseFloat(String.valueOf(scoreList.size()));
                        restaurantReference.child(restaurantName).child("restorant_score").setValue(Float.toString(avgScore));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void isNoReview() {
        restaurantReference.child(restaurantName).child("restorant_review").getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    String score = dataSnapshot.child("review_rate").getValue(String.class);
                    scoreList.add(score);
                    Log.v("ccc", ""+scoreList);
                    if(scoreList.get(0) == null) {
                        restaurantReference.child(restaurantName).child("restorant_score").setValue("0.0");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
