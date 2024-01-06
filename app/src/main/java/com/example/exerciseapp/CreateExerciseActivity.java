package com.example.exerciseapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.Exercise.CreateExercise.BasicExerciseInformation;
import com.example.exerciseapp.Exercise.CreateExercise.CreateExercise;
import com.example.exerciseapp.Exercise.CreateExercise.CreateExerciseExtension;
import com.example.exerciseapp.Exercise.CreateExercise.EquipmentVolume;
import com.example.exerciseapp.Exercise.CreateExercise.ExerciseViewModel;
import com.example.exerciseapp.Exercise.CreateExercise.SelectEquipment;
import com.example.exerciseapp.Exercise.ExerciseDetails;
import com.example.exerciseapp.Exercise.FragmentType;
import com.example.exerciseapp.Exercise.Summary;
import com.example.exerciseapp.mDatabases.Training.TrainingRepository;
import com.example.exerciseapp.mEnums.DetailsType;
import com.example.exerciseapp.mEnums.FragmentErrors;
import com.example.exerciseapp.mInterfaces.FragmentRespond;
import com.example.exerciseapp.mInterfaces.TrainingSummaryHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CreateExerciseActivity extends AppCompatActivity implements FragmentRespond,
        TrainingSummaryHandler {

    private static final String BASIC_INFORMATION_TAG = "BasicInformation";
    private static final String SELECT_EQUIPMENT_TAG = "Equipment";
    private static final String EQUIPMENT_VOLUME_TAG = "EquipmentVolume";
    private static final String EXERCISE_EXTENSION_TAG = "ExerciseExtension";
    private static final String EXERCISE_DETAIL_TAG = "ExerciseDetail";
    private static final String SUMMARY_TAG = "Summary";

    private Button actionBtn;

    private boolean fragmentIsReady = false;
    private boolean goToActivity = false;
    private Map<String, Runnable> exerciseCreatorMap;

    private ExerciseViewModel exerciseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_view_test);
        initializeViewModel();
        initializeWidgets();
        setBasicInformationFragment(actionBtn);
        setupExerciseCreatorMap();
        actionBtn.setOnClickListener(v -> handleButton());
    }

    private void initializeViewModel() {
        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);
    }

    private void initializeWidgets() {
        actionBtn = findViewById(R.id.button_create_exercise_button);
    }

    private void setBasicInformationFragment(Button button) {
        replaceFragment(new BasicExerciseInformation(), BASIC_INFORMATION_TAG);
        button.setText(R.string.next);
    }

    private void setupExerciseCreatorMap() {

        exerciseCreatorMap = new HashMap<>();
        exerciseCreatorMap.put(BASIC_INFORMATION_TAG, this::handleBasicExerciseInformation);
        exerciseCreatorMap.put(SELECT_EQUIPMENT_TAG, this::handleSelectEquipment);
        exerciseCreatorMap.put(EQUIPMENT_VOLUME_TAG, this::handleEquipmentVolume);
        exerciseCreatorMap.put(EXERCISE_EXTENSION_TAG, this::handleCreateExerciseExtension);
        exerciseCreatorMap.put(EXERCISE_DETAIL_TAG, this::handleDetailFragment);
    }

    private void handleBasicExerciseInformation() {
        replaceFragment(new SelectEquipment(), SELECT_EQUIPMENT_TAG);
    }

    private void handleSelectEquipment() {
        replaceFragment(new EquipmentVolume(), EQUIPMENT_VOLUME_TAG);
    }

    private void handleEquipmentVolume() {
        replaceFragment(new CreateExerciseExtension(), EXERCISE_EXTENSION_TAG);
        actionBtn.setText(R.string.next);
    }

    private void handleCreateExerciseExtension() {
        replaceFragment(ExerciseDetails.newInstance(DetailsType.MODIFY), EXERCISE_DETAIL_TAG);
        actionBtn.setText(R.string.save);
    }

    private void handleDetailFragment() {
        replaceFragment(Summary.newInstanceForCreating(
                FragmentType.CREATE_SUMMARY, exerciseViewModel.getName()), SUMMARY_TAG);
        CreateExercise createExercise = new CreateExercise(new TrainingRepository(this),
                exerciseViewModel);

        try {
            createExercise.create();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        goToActivity = true;
        actionBtn.setText(R.string.go_to_exercise_list);
    }


    private void replaceFragment(Fragment fragment, String tag) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame_create_exercise_container, fragment, tag);
        transaction.setReorderingAllowed(true);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    private void handleButton() {

        if (!fragmentIsReady) {
            Log.e("CreateExerciseActivity", "fragment is not ready: ");
        } else if (goToActivity) {
            startNewActivity();
        } else {
            nextFragment();
        }
    }

    private void startNewActivity() {
        Intent intent = new Intent(CreateExerciseActivity.this, LibraryActivity.class);
        startActivity(intent);
    }

    private void nextFragment() {
        for (Map.Entry<String, Runnable> entry : exerciseCreatorMap.entrySet()) {
            String tag = entry.getKey();
            Runnable task = entry.getValue();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
            if (fragment != null && fragment.isVisible()) {
                fragmentIsReady = false;
                task.run();
                break;
            }
        }
    }

    @Override
    public void fragmentAnswer(FragmentErrors fragmentErrors, String message) {

        switch (fragmentErrors) {
            case NO_ERROR:
                fragmentIsReady = true;
                break;
            case NO_VALUE:
                Toast.makeText(this, "Value is empty", Toast.LENGTH_SHORT).show();
                break;
            default:
                Log.e("CreateExerciseActivity", "fragmentAnswer: FragmentError does not exist");
        }
    }

    @Override
    public void summaryMessage(String name, String exerciseName, String duration, long exerciseId,
                               long extensionId, boolean conditionVal) {

    }
}