package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mModels.ExerciseModel;

import java.util.List;

public class DetailsFragment extends Fragment {

    private ImageView image;
    private TextView name;
    private TextView level;
    private TextView bodyParts;
    private TextView equipment;
    private TextView type;
    private TextView kcal;
    private TextView duration;
    private TextView description;

    private long id;
    private final byte POSITION = 0;

    private ContentBD contentBD;
    private List<ExerciseModel> exerciseDetail;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
            Log.e(TAG, "onCreate: " + id );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_details, container, false);
        contentBD = new ContentBD(requireActivity());
        initView(mView);
        return mView;
    }

    private void initView(View v) {
        image = v.findViewById(R.id.frag_details_image);
        name = v.findViewById(R.id.frag_details_name);
        level = v.findViewById(R.id.frag_details_level);
        bodyParts = v.findViewById(R.id.frag_details_body_parts);
        equipment = v.findViewById(R.id.frag_details_equipment);
        type = v.findViewById(R.id.frag_details_type);
        kcal = v.findViewById(R.id.frag_details_kcal);
        duration = v.findViewById(R.id.frag_details_duration);
        description = v.findViewById(R.id.frag_details_description);

        exerciseDetail = contentBD.showExerciseById(id);

//        image.setImageResource(exerciseDetail.get(0).getImage());

        name.setText(exerciseDetail.get(POSITION).getName());
        level.setText(String.valueOf(exerciseDetail.get(POSITION).getLevel()));
        bodyParts.setText(exerciseDetail.get(POSITION).getBodyParts());
        equipment.setText(exerciseDetail.get(POSITION).getEquipment());
        type.setText(String.valueOf(exerciseDetail.get(POSITION).getType()));
        kcal.setText(String.valueOf(exerciseDetail.get(POSITION).getKcal()));
        duration.setText(String.valueOf(exerciseDetail.get(POSITION).getDuration()));
        description.setText(String.valueOf(exerciseDetail.get(POSITION).getDescription()));
    }
}