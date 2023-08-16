package com.example.exerciseapp;

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

import com.example.exerciseapp.mClasses.GlobalClass;
import com.example.exerciseapp.mClasses.SharedViewModel;
import com.example.exerciseapp.mDatabases.DBHelper;
import com.example.exerciseapp.mInterfaces.FragmentSupportListener;
import com.example.exerciseapp.mInterfaces.IUserData;
import com.example.exerciseapp.mModels.IntegerModel;
import com.example.exerciseapp.mModels.UserInformationModel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class WelcomeActivity extends AppCompatActivity implements FragmentSupportListener, IUserData {

    private Button actionBtn;


    private LinkedList<Integer> regListInt = new LinkedList<>();
    private LinkedList<String> regListStr = new LinkedList<>();
    private Map<String, Runnable> runnableMap;
    private boolean isCorrect = false;
    private long newUser;
    private String username;
    private String email;
    private String password;


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView(savedInstanceState);

        fragmentObserver();
    }

    private void initView(Bundle savedInstanceState) {
        actionBtn = findViewById(R.id.aWelcome_actionBtn);

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

        runnableMap = new HashMap<>();
        runnableMap.put(SIGN_IN_TAG, this::handleSignIn);
        runnableMap.put(GENDER_TAG, this::handleSelectGender);
        runnableMap.put(HEIGHT_TAG, this::handleSelectHeight);
        runnableMap.put(WEIGHT_TAG, this::handleSelectWeight);
        runnableMap.put(LEVEL_TAG, this::handleSelectLevel);
        runnableMap.put(GOALS_TAG, this::handleSelectGoals);
        runnableMap.put(PERFORMANCE_TAG, this::handleSelectPerformance);

        actionBtn.setOnClickListener(v -> handleButton());
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

    private void sendToDB() {

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

        dbHelper.insertUnitsTab(new IntegerModel(-1, regListInt.get(1), regListInt.get(3),
                height, weight));

        dbHelper.insertGoalsTab(new IntegerModel(-1, regListInt.get(6), regListInt.get(7),
                regListInt.get(8), regListInt.get(9)));

        dbHelper.insertPerformance(new IntegerModel(-1, regListInt.get(10), regListInt.get(11),
                regListInt.get(12), regListInt.get(13)));

        dbHelper.insertNotifications(new IntegerModel(-1,
                1, 3, 4, 2));

        newUser = dbHelper.insertUserInformation(new UserInformationModel(
                -1, username, email, password, "0", "0",
                regListInt.get(0), dbHelper.getLastID("USER_UNITS"),
                dbHelper.getLastID("USER_PERFORMANCE"),
                dbHelper.getLastID("GOALS"), regListInt.get(5),
                dbHelper.getLastID("NOTIFICATIONS"))).getIndex();
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
        replaceFragment(new SelectGenderFragment(), GENDER_TAG);
    }

    private void handleSelectGender() {
        if (isCorrect) {
            selectGenderFragment.fragmentMessage();
            replaceFragment(new SelectHeightFragment(), HEIGHT_TAG);
        } else {
            Toast.makeText(WelcomeActivity.this, R.string.select, Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSelectHeight() {
        selectHeightFragment.fragmentMessage();
        replaceFragment(new SelectWeightFragment(), WEIGHT_TAG);
    }

    private void handleSelectWeight() {
        selectWeightFragment.fragmentMessage();
        replaceFragment(new SelectLevelFragment(), LEVEL_TAG);
    }

    private void handleSelectLevel() {
        if (isCorrect) {
            selectLevelFragment.fragmentMessage();
            replaceFragment(new SelectGoalsFragment(), GOALS_TAG);
        } else {
            Toast.makeText(WelcomeActivity.this, R.string.select,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSelectGoals() {
        if (isCorrect) {
            selectGoalsFragment.fragmentMessage();
            replaceFragment(new SelectPerformanceFragment(), PERFORMANCE_TAG);
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
    public void data(String username, String email, byte[] password) {
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