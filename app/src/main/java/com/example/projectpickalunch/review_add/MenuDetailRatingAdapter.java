package com.example.projectpickalunch.review_add;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectpickalunch.R;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;

public class MenuDetailRatingAdapter extends RecyclerView.Adapter<MenuDetailRatingAdapter.ReviewHolder> {

    Context context;

    ArrayList<MenuReviewItem> menuReviewList = new ArrayList<>();

    public MenuDetailRatingAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MenuDetailRatingAdapter.ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.menu_detail_review_add_item, parent, false);

        return new MenuDetailRatingAdapter.ReviewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MenuDetailRatingAdapter.ReviewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return menuReviewList.size();
    }

    class ReviewHolder extends RecyclerView.ViewHolder{
        TextView rateName;
        ScaleRatingBar ratingBar;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);

            rateName = itemView.findViewById(R.id.RateName);
            ratingBar = itemView.findViewById(R.id.RatingBar);
        }
    }
}