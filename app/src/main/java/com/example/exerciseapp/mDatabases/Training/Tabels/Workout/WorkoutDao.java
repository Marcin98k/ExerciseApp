package com.example.exerciseapp.mDatabases.Training.Tabels.Workout;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface WorkoutDao {

    @Insert
    long createWorkout(WorkoutModel workoutModel);

    @Query("SELECT * FROM WORKOUT WHERE ID = :id")
    WorkoutModel getWorkoutById(int id);

}
