package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mInterfaces.FragmentRespond;
import com.example.exerciseapp.mInterfaces.ISummary;
import com.example.exerciseapp.mModels.ExerciseModel;

import java.util.List;

public class SummaryFragment extends Fragment implements FragmentRespond {

//    Initializing variables;
    private long id;
    private byte genusSummary;

    private List<ExerciseModel> summaryList;

//    Initializing instances;
    private ContentBD contentBD;

//    Initializing interface;
    private ISummary iSummary;

    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
            genusSummary = getArguments().getByte("genusSummary");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_summary, container, false);
        contentBD = new ContentBD(getActivity());
        if (genusSummary == 1) {
            summaryList = contentBD.showExerciseById(id);
        } else if (genusSummary == 2) {
            summaryList = contentBD.showWorkoutById(id);
        } else {
            throw new NullPointerException("Wrong number (genusSummary)");
        }
        Log.i(TAG, "onCreateView: (Summary): " + summaryList.get(0).getName());
        return mView;
    }

    @Override
    public void fragmentMessage() {
        iSummary.summaryMessage("summaryFragment", "", 0, true);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            iSummary = (ISummary) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement ISummary");
        }
    }
}