package com.example.exerciseapp.Exercise;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mDatabases.ContentDB;
import com.example.exerciseapp.mInterfaces.FragmentRespond;
import com.example.exerciseapp.mInterfaces.FragmentSupportListener;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.ExerciseDescriptionModel;
import com.example.exerciseapp.mModels.IntegerModel;

import java.util.List;

public class RepetitionExerciseFragment extends Fragment implements FragmentRespond {

    private ImageView imageView;
    private TextView nameView;
    private TextView countView;
    private TextView currentSetView;
    private TextView sumSetView;

    private long id;
    private byte currentSet;
    private int rest;
    private int fromWhere;
    private int last = 0;

    private static final String FRAGMENT_TAG = "RepetitionExerciseFragment";

    private ContentDB contentDB;

    private FragmentSupportListener fragmentSupportListener;
    private UpdateIntegersDB updateIntegersDB;

    public RepetitionExerciseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
            fromWhere = getArguments().getInt("fromWhere");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_repetition_exercise, container, false);
        currentSet = (byte) (currentSet + 1);
        intiView(mView);
        return mView;
    }

    private void intiView(View mView) {

        initializeViews(mView);

        contentDB = new ContentDB(requireActivity());
        List<ExerciseDescriptionModel> exercise = getExerciseById(id, fromWhere);

        if (exercise.isEmpty()) {
            throw new NullPointerException(getContext() + " list is empty");
        } else {
            ExerciseDescriptionModel exerciseDescriptionModel = exercise.get(0);
            IntegerModel integerModel = getExerciseExtension(exerciseDescriptionModel);

            int getSet = integerModel.getSecondValue();
            int sumSet = getSet + 1;

            nameView.setText(exerciseDescriptionModel.getName());
            countView.setText(String.valueOf(integerModel.getThirdValue()));
            sumSetView.setText(String.valueOf(getSet));
            currentSetView.setText(String.valueOf(currentSet));
            rest = integerModel.getFifthValue();

            checkLastSet(currentSet, sumSet - 1);
        }
    }

    private void initializeViews(View v) {
        imageView = v.findViewById(R.id.frag_repetition_exercise_image);
        nameView = v.findViewById(R.id.frag_repetition_exercise_name);
        countView = v.findViewById(R.id.frag_repetition_exercise_count);
        currentSetView = v.findViewById(R.id.frag_repetition_exercise_current_set);
        sumSetView = v.findViewById(R.id.frag_repetition_exercise_sum_set);
    }

    private List<ExerciseDescriptionModel> getExerciseById(long id, int fromWhere) {
        if (fromWhere == 0) {
            return contentDB.showExerciseById(id);
        } else {
            return contentDB.showUserExerciseById(id);
        }
    }

    private IntegerModel getExerciseExtension(ExerciseDescriptionModel exerciseDescriptionModel) {
        List<IntegerModel> extension = contentDB.showExerciseExtensionId(exerciseDescriptionModel.getExtension());
        return extension.get(0);
    }

    private void checkLastSet(int currentSet, int sumSet) {
        if (currentSet == (sumSet - 1)) {
            last = 1;
        }
        if (currentSet == sumSet) {
            last = 0;
            fragmentSupportListener.checkCondition(true);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentSupportListener = (FragmentSupportListener) context;
            updateIntegersDB = (UpdateIntegersDB) context;
        } catch (RuntimeException e) {
            throw new RuntimeException(context +
                    " must implement FragmentSupportListener and/or UpdateIntegersDB");
        }
    }

    @Override
    public void fragmentMessage() {
        updateIntegersDB.values(FRAGMENT_TAG, rest, 1,
                1, last);
    }
}