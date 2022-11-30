package com.example.projectpickalunch.restorant_info;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectpickalunch.R;
import com.example.projectpickalunch.review_add.MenuDetailRatingAdapter;
import com.example.projectpickalunch.review_add.MenuRatingItem;
import com.example.projectpickalunch.review_add.MenuReviewItem;

import java.util.ArrayList;

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.ViewHolder> {

    Context context;

    ArrayList<MenuReviewItem> reviewList = new ArrayList<>();
    ArrayList<MenuRatingItem> rateList = new ArrayList<>();

    public MenuRecyclerAdapter(Context context, ArrayList<MenuReviewItem> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.menu_review_item, parent, false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.menuName.setText(reviewList.get(position).getMenuName());

        rateList =  reviewList.get(position).getMenuRatingItems();
        Log.e("reviewListab", position + reviewList.get(position).getMenuName() + reviewList.get(position).getMenuRatingItems());
        MenuDetailRecyclerAdapter adapter = new MenuDetailRecyclerAdapter(context, rateList);

        holder.detailReviewRecycler.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView menuName;
        RecyclerView detailReviewRecycler;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            menuName = itemView.findViewById(R.id.menuName);
            detailReviewRecycler = itemView.findViewById(R.id.detailReviewRecycler);

            GridLayoutManager layoutManager5 = new GridLayoutManager(context, 3);
            layoutManager5.setOrientation(RecyclerView.HORIZONTAL);
            detailReviewRecycler.setLayoutManager(layoutManager5);
            detailReviewRecycler.setHasFixedSize(true);

        }
    }
}
