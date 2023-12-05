package com.example.exerciseapp.mDatabases.Training.Tabels.ExtensionExercises;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ExtensionExercisesDao {

    @Insert
    long createExtensionOfExercises(ExtensionExercisesModel extensionExercisesModel);

    @Query("SELECT * FROM EXTENSION_EXERCISE WHERE ID = :id")
    ExtensionExercisesModel getExtensionById(int id);
}
