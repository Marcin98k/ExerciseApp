package com.example.exerciseapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.Interfaces.FragmentRespond;

public class SelectPerformanceFragment extends Fragment implements FragmentRespond {

    SharedViewModel sharedViewModel;

    private EditText etPush, etPull, etDip, etSquad;

    private int push_count;
    private int pull_count;
    private int dip_count;
    private int squad_count;


    public SelectPerformanceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_select_performance, container, false);

        etPush = mView.findViewById(R.id.fSelectPerformance_ET_push);
        etPull = mView.findViewById(R.id.fSelectPerformance_ET_pull);
        etDip = mView.findViewById(R.id.fSelectPerformance_ET_dip);
        etSquad = mView.findViewById(R.id.fSelectPerformance_ET_squad);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        return mView;
    }

    @Override
    public void fragmentMessage() throws NumberFormatException {

        if (etPush != null && !etPush.getText().toString().isEmpty()) {
            push_count = Integer.parseInt(etPush.getText().toString());
        } else {
            push_count = 0;
        }
        if (etPull != null && !etPull.getText().toString().isEmpty()) {
            pull_count = Integer.parseInt(etPull.getText().toString());
        } else {
            pull_count = 0;
        }
        if (etDip != null && !etDip.getText().toString().isEmpty()) {
            dip_count = Integer.parseInt(etDip.getText().toString());
        } else {
            dip_count = 0;
        }
        if (etSquad != null && !etSquad.getText().toString().isEmpty()) {
            squad_count = Integer.parseInt(etSquad.getText().toString());
        } else {
            squad_count = 0;
        }

        sharedViewModel.setShareInt(push_count);
        sharedViewModel.setShareInt(pull_count);
        sharedViewModel.setShareInt(dip_count);
        sharedViewModel.setShareInt(squad_count);
    }
}