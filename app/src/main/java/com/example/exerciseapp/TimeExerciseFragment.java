package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.mClasses.ClockClass;
import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mInterfaces.FragmentRespond;
import com.example.exerciseapp.mInterfaces.FragmentSupportListener;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.ExerciseModel;
import com.example.exerciseapp.mModels.IntegerModel;

import java.util.ArrayList;
import java.util.List;

public class TimeExerciseFragment extends Fragment implements FragmentRespond {


    private ImageView imageView;
    private TextView nameView;
    private TextView timeView;
    private TextView currentSetView;
    private TextView sumSetView;
    private ProgressBar progressBar;

    private List<ExerciseModel> exercise = new ArrayList<>();

    private byte currentSet;
    private byte sumSet;
    private int rest;
    private final byte POSITION = 0;

    UpdateIntegersDB updateIntegersDB;
    FragmentSupportListener fragmentSupportListener;
    ContentBD contentBD;
    private ClockClass clockClass;

    private long exerciseId;

    public TimeExerciseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exerciseId = getArguments().getLong("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_time_exercise, container, false);
        currentSet = (byte) (currentSet + 1);
        initView(mView);

        return mView;
    }

    private void initView(View v) {

        imageView = v.findViewById(R.id.frag_time_exercise_image);
        nameView = v.findViewById(R.id.frag_time_exercise_name);
        timeView = v.findViewById(R.id.frag_time_exercise_time);
        currentSetView = v.findViewById(R.id.frag_time_exercise_current_set);
        sumSetView = v.findViewById(R.id.frag_time_exercise_sum_set);
        progressBar = v.findViewById(R.id.frag_time_exercise_progress_bar);

        contentBD = new ContentBD(requireActivity());

        exercise = contentBD.showExerciseById(exerciseId);

        if (exercise.isEmpty()) {
            throw new NullPointerException(getContext().toString() + " list is empty");
        } else {
            List<IntegerModel> extension = contentBD.
                    showExerciseExtensionId(exercise.get(POSITION).getExtension());
//            imageView.setImageResource(exercise.get(POSITION).getImage());
            sumSet = (byte) extension.get(POSITION).getSecondValue();
            nameView.setText(exercise.get(POSITION).getName());
            rest = extension.get(POSITION).getFifthValue();
            Log.i(TAG, "initView: (Exercise) " + rest);
            sumSetView.setText(String.valueOf(sumSet));
            currentSetView.setText(String.valueOf(currentSet));
            clockClass = new ClockClass(requireActivity(), true,
                    extension.get(POSITION).getForthValue()
                    ).setBar(progressBar).setTextView(timeView);

            clockClass.setFragmentSupportListener(param -> {
                if (updateIntegersDB != null) {
                    updateIntegersDB.values("TimeExerciseFragment",
                            rest, 2, 2);
                }
            });
            clockClass.runClock();
        }

        if (currentSet == sumSet) {
            fragmentSupportListener.checkCondition(true);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            fragmentSupportListener = (FragmentSupportListener) context;
        } catch (RuntimeException e) {
            throw new RuntimeException(context +
                    " must implement FragmentSupportListener");
        }
        try {
            updateIntegersDB = (UpdateIntegersDB) context;
        } catch (RuntimeException e) {
            throw new RuntimeException(context +
                    " must implement UpdateIntegersDB");
        }
    }

    public void setUpdateIntegersDB(UpdateIntegersDB updateIntegersDB) {
        this.updateIntegersDB = updateIntegersDB;
    }

    @Override
    public void fragmentMessage() {
        updateIntegersDB.values("TimeExerciseFragment", rest, 2, 2);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clockClass.stopThread();
    }
}