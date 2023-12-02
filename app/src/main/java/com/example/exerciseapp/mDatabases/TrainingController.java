package com.example.exerciseapp.mDatabases;

import com.example.exerciseapp.mEnums.BodyParts;
import com.example.exerciseapp.mModels.BodyPartsModel;
import com.example.exerciseapp.mModels.ExerciseModel;
import com.example.exerciseapp.mModels.ExtensionExerciseModel;
import com.example.exerciseapp.mModels.WorkoutModel;

import java.util.List;

public class TrainingController {

    private final TrainingRepository trainingRepository;

    public TrainingController(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public boolean addExercise(ExerciseModel exerciseModel,
                               ExtensionExerciseModel extensionExerciseModel) {
        return trainingRepository.createExercise(exerciseModel, extensionExerciseModel);
    }

    public boolean addWorkout(WorkoutModel workoutModel) {
        return trainingRepository.createWorkout(workoutModel);
    }

    public boolean addBodyPartCombination(BodyPartsModel bodyPartsModel) {
        return trainingRepository.createBodyPartsCombination(bodyPartsModel);
    }

    public List<ExerciseModel> getAllExercises() {
        return trainingRepository.getAllExercises();
    }

    public ExerciseModel getExerciseById(long exerciseId) {
        return trainingRepository.getExerciseById(exerciseId);
    }

    public List<WorkoutModel> getAllWorkouts() {
        return trainingRepository.getAllWorkouts();
    }

    public WorkoutModel getWorkoutById(long workoutId) {
        return trainingRepository.getWorkoutById(workoutId);
    }

    public List<ExtensionExerciseModel> getAllExercisesExtensions() {
        return trainingRepository.getAllExercisesExtensions();
    }

    public List<ExtensionExerciseModel> getAllExerciseExtensionsById(long exerciseId) {
        return trainingRepository.getAllExerciseExtensionsById(exerciseId);
    }

    public List<ExtensionExerciseModel> getAllUserExerciseExtensionsById(long userId, long exerciseId) {
        return trainingRepository.getAllUserExerciseExtensionsById(userId, exerciseId);
    }

    public ExtensionExerciseModel getExerciseExtensionById(long exerciseId) {
        return trainingRepository.getExerciseExtensionById(exerciseId);
    }

    public List<BodyPartsModel> getAllBodyPartsCombinations() {
        return trainingRepository.getAllBodyPartsCombinations();
    }

    public List<BodyPartsModel> getAllCombinationsBySpecificPart(BodyParts part) {
        return trainingRepository.getAllCombinationsBySpecificPart(part);
    }

    public BodyPartsModel getBodyPartsCombinationById(long bodyPartsCombinationId) {
        return trainingRepository.getBodyPartsCombinationById(bodyPartsCombinationId);
    }
}
