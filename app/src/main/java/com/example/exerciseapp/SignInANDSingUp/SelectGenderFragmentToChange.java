package com.example.exerciseapp.SignInANDSingUp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mClasses.SharedViewModel;
import com.example.exerciseapp.mInterfaces.FragmentRespondToChange;
import com.example.exerciseapp.mInterfaces.FragmentSupportListener;

import java.util.HashMap;
import java.util.Map;


public class SelectGenderFragmentToChange extends Fragment implements FragmentRespondToChange {
    private RadioButton maleBtn, femaleBtn, otherBtn;
    private int selectedGender;

    private FragmentSupportListener fragmentSupportListener;
    private CompoundButton previousCheckedCompoundButton;
    private SharedViewModel sharedViewModel;
    private RegisterViewModel registerViewModel;

    public SelectGenderFragmentToChange() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_select_gender, container, false);

        initView(mView);
        selectGender(maleBtn, femaleBtn, otherBtn);

        registerViewModel = new RegisterViewModel();

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        return mView;
    }

    private void initView(View v) {
        maleBtn = v.findViewById(R.id.fSelectGender_rBtn_male);
        femaleBtn = v.findViewById(R.id.fSelectGender_rBtn_female);
        otherBtn = v.findViewById(R.id.fSelectGender_rBtn_other);
    }

    private void selectGender(RadioButton... buttons) {

        Map<RadioButton, Integer> buttonValues = new HashMap<>();
        for (int i = 0; i < buttons.length; i++) {
            buttonValues.put(buttons[i], i + 1);
        }

        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = (compoundButton, b) -> {

            if (!b) {
                fragmentSupportListener.checkCondition(false);
            }

            if (previousCheckedCompoundButton != null) {
                previousCheckedCompoundButton.setChecked(false);
            }
            previousCheckedCompoundButton = compoundButton;

            selectedGender = buttonValues.getOrDefault(compoundButton, 0);
            fragmentSupportListener.checkCondition(true);
        };

        for (RadioButton button : buttons) {
            button.setOnCheckedChangeListener(onCheckedChangeListener);
        }
    }

    @Override
    public void fragmentMessage() {
        sharedViewModel.setShareInt(selectedGender);
    }
}