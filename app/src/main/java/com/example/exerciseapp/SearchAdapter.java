package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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

import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.FourElementsModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements Filterable {

    private Context mContext;
    private List<FourElementsModel> resultList;
    private List<FourElementsModel> resultListFull;
    private String listName;

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
        mView.setOnClickListener(v -> updateIntegersDB.values(listName,
                resultList.get(viewHolder.getAdapterPosition()).getId(), 0, 0));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.icon.setImageResource(resultList.get(position).getIcon());
        holder.name.setText(resultList.get(position).getName());
        holder.type.setText(resultList.get(position).getType());
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

                for(FourElementsModel model : resultListFull) {

                    if (model.getName().contains(filterPattern)) {
                        filterList.add(model);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterList;
//                results.count = filterList.size();
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

        ImageView image;
        ImageView icon;
        TextView name;
        TextView type;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.asseta_four_elements_block_image);
            icon = itemView.findViewById(R.id.asseta_four_elements_block_icon);
            name = itemView.findViewById(R.id.asseta_four_elements_block_name);
            type = itemView.findViewById(R.id.asseta_four_elements_block_type);
        }
    }
}
