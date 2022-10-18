package com.example.projectpickalunch.restorant_info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectpickalunch.R;

import java.util.ArrayList;

//식당 세부사진 리사이클러뷰 어댑터
public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder> {

    Context context;

    ArrayList<RecyclerImageItem> imglist = new ArrayList<>();

    public ImageRecyclerAdapter(Context context, ArrayList<RecyclerImageItem> imglist) {
        this.context = context;
        this.imglist = imglist;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_item, parent, false);

        return new ImageViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Glide.with(context).load(imglist.get(position).getImageUrl()).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return imglist.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.item_image);
        }
    }
}
