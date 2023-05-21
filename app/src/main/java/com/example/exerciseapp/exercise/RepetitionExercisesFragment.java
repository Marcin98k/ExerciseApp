package com.example.exerciseapp.exercise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.exerciseapp.R;

public class RepetitionExercisesFragment extends Fragment {


    private TextView countRepetitions;
    private TextView currentSet;
    private TextView sumSet;
    private Button nextBtn;

    public RepetitionExercisesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_repetition_exercises, container, false);
        initView(mView);
        
        return mView;
    }

    private void initView(View v) {
        countRepetitions = v.findViewById(R.id.fRepetitionExercises_showCount);
        currentSet = v.findViewById(R.id.fRepetitionExercises_currentSet);
        sumSet = v.findViewById(R.id.fRepetitionExercises_sumSets);
        nextBtn = v.findViewById(R.id.fRepetitionExercises_next);
    }
}