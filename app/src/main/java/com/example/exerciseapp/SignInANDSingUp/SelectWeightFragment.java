package com.example.exerciseapp.SignInANDSingUp;

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

import com.example.exerciseapp.R;
import com.example.exerciseapp.mClasses.SharedViewModel;
import com.example.exerciseapp.mInterfaces.FragmentRespond;

public class SelectWeightFragment extends Fragment implements FragmentRespond {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private RadioButton defaultBtn;
    private NumberPicker numberPicker;

    private int selectedUnit;

    private SharedViewModel sharedViewModel;

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

        initializeViews(mView);
        setupNumberPicker();
        setupUnitGroup();

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        return mView;
    }

    private void initializeViews(View mView) {
        numberPicker = mView.findViewById(R.id.fSelectWeight_numberPicker);
        defaultBtn = mView.findViewById(R.id.fSelectWeight_firstUnit);
        radioGroup = mView.findViewById(R.id.fSelectWeight_unitGroup);
    }

    private void setupNumberPicker() {
        numberPicker.setMinValue(10);
        numberPicker.setMaxValue(400);
        numberPicker.setValue(80);
    }

    private void setupUnitGroup() {
        defaultBtn.setChecked(true);
        selectedUnit = 1;

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
    }

    @Override
    public void fragmentMessage() {
        sharedViewModel.setShareInt(numberPicker.getValue());
        sharedViewModel.setShareInt(selectedUnit);
    }
}