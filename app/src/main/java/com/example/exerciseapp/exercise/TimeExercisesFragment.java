package com.example.exerciseapp.exercise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.exerciseapp.mClasses.ClockClass;
import com.example.exerciseapp.R;

public class TimeExercisesFragment extends Fragment {

    private ProgressBar progressBar;
    private TextView showTime;

    private TextView currentSet;
    private TextView sumSet;

    private Button pauseBtn;
    private Button nextBtn;

    public TimeExercisesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_time_exercises, container, false);
        initView(mView);

        new ClockClass(requireContext(), true, 78)
                .setTextView(showTime).setBar(progressBar).runClock();
        return mView;
    }

    private void initView(View v) {
        progressBar = v.findViewById(R.id.fTimeExercises_progressBar);
        showTime = v.findViewById(R.id.fTimeExercises_showTime);
        currentSet = v.findViewById(R.id.fTimeExercises_currentSet);
        sumSet = v.findViewById(R.id.fTimeExercises_SumSets);
        pauseBtn = v.findViewById(R.id.fTimeExercise_pause);
        nextBtn = v.findViewById(R.id.fTimeExercise_next);
    }
}