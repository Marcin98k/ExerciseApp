package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mInterfaces.FragmentSupportListener;
import com.example.exerciseapp.mInterfaces.ISummary;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity implements
        FragmentSupportListener, UpdateIntegersDB, ISummary {

    private Button nextBtn;


    private byte typeExercise = 0;
    private long id = 0;
    private byte scenario;
    private byte currentFragment;
    private int rest;
    private int currentExercise;
    private long idMain;
    private byte genusSummary;
    private List<WorkoutModel> workoutPattern;
    private long startTime;


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
                elapsed();
            } else {
                throw new NullPointerException("(ExerciseActivity)nextBtn -> not work");
            }
        });
    }

    private void intView(Bundle savedInstanceState) {

        nextBtn = findViewById(R.id.act_exercise_button);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getLong("id");
            typeExercise = bundle.getByte("type");
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
                WorkoutModel singleExercise = new WorkoutModel(0, id, typeExercise);
                workoutPattern.add(singleExercise);
                sumExercise++;
                genusSummary = 1;
            } else {
                genusSummary = 2;
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
                            (byte) contentBD.showExerciseByIdFromWorkout(exercisesId[i]).get(0).getType());
                    workoutPattern.add(model);
                }
            }
            typeExercise = workoutPattern.get(currentExercise).getType();
            addFragment(workoutPattern.get(0).getExerciseId(), typeExercise);

            WorkoutModel lastExercise = new WorkoutModel(sumExercise, MINUS_ONE, (byte) 0);
            workoutPattern.add(lastExercise);
        }
    }

    private void elapsed() {
        long endTime = SystemClock.elapsedRealtime() - startTime;
        Log.i(TAG, "elapsed: " + endTime);
    }

    private void addFragment(long id, int type) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle sendToFragment = new Bundle();
        sendToFragment.putLong("id", id);
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
                bundle.putLong("id", idMain);
                bundle.putByte("genusSummary", genusSummary);
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
                bundle.putLong("id", idMain);
                bundle.putByte("genusSummary", genusSummary);
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

    private void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.act_exercise_container, fragment, tag);
        ft.setReorderingAllowed(true);
        ft.commit();
    }

    private void swapFragments() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle sendRestValue = new Bundle();
        sendRestValue.putInt("rest", rest);

        switch (currentFragment) {
            case 1:
            case 2:
                ft.detach(currentFragment == 1 ? repetitionExerciseFragment : timeExerciseFragment);
                timeBreakFragment.setArguments(sendRestValue);
                ft.replace(R.id.act_exercise_container, timeBreakFragment, TIME_BREAK_TAG);
                break;
            case 3:
                ft.remove(timeBreakFragment);
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
    public void values(String listName, int firstValue, int secondValue, int thirdValue) {
        rest = firstValue;
        currentFragment = (byte) secondValue;
        if (thirdValue > 0) {
            scenario = (byte) thirdValue;
        }
        swapFragments();
    }

    @Override
    public void summaryMessage(String name, String strVal, int numVal, boolean conditionVal) {
        Log.i(TAG, "summaryMessage: ");
        if (name.equals("EmptyFragment") && conditionVal) {
            summaryFragment = new SummaryFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("id", idMain);
            bundle.putByte("genusSummary", genusSummary);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            summaryFragment.setArguments(bundle);
            ft.replace(R.id.act_exercise_container, summaryFragment, "summaryTag");
            ft.setReorderingAllowed(true);
            ft.commit();
        } else if (name.equals("summaryFragment") && conditionVal) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}