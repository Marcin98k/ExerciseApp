package com.example.exerciseapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.mClasses.ClockClass;
import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mInterfaces.FragmentRespond;
import com.example.exerciseapp.mInterfaces.FragmentSupportListener;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.ExerciseModel;
import com.example.exerciseapp.mModels.IntegerModel;

import java.util.List;

public class TimeExerciseFragment extends Fragment implements FragmentRespond {

    private static final String TAG_TIME_EXERCISE_FRAGMENT = "TimeExerciseFragment";

    private ImageView imageView;
    private TextView nameView;
    private TextView timeView;
    private TextView currentSetView;
    private TextView sumSetView;
    private ProgressBar progressBar;

    private List<ExerciseModel> exercise;
    private List<IntegerModel> extension;

    private byte currentSet;
    private int rest;
    private final byte POSITION = 0;
    private int fromWhere;
    private int last = 0;

    private byte getSet;

    private UpdateIntegersDB updateIntegersDB;
    private FragmentSupportListener fragmentSupportListener;
    private ContentBD contentBD;
    private ClockClass clockClass;

    private long exerciseId;

    public TimeExerciseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            fragmentSupportListener = (FragmentSupportListener) context;
            updateIntegersDB = (UpdateIntegersDB) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context +
                    " must implement FragmentSupportListener" +
                    " and/or UpdateIntegersDB");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exerciseId = getArguments().getLong("id");
            fromWhere = getArguments().getInt("fromWhere");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_time_exercise, container, false);
        initView(mView);
        return mView;
    }

    private void initView(View v) {

        initializeViews(v);

        currentSet = (byte) (currentSet + 1);
        contentBD = new ContentBD(requireActivity());
        exercise = getExerciseData();

        if (exercise.isEmpty()) {
            throw new IllegalStateException(requireContext() + " list is empty");
        } else {
            setupExerciseData();
            setupClockClass();
            checkCurrentSet();
        }
    }

    private void initializeViews(View v) {

        imageView = v.findViewById(R.id.frag_time_exercise_image);
        nameView = v.findViewById(R.id.frag_time_exercise_name);
        timeView = v.findViewById(R.id.frag_time_exercise_time);
        currentSetView = v.findViewById(R.id.frag_time_exercise_current_set);
        sumSetView = v.findViewById(R.id.frag_time_exercise_sum_set);
        progressBar = v.findViewById(R.id.frag_time_exercise_progress_bar);
    }

    @VisibleForTesting
    private List<ExerciseModel> getExerciseData() {
        if (fromWhere == 0) {
            return contentBD.showExerciseById(exerciseId);
        } else {
            return contentBD.showUserExerciseById(exerciseId);
        }
    }

    @VisibleForTesting
    private void setupExerciseData() {
        extension = contentBD.showExerciseExtensionId(
                exercise.get(POSITION).getExtension());
        getSet = (byte) extension.get(POSITION).getSecondValue();
        nameView.setText(exercise.get(POSITION).getName());
        rest = extension.get(POSITION).getFifthValue();
        sumSetView.setText(String.valueOf(getSet));
        currentSetView.setText(String.valueOf(currentSet));
    }

    @VisibleForTesting
    private void setupClockClass() {
        clockClass = new ClockClass(requireActivity(), true,
                extension.get(POSITION).getForthValue()
        ).setBar(progressBar).setTextView(timeView);

        clockClass.setFragmentSupportListener(param -> {
            if (updateIntegersDB != null) {
                updateIntegersDB.values(TAG_TIME_EXERCISE_FRAGMENT,
                        rest, 2, 2, last);
            }
        });
        clockClass.runClock();
    }

    @VisibleForTesting
    private void checkCurrentSet() {

        int sumSet = getSet + 1;
        if (currentSet == (sumSet - 1)) {
            last = 1;
        }
        if (currentSet == sumSet) {
            last = 0;
            fragmentSupportListener.checkCondition(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clockClass.stopThread();
    }

    public void setUpdateIntegersDB(UpdateIntegersDB updateIntegersDB) {
        this.updateIntegersDB = updateIntegersDB;
    }

    @Override
    public void fragmentMessage() {
        updateIntegersDB.values(TAG_TIME_EXERCISE_FRAGMENT, rest,
                2, 2, last);
    }
}