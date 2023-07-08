package com.example.exerciseapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.mClasses.ClockClass;
import com.example.exerciseapp.mClasses.CreateExerciseClass;
import com.example.exerciseapp.mClasses.InsertResult;
import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.CustomUserExerciseModel;
import com.example.exerciseapp.mModels.ExerciseModel;
import com.example.exerciseapp.mModels.FourElementsModel;
import com.example.exerciseapp.mModels.IntegerModel;

import java.util.ArrayList;
import java.util.List;

public class CustomExerciseCreatorFragment extends Fragment implements UpdateIntegersDB {

    private static final String TAG = "CustomExerciseCreatorFragment";
    private ToggleButton selectExercise;
    private ToggleButton selectVolume;

    private TextView exerciseName;
    private TextView exerciseType;
    private TextView exerciseSets;
    private TextView exerciseVolume;
    private TextView exerciseRest;
    private EditText customExerciseName;
    private Button btnCreate;


    private List<FourElementsModel> fourElementsModelList;
    private static final String[] CUSTOM_EXERCISE_TITLES = new String[]{"Repetition", "Time"};
    private String nameOfExercise;
    private String nameOfCustomExercise;
    private Integer numberOfExerciseID;
    private Integer numberOfExerciseType;
    private Integer numberOfExerciseSets;
    private Integer numberOfExerciseVolume;
    private Integer numberOfExerciseRest;



    private ViewPagerFragment viewPagerFragment;
    private SearchList searchList;
    private ContentBD contentBD;
    private CreateExerciseClass createExerciseClass;
    private ClockClass clockClassVolumeTime;
    private ClockClass clockClassRestTime;

    public CustomExerciseCreatorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_custom_exercise_creator, container, false);
        initView(mView);

        createExerciseClass = new ViewModelProvider(requireActivity()).get(CreateExerciseClass.class);
        contentBD = new ContentBD(requireContext());
        fourElementsModelList = new ArrayList<>();
        clockClassVolumeTime = new ClockClass(requireContext());
        clockClassRestTime = new ClockClass(requireContext());

        List<ExerciseModel> temp = contentBD.showExercise();

        for (int i = 0; i < temp.size(); i++) {
            FourElementsModel model = new FourElementsModel(
                    temp.get(i).getId(), temp.get(i).getImage(), temp.get(i).getName(),
                    String.valueOf(temp.get(i).getType()), R.drawable.ic_hexagon);
            fourElementsModelList.add(model);
        }

        selectExercise.setOnCheckedChangeListener(((compoundButton, isChecked) -> {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            if (isChecked) {
                if (searchList == null) {
                    searchList = new SearchList("ExerciseModelList", fourElementsModelList);
                    ft.add(R.id.frag_custom_exercise_creator_select_container, searchList, "ExerciseModelTag");
                    ft.commit();
                } else {
                    ft.attach(searchList);
                    ft.commit();
                }
            } else {
                ft.detach(searchList);
                ft.commit();
            }
        }));

        selectVolume.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            if (isChecked) {
                if (viewPagerFragment == null) {
                    viewPagerFragment = new ViewPagerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("titles", CUSTOM_EXERCISE_TITLES);
                    viewPagerFragment.setArguments(bundle);
                    ft.add(R.id.frag_custom_exercise_creator_volume_container,
                            viewPagerFragment, "CustomExerciseCounterTag");
                    ft.commit();
                } else {
                    ft.attach(viewPagerFragment);
                    ft.commit();
                }
            } else {
                if (viewPagerFragment != null) {
                    ft.detach(viewPagerFragment);
                    ft.commit();
                }
            }
        });

        btnCreate.setOnClickListener(v -> {
            nameOfCustomExercise = String.valueOf(customExerciseName.getText()).trim();
            if (numberOfExerciseID == null || numberOfExerciseID == 0) {
                return;
            }
            if (numberOfExerciseType == null) {
                return;
            }
            if (nameOfCustomExercise.equals("")) {
                return;
            }
            IntegerModel integerModel;
            if (numberOfExerciseType == 0) {
                integerModel = new IntegerModel(
                        -1, numberOfExerciseSets, numberOfExerciseVolume,0, numberOfExerciseRest);
            } else {
                integerModel = new IntegerModel(
                        -1, numberOfExerciseSets, 0, numberOfExerciseVolume, numberOfExerciseRest);
            }

            InsertResult exerciseExtensionResult = contentBD.insertExerciseExtension(integerModel);

            CustomUserExerciseModel customUserExerciseModel = new CustomUserExerciseModel(
                    -1, nameOfCustomExercise,numberOfExerciseID,
                    (int) exerciseExtensionResult.getIndex());
            contentBD.insertCustomUserExercise(customUserExerciseModel);
        });
        return mView;
    }

    private void initView(View v) {

        selectExercise = v.findViewById(R.id.frag_custom_exercise_creator_btn_select);
        selectVolume = v.findViewById(R.id.frag_custom_exercise_creator_btn_volume);

        exerciseName = v.findViewById(R.id.frag_custom_exercise_creator_exercise_name);
        exerciseType = v.findViewById(R.id.frag_custom_exercise_creator_exercise_type);
        exerciseSets = v.findViewById(R.id.frag_custom_exercise_creator_exercise_sets);
        exerciseVolume = v.findViewById(R.id.frag_custom_exercise_creator_exercise_volume);
        exerciseRest = v.findViewById(R.id.frag_custom_exercise_creator_exercise_rest);

        customExerciseName = v.findViewById(R.id.frag_custom_exercise_creator_custom_name);

        btnCreate = v.findViewById(R.id.frag_custom_exercies_creator_btn_create);
    }

    public void fillFields() {

        numberOfExerciseType = createExerciseClass.getValue(createExerciseClass.TYPE).getValue();
        numberOfExerciseSets = createExerciseClass.getValue(createExerciseClass.SETS).getValue();
        numberOfExerciseVolume = createExerciseClass.getValue(createExerciseClass.VOLUME).getValue();
        numberOfExerciseRest = createExerciseClass.getValue(createExerciseClass.REST).getValue();
        numberOfExerciseID = createExerciseClass.getValue(createExerciseClass.EXERCISE).getValue();

        if (numberOfExerciseType == 0) {
            exerciseType.setText(R.string.time);
            clockClassVolumeTime.setSecond(numberOfExerciseVolume);
            clockClassVolumeTime.dynamicIncreaseTime(exerciseVolume);
        } else if (numberOfExerciseType == 1) {
            exerciseType.setText(R.string.repetition);
            exerciseVolume.setText(String.valueOf(numberOfExerciseVolume));
        } else {
            exerciseType.setText(R.string.unknown);
            exerciseVolume.setText(String.valueOf(numberOfExerciseVolume));
        }
        exerciseSets.setText(String.valueOf(numberOfExerciseSets));

        if (numberOfExerciseID == null || numberOfExerciseID <= 0) {
            nameOfExercise = "Null or empty";
        } else {
            nameOfExercise = contentBD.showExerciseById(numberOfExerciseID).get(0).getName();
        }
        exerciseName.setText(nameOfExercise);
        clockClassRestTime.setSecond(numberOfExerciseRest);
        clockClassRestTime.dynamicIncreaseTime(exerciseRest);
    }

    @Override
    public void values(String listName, int firstValue, int secondValue, int thirdValue) {
        switch (listName) {
            case "customExerciseCounterFragmentName":
                Log.i(TAG, "values: customExerciseCounterFragmentName");
                if (firstValue == 1) {
                    exerciseName.setText(R.string.repetition);
                } else {
                    exerciseName.setText(R.string.time);
                }
                exerciseSets.setText(String.valueOf(secondValue));
                exerciseVolume.setText(String.valueOf(thirdValue));
                break;
            case "firstList":
                exerciseName.setText(String.valueOf(thirdValue));
                Log.i(TAG, "values: == firstValue");
                break;
            case "Counter":
                Log.i(TAG, "values: FragValues");
                break;
            default:
                Log.i(TAG, "values: value else");
                break;
        }

        Log.i(TAG, "values: UpdateIntegerDB");
    }
}