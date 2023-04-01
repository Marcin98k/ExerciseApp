package com.example.exerciseapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

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

                signUpFragment.fragmentMessage();
                sendToDB();
//                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
//                startActivity(intent);
            }
            Toast.makeText(this, "List: " + regListInt, Toast.LENGTH_SHORT).show();
        });
    }

    private void sendToDB() {

        UserInformationModel userInformationModel;
        IntegerModel integerModel;

        try {
            userInformationModel = new UserInformationModel(-1, "", regListInt.get(0),
                    regListInt.get(1), regListInt.get(2), regListInt.get(3), regListInt.get(4), regListInt.get(5));
        } catch (Exception e) {
            userInformationModel = new UserInformationModel(-1, "", 0,
                    0, 0, 0, 0, 0);
            e.printStackTrace();
        }
        dbHelper.insertUserInformation(userInformationModel);

        try {
            integerModel = new IntegerModel(-1, regListInt.get(6), regListInt.get(7),
                    regListInt.get(8), regListInt.get(9));
        } catch (Exception e) {
            integerModel = new IntegerModel(-1, 0, 0, 0, 0);
        }
        dbHelper.insertUserGoals(integerModel);

        try {
            integerModel = new IntegerModel(-1, regListInt.get(10), regListInt.get(11),
                    regListInt.get(12), regListInt.get(13));
        } catch (Exception e) {
            integerModel = new IntegerModel(-1, 0, 0, 0, 0);
        }
        dbHelper.insertUserPerformance(integerModel);
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