package com.example.exerciseapp.Exercise;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mClasses.GlobalClass;
import com.example.exerciseapp.mClasses.TrainingTimer;
import com.example.exerciseapp.mDatabases.ContentDB;
import com.example.exerciseapp.mInterfaces.TitleChangeListener;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.ExerciseDescriptionModel;
import com.example.exerciseapp.mModels.IntegerModel;
import com.example.exerciseapp.mModels.StringModel;

import java.util.LinkedHashSet;
import java.util.List;

public class DetailsFragment extends Fragment implements View.OnClickListener {

    private final Integer UNKNOWN = -1;
    private final int ZERO = 0;
    private ImageView imageIMG;
    private TextView nameTV;
    private TextView levelTV;
    private TextView bodyPartsTV;
    private TextView equipmentTV;
    private TextView typeTV;
    private TextView kcalTV;
    private TextView durationTV;
    private TextView descriptionTV;
    private Button nextBtnView;

    private String listName;
    private String fragmentName;
    private long id;
    private List<ExerciseDescriptionModel> exerciseDetail;
    private int buttonText;
    private int fromWhere;

    private ContentDB contentDB;
    private ExerciseDescriptionModel exercise;
    private UpdateIntegersDB updateIntegersDB;
    private TitleChangeListener titleChangeListener;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            updateIntegersDB = (UpdateIntegersDB) context;
            titleChangeListener = (TitleChangeListener) context;
        } catch (NullPointerException e) {
            throw new NullPointerException(context.toString() +
                    " must implement UpdateIntegerDB and/or TitleChangeListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (titleChangeListener != null) {
            titleChangeListener.title(fragmentName);
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
            id = getArguments().getLong("id");
            listName = getArguments().getString("listName", getString(R.string.nan));
            buttonText = getArguments().getInt("btnName", R.string.nan);
            fromWhere = getArguments().getInt("fromWhere", UNKNOWN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_details, container, false);
        contentDB = new ContentDB(requireActivity());
        initializeView(mView);
        fillView();

        nextBtnView.setOnClickListener(this);
        return mView;
    }

    private void initializeView(View v) {

        imageIMG = v.findViewById(R.id.frag_details_image);
        nameTV = v.findViewById(R.id.frag_details_name);
        levelTV = v.findViewById(R.id.frag_details_level);
        bodyPartsTV = v.findViewById(R.id.frag_details_body_parts);
        equipmentTV = v.findViewById(R.id.frag_details_equipment);
        typeTV = v.findViewById(R.id.frag_details_type);
        kcalTV = v.findViewById(R.id.frag_details_kcal);
        durationTV = v.findViewById(R.id.frag_details_duration);
        descriptionTV = v.findViewById(R.id.frag_details_description);
        nextBtnView = v.findViewById(R.id.frag_details_button);
    }

    private void fillView() {


        fragmentName = getString(R.string.details);
        nextBtnView.setText(getString(buttonText));


        if (fromWhere == ZERO) {
            exerciseDetail = contentDB.showExerciseById(id);
        } else {
            exerciseDetail = contentDB.showUserExerciseById(id);
        }
        exercise = exerciseDetail.get(ZERO);

//        imageIMG.setImageResource(new StorageClass().exercise.getImage());
        nameTV.setText(exercise.getName());
        typeTV.setText(exercise.getType() == 1 ? getString(R.string.repetition) : getString(R.string.time));

        levelTV.setText(setExerciseLevel());
        bodyPartsTV.setText(setBodyParts());
        equipmentTV.setText(setEquipment());

        kcalTV.setText(exercise.getKcal() < ZERO ? getString(R.string.unknown) :
                String.valueOf(exercise.getKcal()));

        setupTimerClock(durationTV, calculateFullTrainingTimeInSeconds());

        descriptionTV.setText(String.valueOf(exercise.getDescription()));
    }

    private String setEquipment() {

        String equipmentIDs = exerciseDetail.get(ZERO).getEquipment();
        String[] splitEquipment = equipmentIDs.split(",");

        if (splitEquipment[ZERO].equals(UNKNOWN.toString()) || splitEquipment[ZERO].equals("")) {
            return getString(R.string.unknown);
        } else {

            LinkedHashSet<String> equipmentSet = new LinkedHashSet<>();
            for (String s : splitEquipment) {
                StringModel equipmentModelDB = contentDB.showEquipment(Long.parseLong(s));
                equipmentSet.add(equipmentModelDB.getName());
            }
            return String.join(",\n", equipmentSet);
        }
    }

    private String setBodyParts() {
        if (exerciseDetail.get(ZERO).getBodyParts() == UNKNOWN) {
            return getString(R.string.unknown);
        } else {
            return String.join(",\n ",
                    contentDB.showBodyPartsById(exerciseDetail.get(ZERO).getBodyParts()));
        }
    }

    private String setExerciseLevel() {
        String[] names = {getString(R.string.beginner), getString(R.string.intermediate),
                getString(R.string.advanced)};
        if (exercise.getLevel() == UNKNOWN) {
            return getString(R.string.unknown);
        } else {
            return names[exercise.getLevel() - 1];
        }
    }

    private void setupTimerClock(TextView timeView, int trainingTime) {
        new TrainingTimer.TrainingTimerBuilder(requireActivity())
                .setSecond(trainingTime)
                .build()
                .dynamicIncreaseTime(timeView);
    }

    private int calculateFullTrainingTimeInSeconds() {
        List<IntegerModel> extensionExe = contentDB.showExerciseExtensionId(exerciseDetail.get(ZERO)
                .getExtension());
        IntegerModel extension = extensionExe.get(ZERO);
        int sets = extension.getSecondValue();
        int rest = extension.getFifthValue();
        int multiple = exerciseDetail.get(ZERO).getType() == 1 ? GlobalClass.DEFAULT_EXERCISE_TIME :
                extension.getForthValue();
        return (sets * multiple) + ((sets - 1) * rest);
    }

    @Override
    public void onClick(View view) {
        updateIntegersDB.values(listName, (int) id, exerciseDetail.get(ZERO).getType(),
                fromWhere, GlobalClass.FOURTH_VAL);
    }
}