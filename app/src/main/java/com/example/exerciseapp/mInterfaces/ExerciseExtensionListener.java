package com.example.exerciseapp.mInterfaces;

import com.example.exerciseapp.mEnums.ExerciseType;

public interface ExerciseExtensionListener {
    void passExtension(ExerciseType exerciseType, int numberOfSeries, int volume, int breakLength);
}
