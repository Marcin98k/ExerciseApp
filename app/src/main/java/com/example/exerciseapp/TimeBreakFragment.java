package com.example.exerciseapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.exerciseapp.mClasses.ClockClass;

public class TimeBreakFragment extends Fragment {

    private ProgressBar progressBar;
    private TextView showTime;
    private Button skipBtn;
    private Button addBtn;

    public TimeBreakFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_time_break, container, false);
        initView(mView);

        new ClockClass(requireContext(), true, 78)
                .setBar(progressBar)
                .setAddBtn(addBtn).setSkipBtn(skipBtn)
                .setTextView(showTime)
                .runClock();

        return mView;
    }

    private void initView(View v) {
        progressBar = v.findViewById(R.id.fTimeBreak_progress_bar);
        showTime = v.findViewById(R.id.fTimeBreak_tv_show_time);
        addBtn = v.findViewById(R.id.fTimeBreak_btn_add);
        skipBtn = v.findViewById(R.id.fTimeBreak_btn_skip);
    }
}