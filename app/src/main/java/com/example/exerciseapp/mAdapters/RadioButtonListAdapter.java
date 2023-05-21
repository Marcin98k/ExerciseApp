package com.example.exerciseapp.mAdapters;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.ThreeElementLinearListModel;
import com.example.exerciseapp.R;

import java.util.List;

public class RadioButtonListAdapter extends RecyclerView.Adapter<RadioButtonListAdapter.ViewHolder> {

    Context mContext;
    UpdateIntegersDB updateIntegersDB;
    List<ThreeElementLinearListModel> list;

    private String listName;
    int selectedPosition = -1;
    int oldPosition;
    int currentPos;

    public RadioButtonListAdapter(Context context, String listName, List<ThreeElementLinearListModel> list
            , UpdateIntegersDB updateIntegersDB) {
        this.mContext = context;
        this.list = list;
        this.listName = listName;
        this.updateIntegersDB = updateIntegersDB;

        for (int i = 0; i < list.size(); i++) {
            if (listName.equals("userGender") || listName.equals("userLevel")) {
                if (list.get(i).getAction() == 1) {
                    oldPosition = list.get(i).getId();
                    selectedPosition = list.get(i).getId();
                    currentPos = i;
                }
            } else {
                if (list.get(i).getAction() == 1) {
                    oldPosition = i;
                    selectedPosition = i;
                    currentPos = i;
                }
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
            holder.radioButton.setBackgroundColor(Color.BLUE);
        } else {
            holder.radioButton.setBackgroundColor(Color.TRANSPARENT);
        }
        holder.radioButton.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                if (listName.equals("userGender") || listName.equals("userLevel")) {
                    selectedPosition = list.get(holder.getAdapterPosition()).getId();
                } else {
                    selectedPosition = holder.getAdapterPosition();
                }
                currentPos = holder.getAdapterPosition();
                notifyDataSetChanged();
                Log.i(TAG, "RadioButtonListAdapter: " + oldPosition + " " + selectedPosition);
                updateIntegersDB.values(listName, oldPosition, selectedPosition, 0);
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

        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.sCustomRadioButton_button);
        }
    }
}
