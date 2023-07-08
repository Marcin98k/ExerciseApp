package com.example.exerciseapp.mInterfaces;

import com.example.exerciseapp.ExerciseType;

public interface INewExercise {

    void createExercise(String name, ExerciseType exerciseType, int sets, int volume, int rest);

//    void createExercise(String name, int id);
}
