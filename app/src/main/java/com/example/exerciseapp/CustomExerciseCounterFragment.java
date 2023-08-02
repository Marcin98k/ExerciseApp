package com.example.exerciseapp;

import android.content.Context;
import android.os.Bundle;
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

    private Button plusSetsBtn, minusSetsBtn;
    private TextView showSetsVolume;
    private Button plusExerciseVolumeBtn, minusExerciseVolumeBtn;
    private TextView showExerciseVolume;
    private Button plusRestVolumeBtn, minusRestVolumeBtn;
    private TextView showRestVolume;


    private int numberOfSets = 1;
    private int trainingVolume;
    private int restVolume;


    private static final int INCREASE_TIME_AT = 5;


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
        iNewExercise.createExercise("selectExercise", exerciseType,
                numberOfSets, trainingVolume, restVolume);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_custom_exercise_counter, container, false);
        initView(mView);

        return mView;
    }

    private void initView(View v) {

        plusSetsBtn = v.findViewById(R.id.frag_custom_exercise_btn_plus_sets);
        plusSetsBtn.setOnClickListener(this);
        minusSetsBtn = v.findViewById(R.id.frag_custom_exercise_btn_minus_sets);
        minusSetsBtn.setOnClickListener(this);
        showSetsVolume = v.findViewById(R.id.frag_custom_exercise_txt_sets);
        showSetsVolume.setText(String.valueOf(numberOfSets));

        plusExerciseVolumeBtn = v.findViewById(R.id.frag_custom_exercise_btn_plus_exercise_volume);
        plusExerciseVolumeBtn.setOnClickListener(this);
        minusExerciseVolumeBtn = v.findViewById(R.id.frag_custom_exercise_btn_minus_exercise_volume);
        minusExerciseVolumeBtn.setOnClickListener(this);
        showExerciseVolume = v.findViewById(R.id.frag_custom_exercise_txt_exercise_volume);

        plusRestVolumeBtn = v.findViewById(R.id.frag_custom_exercise_btn_plus_rest_volume);
        plusRestVolumeBtn.setOnClickListener(this);
        minusRestVolumeBtn = v.findViewById(R.id.frag_custom_exercise_btn_minus_rest_volume);
        minusRestVolumeBtn.setOnClickListener(this);
        showRestVolume = v.findViewById(R.id.frag_custom_exercise_txt_rest_volume);

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
                    numberOfSets++;
                    break;
                case (R.id.frag_custom_exercise_btn_minus_sets):
                    if (numberOfSets > 1) numberOfSets--;
                    break;
                case (R.id.frag_custom_exercise_btn_plus_exercise_volume):
                    if (exerciseType == ExerciseType.TIME) {
                        trainingVolume += INCREASE_TIME_AT;
                        clockClassExerciseVolume.addSecond(INCREASE_TIME_AT);
                        break;
                    }
                    trainingVolume++;
                    break;
                case (R.id.frag_custom_exercise_btn_minus_exercise_volume):
                    if (exerciseType == ExerciseType.TIME) {
                        if (trainingVolume > INCREASE_TIME_AT) {
                            clockClassExerciseVolume.minusSecond(INCREASE_TIME_AT);
                            trainingVolume -= INCREASE_TIME_AT;
                        }
                        break;
                    }
                    if (trainingVolume > 1) trainingVolume--;
                    break;
                case (R.id.frag_custom_exercise_btn_plus_rest_volume):
                    clockClassRestVolume.addSecond(INCREASE_TIME_AT);
                    restVolume += INCREASE_TIME_AT;
                    break;
                case (R.id.frag_custom_exercise_btn_minus_rest_volume):
                    if (restVolume > INCREASE_TIME_AT) {
                        clockClassRestVolume.minusSecond(INCREASE_TIME_AT);
                        restVolume -= INCREASE_TIME_AT;
                    }
                    break;
                default:
            }
        } finally {
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
}