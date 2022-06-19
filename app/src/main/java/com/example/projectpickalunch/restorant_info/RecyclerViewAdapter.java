package com.example.projectpickalunch.restorant_info;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectpickalunch.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<RecyclerViewItem> mData;
    private Context context;
    ImageView imageView;

    public RecyclerViewAdapter(ArrayList<RecyclerViewItem> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    // onCreateViewHolder : 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    // onBindViewHolder : position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        String[] restorant_datail_image = {re};

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child("가타쯔무리/");

        if(pathReference == null){
            Toast.makeText(context.getApplicationContext(), "저장소에 사진이 없습니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            //RealTimeDatabase에 저장된 이미지 경로를 가져와서 Storage의 이미지를 참조하고 그리드뷰의 이미지뷰에 출력
            StorageReference getRestorantImage = storageReference.child(String.valueOf(mData.get(position).getDetail_image_1()));
            getRestorantImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(context).load(uri).into(imageView);
                }
            });
        }
    }

    // getItemCount : 전체 데이터의 개수를 리턴
    @Override
    public int getItemCount() {
        return mData != null? mData.size() : 0;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {


        ViewHolder(View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조
            imageView = itemView.findViewById(R.id.item_image);
        }
    }
}