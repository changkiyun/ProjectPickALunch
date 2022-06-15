package com.example.projectpickalunch.menu_picker.menu_picker_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectpickalunch.R;

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

        return convertView;

    }

}
