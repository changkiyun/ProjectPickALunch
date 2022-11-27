package com.example.projectpickalunch.review_add;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectpickalunch.R;
import com.example.projectpickalunch.login_api.KakaoLoginActivity;
import com.example.projectpickalunch.restorant_info.RecyclerImageItem;
import com.example.projectpickalunch.restorant_info.Sickdang_Jeongbo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
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
    private DatabaseReference imgReference;
    private StorageReference restaurantStorage;

    //뷰 선언
    ImageButton review_add_return_button;
    static Button review_add_button;
    static Button menuPlusButton;
    Button imagePlusButton;
    TextView restaurantNameTextView;
    ScaleRatingBar tasteRatingBar;
    ScaleRatingBar kindnessRatingBar;
    ScaleRatingBar cleanRatingBar;
    ScaleRatingBar priceRatingBar;
    RecyclerView menuDetailReviewRecyclerView;
    RecyclerView imageReviewRecyclerView;
    EditText commentEditText;
    ProgressBar progressBar;

    //평가 내용,점수
    private String userName;
    private float tasteRate;
    private float kindRate;
    private float cleanRate;
    private float priceRate;
    private float avgRate;
    private String comment;

    private static ArrayList<MenuReviewItem> menuReviewItems;
    private static MenuDetailReviewAdapter menuDetailReviewAdapter;
    private static MultiImageAdapter multiImageAdapter;
    private static ArrayList<Uri> uriList;
    private static int menuReviewCount;

    String restaurantName;

    //전체 리뷰 평점을 받아올 리스트
    ArrayList<String> scoreList = new ArrayList<String>();

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
        progressBar = findViewById(R.id.progressBar);

        menuReviewCount = 0;
        progressBar.setVisibility(View.INVISIBLE);
        restaurantNameTextView.setText(restaurantName);

        uriList = new ArrayList<>();
        multiImageAdapter = new MultiImageAdapter(uriList, getApplicationContext());
        imageReviewRecyclerView.setAdapter(multiImageAdapter);
        imageReviewRecyclerView.setHasFixedSize(true);
        imageReviewRecyclerView.setLayoutManager(new LinearLayoutManager(ReviewAdd.this, LinearLayoutManager.HORIZONTAL, true));

        //리뷰추가 버튼 TODO : 수정전
        review_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tasteRate = tasteRatingBar.getRating();
                cleanRate = cleanRatingBar.getRating();
                kindRate = kindnessRatingBar.getRating();
                priceRate = priceRatingBar.getRating();
                comment = commentEditText.getText().toString();
                avgRate = getAvg(new float[]{tasteRate, cleanRate, kindRate, priceRate});
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HHmmss");
                Date now = new Date();
                ReviewItem reviewItem = new ReviewItem(
                        userName,
                        comment,
                        dateFormat.format(now),
                        String.valueOf(tasteRate),
                        String.valueOf(kindRate),
                        String.valueOf(cleanRate),
                        String.valueOf(priceRate),
                        String.valueOf(avgRate)
                );
                uploadToFireBase(uriList, reviewItem);
                setAvgScore();

            }
        });

        //돌아가기버튼
        review_add_return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        menuReviewItems = new ArrayList();
        menuDetailReviewAdapter = new MenuDetailReviewAdapter(ReviewAdd.this, menuReviewItems);
        menuDetailReviewRecyclerView.setAdapter(menuDetailReviewAdapter);
        menuDetailReviewRecyclerView.setLayoutManager(layoutManager);

        //이미지 추가 버튼
        imagePlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallaryIntent = new Intent(Intent.ACTION_PICK);
                gallaryIntent.setAction(Intent.ACTION_GET_CONTENT);
                gallaryIntent.setType("image/");
                gallaryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                activityResult.launch(gallaryIntent);
            }
        });

        //메뉴 추가 버튼
        menuPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuReviewItems.add(new MenuReviewItem());
                menuDetailReviewAdapter.notifyDataSetChanged();
                menuReviewCount++;
                review_add_button.setEnabled(false);
                menuPlusButton.setEnabled(false);
            }
        });
    }

    //사진을 데이터베이스와 스토리지에 저장하는 메소드
    private void uploadToFireBase(ArrayList<Uri> uriList, ReviewItem reviewItem) {
        //imgReference = restaurantDatabase.child(restaurantName).child("restorant_detail_image");
        restaurantStorage = storage.getReference(restaurantName);
        String id = restaurantReference.push().getKey();

        //리뷰 등록
        restaurantReference.child(restaurantName).child("restorant_review").child(id).setValue(reviewItem);

        for (int i = 0; i < menuReviewCount; i++)
        {
            for (int j = 0; j < menuReviewItems.get(i).menuRatingItems.size(); j++) {
                restaurantReference.child(restaurantName).child("restorant_review").child(id).child("menu_review")
                        .child(menuReviewItems.get(i).getMenuName()).child(menuReviewItems.get(i).menuRatingItems.get(j).getRateName())
                        .setValue(menuReviewItems.get(i).menuRatingItems.get(j).getRate());
            }
        }

        //현재 시간
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'_'HHmmss");

        if (uriList!= null) {
            for (int i = 0; i < uriList.size(); i++)
            {
                //사진 이름을 "식당이름_유저이름_날짜.확장자"로 저장
                Date now = new Date();
                StorageReference fileRef = restaurantStorage.child(userName)
                        .child(restaurantName + "_" + userName + "_" + dateFormat.format(now)+ "_"+ i + "." + getFileExtension(uriList.get(i)));
                fileRef.putFile(uriList.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                restaurantReference.child(restaurantName).child("restorant_review").child(id).child("ImageSrc").push()
                                        .child("imageUrl").setValue(uri.toString());
                                progressBar.setVisibility(View.INVISIBLE);
                                finish();
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
                    }
                });
            }
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private ActivityResultLauncher<Intent> activityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override public void onActivityResult(ActivityResult result) {

                        if(result.getData() == null){   // 어떤 이미지도 선택하지 않은 경우
                            Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                        }
                        else{   // 이미지를 하나라도 선택한 경우
                            if(result.getData().getClipData() == null){     // 이미지를 하나만 선택한 경우
                                Log.e("single choice: ", String.valueOf(result.getData().getData()));
                                Uri imageUri = result.getData().getData();
                                uriList.add(imageUri);

                                multiImageAdapter.notifyDataSetChanged();

                            }
                            else{      // 이미지를 여러장 선택한 경우
                                ClipData clipData = result.getData().getClipData();
                                Log.e("clipData", String.valueOf(clipData.getItemCount()));

                                if(clipData.getItemCount() > 10){   // 선택한 이미지가 11장 이상인 경우
                                    Toast.makeText(getApplicationContext(), "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG).show();
                                }
                                else{   // 선택한 이미지가 1장 이상 10장 이하인 경우
                                    Log.e("TAG", "multiple choice");

                                    for (int i = 0; i < clipData.getItemCount(); i++){
                                        Uri imageUri = clipData.getItemAt(i).getUri();  // 선택한 이미지들의 uri를 가져온다.
                                        try {
                                            uriList.add(imageUri);  //uri를 list에 담는다.

                                        } catch (Exception e) {
                                            Log.e("TAG", "File select error", e);
                                        }
                                    }

                                    Log.e("image", String.valueOf(clipData.getItemCount()));
                                    Log.e("image", uriList.toString());
                                    multiImageAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                }
            });

    //메뉴 삭제 버튼
    public static void deleteMenu(int position){
        menuReviewItems.remove(position);
        menuReviewCount--;
        menuDetailReviewAdapter.notifyDataSetChanged();
    }

    public static void deleteImg(int position){
        uriList.remove(position);
        multiImageAdapter.notifyDataSetChanged();
    }

    // 유저 이름으로 리뷰 등록하는 함수
    public void addReview(ReviewItem reviewItem){
        String id = restaurantReference.push().getKey();
        restaurantReference.child(restaurantName).child("restorant_review").child(id).setValue(reviewItem);
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

    //평균 점수 내는 함수
    public void setAvgScore() {
        restaurantReference.child(restaurantName).child("restorant_review").getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String score = dataSnapshot.child("avgRate").getValue(String.class);
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