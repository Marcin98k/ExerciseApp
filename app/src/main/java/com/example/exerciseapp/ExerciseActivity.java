package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;

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

public class ExerciseActivity extends AppCompatActivity
        implements FragmentSupportListener, UpdateIntegersDB, ISummary {

    //    Initializing widgets;
    private FrameLayout container;
    private Button nextBtn;

    //    Initializing variables;
    private byte typeExercise = 0;
    private long id = 0;
    private byte scenario;
    private byte currentFragment;
    private int rest;
    private int currentExercise;
    private long exerciseId;

    private long idMain;
    private byte genusSummary;
    private List<WorkoutModel> workoutPattern;

    //    Initializing constant;
    private final String REPETITION_EXE_TAG = "tagRepetitionExerciseFragment";
    private final String TIME_EXE_TAG = "tagTimeExerciseFragment";
    private final String TIME_BREAK_TAG = "tagBreakExerciseFragment";
    private final String EMPTY_TAG = "tagEmptyFragment";

    //    Initializing instances;
    private ContentBD contentBD;
    private TimeExerciseFragment timeExerciseFragment;
    private RepetitionExerciseFragment repetitionExerciseFragment;
    private TimeBreakFragment timeBreakFragment;
    private EmptyFragment emptyFragment;
    private SummaryFragment summaryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        intView(savedInstanceState);

        nextBtn.setOnClickListener(v -> {
            if (repetitionExerciseFragment != null && repetitionExerciseFragment.isVisible()) {
                repetitionExerciseFragment.fragmentMessage();
            } else if (timeExerciseFragment != null && timeExerciseFragment.isVisible()) {
                timeExerciseFragment.fragmentMessage();
            } else if (emptyFragment != null && emptyFragment.isVisible()) {
                emptyFragment.fragmentMessage();
            } else if(summaryFragment != null && summaryFragment.isVisible()) {
                summaryFragment.fragmentMessage();
            }else {
                Log.i(TAG, "onCreate: " + emptyFragment);
                throw new NullPointerException("(ExerciseActivity)nextBtn -> not work");
            }
        });
    }

    private void intView(Bundle savedInstanceState) {

        container = findViewById(R.id.act_exercise_container);
        nextBtn = findViewById(R.id.act_exercise_button);

        emptyFragment = new EmptyFragment();
        summaryFragment = new SummaryFragment();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getLong("id");
            typeExercise = bundle.getByte("type");
            idMain = id;
        }

        if (findViewById(R.id.act_exercise_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            if (typeExercise != -1) {
                addFragment(id, typeExercise);
                genusSummary = 1;
                return;
            }
            genusSummary = 2;
            workoutPattern = new ArrayList<>();
            contentBD = new ContentBD(this);
            String temp = contentBD.showWorkoutById(id).get(0).getExerciseId();
            String[] exerciseStr = temp.split(",");
            long[] exercisesId = new long[exerciseStr.length];
            for (int i = 0; i < exercisesId.length; i++) {
                exercisesId[i] = Long.parseLong(exerciseStr[i]);
            }
            for (int i = 0; i < exercisesId.length; i++) {
                WorkoutModel model = new WorkoutModel(i, exercisesId[i],
                        (byte) contentBD.showExerciseById(exercisesId[i]).get(0).getType());
                workoutPattern.add(model);
            }

//                Ending fragment;
            WorkoutModel lastExercise = new WorkoutModel(exercisesId.length, -1, (byte) 0);
            workoutPattern.add(lastExercise);

            currentExercise = 0;
            typeExercise = workoutPattern.get(currentExercise).getType();
            addFragment(workoutPattern.get(0).getExerciseId(), typeExercise);

        }
    }

    /**
     * @param id   is responsible for displaying the correct content;
     * @param type is responsible for displaying the correct fragment typeExercise;
     *             <p>
     *             The method responsible for adding a fragment to the activity after id
     *             and show appropriate fragment typeExercise;
     */
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
            ft.add(R.id.act_exercise_container, emptyFragment, EMPTY_TAG);
        }
        ft.setReorderingAllowed(true);
        ft.commit();
    }


    /**
     * @param param Each exercise is divided into sets, if user completes them all,
     *              then the exercise changes;
     */
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
            exerciseId = workoutPattern.get(workoutPattern.get(currentExercise).getId()).getExerciseId();
            if (exerciseId == -1) {
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

        timeBreakFragment = new TimeBreakFragment();
        switch (currentFragment) {
            case 1:
                ft.detach(repetitionExerciseFragment);
                timeBreakFragment.setArguments(sendRestValue);
                ft.replace(R.id.act_exercise_container, timeBreakFragment, TIME_BREAK_TAG);
                break;
            case 2:
                ft.detach(timeExerciseFragment);
                timeBreakFragment.setArguments(sendRestValue);
                ft.replace(R.id.act_exercise_container, timeBreakFragment, TIME_BREAK_TAG);
                break;
            case 3:
                ft.remove(timeBreakFragment);
                if (scenario == 1) {
                    ft.attach(repetitionExerciseFragment);
                    ft.replace(R.id.act_exercise_container, repetitionExerciseFragment, REPETITION_EXE_TAG);
                } else if (scenario == 2) {
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