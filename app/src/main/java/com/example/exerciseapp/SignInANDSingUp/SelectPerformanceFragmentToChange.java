package com.example.exerciseapp.SignInANDSingUp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mClasses.SharedViewModel;
import com.example.exerciseapp.mInterfaces.FragmentRespondToChange;

public class SelectPerformanceFragmentToChange extends Fragment implements FragmentRespondToChange {

    private EditText pushRepsEditText, pullRepsEditText, dipRepsEditText, squadRepsEditText;

    private int numberOfPushUps, numberOfPullUps, numberOfDips, numberOfSquads;

    private SharedViewModel sharedViewModel;

    public SelectPerformanceFragmentToChange() {
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
        initView(mView);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        return mView;
    }

    private void initView(View v) {

        pushRepsEditText = v.findViewById(R.id.fSelectPerformance_ET_push);
        pullRepsEditText = v.findViewById(R.id.fSelectPerformance_ET_pull);
        dipRepsEditText = v.findViewById(R.id.fSelectPerformance_ET_dip);
        squadRepsEditText = v.findViewById(R.id.fSelectPerformance_ET_squad);
    }

    @Override
    public void fragmentMessage() throws NumberFormatException {

        numberOfPushUps = assignPerformanceValue(pushRepsEditText);
        numberOfPullUps = assignPerformanceValue(pullRepsEditText);
        numberOfDips = assignPerformanceValue(dipRepsEditText);
        numberOfSquads = assignPerformanceValue(squadRepsEditText);

        setValueToSharedInt();
    }

    @VisibleForTesting
    private int assignPerformanceValue(EditText editText) {
        if (editText != null && !editText.getText().toString().isEmpty()) {
            return Integer.parseInt(editText.getText().toString());
        } else {
            return 0;
        }
    }

    private void setValueToSharedInt() {
        sharedViewModel.setShareInt(numberOfPushUps);
        sharedViewModel.setShareInt(numberOfPullUps);
        sharedViewModel.setShareInt(numberOfDips);
        sharedViewModel.setShareInt(numberOfSquads);
    }
}