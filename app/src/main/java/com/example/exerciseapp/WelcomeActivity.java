package com.example.exerciseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class WelcomeActivity extends AppCompatActivity implements SendByte {
    private FragmentManager fragmentManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        toolbar = findViewById(R.id.aWelcome_toolbar);

        fragmentManager = getSupportFragmentManager();

        if (findViewById(R.id.aWelcome_FL_mainContainer) != null) {

            if (savedInstanceState != null) {
                return;
            }
            addFragment(R.id.aWelcome_FL_mainContainer, new WelcomeFragment());
        }
    }

    private void addFragment(int container, Fragment fragment) {

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void replaceFragment(Fragment fragment, int container, boolean addToBackStack, String tag) {

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(container, fragment);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commit();
    }

    @Override
    public void mByte(byte param) {

        if (param == 0) {
            replaceFragment(new SignInFragment(), R.id.aWelcome_FL_mainContainer, true, "tagSignIn");
        } else {
            replaceFragment(new SignUpFragment(), R.id.aWelcome_FL_mainContainer, true, "tagSignUp");
        }
    }
}