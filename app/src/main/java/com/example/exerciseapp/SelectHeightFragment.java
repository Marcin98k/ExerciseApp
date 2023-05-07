package com.example.exerciseapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.Interfaces.FragmentRespond;
import com.example.exerciseapp.Interfaces.UpdateValueDB;

public class SelectHeightFragment extends Fragment implements FragmentRespond {

    private NumberPicker numberPicker;
    private RadioButton radioButton;
    private RadioButton defaultBtn;
    private RadioGroup unitGroup;
    SharedViewModel sharedViewModel;
    UpdateValueDB updateValueDB;
    private int selectedUnit;
    private int number;

    public SelectHeightFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_select_height, container, false);

        numberPicker = mView.findViewById(R.id.fSelectHeight_numberPicker);
        numberPicker.setMinValue(10);
        numberPicker.setMaxValue(400);
        numberPicker.setValue(170);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        defaultBtn = mView.findViewById(R.id.fSelectHeight_firstUnit);
        defaultBtn.setChecked(true);
        selectedUnit = 1;

        unitGroup = mView.findViewById(R.id.fSelectHeight_unitGroup);
        unitGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            radioButton = radioGroup.findViewById(i);
            switch (i) {
                case (R.id.fSelectHeight_firstUnit):
                    selectedUnit = 1;
                    break;
                case (R.id.fSelectHeight_secondUnit):
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