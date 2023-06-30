package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.exerciseapp.mInterfaces.ISummary;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;

public class UserActivity extends AppCompatActivity implements UpdateIntegersDB, ISummary {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        if (findViewById(R.id.act_user_calendar) != null) {

            if (savedInstanceState != null) {
                return;
            }

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setReorderingAllowed(true);
            ft.add(R.id.act_user_calendar, new CalendarFragment(), "calendarTag");
            ft.commit();
        }
    }

    @Override
    public void values(String listName, int firstValue, int secondValue, int thirdValue) {

        if (listName.equals("detailsFragment")) {
            Log.i(TAG, "values: (intent): " + firstValue);
            Intent intent = new Intent(UserActivity.this, ExerciseActivity.class);
            intent.putExtra("id", (long) firstValue);

            Log.i(TAG, "values: (type): " + secondValue);
            intent.putExtra("type", (byte) secondValue);
            startActivity(intent);
        } else if (listName.equals("calendarFragment")) {
            Log.i(TAG, "values: " + firstValue);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.setReorderingAllowed(true);
            if (firstValue <= 0) {
                ft.replace(R.id.act_user_calendar_details, new EmptyFragment(), "emptyTag");
            } else {
                DetailsFragment detailsFragment = new DetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("id", firstValue);
                bundle.putString("btnText", "Goo");
                detailsFragment.setArguments(bundle);
                ft.replace(R.id.act_user_calendar_details, detailsFragment, "exerciseTag");
            }
            ft.commit();
        }
    }

    @Override
    public void summaryMessage(String name, String strVal, int numVal, boolean conditionVal) {

    }
}