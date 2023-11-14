package com.example.exerciseapp.mResource;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.exerciseapp.R;

public class LoadingFragment extends Fragment {

    private ProgressBar progressBar;

    public LoadingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_loading, container, false);
        initView(mView);
        return mView;
    }

    private void initView(View v) {
        progressBar = v.findViewById(R.id.frag_loading_progress_bar);
    }
}