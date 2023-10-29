package com.example.exerciseapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.mClasses.ClockClass;
import com.example.exerciseapp.mClasses.GlobalClass;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;

public class TimeBreakFragment extends Fragment {

    private static final String FRAGMENT_TAG = "TimeBreakFragment";

    private ProgressBar progressBar;
    private TextView showTime;
    private Button skipBtn;
    private Button addBtn;


    private int rest;
    private String exerciseName;

    private ClockClass clockClass;
    private UpdateIntegersDB updateIntegersDB;

    public TimeBreakFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            updateIntegersDB = (UpdateIntegersDB) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context +
                    " must implement UpdateIntegersDB");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rest = getArguments().getInt("rest");
            exerciseName = getArguments().getString("exerciseName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_time_break, container, false);
        initView(mView);
        setupClockClass();
        return mView;
    }

    private void initView(View v) {
        progressBar = v.findViewById(R.id.frag_time_break_progress_bar);
        showTime = v.findViewById(R.id.frag_time_break_tv_show_time);
        addBtn = v.findViewById(R.id.frag_time_break_btn_add);
        skipBtn = v.findViewById(R.id.frag_time_break_btn_skip);

        TextView showNext = v.findViewById(R.id.frag_time_break_tv_next);
        showNext.setText(exerciseName);
    }

    private void setupClockClass() {
        clockClass = new ClockClass(requireContext(), true, rest, true)
                .setBar(progressBar)
                .setAddBtn(addBtn).setSkipBtn(skipBtn)
                .setTextView(showTime);

        clockClass.setFragmentSupportListener(param -> {
            if (updateIntegersDB != null) {
                updateIntegersDB.values(FRAGMENT_TAG, 0, 3, 0,
                        GlobalClass.FOURTH_VAL);
            }
        });
        clockClass.runClock();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clockClass.stopThread();
    }

    public void setUpdateIntegersDB(UpdateIntegersDB updateIntegersDB) {
        this.updateIntegersDB = updateIntegersDB;
    }
}