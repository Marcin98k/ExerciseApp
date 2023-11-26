package com.example.exerciseapp.Exercise;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mAdapters.SearchAdapter;
import com.example.exerciseapp.mClasses.TrainingTimer;
import com.example.exerciseapp.mClasses.GlobalClass;
import com.example.exerciseapp.mDatabases.ContentDB;
import com.example.exerciseapp.mInterfaces.ISingleIntegerValue;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.ExerciseDescriptionModel;
import com.example.exerciseapp.mModels.FourElementsModel;
import com.example.exerciseapp.mModels.IntegerModel;
import com.example.exerciseapp.mModels.StringModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorkoutList extends Fragment {

    private final String startWorkout = "startWorkout";
    private Button startWorkoutBtn;
    private ToggleButton showHideBtn;
    private TableLayout secondPartLayout;
    private ImageView imageView;
    private TextView nameView, levelView, bodyPartsView, equipmentView,
            kcalView, durationView, descriptionView;
    private RecyclerView exerciseListView;

    private List<FourElementsModel> exercisesList;
    private long id;
    private int durationExe;
    private int durationSum;
    private long kcalSum;
    private final byte POSITION = 0;
    private List<ExerciseDescriptionModel> workoutList;

    private SearchAdapter adapter;
    private UpdateIntegersDB updateIntegersDB;
    private ISingleIntegerValue iSingleIntegerValue;
    private ContentDB contentDB;

    public WorkoutList() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            updateIntegersDB = (UpdateIntegersDB) context;
            iSingleIntegerValue = (ISingleIntegerValue) context;
        } catch (RuntimeException e) {
            throw new RuntimeException(context +
                    " must implement UpdateIntegersDB and/or ISingleIntegerValue");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_workout_list, container, false);
        initializeView(mView);

        contentDB = new ContentDB(requireActivity());
        workoutList = contentDB.getWorkoutById(id);

        initializeListeners();
        fillView();
        initializeAdapter();
        return mView;
    }

    private void initializeView(View v) {

        startWorkoutBtn = v.findViewById(R.id.frag_workout_list_start_btn);
        showHideBtn = v.findViewById(R.id.frag_workout_list_show_hide_btn);
        secondPartLayout = v.findViewById(R.id.frag_workout_list_second_part_layout);
        secondPartLayout.setVisibility(View.GONE);

        imageView = v.findViewById(R.id.frag_workout_list_image);
        nameView = v.findViewById(R.id.frag_workout_list_name);
        levelView = v.findViewById(R.id.frag_workout_list_level);
        bodyPartsView = v.findViewById(R.id.frag_workout_list_body_parts);
        equipmentView = v.findViewById(R.id.frag_workout_list_equipment);
        kcalView = v.findViewById(R.id.frag_workout_list_kcal);
        durationView = v.findViewById(R.id.frag_workout_list_duration);
        descriptionView = v.findViewById(R.id.frag_workout_list_description);
        exerciseListView = v.findViewById(R.id.frag_workout_list_exercise_list);
    }

    private void initializeListeners() {
        showHideBtn.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                secondPartLayout.setVisibility(View.VISIBLE);
            } else {
                secondPartLayout.setVisibility(View.GONE);
            }
        });

        startWorkoutBtn.setOnClickListener(view -> iSingleIntegerValue
                .singleIntValue(startWorkout, 1));
    }

    private void initializeAdapter() {
        adapter = new SearchAdapter(requireContext(), exercisesList, "workoutList",
                updateIntegersDB);
        exerciseListView.setHasFixedSize(true);
        exerciseListView.setLayoutManager(new LinearLayoutManager(requireActivity(),
                RecyclerView.VERTICAL, false));
        exerciseListView.setAdapter(adapter);
    }

    private void fillView() {

        String exerciseIdString = contentDB.getWorkoutById(workoutList.get(POSITION).getId()).
                get(0).getExerciseId();
        long[] exercisesId = getExerciseIds(exerciseIdString);
        exercisesList = getExercisesList(exercisesId);

        String[] names = {getString(R.string.beginner), getString(R.string.intermediate), getString(R.string.advanced)};

        initializeTrainingTimer();

        nameView.setText(workoutList.get(POSITION).getName());
        equipmentView.setText(getEquipment());
        kcalView.setText(String.valueOf(kcalSum));
        bodyPartsView.setText(getBodyParts());
        levelView.setText(names[workoutList.get(POSITION).getLevel() - 1]);
        descriptionView.setText(workoutList.get(POSITION).getDescription());
    }

    private long[] getExerciseIds(String exerciseIdString) {
        String[] exercisesStr = exerciseIdString.split(",");
        long[] exercisesId = new long[exercisesStr.length];
        for (int i = 0; i < exercisesId.length; i++) {
            exercisesId[i] = Long.parseLong(exercisesStr[i]);
        }
        return exercisesId;
    }

    private List<FourElementsModel> getExercisesList(long[] exercisesId) {

        List<FourElementsModel> exercisesList = new ArrayList<>();

        for (long l : exercisesId) {
            List<ExerciseDescriptionModel> exercisesDescriptionList = contentDB.showExerciseById(l);
            kcalSum += exercisesDescriptionList.get(0).getKcal();
            List<IntegerModel> extensionExercise = contentDB.showExerciseExtensionId(
                    exercisesDescriptionList.get(0).getExtension());

            durationExe = calculateDurationExe(exercisesDescriptionList, extensionExercise);
            durationExe += durationExe;

            FourElementsModel model = new FourElementsModel(exercisesDescriptionList.get(0).getId(),
                    exercisesDescriptionList.get(0).getImage(),
                    exercisesDescriptionList.get(0).getName(),
                    String.valueOf(exercisesDescriptionList.get(0).getKcal()),
                    exercisesDescriptionList.get(0).getLevel(),
                    durationExe, exercisesDescriptionList.get(0).getFromWhere());
            exercisesList.add(model);
            durationExe = 0;
        }
        return exercisesList;
    }

    private int calculateDurationExe(List<ExerciseDescriptionModel> exercisesDescriptionList,
                                     List<IntegerModel> extensionExercise) {
        if (exercisesDescriptionList.get(0).getType() == 1) {
            return (extensionExercise.get(0).getSecondValue() * GlobalClass.DEFAULT_EXERCISE_TIME)
                    + ((extensionExercise.get(0).getSecondValue() - 1) * extensionExercise.get(0).getFifthValue());
        } else {
            return (extensionExercise.get(0).getSecondValue() * extensionExercise.get(0).getForthValue())
                    + ((extensionExercise.get(0).getSecondValue() - 1) * extensionExercise.get(0).getFifthValue());
        }
    }

    private String getBodyParts() {
        return String.join(", ", contentDB
                .showBodyPartsById(workoutList.get(POSITION).getBodyParts()));
    }

    private String getEquipment() {
        List<StringModel> equipmentList = new ArrayList<>();
        String equipmentIDs = workoutList.get(POSITION).getEquipment();
        String[] splitEquipment = equipmentIDs.split(",");

        String setEquipment = "";
        if (Objects.equals(splitEquipment[0], "0")) {
            setEquipment = "NaN";
        } else {
            for (String s : splitEquipment) {
                StringModel equipmentModelDB = contentDB.showEquipment(Long.parseLong(s));
                equipmentList.add(equipmentModelDB);
            }
            for (int i = 0; i < equipmentList.size(); i++) {
                setEquipment = String.join(", ", equipmentList.get(i).getName());
            }
        }
        return setEquipment;
    }

    private void initializeTrainingTimer() {
        TrainingTimer trainingTimer = new TrainingTimer.TrainingTimerBuilder(requireActivity())
                .setSecond(durationSum)
                .build();

        trainingTimer.dynamicIncreaseTime(durationView);
    }
}