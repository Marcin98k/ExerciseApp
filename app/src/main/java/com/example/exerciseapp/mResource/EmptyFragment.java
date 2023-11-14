package com.example.exerciseapp.mResource;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mInterfaces.FragmentRespond;
import com.example.exerciseapp.mInterfaces.TrainingSummaryHandler;

public class EmptyFragment extends Fragment implements FragmentRespond {


    private TrainingSummaryHandler trainingSummaryHandler;

    public EmptyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_empty, container, false);
    }

    @Override
    public void fragmentMessage() {
        Log.i(TAG, "fragmentMessage: EmptyFrag");
        trainingSummaryHandler.summaryMessage("EmptyFragment", "", "", 0,
                0, 0, true);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            trainingSummaryHandler = (TrainingSummaryHandler) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement TrainingSummaryHandler");
        }
    }
}