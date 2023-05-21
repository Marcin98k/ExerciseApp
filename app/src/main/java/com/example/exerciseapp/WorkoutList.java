package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mModels.ExerciseModel;
import com.example.exerciseapp.mModels.IntegerModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkoutList extends Fragment {

    private ImageView imageView;
    private TextView nameView;
    private TextView levelView;
    private TextView bodyPartsView;
    private TextView equipmentView;
    private TextView kcalView;
    private TextView durationView;
    private TextView descriptionView;

    private RecyclerView exerciseListView;


    private ContentBD contentBD;
    private long id = 0;
    private String exercises;
    private String[] exercisesArray;
    private int durationSum;
    private int kcalSum;
    private final byte POSITION = 0;
    private List<ExerciseModel> workoutList;
    private List<List<ExerciseModel>> exerciseList = new ArrayList<>();


    public WorkoutList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_workout_list, container, false);
        contentBD = new ContentBD(requireActivity());
        workoutList = contentBD.showWorkoutById(id);
        exercisesArray = workoutList.get(POSITION).getExerciseId().split(",");
        for (int i = 0; i < exercisesArray.length; i++) {
            exerciseList.add(contentBD.showExerciseById(Integer.parseInt(exercisesArray[i])));
            Log.i(TAG, "onCreateView: " + exercisesArray[i]);

        }
        initView(mView);
        return mView;
    }

    private void initView(View v) {
        imageView = v.findViewById(R.id.frag_workout_list_image);
        nameView = v.findViewById(R.id.frag_workout_list_name);
        levelView = v.findViewById(R.id.frag_workout_list_level);
        bodyPartsView = v.findViewById(R.id.frag_workout_list_body_parts);
        equipmentView = v.findViewById(R.id.frag_workout_list_equipment);
        kcalView = v.findViewById(R.id.frag_workout_list_kcal);
        durationView = v.findViewById(R.id.frag_workout_list_duration);
        descriptionView = v.findViewById(R.id.frag_workout_list_description);
        exerciseListView = v.findViewById(R.id.frag_workout_list_exercise_list);

        nameView.setText(workoutList.get(POSITION).getName());
        levelView.setText(String.valueOf(workoutList.get(POSITION).getLevel()));
        bodyPartsView.setText(String.valueOf(workoutList.get(POSITION).getBodyParts()));
        equipmentView.setText(workoutList.get(POSITION).getEquipment());
        kcalView.setText(String.valueOf(workoutList.get(POSITION).getKcal()));
        durationView.setText(String.valueOf(workoutList.get(POSITION).getDuration()));
        descriptionView.setText(workoutList.get(POSITION).getDescription() +
                "\n workoutList: \n" + workoutList.get(POSITION).getExerciseId() +
                "\n exerciseArray: \n" + Arrays.toString(exercisesArray));

        Log.i(TAG, "initView: " + workoutList.toString());
        Log.i(TAG, "initView: ||break|| ");
        for (int i = 0; i < exerciseList.size(); i++) {
            Log.i(TAG, "\n initView: " + exerciseList.get(i).get(POSITION).getName());
        }
    }
}