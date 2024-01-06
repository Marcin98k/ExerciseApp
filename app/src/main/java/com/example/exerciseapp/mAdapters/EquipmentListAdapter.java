package com.example.exerciseapp.mAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mDatabases.Training.Tabels.Equipment.EquipmentModel;
import com.example.exerciseapp.mDatabases.Training.TrainingRepository;
import com.example.exerciseapp.mModels.ItemListModel;

import java.util.List;
import java.util.Map;

public class EquipmentListAdapter extends ArrayAdapter<EquipmentModel> {

    private final Context context;
    private final List<ItemListModel> itemListModel;

    public EquipmentListAdapter(@NonNull Context context,
                                @NonNull List<ItemListModel> itemListModel) {
        super(context, R.layout.list_item);
        this.context = context;
        this.itemListModel = itemListModel;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image_list_item);
            viewHolder.name = (TextView) convertView.findViewById(R.id.text_name_list_item);
            viewHolder.value = (TextView) convertView.findViewById(R.id.text_value_list_item);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ItemListModel itemListModel1 = itemListModel.get(position);
//        viewHolder.image.setImageResource(equipment.getImage());
        viewHolder.name.setText(itemListModel1.getName());
        viewHolder.value.setText(String.valueOf(itemListModel1.getValue()));

        return convertView;
    }

    @Override
    public int getCount() {
        return itemListModel.size();
    }

    static class ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView value;
    }
}
