package com.example.exerciseapp;

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

import com.example.exerciseapp.mAdapters.SearchAdapter;
import com.example.exerciseapp.mClasses.ClockClass;
import com.example.exerciseapp.mClasses.GlobalClass;
import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mInterfaces.ISingleIntegerValue;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.ExerciseModel;
import com.example.exerciseapp.mModels.FourElementsModel;
import com.example.exerciseapp.mModels.IntegerModel;
import com.example.exerciseapp.mModels.StringModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorkoutList extends Fragment {

    private Button startWorkoutBtn;
    private ToggleButton showHideBtn;
    private TableLayout secondPartLayout;
    private ImageView imageView;
    private TextView nameView;
    private TextView levelView;
    private TextView bodyPartsView;
    private TextView equipmentView;
    private TextView kcalView;
    private TextView durationView;
    private TextView descriptionView;
    private RecyclerView exerciseListView;

    private List<FourElementsModel> exercisesList;
    private long id;
    private int durationExe;
    private int durationSum;
    private long kcalSum;
    private final byte POSITION = 0;
    private List<ExerciseModel> workoutList;

    private SearchAdapter adapter;
    private UpdateIntegersDB updateIntegersDB;
    private ISingleIntegerValue iSingleIntegerValue;
    private ContentBD contentBD;

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
        initView(mView);
        return mView;
    }

    private void initView(View v) {

        startWorkoutBtn = v.findViewById(R.id.frag_workout_list_start_btn);
        showHideBtn = v.findViewById(R.id.frag_workout_list_show_hide_btn);
        secondPartLayout = v.findViewById(R.id.frag_workout_list_second_part_layout);

        imageView = v.findViewById(R.id.frag_workout_list_image);
        nameView = v.findViewById(R.id.frag_workout_list_name);
        levelView = v.findViewById(R.id.frag_workout_list_level);
        bodyPartsView = v.findViewById(R.id.frag_workout_list_body_parts);
        equipmentView = v.findViewById(R.id.frag_workout_list_equipment);
        kcalView = v.findViewById(R.id.frag_workout_list_kcal);
        durationView = v.findViewById(R.id.frag_workout_list_duration);
        descriptionView = v.findViewById(R.id.frag_workout_list_description);
        exerciseListView = v.findViewById(R.id.frag_workout_list_exercise_list);

        contentBD = new ContentBD(requireActivity());
        workoutList = contentBD.showWorkoutById(id);

        showHideBtn.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                secondPartLayout.setVisibility(View.VISIBLE);
            } else {
                secondPartLayout.setVisibility(View.GONE);
            }
        });

        startWorkoutBtn.setOnClickListener(view -> iSingleIntegerValue.
                singleIntValue("startWorkout", 1));

        secondPartLayout.setVisibility(View.GONE);

        fillView();

        adapter = new SearchAdapter(requireContext(), exercisesList, "workoutList",
                updateIntegersDB);
        exerciseListView.setHasFixedSize(true);
        exerciseListView.setLayoutManager(new LinearLayoutManager(requireActivity(),
                RecyclerView.VERTICAL, false));
        exerciseListView.setAdapter(adapter);

    }

    private void fillView() {

        String temp = contentBD.showWorkoutById(workoutList.get(POSITION).getId()).
                get(0).getExerciseId();
        String[] exerciseStr = temp.split(",");
        long[] exercisesId = new long[exerciseStr.length];
        for (int i = 0; i < exercisesId.length; i++) {
            exercisesId[i] = Long.parseLong(exerciseStr[i]);
        }

        exercisesList = new ArrayList<>();

        for (long l : exercisesId) {
            List<ExerciseModel> temp2 = contentBD.showExerciseById(l);
            kcalSum += temp2.get(0).getKcal();
            List<IntegerModel> extensionExe = contentBD.showExerciseExtensionId(
                    temp2.get(0).getExtension());

//            1 -> type: repetition, 2 -> type = time;
            if (temp2.get(0).getType() == 1) {
                durationExe += (extensionExe.get(0).getSecondValue() * GlobalClass.DEFAULT_EXERCISE_TIME) +
                        ((extensionExe.get(0).getSecondValue() - 1) * extensionExe.get(0).getFifthValue());
                durationSum += durationExe;
            } else {
                durationExe += (extensionExe.get(0).getSecondValue() * extensionExe.get(0).getForthValue()) +
                        ((extensionExe.get(0).getSecondValue() - 1) * extensionExe.get(0).getFifthValue());
                durationSum += durationExe;
            }

            FourElementsModel model = new FourElementsModel(temp2.get(0).getId(),
                    temp2.get(0).getImage(), temp2.get(0).getName(),
                    String.valueOf(temp2.get(0).getKcal()), temp2.get(0).getLevel(),
                    durationExe, temp2.get(0).getFromWhere());
            exercisesList.add(model);
            durationExe = 0;
        }

        String[] names = {getString(R.string.beginner), getString(R.string.intermediate),
                getString(R.string.advanced)};

        String bodyPartsText = String.join(", ",
                contentBD.showBodyPartsById(workoutList.get(POSITION).getBodyParts()));

        List<StringModel> equipmentList = new ArrayList<>();
        String equipmentIDs = workoutList.get(POSITION).getEquipment();
        String[] splitEquipment = equipmentIDs.split(",");

        String setEquipment = "";
        if (Objects.equals(splitEquipment[0], "0")) {
            setEquipment = "NaN";
        } else {
            for (String s : splitEquipment) {
                StringModel equipmentModelDB = contentBD.showEquipment(Long.parseLong(s));
                equipmentList.add(equipmentModelDB);
            }
            for (int i = 0; i < equipmentList.size(); i++) {
                setEquipment = String.join(", ", equipmentList.get(i).getName());
            }
        }

        ClockClass clockClass = new ClockClass(requireActivity());
        clockClass.setSecond(durationSum);

        clockClass.dynamicIncreaseTime(durationView);
        nameView.setText(workoutList.get(POSITION).getName());
        equipmentView.setText(setEquipment);
        kcalView.setText(String.valueOf(kcalSum));
        bodyPartsView.setText(bodyPartsText);
        levelView.setText(names[workoutList.get(POSITION).getLevel() - 1]);
        descriptionView.setText(workoutList.get(POSITION).getDescription());
    }
}