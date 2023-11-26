package com.example.exerciseapp.mInterfaces;

import com.example.exerciseapp.mEnums.ExerciseType;

public interface NewExerciseToChange {

    void createExercise(String name, ExerciseType exerciseType, int sets, int volume, int rest);
}
