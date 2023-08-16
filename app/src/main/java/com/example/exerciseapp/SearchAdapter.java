package com.example.exerciseapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciseapp.mClasses.ClockClass;
import com.example.exerciseapp.mInterfaces.INewExercise;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.ExerciseModel;
import com.example.exerciseapp.mModels.FourElementsModel;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements
        Filterable {

    private Context mContext;
    private List<FourElementsModel> resultList;
    private List<FourElementsModel> resultListFull;
    private String listName;
    private int fromWhere;
    private UpdateIntegersDB updateIntegersDB;


    public SearchAdapter(Context mContext, List<FourElementsModel> resultList, String listName,
                         UpdateIntegersDB updateIntegersDB) {
        this.mContext = mContext;
        this.resultList = resultList;
        this.resultListFull = new ArrayList<>(resultList);
        this.listName = listName;
        this.updateIntegersDB = updateIntegersDB;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.four_elements_block, parent, false);
        ViewHolder viewHolder = new ViewHolder(mView);
        mView.setOnClickListener(v -> {
            updateIntegersDB.values(listName,
                    resultList.get(viewHolder.getAdapterPosition()).getId(),
                    resultList.get(viewHolder.getBindingAdapterPosition()).getFromWhere(),
                    resultList.get(0).getId());
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nameTV.setText(resultList.get(position).getName());

        switch (listName) {
            case "secondList":
            case "workoutList":
                holder.typeTV.setText("---");
                break;
            default:
                holder.typeTV.setText(resultList.get(position).getType().equals("1") ?
                        "Repetition" : "Time");
        }
        new ClockClass(mContext).setSecond(resultList.get(position).
                getDuration()).dynamicIncreaseTime(holder.durationTV);

        String[] names = {mContext.getString(R.string.beginner),
                mContext.getString(R.string.intermediate), mContext.getString(R.string.advanced)};
        if (resultList.get(position).getLevel() == 0) {
            holder.levelTV.setText(mContext.getString(R.string.nan));
        } else {
            holder.levelTV.setText(names[resultList.get(position).getLevel() - 1]);
        }
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<FourElementsModel> filterList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filterList.addAll(resultListFull);
            } else {

                String filterPattern = charSequence.toString().trim();
                for (FourElementsModel model : resultListFull) {
                    if (model.getName().contains(filterPattern)) {
                        filterList.add(model);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            resultList.clear();
            resultList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageTV;
        ImageView levelIMG;
        TextView nameTV;
        TextView levelTV;
        TextView typeTV;
        TextView durationTV;
        ImageView typeIMG;
        ImageView durationIMG;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageTV = itemView.findViewById(R.id.asset_four_elements_block_image);
            levelIMG = itemView.findViewById(R.id.asset_four_elements_block_lvl_image);
            nameTV = itemView.findViewById(R.id.asset_four_elements_block_name);
            levelTV = itemView.findViewById(R.id.asset_four_elements_block_lvl);
            typeTV = itemView.findViewById(R.id.asset_four_elements_block_type);
            durationTV = itemView.findViewById(R.id.asset_four_elements_block_duration);
            typeIMG = itemView.findViewById(R.id.asset_four_elements_block_type_image);
            durationIMG = itemView.findViewById(R.id.asset_four_elements_block_duration_image);
        }
    }
}
