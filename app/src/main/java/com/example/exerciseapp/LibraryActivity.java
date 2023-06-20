package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.ExerciseModel;
import com.example.exerciseapp.mModels.FourElementsModel;
import com.example.exerciseapp.mModels.IntegerModel;

import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends AppCompatActivity implements UpdateIntegersDB {

    private String[] fragmentTitles = new String[]{"Exercises", "Workouts"};

    private List<FourElementsModel> exerciseList = new ArrayList<>();
    private List<FourElementsModel> workoutList = new ArrayList<>();

    private List<IntegerModel> extendedExercise = new ArrayList<>();

    private String firstList = "firstList";
    private String secondList = "secondList";

    private ContentBD contentBD;

    private final String TAG_VIEW_PAGER = "tagViewPager";
    private final String TAG_DETAIL = "tagDetail";
    private final String TAG_WORKOUT_LIST = "tagWorkoutList";

    private long id;
    private long workoutId;
    private long[] exerciseListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        contentBD = new ContentBD(this);
        initView();

        List<ExerciseModel> temp = contentBD.showExercise();
        List<ExerciseModel> workoutTemp = contentBD.showWorkout();

        for (int i = 0; i < temp.size(); i++) {

            FourElementsModel model = new FourElementsModel(
                    temp.get(i).getId(), temp.get(i).getImage(), temp.get(i).getName(),
                    String.valueOf(temp.get(i).getType()), R.drawable.ic_hexagon);
            exerciseList.add(model);
        }

        for (int i = 0; i < workoutTemp.size(); i++) {

            FourElementsModel model = new FourElementsModel(
                    workoutTemp.get(i).getId(), workoutTemp.get(i).getImage(), workoutTemp.get(i).getName(),
                    String.valueOf(workoutTemp.get(i).getKcal()), R.drawable.ic_hexagon);
            workoutList.add(model);
        }

        fillLists();

        if (findViewById(R.id.act_library_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            addFragment(R.id.act_library_container, new ViewPagerFragment(), true, TAG_VIEW_PAGER);
        }
    }

    private void fillLists() {
        if (extendedExercise.size() <= 0) {
            IntegerModel extend1 = new IntegerModel(-1, 1, 8, 12, 0, 30);
            contentBD.insertExerciseExtension(extend1);
            IntegerModel extend2 = new IntegerModel(-1, 2, 3, 0, 65, 45);
            contentBD.insertExerciseExtension(extend2);
            IntegerModel extend3 = new IntegerModel(-1, 3, 4, 6, 0, 45);
            contentBD.insertExerciseExtension(extend3);
        }
        extendedExercise = contentBD.showExerciseExtensionId(1);

        if (exerciseList.size() <= 0) {
            ExerciseModel exercise1 = new ExerciseModel(-1, "Exercise1 ABC", "", 2,
                    "5", "21", 1, 5, 20, "description1", 1);
            contentBD.insertExercise(exercise1);
            ExerciseModel exercise2 = new ExerciseModel(-1, "Exercise2 BGH", "", 1,
                    "2", "54", 2, 10, 25, "description2", 2);
            contentBD.insertExercise(exercise2);
            ExerciseModel exercise3 = new ExerciseModel(-1, "Exercise3 BCI", "", 3,
                    "4", "71", 1, 15, 35, "description3", 3);
            contentBD.insertExercise(exercise3);
        }

        if (workoutList.size() <= 0) {
            ExerciseModel workout1 = new ExerciseModel(-1, "Workout1 YTR", "", 2,
                    "5", "21", 10, 55, "descriptionWorkout1",
                    "2,3,1");
            contentBD.insertWorkout(workout1);
            ExerciseModel workout2 = new ExerciseModel(-1, "Workout2 URC", "", 1,
                    "2", "54", 21, 35, "descriptionWorkout2",
                    "1,2,3");
            contentBD.insertWorkout(workout2);
            ExerciseModel workout3 = new ExerciseModel(-1, "Workout3 YTN", "", 3,
                    "4", "71", 17, 15, "descriptionWorkout3",
                    "3,2,1");
            contentBD.insertWorkout(workout3);
        }
    }

    private void addFragment(int container, Fragment fragment, boolean addToBackStack, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putStringArray("titles", fragmentTitles);
        bundle.putParcelableArrayList("firstList", (ArrayList<? extends Parcelable>) exerciseList);
        bundle.putParcelableArrayList("secondList", (ArrayList<? extends Parcelable>) workoutList);
        fragment.setArguments(bundle);
        ft.setReorderingAllowed(true);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.add(container, fragment, tag);
        ft.commit();
    }

    private void replaceFragment(int container, Fragment fragment, boolean addToBackStack, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        System.out.println("ID: " + id);
        fragment.setArguments(bundle);
        ft.setReorderingAllowed(true);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(container, fragment, tag);
        ft.commit();
    }

    private void initView() {

    }


    /**
     *
     * @param listName
     * @param firstValue = id exercise or workout;
     * @param secondValue = exercise type 1 == repetitions, 2 == time;
     * @param thirdValue
     */
    @Override
    public void values(String listName, int firstValue, int secondValue, int thirdValue) {
        id = (long) firstValue;

        System.out.println("(v)ListName: " + listName);
        System.out.println("(v)firstValue: " + firstValue);
        System.out.println("(v)secondValue: " + secondValue);

        if (listName.equals(firstList)) {
            replaceFragment(R.id.act_library_container, new DetailsFragment(), true, TAG_DETAIL);
            System.out.println("detailsFragment= firstList");
        } else if (listName.equals(secondList)) {
            replaceFragment(R.id.act_library_container, new WorkoutList(), true, TAG_WORKOUT_LIST);
            workoutId = firstValue;
        } else if (listName.equals("detailsFragment")) {
            Intent intent = new Intent(LibraryActivity.this, ExerciseActivity.class);
            if (workoutId != 0) {
                intent.putExtra("id", (long) workoutId);
                intent.putExtra("type", (byte) -1);
            } else {
                intent.putExtra("exerciseId", (long) firstValue);
                intent.putExtra("exerciseType", (byte) secondValue);
                System.out.println("new Intent");
            }
            startActivity(intent);
        } else if(listName.equals("workoutList")) {
            replaceFragment(R.id.act_library_container, new DetailsFragment(), true, TAG_DETAIL);
            System.out.println("workoutFragment");

        } else if(0 != 0) {
//            Intent intent = new Intent(LibraryActivity.this, ExerciseActivity.class);
//            intent.putExtra("exerciseListId", exerciseListId);
//            startActivity(intent);
        } else {
            Log.i(TAG, "(lib_act)values: " + listName);
            throw new NullPointerException(TAG + " listName does not exist");
        }
    }
}