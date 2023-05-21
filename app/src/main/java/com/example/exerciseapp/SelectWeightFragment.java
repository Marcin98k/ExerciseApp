package com.example.exerciseapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.exerciseapp.mClasses.SharedViewModel;
import com.example.exerciseapp.mInterfaces.FragmentRespond;

public class SelectWeightFragment extends Fragment implements FragmentRespond {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private RadioButton defaultBtn;
    private NumberPicker numberPicker;

    SharedViewModel sharedViewModel;

    private int selectedUnit;

    public SelectWeightFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_select_weight, container, false);

        numberPicker = mView.findViewById(R.id.fSelectWeight_numberPicker);
        numberPicker.setMinValue(10);
        numberPicker.setMaxValue(400);
        numberPicker.setValue(80);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        defaultBtn = mView.findViewById(R.id.fSelectWeight_firstUnit);
        defaultBtn.setChecked(true);
        selectedUnit = 1;

        radioGroup = mView.findViewById(R.id.fSelectWeight_unitGroup);
        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            radioButton = radioGroup.findViewById(i);

            switch (i) {
                case (R.id.fSelectWeight_firstUnit):
                    selectedUnit = 1;
                    break;
                case (R.id.fSelectWeight_secondUnit):
                    selectedUnit = 2;
                    break;
            }
        });
        return mView;
    }

    @Override
    public void fragmentMessage() {
        sharedViewModel.setShareInt(numberPicker.getValue());
        sharedViewModel.setShareInt(selectedUnit);
    }
}