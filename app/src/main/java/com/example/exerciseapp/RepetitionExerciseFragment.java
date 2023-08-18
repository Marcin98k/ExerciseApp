package com.example.exerciseapp;

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

import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mInterfaces.FragmentRespond;
import com.example.exerciseapp.mInterfaces.FragmentSupportListener;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.ExerciseModel;
import com.example.exerciseapp.mModels.IntegerModel;

import java.util.List;

public class RepetitionExerciseFragment extends Fragment implements FragmentRespond {

    private long id;
    private byte currentSet;
    private int rest;
    private int fromWhere;
    private int last = 0;

    private static final String FRAGMENT_TAG = "RepetitionExerciseFragment";

    private ContentBD contentBD;

    private FragmentSupportListener fragmentSupportListener;
    private UpdateIntegersDB updateIntegersDB;

    public RepetitionExerciseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
            fromWhere = getArguments().getInt("fromWhere");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_repetition_exercise, container, false);
        currentSet = (byte) (currentSet + 1);
        intiView(mView);
        return mView;
    }

    private void intiView(View v) {

        ImageView imageView = v.findViewById(R.id.frag_repetition_exercise_image);
        TextView nameView = v.findViewById(R.id.frag_repetition_exercise_name);
        TextView countView = v.findViewById(R.id.frag_repetition_exercise_count);
        TextView currentSetView = v.findViewById(R.id.frag_repetition_exercise_current_set);
        TextView sumSetView = v.findViewById(R.id.frag_repetition_exercise_sum_set);

        List<ExerciseModel> exercise;
        contentBD = new ContentBD(requireActivity());
        if (fromWhere == 0) {
            exercise = contentBD.showExerciseById(id);
        } else {
            exercise = contentBD.showUserExerciseById(id);
        }
        if (exercise.isEmpty()) {
            throw new NullPointerException(getContext() + " list is empty");
        } else {
            final byte POSITION = 0;
            ExerciseModel exerciseModel = exercise.get(POSITION);
            List<IntegerModel> extension = contentBD.showExerciseExtensionId(exercise.get(POSITION).getExtension());
            IntegerModel integerModel = extension.get(POSITION);
            int getSet = integerModel.getSecondValue();
            int sumSet = getSet + 1;

//            imageView.setImageResource(exercise.get(POSITION).getImage());
            nameView.setText(exerciseModel.getName());
            countView.setText(String.valueOf(integerModel.getThirdValue()));
            sumSetView.setText(String.valueOf(getSet));
            currentSetView.setText(String.valueOf(currentSet));
            rest = integerModel.getFifthValue();


            if (currentSet == (sumSet - 1)) {
                last = 1;
            }
            if (currentSet == sumSet) {
                last = 0;
                fragmentSupportListener.checkCondition(true);
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentSupportListener = (FragmentSupportListener) context;
            updateIntegersDB = (UpdateIntegersDB) context;
        } catch (RuntimeException e) {
            throw new RuntimeException(context +
                    " must implement FragmentSupportListener and/or UpdateIntegersDB");
        }
    }

    @Override
    public void fragmentMessage() {
        updateIntegersDB.values(FRAGMENT_TAG, rest, 1,
                1, last);
    }
}