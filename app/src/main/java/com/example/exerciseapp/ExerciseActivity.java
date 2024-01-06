package com.example.exerciseapp;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.exerciseapp.Exercise.RepetitionExerciseFragmentToChange;
import com.example.exerciseapp.Exercise.Summary;
import com.example.exerciseapp.Exercise.TimeBreakFragment;
import com.example.exerciseapp.Exercise.TimeExerciseFragmentToChange;
import com.example.exerciseapp.mClasses.GlobalClass;
import com.example.exerciseapp.mDatabases.ContentDB;
import com.example.exerciseapp.mInterfaces.FragmentRespondToChange;
import com.example.exerciseapp.mInterfaces.FragmentSupportListener;
import com.example.exerciseapp.mInterfaces.TrainingSummaryHandler;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.UserExercisePerformedModel;
import com.example.exerciseapp.mModels.WorkoutModelToChange;
import com.example.exerciseapp.mResource.EmptyToChangeFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExerciseActivity extends AppCompatActivity implements FragmentSupportListener,
        UpdateIntegersDB, TrainingSummaryHandler {

    private static final String REPETITION_EXE_TAG = "tagRepetitionExerciseFragment";
    private static final String TIME_EXE_TAG = "tagTimeExerciseFragment";
    private static final String TIME_BREAK_TAG = "tagBreakExerciseFragment";
    private static final String EMPTY_TAG = "tagEmptyFragment";
    private static final int MINUS_ONE = -1;
    private static final int REPETITION_EXERCISE_FRAGMENT = 1;
    private static final int TIME_EXERCISE_FRAGMENT = 2;

    private Button nextBtn;

    private long currentUserId;

    private long exerciseId = 0;
    private String exerciseName;
    private long extensionId;
    private String extensionExerciseName = "";
    private int fromWhere;
    private byte typeExercise = 0;
    private double duration;
    private int rest;
    private int tempIdForExercises = -1;

    private byte scenario;
    private int currentExercise;
    private byte currentFragment;
    private long idMain;
    private List<WorkoutModelToChange> workoutPattern;
    private long startTime;
    private boolean lastSet;

    private ContentDB contentDB;
    private TimeExerciseFragmentToChange timeExerciseFragment;
    private RepetitionExerciseFragmentToChange repetitionExerciseFragment;
    private EmptyToChangeFragment emptyFragment;
    private Summary summaryFragment;
    private TimeBreakFragment timeBreakFragment;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(GlobalClass.initLanguage(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        initView(savedInstanceState);

        startTime = SystemClock.elapsedRealtime();

        setNextButtonOnClickListener();
    }

    private void setNextButtonOnClickListener() {
        nextBtn.setOnClickListener(v -> {
            Fragment visibleFragment = getVisibilityFragment();
            if (visibleFragment instanceof FragmentRespondToChange) {
                ((FragmentRespondToChange) visibleFragment).fragmentMessage();
            }
        });
    }

    private Fragment getVisibilityFragment() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible()) {
                return fragment;
            }
        }
        return null;
    }

    private void initView(Bundle savedInstanceState) {

        initializeVariables();

        if (findViewById(R.id.act_exercise_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            int sumExercise = 0;
            sumExercise = prepareWorkoutPattern(sumExercise);
            addLastExerciseToWorkoutPattern(sumExercise);
        }
    }

    private void initializeVariables() {

        nextBtn = findViewById(R.id.act_exercise_button);

        timeBreakFragment = new TimeBreakFragment();

        Intent intent = getIntent();
        currentUserId = intent.getLongExtra(GlobalClass.userID, -1);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            exerciseId = bundle.getLong("id");
            typeExercise = bundle.getByte("type");
            fromWhere = bundle.getInt("fromWhere");
            idMain = exerciseId;
        }
        workoutPattern = new ArrayList<>();
        contentDB = new ContentDB(this);
    }

    @VisibleForTesting
    private int prepareWorkoutPattern(int sumExercise) {
        if (typeExercise > 0) {
            sumExercise = handleSingleExercise(sumExercise);
        } else {
            sumExercise = handleMultipleExercises(sumExercise);
        }
        extensionId = workoutPattern.get(currentExercise).getExtensionId();
        typeExercise = workoutPattern.get(currentExercise).getType();
        extensionExerciseName = workoutPattern.get(0).getName();
        addFragment(workoutPattern.get(0).getExerciseId(), typeExercise);
        return sumExercise;
    }

    @VisibleForTesting
    private int handleSingleExercise(int sumExercise) {

        if (fromWhere == 0) {
            exerciseName = contentDB.showExerciseById(exerciseId).get(0).getName();
            WorkoutModelToChange singleExercise = createWorkoutModel(exerciseId, fromWhere, typeExercise, exerciseName);
            workoutPattern.add(singleExercise);
        } else {
            exerciseName = contentDB.showUserExerciseById(exerciseId).get(0).getName();
            WorkoutModelToChange singleExercise = createWorkoutModel(exerciseId, fromWhere, typeExercise, exerciseName);
            workoutPattern.add(singleExercise);
        }
        sumExercise++;
        return sumExercise;
    }

    @VisibleForTesting
    private int handleMultipleExercises(int sumExercise) {
        exerciseName = contentDB.getWorkoutById(exerciseId).get(0).getName();
        String exerciseIds = contentDB.showExercisesFromWorkout(exerciseId);
        String[] exerciseList = exerciseIds.split(",");
        long[] exercisesId = new long[exerciseList.length];
        int exercisesIdLength = exercisesId.length;

        for (int i = 0; i < exercisesIdLength; i++) {
            exercisesId[i] = Long.parseLong(exerciseList[i]);
            sumExercise++;
        }

        for (long l : exercisesId) {
            WorkoutModelToChange model = createWorkoutModel(l,
                    contentDB.showExerciseById(l).get(0).getFromWhere(),
                    (byte) contentDB.showExerciseByIdFromWorkout(l).get(0).getType(),
                    contentDB.showExerciseById(l).get(0).getName());
            workoutPattern.add(model);
        }
        for (WorkoutModelToChange model :
                workoutPattern) {
            Log.i(TAG, "handleMultipleExercises: " + model);
        }
        return sumExercise;
    }

    private WorkoutModelToChange createWorkoutModel(long id, int fromWhere, byte type, String name) {
        tempIdForExercises++;
        return new WorkoutModelToChange(tempIdForExercises, id, contentDB.showExerciseById(id).get(0).getExtension(),
                type, name, fromWhere);
    }

    private void addLastExerciseToWorkoutPattern(int sumExercise) {
        WorkoutModelToChange lastExercise = new WorkoutModelToChange(sumExercise + 1, (long) MINUS_ONE, (byte) 0);
        workoutPattern.add(lastExercise);
    }

    private void elapsed() {
        long endTime = SystemClock.elapsedRealtime() - startTime;
        duration = endTime / 1000.0;
    }

    private String getCurrentDate() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.US);
        return dateFormat.format(new Date());
    }

    private void addFragment(long id, int type) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle sendToFragment = new Bundle();
        sendToFragment.putLong("id", id);
        sendToFragment.putString("exerciseName", extensionExerciseName);
        sendToFragment.putInt("fromWhere", fromWhere);
        if (type == 1) {
            repetitionExerciseFragment = new RepetitionExerciseFragmentToChange();
            repetitionExerciseFragment.setArguments(sendToFragment);
            ft.add(R.id.act_exercise_container, repetitionExerciseFragment, REPETITION_EXE_TAG);
        } else if (type == 2) {
            timeExerciseFragment = new TimeExerciseFragmentToChange();
            timeExerciseFragment.setArguments(sendToFragment);
            ft.add(R.id.act_exercise_container, timeExerciseFragment, TIME_EXE_TAG);
        } else {
            emptyFragment = new EmptyToChangeFragment();
            ft.add(R.id.act_exercise_container, emptyFragment, EMPTY_TAG);
        }
        ft.setReorderingAllowed(true);
        ft.commit();
    }

    private void swapFragments() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (lastSet && workoutPattern.get(currentExercise + 1).getExerciseId() == -1) {
            addSummaryFragment(extensionId);
            return;
        }
        if (lastSet) {
            extensionExerciseName = workoutPattern.get(currentExercise + 1).getName();
        } else {
            extensionExerciseName = workoutPattern.get(currentExercise).getName();
        }
        Bundle sendRestValue = new Bundle();
        sendRestValue.putInt("rest", rest);
        sendRestValue.putString("exerciseName", extensionExerciseName);
        switch (currentFragment) {
            case 1:
            case 2:
                ft.detach(currentFragment == 1 ? repetitionExerciseFragment : timeExerciseFragment);
                timeBreakFragment.setArguments(sendRestValue);
                runOnUiThread(() -> nextBtn.setVisibility(View.GONE));
                ft.replace(R.id.act_exercise_container, timeBreakFragment, TIME_BREAK_TAG);
                break;
            case 3:
                ft.remove(timeBreakFragment);
                runOnUiThread(() -> nextBtn.setVisibility(View.VISIBLE));
                if (scenario == REPETITION_EXERCISE_FRAGMENT) {
                    ft.attach(repetitionExerciseFragment);
                    ft.replace(R.id.act_exercise_container, repetitionExerciseFragment, REPETITION_EXE_TAG);
                } else if (scenario == TIME_EXERCISE_FRAGMENT) {
                    ft.attach(timeExerciseFragment);
                    ft.replace(R.id.act_exercise_container, timeExerciseFragment, TIME_EXE_TAG);
                } else {
                    throw new NullPointerException("scenario != (1 || 2)");
                }
                break;
            default:
                ft.attach(emptyFragment);
        }
        ft.setReorderingAllowed(true);
        ft.commit();
    }

    @Override
    public void checkCondition(boolean param) {
        if (!param) {
            return;
        }

        removeFragments(REPETITION_EXE_TAG, TIME_EXE_TAG, TIME_BREAK_TAG);

        if (workoutPattern == null) {
            addSummaryFragment(MINUS_ONE);
            return;
        }
        currentExercise++;
        typeExercise = workoutPattern.get(workoutPattern.get(currentExercise).getId()).getType();
        if (workoutPattern.get(workoutPattern.get(currentExercise).getId()).getExerciseId() == MINUS_ONE) {
            Log.i(TAG, "checkCondition: summary if");
            for (WorkoutModelToChange model :
                    workoutPattern) {
                Log.i(TAG, "checkCondition:(if) " + model);
            }
            addSummaryFragment(extensionId);
            return;
        }
        addFragment(workoutPattern.get(
                workoutPattern.get(currentExercise).getId()).getExerciseId(), typeExercise);

    }

    private void removeFragments(String... tags) {
        FragmentManager fm = getSupportFragmentManager();
        for (String tag : tags) {
            Fragment fragment = fm.findFragmentByTag(tag);
            if (fragment != null) {
                fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
            }
        }
    }

    private void addSummaryFragment(long extensionId) {
        summaryFragment = new Summary();
        Bundle bundle = new Bundle();
        elapsed();
        bundle.putLong("id", idMain);
        bundle.putDouble("duration", duration);
        bundle.putString("exerciseName", exerciseName);
        bundle.putLong("extensionId", extensionId);
        bundle.putInt("fromWhere", fromWhere);
        FragmentTransaction summaryTransaction = getSupportFragmentManager().beginTransaction();
        summaryFragment.setArguments(bundle);
        summaryTransaction.replace(R.id.act_exercise_container, summaryFragment, "summaryTag");
        summaryTransaction.setReorderingAllowed(true);
        summaryTransaction.commit();
    }

    @Override
    public void values(String listName, int firstValue, int secondValue,
                       int thirdValue, int fourthValue) {

        rest = firstValue;
        currentFragment = (byte) secondValue;
        if (thirdValue > 0) {
            scenario = (byte) thirdValue;
        }
        lastSet = fourthValue == 1;
        swapFragments();
    }

    @Override
    public void summaryMessage(String name, String exerciseName, String duration, long exerciseId,
                               long extensionId, boolean conditionVal) {
        if (!conditionVal) {
            return;
        }

        switch (name) {
            case "EmptyToChangeFragment":
                handleEmptyFragment(exerciseName, extensionId);
                break;
            case "summaryFragment":
                handleSummaryFragment(duration, exerciseId, extensionId);
                break;
            default:
                throw new IllegalStateException("That " + name + " does not exit");
        }
    }

    private void handleEmptyFragment(String exerciseName, long extensionId) {
        summaryFragment = new Summary();
        Bundle bundle = new Bundle();
        elapsed();
        bundle.putLong("id", idMain);
        bundle.putDouble("duration", this.duration);
        bundle.putString("exerciseName", exerciseName);
        bundle.putLong("extensionId", extensionId);
        bundle.putInt("fromWhere", this.fromWhere);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        summaryFragment.setArguments(bundle);
        ft.replace(R.id.act_exercise_container, summaryFragment, "summaryTag");
        ft.setReorderingAllowed(true);
        ft.commit();
    }

    private void handleSummaryFragment(String duration, long exerciseId, long extensionId) {
        contentDB.insertUserSummaryExercise(new UserExercisePerformedModel(currentUserId,
                getCurrentDate(), exerciseId, extensionId, duration, fromWhere));
        Intent intent = new Intent(this, LibraryActivity.class);
        intent.putExtra(GlobalClass.userID, currentUserId);
        startActivity(intent);
    }
}