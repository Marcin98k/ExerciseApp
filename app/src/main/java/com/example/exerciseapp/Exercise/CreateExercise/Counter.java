package com.example.exerciseapp.Exercise.CreateExercise;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mClasses.TrainingTimer;
import com.example.exerciseapp.mEnums.ExerciseType;
import com.example.exerciseapp.mInterfaces.OnIntPass;

public class Counter extends Fragment implements View.OnClickListener {

    private static final String ARG_EXERCISE_TYPE = "exerciseType";
    private static final String ARG_SCORE = "score";
    private final int INCREASE_TIME_AT = 5;
    private final int INCREASE_REPETITION_AT = 1;

    private TextView scoreTxt;

    private int score;
    private ExerciseType exerciseType;

    private TrainingTimer trainingTimer;
    private OnIntPass onIntPass;

    public Counter() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onIntPass = (OnIntPass) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement OnIntPass");
        }
    }

    public static Counter newInstance(ExerciseType exerciseType, int defaultScore) {
        Counter fragment = new Counter();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EXERCISE_TYPE, exerciseType);
        args.putInt(ARG_SCORE, defaultScore);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exerciseType = (ExerciseType) getArguments().getSerializable(ARG_EXERCISE_TYPE);
            int valueFromViewModel = getArguments().getInt(ARG_SCORE);
            if (valueFromViewModel != -1) {
                score = valueFromViewModel;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_counter, container, false);
        initializeTimer();
        initializeWidgets(mView);
        setInitialValues();
        updateUI();
        onIntPassListener();
        return mView;
    }

    private void initializeTimer() {
        trainingTimer = new TrainingTimer.TrainingTimerBuilder(requireActivity()).build();
    }

    private void initializeWidgets(View v) {
        scoreTxt = v.findViewById(R.id.text_score_counter);
        getButton(v, R.id.button_plus_counter);
        getButton(v, R.id.button_minus_counter);
    }

    private void setInitialValues() {
        if (score == 0) {
            if (exerciseType == ExerciseType.REPETITION) {
                score = INCREASE_REPETITION_AT;
            } else {
                score = INCREASE_TIME_AT;
            }
        }
    }

    private void getButton(View view, int id) {
        Button button = view.findViewById(id);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        try {
            switch (view.getId()) {
                case (R.id.button_plus_counter):
                    increment(exerciseType);
                    break;
                case (R.id.button_minus_counter):
                    decrement(exerciseType);
                    break;
            }
        } finally {
            updateUI();
            onIntPassListener();
        }
    }

    private void updateUI() {
        scoreTxt.setText(String.valueOf(score));
    }

    private void onIntPassListener() {
        if (onIntPass != null) {
            onIntPass.onIntPass(getTag(), score);
        }
    }

    void increment(ExerciseType exerciseType) {
        if (exerciseType == ExerciseType.REPETITION) {
            incrementRepetition();
        } else {
            incrementTime();
        }
    }

    void decrement(ExerciseType exerciseType) {
        if (exerciseType == ExerciseType.REPETITION) {
            decrementRepetition();
        } else {
            decrementTime();
        }
    }

    private void incrementRepetition() {
        score += INCREASE_REPETITION_AT;
    }

    private void decrementRepetition() {
        if (score > INCREASE_REPETITION_AT) {
            score -= INCREASE_REPETITION_AT;
        }
    }

    private void incrementTime() {
        trainingTimer.addSeconds(INCREASE_TIME_AT);
        score += INCREASE_TIME_AT;
    }

    private void decrementTime() {
        if (score > INCREASE_TIME_AT) {
            trainingTimer.subtractSeconds(INCREASE_TIME_AT);
            score -= INCREASE_TIME_AT;
        }
    }
}