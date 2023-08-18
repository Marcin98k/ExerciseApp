package com.example.exerciseapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.mClasses.BackgroundTask;
import com.example.exerciseapp.mClasses.ClockClass;
import com.example.exerciseapp.mClasses.CreateExerciseClass;
import com.example.exerciseapp.mClasses.InsertResult;
import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mInterfaces.ITitleChangeListener;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.CustomUserExerciseModel;
import com.example.exerciseapp.mModels.ExerciseModel;
import com.example.exerciseapp.mModels.FourElementsModel;
import com.example.exerciseapp.mModels.IntegerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomExerciseCreatorFragment extends Fragment implements UpdateIntegersDB {

    private static final String TAG = "CustomExerciseCreatorFragment";
    private static final String FRAGMENT_NAME = "Create exercise";


    private ToggleButton selectExercise;
    private ToggleButton selectDetails;
    private TextView exerciseName;
    private TextView exerciseType;
    private TextView exerciseSets;
    private TextView exerciseVolume;
    private TextView exerciseRest;
    private EditText customExerciseName;
    private Button btnCreate;


    private List<FourElementsModel> fourElementsModelList;
    private static final String[] CUSTOM_EXERCISE_TITLES = {"Repetition", "Time"};
    private String nameOfCustomExercise;
    private Integer numberOfExerciseID;
    private Integer numberOfExerciseType;
    private Integer numberOfExerciseSets;
    private Integer numberOfExerciseVolume;
    private Integer numberOfExerciseRest;
    private long userId = 0;

    private FragmentManager fm;

    private ViewPagerFragment viewPagerFragment;
    private SearchList searchList;
    private ContentBD contentBD;
    private CreateExerciseClass createExerciseClass;
    private ClockClass clockClassVolumeTime;
    private ClockClass clockClassRestTime;
    private ITitleChangeListener iTitleChangeListener;

    public CustomExerciseCreatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            iTitleChangeListener = (ITitleChangeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context +
                    " must implement ITitleChangeListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (iTitleChangeListener != null) {
            iTitleChangeListener.title(FRAGMENT_NAME);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (iTitleChangeListener != null) {
            iTitleChangeListener.title("");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getLong("userId");
        }
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
        fm = getChildFragmentManager();


        selectExercise.setOnCheckedChangeListener(((compoundButton, isChecked) -> {
            if (isChecked) {
                if (searchList == null) {
                    BackgroundTask.executeWithLoading(
                            () -> {
                                List<ExerciseModel> showExercise = contentBD.showExercise();
                                    fourElementsModelList = showExercise.stream()
                                            .map(exerciseModel -> new FourElementsModel(
                                                    exerciseModel.getId(), exerciseModel.getImage(),
                                                    exerciseModel.getName(), String.valueOf(exerciseModel.getType()),
                                                    exerciseModel.getLevel()
                                            )).collect(Collectors.toList());
                            },
                            () -> fm.beginTransaction().replace(R.id.frag_custom_exercise_creator_select_container,
                                    new LoadingFragment()).commit(),
                            () -> {
                                searchList = new SearchList("ExerciseModelList", fourElementsModelList);
                                fm.beginTransaction().replace(R.id.frag_custom_exercise_creator_select_container, searchList,
                                        "ExerciseModelTag").commit();
                            }
                    );
                } else {
                    fm.beginTransaction().attach(searchList).commit();
                }
            } else {
                fm.beginTransaction().detach(searchList).commit();
            }
        }));

        selectDetails.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                if (viewPagerFragment == null) {
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    viewPagerFragment = new ViewPagerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("titles", CUSTOM_EXERCISE_TITLES);
                    viewPagerFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.frag_custom_exercise_creator_volume_container,
                            viewPagerFragment, "CustomExerciseCounterTag");
                    fragmentTransaction.commit();
                } else {
                    fm.beginTransaction().attach(viewPagerFragment).commit();
                }
            } else {
                if (viewPagerFragment != null) {
                    fm.beginTransaction().detach(viewPagerFragment).commit();
                }
            }
        });

        btnCreate.setOnClickListener(v -> {
            nameOfCustomExercise = String.valueOf(customExerciseName.getText()).trim();

            if (nameOfCustomExercise.equals("")) {
                Toast.makeText(requireActivity(), "Enter the name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (contentBD.searchByName("CUSTOM_USER_EXERCISE_TAB", 
                    "CUSTOM_USER_EXERCISE_NAME", nameOfCustomExercise) ||
                    contentBD.searchByName("EXERCISE_TAB", "NAME", nameOfCustomExercise)) {
                Toast.makeText(requireActivity(), "An exercise with that name exist",
                        Toast.LENGTH_SHORT).show();
            }
            IntegerModel integerModel;
            if (numberOfExerciseType == 0) {
                integerModel = new IntegerModel(-1, (int) userId, numberOfExerciseSets,
                        numberOfExerciseVolume, 0, numberOfExerciseRest);
            } else {
                integerModel = new IntegerModel(-1, (int) userId, numberOfExerciseSets, 0,
                        numberOfExerciseVolume, numberOfExerciseRest);
            }

            InsertResult exerciseExtensionResult = contentBD.insertExerciseExtension(integerModel);
            
            CustomUserExerciseModel customUserExerciseModel = new CustomUserExerciseModel(
                    -1, userId, nameOfCustomExercise, numberOfExerciseType, numberOfExerciseID,
                    (int) exerciseExtensionResult.getIndex());
            contentBD.insertCustomUserExercise(customUserExerciseModel);
        });
        return mView;
    }

    private void initView(View v) {

        selectExercise = v.findViewById(R.id.frag_custom_exercise_creator_btn_select);
        selectDetails = v.findViewById(R.id.frag_custom_exercise_creator_btn_details);

        exerciseName = v.findViewById(R.id.frag_custom_exercise_creator_exist_exercise_name);
        exerciseType = v.findViewById(R.id.frag_custom_exercise_creator_exercise_type);
        exerciseSets = v.findViewById(R.id.frag_custom_exercise_creator_exercise_sets);
        exerciseVolume = v.findViewById(R.id.frag_custom_exercise_creator_exercise_volume);
        exerciseRest = v.findViewById(R.id.frag_custom_exercise_creator_exercise_rest);

        customExerciseName = v.findViewById(R.id.frag_custom_exercise_creator_new_exercise_name);

        btnCreate = v.findViewById(R.id.frag_custom_exercise_creator_btn_create);
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

        String nameOfExercise;
        if (numberOfExerciseID == null || numberOfExerciseID <= 0) {
            nameOfExercise = "---";
        } else {
            nameOfExercise = contentBD.showExerciseById(numberOfExerciseID).get(0).getName();
        }
        exerciseName.setText(nameOfExercise);
        clockClassRestTime.setSecond(numberOfExerciseRest);
        clockClassRestTime.dynamicIncreaseTime(exerciseRest);
    }

    @Override
    public void values(String listName, int firstValue, int secondValue, int thirdValue, int fourthValue) {
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