package com.example.projectpickalunch.Main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectpickalunch.R;
import com.example.projectpickalunch.restorant_info.RecyclerImageItem;
import com.example.projectpickalunch.restorant_info.Sickdang_Jeongbo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URI;
import java.util.ArrayList;

public class RankingGridAdapter  extends RecyclerView.Adapter<RankingGridAdapter.RankingViewHolder> {

    Context context;

    ArrayList<RankingGridItem> restauranatlist = new ArrayList<>();

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    Uri imageUri;
    Float score;

    public RankingGridAdapter(Context context, ArrayList<RankingGridItem> restauranatlist) {
        this.context = context;
        this.restauranatlist = restauranatlist;
        this.imageUri = imageUri;
    }
    public RankingGridAdapter(Context context, ArrayList<RankingGridItem> restauranatlist, Uri imageUri) {
        this.context = context;
        this.restauranatlist = restauranatlist;
        this.imageUri = imageUri;
    }


    @NonNull
    @Override
    public RankingGridAdapter.RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.main_grid_item, parent, false);

        return new RankingGridAdapter.RankingViewHolder(view);
    }

    @Override
    //TODO : 사진 밖에서 가져오기
    public void onBindViewHolder(@NonNull RankingGridAdapter.RankingViewHolder holder, int position) {
        score = Float.parseFloat(restauranatlist.get(position).getRestorant_score());
        StorageReference getRestorantImage = storageReference.child(restauranatlist.get(position).getRestorant_image_src());
        getRestorantImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri)
                        .override(300,300)
                        .centerCrop()
                        .into(holder.mImageView);
            }
        });

        holder.nameTextView.setText(restauranatlist.get(position).getRestorant_name());
        holder.scoreTextView.setText(String.format("%.1f", score));
        holder.rankTextView.setText(position+1 + ".");
        holder.nameTextView.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return restauranatlist.size();
    }

    class RankingViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;
        TextView nameTextView;
        TextView scoreTextView;
        TextView rankTextView;

        Float score;

        public RankingViewHolder(@NonNull View rankingView) {
            super(rankingView);

            rankTextView = rankingView.findViewById(R.id.rank);
            mImageView = rankingView.findViewById(R.id.restorant_image);
            nameTextView = rankingView.findViewById(R.id.restorant_name);
            scoreTextView = rankingView.findViewById(R.id.restorant_score);

            nameTextView.setSelected(true);

            rankingView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        Toast.makeText(context ,restauranatlist.get(pos).getRestorant_name(), Toast.LENGTH_SHORT).show();
                        Intent restorant_information = new Intent(context, Sickdang_Jeongbo.class);
                        restorant_information.putExtra("restorant_name",restauranatlist.get(pos).getRestorant_name());
                        context.startActivity(restorant_information);
                    }
                }
            });

        }
    }
}