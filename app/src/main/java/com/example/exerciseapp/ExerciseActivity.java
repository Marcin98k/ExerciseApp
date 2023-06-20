package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity
        implements FragmentSupportListener, UpdateIntegersDB {

    //    Initializing widgets;
    private FrameLayout container;
    private Button nextBtn;

    //    Initializing variables;
    private byte type = 0;
    private long id = 0;
    private byte scenario;
    private byte currentFragment;
    private int rest;
    private int currentExercise;

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
    EmptyFragment emptyFragment;

    //    Initializing objects
    private List<WorkoutModel> workoutPattern;

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
            } else {
                throw new NullPointerException(" not work - fix it");
            }
        });
    }

    private void intView(Bundle savedInstanceState) {

//        Assign widgets;
        container = findViewById(R.id.act_exercise_container);
        nextBtn = findViewById(R.id.act_exercise_button);


//        Get variables from LibraryActivity;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getLong("id");
            type = bundle.getByte("type");
        }

        if (findViewById(R.id.act_exercise_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            if (type == -1) {
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
                WorkoutModel lastExe = new WorkoutModel(exercisesId.length + 1, (long) -1, (byte) 0);
                workoutPattern.add(lastExe);

                currentExercise = 0;
                type = workoutPattern.get(currentExercise).getType();
                addFragment(workoutPattern.get(0).getExerciseId(), type);
            } else {
                addFragment(id, type);
//                addFragment(id);
            }
        }
    }

    /**
     *
     * @param id is responsible for displaying the correct content;
     * @param type is responsible for displaying the correct fragment type;
     *
     * The method responsible for adding a fragment to the activity after id
     *             and show appropriate fragment type;
     */
    private void addFragment(long id, int type) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (id == -1) {
            ft.replace(R.id.act_exercise_container, new EmptyFragment(), EMPTY_TAG);
        } else {
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
                ft.add(R.id.act_exercise_container, new EmptyFragment(), EMPTY_TAG);
            }
        }
        ft.setReorderingAllowed(true);
        ft.commit();
    }


    /**
     *
     * @param param
     *
     * Each exercise is divided into sets, if user completes them all,
     * then the exercise changes;
     */
    @Override
    public void checkCondition(boolean param) {
        if (param) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment removeRepetitionFragment = fm.findFragmentByTag(REPETITION_EXE_TAG);
            Fragment removeTimeFragment = fm.findFragmentByTag(TIME_EXE_TAG);
            Fragment removeBreakFragment = fm.findFragmentByTag(TIME_BREAK_TAG);
            Fragment removeEmptyFragment = fm.findFragmentByTag(EMPTY_TAG);
            if (removeRepetitionFragment != null) {
                fm.beginTransaction().remove(removeRepetitionFragment).commitAllowingStateLoss();
            }
            if (removeTimeFragment != null) {
                fm.beginTransaction().remove(removeTimeFragment).commitAllowingStateLoss();
            }
            if (removeBreakFragment != null) {
                fm.beginTransaction().remove(removeBreakFragment).commitAllowingStateLoss();
            }
            if (removeEmptyFragment != null) {
                fm.beginTransaction().remove(removeEmptyFragment).commitAllowingStateLoss();
            }

            Log.i(TAG, "checkCondition: " +
                    "\n1" + repetitionExerciseFragment +
                    "\n2" + timeExerciseFragment +
                    "\n3" + timeBreakFragment +
                    "\n4" + emptyFragment
            );

            currentExercise++;
            type = workoutPattern.get(workoutPattern.get(currentExercise).getId()).getType();
            addFragment(workoutPattern.get(
                    workoutPattern.get(currentExercise).getId()).getExerciseId(), type);
        }
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
}