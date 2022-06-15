package com.example.projectpickalunch.Main;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MainGridAdapter extends BaseAdapter {

        ArrayList<MainGridItem> arrayList;
        Context context;
        MainGridItem_View mainGridItem_view = null;

        public MainGridAdapter(ArrayList<MainGridItem> arrayList, Context context){
            this.arrayList = arrayList;
            this.context = context;
        }

    @Override
    public int getCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    //사용자 지정 함수
    public void addItem(MainGridItem item){
        arrayList.add(item);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if(convertView == null){
            mainGridItem_view = new MainGridItem_View(context.getApplicationContext());
        }
        else{
            mainGridItem_view = (MainGridItem_View) convertView;
        }

        MainGridItem mainGridItem = arrayList.get(position);
        mainGridItem_view.setRestorant_name(mainGridItem.getRestorant_name());
        mainGridItem_view.setRestorant_score((mainGridItem.getRestorant_score()));

        //FireStore에서 이미지 가져와서 뷰에 출력
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child("restorant_image");

        if(pathReference == null){
            Toast.makeText(context.getApplicationContext(), "저장소에 사진이 없습니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            //이미지 불러오기는 되는데 child값을 변수로 지정할 수가 없음
            String image_src = mainGridItem.getRestorant_image_src();
            StorageReference getRestorantImage = storageReference.child("restorant_image/sample_image3.png");
            getRestorantImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(context).load(uri).into(mainGridItem_view.restorant_image);
                }
            });
        }

        return mainGridItem_view;


    }
}
