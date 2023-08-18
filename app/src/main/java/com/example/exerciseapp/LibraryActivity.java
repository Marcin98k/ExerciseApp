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

import com.example.exerciseapp.mClasses.BackgroundTask;
import com.example.exerciseapp.mClasses.CreateExerciseClass;
import com.example.exerciseapp.mClasses.GlobalClass;
import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mEnums.Side;
import com.example.exerciseapp.mInterfaces.CallbackList;
import com.example.exerciseapp.mInterfaces.INewExercise;
import com.example.exerciseapp.mInterfaces.ISingleIntegerValue;
import com.example.exerciseapp.mInterfaces.ITitleChangeListener;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.ExerciseModel;
import com.example.exerciseapp.mModels.FourElementsModel;
import com.example.exerciseapp.mModels.IntegerModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class LibraryActivity extends AppCompatActivity implements UpdateIntegersDB,
        ISingleIntegerValue, INewExercise, ITitleChangeListener {

    private BottomNavigationView bottomNavigationView;
    private TextView fragmentTitle;


    private long id;
    private int fromWhere;
    private long workoutId;
    private long extensionId;
    private static long currentUserID;
    int durationSum = 0;
    private List<Integer> workoutTimeList;
    private List<Integer> exerciseTimeList;
    private final String[] fragmentTitles = new String[]{"Exercises", "Workouts"};
    private List<FourElementsModel> exerciseList;
    private List<FourElementsModel> workoutList = new ArrayList<>();


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


    private final FragmentManager fragmentManager = getSupportFragmentManager();


    private ContentBD contentBD;
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

        TextView activityTitle = findViewById(R.id.act_library_title_part_one);
        fragmentTitle = findViewById(R.id.act_library_title_part_two);
        bottomNavigationView = findViewById(R.id.act_library_bottom_nav_bar);

        contentBD = new ContentBD(this);


        String activityName = getString(R.string.workout);
        activityTitle.setText(activityName);

        Intent intent = getIntent();
        currentUserID = intent.getLongExtra(GlobalClass.userID, -1);


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

        createExerciseClass = new ViewModelProvider(this).get(CreateExerciseClass.class);
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
        int[] tempTimeExercise = new int[1];
        List<FourElementsModel> userExerciseList = new ArrayList<>();
        int[] tempUserTimeExercise = new int[1];

        BackgroundTask.executeWithLoading(
                () -> {
                    exerciseTimeList = new ArrayList<>();
                    List<ExerciseModel> showExercise = contentBD.showExercise();
                    for (int i = 0; i < showExercise.size(); i++) {
                        List<IntegerModel> extensionExe = contentBD.showExerciseExtensionId(
                                showExercise.get(i).getExtension());

                        if (showExercise.get(0).getType() == 1) {
                            tempTimeExercise[0] += (extensionExe.get(0).getSecondValue() * GlobalClass.DEFAULT_EXERCISE_TIME) +
                                    ((extensionExe.get(0).getSecondValue() - 1) * extensionExe.get(0).getFifthValue());
                        } else {
                            tempTimeExercise[0] += (extensionExe.get(0).getSecondValue() * extensionExe.get(0).getForthValue()) +
                                    ((extensionExe.get(0).getSecondValue() - 1) * extensionExe.get(0).getFifthValue());
                        }
                        exerciseTimeList.add(tempTimeExercise[0]);
                        tempTimeExercise[0] = 0;

                        ExerciseModel exerciseModel = showExercise.get(i);
                        mainExerciseList.add(new FourElementsModel(exerciseModel.getId(),
                                exerciseModel.getImage(), exerciseModel.getName(),
                                String.valueOf(exerciseModel.getType()), exerciseModel.getLevel(),
                                exerciseTimeList.get(i), 0));
                    }
                    latch.countDown();
                },
                () -> {
//                    Empty;
                },
                () -> {
//                    Empty;
                }
        );
        BackgroundTask.executeWithLoading(
                () -> {
                    List<ExerciseModel> showWorkout = contentBD.showWorkout();
                    workoutTimeList = new ArrayList<>();

                    int[] tempTimeWorkout = new int[1];

                    for (int i = 0; i < showWorkout.size(); i++) {
                        String temp = showWorkout.get(i).getExerciseId();
                        String[] exerciseStr = temp.split(",");
                        long[] exercisesId = new long[exerciseStr.length];
                        for (int j = 0; j < exercisesId.length; j++) {
                            exercisesId[j] = Long.parseLong(exerciseStr[j]);
                        }

                        for (long l : exercisesId) {
                            List<ExerciseModel> temp2 = contentBD.showExerciseById(l);
                            List<IntegerModel> temp3 = contentBD.showExerciseExtensionId(
                                    temp2.get(0).getExtension());
                            if (temp2.get(0).getType() == 1) {
                                tempTimeWorkout[0] += (temp3.get(0).getSecondValue() * 60) +
                                        ((temp3.get(0).getSecondValue() - 1) * temp3.get(0).getFifthValue());
                            } else {
                                tempTimeWorkout[0] += (temp3.get(0).getSecondValue() * temp3.get(0).getForthValue()) +
                                        ((temp3.get(0).getSecondValue() - 1) * temp3.get(0).getFifthValue());
                            }
                        }
                        workoutTimeList.add(tempTimeWorkout[0]);
                        tempTimeWorkout[0] = 0;
                    }

                    for (int i = 0; i < showWorkout.size(); i++) {
                        ExerciseModel exerciseModel = showWorkout.get(i);
                        workoutList.add(new FourElementsModel(
                                exerciseModel.getId(), exerciseModel.getImage(),
                                exerciseModel.getName(), String.valueOf(exerciseModel.getType()),
                                exerciseModel.getLevel(), workoutTimeList.get(i), 0));
                    }
                    //                        Last element in RecyclerView;
                    workoutList.add(new FourElementsModel(0, "", "", "",
                            0, 0, 0));
                    latch.countDown();
                },
                () -> {
//                    Empty;
                },
                () -> {
//                    Empty;
                }
        );

        BackgroundTask.executeWithLoading(
                () -> {
                    if (contentBD.getCount("CUSTOM_USER_EXERCISE_TAB") >= 1) {
                        List<ExerciseModel> showUserExercise = contentBD.showUserExercise();

                        for (int i = 0; i < showUserExercise.size(); i++) {
                            if (showUserExercise.get(i).getExtension() > 0) {
                                List<IntegerModel> extensionExe = contentBD.showExerciseExtensionId(
                                        showUserExercise.get(i).getExtension());

                                if (showUserExercise.get(0).getType() == 1) {
                                    tempUserTimeExercise[0] += (extensionExe.get(0).getSecondValue() * GlobalClass.DEFAULT_EXERCISE_TIME) +
                                            ((extensionExe.get(0).getSecondValue() - 1) * extensionExe.get(0).getFifthValue());
                                } else {
                                    tempUserTimeExercise[0] += (extensionExe.get(0).getSecondValue() * extensionExe.get(0).getForthValue()) +
                                            ((extensionExe.get(0).getSecondValue() - 1) * extensionExe.get(0).getFifthValue());
                                }
                                exerciseTimeList.add(tempUserTimeExercise[0]);
                                tempUserTimeExercise[0] = 0;
                            }
                            ExerciseModel exerciseModel = showUserExercise.get(i);
                            userExerciseList.add(new FourElementsModel(exerciseModel.getId(),
                                    exerciseModel.getImage(), exerciseModel.getName(),
                                    String.valueOf(exerciseModel.getType()), exerciseModel.getLevel(),
                                    exerciseTimeList.get(i), 1));
                        }
                    }
                    latch.countDown();
                },
                () -> {
//                    Empty;
                },
                () -> {
//                    Empty;
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

    private void setDefaultValuesForTheList() {
        //        default values;
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
        Log.i(TAG, "values(fromWhere): " + fromWhere);
        Log.i(TAG, "values(val): " + firstValue + " 2: " + secondValue + " " + thirdValue);
        switch (listName) {
            case THIRD_LIST:
            case FIRST_LIST:
                fromWhere = secondValue;
                Log.i(TAG, "values(FIRST_LIST): " + fromWhere);
                replaceFragment(R.id.act_library_container, new DetailsFragment(), TAG_DETAIL,
                        R.string.start, DETAILS_FRAGMENT_LIST);
                break;
            case SECOND_LIST:
                replaceFragment(R.id.act_library_container, new WorkoutList(), TAG_WORKOUT_LIST,
                        R.string.workout, DETAILS_FRAGMENT_LIST);
                workoutId = firstValue;
                break;
            case DETAILS_FRAGMENT_LIST:
                Intent intent = new Intent(LibraryActivity.this, ExerciseActivity.class);
                intent.putExtra(GlobalClass.userID, currentUserID);
                if (workoutId != 0) {
                    intent.putExtra("id", (long) workoutId);
                    intent.putExtra("type", (byte) -1);
                    intent.putExtra("listName", "Workout");
                    intent.putExtra("extension", -1);
                    intent.putExtra("fromWhere", fromWhere);
                } else {
                    intent.putExtra("id", (long) firstValue);
                    intent.putExtra("type", (byte) secondValue);
                    intent.putExtra("listName", "Exercise");
                    intent.putExtra("extension", extensionId);
                    intent.putExtra("fromWhere", fromWhere);
                }
                startActivity(intent);
                break;
            case "workoutList":
                replaceFragment(R.id.act_library_container, new DetailsFragment(), null,
                        R.string.return_text, WORKOUT_DETAILS);
                break;
            case "ExerciseModelList":
                createExerciseClass.setValue("exercise", firstValue);
                creatorExerciseFragment.fillFields();
                break;
            case WORKOUT_DETAILS:
                getSupportFragmentManager().popBackStack();
                break;
            default:
                throw new NullPointerException(TAG + "This " + listName + " does not exist");
        }
    }

    @Override
    public void singleIntValue(String name, int value) {
        Log.i(TAG, "singleIntValue: " + fromWhere);
        if (name.equals("LibraryButtonFragment")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            switch (value) {
                case 1:
                    fillLists(() -> {
                        ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
                        bundle.putStringArray("titles", fragmentTitles);
                        bundle.putParcelableArrayList(FIRST_LIST,
                                (ArrayList<? extends Parcelable>) exerciseList);
                        bundle.putParcelableArrayList(SECOND_LIST,
                                (ArrayList<? extends Parcelable>) workoutList);
                        viewPagerFragment.setArguments(bundle);
                        ft.addToBackStack(TAG_VIEW_PAGER);
                        ft.replace(R.id.act_library_container, viewPagerFragment, TAG_VIEW_PAGER);
                    });
                    break;
                case 2:
                    break;
                case 3:
                    creatorExerciseFragment = new CustomExerciseCreatorFragment();
                    bundle.putLong("userId", currentUserID);
                    creatorExerciseFragment.setArguments(bundle);
                    ft.addToBackStack(CUSTOM_EXERCISE_CREATOR_TAG);
                    ft.replace(R.id.act_library_container, creatorExerciseFragment,
                            CUSTOM_EXERCISE_CREATOR_TAG);
                    break;
                default:
                    throw new NullPointerException("Button does no exist");
            }
            ft.setReorderingAllowed(true);
            ft.commit();
        } else if (name.equals("startWorkout")) {
            Intent intent = new Intent(LibraryActivity.this, ExerciseActivity.class);
            intent.putExtra(GlobalClass.userID, currentUserID);
            intent.putExtra("id", (long) workoutId);
            intent.putExtra("type", (byte) -1);
            intent.putExtra("fromWhere", fromWhere);
            intent.putExtra("listName", "Workout");
            startActivity(intent);
        }
    }

    @Override
    public void createExercise(String name, ExerciseType exerciseType, int sets, int volume,
                               int rest) {

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