package com.example.exerciseapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.mClasses.ClockClass;
import com.example.exerciseapp.mClasses.GlobalClass;
import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mInterfaces.ITitleChangeListener;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.ExerciseModel;
import com.example.exerciseapp.mModels.IntegerModel;
import com.example.exerciseapp.mModels.StringModel;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

public class DetailsFragment extends Fragment implements View.OnClickListener {

    private Button nextBtnView;


    private static final String TAG = "DetailsFragment";
    private String listName;
    private String fragmentName;
    private long id;
    private List<ExerciseModel> exerciseDetail;
    private int buttonText;
    private final byte POSITION = 0;
    private int durationSum;
    private int fromWhere;


    private ContentBD contentBD;
    private UpdateIntegersDB updateIntegersDB;
    private ITitleChangeListener iTitleChangeListener;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            updateIntegersDB = (UpdateIntegersDB) context;
            iTitleChangeListener = (ITitleChangeListener) context;
        } catch (NullPointerException e) {
            throw new NullPointerException(context.toString() +
                    " must implement UpdateIntegerDB and/or ITitleChangeListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (iTitleChangeListener != null) {
            iTitleChangeListener.title(fragmentName);
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
            id = getArguments().getLong("id");
            listName = getArguments().getString("listName", getString(R.string.nan));
            buttonText = getArguments().getInt("btnName", R.string.nan);
            fromWhere = getArguments().getInt("fromWhere", -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_details, container, false);
        contentBD = new ContentBD(requireActivity());
        initView(mView);
        nextBtnView.setOnClickListener(this);
        return mView;
    }

    private void initView(View v) {

        ImageView imageIMG = v.findViewById(R.id.frag_details_image);
        TextView nameTV = v.findViewById(R.id.frag_details_name);
        TextView levelTV = v.findViewById(R.id.frag_details_level);
        TextView bodyPartsTV = v.findViewById(R.id.frag_details_body_parts);
        TextView equipmentTV = v.findViewById(R.id.frag_details_equipment);
        TextView typeTV = v.findViewById(R.id.frag_details_type);
        TextView kcalTV = v.findViewById(R.id.frag_details_kcal);
        TextView durationTV = v.findViewById(R.id.frag_details_duration);
        TextView descriptionTV = v.findViewById(R.id.frag_details_description);
        nextBtnView = v.findViewById(R.id.frag_details_button);


        Log.i(TAG, "(initView)fromWhere: : " + fromWhere);
        Log.i(TAG, "id: : " + id);
        if (fromWhere == 0) {
            exerciseDetail = contentBD.showExerciseById(id);
        } else {
            exerciseDetail = contentBD.showUserExerciseById(id);
        }
        ExerciseModel exercise = exerciseDetail.get(POSITION);

//        imageIMG.setImageResource(exercise.getImage());
        nameTV.setText(exercise.getName());
        typeTV.setText(exercise.getType() == 1 ? getString(R.string.repetition) : getString(R.string.time));
        String[] names = {getString(R.string.beginner), getString(R.string.intermediate),
                getString(R.string.advanced)};
        if (exercise.getLevel() == -1) {
            levelTV.setText(getString(R.string.unknown));
        } else {
            levelTV.setText(names[exercise.getLevel() - 1]);
        }

        String bodyPartsText;
        if (exerciseDetail.get(0).getBodyParts() == -1) {
            bodyPartsText = getString(R.string.unknown);
        } else {
            bodyPartsText = String.join(",\n ",
                    contentBD.showBodyPartsById(exerciseDetail.get(POSITION).getBodyParts()));
        }
        bodyPartsTV.setText(bodyPartsText);

        List<StringModel> equipmentList = new ArrayList<>();
        String equipmentIDs = exerciseDetail.get(POSITION).getEquipment();
        String[] splitEquipment = equipmentIDs.split(",");
        if (splitEquipment[0].equals("-1") || splitEquipment[0].equals("")) {
            equipmentTV.setText(getString(R.string.unknown));
        } else {

            LinkedHashSet<String> equipmentSet = new LinkedHashSet<>();
            for (String s : splitEquipment) {
                StringModel equipmentModelDB = contentBD.showEquipment(Long.parseLong(s));
                equipmentList.add(equipmentModelDB);
            }
            for (int i = 0; i < equipmentList.size(); i++) {
                equipmentSet.add(equipmentList.get(i).getName());
            }
            StringBuilder setEquipment = new StringBuilder();
            for (String equipment : equipmentSet) {
                setEquipment.append(equipment);
                if (!equipment.equals(equipmentSet.toArray()[equipmentSet.size() - 1])) {
                    setEquipment.append(",");
                    setEquipment.append("\n");
                }
            }
            equipmentTV.setText(setEquipment.toString());
        }


        if (exercise.getKcal() < 0) {
            kcalTV.setText(R.string.unknown);
        } else {
            kcalTV.setText(String.valueOf(exercise.getKcal()));
        }
        List<IntegerModel> extensionExe = contentBD.showExerciseExtensionId(
                exerciseDetail.get(0).getExtension());

//            1 -> type: repetition, 2 -> type = time;
        if (exerciseDetail.get(0).getType() == 1) {
            durationSum += (extensionExe.get(0).getSecondValue() * GlobalClass.DEFAULT_EXERCISE_TIME) +
                    ((extensionExe.get(0).getSecondValue() - 1) * extensionExe.get(0).getFifthValue());
        } else {
            durationSum += (extensionExe.get(0).getSecondValue() * extensionExe.get(0).getForthValue()) +
                    ((extensionExe.get(0).getSecondValue() - 1) * extensionExe.get(0).getFifthValue());
        }
        new ClockClass(requireActivity()).setSecond(durationSum).dynamicIncreaseTime(durationTV);
        descriptionTV.setText(String.valueOf(exercise.getDescription()));
        fragmentName = getString(R.string.details);
        nextBtnView.setText(getString(buttonText));
    }

    @Override
    public void onClick(View view) {
        updateIntegersDB.values(listName, (int) id, exerciseDetail.get(POSITION).getType(),
                fromWhere, GlobalClass.FOURTH_VAL);
    }
}