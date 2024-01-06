package com.example.exerciseapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.SignInANDSingUp.SelectGenderFragmentToChange;
import com.example.exerciseapp.SignInANDSingUp.SelectGoalsFragmentToChange;
import com.example.exerciseapp.SignInANDSingUp.SelectHeightFragmentToChange;
import com.example.exerciseapp.SignInANDSingUp.SelectLevelFragmentToChange;
import com.example.exerciseapp.SignInANDSingUp.SelectPerformanceFragmentToChange;
import com.example.exerciseapp.SignInANDSingUp.SelectWeightFragmentToChange;
import com.example.exerciseapp.SignInANDSingUp.SignInFragmentToChange;
import com.example.exerciseapp.SignInANDSingUp.SignUpFragment;
import com.example.exerciseapp.mClasses.GlobalClass;
import com.example.exerciseapp.mClasses.SharedViewModel;
import com.example.exerciseapp.mDatabases.DBHelper;
import com.example.exerciseapp.mInterfaces.FragmentSupportListener;
import com.example.exerciseapp.mInterfaces.UserMasterData;
import com.example.exerciseapp.mModels.IntegerModel;
import com.example.exerciseapp.mModels.UserInformationModelToChange;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class WelcomeActivity extends AppCompatActivity implements FragmentSupportListener,
        UserMasterData {

    private final String SIGN_IN_TAG = "tagSignInFragment";
    private final String SIGN_UP_TAG = "tagSignUpFragment";
    private final String GENDER_TAG = "tagSelectGenderFragment";
    private final String HEIGHT_TAG = "tagSelectHeightFragment";
    private final String WEIGHT_TAG = "tagSelectWeightFragment";
    private final String LEVEL_TAG = "tagSelectLevelFragment";
    private final String GOALS_TAG = "tagSelectGoalsFragment";
    private final String PERFORMANCE_TAG = "tagSelectPerformanceFragment";

    private Button actionBtn;

    private LinkedList<Integer> regListInt = new LinkedList<>();
    private LinkedList<String> regListStr = new LinkedList<>();
    private Map<String, Runnable> runnableMap;
    private boolean isCorrect = false;
    private long newUser;
    private String username;
    private String email;
    private String password;

    private FragmentManager fragmentManager;

    private DBHelper dbHelper;
    private SelectGenderFragmentToChange selectGenderFragment;
    private SelectHeightFragmentToChange selectHeightFragment;
    private SelectWeightFragmentToChange selectWeightFragment;
    private SelectLevelFragmentToChange selectLevelFragment;
    private SelectGoalsFragmentToChange selectGoalsFragment;
    private SelectPerformanceFragmentToChange selectPerformanceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initializeView(savedInstanceState);

        fragmentObserver();
    }

    private void initializeView(Bundle savedInstanceState) {
        initializeWidgets();

        dbHelper = new DBHelper(WelcomeActivity.this);
        fragmentManager = getSupportFragmentManager();

        if (findViewById(R.id.aWelcome_FL_mainContainer) != null) {
            if (savedInstanceState != null) {
                return;
            }
            addFirstFragmentToActivity();
        }

        setupRunnableMapToRegistration();
        actionBtn.setOnClickListener(v -> handleButton());
    }

    private void initializeWidgets() {
        actionBtn = findViewById(R.id.aWelcome_actionBtn);
    }

    private void addFirstFragmentToActivity() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.addToBackStack(SIGN_IN_TAG);
        ft.add(R.id.aWelcome_FL_mainContainer, new SignInFragmentToChange(), SIGN_IN_TAG);
        ft.commit();
    }

    private void setupRunnableMapToRegistration() {

        runnableMap = new HashMap<>();
        runnableMap.put(SIGN_IN_TAG, this::handleSignIn);
        runnableMap.put(GENDER_TAG, this::handleSelectGender);
        runnableMap.put(HEIGHT_TAG, this::handleSelectHeight);
        runnableMap.put(WEIGHT_TAG, this::handleSelectWeight);
        runnableMap.put(LEVEL_TAG, this::handleSelectLevel);
        runnableMap.put(GOALS_TAG, this::handleSelectGoals);
        runnableMap.put(PERFORMANCE_TAG, this::handleSelectPerformance);
    }

    private void replaceFragment(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft1 = fm.beginTransaction();
        ft1.addToBackStack(tag);
        ft1.replace(R.id.aWelcome_FL_mainContainer, fragment, tag);
        ft1.commit();
    }

    private void initFragments() {
        this.selectGenderFragment = (SelectGenderFragmentToChange) this.fragmentManager
                .findFragmentByTag(GENDER_TAG);
        this.selectHeightFragment = (SelectHeightFragmentToChange) this.fragmentManager
                .findFragmentByTag(HEIGHT_TAG);
        this.selectWeightFragment = (SelectWeightFragmentToChange) this.fragmentManager
                .findFragmentByTag(WEIGHT_TAG);
        this.selectLevelFragment = (SelectLevelFragmentToChange) this.fragmentManager
                .findFragmentByTag(LEVEL_TAG);
        this.selectGoalsFragment = (SelectGoalsFragmentToChange) this.fragmentManager
                .findFragmentByTag(GOALS_TAG);
        this.selectPerformanceFragment = (SelectPerformanceFragmentToChange) this.fragmentManager
                .findFragmentByTag(PERFORMANCE_TAG);
    }

    private void sendToDB() {

        int height = getUnit(regListInt.get(2));
        int weight = getUnit(regListInt.get(4));

        dbHelper.insertUnitsTab(new IntegerModel(-1, regListInt.get(1), regListInt.get(3),
                height, weight));

        dbHelper.insertGoalsTab(new IntegerModel(-1, regListInt.get(6), regListInt.get(7),
                regListInt.get(8), regListInt.get(9)));

        dbHelper.insertPerformance(new IntegerModel(-1, regListInt.get(10), regListInt.get(11),
                regListInt.get(12), regListInt.get(13)));

        dbHelper.insertNotifications(new IntegerModel(-1,
                1, 3, 4, 2));

        newUser = dbHelper.insertUserInformation(new UserInformationModelToChange(
                -1, username, email, password, "0", "0",
                regListInt.get(0), dbHelper.getLastID("USER_UNITS"),
                dbHelper.getLastID("USER_PERFORMANCE"),
                dbHelper.getLastID("GOALS"), regListInt.get(5),
                dbHelper.getLastID("NOTIFICATIONS"))).getIndex();
    }

    private int getUnit(int value) {
        switch (value) {
            case 1:
            case 2:
                return value;
            default:
                return 0;
        }
    }

    private void fragmentObserver() {
        SharedViewModel sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.getSharedInt().observe(this, item -> regListInt.add(item));
        sharedViewModel.getSharedStr().observe(this, item -> regListStr.add(item));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        initFragments();

        if (regListInt.isEmpty()) {
            actionBtn.setText(R.string.sign_up);
        }

        handleFragmentVisibility(selectGenderFragment, 1);
        handleFragmentVisibility(selectHeightFragment, 2);
        handleFragmentVisibility(selectWeightFragment, 2);
        handleFragmentVisibility(selectLevelFragment, 1);
        handleFragmentVisibility(selectGoalsFragment, 4);

        if (selectPerformanceFragment != null && selectPerformanceFragment.isVisible()) {
            actionBtn.setText(R.string.next);
            deleteLoop(regListInt, 4);
        }
    }

    private void handleFragmentVisibility(Fragment fragment, int value) {
        if (fragment != null && fragment.isVisible()) {
            deleteLoop(regListInt, value);
        }
    }

    private void deleteLoop(LinkedList<Integer> list, int count) {
        for (int i = 0; i < count; i++) {
            list.remove(list.size() - 1);
        }
    }

    @Override
    public void checkCondition(boolean param) {
        isCorrect = param;
    }

    private void handleButton() {

        initFragments();
        for (Map.Entry<String, Runnable> entry : runnableMap.entrySet()) {
            String tag = entry.getKey();
            Runnable task = entry.getValue();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
            if (fragment != null && fragment.isVisible()) {
                task.run();
                break;
            }
        }
    }

    private void handleSignIn() {
        actionBtn.setText(R.string.next);
        replaceFragment(new SelectGenderFragmentToChange(), GENDER_TAG);
    }

    private void handleSelectGender() {
        if (isCorrect) {
            selectGenderFragment.fragmentMessage();
            replaceFragment(new SelectHeightFragmentToChange(), HEIGHT_TAG);
        } else {
            Toast.makeText(WelcomeActivity.this, R.string.select, Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSelectHeight() {
        selectHeightFragment.fragmentMessage();
        replaceFragment(new SelectWeightFragmentToChange(), WEIGHT_TAG);
    }

    private void handleSelectWeight() {
        selectWeightFragment.fragmentMessage();
        replaceFragment(new SelectLevelFragmentToChange(), LEVEL_TAG);
    }

    private void handleSelectLevel() {
        if (isCorrect) {
            selectLevelFragment.fragmentMessage();
            replaceFragment(new SelectGoalsFragmentToChange(), GOALS_TAG);
        } else {
            Toast.makeText(WelcomeActivity.this, R.string.select,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSelectGoals() {
        if (isCorrect) {
            selectGoalsFragment.fragmentMessage();
            replaceFragment(new SelectPerformanceFragmentToChange(), PERFORMANCE_TAG);
        } else {
            Toast.makeText(WelcomeActivity.this, R.string.select +
                    getString(R.string.min_d_one), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSelectPerformance() {
        selectPerformanceFragment.fragmentMessage();
        replaceFragment(new SignUpFragment(), SIGN_UP_TAG);
        actionBtn.setText(R.string.sign_up);
    }

    @Override
    public void userData(String username, String email, byte[] password) {
        this.username = username;
        this.email = email;

        StringBuilder builder = new StringBuilder();
        for (byte b : password) {
            builder.append(String.format("%02x", b));
        }
        this.password = builder.toString();

        sendToDB();

        Intent intent = new Intent(WelcomeActivity.this, SettingsActivity.class);
        intent.putExtra(GlobalClass.userID, newUser);
        startActivity(intent);
    }
}