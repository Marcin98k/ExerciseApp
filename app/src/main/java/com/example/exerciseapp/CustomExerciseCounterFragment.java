package com.example.exerciseapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.mClasses.ClockClass;
import com.example.exerciseapp.mInterfaces.INewExercise;

public class CustomExerciseCounterFragment extends Fragment implements View.OnClickListener {

    private static final String SELECT_EXERCISE = "selectExercise";
    private static final String TAG = "CustomExerciseCounterFragment";
    private static final int INCREASE_TIME_AT = 5;

    private TextView showSetsVolume;
    private TextView showExerciseVolume;
    private TextView showRestVolume;


    private int numberOfSets = 1;
    private int trainingVolume;
    private int restVolume;

    private ExerciseType exerciseType;
    private ClockClass clockClassExerciseVolume;
    private ClockClass clockClassRestVolume;


    private INewExercise iNewExercise;


    public CustomExerciseCounterFragment() {
        // Required empty public constructor
    }

    public CustomExerciseCounterFragment(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            iNewExercise = (INewExercise) context;
        } catch (NullPointerException e) {
            throw new NullPointerException(context.toString() +
                    " must implement INewExercise");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        iNewExercise.createExercise(SELECT_EXERCISE, exerciseType, numberOfSets, trainingVolume,
                restVolume);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_custom_exercise_counter, container, false);
        initView(mView);

        return mView;
    }

    private void initView(View v) {

        initButtons(v);
        initTextView(v);
        initClockClasses();
    }

    private void initButtons(View view) {

        getButton(view, R.id.frag_custom_exercise_btn_plus_sets);
        getButton(view, R.id.frag_custom_exercise_btn_minus_sets);
        getButton(view, R.id.frag_custom_exercise_btn_plus_exercise_volume);
        getButton(view, R.id.frag_custom_exercise_btn_minus_exercise_volume);
        getButton(view, R.id.frag_custom_exercise_btn_plus_rest_volume);
        getButton(view, R.id.frag_custom_exercise_btn_minus_rest_volume);
    }

    private void getButton(View view, int id) {
        Button button = view.findViewById(id);
        button.setOnClickListener(this);
    }

    private void initTextView(View view) {

        showSetsVolume = getTextView(view, R.id.frag_custom_exercise_txt_sets);
        showSetsVolume.setText(String.valueOf(numberOfSets));

        showExerciseVolume = getTextView(view, R.id.frag_custom_exercise_txt_exercise_volume);
        showRestVolume = getTextView(view, R.id.frag_custom_exercise_txt_rest_volume);
    }

    private TextView getTextView(View view, int id) {
        return view.findViewById(id);
    }

    private void initClockClasses() {

        clockClassRestVolume = new ClockClass(requireContext()).setSecond(INCREASE_TIME_AT);
        clockClassRestVolume.dynamicIncreaseTime(showRestVolume);
        restVolume = INCREASE_TIME_AT;

        if (exerciseType == ExerciseType.TIME) {
            clockClassExerciseVolume = new ClockClass(requireContext()).setSecond(INCREASE_TIME_AT);
            trainingVolume = INCREASE_TIME_AT;
            clockClassExerciseVolume.dynamicIncreaseTime(showExerciseVolume);
        } else {
            trainingVolume = 1;
            showExerciseVolume.setText(String.valueOf(trainingVolume));
        }
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case (R.id.frag_custom_exercise_btn_plus_sets):
                    incrementSets();
                    break;
                case (R.id.frag_custom_exercise_btn_minus_sets):
                    decrementSets();
                    break;
                case (R.id.frag_custom_exercise_btn_plus_exercise_volume):
                    incrementExerciseVolume();
                    break;
                case (R.id.frag_custom_exercise_btn_minus_exercise_volume):
                    decrementExerciseVolume();
                    break;
                case (R.id.frag_custom_exercise_btn_plus_rest_volume):
                    incrementRestVolume();
                    break;
                case (R.id.frag_custom_exercise_btn_minus_rest_volume):
                    decrementRestVolume();
                    break;
                default:
                    Log.i(TAG, "onClick: default");
            }
        } finally {
            updateUI();
        }
    }

    private void incrementSets() {
        numberOfSets++;
    }

    private void decrementSets() {
        if (numberOfSets > 1) {
            numberOfSets--;
        }
    }

    private void incrementExerciseVolume() {
        if (exerciseType == ExerciseType.TIME) {
            trainingVolume += INCREASE_TIME_AT;
            clockClassExerciseVolume.addSecond(INCREASE_TIME_AT);
        } else {
            trainingVolume++;
        }
    }

    private void decrementExerciseVolume() {
        if (exerciseType == ExerciseType.TIME) {
            if (trainingVolume > INCREASE_TIME_AT) {
                clockClassExerciseVolume.minusSecond(INCREASE_TIME_AT);
                trainingVolume -= INCREASE_TIME_AT;
            }
        } else {
            if (trainingVolume > 1) {
                trainingVolume--;
            }
        }
    }

    private void incrementRestVolume() {
        clockClassRestVolume.addSecond(INCREASE_TIME_AT);
        restVolume += INCREASE_TIME_AT;
    }

    private void decrementRestVolume() {
        if (restVolume > INCREASE_TIME_AT) {
            clockClassRestVolume.minusSecond(INCREASE_TIME_AT);
            restVolume -= INCREASE_TIME_AT;
        }
    }

    private void updateUI() {
        showSetsVolume.setText(String.valueOf(numberOfSets));
        if (exerciseType == ExerciseType.TIME) {
            clockClassExerciseVolume.dynamicIncreaseTime(showExerciseVolume);
        } else {
            showExerciseVolume.setText(String.valueOf(trainingVolume));
        }
        clockClassRestVolume.dynamicIncreaseTime(showRestVolume);

        if (iNewExercise != null) {
            iNewExercise.createExercise("selectExercise", exerciseType,
                    numberOfSets, trainingVolume, restVolume);
        }
    }
}