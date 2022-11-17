package com.example.projectpickalunch.review_add;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectpickalunch.R;
import com.example.projectpickalunch.restorant_info.RecyclerImageItem;

import java.util.ArrayList;

public class MenuDetailReviewAdapter extends RecyclerView.Adapter<MenuDetailReviewAdapter.ReviewHolder> {

    Context context;

    ArrayList<MenuReviewItem> menuReviewList = new ArrayList<>();

    public MenuDetailReviewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MenuDetailReviewAdapter.ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.menu_detail_review_add_item, parent, false);

        return new MenuDetailReviewAdapter.ReviewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MenuDetailReviewAdapter.ReviewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return menuReviewList.size();
    }

    class ReviewHolder extends RecyclerView.ViewHolder{


        public ReviewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}