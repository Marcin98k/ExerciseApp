package com.example.exerciseapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.exerciseapp.mClasses.SharedViewModel;
import com.example.exerciseapp.mInterfaces.FragmentRespond;
import com.example.exerciseapp.mInterfaces.FragmentSupportListener;

public class SelectLevelFragment extends Fragment implements FragmentRespond {

    private FragmentSupportListener mListener;
    private SharedViewModel sharedViewModel;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private int selectedLevel;

    public SelectLevelFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListener.checkCondition(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_select_level, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        radioGroup = mView.findViewById(R.id.fSelectLevel_levelGroup);
        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            radioButton = radioGroup.findViewById(i);
            switch (i) {
                case (R.id.fSelectLevel_firstOption):
                    selectedLevel = 1;
                    break;
                case (R.id.fSelectLevel_secondOption):
                    selectedLevel = 2;
                    break;
                case (R.id.fSelectLevel_thirdOption):
                    selectedLevel = 3;
                    break;
            }
            mListener.checkCondition(true);
        });
        return mView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (FragmentSupportListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fragmentMessage() {
        sharedViewModel.setShareInt(selectedLevel);
    }
}