package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.exerciseapp.mClasses.ClockClass;
import com.example.exerciseapp.mInterfaces.FragmentSupportListener;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;

public class TimeBreakFragment extends Fragment{

    private ProgressBar progressBar;
    private TextView showTime;
    private TextView showNext;
    private Button skipBtn;
    private Button addBtn;


    private int rest;
    private String exerciseName;

    private static final String FRAGMENT_TAG = "TimeBreakFragment";

    private ClockClass clockClass;
    private UpdateIntegersDB updateIntegersDB;

    public TimeBreakFragment() {
        // Required empty public constructor
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

        clockClass = new ClockClass(requireContext(), true, 6,true)
                .setBar(progressBar)
                .setAddBtn(addBtn).setSkipBtn(skipBtn)
                .setTextView(showTime);
        clockClass.setFragmentSupportListener(param -> {
            if (updateIntegersDB != null) {
                updateIntegersDB.values(FRAGMENT_TAG,
                        0, 3, 0);
            }
        });
        showNext.setText(exerciseName);
        clockClass.runClock();
        return mView;
    }

    private void initView(View v) {
        progressBar = v.findViewById(R.id.frag_time_break_progress_bar);
        showTime = v.findViewById(R.id.frag_time_break_tv_show_time);
        showNext = v.findViewById(R.id.frag_time_break_tv_next);
        addBtn = v.findViewById(R.id.frag_time_break_btn_add);
        skipBtn = v.findViewById(R.id.frag_time_break_btn_skip);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            updateIntegersDB = (UpdateIntegersDB) context;
        } catch (RuntimeException e) {
            throw new RuntimeException(context +
                    " must implement FragmentSupportListener and/or UpdateIntegersDB");
        }
    }

    public void setUpdateIntegersDB(UpdateIntegersDB updateIntegersDB) {
        this.updateIntegersDB = updateIntegersDB;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clockClass.stopThread();
    }
}