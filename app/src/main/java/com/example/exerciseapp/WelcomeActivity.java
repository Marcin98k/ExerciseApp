package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.Interfaces.FragmentSupportListener;
import com.example.exerciseapp.Models.AppearanceBlockModel;
import com.example.exerciseapp.Models.IntegerModel;
import com.example.exerciseapp.Models.UserInformationModel;

import java.util.LinkedList;

public class WelcomeActivity extends AppCompatActivity implements FragmentSupportListener {
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    private LinkedList<Integer> regListInt = new LinkedList<>();
    private LinkedList<String> regListStr = new LinkedList<>();
    private Button actionBtn;

    private SharedViewModel sharedViewModel;

    private DBHelper dbHelper;

    private boolean isCorrect = false;

    //    Fragments instances;
    SignInFragment signInFragment;
    SignUpFragment signUpFragment;
    SelectGenderFragment selectGenderFragment;
    SelectHeightFragment selectHeightFragment;
    SelectWeightFragment selectWeightFragment;
    SelectLevelFragment selectLevelFragment;
    SelectGoalsFragment selectGoalsFragment;
    SelectPerformanceFragment selectPerformanceFragment;

    private final String tagSignIn = "tagSignInFragment";
    private final String tagSignUp = "tagSignUpFragment";
    private final String tagGender = "tagSelectGenderFragment";
    private final String tagHeight = "tagSelectHeightFragment";
    private final String tagWeight = "tagSelectWeightFragment";
    private final String tagLevel = "tagSelectLevelFragment";
    private final String tagGoals = "tagSelectGoalsFragment";
    private final String tagPerformance = "tagSelectPerformanceFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        toolbar = findViewById(R.id.aWelcome_toolbar);
        dbHelper = new DBHelper(WelcomeActivity.this);
        fragmentManager = getSupportFragmentManager();

        if (findViewById(R.id.aWelcome_FL_mainContainer) != null) {

            if (savedInstanceState != null) {
                return;
            }
            addFragment(R.id.aWelcome_FL_mainContainer, new SignInFragment(), tagSignIn);
        }

        fragmentObserver();
        if (dbHelper.getCount("APPEARANCE") <= 0) {
            insertUnits();
        } else {
            Log.e(TAG, "onCreate: Appearance is filled");
        }
        if (dbHelper.getCount("FUTURE") <= 0) {
            dbHelper.insertFutureTab("Test@test");
        }

        actionBtn = findViewById(R.id.aWelcome_actionBtn);
        nextFragment(actionBtn);
    }

    private void addFragment(int container, Fragment fragment, String tag) {

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(container, fragment, tag);
        ft.commit();
    }

    private void replaceFragment(Fragment fragment, String tag) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft1 = fm.beginTransaction();
        ft1.addToBackStack(tag);
        ft1.replace(R.id.aWelcome_FL_mainContainer, fragment, tag);
        ft1.commit();
    }

    private void initFragments() {

        this.signInFragment = (SignInFragment) this.fragmentManager.findFragmentByTag(tagSignIn);
        this.signUpFragment = (SignUpFragment) this.fragmentManager.findFragmentByTag(tagSignUp);
        this.selectGenderFragment = (SelectGenderFragment) this.fragmentManager.findFragmentByTag(tagGender);
        this.selectHeightFragment = (SelectHeightFragment) this.fragmentManager.findFragmentByTag(tagHeight);
        this.selectWeightFragment = (SelectWeightFragment) this.fragmentManager.findFragmentByTag(tagWeight);
        this.selectLevelFragment = (SelectLevelFragment) this.fragmentManager.findFragmentByTag(tagLevel);
        this.selectGoalsFragment = (SelectGoalsFragment) this.fragmentManager.findFragmentByTag(tagGoals);
        this.selectPerformanceFragment = (SelectPerformanceFragment) this.fragmentManager.findFragmentByTag(tagPerformance);
    }

    private void nextFragment(Button btn) {

        btn.setOnClickListener(v -> {

            initFragments();

            if (signInFragment != null && signInFragment.isVisible()) {
                btn.setText(R.string.next);
                replaceFragment(new SelectGenderFragment(), tagGender);
            } else if (selectGenderFragment != null && selectGenderFragment.isVisible()) {

                if (isCorrect) {

                    selectGenderFragment.fragmentMessage();
                    replaceFragment(new SelectHeightFragment(), tagHeight);
                } else {
                    Toast.makeText(WelcomeActivity.this, "Select", Toast.LENGTH_SHORT).show();
                }
            } else if (selectHeightFragment != null && selectHeightFragment.isVisible()) {

                selectHeightFragment.fragmentMessage();
                replaceFragment(new SelectWeightFragment(), tagWeight);

            } else if (selectWeightFragment != null && selectWeightFragment.isVisible()) {

                selectWeightFragment.fragmentMessage();
                replaceFragment(new SelectLevelFragment(), tagLevel);

            } else if (selectLevelFragment != null && selectLevelFragment.isVisible()) {

                if (isCorrect) {
                    selectLevelFragment.fragmentMessage();
                    replaceFragment(new SelectGoalsFragment(), tagGoals);
                } else {
                    Toast.makeText(WelcomeActivity.this, "Select", Toast.LENGTH_SHORT).show();
                }
            } else if (selectGoalsFragment != null && selectGoalsFragment.isVisible()) {

                if (isCorrect) {
                    selectGoalsFragment.fragmentMessage();
                    replaceFragment(new SelectPerformanceFragment(), tagPerformance);
                } else {
                    Toast.makeText(WelcomeActivity.this, "Select min. 1", Toast.LENGTH_SHORT).show();
                }
            } else if (selectPerformanceFragment != null && selectPerformanceFragment.isVisible()) {

                selectPerformanceFragment.fragmentMessage();
                replaceFragment(new SignUpFragment(), tagSignUp);
                btn.setText(R.string.sign_up);
                System.out.println(regListInt);
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

    private void insertUnits() {


//        Gender;
        AppearanceBlockModel gender1 = new AppearanceBlockModel(
                -1, R.drawable.ic_male, "", getResources().getString(R.string.male),
                0, "", "Gender");
        AppearanceBlockModel gender2 = new AppearanceBlockModel(
                -1, R.drawable.ic_female, "", getResources().getString(R.string.female),
                0, "", "Gender");
        AppearanceBlockModel gender3 = new AppearanceBlockModel(
                -1, R.drawable.ic_block, "", getResources().getString(R.string.other),
                0, "", "Gender");

        dbHelper.insertAppearanceBlock(gender1);
        dbHelper.insertAppearanceBlock(gender2);
        dbHelper.insertAppearanceBlock(gender3);

//        Units;
        AppearanceBlockModel unit1 = new AppearanceBlockModel(
                -1, R.drawable.ic_hexagon, "", "Height",
                0, "", "Units");
        AppearanceBlockModel unit2 = new AppearanceBlockModel(
                -1, R.drawable.ic_hexagon, "", "Weight",
                0, "", "Units");

        dbHelper.insertAppearanceBlock(unit1);
        dbHelper.insertAppearanceBlock(unit2);


//        Units other table;
        AppearanceBlockModel model = new AppearanceBlockModel(-1, "cm");
        AppearanceBlockModel model1 = new AppearanceBlockModel(-1, "in");

        dbHelper.insertHeightTab(model);
        dbHelper.insertHeightTab(model1);


        AppearanceBlockModel model2 = new AppearanceBlockModel(-1, "kg");
        AppearanceBlockModel model3 = new AppearanceBlockModel(-1, "lbs");

        dbHelper.insertWeightTab(model2);
        dbHelper.insertWeightTab(model3);


//        Goals;
        AppearanceBlockModel goal1 = new AppearanceBlockModel(
                -1, 0, "", getResources().getString(R.string.strength),
                0, "", "Goal");
        AppearanceBlockModel goal2 = new AppearanceBlockModel(
                -1, 0, "", getResources().getString(R.string.muscle),
                0, "", "Goal");
        AppearanceBlockModel goal3 = new AppearanceBlockModel(
                -1, 0, "", getResources().getString(R.string.fatLose),
                0, "", "Goal");
        AppearanceBlockModel goal4 = new AppearanceBlockModel(
                -1, 0, "", getResources().getString(R.string.technique),
                0, "", "Goal");

        dbHelper.insertAppearanceBlock(goal1);
        dbHelper.insertAppearanceBlock(goal2);
        dbHelper.insertAppearanceBlock(goal3);
        dbHelper.insertAppearanceBlock(goal4);


//        Level;
        AppearanceBlockModel level1 = new AppearanceBlockModel(
                -1, R.drawable.ic_star_one, "", getResources().getString(R.string.beginner),
                0, "", "Level");
        AppearanceBlockModel level2 = new AppearanceBlockModel(
                -1, R.drawable.ic_star_two, "", getResources().getString(R.string.intermediate),
                0, "", "Level");
        AppearanceBlockModel level3 = new AppearanceBlockModel(
                -1, R.drawable.ic_star_three, "", getResources().getString(R.string.advanced),
                0, "", "Level");

        dbHelper.insertAppearanceBlock(level1);
        dbHelper.insertAppearanceBlock(level2);
        dbHelper.insertAppearanceBlock(level3);


//        Performance;
        AppearanceBlockModel performance1 = new AppearanceBlockModel(
                -1, 0, "", getResources().getString(R.string.push),
                0, "", "Performance");
        AppearanceBlockModel performance2 = new AppearanceBlockModel(
                -1, 0, "", getResources().getString(R.string.pull),
                0, "", "Performance");
        AppearanceBlockModel performance3 = new AppearanceBlockModel(
                -1, 0, "", getResources().getString(R.string.squad),
                0, "", "Performance");
        AppearanceBlockModel performance4 = new AppearanceBlockModel(
                -1, 0, "", getResources().getString(R.string.dip),
                0, "", "Performance");

        dbHelper.insertAppearanceBlock(performance1);
        dbHelper.insertAppearanceBlock(performance2);
        dbHelper.insertAppearanceBlock(performance3);
        dbHelper.insertAppearanceBlock(performance4);


//        User section ID{17 - 20};

        AppearanceBlockModel username = new AppearanceBlockModel(
                -1, R.drawable.ic_person, "", "Username",
                0, "", "User");
        AppearanceBlockModel email = new AppearanceBlockModel(
                -1, R.drawable.ic_email, "", "E-mail",
                0, "", "User");
        AppearanceBlockModel password = new AppearanceBlockModel(
                -1, R.drawable.ic_lock, "", "Password",
                0, "", "User");

        dbHelper.insertAppearanceBlock(username);
        dbHelper.insertAppearanceBlock(email);
        dbHelper.insertAppearanceBlock(password);

    }

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