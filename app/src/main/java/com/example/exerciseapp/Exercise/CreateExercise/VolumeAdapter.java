package com.example.exerciseapp.Exercise.CreateExercise;

import android.content.Context;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mDatabases.Training.Tabels.Equipment.EquipmentModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VolumeAdapter extends RecyclerView.Adapter<VolumeAdapter.ViewHolder> {

    private static final String ERROR_EMPTY = "Cannot be empty";
    private static final String ERROR_NOT_NUMBER = "Only numbers allowed";

    private final Context mContext;
    private final List<EquipmentModel> list;
    private Map<Integer, Double> volumeSetByTheUser;

    public VolumeAdapter(Context mContext, List<EquipmentModel> list,
                         Map<Integer, Double> volumeSetByTheUser) {
        this.mContext = mContext;
        this.list = list;
        this.volumeSetByTheUser = volumeSetByTheUser;
    }

    @NonNull
    @Override
    public VolumeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.volume_item, parent,
                false);
        fillTheEquipmentMapWithDefaultValues();
        return new ViewHolder(itemView);
    }

    private void fillTheEquipmentMapWithDefaultValues() {

        Map<Integer, Double> updateVolume = new HashMap<>();
        for (EquipmentModel equipmentModel: list) {
            Integer id = equipmentModel.getId();
            updateVolume.put(id, volumeSetByTheUser.getOrDefault(id, 0.0));
        }

        volumeSetByTheUser = updateVolume;
    }

    @Override
    public void onBindViewHolder(@NonNull VolumeAdapter.ViewHolder holder, int position) {

        EquipmentModel equipment = list.get(position);
        setVolumeToHashMap(holder, equipment);
        holder.image.setImageURI(Uri.parse(equipment.getImage()));
        holder.name.setText(equipment.getName());

        hideFrame(holder);

        holder.itemView.setOnClickListener(view -> {

            if (holder.frame.getVisibility() == View.VISIBLE) {
                hideFrame(holder);
            } else {
                showFrame(holder);
            }
        });
    }

    private void setVolumeToHashMap(ViewHolder holder, EquipmentModel equipment) {

        holder.volumeValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String volumeText = editable.toString();
                if (volumeText.isEmpty()) {
                    setError(holder.volumeValue, ERROR_EMPTY);
                } else {
                    parseAndSetVolume(volumeText, holder.volumeValue, equipment.getId());
                }
            }
        });
    }

    private void setError(TextView view, String error) {
        view.setError(error);
    }

    private void parseAndSetVolume(String volumeText, TextView view, int id) {
        try {
            Double volume = Double.parseDouble(volumeText);
            volumeSetByTheUser.put(id, volume);
        } catch (NumberFormatException e) {
            setError(view, ERROR_NOT_NUMBER);
        }
    }

    private void hideFrame(ViewHolder holder) {
        holder.icon.setImageResource(R.drawable.ic_double_arrow_down);
        holder.frame.setVisibility(View.GONE);
    }

    private void showFrame(ViewHolder holder) {
        holder.icon.setImageResource(R.drawable.ic_double_arrow_up);
        holder.frame.setVisibility(View.VISIBLE);
    }

    public Map<Integer, Double> getEquipmentVolume() {
        return volumeSetByTheUser;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView name;
        public ImageView icon;
        public FrameLayout frame;
        public EditText volumeValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_volume_exercise_image);
            name = itemView.findViewById(R.id.text_volume_exercise_name);
            icon = itemView.findViewById(R.id.image_volume_status_icon);
            frame = itemView.findViewById(R.id.frame_volume_extension);
            volumeValue = itemView.findViewById(R.id.edit_text_volume_value);
        }
    }
}
