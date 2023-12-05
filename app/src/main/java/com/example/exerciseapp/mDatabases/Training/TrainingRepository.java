package com.example.exerciseapp.mDatabases.Training;

import android.content.Context;

import androidx.room.Room;

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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TrainingRepository {

    private final ExerciseDao exerciseDao;
    private final WorkoutDao workoutDao;
    private final ExtensionExercisesDao extensionExercisesDao;
    private final BodyPartsDao bodyPartsDao;
    private final EquipmentDao equipmentDao;
    private final ExecutorService executorService;

    public TrainingRepository(Context context) {

        TrainingDatabase db = Room.databaseBuilder(context, TrainingDatabase.class,
                "Training.db").build();

        exerciseDao = db.exerciseDao();
        workoutDao = db.workoutDao();
        extensionExercisesDao = db.extensionExercisesDao();
        bodyPartsDao = db.bodyPartsDao();
        equipmentDao = db.equipmentDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    //    CREATE
    public void createExercise(ExerciseModel exerciseModel) {
        executorService.execute(() -> exerciseDao.createExercise(exerciseModel));
    }

    public void createWorkout(WorkoutModel workoutModel) {
        executorService.execute(() -> workoutDao.createWorkout(workoutModel));
    }

    public void createExtensionOfExercises(ExtensionExercisesModel extensionExercisesModel) {
        executorService.execute(() ->
                extensionExercisesDao.createExtensionOfExercises(extensionExercisesModel));
    }

    public void createBodyPartsCombination(BodyPartsModel bodyPartsModel) {
        executorService.execute(() -> bodyPartsDao.createBodyPartsCombination(bodyPartsModel));
    }

    public void createEquipment(EquipmentModel equipmentModel) {
        executorService.execute(() -> equipmentDao.createEquipment(equipmentModel));
    }
}
