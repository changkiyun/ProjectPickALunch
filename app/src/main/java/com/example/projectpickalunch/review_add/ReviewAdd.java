package com.example.projectpickalunch.review_add;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectpickalunch.R;
import com.example.projectpickalunch.login_api.KakaoLoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.willy.ratingbar.ScaleRatingBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReviewAdd extends AppCompatActivity {
    //파이어베이스
    private final FirebaseDatabase root = FirebaseDatabase.getInstance(); //파이어베이스 Root
    private final DatabaseReference restaurantDatabase = root.getReference("식당");   //식당 db
    private final DatabaseReference restaurantReference = root.getReference().child("식당"); //식당 db Child
    private final FirebaseStorage storage = FirebaseStorage.getInstance();  //스토리지

    //뷰 선언
    ImageButton review_add_return_button;
    ScaleRatingBar review_ratingBar;
    EditText review_text;
    Button review_add_button;
    ImageButton reviewImageAddButton;
    ConstraintLayout noImageLayout;
    RecyclerView addReviewImageRecyclerView;

    //가게이름, 유저 정보
    String restaurantName;

    //전체 리뷰 평점을 받아올 리스트
    ArrayList<String> scoreList = new ArrayList<String>();

    //TODO : 삭제예정 테스트 뷰
    EditText testEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_add);

        Intent intent = getIntent();
        restaurantName = intent.getStringExtra("restorant_name");

        review_ratingBar = findViewById(R.id.review_ratingBar);
        review_text = findViewById(R.id.review_text);
        noImageLayout = findViewById(R.id.noImage);
        addReviewImageRecyclerView = findViewById(R.id.addReviewImageRecyclerView);
        reviewImageAddButton = findViewById(R.id.review_image_add_button);
        review_add_button = findViewById(R.id.review_add_button);
        review_add_return_button = findViewById(R.id.review_add_return_button);

        //TODO : 삭제예정
        testEdit = findViewById(R.id.editTestName);
        testEdit.setText(KakaoLoginActivity.userNickName);

        //이미지 추가 버튼

        //리뷰추가 버튼
        review_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HHmmss");
                Date now = new Date();
                ReviewItem reviewItem = new ReviewItem(
                        testEdit.getText().toString(),                  //유저이름
                        review_text.getText().toString(),               //리뷰 본문
                        Float.toString(review_ratingBar.getRating()),   //점수
                        dateFormat.format(now)                          //등록시간
                );
                addReview(reviewItem);
                setAvgScore();
                finish();
            }
        });

        //돌아가기버튼
        review_add_return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void addReview(ReviewItem reviewItem){
        restaurantReference.child(restaurantName).child("restorant_review").child(KakaoLoginActivity.userNickName).setValue(reviewItem);
    }

    public void setAvgScore() {
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
}