package com.example.exerciseapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.CompoundButtonCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;


public class SelectGenderFragment extends Fragment implements FragmentRespond{

    private CompoundButton previousCheckedCompoundButton;
    private RadioButton maleBtn, femaleBtn, otherBtn;
    private int selectedGender;

    FragmentSupportListener mListener;

    SharedViewModel sharedViewModel;

    public SelectGenderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_select_gender, container, false);

        maleBtn = mView.findViewById(R.id.fSelectGender_rBtn_male);
        femaleBtn = mView.findViewById(R.id.fSelectGender_rBtn_female);
        otherBtn = mView.findViewById(R.id.fSelectGender_rBtn_other);

        selectBtn(maleBtn, femaleBtn, otherBtn);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        return mView;
    }

    private void selectBtn(RadioButton btn1, RadioButton btn2, RadioButton btn3) {

        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = (compoundButton, b) -> {

            if (!b) { mListener.checkCondition(false);}
            if (previousCheckedCompoundButton != null) {
                previousCheckedCompoundButton.setChecked(false);
            }
            previousCheckedCompoundButton = compoundButton;

            if (btn1.isChecked()) {
                selectedGender = 1;
            } else if (btn2.isChecked()) {
                selectedGender = 2;
            } else if (btn3.isChecked()) {
                selectedGender = 3;
            } else {
                selectedGender = 0;
            }

            mListener.checkCondition(true);
        };
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
            throw new ClassCastException(context.toString()
                    + " must implement FragmentSupportListener");
        }
    }

    @Override
    public void fragmentMessage() {
        sharedViewModel.setShareInt(selectedGender);
    }
}