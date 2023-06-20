package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.ExerciseModel;
import com.example.exerciseapp.mModels.FourElementsModel;

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
    private SearchAdapter adapter;
    private UpdateIntegersDB updateIntegersDB;
    private List<FourElementsModel> exercisesList;

    private ContentBD contentBD;
    private long id = 0;
    private int durationSum;
    private int kcalSum;
    private final byte POSITION = 0;
    private List<ExerciseModel> workoutList;


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

        contentBD = new ContentBD(requireActivity());
        workoutList = contentBD.showWorkoutById(id);

        nameView.setText(workoutList.get(POSITION).getName());
        levelView.setText(String.valueOf(workoutList.get(POSITION).getLevel()));
        bodyPartsView.setText(String.valueOf(workoutList.get(POSITION).getBodyParts()));
        equipmentView.setText(workoutList.get(POSITION).getEquipment());
        kcalView.setText(String.valueOf(workoutList.get(POSITION).getKcal()));
        durationView.setText(String.valueOf(workoutList.get(POSITION).getDuration()));
        descriptionView.setText(workoutList.get(POSITION).getDescription());

        String temp = contentBD.showWorkoutById(workoutList.get(POSITION).getId()).get(0).getExerciseId();
        String[] exerciseStr = temp.split(",");
        long[] exercisesId = new long[exerciseStr.length];
        for (int i = 0; i < exercisesId.length; i++) {
            exercisesId[i] = Long.parseLong(exerciseStr[i]);
        }
        exercisesList = new ArrayList<>();

        for (int i = 0; i < exercisesId.length; i++) {
            List<ExerciseModel> temp2 = contentBD.showExerciseById(exercisesId[i]);
            FourElementsModel model = new FourElementsModel(temp2.get(0).getId(),
                    temp2.get(0).getImage(), temp2.get(0).getName(),
                    String.valueOf(temp2.get(0).getKcal()), R.drawable.ic_hexagon);
            exercisesList.add(model);
            Log.i(TAG + " " + getContext().toString(), " exe: " + exercisesId[i]);
        }

        adapter = new SearchAdapter(requireContext(), exercisesList, "workoutList", updateIntegersDB);
        exerciseListView.setHasFixedSize(true);
        exerciseListView.setLayoutManager(new LinearLayoutManager(requireActivity(),
                RecyclerView.VERTICAL, false));
        exerciseListView.setAdapter(adapter);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            updateIntegersDB = (UpdateIntegersDB) context;
        } catch (RuntimeException e) {
            throw new RuntimeException(context.toString() +
                    " must implement UpdateIntegersDB");
        }
    }
}