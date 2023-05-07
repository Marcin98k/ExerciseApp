package com.example.exerciseapp.Adapters;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciseapp.Interfaces.UpdateStringsDB;
import com.example.exerciseapp.Interfaces.UpdateValueDB;
import com.example.exerciseapp.Models.FourElementLinearListModel;
import com.example.exerciseapp.R;

import java.util.List;

public class FourElementLinearListAdapter extends RecyclerView.Adapter<FourElementLinearListAdapter.ViewHolder> {

    Context mContext;

    UpdateValueDB valueDB;
    UpdateStringsDB updateStringsDB;

    private List<FourElementLinearListModel> list;

    private String listName;

    private int oldPosition = 0;

    public FourElementLinearListAdapter() {

    }
    public FourElementLinearListAdapter(Context context, List<FourElementLinearListModel> list,
                                        String listName, UpdateValueDB valueDB) {
        this.mContext = context;
        this.list = list;
        this.listName = listName;
        this.valueDB = valueDB;

        if (listName.equals("userGoals")) {
            for (int i = 0; i < list.size(); i++) {
                if (Integer.parseInt(list.get(i).getFirstValue()) == 1) {
                    oldPosition = list.get(i).getId();
                }
            }
        }
    }
    public FourElementLinearListAdapter(Context context, List<FourElementLinearListModel> list,
                                        String listName, UpdateStringsDB updateStringsDB) {
        this.mContext = context;
        this.list = list;
        this.listName = listName;
        this.updateStringsDB = updateStringsDB;

        if (this.listName.equals("tagTELL_account")) {
            Log.e(TAG, "FourElementLinearListAdapter: "
                    + list.toString());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.four_element_linear_block, parent, false);

        ViewHolder viewHolder = new ViewHolder(mView);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = list.get(viewHolder.getAdapterPosition()).getId();

                if (listName.equals("userInformation")) {
                    valueDB.values(listName, viewHolder.getAdapterPosition(), pos, list.get(viewHolder.getAdapterPosition()).getSecondId());
                    Log.i(TAG, "onClick(userInfo): " + viewHolder.getAdapterPosition() + " position: " + pos);
                } else if (listName.equals("userPerformance")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    alert.setTitle(list.get(viewHolder.getAdapterPosition()).getName());
                    final View customLayout = LayoutInflater.from(mContext).inflate(R.layout.alert_window, parent, false);
                    alert.setView(customLayout);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EditText editText = customLayout.findViewById(R.id.sAlertWindow_editText);
                            int value = Integer.parseInt(editText.getText().toString());
                            Log.i(TAG, "onClick: " + value);

                            valueDB.values(listName, list.get(viewHolder.getAdapterPosition()).getId(),
                                    value, 0);
                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();

                } else if (listName.equals("userGoals")) {

                    int temp;
                    Log.i(TAG, "onClick: " + viewHolder.getAdapterPosition());
                    if (list.get(viewHolder.getAdapterPosition()).getFirstValue().equals("0")) {
                        list.get(viewHolder.getAdapterPosition()).setFirstValue("1");
                        temp = 1;
                    } else {
                        list.get(viewHolder.getAdapterPosition()).setFirstValue("0");
                        temp = 0;
                    }

                    valueDB.values(listName, list.get(viewHolder.getAdapterPosition()).getId(),
                            temp,0);
                } else if (listName.equals("tagTELL_account")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    alert.setTitle(list.get(viewHolder.getAdapterPosition()).getName());
                    final View customLayout = LayoutInflater.from(mContext).inflate(R.layout.alert_window, parent, false);
                    alert.setView(customLayout);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EditText editText = customLayout.findViewById(R.id.sAlertWindow_editText);
                            String value = editText.getText().toString();
                            Log.i(TAG, "onClick: " + value);

                            updateStringsDB.strValues(listName, viewHolder.getAdapterPosition(),
                                    list.get(viewHolder.getAdapterPosition()).getId(),
                                    value);
                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                    } else {
                    valueDB.values(listName, oldPosition, pos, 0);
                }
                notifyDataSetChanged();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.icon.setImageResource(list.get(position).getIcon());
        holder.name.setText(list.get(position).getName());
        if (list.get(position).getFirstValue().equals("1")) {
            holder.itemView.setBackgroundColor(Color.BLUE);
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
        holder.firstValue.setText(list.get(position).getFirstValue());
        if (listName.equals("userInformation")) {
            holder.secondValue.setText(list.get(position).getSecondValue());
        } else {
            holder.secondValue.setText(" " + list.get(position).getId());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView icon;
        private final TextView name;
        private final TextView firstValue;
        private final TextView secondValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.sFourElementLinearBlock_icon);
            name = itemView.findViewById(R.id.sFourElementLinearBlock_name);
            firstValue = itemView.findViewById(R.id.sFourElementLinearBlock_firstValue);
            secondValue = itemView.findViewById(R.id.sFourElementLinearBlock_secondValue);
        }
    }
}
