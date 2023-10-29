package com.example.exerciseapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.mClasses.SharedViewModel;
import com.example.exerciseapp.mInterfaces.FragmentRespond;
import com.example.exerciseapp.mInterfaces.FragmentSupportListener;

import java.util.Arrays;

public class SelectGoalsFragment extends Fragment implements FragmentRespond {

    private final int[] selected = new int[4];

    private CheckBox strengthBtn, muscleBtn, fatLoseBtn, techniqueBtn;

    private FragmentSupportListener fragmentSupportListener;
    private SharedViewModel sharedViewModel;

    public SelectGoalsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentSupportListener.checkCondition(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_select_goals, container, false);
        initView(mView);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        selectGoals(strengthBtn, muscleBtn, fatLoseBtn, techniqueBtn);
        return mView;
    }

    private void initView(View v) {

        strengthBtn = v.findViewById(R.id.fSelectGoal_strength);
        muscleBtn = v.findViewById(R.id.fSelectGoal_muscle);
        fatLoseBtn = v.findViewById(R.id.fSelectGoal_fat_lose);
        techniqueBtn = v.findViewById(R.id.fSelectGoal_technique);
    }

    private void selectGoals(CheckBox... buttons) {

        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = (compoundButton, b) -> {

            for(int i = 0; i < 4; i++) {
                if (buttons[i].isChecked()) {
                    selected[i] = 1;
                }
            }

            boolean isAnyButtonChecked = Arrays.stream(buttons)
                    .anyMatch(CheckBox::isChecked);
            fragmentSupportListener.checkCondition(isAnyButtonChecked);
        };

        for(CheckBox button: buttons) {
            button.setOnCheckedChangeListener(onCheckedChangeListener);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentSupportListener = (FragmentSupportListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context
                    + " must implement FragmentSupportListener");
        }
    }

    @Override
    public void fragmentMessage() {
        for (int i : selected) {
            sharedViewModel.setShareInt(i);
        }
    }
}