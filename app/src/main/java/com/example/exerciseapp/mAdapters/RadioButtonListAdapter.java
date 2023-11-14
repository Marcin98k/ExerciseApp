package com.example.exerciseapp.mAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mClasses.GlobalClass;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.ThreeElementLinearListModel;

import java.util.List;

public class RadioButtonListAdapter extends RecyclerView.Adapter<RadioButtonListAdapter.ViewHolder> {

    private Context mContext;

    private List<ThreeElementLinearListModel> list;
    private String listName;
    int selectedPosition = -1;
    int oldPosition;
    int currentPos;


    private UpdateIntegersDB updateIntegersDB;

    public RadioButtonListAdapter(Context context, String listName, List<ThreeElementLinearListModel> list
            , UpdateIntegersDB updateIntegersDB) {
        this.mContext = context;
        this.list = list;
        this.listName = listName;
        this.updateIntegersDB = updateIntegersDB;

        setCheckedButtons();
    }

    private void setCheckedButtons() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getAction() == 1) {
                oldPosition = list.get(i).getId();
                selectedPosition = list.get(i).getId();
                currentPos = i;
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_radio_button, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.radioButton.setText(list.get(position).getName());
        holder.radioButton.setChecked(position == currentPos);
        if (holder.radioButton.isChecked()) {
            holder.radioButton.setBackgroundResource(R.drawable.selected_radio_button);
        } else {
            holder.radioButton.setBackgroundResource(R.drawable.unselected_radio_button);
        }

        holder.radioButton.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                selectedPosition = list.get(holder.getBindingAdapterPosition()).getId();
                currentPos = holder.getBindingAdapterPosition();
                notifyDataSetChanged();
                updateIntegersDB.values(listName, oldPosition, selectedPosition, 0,
                        GlobalClass.FOURTH_VAL);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.sCustomRadioButton_button);
        }
    }
}
