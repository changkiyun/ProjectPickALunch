package com.example.projectpickalunch.review_add;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectpickalunch.R;
import com.example.projectpickalunch.restorant_info.RecyclerImageItem;

import java.util.ArrayList;

public class MenuDetailReviewAdapter extends RecyclerView.Adapter<MenuDetailReviewAdapter.ReviewHolder> {

    Context context;
    ArrayList<MenuReviewItem> menuReviewList = new ArrayList<>();
    ArrayList<MenuRatingItem> menuRatingItems;

    public MenuDetailReviewAdapter(Context context, ArrayList<MenuReviewItem> menuReviewList) {
        this.context = context;
        this.menuReviewList = menuReviewList;
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
        EditText menuNameEditText;
        Button menuConfirmButton;
        Button menuDeleteButton;
        Button menuReviewSaveButton;
        RecyclerView menuRatingRecyclerView;
        LinearLayout menuDetailReviewLayout;
        CheckBox checkBoxes[] = new CheckBox[10];
        MenuRatingItem models[] = new MenuRatingItem[10];

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);

            menuDetailReviewLayout = itemView.findViewById(R.id.menuDetailReviewLayout);
            menuNameEditText = itemView.findViewById(R.id.menuNameEditText);
            menuReviewSaveButton = itemView.findViewById(R.id.menuReviewSaveButton);
            menuConfirmButton = itemView.findViewById(R.id.menuConfirmButton);
            menuDeleteButton = itemView.findViewById(R.id.menuDeleteButton);
            menuRatingRecyclerView = itemView.findViewById(R.id.menuRatingRecyclerView);
            checkBoxes[0] = itemView.findViewById(R.id.checkbox_1);
            checkBoxes[1] = itemView.findViewById(R.id.checkbox_2);
            checkBoxes[2] = itemView.findViewById(R.id.checkbox_3);
            checkBoxes[3] = itemView.findViewById(R.id.checkbox_4);
            checkBoxes[4] = itemView.findViewById(R.id.checkbox_5);
            checkBoxes[5] = itemView.findViewById(R.id.checkbox_6);
            checkBoxes[6] = itemView.findViewById(R.id.checkbox_7);
            checkBoxes[7] = itemView.findViewById(R.id.checkbox_8);
            checkBoxes[8] = itemView.findViewById(R.id.checkbox_9);
            checkBoxes[9] = itemView.findViewById(R.id.checkbox_10);

            menuRatingItems = new ArrayList<MenuRatingItem>();
            MenuDetailRatingAdapter menuDetailRatingAdapter = new MenuDetailRatingAdapter(context.getApplicationContext(), menuRatingItems);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context.getApplicationContext());
            menuRatingRecyclerView.setAdapter(menuDetailRatingAdapter);
            menuRatingRecyclerView.setLayoutManager(layoutManager);

            for (int i = 0; i < checkBoxes.length; i++)
            {
                int finalI = i;
                checkBoxes[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkBoxes[finalI].isChecked())
                        {
                            models[finalI] = new MenuRatingItem(checkBoxes[finalI].getText().toString());
                            menuRatingItems.add(models[finalI]);
                            menuDetailRatingAdapter.notifyDataSetChanged();
                        } else if (!checkBoxes[finalI].isChecked())
                        {
                            menuRatingItems.remove(models[finalI]);
                            menuDetailRatingAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }

            menuConfirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String menuName = menuNameEditText.getText().toString();
                    if (menuName.trim().getBytes().length > 0) {
                        menuDetailReviewLayout.setVisibility(View.VISIBLE);
                        menuReviewList.get(getAdapterPosition()).setMenuName(menuName);
                        menuNameEditText.setEnabled(false);
                    }
                }
            });

            menuDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReviewAdd.deleteMenu(getAdapterPosition());
                    ReviewAdd.review_add_button.setEnabled(true);
                    ReviewAdd.menuPlusButton.setEnabled(true);
                }
            });

            menuReviewSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuReviewList.get(getAdapterPosition()).setMenuRatingItems(menuRatingItems);
                    ReviewAdd.review_add_button.setEnabled(true);
                    ReviewAdd.menuPlusButton.setEnabled(true);
                }
            });
        }
    }
}