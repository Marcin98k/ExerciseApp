package com.example.exerciseapp.mDatabases.Training.Tabels.Exercise;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert
    long createExercise(ExerciseModel exerciseModel);

    @Query("SELECT * FROM EXERCISE")
    List<ExerciseModel> getAll();

    @Query("SELECT * FROM EXERCISE WHERE ID = :id")
    ExerciseModel getExerciseById(int id);

    @Delete
    void delete(ExerciseModel exerciseModel);
}
