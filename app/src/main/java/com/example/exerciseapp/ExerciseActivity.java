package com.example.exerciseapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.exerciseapp.mClasses.GlobalClass;
import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mInterfaces.FragmentSupportListener;
import com.example.exerciseapp.mInterfaces.ISummary;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.UserExercisePerformedModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity implements
        FragmentSupportListener, UpdateIntegersDB, ISummary {

    private Button nextBtn;


    private long currentUserID;
    private byte typeExercise = 0;
    private long id = 0;
    private String exerciseName = "";
    private int fromWhere;
    private byte scenario;
    private byte currentFragment;
    private int rest;
    private long extensionId;
    private int currentExercise;
    private long idMain;
    private double duration;
    private String name;
    private List<WorkoutModel> workoutPattern;
    private long startTime;
    private boolean lastSet;


    private static final String TAG = "ExerciseActivity";
    private static final String REPETITION_EXE_TAG = "tagRepetitionExerciseFragment";
    private static final String TIME_EXE_TAG = "tagTimeExerciseFragment";
    private static final String TIME_BREAK_TAG = "tagBreakExerciseFragment";
    private static final String EMPTY_TAG = "tagEmptyFragment";
    private static final int MINUS_ONE = -1;
    private static final int REPETITION_EXERCISE_FRAGMENT = 1;
    private static final int TIME_EXERCISE_FRAGMENT = 2;


    private ContentBD contentBD;
    private TimeExerciseFragment timeExerciseFragment;
    private RepetitionExerciseFragment repetitionExerciseFragment;
    private EmptyFragment emptyFragment;
    private SummaryFragment summaryFragment;
    private TimeBreakFragment timeBreakFragment = new TimeBreakFragment();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(GlobalClass.initLanguage(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        intView(savedInstanceState);
        startTime = SystemClock.elapsedRealtime();

        nextBtn.setOnClickListener(v -> {
            if (repetitionExerciseFragment != null && repetitionExerciseFragment.isVisible()) {
                repetitionExerciseFragment.fragmentMessage();
            } else if (timeExerciseFragment != null && timeExerciseFragment.isVisible()) {
                timeExerciseFragment.fragmentMessage();
            } else if (emptyFragment != null && emptyFragment.isVisible()) {
                emptyFragment.fragmentMessage();
            } else if (summaryFragment != null && summaryFragment.isVisible()) {
                summaryFragment.fragmentMessage();
            } else {
                throw new NullPointerException(TAG + " (nextBtn) -> not work");
            }
        });
    }

    private void intView(Bundle savedInstanceState) {

        nextBtn = findViewById(R.id.act_exercise_button);

        Intent intent = getIntent();
        currentUserID = intent.getLongExtra(GlobalClass.userID, -1);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getLong("id");
            typeExercise = bundle.getByte("type");
            fromWhere = bundle.getInt("fromWhere");
            idMain = id;
        }
        workoutPattern = new ArrayList<>();
        contentBD = new ContentBD(this);
        if (findViewById(R.id.act_exercise_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            int sumExercise = 0;
            currentExercise = 0;
            if (typeExercise > 0) {
                if (fromWhere == 0) {
                    name = contentBD.showExerciseById(id).get(0).getName();
                    WorkoutModel singleExercise = new WorkoutModel(0, id,
                            contentBD.showExerciseById(id).get(0).getExtension(), typeExercise,
                            contentBD.showExerciseById(id).get(0).getName(), fromWhere);
                    workoutPattern.add(singleExercise);
                } else {
                    name = contentBD.showUserExerciseById(id).get(0).getName();
                    WorkoutModel singleExercise = new WorkoutModel(0, id,
                            contentBD.showUserExerciseById(id).get(0).getExtension(), typeExercise,
                            contentBD.showUserExerciseById(id).get(0).getName(), fromWhere);
                    workoutPattern.add(singleExercise);
                }
                sumExercise++;
            } else {
                name = contentBD.showWorkoutById(id).get(0).getName();
                String exerciseIds = contentBD.showExercisesFromWorkout(id);
                String[] exerciseList = exerciseIds.split(",");
                long[] exercisesId = new long[exerciseList.length];
                int exercisesIdLength = exercisesId.length;
                for (int i = 0; i < exercisesIdLength; i++) {
                    exercisesId[i] = Long.parseLong(exerciseList[i]);
                    sumExercise++;
                }
                for (int i = 0; i < exercisesIdLength; i++) {
                    WorkoutModel model = new WorkoutModel(i, exercisesId[i],
                            contentBD.showExerciseById(exercisesId[i]).get(0).getExtension(),
                            (byte) contentBD.showExerciseByIdFromWorkout(exercisesId[i]).get(0).getType(),
                            contentBD.showExerciseById(exercisesId[i]).get(0).getName(),
                            contentBD.showExerciseById(exercisesId[i]).get(0).getFromWhere());
                    workoutPattern.add(model);
                }
            }
            extensionId = workoutPattern.get(currentExercise).getExtensionId();
            typeExercise = workoutPattern.get(currentExercise).getType();
            exerciseName = workoutPattern.get(0).getName();
            addFragment(workoutPattern.get(0).getExerciseId(), typeExercise);

            WorkoutModel lastExercise = new WorkoutModel(sumExercise, MINUS_ONE, (byte) 0);
            workoutPattern.add(lastExercise);
        }
    }

    private void elapsed() {
        long endTime = SystemClock.elapsedRealtime() - startTime;
        duration = endTime / 1000.0;
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        return dateFormat.format(new Date());
    }

    private void addFragment(long id, int type) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle sendToFragment = new Bundle();
        sendToFragment.putLong("id", id);
        sendToFragment.putString("exerciseName", exerciseName);
        sendToFragment.putInt("fromWhere", fromWhere);
        if (type == 1) {
            repetitionExerciseFragment = new RepetitionExerciseFragment();
            repetitionExerciseFragment.setArguments(sendToFragment);
            ft.add(R.id.act_exercise_container, repetitionExerciseFragment, REPETITION_EXE_TAG);
        } else if (type == 2) {
            timeExerciseFragment = new TimeExerciseFragment();
            timeExerciseFragment.setArguments(sendToFragment);
            ft.add(R.id.act_exercise_container, timeExerciseFragment, TIME_EXE_TAG);
        } else {
            emptyFragment = new EmptyFragment();
            ft.add(R.id.act_exercise_container, emptyFragment, EMPTY_TAG);
        }
        ft.setReorderingAllowed(true);
        ft.commit();
    }

    private void swapFragments() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (lastSet && workoutPattern.get(currentExercise + 1).getExerciseId() == -1) {
            summaryFragment = new SummaryFragment();
            Bundle bundle = new Bundle();
            elapsed();
            Log.i(TAG, "swapFragments(id): " + MINUS_ONE);
            bundle.putLong("id", idMain);
            bundle.putDouble("duration", duration);
            bundle.putString("name", name);
            bundle.putLong("extensionId", extensionId);
            bundle.putInt("fromWhere", fromWhere);
            FragmentTransaction summaryTransaction = getSupportFragmentManager().beginTransaction();
            summaryFragment.setArguments(bundle);
            summaryTransaction.replace(R.id.act_exercise_container, summaryFragment, "summaryTag");
            summaryTransaction.setReorderingAllowed(true);
            summaryTransaction.commit();
            return;
        }
        if (lastSet) {
            exerciseName = workoutPattern.get(currentExercise + 1).getName();
        } else {
            exerciseName = workoutPattern.get(currentExercise).getName();
        }
        Bundle sendRestValue = new Bundle();
        sendRestValue.putInt("rest", rest);
        sendRestValue.putString("exerciseName", exerciseName);
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
        if (param) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment removeRepetitionFragment = fm.findFragmentByTag(REPETITION_EXE_TAG);
            Fragment removeTimeFragment = fm.findFragmentByTag(TIME_EXE_TAG);
            Fragment removeBreakFragment = fm.findFragmentByTag(TIME_BREAK_TAG);
            if (removeRepetitionFragment != null) {
                fm.beginTransaction().remove(removeRepetitionFragment).commitAllowingStateLoss();
            }
            if (removeTimeFragment != null) {
                fm.beginTransaction().remove(removeTimeFragment).commitAllowingStateLoss();
            }
            if (removeBreakFragment != null) {
                fm.beginTransaction().remove(removeBreakFragment).commitAllowingStateLoss();
            }

            if (workoutPattern == null) {
                summaryFragment = new SummaryFragment();
                Bundle bundle = new Bundle();
                elapsed();
                bundle.putLong("id", idMain);
                bundle.putDouble("duration", duration);
                bundle.putString("name", name);
                bundle.putInt("fromWhere", fromWhere);
                bundle.putLong("extensionId", MINUS_ONE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                summaryFragment.setArguments(bundle);
                ft.replace(R.id.act_exercise_container, summaryFragment, "summaryTag");
                ft.setReorderingAllowed(true);
                ft.commit();
                return;
            }
            currentExercise++;
            typeExercise = workoutPattern.get(workoutPattern.get(currentExercise).getId()).getType();
            if (workoutPattern.get(workoutPattern.get(currentExercise).getId()).getExerciseId() == MINUS_ONE) {
                summaryFragment = new SummaryFragment();
                Bundle bundle = new Bundle();
                elapsed();
                bundle.putLong("id", idMain);
                bundle.putDouble("duration", duration);
                bundle.putString("name", name);
                bundle.putLong("extensionId", extensionId);
                bundle.putInt("fromWhere", fromWhere);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                summaryFragment.setArguments(bundle);
                ft.replace(R.id.act_exercise_container, summaryFragment, "summaryTag");
                ft.setReorderingAllowed(true);
                ft.commit();
                return;
            }
            addFragment(workoutPattern.get(
                    workoutPattern.get(currentExercise).getId()).getExerciseId(), typeExercise);
        }
    }

    @Override
    public void values(String listName, int firstValue, int secondValue, int thirdValue, int fourthValue) {

        Log.i(TAG, "values: " + listName + " " + firstValue + " " + secondValue +
                " " + thirdValue + " " + fourthValue);
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
                               long extensionId, int fromWhere, boolean conditionVal) {
        if (name.equals("EmptyFragment") && conditionVal) {
            summaryFragment = new SummaryFragment();
            Bundle bundle = new Bundle();
            elapsed();
            bundle.putLong("id", idMain);
            bundle.putDouble("duration", this.duration);
            bundle.putString("name", exerciseName);
            bundle.putLong("extensionId", extensionId);
            bundle.putInt("fromWhere", this.fromWhere);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            summaryFragment.setArguments(bundle);
            ft.replace(R.id.act_exercise_container, summaryFragment, "summaryTag");
            ft.setReorderingAllowed(true);
            ft.commit();
        } else if (name.equals("summaryFragment") && conditionVal) {
            contentBD.insertUserSummaryExercise(new UserExercisePerformedModel(currentUserID,
                    getCurrentDate(), exerciseId, extensionId, duration, fromWhere));
            Intent intent = new Intent(this, LibraryActivity.class);
            intent.putExtra(GlobalClass.userID, currentUserID);
            startActivity(intent);
        }
    }
}