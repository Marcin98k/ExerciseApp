package com.example.exerciseapp.mDatabases.Training;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.exerciseapp.mDatabases.Training.Tabels.BodyParts.BodyPartsDao;
import com.example.exerciseapp.mDatabases.Training.Tabels.BodyParts.BodyPartsModel;
import com.example.exerciseapp.mDatabases.Training.Tabels.Equipment.EquipmentDao;
import com.example.exerciseapp.mDatabases.Training.Tabels.Equipment.EquipmentModel;
import com.example.exerciseapp.mDatabases.Training.Tabels.Exercise.ExerciseDao;
import com.example.exerciseapp.mDatabases.Training.Tabels.Exercise.ExerciseModel;
import com.example.exerciseapp.mDatabases.Training.Tabels.ExtensionExercises.ExtensionExercisesDao;
import com.example.exerciseapp.mDatabases.Training.Tabels.ExtensionExercises.ExtensionExercisesModel;
import com.example.exerciseapp.mDatabases.Training.Tabels.Workout.WorkoutDao;
import com.example.exerciseapp.mDatabases.Training.Tabels.Workout.WorkoutModel;

@Database(entities = {ExerciseModel.class, WorkoutModel.class, ExtensionExercisesModel.class,
        BodyPartsModel.class, EquipmentModel.class}, version = 1, exportSchema = false)
public abstract class TrainingDatabase extends RoomDatabase {

    public abstract ExerciseDao exerciseDao();

    public abstract WorkoutDao workoutDao();

    public abstract ExtensionExercisesDao extensionExercisesDao();

    public abstract BodyPartsDao bodyPartsDao();

    public abstract EquipmentDao equipmentDao();
}
