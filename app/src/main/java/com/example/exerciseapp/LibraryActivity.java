package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.Exercise.CustomExerciseCreatorFragment;
import com.example.exerciseapp.Exercise.DetailsFragment;
import com.example.exerciseapp.Exercise.WorkoutList;
import com.example.exerciseapp.mClasses.BackgroundTask;
import com.example.exerciseapp.mClasses.CreateExerciseClass;
import com.example.exerciseapp.mClasses.GlobalClass;
import com.example.exerciseapp.mDatabases.ContentDB;
import com.example.exerciseapp.mEnums.ExerciseType;
import com.example.exerciseapp.mEnums.Side;
import com.example.exerciseapp.mInterfaces.CallbackList;
import com.example.exerciseapp.mInterfaces.ISingleIntegerValue;
import com.example.exerciseapp.mInterfaces.NewExercise;
import com.example.exerciseapp.mInterfaces.NewExerciseToChange;
import com.example.exerciseapp.mInterfaces.TitleChangeListener;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.ExerciseDescriptionModel;
import com.example.exerciseapp.mModels.ExtensionExerciseModel;
import com.example.exerciseapp.mModels.FourElementsModel;
import com.example.exerciseapp.mModels.IntegerModel;
import com.example.exerciseapp.mModels.TrainingModel;
import com.example.exerciseapp.mResource.LibraryButtonsFragment;
import com.example.exerciseapp.mResource.LoadingFragment;
import com.example.exerciseapp.mResource.ViewPagerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class LibraryActivity extends AppCompatActivity implements UpdateIntegersDB,
        ISingleIntegerValue, NewExerciseToChange, TitleChangeListener {

    private final String[] fragmentTitles = new String[]{"Exercises", "Workouts"};
    private static final String FIRST_LIST = "firstList";
    private static final String SECOND_LIST = "secondList";
    private static final String THIRD_LIST = "userExerciseList";
    private static final String TAG_VIEW_PAGER = "tagViewPager";
    private static final String TAG_DETAIL = "tagDetail";
    private static final String TAG_WORKOUT_LIST = "tagWorkoutList";
    public static final String LIBRARY_BUTTON_TAG = "LibraryButtonTag";
    public static final String CUSTOM_EXERCISE_CREATOR_TAG = "CustomExerciseCreatorTag";
    private static final String DETAILS_FRAGMENT_LIST = "detailsFragmentList";
    private static final String WORKOUT_DETAILS = "workoutDetails";

    private BottomNavigationView bottomNavigationView;
    private TextView fragmentTitle;


    private long id;
    private int fromWhere;
    private long workoutId;
    private long extensionId;
    private static long currentUserID;
    private List<Integer> workoutTimeList = new ArrayList<>();
    private List<Integer> exerciseTimeList = new ArrayList<>();
    private List<FourElementsModel> exerciseList;
    private List<FourElementsModel> workoutList = new ArrayList<>();

    private final FragmentManager fragmentManager = getSupportFragmentManager();

    private ContentDB contentDB;
    private CustomExerciseCreatorFragment creatorExerciseFragment;
    private CreateExerciseClass createExerciseClass;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(GlobalClass.initLanguage(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        initView(savedInstanceState);
        initMenu();

        setDefaultValuesForTheList();
    }

    private void initView(Bundle savedInstanceState) {
        initializeViews();

        contentDB = new ContentDB(this);
        createExerciseClass = new ViewModelProvider(this).get(CreateExerciseClass.class);

        Intent intent = getIntent();
        currentUserID = intent.getLongExtra(GlobalClass.userID, -1);

        if (findViewById(R.id.act_library_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            addFirstFragmentToActivity();
        }
    }

    private void initializeViews() {
        fragmentTitle = findViewById(R.id.act_library_title_part_two);
        bottomNavigationView = findViewById(R.id.act_library_bottom_nav_bar);

        String activityName = getString(R.string.workout);
        TextView activityTitle = findViewById(R.id.act_library_title_part_one);
        activityTitle.setText(activityName);
    }

    private void addFirstFragmentToActivity() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setReorderingAllowed(true);
        ft.addToBackStack(LIBRARY_BUTTON_TAG);
        ft.add(R.id.act_library_container, new LibraryButtonsFragment(), LIBRARY_BUTTON_TAG);
        ft.commit();
    }

    private void initMenu() {
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_bar_workout);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case (R.id.bottom_nav_bar_main):
                    startNewActivity(MainActivity.class, Side.LEFT);
                    return true;
                case (R.id.bottom_nav_bar_workout):
                    return true;
                case (R.id.bottom_nav_bar_profile):
                    startNewActivity(UserActivity.class, Side.RIGHT);
                    return true;
                case (R.id.bottom_nav_bar_settings):
                    startNewActivity(SettingsActivity.class, Side.RIGHT);
                    return true;
            }
            return false;
        });
    }

    private void startNewActivity(Class<?> activity, Side side) {
        Intent intent = new Intent(getApplicationContext(), activity);
        intent.putExtra(GlobalClass.userID, currentUserID);
        if (side == Side.LEFT) {
            overridePendingTransition(R.anim.slide_to_right, R.anim.slide_from_right);
        } else {
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
        }
        startActivity(intent);
        finish();
    }

    private void fillLists(CallbackList callbackList) {

        CountDownLatch latch = new CountDownLatch(3);

        fragmentManager.beginTransaction()
                .replace(R.id.act_library_container, new LoadingFragment())
                .commit();

        List<FourElementsModel> mainExerciseList = new ArrayList<>();
        List<FourElementsModel> userExerciseList = new ArrayList<>();

        BackgroundTask.executeWithLoading(
                () -> {
                    fillMainExerciseList(mainExerciseList);
                    latch.countDown();
                },
                () -> {
                },
                () -> {
                }
        );
        BackgroundTask.executeWithLoading(
                () -> {
                    fillWorkoutList();
                    latch.countDown();
                },
                () -> {
                },
                () -> {
                }
        );

        BackgroundTask.executeWithLoading(
                () -> {
                    fillUserExerciseList(userExerciseList);
                    latch.countDown();
                },
                () -> {
                },
                () -> {
                }
        );
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//                        Last element in RecyclerView;
        userExerciseList.add(new FourElementsModel(0, "", "",
                "", 0, 0, 1));
        fragmentManager.popBackStack();
        exerciseList = new ArrayList<>(mainExerciseList);
        exerciseList.addAll(userExerciseList);
        callbackList.onListsLoaded();
    }


    private void fillMainExerciseList(List<FourElementsModel> mainExerciseList) {
        int[] tempTimeExercise = new int[1];
        List<ExerciseDescriptionModel> showExercise = contentDB.showExercise();

        for (int i = 0; i < showExercise.size(); i++) {
            ExerciseDescriptionModel exerciseDescriptionModel = showExercise.get(i);
            List<IntegerModel> extensionExe = contentDB.showExerciseExtensionId(exerciseDescriptionModel.getExtension());

            tempTimeExercise[0] = calculateTime(exerciseDescriptionModel, extensionExe);
            exerciseTimeList.add(tempTimeExercise[0]);
            tempTimeExercise[0] = 0;

            mainExerciseList.add(createFourElementsModel(exerciseDescriptionModel, exerciseTimeList.get(i), 0));
        }
    }

    private void fillWorkoutList() {
        List<ExerciseDescriptionModel> showWorkout = contentDB.showWorkout();
        workoutTimeList = new ArrayList<>();
        int[] tempTimeWorkout = new int[1];

        int index = 0;
        for (ExerciseDescriptionModel exerciseDescriptionModel : showWorkout) {
            long[] exercisesId = parseExerciseIds(exerciseDescriptionModel.getExerciseId());

            for (long l : exercisesId) {
                List<ExerciseDescriptionModel> tempExercises = contentDB.showExerciseById(l);
                List<IntegerModel> tempExtensions = contentDB.showExerciseExtensionId(tempExercises.get(0).getExtension());
                tempTimeWorkout[0] += calculateTime(tempExercises.get(0), tempExtensions);
            }

            workoutTimeList.add(tempTimeWorkout[0]);
            tempTimeWorkout[0] = 0;

            workoutList.add(createFourElementsModel(exerciseDescriptionModel, workoutTimeList.get(index), 0));
            index++;
        }

        // Last element in RecyclerView
        workoutList.add(new FourElementsModel(0, "", "", "", 0, 0, 0));
    }

    private long[] parseExerciseIds(String exerciseIdsStr) {
        String[] exerciseStr = exerciseIdsStr.split(",");
        long[] exercisesId = new long[exerciseStr.length];
        for (int j = 0; j < exercisesId.length; j++) {
            exercisesId[j] = Long.parseLong(exerciseStr[j]);
        }
        return exercisesId;
    }


    private void fillUserExerciseList(List<FourElementsModel> userExerciseList) {
        if (contentDB.getCount("CUSTOM_USER_EXERCISE_TAB") >= 1) {
            int[] tempUserTimeExercise = new int[1];
            List<ExerciseDescriptionModel> showUserExercise = contentDB.showUserExercise();

            for (int i = 0; i < showUserExercise.size(); i++) {
                ExerciseDescriptionModel exerciseDescriptionModel = showUserExercise.get(i);

                if (exerciseDescriptionModel.getExtension() > 0) {
                    List<IntegerModel> extensionExe = contentDB.showExerciseExtensionId(exerciseDescriptionModel.getExtension());
                    tempUserTimeExercise[0] = calculateTime(exerciseDescriptionModel, extensionExe);
                    exerciseTimeList.add(tempUserTimeExercise[0]);
                    tempUserTimeExercise[0] = 0;
                }

                userExerciseList.add(createFourElementsModel(exerciseDescriptionModel, exerciseTimeList.get(i), 1));
            }
        }
    }

    private int calculateTime(ExerciseDescriptionModel exerciseDescriptionModel, List<IntegerModel> extensionExe) {

        IntegerModel firstExtension = extensionExe.get(0);
        int secondValue = firstExtension.getSecondValue();
        int fifthValue = firstExtension.getFifthValue();
        int time;

        if (exerciseDescriptionModel.getType() == 1) {
            time = secondValue * GlobalClass.DEFAULT_EXERCISE_TIME;
        } else {
            int forthValue = firstExtension.getForthValue();
            time = secondValue * forthValue;
        }
        return time + ((secondValue - 1) * fifthValue);
    }

    private FourElementsModel createFourElementsModel(ExerciseDescriptionModel exerciseDescriptionModel, int time,
                                                      int lastElement) {
        return new FourElementsModel(exerciseDescriptionModel.getId(), exerciseDescriptionModel.getImage(),
                exerciseDescriptionModel.getName(), String.valueOf(exerciseDescriptionModel.getType()),
                exerciseDescriptionModel.getLevel(), time, lastElement);
    }

    private void setDefaultValuesForTheList() {
        createExerciseClass.setValue(createExerciseClass.TYPE, 1);
        createExerciseClass.setValue(createExerciseClass.SETS, 1);
        createExerciseClass.setValue(createExerciseClass.VOLUME, 1);
        createExerciseClass.setValue(createExerciseClass.REST, 5);
        createExerciseClass.setValue(createExerciseClass.EXERCISE, 0);
    }

    private void replaceFragment(int container, Fragment fragment, String tag, int buttonName,
                                 String listName) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        bundle.putInt("btnName", buttonName);
        bundle.putString("listName", listName);
        bundle.putInt("fromWhere", fromWhere);
        fragment.setArguments(bundle);
        ft.setReorderingAllowed(true);
        ft.addToBackStack(tag);
        ft.replace(container, fragment, tag);
        ft.commit();
    }

    @Override
    public void values(String listName, int firstValue, int secondValue, int thirdValue, int fourthValue) {
        id = (long) firstValue;

        logValues(firstValue, secondValue, thirdValue);
        switch (listName) {
            case THIRD_LIST:
            case FIRST_LIST:
                handleFirstList(secondValue);
                break;
            case SECOND_LIST:
                handleSecondList(firstValue);
                break;
            case DETAILS_FRAGMENT_LIST:
                handleDetailsFragmentList(firstValue, secondValue);
                break;
            case "workoutList":
                handleWorkoutList();
                break;
            case "ExerciseModelList":
                handleExerciseModelList(firstValue);
                break;
            case WORKOUT_DETAILS:
                handleWorkoutDetails();
                break;
            default:
                throw new IllegalArgumentException(TAG + " This " + listName + " does not exist");
        }
    }

    private void logValues(int firstValue, int secondValue, int thirdValue) {
        Log.i(TAG, "values(fromWhere): " + fromWhere);
        Log.i(TAG, "values(val): " + firstValue + " 2: " + secondValue + " " + thirdValue);
    }

    private void handleFirstList(int secondValue) {
        fromWhere = secondValue;
        replaceFragment(R.id.act_library_container, new DetailsFragment(), TAG_DETAIL,
                R.string.start, DETAILS_FRAGMENT_LIST);
    }

    private void handleSecondList(int firstValue) {
        replaceFragment(R.id.act_library_container, new WorkoutList(), TAG_WORKOUT_LIST,
                R.string.workout, DETAILS_FRAGMENT_LIST);
        workoutId = firstValue;
    }

    private void handleDetailsFragmentList(int firstValue, int secondValue) {

        Intent intent = new Intent(LibraryActivity.this, ExerciseActivity.class);
        intent.putExtra(GlobalClass.userID, currentUserID);
        if (workoutId != 0) {
            addWorkoutIntentExtras(intent);
        } else {
            addExerciseIntentExtras(intent, firstValue, secondValue);
        }
        startActivity(intent);

    }

    private void addWorkoutIntentExtras(Intent intent) {
        intent.putExtra("id", (long) workoutId);
        intent.putExtra("type", (byte) -1);
        intent.putExtra("listName", "Workout");
        intent.putExtra("extension", -1);
        intent.putExtra("fromWhere", fromWhere);
    }

    private void addExerciseIntentExtras(Intent intent, int firstValue, int secondValue) {
        intent.putExtra("id", (long) firstValue);
        intent.putExtra("type", (byte) secondValue);
        intent.putExtra("listName", "Exercise");
        intent.putExtra("extension", extensionId);
        intent.putExtra("fromWhere", fromWhere);
    }

    private void handleWorkoutList() {
        replaceFragment(R.id.act_library_container, new DetailsFragment(), null,
                R.string.return_text, WORKOUT_DETAILS);
    }

    private void handleExerciseModelList(int firstValue) {
        createExerciseClass.setValue("exercise", firstValue);
        creatorExerciseFragment.fillFields();
    }

    private void handleWorkoutDetails() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void singleIntValue(String name, int value) {

        if (name.equals("LibraryButtonFragment")) {
            handleLibraryButtonFragment(value);
        } else if (name.equals("startWorkout")) {
            startWorkout();
        }
    }

    private void handleLibraryButtonFragment(int value) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        switch (value) {
            case 1:
                fillLists(() -> setupViewPagerFragment(ft, bundle));
                break;
            case 2:
//                Empty
                break;
            case 3:
                setupCustomExerciseCreatorFragment(ft, bundle);
                break;
            default:
                throw new IllegalArgumentException("Button does no exist");
        }
        ft.setReorderingAllowed(true);
        ft.commit();

    }

    private void setupViewPagerFragment(FragmentTransaction ft, Bundle bundle) {

        ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
        bundle.putStringArray("titles", fragmentTitles);
        bundle.putParcelableArrayList(FIRST_LIST,
                (ArrayList<? extends Parcelable>) exerciseList);
        bundle.putParcelableArrayList(SECOND_LIST,
                (ArrayList<? extends Parcelable>) workoutList);
        viewPagerFragment.setArguments(bundle);
        ft.addToBackStack(TAG_VIEW_PAGER);
        ft.replace(R.id.act_library_container, viewPagerFragment, TAG_VIEW_PAGER);
    }

    private void setupCustomExerciseCreatorFragment(FragmentTransaction ft, Bundle bundle) {

        creatorExerciseFragment = new CustomExerciseCreatorFragment();
        bundle.putLong("userId", currentUserID);
        creatorExerciseFragment.setArguments(bundle);
        ft.addToBackStack(CUSTOM_EXERCISE_CREATOR_TAG);
        ft.replace(R.id.act_library_container, creatorExerciseFragment, CUSTOM_EXERCISE_CREATOR_TAG);
    }

    private void startWorkout() {

        Intent intent = new Intent(LibraryActivity.this, ExerciseActivity.class);
        intent.putExtra(GlobalClass.userID, currentUserID);
        intent.putExtra("id", (long) workoutId);
        intent.putExtra("type", (byte) -1);
        intent.putExtra("fromWhere", fromWhere);
        intent.putExtra("listName", "Workout");
        startActivity(intent);
    }

    @Override
    public void createExercise(String name, ExerciseType exerciseType, int sets, int volume,
                               int rest) {
        Log.i(TAG, "createExercise: library create");
        createExerciseClass.setValue(createExerciseClass.TYPE,
                exerciseType == ExerciseType.REPETITION ? 1 : 0);
        createExerciseClass.setValue(createExerciseClass.SETS, sets);
        createExerciseClass.setValue(createExerciseClass.VOLUME, volume);
        createExerciseClass.setValue(createExerciseClass.REST, rest);

        creatorExerciseFragment.fillFields();
    }

    @Override
    public void title(String value) {
        fragmentTitle.setText(value);
    }
}