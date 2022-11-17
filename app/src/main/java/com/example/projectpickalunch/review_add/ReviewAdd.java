package com.example.projectpickalunch.review_add;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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

import java.lang.reflect.Array;
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
    Button review_add_button;
    Button menuPlusButton;
    Button imagePlusButton;
    TextView restaurantNameTextView;
    ScaleRatingBar tasteRatingBar;
    ScaleRatingBar kindnessRatingBar;
    ScaleRatingBar cleanRatingBar;
    ScaleRatingBar priceRatingBar;
    RecyclerView menuDetailReviewRecyclerView;
    RecyclerView imageReviewRecyclerView;
    EditText commentEditText;

    //평가 내용,점수
    private String userName;
    private float tasteRate;
    private float kindRate;
    private float cleanRate;
    private float priceRate;
    private float avgRate;
    private String comment;
    private ArrayList<String> imgUris;
    private int menuReviewCount = 0;

    //이미지, 메뉴 평가 ArrayList


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
        userName = KakaoLoginActivity.userNickName;

        review_add_return_button = findViewById(R.id.review_add_return_button);
        review_add_button = findViewById(R.id.review_add_button);
        menuPlusButton = findViewById(R.id.menuPlusButton);
        imagePlusButton = findViewById(R.id.imagePlusButton);
        restaurantNameTextView = findViewById(R.id.restaurantNameTextView);
        priceRatingBar = findViewById(R.id.priceRatingBar);
        tasteRatingBar = findViewById(R.id.tasteRatingBar);
        cleanRatingBar = findViewById(R.id.cleanRatingBar);
        kindnessRatingBar = findViewById(R.id.kindnessRatingBar);
        menuDetailReviewRecyclerView = findViewById(R.id.menuDetailReviewRecyclerView);
        imageReviewRecyclerView = findViewById(R.id.imageReviewRecyclerView);
        commentEditText = findViewById(R.id.commentEditText);

        restaurantNameTextView.setText(restaurantName);
        tasteRate = tasteRatingBar.getRating();
        cleanRate = cleanRatingBar.getRating();
        kindRate = kindnessRatingBar.getRating();
        priceRate = priceRatingBar.getRating();
        comment = commentEditText.getText().toString();
        avgRate = getAvg(new float[]{tasteRate, cleanRate, kindRate, priceRate});
        


        //리뷰추가 버튼 TODO : 수정전
        review_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HHmmss");
                Date now = new Date();
                ReviewItem reviewItem = new ReviewItem(
                       userName,                         //유저이름
                        comment,                         //리뷰 본문
                        String.valueOf(tasteRate),
                        String.valueOf(kindRate),
                        String.valueOf(cleanRate),
                        String.valueOf(priceRate),
                        String.valueOf(avgRate),         //점수
                        dateFormat.format(now),
                        getMenuDetailReview(),
                        getImgUris()
                        

                        //등록시간
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
        
        //이미지 추가 버튼
        imagePlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

        menuPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMenuCount();
            }
        });
    }

    //메뉴 리뷰 추가할 시 카운트+
    public void addMenuCount()
    {
        menuReviewCount++;
    }

    //메뉴 리뷰 추가할 시 카운트-
    public void subMenuCount()
    {
        if(menuReviewCount >= 1)
            menuReviewCount--;
        else
            return;
    }

    //메뉴 리뷰
    public ArrayList<MenuReviewItem> getMenuDetailReview()
    {
        ArrayList<MenuReviewItem> menuReviewItems = new ArrayList<MenuReviewItem>();
        
        return  menuReviewItems;
    }

    public ArrayList<String> getImgUris()
    {
        ArrayList<String> ImgUris = new ArrayList<String>();
        return  ImgUris;
    }

    // 유저 이름으로 리뷰 등록하는 함수 TODO : 수정전
    public void addReview(ReviewItem reviewItem){
        restaurantReference.child(restaurantName).child("restorant_review").child(userName).setValue(reviewItem);
    }

    //평균 계산 해주는 함수
    private float getAvg(float[] scores) {
     float avg = 0;
     float sum = 0;

     for (float score : scores)
     {
         sum += score;
     }
     avg = sum/scores.length;
     return avg;
    }

    //평균 점수 내는 함수 TODO : 수정전
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