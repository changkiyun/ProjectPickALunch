package com.example.projectpickalunch.restorant_info;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectpickalunch.R;
import com.example.projectpickalunch.restorant_add.RestorantAdd;
import com.example.projectpickalunch.review_add.ReviewAdd;
import com.example.projectpickalunch.user_information.NickName;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    //식당 정보를 저장할 변수
    String sickdang_title;
    Float sickdang_score;

    //상단 리사이클러뷰에 사용될 변수 :YJW
    RecyclerView imageRecyclerView;
    ProgressBar progressBar;
    Uri imageUri;
    ArrayList<RecyclerImageItem> imageList;

    //상단 리사이클러뷰 어댑터 :YJW
    ImageRecyclerAdapter imageAdapter;

    //사진 추가에 사용되는 테스트용 이미지 뷰 :YJW
    ImageView testImg;

    //리뷰에 쓰이는 리스트아이템 어뎁터
    ListItemAdapter adapter;

    //리스트뷰 값 저장할 ArrayList
    ArrayList<ListItem> arrayList;
    ArrayList<String> rate_array;
    ArrayList<String> nameList;
    String nickName;

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
        sickdang_score = Float.parseFloat(intent.getStringExtra("restorant_score"));

        //Kakao Map Api

        //변수 초기화
        locationArrayList = new ArrayList<>();

        //식당 위도 경도 가져오기
        locationReference = root.getReference("식당");
        locationReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                locationArrayList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RestorantLocationItem restorantLocationItem = snapshot.getValue(RestorantLocationItem.class);
                    locationArrayList.add(restorantLocationItem);
                    System.out.println("리스너 실행됨");
                }


                for(int i=0 ; i<locationArrayList.size(); i++){
                    if(locationArrayList.get(i).getRestorant_name().equals(sickdang_title)){
                        url = "kakaomap://place?id=" + locationArrayList.get(i).getRestorant_location_id();
                        latitude = Double.parseDouble(locationArrayList.get(i).getRestorant_location_latitude());
                        longitude = Double.parseDouble(locationArrayList.get(i).getRestorant_location_longitude());
                        mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);
                        mapView.setMapCenterPoint(mapPoint,true);
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
                public void onCancelled (@NonNull DatabaseError error){
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
        //
         mapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.kakaoMapView);
        mapViewContainer.addView(mapView);
        //

         marker = new MapPOIItem();


        //맵 포인트 위도경도 설정


        //리사이클러 뷰로 가게 상세 사진 보여주기
        imageRecyclerView = findViewById(R.id.recycler_view); //
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        imageRecyclerView.setLayoutManager(layoutManager);
        imageRecyclerView.setHasFixedSize(true);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        imageList = new ArrayList<>();


        imageAdapter = new ImageRecyclerAdapter(Sickdang_Jeongbo.this, imageList);
        imageRecyclerView.setAdapter(imageAdapter);
        detail_images = restaurantReference.child(sickdang_title).child("restorant_detail_image").getRef();

        detail_images.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imageList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    RecyclerImageItem model = dataSnapshot.getValue(RecyclerImageItem.class);
                    imageList.add(model);
                }
                imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //사진 업로드 테스트
        //TODO: 리뷰 및 이미지 업로드 기능 정식으로 추가할 떄 사용할 가능성 있음
        Button testUploadBtn = findViewById(R.id.testBtn);
        Button testSelectBtn = findViewById(R.id.testSelectBtn);
        testImg = findViewById(R.id.testImg);
        progressBar = findViewById(R.id.testbar);
        progressBar.setVisibility(View.INVISIBLE);

        //사진 선택 버튼 리스너
        testSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallaryIntent = new Intent();
                gallaryIntent.setAction(Intent.ACTION_GET_CONTENT);
                gallaryIntent.setType("image/");
                activityResult.launch(gallaryIntent);
            }
        });

        //사진 업로드 버튼 리스너
        testUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageUri != null) {
                    uploadToFireBase(imageUri);
                }else {
                    Toast.makeText(Sickdang_Jeongbo.this, "사진을 선택해 주세요", Toast.LENGTH_SHORT).show();
                }
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

        //가게 이름
        TextView sickdanTitle = (TextView)findViewById(R.id.sicdangTitle);
        sickdanTitle.setText(sickdang_title);



        //
        TextView sickdangScore = (TextView)findViewById(R.id.pyeongJeom);
        sickdangScore.setText(String.format("%.1f", sickdang_score));
        
        //Todo: 오류 확인 후 삭제

//        ListView listView = (ListView) findViewById(R.id.listView);
//        EditText reviewWriten =(EditText) findViewById(R.id.reviewWrite);
//        RatingBar rB = (RatingBar)findViewById(R.id.ratingBar);



        //파이어 베이스에서 리뷰 가져와서 리스트뷰에 출력하기
//        arrayList = new ArrayList<>();
//        rate_array = new ArrayList<>();

        //데베에서 리뷰 가져오기

//        restaurantReference.child(sickdang_title).child("restorant_review").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                arrayList.clear();
//                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
//                    ListItem listItem = snapshot.getValue(ListItem.class);
//                    arrayList.add(listItem); // 리뷰 가져옴
//                    Review_Rate listItem1 = snapshot.getValue(Review_Rate.class);
//                    rate_array.add(listItem1.getReview_rate());
//
//                    String[] rate_string = new String[rate_array.size()];
//                    rate_string = rate_array.toArray(rate_string);
//
//                    float rate_hap=0;
//                    for(int i=0;i<rate_string.length;i++){
//                        rate_hap += Float.parseFloat(rate_string[i]);
//                    }
//                    float rate_avg = rate_hap/Float.parseFloat(String.valueOf(rate_array.size()));
//                    addScore(String.valueOf(rate_avg));
//                    TextView pyeongJeom = (TextView)findViewById(R.id.pyeongJeom);
//                    pyeongJeom.setText(String.valueOf(rate_avg));
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
//
//            }
//
//        });
//        adapter = new ListItemAdapter(arrayList, this);
//        listView.setAdapter(adapter);
//
//        nameList = new ArrayList<>();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        //String uid = user.getUid();
//        usersReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
//                    NickName nickname = snapshot.getValue(NickName.class);
//                    nameList.add(nickname.getNickName());
//                    String[] name = new String[nameList.size()];
//                    name = nameList.toArray(name);
//                    nickName = String.valueOf(name[0]);
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
//
//            }
//
//        });
//
//        Button reviewRegis = (Button) findViewById(R.id.reviewRegis);
//        reviewRegis.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                adapter.addItems(new ListItem(nickName, reviewWriten.getText().toString() , "" + rB.getRating()));
//                addReview(reviewWriten.getText().toString(),nickName, String.valueOf(rB.getRating()));
//                listView.setAdapter(adapter);
//            }
//        });








//Todo: 오류 확인 후 삭제 (oncreate 밖에 위치 )

//    //리사이클러 뷰에 이미지 넣기
//
//    //리뷰 파이어베이스에 저장
//    public void addReview(String restaurant_review, String user_name, String review_rate){
//        Intent intent = getIntent();
//        String sickdang_title = intent.getStringExtra("itemname.get(i)");
//        ListItem listItem = new ListItem(user_name,restaurant_review,review_rate);
//        restaurantReference.child(sickdang_title).child("restorant_review").child(user_name).setValue(listItem);
//    }
//    public void addScore(String restorant_score){
//        Intent intent = getIntent();
//        String sickdang_title = intent.getStringExtra("itemname.get(i)");
//        restaurantReference.child(sickdang_title).child("restorant_score").setValue(restorant_score);

//    }

    //유저의 이름을 받아오는 메소드
    //TODO : 유저 다시 생기면 다시 만들어야함
    private String getUserName() {
        String TestName = "TestName";

        //return nickName;
        return TestName;
    }

    //사진을 데이터베이스와 스토리지에 저장하는 메소드
    private void uploadToFireBase(Uri uri) {
        imgReference = restaurantDatabase.child(sickdang_title).child("restorant_detail_image");
        restaurantStorage = storage.getReference(sickdang_title);

        //현재 시간
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'_'HHmmss");
        Date now = new Date();

        //사진 이름을 "식당이름_유저이름_날짜.확장자"로 저장
        StorageReference fileRef = restaurantStorage.child(getUserName())
                .child(sickdang_title + "_" + getUserName() + "_" + dateFormat.format(now) + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        RecyclerImageItem model = new RecyclerImageItem(uri.toString());
                        String modelid = imgReference.push().getKey();
                        imgReference.child(modelid).setValue(model);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(Sickdang_Jeongbo.this, "업로드 성공", Toast.LENGTH_SHORT).show();
                        testImg.setImageResource(0);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Sickdang_Jeongbo.this, "업로드 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //파일 타입을 가져오는 메소드
    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    //사진 가져오기
    private ActivityResultLauncher<Intent> activityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                        testImg.setImageURI(imageUri);
                    }
                }
            });
}
