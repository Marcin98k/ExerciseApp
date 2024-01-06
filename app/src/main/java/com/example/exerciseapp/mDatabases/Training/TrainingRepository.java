package com.example.exerciseapp.mDatabases.Training;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.exerciseapp.mDatabases.Training.Tabels.BodyParts.BodyPartsDao;
import com.example.exerciseapp.mDatabases.Training.Tabels.BodyParts.BodyPartsModel;
import com.example.exerciseapp.mDatabases.Training.Tabels.Equipment.EquipmentDao;
import com.example.exerciseapp.mDatabases.Training.Tabels.Equipment.EquipmentModel;
import com.example.exerciseapp.mDatabases.Training.Tabels.Equipment.EquipmentVolumeDao;
import com.example.exerciseapp.mDatabases.Training.Tabels.Equipment.EquipmentVolumeModel;
import com.example.exerciseapp.mDatabases.Training.Tabels.Equipment.VolumeDao;
import com.example.exerciseapp.mDatabases.Training.Tabels.Equipment.VolumeModel;
import com.example.exerciseapp.mDatabases.Training.Tabels.Exercise.ExerciseDao;
import com.example.exerciseapp.mDatabases.Training.Tabels.Exercise.ExerciseModel;
import com.example.exerciseapp.mDatabases.Training.Tabels.ExtensionExercises.ExtensionExercisesDao;
import com.example.exerciseapp.mDatabases.Training.Tabels.ExtensionExercises.ExtensionExercisesModel;
import com.example.exerciseapp.mDatabases.Training.Tabels.Workout.WorkoutDao;
import com.example.exerciseapp.mDatabases.Training.Tabels.Workout.WorkoutModel;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TrainingRepository {

    private final ExerciseDao exerciseDao;
    private final WorkoutDao workoutDao;
    private final ExtensionExercisesDao extensionExercisesDao;
    private final BodyPartsDao bodyPartsDao;
    private final EquipmentDao equipmentDao;
    private final VolumeDao volumeDao;
    private final EquipmentVolumeDao equipmentVolumeDao;
    private final ExecutorService executorService;


    public TrainingRepository(Context context) {

        TrainingDatabase db = Room.databaseBuilder(context, TrainingDatabase.class,
                "Training.db").build();

        exerciseDao = db.exerciseDao();
        workoutDao = db.workoutDao();
        extensionExercisesDao = db.extensionExercisesDao();
        bodyPartsDao = db.bodyPartsDao();
        equipmentDao = db.equipmentDao();
        volumeDao = db.volumeDao();
        equipmentVolumeDao = db.equipmentVolumeDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    //    CREATE
    public long createExercise(ExerciseModel exerciseModel) throws ExecutionException, InterruptedException {
        return executorService.submit(() -> exerciseDao.createExercise(exerciseModel)).get();
    }

    public void createWorkout(WorkoutModel workoutModel) {
        executorService.execute(() -> workoutDao.createWorkout(workoutModel));
    }

    public long createExtensionOfExercises(ExtensionExercisesModel extensionExercisesModel)
            throws ExecutionException, InterruptedException {
        return executorService.submit(() ->
                extensionExercisesDao.createExtensionOfExercises(extensionExercisesModel)).get();
    }

    public long createBodyPartsCombination(BodyPartsModel bodyPartsModel) throws
            ExecutionException, InterruptedException {
        return executorService.submit(() ->
                bodyPartsDao.createBodyPartsCombination(bodyPartsModel)).get();
    }

    public void createEquipment(EquipmentModel equipmentModel) {
        executorService.execute(() -> equipmentDao.createEquipment(equipmentModel));
    }

    public void createVolume(VolumeModel volumeModel) {
        executorService.execute(() -> volumeDao.createVolume(volumeModel));
    }

    public void createEquipmentVolume(EquipmentVolumeModel equipmentVolumeModel) {
        executorService.execute(() -> {
            try {
                equipmentVolumeDao.createEquipmentVolume(equipmentVolumeModel);
            } catch (android.database.sqlite.SQLiteConstraintException e) {
                Log.e("TrainingRepository", "Error inserting data: ", e);
            }
        });
    }


    public LiveData<EquipmentModel> getEquipmentById(int id) {
        MutableLiveData<EquipmentModel> liveData = new MutableLiveData<>();
        executorService.execute(() -> {
            EquipmentModel equipment = equipmentDao.getEquipmentById(id);
            if (equipment != null) {
                liveData.postValue(equipment);
            } else {
                Log.i("TAG", "No equipment found with id: " + id);
            }
        });
        return liveData;
    }

    public LiveData<List<EquipmentModel>> getAllEquipment() {
        MutableLiveData<List<EquipmentModel>> liveData = new MutableLiveData<>();
        executorService.execute(() -> {
            List<EquipmentModel> equipmentList = equipmentDao.getAllEquipment();
            liveData.postValue(equipmentList);
        });
        return liveData;
    }

    public LiveData<List<EquipmentModel>> getAllEquipmentByIds(List<Integer> integerList) {

        MutableLiveData<List<EquipmentModel>> liveData = new MutableLiveData<>();
        executorService.execute(() -> {
            List<EquipmentModel> equipmentList = equipmentDao.getListEquipmentByIds(
                    integerList.stream().mapToInt(i -> i).toArray()
            );
            liveData.postValue(equipmentList);
        });
        return liveData;
    }
}
