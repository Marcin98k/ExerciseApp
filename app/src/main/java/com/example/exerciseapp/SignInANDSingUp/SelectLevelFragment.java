package com.example.exerciseapp.SignInANDSingUp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mClasses.SharedViewModel;
import com.example.exerciseapp.mInterfaces.FragmentRespond;
import com.example.exerciseapp.mInterfaces.FragmentSupportListener;

import java.util.HashMap;
import java.util.Map;

public class SelectLevelFragment extends Fragment implements FragmentRespond {
    private RadioGroup radioGroup;
    private RadioButton beginnerBtn, intermediateBtn, advancedBtn;
    private RadioButton radioButton;

    private int selectedLevel;

    private FragmentSupportListener mListener;
    private SharedViewModel sharedViewModel;

    public SelectLevelFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (FragmentSupportListener) context;
        } catch (ClassCastException e) {
           throw new ClassCastException(context +
                   " must implement FragmentSupportListener");
        }
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

        initializeView(mView);
        selectLevel(beginnerBtn, intermediateBtn, advancedBtn);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        return mView;
    }

    private void initializeView(View mView) {
        radioGroup = mView.findViewById(R.id.fSelectLevel_levelGroup);
        beginnerBtn = mView.findViewById(R.id.fSelectLevel_beginner);
        intermediateBtn = mView.findViewById(R.id.fSelectLevel_intermediate);
        advancedBtn = mView.findViewById(R.id.fSelectLevel_advanced);
    }

    private void selectLevel(RadioButton... buttons) {
        Map<RadioButton, Integer> buttonValues = new HashMap<>();
        buttonValues.put(beginnerBtn, 1);
        buttonValues.put(intermediateBtn, 2);
        buttonValues.put(advancedBtn, 3);

        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            radioButton = radioGroup.findViewById(i);
            selectedLevel = buttonValues.getOrDefault(i, 0);
            mListener.checkCondition(true);
        });

    }

    @Override
    public void fragmentMessage() {
        sharedViewModel.setShareInt(selectedLevel);
    }
}