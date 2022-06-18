package com.example.projectpickalunch.menu_picker.menu_picker_fragment;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projectpickalunch.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MenuPickerAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {

    private Context context;
    private List list;

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
        Toast.makeText(context,"clicked", Toast.LENGTH_SHORT).show();
    }

    class ViewHolder{
        public ImageView menu_picker_restorant_image;
        public TextView menu_picker_restorant_name;
        public TextView menu_picker_restorant_score;
    }

    public MenuPickerAdapter(Context context, ArrayList list){
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder viewHolder;

        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.menu_picker_item, parent, false);
        }

        viewHolder = new ViewHolder();
        viewHolder.menu_picker_restorant_image = (ImageView) convertView.findViewById(R.id.menu_picker_restorant_image);
        viewHolder.menu_picker_restorant_name = (TextView) convertView.findViewById(R.id.menu_picker_restorant_name);
        viewHolder.menu_picker_restorant_score = (TextView) convertView.findViewById(R.id.menu_picker_restorant_score);

        final MenuPickerItem menuPickerItem = (MenuPickerItem) list.get(position);
        viewHolder.menu_picker_restorant_name.setText(menuPickerItem.getRestorant_name());
        viewHolder.menu_picker_restorant_score.setText(menuPickerItem.getRestorant_score());

        //FireStore에서 이미지 가져와서 뷰에 출력
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child("restorant_image");
        String restorant_image_src = menuPickerItem.getRestorant_image_src();

        if(pathReference == null){
            Toast.makeText(context.getApplicationContext(), "저장소에 사진이 없습니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            //RealTimeDatabase에 저장된 이미지 경로를 가져와서 Storage의 이미지를 참조하고 그리드뷰의 이미지뷰에 출력
            StorageReference getRestorantImage = storageReference.child(restorant_image_src);
            getRestorantImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(context.getApplicationContext()).load(uri).into(viewHolder.menu_picker_restorant_image);
                }
            });
        }

        return convertView;

    }

}
