package com.example.exerciseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

public class ExerciseActivity extends AppCompatActivity {

    FrameLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        container = findViewById(R.id.aExercise_container);

    }
}