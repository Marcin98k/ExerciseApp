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

public class SelectGoalsFragment extends Fragment implements FragmentRespond {

    FragmentSupportListener mListener;
    SharedViewModel sharedViewModel;

    private final int[] selected = new int[4];

    private CheckBox firstOption;
    private CheckBox secondOption;
    private CheckBox thirdOption;
    private CheckBox forthOption;

    public SelectGoalsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListener.checkCondition(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_select_goals, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        firstOption = mView.findViewById(R.id.fSelectGoal_firstOption);
        secondOption = mView.findViewById(R.id.fSelectGoal_secondOption);
        thirdOption = mView.findViewById(R.id.fSelectGoal_thirdOption);
        forthOption = mView.findViewById(R.id.fSelectGoal_forthOption);

        selectGoals(firstOption, secondOption, thirdOption, forthOption);
        return mView;
    }

    private void selectGoals(CheckBox btn0, CheckBox btn1, CheckBox btn2, CheckBox btn3) {

        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = (compoundButton, b) -> {

            if (btn0.isChecked()) {
                selected[0] = 1;
            }
            if (btn1.isChecked()) {
                selected[1] = 1;
            }
            if (btn2.isChecked()) {
                selected[2] = 1;
            }
            if (btn3.isChecked()) {
                selected[3] = 1;
            }

            mListener.checkCondition(btn0.isChecked() || btn1.isChecked() || btn2.isChecked() || btn3.isChecked());
        };

        btn0.setOnCheckedChangeListener(onCheckedChangeListener);
        btn1.setOnCheckedChangeListener(onCheckedChangeListener);
        btn2.setOnCheckedChangeListener(onCheckedChangeListener);
        btn3.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (FragmentSupportListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement FragmentSupportListener");
        }
    }

    @Override
    public void fragmentMessage() {
        for (int i : selected) {
            sharedViewModel.setShareInt(i);
        }
    }
}