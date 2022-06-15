package com.example.projectpickalunch.Main;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.projectpickalunch.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainGridItem_View extends ConstraintLayout {
    ImageView restorant_image;
    TextView restorant_name;
    TextView restorant_score;

    Context context;

    MainGridItem_View(Context context){
        super(context);
        init(context);
        this.context = context;
    }

    public MainGridItem_View(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.main_grid_item, this, true);
        restorant_image = findViewById(R.id.restorant_image);
        restorant_name = findViewById(R.id.restorant_name);
        restorant_score = findViewById(R.id.restorant_score);
    }

    public void setRestorant_image_src(String restorant_image_src){
        //FireStore에서 이미지 가져와서 뷰에 출력
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child("restorant_image");

        if(pathReference == null){
            Toast.makeText(context.getApplicationContext(), "저장소에 사진이 없습니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            //이미지 불러오기는 되는데 child값을 변수로 지정할 수가 없음

            String imageSrc = restorant_image_src;
            StorageReference getRestorantImage = storageReference.child(imageSrc);
            getRestorantImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(context).load(uri).into(restorant_image);
                }
            });
        }
    }
    public void setRestorant_name(String name){
        restorant_name.setText(name);
    }
    public void setRestorant_score(String score){
        restorant_score.setText(score);
    }

}
