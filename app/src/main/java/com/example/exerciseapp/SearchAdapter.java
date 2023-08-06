package com.example.exerciseapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

    private List<ExerciseModel> exerciseList = new ArrayList<>();
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
                    resultList.get(viewHolder.getAdapterPosition()).getId(), 0, resultList.get(0).getId());
//        iNewExercise.createExercise(listName, resultList.get(0).getId());
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.iconIMG.setImageResource(resultList.get(position).getIcon());
        holder.nameTV.setText(resultList.get(position).getName());
        holder.typeTV.setText(resultList.get(position).getType());
//        if (resultList.get(position).getType() == 1) {
//            holder.typeTV.setText("Repetition");
//        } else if (resultList.get(position).getType() == 2) {
//            holder.typeTV.setText("Time");
//        } else {
//            holder.typeTV.setText("");
//        }

//        holder.imageTV.setImageBitmap(exerciseList.get(position).getImage());
//        holder.nameTV.setText(exerciseList.get(position).getName());
//        holder.bodyPartTV.setText(exerciseList.get(position).getBodyParts());
//        holder.kcalTV.setText(exerciseList.get(position).getKcal());
//        holder.levelTV.setText(exerciseList.get(position).getLevel());
//        holder.equipmentTV.setText(exerciseList.get(position).getEquipment());
//        holder.typeTV.setText(exerciseList.get(position).getType());
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
        TextView bodyPartTV;
        TextView levelTV;
        TextView typeTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageTV = itemView.findViewById(R.id.asset_four_elements_block_image);
            levelIMG = itemView.findViewById(R.id.asset_four_elements_block_lvl_image);
            nameTV = itemView.findViewById(R.id.asset_four_elements_block_name);
            bodyPartTV = itemView.findViewById(R.id.asset_four_elements_block_body);
            levelTV = itemView.findViewById(R.id.asset_four_elements_block_lvl);
            typeTV = itemView.findViewById(R.id.asset_four_elements_block_type);
        }
    }
}
