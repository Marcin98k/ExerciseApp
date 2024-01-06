package com.example.exerciseapp.Exercise.CreateExercise;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mEnums.BodyParts;
import com.example.exerciseapp.mEnums.ExerciseType;
import com.example.exerciseapp.mEnums.FragmentErrors;
import com.example.exerciseapp.mEnums.FromWhere;
import com.example.exerciseapp.mEnums.Level;
import com.example.exerciseapp.mInterfaces.FragmentRespond;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicExerciseInformation extends Fragment {

    private GridView gridView;
    private GridAdapter gridAdapter;

    private EditText nameTxt;
    private Spinner levelSpn, exerciseTypeSpn;

    private ExerciseViewModel exerciseViewModel;
    private FragmentRespond fragmentRespond;

    private static final String NAME_REG_EX = "^[a-zA-Z-][a-zA-Z0-9-]+$";

    public BasicExerciseInformation() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_basic_exercise_information, container, false);
        initializeViewModel();
        initializeWidgets(mView);
        setExerciseName(exerciseViewModel, nameTxt);
        setSpinners(exerciseViewModel, levelSpn, exerciseTypeSpn);
        initializeBodyPartsList(exerciseViewModel, gridView);
        return mView;
    }

    private void initializeViewModel() {
        exerciseViewModel = new ViewModelProvider(requireActivity()).get(ExerciseViewModel.class);
        exerciseViewModel.setFromWhere(FromWhere.USER);
        exerciseViewModel.setUserId(1);
    }

    private void initializeWidgets(View v) {

        nameTxt = v.findViewById(R.id.edit_name_basic_exercise_information);
        levelSpn = v.findViewById(R.id.spinner_level_basic_exercise_information);
        exerciseTypeSpn = v.findViewById(R.id.spinner_exercise_type_basic_exercise_information);
        gridView = v.findViewById(R.id.grid_view_basic_exercise_information);
    }

    void setExerciseName(ExerciseViewModel exerciseViewModel, EditText editText) {
        setNameFromUser(editText);
        setNameFromViewModel(exerciseViewModel, editText);
    }

    public void setNameFromUser(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Pattern namePattern = Pattern.compile(NAME_REG_EX);
                Matcher nameMatcher = namePattern.matcher(charSequence);

                if (!nameMatcher.matches()) {
                    nameTxt.setError("First sign cannot be number and allows only letters, numbers and -");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                String name = editText.getText().toString().trim();
                FragmentErrors fragmentErrors;
                if (name.length() == 0) {
                    nameTxt.setError("Field cannot be empty");
                    fragmentErrors = FragmentErrors.NO_VALUE;
                } else {
                    exerciseViewModel.setTextValues(ExerciseViewModel.ExerciseTextFields.NAME, name);
                    fragmentErrors = FragmentErrors.NO_ERROR;
                }
                fragmentRespond.fragmentAnswer(fragmentErrors, "");
            }
        });
    }

    private void setNameFromViewModel(ExerciseViewModel exerciseViewModel, EditText editText) {
        Optional<String> nameOptional = Optional.ofNullable(exerciseViewModel.getTextValues().getValue()
                .get(ExerciseViewModel.ExerciseTextFields.NAME));
        nameOptional.ifPresent(editText::setText);
    }

    void setSpinners(ExerciseViewModel exerciseViewModel, Spinner levelSpn, Spinner exerciseTypeSpn) {
        setLevelSpinner(exerciseViewModel, levelSpn);
        setExerciseTypeSpinner(exerciseViewModel, exerciseTypeSpn);
    }

    private void setLevelSpinner(ExerciseViewModel exerciseViewModel, Spinner levelSpn) {
        if (exerciseViewModel.getLevel().getValue() != null) {
            exerciseViewModel.getLevel().observe(getViewLifecycleOwner(), level ->
                    setupSpinner(levelSpn, Level.values(), exerciseViewModel::setLevel, level));
        } else {
            setupSpinner(levelSpn, Level.values(), exerciseViewModel::setLevel, Level.BEGINNER);
        }
    }

    private void setExerciseTypeSpinner(ExerciseViewModel exerciseViewModel, Spinner exerciseTypeSpn) {
        if (exerciseViewModel.getExerciseType().getValue() != null) {
            exerciseViewModel.getExerciseType().observe(getViewLifecycleOwner(), exerciseType ->
                    setupSpinner(exerciseTypeSpn, ExerciseType.values(),
                            exerciseViewModel::setExerciseType, exerciseType));
        } else {
            setupSpinner(exerciseTypeSpn, ExerciseType.values(), exerciseViewModel::setExerciseType,
                    ExerciseType.REPETITION);
        }
    }

    private <T extends Enum<T>> void setupSpinner(Spinner spinner, T[] values, Consumer<T> onItemSelected,
                                                  T defaultValue) {
        ArrayAdapter<T> adapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_item, values);
        spinner.setAdapter(adapter);
        spinner.setSelection(defaultValue.ordinal());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                T selectedItem = (T) adapterView.getItemAtPosition(position);
                onItemSelected.accept(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                onItemSelected.accept(defaultValue);
            }
        });
    }

    private void initializeBodyPartsList(ExerciseViewModel exerciseViewModel, GridView gridView) {
        List<GridModel> gridList = createGridModels();

        if (exerciseViewModel.getBodyPartsValues().getValue() != null) {
            List<Integer> selectedPositions = getSelectedPositionsFromBodyPartsMap();
            gridAdapter = new GridAdapter(requireActivity(), gridList, selectedPositions);
        } else {
            gridAdapter = new GridAdapter(requireActivity(), gridList, new ArrayList<>());
        }
        gridView.setNumColumns(2);
        gridView.setAdapter(gridAdapter);
    }

    private List<Integer> getSelectedPositionsFromBodyPartsMap() {
        Map<BodyParts, Boolean> bodyPartsMap = exerciseViewModel.getBodyPartsValues().getValue();
        if (bodyPartsMap == null) {
            return new ArrayList<>();
        }

        List<Integer> selectedPositions = new ArrayList<>();

        for (BodyParts bodyPart : bodyPartsMap.keySet()) {
            if (Boolean.TRUE.equals(bodyPartsMap.get(bodyPart))) {
                selectedPositions.add(bodyPart.ordinal());
            }
        }
        return selectedPositions;
    }

    private List<GridModel> createGridModels() {
        List<GridModel> gridModels = new ArrayList<>();

        for (BodyParts bodyPart : BodyParts.values()) {
            GridModel gridModel = new GridModel();
            gridModel.setId(bodyPart.ordinal());
            gridModel.setIcon(R.drawable.ic_dumbbell);
            gridModel.setName(bodyPart.name());
            gridModels.add(gridModel);
        }
        return gridModels;
    }

    @Override
    public void onPause() {
        super.onPause();
        exerciseViewModel.setBodyPartsList(setBodyPartsMap(gridAdapter.getIdsFromSelectedPositions()));
    }

    Map<BodyParts, Boolean> setBodyPartsMap(List<Integer> selectedPositions) {
        if (selectedPositions == null) {
            throw new IllegalArgumentException("selectedPositions cannot be null");
        }

        checkTheScopeOfTheList(selectedPositions);

        HashMap<BodyParts, Boolean> bodyPartsMap = new HashMap<>();

        fillBodyPartsMap(bodyPartsMap, selectedPositions);

        return bodyPartsMap;
    }

    private void checkTheScopeOfTheList(List<Integer> selectedPositions) {
        for (int number : selectedPositions) {
            if (number < 0 || number > BodyParts.values().length) {
                throw new IllegalArgumentException("Out of range");
            }
        }
    }

    private void fillBodyPartsMap(HashMap<BodyParts, Boolean> bodyPartsMap,
                                  List<Integer> selectedPositions) {
        for (Integer position : selectedPositions) {
            bodyPartsMap.put(BodyParts.values()[position], true);
        }
        for (BodyParts bodyPart : BodyParts.values()) {
            bodyPartsMap.putIfAbsent(bodyPart, false);
        }
    }
}