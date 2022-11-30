package com.example.projectpickalunch.review_add;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectpickalunch.R;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;

public class MenuDetailRatingAdapter extends RecyclerView.Adapter<MenuDetailRatingAdapter.ReviewHolder> {

    Context context;

    ArrayList<MenuRatingItem> menuRatingItems = new ArrayList<>();

    public MenuDetailRatingAdapter(Context context, ArrayList<MenuRatingItem> menuRatingItems) {
        this.context = context;
        this.menuRatingItems = menuRatingItems;
    }

    @NonNull
    @Override
    public MenuDetailRatingAdapter.ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.menu_detail_review_rating_item, parent, false);

        return new MenuDetailRatingAdapter.ReviewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MenuDetailRatingAdapter.ReviewHolder holder, int position) {
        holder.rateName.setText(menuRatingItems.get(position).getRateName());
        menuRatingItems.get(position).setRate(5.0F);
    }

    @Override
    public int getItemCount() {
        return menuRatingItems.size();
    }

    class ReviewHolder extends RecyclerView.ViewHolder{
        TextView rateName;
        RadioGroup radioGroup;
        RadioButton firstRBtn;
        RadioButton secondRbtn;
        RadioButton thirdRbtn;
        ImageView badimg;
        ImageView nbadimg;
        ImageView goodimg;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);

            radioGroup = itemView.findViewById(R.id.radioGroup);
            rateName = itemView.findViewById(R.id.RateName);
            firstRBtn = itemView.findViewById(R.id.FirstRadio);
            secondRbtn = itemView.findViewById(R.id.SecondRadio);
            thirdRbtn = itemView.findViewById(R.id.ThirdRadio);
            badimg = itemView.findViewById(R.id.badimg);
            nbadimg = itemView.findViewById(R.id.notbadimg);
            goodimg = itemView.findViewById(R.id.goodimg);

            radioGroup.check(thirdRbtn.getId());
            goodimg.setColorFilter(context.getResources().getColor(R.color.kmongColor));

            firstRBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuRatingItems.get(getAdapterPosition()).setRate(1.0F);
                    badimg.setColorFilter(context.getResources().getColor(R.color.kmongColor));
                    nbadimg.setColorFilter(context.getResources().getColor(R.color.nonSelected));
                    goodimg.setColorFilter(context.getResources().getColor(R.color.nonSelected));
                }
            });
            secondRbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuRatingItems.get(getAdapterPosition()).setRate(3.0F);
                    badimg.setColorFilter(context.getResources().getColor(R.color.nonSelected));
                    nbadimg.setColorFilter(context.getResources().getColor(R.color.kmongColor));
                    goodimg.setColorFilter(context.getResources().getColor(R.color.nonSelected));
                }
            });
            thirdRbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuRatingItems.get(getAdapterPosition()).setRate(5.0F);
                    badimg.setColorFilter(context.getResources().getColor(R.color.nonSelected));
                    nbadimg.setColorFilter(context.getResources().getColor(R.color.nonSelected));
                    goodimg.setColorFilter(context.getResources().getColor(R.color.kmongColor));
                }
            });
        }
    }

}