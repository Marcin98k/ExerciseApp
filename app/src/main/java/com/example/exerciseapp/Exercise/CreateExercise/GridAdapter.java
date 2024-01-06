package com.example.exerciseapp.Exercise.CreateExercise;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.exerciseapp.R;

import java.util.List;

public class GridAdapter extends BaseAdapter {

    private final Context mContext;
    private final LayoutInflater mInflater;
    private final List<GridModel> gridList;

    private final List<Integer> selectedPositions;

    public GridAdapter(Context context, List<GridModel> gridList, List<Integer> selectedPositions) {
        this.mContext = context;
        this.gridList = gridList;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.selectedPositions = selectedPositions;
    }

    static class ViewHolder {
        private ImageView icon;
        private TextView name;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.grid_view_item, parent, false);

            holder = new ViewHolder();
            initializeWidgets(holder, convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        setSelectedItems(position, convertView, parent);
        fillItem(holder, position, convertView);

        return convertView;
    }

    private void initializeWidgets(ViewHolder holder, View convertView) {
        holder.icon = (ImageView) convertView.findViewById(R.id.image_grid_view_item);
        holder.name = (TextView) convertView.findViewById(R.id.text_grid_view_name_item);
    }

    private void fillItem(ViewHolder holder, int position, View convertView) {
        GridModel equipment = gridList.get(position);
        setImage(holder, equipment);
        holder.name.setText(equipment.getName());
        itemStatus(convertView, position);
    }

    private void setImage(ViewHolder holder, GridModel gridModel) {

        if (gridModel.getImage() == null || gridModel.getImage().isEmpty()) {
            holder.icon.setImageResource(R.drawable.ic_dumbbell);
        } else {
            holder.icon.setImageURI(Uri.parse(gridModel.getImage()));
        }
    }

    private void setSelectedItems(int position, View convertView, ViewGroup parent) {
        if (!selectedPositions.isEmpty()) {
            for (int selected : selectedPositions) {
                if (selected == gridList.get(position).getId()) {
                    View itemView = getViewByPosition(convertView, parent);
                    changeItemBackground(selected, itemView);
                }
            }
        }
    }

    private View getViewByPosition(View convertView, ViewGroup parent) {
        if (convertView != null) {
            return convertView;
        } else {
            return mInflater.inflate(R.layout.grid_view_item, parent, false);
        }
    }

    private void itemStatus(View convertView, int position) {

        convertView.setOnClickListener(view -> {
            int equipmentId = gridList.get(position).getId();
            addOrRemoveFromList(equipmentId);
            changeItemBackground(equipmentId, view);
        });
    }

    private void addOrRemoveFromList(int equipmentId) {

        if (selectedPositions.contains(equipmentId)) {
            selectedPositions.remove(Integer.valueOf(equipmentId));
        } else {
            selectedPositions.add(equipmentId);
        }
    }

    private void changeItemBackground(int equipmentId, View view) {

        if (selectedPositions.contains(equipmentId)) {
            view.setBackgroundResource(R.drawable.selected_item);
        } else {
            view.setBackgroundResource(R.drawable.unselected_item);
        }
    }


    public List<Integer> getIdsFromSelectedPositions() {
        return selectedPositions;
    }

    @Override
    public int getCount() {
        return gridList.size();
    }

    @Override
    public Object getItem(int position) {
        return gridList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}