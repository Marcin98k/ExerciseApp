package com.example.exerciseapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.exerciseapp.Models.ThreeElementLinearListModel;
import com.example.exerciseapp.R;
import com.example.exerciseapp.StorageClass;

import java.util.List;

public class ThreeElementLinearListAdapter extends ArrayAdapter<ThreeElementLinearListModel> {

    Context mContext;

    private int mResource;

    static class ViewHolder {

        private ImageView icon;
        private TextView name;
        private ImageView action;

    }

    public ThreeElementLinearListAdapter(@NonNull Context context, int resource, List<ThreeElementLinearListModel> list) {
        super(context, resource, list);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String image = getItem(position).getImage();
        int icon = getItem(position).getIcon();
        String name = getItem(position).getName();
        int action = getItem(position).getAction();

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(mResource, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.icon = convertView.findViewById(R.id.sThreeElementLinearBlock_icon);
            viewHolder.name = convertView.findViewById(R.id.sThreeElementLinearBlock_name);
            viewHolder.action = convertView.findViewById(R.id.sThreeElementLinearBlock_action);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (icon <= 0) {
            viewHolder.icon.setImageBitmap(new StorageClass(mContext, image, ".png").setInternalBitmapFile());
        } else {
            viewHolder.icon.setImageResource(icon);
        }
        viewHolder.name.setText(name);
        viewHolder.action.setImageResource(action);

        return convertView;
    }
}
