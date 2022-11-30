package com.example.projectpickalunch.restorant_info;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectpickalunch.R;
import com.example.projectpickalunch.review_add.MenuRatingItem;
import com.example.projectpickalunch.review_add.MenuReviewItem;

import java.util.ArrayList;

public class MenuDetailRecyclerAdapter extends RecyclerView.Adapter<MenuDetailRecyclerAdapter.ViewHolder> {

    Context context;

    ArrayList<MenuRatingItem> reviewList = new ArrayList<>();

    public MenuDetailRecyclerAdapter(Context context, ArrayList<MenuRatingItem> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.detail_review_item, parent, false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rateName.setText(reviewList.get(position).getRateName());

        Log.e("reviewList", reviewList.get(position).getRateName());
        switch (String.valueOf(reviewList.get(position).getRate()))
        {
            case "1.0" : holder.rateImg.setImageResource(R.drawable.ic_bad_img_selected);
                break;
            case "3.0" : holder.rateImg.setImageResource(R.drawable.ic_notbad_img_selected);
                break;
            case "5.0" : holder.rateImg.setImageResource(R.drawable.ic_good_img_selected);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView rateName;
        ImageView rateImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rateName = itemView.findViewById(R.id.rateName);
            rateImg = itemView.findViewById(R.id.rateImg);
        }
    }
}
