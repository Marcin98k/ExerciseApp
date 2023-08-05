package com.example.exerciseapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class ExerciseFragment extends Fragment {

    private ImageView image;
    private FrameLayout container;

    public ExerciseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_exercise, container, false);
        initView(mView);

        return mView;
    }

    private void initView(View v) {
        image = v.findViewById(R.id.frag_exercise_image);
        container = v.findViewById(R.id.frag_exercise_container);
    }
}