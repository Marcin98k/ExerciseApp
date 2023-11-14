package com.example.exerciseapp.mInterfaces;

import com.example.exerciseapp.mEnums.ExerciseType;

public interface NewExercise {

    void createExercise(String name, ExerciseType exerciseType, int sets, int volume, int rest);
}
