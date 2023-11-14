package com.example.exerciseapp.Exercise;

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

import com.example.exerciseapp.R;
import com.example.exerciseapp.mClasses.BackgroundTask;
import com.example.exerciseapp.mClasses.CreateExerciseClass;
import com.example.exerciseapp.mClasses.InsertResult;
import com.example.exerciseapp.mClasses.TrainingTimer;
import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mInterfaces.TitleChangeListener;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.CustomUserExerciseModel;
import com.example.exerciseapp.mModels.ExerciseDescriptionModel;
import com.example.exerciseapp.mModels.FourElementsModel;
import com.example.exerciseapp.mModels.IntegerModel;
import com.example.exerciseapp.mResource.LoadingFragment;
import com.example.exerciseapp.mResource.SearchList;
import com.example.exerciseapp.mResource.ViewPagerFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomExerciseCreatorFragment extends Fragment implements UpdateIntegersDB {

    private static final String CREATOR_TAG = "CustomExerciseCreatorFragment";
    private static final String COUNTER_TAG = "CustomExerciseCounterTag";
    private static final String EXERCISE_MODEL_TAG = "ExerciseModelTag";
    private static final String FRAGMENT_NAME = "Create exercise";
    private static final String SEARCH_LIST_NAME = "ExerciseModelList";
    private static final String[] CUSTOM_EXERCISE_TITLES = {"Repetition", "Time"};


    private ToggleButton selectExercise;
    private ToggleButton selectDetails;
    private Button createExercise;
    private TextView exerciseName;
    private TextView exerciseType;
    private TextView exerciseSets;
    private TextView exerciseVolume;
    private TextView exerciseRest;
    private EditText customExerciseName;


    private List<FourElementsModel> fourElementsModelList;
    private String nameOfCustomExercise;
    private Integer numberOfExerciseID;
    private Integer numberOfExerciseType;
    private Integer numberOfExerciseSets;
    private Integer numberOfExerciseVolume;
    private Integer numberOfExerciseRest;
    private Long userId = 0L;

    private FragmentManager fragmentManager;

    private ViewPagerFragment viewPagerFragment;
    private SearchList searchList;
    private ContentBD contentBD;
    private CreateExerciseClass createExerciseClass;
    private TitleChangeListener titleChangeListener;

    public CustomExerciseCreatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            titleChangeListener = (TitleChangeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context +
                    " must implement TitleChangeListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (titleChangeListener != null) {
            titleChangeListener.title(FRAGMENT_NAME);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (titleChangeListener != null) {
            titleChangeListener.title("");
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

        initClassProperties();
        buttonOperation();

        return mView;
    }

    private void initView(View v) {

        selectExercise = v.findViewById(R.id.frag_custom_exercise_creator_btn_select);
        selectDetails = v.findViewById(R.id.frag_custom_exercise_creator_btn_details);
        createExercise = v.findViewById(R.id.frag_custom_exercise_creator_btn_create);

        exerciseName = v.findViewById(R.id.frag_custom_exercise_creator_exist_exercise_name);
        exerciseType = v.findViewById(R.id.frag_custom_exercise_creator_exercise_type);
        exerciseSets = v.findViewById(R.id.frag_custom_exercise_creator_exercise_sets);
        exerciseVolume = v.findViewById(R.id.frag_custom_exercise_creator_exercise_volume);
        exerciseRest = v.findViewById(R.id.frag_custom_exercise_creator_exercise_rest);

        customExerciseName = v.findViewById(R.id.frag_custom_exercise_creator_new_exercise_name);
    }

    private void initClassProperties() {

        createExerciseClass = new ViewModelProvider(requireActivity()).get(CreateExerciseClass.class);
        contentBD = new ContentBD(requireContext());
        fourElementsModelList = new ArrayList<>();
        fragmentManager = getChildFragmentManager();
    }

    public void fillFields() {

        initExerciseValue();
        setExerciseTimeAndVolume();
        exerciseSets.setText(String.valueOf(numberOfExerciseSets));
        exerciseName.setText(getExerciseName());
        setRestTime();
    }

    private void initExerciseValue() {

        numberOfExerciseType = createExerciseClass.getValue(createExerciseClass.TYPE).getValue();
        numberOfExerciseSets = createExerciseClass.getValue(createExerciseClass.SETS).getValue();
        numberOfExerciseVolume = createExerciseClass.getValue(createExerciseClass.VOLUME).getValue();
        numberOfExerciseRest = createExerciseClass.getValue(createExerciseClass.REST).getValue();
        numberOfExerciseID = createExerciseClass.getValue(createExerciseClass.EXERCISE).getValue();
    }

    private void setExerciseTimeAndVolume() {
        if (numberOfExerciseType == 0) {
            exerciseType.setText(R.string.time);
            new TrainingTimer.TrainingTimerBuilder(requireContext())
                    .setSecond(numberOfExerciseVolume)
                    .build()
                    .dynamicIncreaseTime(exerciseVolume);
        } else if (numberOfExerciseType == 1) {
            exerciseType.setText(R.string.repetition);
            exerciseVolume.setText(String.valueOf(numberOfExerciseVolume));
        } else {
            exerciseType.setText(R.string.unknown);
            exerciseVolume.setText(String.valueOf(numberOfExerciseVolume));
        }
    }

    private String getExerciseName() {
        if (numberOfExerciseID == null || numberOfExerciseID <= 0) {
            return "---";
        } else {
            return contentBD.showExerciseById(numberOfExerciseID).get(0).getName();
        }
    }

    private void setRestTime() {
        new TrainingTimer.TrainingTimerBuilder(requireContext())
                .setSecond(numberOfExerciseRest)
                .build()
                .dynamicIncreaseTime(exerciseRest);
    }

    private void buttonOperation() {

        selectExerciseBtn(selectExercise);
        selectDetailsBtn(selectDetails);
        createExerciseBtn(createExercise);
    }

    private void selectExerciseBtn(ToggleButton btn) {
        btn.setOnCheckedChangeListener(((compoundButton, isChecked) -> {
            if (isChecked) {
                attachSearchListFragment();
            } else {
                detachSearchListFragment();
            }
        }));
    }

    private void attachSearchListFragment() {
        if (searchList == null) {
            BackgroundTask.executeWithLoading(
                    this::prepareExerciseData,
                    this::showLoadingFragment,
                    this::replaceWithSearchListFragment
            );
        }
    }

    private void prepareExerciseData() {
        List<ExerciseDescriptionModel> showExercise = contentBD.showExercise();
        fourElementsModelList = showExercise.stream()
                .map(exerciseModel -> new FourElementsModel(
                        exerciseModel.getId(), exerciseModel.getImage(),
                        exerciseModel.getName(), String.valueOf(exerciseModel.getType()),
                        exerciseModel.getLevel()
                )).collect(Collectors.toList());
    }

    private void showLoadingFragment() {
        fragmentManager.beginTransaction().replace(R.id.frag_custom_exercise_creator_select_container,
                new LoadingFragment()).commit();
    }

    private void replaceWithSearchListFragment() {
        searchList = new SearchList(SEARCH_LIST_NAME, fourElementsModelList);
        fragmentManager.beginTransaction().replace(R.id.frag_custom_exercise_creator_select_container,
                searchList, EXERCISE_MODEL_TAG).commit();
    }

    private void detachSearchListFragment() {
        fragmentManager.beginTransaction().detach(searchList).commit();
    }

    private void selectDetailsBtn(ToggleButton detailsBtn) {
        detailsBtn.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                attachViewPagerView();
            } else {
                detachViewPagerView();
            }
        });
    }
    private void attachViewPagerView() {
        if (viewPagerFragment == null) {
           viewPagerFragment = createViewPagerFragment();
           addFragmentToFragmentManager(viewPagerFragment,
                   R.id.frag_custom_exercise_creator_volume_container, COUNTER_TAG);
        } else {
            fragmentManager.beginTransaction().attach(viewPagerFragment).commit();
        }
    }

    private ViewPagerFragment createViewPagerFragment() {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray("titles", CUSTOM_EXERCISE_TITLES);
        viewPagerFragment.setArguments(bundle);
        return fragment;
    }

    private void addFragmentToFragmentManager(Fragment fragment, int containerId, String tag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerId, fragment, tag);
        fragmentTransaction.commit();
    }

    private void detachViewPagerView() {
        if (viewPagerFragment != null) {
            fragmentManager.beginTransaction().detach(viewPagerFragment).commit();
        }
    }

    private void createExerciseBtn(Button createBtn) {
        createBtn.setOnClickListener(v -> {

            IntegerModel customExercise;
            nameOfCustomExercise = String.valueOf(customExerciseName.getText()).trim();

            if (nameOfCustomExercise.equals("")) {
                displayToast("Enter the name");
                return;
            }
            if (doesExerciseExist(nameOfCustomExercise)) {
                displayToast("An exercise with that name exist");
            }

            customExercise = createCustomExercise();
            insertExerciseExtension(customExercise);
        });
    }
    private void displayToast(String message) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private boolean doesExerciseExist(String exerciseName) {
        return contentBD.searchByName("CUSTOM_USER_EXERCISE_TAB",
                "CUSTOM_USER_EXERCISE_NAME", exerciseName) ||
                contentBD.searchByName("EXERCISE_TAB", "NAME", exerciseName);
    }

    private IntegerModel createCustomExercise() {
        int timeExerciseValue = (numberOfExerciseType == 0) ? numberOfExerciseVolume : 0;
        int repetitionExerciseValue = (numberOfExerciseType == 0) ? 0 : numberOfExerciseVolume;

//        TO-DO create a personalized model
        return new IntegerModel(-1, Math.toIntExact(userId), numberOfExerciseSets, timeExerciseValue,
                repetitionExerciseValue, numberOfExerciseVolume, numberOfExerciseRest);
    }

    private void insertExerciseExtension(IntegerModel customExercise) {
        InsertResult exerciseExtensionResult = contentBD.insertExerciseExtension(customExercise);

        CustomUserExerciseModel customUserExerciseModel = new CustomUserExerciseModel(
                -1, userId, nameOfCustomExercise, numberOfExerciseType, numberOfExerciseID,
                (int) exerciseExtensionResult.getIndex());
        contentBD.insertCustomUserExercise(customUserExerciseModel);
    }

    @Override
    public void values(String listName, int firstValue, int secondValue, int thirdValue,
                       int fourthValue) {
        switch (listName) {
            case "customExerciseCounterFragmentName":
                Log.i(CREATOR_TAG, "values: customExerciseCounterFragmentName");
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
                Log.i("CustomExerciseCounterTag", "values: == firstValue");
                break;
            case "Counter":
                Log.i("CustomExerciseCounterTag", "values: FragValues");
                break;
            default:
                Log.i("CustomExerciseCounterTag", "values: value else");
                break;
        }

        Log.i("CustomExerciseCounterTag", "values: UpdateIntegerDB");
    }
}