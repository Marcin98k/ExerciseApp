package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.ExerciseModel;
import com.example.exerciseapp.mModels.FourElementsModel;

import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends AppCompatActivity implements UpdateIntegersDB {

    private String[] fragmentTitles = new String[]{"Exercises", "Workouts"};

    private List<FourElementsModel> exerciseList = new ArrayList<>();
    private List<FourElementsModel> workoutList = new ArrayList<>();

    private String firstList = "firstList";
    private String secondList = "secondList";

    private ContentBD contentBD;

    private final String TAG_VIEW_PAGER = "tagViewPager";
    private final String TAG_DETAIL = "tagDetail";
    private final String TAG_WORKOUT_LIST = "tagWorkoutList";

    private long id;

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


//        if (exerciseList.size() <= 0) {
//            ExerciseModel exercise1 = new ExerciseModel(-1, "Exercise1 ABC", "", 2,
//                    "5", "21", 1, 5, 20, "description1");
//            contentBD.insertExercise(exercise1);
//            ExerciseModel exercise2 = new ExerciseModel(-1, "Exercise2 BGH", "", 1,
//                    "2", "54", 2, 10, 25, "description2");
//            contentBD.insertExercise(exercise2);
//            ExerciseModel exercise3 = new ExerciseModel(-1, "Exercise3 BCI", "", 3,
//                    "4", "71", 1, 15, 35, "description3");
//            contentBD.insertExercise(exercise3);
//        }
//
//        if (workoutList.size() <= 0) {
//            ExerciseModel workout1 = new ExerciseModel(-1, "Workout1 YTR", "", 2,
//                    "5", "21", 10, 55, "descriptionWorkout1",
//                    "7,88");
//            contentBD.insertWorkout(workout1);
//            ExerciseModel workout2 = new ExerciseModel(-1, "Workout2 URC", "", 1,
//                    "2", "54", 21, 35, "descriptionWorkout2",
//                    "4,5,6");
//            contentBD.insertWorkout(workout2);
//            ExerciseModel workout3 = new ExerciseModel(-1, "Workout3 YTN", "", 3,
//                    "4", "71", 17, 15, "descriptionWorkout3",
//                    "3,2,1");
//            contentBD.insertWorkout(workout3);
//        }

        if (findViewById(R.id.act_library_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            addFragment(R.id.act_library_container, new ViewPagerFragment(), true, TAG_VIEW_PAGER);
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

    @Override
    public void values(String listName, int firstValue, int secondValue, int thirdValue) {
        id = (long) firstValue;

        if (listName.equals(firstList)) {
            replaceFragment(R.id.act_library_container, new DetailsFragment(), true, TAG_DETAIL);
        } else if (listName.equals(secondList)) {
            replaceFragment(R.id.act_library_container, new WorkoutList(), true, TAG_WORKOUT_LIST);
        } else {
            throw new NullPointerException(TAG + " listName does not exist");
        }
    }
}