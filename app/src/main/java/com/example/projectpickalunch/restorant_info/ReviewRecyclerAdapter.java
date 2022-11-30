package com.example.projectpickalunch.restorant_info;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectpickalunch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.ReviewViewHolder> {

    final FirebaseDatabase root = FirebaseDatabase.getInstance();

    Context context;
    ArrayList<ReviewRecyclerItem> reviewlist = new ArrayList<>();
    ArrayList<ReviewImageItem> itemList = new ArrayList<>();
    ArrayList<MenuRecyclerAdapter> adapters;
    String sickdang_title;

    public ReviewRecyclerAdapter(Context context, ArrayList<ReviewRecyclerItem> reviewlist, String sickdang_title, ArrayList<ReviewImageItem> itemList, ArrayList<MenuRecyclerAdapter> adapters) {
        this.context = context;
        this.reviewlist = reviewlist;
        this.sickdang_title = sickdang_title;
        this.itemList = itemList;
        this.adapters = adapters;
    }

    @NonNull
    @Override
    public ReviewRecyclerAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.review_item, parent, false);

        return new ReviewRecyclerAdapter.ReviewViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ReviewRecyclerAdapter.ReviewViewHolder holder, int pos) {
        holder.userName.setText(reviewlist.get(pos).getUser_name());
        holder.score.setText(String.format("%.1f", Float.parseFloat(reviewlist.get(pos).getAvgRate())));
        holder.reviewDate.setText(reviewlist.get(pos).getReview_date().substring(0, reviewlist.get(pos).getReview_date().indexOf(":")));
        holder.mainText.setText(reviewlist.get(pos).getRestaurant_review());

        ImageRecyclerAdapter adapter = itemList.get(pos).getAdapter();
        MenuRecyclerAdapter menuAdapter = adapters.get(pos);

        Log.e("reviewListc", pos + adapters.get(pos).reviewList.toString());

        holder.detailItemRecyclerView.setAdapter(menuAdapter);
        holder.reviewImageRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        menuAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return reviewlist.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        TextView score;
        TextView reviewDate;
        TextView mainText;
        RecyclerView reviewImageRecyclerView;
        RecyclerView detailItemRecyclerView;

        public ReviewViewHolder(@NonNull View reviewView) {
            super(reviewView);
            int pos = getAdapterPosition() ;

            userName = reviewView.findViewById(R.id.userName);
            score = reviewView.findViewById(R.id.userScore);
            reviewDate = reviewView.findViewById(R.id.reviewDate);
            mainText = reviewView.findViewById(R.id.mainText);
            reviewImageRecyclerView = reviewView.findViewById(R.id.reviewImageRecyclerView);
            detailItemRecyclerView = reviewView.findViewById(R.id.detailItemRecyclerView);


            LinearLayoutManager layoutManager3 = new LinearLayoutManager(context);
            LinearLayoutManager layoutManager4 = new LinearLayoutManager(context);
            reviewImageRecyclerView.setLayoutManager(layoutManager3);
            detailItemRecyclerView.setLayoutManager(layoutManager4);
            reviewImageRecyclerView.setHasFixedSize(true);
            detailItemRecyclerView.setHasFixedSize(true);
            layoutManager3.setOrientation(RecyclerView.HORIZONTAL);
            layoutManager4.setOrientation(RecyclerView.HORIZONTAL);

            reviewView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        ArrayList<String> fileName = new ArrayList<>();
                        Intent fullReview = new Intent(context, FullReviewActivity.class);
                        for (int i = 0; i < itemList.get(pos).getImageList().size(); i++) {
                            fileName.add(itemList.get(pos).getImageList().get(i).getFileName());
                        }
                        fullReview.putExtra("taste", reviewlist.get(pos).getTasteRate());
                        fullReview.putExtra("clean", reviewlist.get(pos).getCleanRate());
                        fullReview.putExtra("kind", reviewlist.get(pos).getKindRate());
                        fullReview.putExtra("price", reviewlist.get(pos).getPriceRate());
                        fullReview.putExtra("fileName", fileName);
                        fullReview.putExtra("UID", reviewlist.get(pos).getUID());
                        fullReview.putExtra("userName",reviewlist.get(pos).getUser_name());
                        fullReview.putExtra("score",reviewlist.get(pos).getAvgRate());
                        fullReview.putExtra("mainText",reviewlist.get(pos).getRestaurant_review());
                        fullReview.putExtra("reviewDate", reviewlist.get(pos).getReview_date());
                        fullReview.putExtra("restaurantName", sickdang_title);
                        fullReview.putExtra("key", reviewlist.get(pos).getKey());
                        context.startActivity(fullReview);
                    }
                }
            });
        }
    }
}
