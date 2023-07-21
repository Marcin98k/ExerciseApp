package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.mClasses.SharedViewModel;
import com.example.exerciseapp.mDatabases.DBHelper;
import com.example.exerciseapp.mInterfaces.FragmentSupportListener;
import com.example.exerciseapp.mInterfaces.INewExercise;
import com.example.exerciseapp.mModels.IntegerModel;
import com.example.exerciseapp.mModels.UserInformationModel;

import java.util.LinkedList;

public class WelcomeActivity extends AppCompatActivity implements FragmentSupportListener {

    private Button actionBtn;


    private LinkedList<Integer> regListInt = new LinkedList<>();
    private LinkedList<String> regListStr = new LinkedList<>();
    private boolean isCorrect = false;


    private SharedViewModel sharedViewModel;
    private FragmentManager fragmentManager;


    private DBHelper dbHelper;
    private SignInFragment signInFragment;
    private SignUpFragment signUpFragment;
    private SelectGenderFragment selectGenderFragment;
    private SelectHeightFragment selectHeightFragment;
    private SelectWeightFragment selectWeightFragment;
    private SelectLevelFragment selectLevelFragment;
    private SelectGoalsFragment selectGoalsFragment;
    private SelectPerformanceFragment selectPerformanceFragment;


    private final String SIGN_IN_TAG = "tagSignInFragment";
    private final String SIGN_UP_TAG = "tagSignUpFragment";
    private final String GENDER_TAG = "tagSelectGenderFragment";
    private final String HEIGHT_TAG = "tagSelectHeightFragment";
    private final String WEIGHT_TAG = "tagSelectWeightFragment";
    private final String LEVEL_TAG = "tagSelectLevelFragment";
    private final String GOALS_TAG = "tagSelectGoalsFragment";
    private final String PERFORMANCE_TAG = "tagSelectPerformanceFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        dbHelper = new DBHelper(WelcomeActivity.this);
        fragmentManager = getSupportFragmentManager();

        if (findViewById(R.id.aWelcome_FL_mainContainer) != null) {

            if (savedInstanceState != null) {
                return;
            }

            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.addToBackStack(SIGN_IN_TAG);
            ft.add(R.id.aWelcome_FL_mainContainer, new SignInFragment(), SIGN_IN_TAG);
            ft.commit();
        }

        fragmentObserver();

        nextFragment(actionBtn);
    }

    private void initView() {
        actionBtn = findViewById(R.id.aWelcome_actionBtn);
    }

    private void replaceFragment(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft1 = fm.beginTransaction();
        ft1.addToBackStack(tag);
        ft1.replace(R.id.aWelcome_FL_mainContainer, fragment, tag);
        ft1.commit();
    }

    private void initFragments() {
        this.signInFragment = (SignInFragment) this.fragmentManager.findFragmentByTag(SIGN_IN_TAG);
        this.signUpFragment = (SignUpFragment) this.fragmentManager.findFragmentByTag(SIGN_UP_TAG);
        this.selectGenderFragment = (SelectGenderFragment) this.fragmentManager.findFragmentByTag(GENDER_TAG);
        this.selectHeightFragment = (SelectHeightFragment) this.fragmentManager.findFragmentByTag(HEIGHT_TAG);
        this.selectWeightFragment = (SelectWeightFragment) this.fragmentManager.findFragmentByTag(WEIGHT_TAG);
        this.selectLevelFragment = (SelectLevelFragment) this.fragmentManager.findFragmentByTag(LEVEL_TAG);
        this.selectGoalsFragment = (SelectGoalsFragment) this.fragmentManager.findFragmentByTag(GOALS_TAG);
        this.selectPerformanceFragment = (SelectPerformanceFragment) this.fragmentManager.findFragmentByTag(PERFORMANCE_TAG);
    }

    private void nextFragment(Button btn) {

        btn.setOnClickListener(v -> {

            initFragments();

            if (signInFragment != null && signInFragment.isVisible()) {
                Log.i(TAG, "nextFragment: signIn");
                btn.setText(R.string.next);
                replaceFragment(new SelectGenderFragment(), GENDER_TAG);
            } else if (selectGenderFragment != null && selectGenderFragment.isVisible()) {
                Log.i(TAG, "nextFragment: selectGen");
                if (isCorrect) {
                    selectGenderFragment.fragmentMessage();
                    replaceFragment(new SelectHeightFragment(), HEIGHT_TAG);
                } else {
                    Toast.makeText(WelcomeActivity.this, "Select", Toast.LENGTH_SHORT).show();
                }
            } else if (selectHeightFragment != null && selectHeightFragment.isVisible()) {
                selectHeightFragment.fragmentMessage();
                replaceFragment(new SelectWeightFragment(), WEIGHT_TAG);
            } else if (selectWeightFragment != null && selectWeightFragment.isVisible()) {
                selectWeightFragment.fragmentMessage();
                replaceFragment(new SelectLevelFragment(), LEVEL_TAG);
            } else if (selectLevelFragment != null && selectLevelFragment.isVisible()) {

                if (isCorrect) {
                    selectLevelFragment.fragmentMessage();
                    replaceFragment(new SelectGoalsFragment(), GOALS_TAG);
                } else {
                    Toast.makeText(WelcomeActivity.this, "Select", Toast.LENGTH_SHORT).show();
                }
            } else if (selectGoalsFragment != null && selectGoalsFragment.isVisible()) {

                if (isCorrect) {
                    selectGoalsFragment.fragmentMessage();
                    replaceFragment(new SelectPerformanceFragment(), PERFORMANCE_TAG);
                } else {
                    Toast.makeText(WelcomeActivity.this, "Select min. 1", Toast.LENGTH_SHORT).show();
                }
            } else if (selectPerformanceFragment != null && selectPerformanceFragment.isVisible()) {
                selectPerformanceFragment.fragmentMessage();
                replaceFragment(new SignUpFragment(), SIGN_UP_TAG);
                btn.setText(R.string.sign_up);
            } else if (signUpFragment != null && signUpFragment.isVisible()) {

                Log.e("WelcomeActivity!", regListInt.toString());
                signUpFragment.fragmentMessage();
                sendToDB();

                Intent intent = new Intent(WelcomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
            Toast.makeText(this, "List: " + regListInt, Toast.LENGTH_SHORT).show();
        });
    }

    //    Before use add units and performance table;
    private void sendToDB() {

//        Gender fragment(number of indexes = 1);
        IntegerModel gender1;
        IntegerModel gender2;
        IntegerModel gender3;

            switch (regListInt.get(0)) {
                case 1:
                    gender1 = new IntegerModel(-1, 1, 1);
                    gender2 = new IntegerModel(-1, 0, 2);
                    gender3 = new IntegerModel(-1, 0, 3);
                    break;
                case 2:
                    gender1 = new IntegerModel(-1, 0, 1);
                    gender2 = new IntegerModel(-1, 1, 2);
                    gender3 = new IntegerModel(-1, 0, 3);
                    break;
                case 3:
                    gender1 = new IntegerModel(-1, 0, 1);
                    gender2 = new IntegerModel(-1, 0, 2);
                    gender3 = new IntegerModel(-1, 1, 3);
                    break;
                default:
                    gender1 = new IntegerModel(-1, 0, 1);
                    gender2 = new IntegerModel(-1, 0, 2);
                    gender3 = new IntegerModel(-1, 0, 3);
            }
        dbHelper.insertMiddleTab(gender1);
        dbHelper.insertMiddleTab(gender2);
        dbHelper.insertMiddleTab(gender3);


//        Height fragment(number of indexes = 2);

        IntegerModel userUnits;

        int height;
        int weight;

        switch (regListInt.get(2)) {
            case 1:
                height = 1;
                break;
            case 2:
                height = 2;
                break;
            default:
                height = 0;
        }
        switch (regListInt.get(4)) {
            case 1:
                weight = 1;
                break;
            case 2:
                weight = 2;
                break;
            default:
                weight = 0;
        }
        userUnits = new IntegerModel(
                -1,
                4,
                5,
                height,
                weight);

        dbHelper.insertUnitsTab(userUnits);

        IntegerModel unit1 = new IntegerModel(-1, regListInt.get(1), 4);
        IntegerModel unit2 = new IntegerModel(-1, regListInt.get(3), 5);

        dbHelper.insertMiddleTab(unit1);
        dbHelper.insertMiddleTab(unit2);


        //        Level fragment(number of indexes = 3);

        IntegerModel level1;
        IntegerModel level2;
        IntegerModel level3;
        switch (regListInt.get(5)) {
            case 1:
                level1 = new IntegerModel(-1, 1, 10);
                level2 = new IntegerModel(-1, 0, 11);
                level3 = new IntegerModel(-1, 0, 12);
                break;
            case 2:
                level1 = new IntegerModel(-1, 0, 10);
                level2 = new IntegerModel(-1, 1, 11);
                level3 = new IntegerModel(-1, 0, 12);
                break;
            case 3:
                level1 = new IntegerModel(-1, 0, 10);
                level2 = new IntegerModel(-1, 0, 11);
                level3 = new IntegerModel(-1, 1, 12);
                break;
            default:
                level1 = new IntegerModel(-1, 0, 10);
                level2 = new IntegerModel(-1, 0, 11);
                level3 = new IntegerModel(-1, 0, 12);
        }

        dbHelper.insertMiddleTab(level1);
        dbHelper.insertMiddleTab(level2);
        dbHelper.insertMiddleTab(level3);

        //        Goals fragment(number of indexes = 4);
        IntegerModel goals1;
        IntegerModel goals2;
        IntegerModel goals3;
        IntegerModel goals4;

        try {
            goals1 = new IntegerModel(-1, regListInt.get(6), 6);
        } catch (Exception e) {
            goals1 = new IntegerModel(-1, 2, 0);
        }

        try {
            goals2 = new IntegerModel(-1, regListInt.get(7), 7);
        } catch (Exception e) {
            goals2 = new IntegerModel(-1, 2, 0);
        }

        try {
            goals3 = new IntegerModel(-1, regListInt.get(8), 8);
        } catch (Exception e) {
            goals3 = new IntegerModel(-1, 2, 0);
        }

        try {
            goals4 = new IntegerModel(-1, regListInt.get(9), 9);
        } catch (Exception e) {
            goals4 = new IntegerModel(-1, 2, 0);
        }
        dbHelper.insertMiddleTab(goals1);
        dbHelper.insertMiddleTab(goals2);
        dbHelper.insertMiddleTab(goals3);
        dbHelper.insertMiddleTab(goals4);



//        Pefrormance fragment(number of indexes = 4);
        IntegerModel performance1;
        IntegerModel performance2;
        IntegerModel performance3;
        IntegerModel performance4;

        try {
            performance1 = new IntegerModel(-1, regListInt.get(10), 13);
        } catch (Exception e) {
            performance1 = new IntegerModel(-1, 0, 0);
        }

        try {
            performance2 = new IntegerModel(-1, regListInt.get(11), 14);
        } catch (Exception e) {
            performance2 = new IntegerModel(-1, 0, 0);
        }

        try {
            performance3 = new IntegerModel(-1, regListInt.get(12), 15);
        } catch (Exception e) {
            performance3 = new IntegerModel(-1, 0, 0);
        }

        try {
            performance4 = new IntegerModel(-1, regListInt.get(13), 16);
        } catch (Exception e) {
            performance4 = new IntegerModel(-1, 0, 0);
        }
        dbHelper.insertMiddleTab(performance1);
        dbHelper.insertMiddleTab(performance2);
        dbHelper.insertMiddleTab(performance3);
        dbHelper.insertMiddleTab(performance4);


        Log.e(TAG, "sendToDB: " +  dbHelper.showMiddleTab().toString());
        int length = dbHelper.showMiddleTab().size() - 16;

        Log.e(TAG, "sendToDB: " +  dbHelper.showMiddleTab().get(length).getId());
        IntegerModel genderModel = new IntegerModel(-1,
                dbHelper.showMiddleTab().get(length).getId(),
                dbHelper.showMiddleTab().get(length + 1).getId(),
                dbHelper.showMiddleTab().get(length + 2).getId());
        dbHelper.insertGenderTab(genderModel);

        IntegerModel unitsModel = new IntegerModel(-1,
                dbHelper.showMiddleTab().get(length + 3).getId());
        dbHelper.insertUnitsTab(unitsModel);

        IntegerModel levelModel = new IntegerModel(-1,
                dbHelper.showMiddleTab().get(length + 5).getId(),
                dbHelper.showMiddleTab().get(length + 6).getId(),
                dbHelper.showMiddleTab().get(length + 7).getId());
        dbHelper.insertLevelTab(levelModel);

        IntegerModel goalsModel = new IntegerModel(-1,
                dbHelper.showMiddleTab().get(length + 8).getId(),
                dbHelper.showMiddleTab().get(length + 9).getId(),
                dbHelper.showMiddleTab().get(length + 10).getId(),
                dbHelper.showMiddleTab().get(length + 11).getId());
        dbHelper.insertGoalsTab(goalsModel);

        IntegerModel performanceModel = new IntegerModel(-1,
                dbHelper.showMiddleTab().get(length + 12).getId(),
                dbHelper.showMiddleTab().get(length + 13).getId(),
                dbHelper.showMiddleTab().get(length + 14).getId(),
                dbHelper.showMiddleTab().get(length + 15).getId());
        dbHelper.insertUserPerformance(performanceModel);

        IntegerModel notificationModel = new IntegerModel(-1,
                1,3, 4, 2);
        dbHelper.insertUserNotifications(notificationModel);

        UserInformationModel userInformation = new UserInformationModel(
                -1, "name", "email", "password",
                dbHelper.showLastGender().get(0).getId(),
                dbHelper.showUnitsTab().get(0).getId(),
                dbHelper.showLastUserPerformance().get(0).getId(),
                dbHelper.showLastUserGoals().get(0).getId(),
                dbHelper.showLastLevel().get(0).getId(),
                dbHelper.showLastUserNotification().get(0).getId());
        dbHelper.insertUserInformation(userInformation);

    }

    /*

    Appperance + 1 (units)

    FirebaseConnect.class
     */

    private void fragmentObserver() {
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.getSharedInt().observe(this, item -> regListInt.add(item));
        sharedViewModel.getSharedStr().observe(this, item -> regListStr.add(item));
    }

    private void deleteLoop(LinkedList<Integer> list, int count) {
        for (int i = 0; i < count; i++) {
            list.remove(list.size() - 1);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        initFragments();

        if (regListInt.isEmpty()) {
            actionBtn.setText(R.string.sign_up);
            Toast.makeText(this, "List is Empty", Toast.LENGTH_SHORT).show();
        }
        if (selectGenderFragment != null && selectGenderFragment.isVisible()) {
            deleteLoop(regListInt, 1);
        }
        if (selectHeightFragment != null && selectHeightFragment.isVisible()) {
            deleteLoop(regListInt, 2);
        }
        if (selectWeightFragment != null && selectWeightFragment.isVisible()) {
            deleteLoop(regListInt, 2);
        }
        if (selectLevelFragment != null && selectLevelFragment.isVisible()) {
            deleteLoop(regListInt, 1);
        }
        if (selectGoalsFragment != null && selectGoalsFragment.isVisible()) {
            deleteLoop(regListInt, 4);
        }
        if (selectPerformanceFragment != null && selectPerformanceFragment.isVisible()) {
            actionBtn.setText(R.string.next);
            deleteLoop(regListInt, 4);
        }
        Toast.makeText(this, "Inside list is:\n" + regListInt, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void checkCondition(boolean param) {
        isCorrect = param;
    }

}