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


public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.TestViewHolder> {

    Context context;

    ArrayList<RecyclerImageItem> imglist = new ArrayList<>();

    public ImageRecyclerAdapter(Context context, ArrayList<RecyclerImageItem> testlist) {
        this.context = context;
        this.imglist = testlist;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_item, parent, false);

        return new TestViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        Glide.with(context).load(imglist.get(position).getImageUrl()).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return imglist.size();
    }

    class TestViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.item_image);
        }
    }
}
