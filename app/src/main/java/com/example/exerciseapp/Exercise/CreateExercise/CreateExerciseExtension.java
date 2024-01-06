package com.example.exerciseapp.Exercise.CreateExercise;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mEnums.ExerciseType;
import com.example.exerciseapp.mEnums.FragmentErrors;
import com.example.exerciseapp.mInterfaces.FragmentRespond;
import com.example.exerciseapp.mInterfaces.OnIntPass;

import java.util.Map;

public class CreateExerciseExtension extends Fragment implements OnIntPass {

    private static final String NUMBER_OF_SERIES_NAME = "numberOfSeries";
    private static final String VOLUME_NAME = "volume";
    private static final String BREAK_LENGTH_NAME = "breakLength";
    private static final String KCAL_NAME = "kcal";

    private TextView exerciseTypeTXT;

    private ExerciseType exerciseType;

    private ExerciseViewModel exerciseViewModel;

    private FragmentRespond fragmentRespond;

    public CreateExerciseExtension() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentRespond = (FragmentRespond) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement FragmentRespond");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_create_exercise_extension, container, false);
        initializeViewModel();
        setExerciseType();
        initializeWidgets(mView);
        fillWidgets();
        addFragments();
        fragmentRespond.fragmentAnswer(FragmentErrors.NO_ERROR, "");
        return mView;
    }

    private void initializeViewModel() {
        exerciseViewModel = new ViewModelProvider(requireActivity()).get(ExerciseViewModel.class);
    }

    private void setExerciseType() {
        ExerciseType exerciseTypeFromViewModel = exerciseViewModel.getExerciseType().getValue();
        if (exerciseTypeFromViewModel != null) {
            exerciseType = exerciseTypeFromViewModel;
        } else {
            throw new IllegalArgumentException("ExerciseType in ExerciseViewModel is null");
        }
    }

    private void initializeWidgets(View v) {
        exerciseTypeTXT = (TextView) v.findViewById(R.id.text_exercise_type_exercise_extension);
    }

    private void fillWidgets() {
        exerciseTypeTXT.setText(exerciseType.toString());
    }

    void addFragments() {

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();

        Map<ExerciseViewModel.ExerciseIntegerFields, Integer> numericalValues =
                exerciseViewModel.getNumericalValues().getValue();

        int numberOfSeries = getSafeValue(numericalValues,
                ExerciseViewModel.ExerciseIntegerFields.NUMBER_OF_SERIES);
        ft.add(R.id.frame_number_of_series_exercise_extension, Counter.newInstance(exerciseType,
                numberOfSeries), NUMBER_OF_SERIES_NAME);

        int volume = getSafeValue(numericalValues, ExerciseViewModel.ExerciseIntegerFields.VOLUME);
        ft.add(R.id.frame_volume_exercise_extension, Counter.newInstance(exerciseType, volume),
                VOLUME_NAME);

        int breakLength = getSafeValue(numericalValues,
                ExerciseViewModel.ExerciseIntegerFields.BREAK_LENGTH);
        ft.add(R.id.frame_break_length_exercise_extension, Counter.newInstance(ExerciseType.TIME,
                breakLength), BREAK_LENGTH_NAME);

        int kcal = getSafeValue(numericalValues,
                ExerciseViewModel.ExerciseIntegerFields.KCAL);
        ft.add(R.id.frame_kcal_exercise_extension, Counter.newInstance(ExerciseType.REPETITION,
                kcal), KCAL_NAME);

        ft.commit();
    }

    private int getSafeValue(Map<ExerciseViewModel.ExerciseIntegerFields, Integer> map,
                             ExerciseViewModel.ExerciseIntegerFields key) {
        Integer value = map.getOrDefault(key, -1);
        return value != null ? value : -1;
    }

    @Override
    public void onIntPass(String tag, int data) {
        switch (tag) {
            case NUMBER_OF_SERIES_NAME:
                exerciseViewModel.setNumericalValues(
                        ExerciseViewModel.ExerciseIntegerFields.NUMBER_OF_SERIES, data
                );
                break;
            case VOLUME_NAME:
                exerciseViewModel.setNumericalValues(
                        ExerciseViewModel.ExerciseIntegerFields.VOLUME, data
                );
                break;
            case BREAK_LENGTH_NAME:
                exerciseViewModel.setNumericalValues(
                        ExerciseViewModel.ExerciseIntegerFields.BREAK_LENGTH, data
                );
                break;
            case KCAL_NAME:
                exerciseViewModel.setNumericalValues(
                        ExerciseViewModel.ExerciseIntegerFields.KCAL, data
                );
                break;
            default:
                Log.e(TAG, "Tag with this name: " + tag + " does not exist");
        }
    }
}