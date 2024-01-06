package com.example.exerciseapp.Exercise;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.Exercise.CreateExercise.ExerciseViewModel;
import com.example.exerciseapp.R;
import com.example.exerciseapp.mAdapters.EquipmentListAdapter;
import com.example.exerciseapp.mDatabases.Training.TrainingRepository;
import com.example.exerciseapp.mEnums.DetailsType;
import com.example.exerciseapp.mEnums.FragmentErrors;
import com.example.exerciseapp.mInterfaces.FragmentRespond;
import com.example.exerciseapp.mModels.ItemListModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ExerciseDetails extends Fragment {

    private static final String ARG_DETAILS_TYPE = "detailsType";

    private ImageView imageImg;
    private TextView nameTxt, typeTxt, levelTxt;
    private Button showEquipmentBtn;
    private TextView numberOfSeriesTxt, numberOfVolumeTxt, numberOfBreakLengthTxt;
    private TextView numberOfUnitKcalTxt, numberOfSumKcalTxt;
    private EditText descriptionEdit;
    private TextView descriptionTxt;

    private DetailsType detailsType;

    private LifecycleOwner viewLifecycleOwner;
    private ExerciseViewModel exerciseViewModel;
    private TrainingRepository trainingRepository;
    private FragmentRespond fragmentRespond;

    public ExerciseDetails() {
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

    public static ExerciseDetails newInstance(DetailsType detailsType) {
        ExerciseDetails fragment = new ExerciseDetails();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DETAILS_TYPE, detailsType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            detailsType = (DetailsType) getArguments().getSerializable(ARG_DETAILS_TYPE);
        }

        trainingRepository = new TrainingRepository(requireActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_exercise_details, container, false);
        initializeWidgets(mView);
        fillWidgets();

        fragmentRespond.fragmentAnswer(FragmentErrors.NO_ERROR, "");
        return mView;
    }

    private void initializeWidgets(View v) {

        imageImg = v.findViewById(R.id.image_exercise_details);
        nameTxt = v.findViewById(R.id.text_name_exercise_details);
        typeTxt = v.findViewById(R.id.text_type_exercise_details);
        levelTxt = v.findViewById(R.id.text_level_exercise_details);
        numberOfSeriesTxt = v.findViewById(R.id.text_sets_exercise_details);
        numberOfVolumeTxt = v.findViewById(R.id.text_volume_exercise_details);
        numberOfBreakLengthTxt = v.findViewById(R.id.text_break_length_exercise_details);
        numberOfUnitKcalTxt = v.findViewById(R.id.text_unit_kcal_exercise_details);
        numberOfSumKcalTxt = v.findViewById(R.id.text_sum_kcal_exercise_details);
        descriptionTxt = v.findViewById(R.id.text_description_exercise_details);
        descriptionEdit = v.findViewById(R.id.edit_description_exercise_details);
        showEquipmentBtn = v.findViewById(R.id.button_show_equipment_exercise_details);
    }

    private ExerciseViewModel getExerciseViewModel() {
        return new ViewModelProvider(requireActivity()).get(ExerciseViewModel.class);
    }

    private void fillWidgets() {

        viewLifecycleOwner = getViewLifecycleOwner();
        exerciseViewModel = getExerciseViewModel();

        setImage();
        setName();
        setLevel();
        setExerciseType();

        showEquipmentBtn.setOnClickListener(v -> setEquipmentList());

        setNumberOfSeriesTxt();
        setNumberOfVolumeTxt();
        setNumberOfBreakLengthTxt();
        setNumberOfUnitKcalTxt();

        setNumberOfSumKcal();
        setDescriptionTxt(detailsType);
    }

    private void setImage() {
//        observeAndSetTextValue(ExerciseViewModel.ExerciseTextFields.IMAGE, imageImg);
    }

    private void setName() {
        observeAndSetTextValue(ExerciseViewModel.ExerciseTextFields.NAME, nameTxt);
    }

    private void setDescriptionTxt(DetailsType detailsType) {
        if (detailsType == DetailsType.MODIFY) {
            observeAndGetTextValue(ExerciseViewModel.ExerciseTextFields.DESCRIPTION, descriptionEdit);
        } else {
            observeAndSetTextValue(ExerciseViewModel.ExerciseTextFields.DESCRIPTION, descriptionTxt);
        }
    }

    private void observeAndGetTextValue(ExerciseViewModel.ExerciseTextFields field,
                                        EditText editText) {
        exerciseViewModel.setTextValues(field, editText.getText().toString());
    }

    private void observeAndSetTextValue(ExerciseViewModel.ExerciseTextFields field,
                                        TextView textView) {
        exerciseViewModel.getTextValues().observe(viewLifecycleOwner, textValues -> {
            String text = textValues.get(field);
            if (text != null) {
                textView.setText(text);
            }
        });
    }

    private void setLevel() {
        observeAndSetText(exerciseViewModel.getLevel(), levelTxt);
    }

    private void setExerciseType() {
        observeAndSetText(exerciseViewModel.getExerciseType(), typeTxt);
    }

    private void observeAndSetText(LiveData<?> liveData, TextView textView) {
        liveData.observe(viewLifecycleOwner, value -> textView.setText(value.toString()));
    }

    private void setEquipmentList() {

        Map<Integer, Double> equipmentMapVolume = exerciseViewModel.getEquipmentMapVolume();

        List<ItemListModel> itemListModelList = new ArrayList<>();

        for (Map.Entry<Integer, Double> entry : equipmentMapVolume.entrySet()) {
            final ItemListModel itemListModel = new ItemListModel();
            int equipmentId = entry.getKey();
            double equipmentValue = entry.getValue();

            trainingRepository.getEquipmentById(equipmentId).observe(getViewLifecycleOwner(),
                    equipmentModel -> {
                        itemListModel.setImage(equipmentModel.getImage());
                        itemListModel.setName(equipmentModel.getName());
                        itemListModel.setValue(equipmentValue);
                        itemListModelList.add(itemListModel);

                        if (itemListModelList.size() == equipmentMapVolume.size()) {

                            EquipmentListAdapter equipmentAdapter =
                                    new EquipmentListAdapter(requireActivity(),
                                            itemListModelList);

                            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                            builder.setTitle("Equipment");

                            builder.setAdapter(equipmentAdapter, (dialogInterface, i) -> {

                            });

                            builder.setNegativeButton("Close", ((dialogInterface, i) -> dialogInterface.dismiss()));
                            AlertDialog alert = builder.create();
                            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                            layoutParams.copyFrom(Objects.requireNonNull(alert.getWindow()).getAttributes());
                            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;

                            alert.show();
                            alert.getWindow().setAttributes(layoutParams);
                        }
                    });
        }
    }

    private void setNumberOfSeriesTxt() {
        observeAndSetNumbersValue(ExerciseViewModel.ExerciseIntegerFields.NUMBER_OF_SERIES,
                numberOfSeriesTxt);
    }

    private void setNumberOfVolumeTxt() {
        observeAndSetNumbersValue(ExerciseViewModel.ExerciseIntegerFields.VOLUME, numberOfVolumeTxt);
    }

    private void setNumberOfBreakLengthTxt() {
        observeAndSetNumbersValue(ExerciseViewModel.ExerciseIntegerFields.BREAK_LENGTH,
                numberOfBreakLengthTxt);
    }

    public void setNumberOfUnitKcalTxt() {
        observeAndSetNumbersValue(ExerciseViewModel.ExerciseIntegerFields.KCAL, numberOfUnitKcalTxt);
    }

    private void observeAndSetNumbersValue(ExerciseViewModel.ExerciseIntegerFields field,
                                           TextView textView) {
        exerciseViewModel.getNumericalValues().observe(viewLifecycleOwner, numericalValues -> {
            Integer number = numericalValues.get(field);
            if (number != null) {
                textView.setText(String.valueOf(number));
            }
        });
    }

    private void setNumberOfSumKcal() {
        exerciseViewModel.getNumericalValues().observe(viewLifecycleOwner, numericalValues -> {
            Integer unitKcal = numericalValues.get(ExerciseViewModel.ExerciseIntegerFields.KCAL);
            Integer numberOfSeries = numericalValues.get(
                    ExerciseViewModel.ExerciseIntegerFields.NUMBER_OF_SERIES
            );

            if (unitKcal != null && numberOfSeries != null) {
                int sumKcal = unitKcal * numberOfSeries;
                numberOfSumKcalTxt.setText(String.valueOf(sumKcal));
            }
        });
    }
}