package com.example.projectpickalunch.restorant_info;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectpickalunch.R;
import com.example.projectpickalunch.review_add.MenuRatingItem;
import com.example.projectpickalunch.review_add.MenuReviewItem;
import com.example.projectpickalunch.review_add.ReviewAdd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

public class Sickdang_Jeongbo extends AppCompatActivity {
    //파이어베이스 참조
    //TODO: 이미지 삭제 기능 추가 필요, 기존 이미지 저장 경로 삭제, 이미지 크롭
    private final FirebaseDatabase root = FirebaseDatabase.getInstance(); //파이어베이스 Root
    private final DatabaseReference usersDatabase = root.getReference("users"); //유저 db
    private final DatabaseReference usersReference = root.getReference().child("users"); //유저 db Child
    private final DatabaseReference restaurantDatabase = root.getReference("식당");   //식당 db
    private final DatabaseReference restaurantReference = root.getReference().child("식당"); //식당 db Child
    private final FirebaseStorage storage = FirebaseStorage.getInstance();  //스토리지

    //데이터 베이스 참조
    private StorageReference restaurantStorage;  //식당 사진 스토리지
    private DatabaseReference detail_images;    //식당 세부사진 db
    private DatabaseReference locationReference;    //식당 위치 db
    private DatabaseReference imgReference; //식당 세부 사진 저장을 위한 경로 db
    private DatabaseReference reviewReference;
    private DatabaseReference review_images;

    //식당 정보를 저장할 변수
    String sickdang_title;

    //상단,하단 리사이클러뷰에 사용될 변수 :YJW
    RecyclerView imageRecyclerView;
    RecyclerView reviewRecyclerView;
    ProgressBar progressBar;
    Uri imageUri;

    ArrayList<RecyclerImageItem> imageList;
    ArrayList<ReviewRecyclerItem> reviewList;
    ArrayList<RecyclerImageItem> reviewImageList;
    ArrayList<ReviewImageItem> reviewImageItemList;
    ArrayList<MenuRatingItem> menuRatingItemList;
    ArrayList<MenuReviewItem> menuReviewItemList;
    ArrayList<MenuRecyclerAdapter> menuRecyclerAdapterList;

    ImageRecyclerAdapter imageAdapter;
    ReviewRecyclerAdapter reviewRecyclerAdapter;
    ImageRecyclerAdapter reviewImageAdapter;

    //사진 추가에 사용되는 테스트용 이미지 뷰 :YJW
    ImageView testImg;

    //이미지 저장하는 Drawble
    private Drawable mImageDrawable;

    //kakaoMap
    String url;
    Double latitude;
    Double longitude;
    Button mapTest;

    RestorantLocationItem restorantLocationItem;
    ArrayList<RestorantLocationItem> locationArrayList;

    //FirebaseDatabase locationDatabase;

    MapPoint mapPoint;
    MapView mapView;
    MapPOIItem marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sicdangjeongbo);

        //인텐트 받아오기 가게 이름
        Intent intent = getIntent();
        sickdang_title = intent.getStringExtra("restorant_name");

        //가게 정보
        TextView sickdanTitle = (TextView) findViewById(R.id.sicdangTitle);
        sickdanTitle.setText(sickdang_title);

        //Kakao Map Api

        //변수 초기화
        locationArrayList = new ArrayList<>();

        //식당 위도 경도 가져오기
        locationReference = root.getReference("식당");
        locationReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                locationArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RestorantLocationItem restorantLocationItem = snapshot.getValue(RestorantLocationItem.class);
                    locationArrayList.add(restorantLocationItem);
                    System.out.println("리스너 실행됨");
                }


                for (int i = 0; i < locationArrayList.size(); i++) {
                    if (locationArrayList.get(i).getRestorant_name().equals(sickdang_title)) {
                        url = "kakaomap://place?id=" + locationArrayList.get(i).getRestorant_location_id();
                        latitude = Double.parseDouble(locationArrayList.get(i).getRestorant_location_latitude());
                        longitude = Double.parseDouble(locationArrayList.get(i).getRestorant_location_longitude());
                        mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);
                        mapView.setMapCenterPoint(mapPoint, true);
                        marker.setMapPoint(mapPoint);
                        marker.setItemName("Default Marker");
                        marker.setTag(0);
                        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

                        mapView.addPOIItem(marker);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Sickdang_Jeongbo", String.valueOf(error.toException()));
            }
        });


        //카카오 맵 열기
        mapTest = (Button) findViewById(R.id.mapTest);
        mapTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent2);
            }
        });
        
        //TODO : 액티비티 실행시 맵뷰 꺼지는거
        mapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.kakaoMapView);
        mapViewContainer.addView(mapView);
        
        marker = new MapPOIItem();


        //맵 포인트 위도경도 설정


        //리사이클러 뷰로 가게 상세 사진 보여주기
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        imageRecyclerView = findViewById(R.id.recycler_view); //
        imageRecyclerView.setLayoutManager(layoutManager);
        imageRecyclerView.setHasFixedSize(true);

        imageList = new ArrayList<>();

        imageAdapter = new ImageRecyclerAdapter(Sickdang_Jeongbo.this, imageList);
        imageRecyclerView.setAdapter(imageAdapter);

        //리뷰
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        reviewRecyclerView = findViewById(R.id.reviewRecyclerView);
        reviewRecyclerView.setLayoutManager(layoutManager2);
        reviewRecyclerView.setHasFixedSize(true);

        reviewList = new ArrayList<>();
        reviewImageList = new ArrayList<>();
        reviewImageItemList = new ArrayList<>();
        menuRatingItemList = new ArrayList<>();
        menuReviewItemList = new ArrayList<>();
        menuRecyclerAdapterList = new ArrayList<>();

        reviewReference = restaurantReference.child(sickdang_title).child("restorant_review");

        reviewReference.getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reviewList.clear();
                reviewImageItemList.clear();
                menuRecyclerAdapterList.clear();
                menuReviewItemList.clear();
                imageList.clear();
                String key = null;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    reviewImageList.clear();
                    key  = dataSnapshot.getKey();
                    ReviewRecyclerItem model = dataSnapshot.getValue(ReviewRecyclerItem.class);
                    reviewList.add(model);

                    for (DataSnapshot imageSrc : dataSnapshot.child("ImageSrc").getChildren()) {
                        RecyclerImageItem totImgSrc = imageSrc.getValue(RecyclerImageItem.class);
                        imageList.add(totImgSrc);
                    }
                    imageAdapter.notifyDataSetChanged();

                    for (DataSnapshot imageSrc : snapshot.child(key).child("ImageSrc").getChildren()) {
                        RecyclerImageItem reviewImgSrc = imageSrc.getValue(RecyclerImageItem.class);
                        reviewImageList.add(reviewImgSrc);
                        Log.e("test", key +  reviewImgSrc.getFileName());
                    }

                    for (DataSnapshot snapshot1 : snapshot.child(key).child("menu_review").getChildren()) {
                        String menuName = snapshot1.getKey();
                        menuRatingItemList.clear();
                        for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                            String rateName = snapshot2.getKey();
                            float rate = snapshot2.getValue(float.class);
                            menuRatingItemList.add(new MenuRatingItem(rateName, rate));
                        }
                        menuReviewItemList.add(new MenuReviewItem(menuName, menuRatingItemList));
                    }
                    Log.e("test1" , menuRatingItemList.toString());
                    menuRecyclerAdapterList.add(new MenuRecyclerAdapter(Sickdang_Jeongbo.this, new ArrayList<MenuReviewItem>(menuReviewItemList)));
                    reviewImageItemList.add(new ReviewImageItem(key, reviewImageList, new ImageRecyclerAdapter(Sickdang_Jeongbo.this, new ArrayList<RecyclerImageItem>(reviewImageList))));

                }

                reviewRecyclerAdapter = new ReviewRecyclerAdapter(Sickdang_Jeongbo.this, reviewList, sickdang_title, reviewImageItemList, menuRecyclerAdapterList);
                reviewRecyclerAdapter.notifyDataSetChanged();

                reviewRecyclerView.setAdapter(reviewRecyclerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //뒤로가기 버튼
        ImageButton btnReturn = (ImageButton) findViewById(R.id.returnBtn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //가게 점수 가져오기
        TextView sickdangScore = (TextView) findViewById(R.id.pyeongJeom);
        restaurantReference.child(sickdang_title).child("restorant_score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String score = snapshot.getValue(String.class);
                    sickdangScore.setText(String.format("%.1f", Float.parseFloat(score)));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        NestedScrollView reviewScroll = findViewById(R.id.reviewScroll);
        reviewScroll.fullScroll(ScrollView.FOCUS_UP);

        //리뷰 추가 버튼
        Button newReviewBtn = findViewById(R.id.new_review_btn);
        newReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reviewAdd = new Intent(getApplicationContext(), ReviewAdd.class);
                reviewAdd.putExtra("restorant_name", sickdang_title);
                startActivity(reviewAdd);
            }
        });

    }
    //유저의 이름을 받아오는 메소드
    //TODO : 유저 다시 생기면 다시 만들어야함
    private String getUserName() {
        String TestName = "TestName";

        //return nickName;
        return TestName;
    }

}
