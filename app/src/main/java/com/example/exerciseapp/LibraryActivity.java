package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.mClasses.CreateExerciseClass;
import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mInterfaces.INewExercise;
import com.example.exerciseapp.mInterfaces.ISingleIntegerValue;
import com.example.exerciseapp.mInterfaces.ITitleChangeListener;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.ExerciseModel;
import com.example.exerciseapp.mModels.FourElementsModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryActivity extends AppCompatActivity implements UpdateIntegersDB,
        ISingleIntegerValue, INewExercise, ITitleChangeListener {

    private BottomNavigationView bottomNavigationView;
    private TextView activityTitle;

    private static final String activityName = "Workout";

    private long id;
    private long workoutId;
    private final String[] fragmentTitles = new String[]{"Exercises", "Workouts"};
    private List<FourElementsModel> exerciseList = new ArrayList<>();
    private List<FourElementsModel> workoutList = new ArrayList<>();


    private static final String FIRST_LIST = "firstList";
    private static final String SECOND_LIST = "secondList";
    private static final String TAG_VIEW_PAGER = "tagViewPager";
    private static final String TAG_DETAIL = "tagDetail";
    private static final String TAG_WORKOUT_LIST = "tagWorkoutList";
    public static final String LIBRARY_BUTTON_TAG = "LibraryButtonTag";
    public static final String CUSTOM_EXERCISE_CREATOR_TAG = "CustomExerciseCreatorTag";


    private ContentBD contentBD;
    private CustomExerciseCreatorFragment creatorExerciseFragment;
    private CreateExerciseClass createExerciseClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        contentBD = new ContentBD(this);
        initView(savedInstanceState);
        initMenu();

        createExerciseClass = new ViewModelProvider(this).get(CreateExerciseClass.class);

        setDefaultValuesToLists();
        fillLists();
    }

    private void setDefaultValuesToLists() {
        //        default values;
        createExerciseClass.setValue(createExerciseClass.TYPE, 1);
        createExerciseClass.setValue(createExerciseClass.SETS, 1);
        createExerciseClass.setValue(createExerciseClass.VOLUME, 1);
        createExerciseClass.setValue(createExerciseClass.REST, 5);
        createExerciseClass.setValue(createExerciseClass.EXERCISE, 0);
    }

    private void fillLists() {

        List<ExerciseModel> showExercise = contentBD.showExercise();
        List<ExerciseModel> showWorkout = contentBD.showWorkout();

        exerciseList = showExercise.stream()
                .map(exercise -> new FourElementsModel(
                        exercise.getId(), exercise.getImage(), exercise.getName(),
                        String.valueOf(exercise.getType()), R.drawable.ic_hexagon
                )).collect(Collectors.toList());

        workoutList = showWorkout.stream()
                .map(workout -> new FourElementsModel(
                        workout.getId(), workout.getImage(), workout.getName(),
                        String.valueOf(workout.getType()), R.drawable.ic_hexagon
                )).collect(Collectors.toList());
    }

    private void initMenu() {
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_bar_workout);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case (R.id.bottom_nav_bar_main):
                    startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                    overridePendingTransition(R.anim.slide_to_right, R.anim.slide_from_right);
                    finish();
                    return true;
                case (R.id.bottom_nav_bar_workout):
                    return true;
                case (R.id.bottom_nav_bar_profile):
                    startActivity(new Intent(getApplicationContext(), UserActivity.class));
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
                    finish();
                    return true;
                case (R.id.bottom_nav_bar_settings):
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
                    finish();
                    return true;
            }
            return false;
        });
    }

    private void replaceFragment(int container, Fragment fragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        fragment.setArguments(bundle);
        ft.setReorderingAllowed(true);
        ft.addToBackStack(tag);
        ft.replace(container, fragment, tag);
        ft.commit();
    }

    private void initView(Bundle savedInstanceState) {

        activityTitle = findViewById(R.id.act_library_title);
        bottomNavigationView = findViewById(R.id.act_library_bottom_nav_bar);

        activityTitle.setText(activityName);

        if (findViewById(R.id.act_library_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setReorderingAllowed(true);
            ft.addToBackStack(LIBRARY_BUTTON_TAG);
            ft.add(R.id.act_library_container, new LibraryButtonsFragment(), LIBRARY_BUTTON_TAG);
            ft.commit();
        }
    }

    @Override
    public void values(String listName, int firstValue, int secondValue, int thirdValue) {
        id = (long) firstValue;

        switch (listName) {
            case FIRST_LIST:
                Log.e(TAG, "values: first_list");
                replaceFragment(R.id.act_library_container, new DetailsFragment(), TAG_DETAIL);
                break;
            case SECOND_LIST:
                Log.e(TAG, "values: second_list");
                replaceFragment(R.id.act_library_container, new WorkoutList(), TAG_WORKOUT_LIST);
                workoutId = firstValue;
                break;
            case "detailsFragment":
                Log.e(TAG, "values: details_list");
                Intent intent = new Intent(LibraryActivity.this, ExerciseActivity.class);
                if (workoutId != 0) {
                    intent.putExtra("id", (long) workoutId);
                    intent.putExtra("type", (byte) -1);
                } else {
                    intent.putExtra("id", (long) firstValue);
                    intent.putExtra("type", (byte) secondValue);
                }
                startActivity(intent);
                break;
            case "workoutList":
                Log.e(TAG, "values: workoutList ");
                replaceFragment(R.id.act_library_container, new DetailsFragment(), TAG_DETAIL);
                break;
            case "ExerciseModelList":
                Log.e(TAG, "values: ExerciseModelList | firstValue: " + firstValue + " s: " + secondValue + " t: " + thirdValue);
                createExerciseClass.setValue("exercise", firstValue);
                creatorExerciseFragment.fillFields();
                break;
            default:
                throw new NullPointerException(TAG + " This " + listName + "  does not exist");
        }
    }

    @Override
    public void singleIntValue(String name, int value) {
        if (name.equals("LibraryButtonFragment")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            switch (value) {
                case 1:
                    ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
                    bundle.putString("activityName", activityName);
                    bundle.putStringArray("titles", fragmentTitles);
                    bundle.putParcelableArrayList(FIRST_LIST, (ArrayList<? extends Parcelable>) exerciseList);
                    bundle.putParcelableArrayList(SECOND_LIST, (ArrayList<? extends Parcelable>) workoutList);
                    viewPagerFragment.setArguments(bundle);
                    ft.addToBackStack(TAG_VIEW_PAGER);
                    ft.replace(R.id.act_library_container, viewPagerFragment, TAG_VIEW_PAGER);
                    break;
                case 2:
                    break;
                case 3:
                    creatorExerciseFragment = new CustomExerciseCreatorFragment();
                    bundle.putString("activityName", activityName);
                    creatorExerciseFragment.setArguments(bundle);
                    ft.addToBackStack(CUSTOM_EXERCISE_CREATOR_TAG);
                    ft.replace(R.id.act_library_container, creatorExerciseFragment, CUSTOM_EXERCISE_CREATOR_TAG);
                    break;
                default:
                    throw new NullPointerException("Empty button");
            }
            ft.setReorderingAllowed(true);
            ft.commit();
        }
    }

    @Override
    public void createExercise(String name, ExerciseType exerciseType, int sets, int volume, int rest) {

        createExerciseClass.setValue(createExerciseClass.TYPE, exerciseType == ExerciseType.REPETITION ? 1 : 0);
        createExerciseClass.setValue(createExerciseClass.SETS, sets);
        createExerciseClass.setValue(createExerciseClass.VOLUME, volume);
        createExerciseClass.setValue(createExerciseClass.REST, rest);

        creatorExerciseFragment.fillFields();
    }

    @Override
    public void title(String value) {
        activityTitle.setText(value);
    }
}