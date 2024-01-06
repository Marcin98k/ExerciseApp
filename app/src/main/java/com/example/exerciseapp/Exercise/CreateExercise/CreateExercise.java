package com.example.exerciseapp.Exercise.CreateExercise;

import com.example.exerciseapp.mDatabases.Training.Tabels.BodyParts.BodyPartsModel;
import com.example.exerciseapp.mDatabases.Training.Tabels.Exercise.ExerciseModel;
import com.example.exerciseapp.mDatabases.Training.Tabels.ExtensionExercises.ExtensionExercisesModel;
import com.example.exerciseapp.mDatabases.Training.TrainingRepository;
import com.example.exerciseapp.mEnums.BodyParts;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class CreateExercise {

    private final TrainingRepository trainingRepository;
    private final ExerciseViewModel exerciseViewModel;

    public CreateExercise(TrainingRepository trainingRepository,
                          ExerciseViewModel exerciseViewModel) {
        if (trainingRepository == null || exerciseViewModel == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        this.trainingRepository = trainingRepository;
        this.exerciseViewModel = exerciseViewModel;
    }

    public void create() throws ExecutionException, InterruptedException {
        long bodyPartsId = setBodyParts();
        long exerciseId = setExercise(bodyPartsId);
        setExtension(exerciseId);
    }

    long setBodyParts() throws ExecutionException, InterruptedException {

        BodyPartsModel bodyParts = createBodyPartsModel(Objects.requireNonNull(
                exerciseViewModel.getBodyPartsValues().getValue()
        ));
        return trainingRepository.createBodyPartsCombination(bodyParts);
    }

    private BodyPartsModel createBodyPartsModel(Map<BodyParts, Boolean> bodyPartsValues) {

        BodyPartsModel bodyParts = new BodyPartsModel();
        bodyParts.setChestStatus(Boolean.TRUE.equals(bodyPartsValues.get(BodyParts.CHEST)));
        bodyParts.setBackStatus(Boolean.TRUE.equals(bodyPartsValues.get(BodyParts.BACK)));
        bodyParts.setShouldersStatus(Boolean.TRUE.equals(bodyPartsValues.get(BodyParts.SHOULDERS)));
        bodyParts.setArmsStatus(Boolean.TRUE.equals(bodyPartsValues.get(BodyParts.ARMS)));
        bodyParts.setABSStatus(Boolean.TRUE.equals(bodyPartsValues.get(BodyParts.ABS)));
        bodyParts.setLegsStatus(Boolean.TRUE.equals(bodyPartsValues.get(BodyParts.LEGS)));
        bodyParts.setFullBodyStatus(Boolean.TRUE.equals(bodyPartsValues.get(BodyParts.FULL_BODY)));
        return bodyParts;
    }

    long setExercise(long bodyPartsId) throws ExecutionException, InterruptedException {
        ExerciseModel exerciseModel = createExercise();

        if (bodyPartsId == -1) {
            throw new IllegalArgumentException("Body Parts ID does not exist");
        } else {
            exerciseModel.setBodyParts(bodyPartsId);
            return trainingRepository.createExercise(exerciseModel);
        }
    }

    private ExerciseModel createExercise() {

        ExerciseModel exerciseModel = new ExerciseModel();

        exerciseModel.setImage(getTextValues(ExerciseViewModel.ExerciseTextFields.IMAGE));
        exerciseModel.setName(getTextValues(ExerciseViewModel.ExerciseTextFields.NAME));
        exerciseModel.setDescription(getTextValues(ExerciseViewModel.ExerciseTextFields.DESCRIPTION));
        exerciseModel.setEquipmentIds(getTextValues(ExerciseViewModel.ExerciseTextFields.EQUIPMENT));

        exerciseModel.setLevel(exerciseViewModel.getLevel().getValue());
        exerciseModel.setFromWhere(exerciseViewModel.getFromWhere().getValue());
        exerciseModel.setUserId(exerciseViewModel.getUserId().getValue());

        exerciseModel.setKcal(getNumericalValues(ExerciseViewModel.ExerciseIntegerFields.KCAL));
        exerciseModel.setDuration(getNumericalValues(ExerciseViewModel.ExerciseIntegerFields.DURATION));

        return exerciseModel;
    }

    long setExtension(long exerciseId) throws ExecutionException, InterruptedException {

        ExtensionExercisesModel extensionExercises = createExtension();

        if (exerciseId == -1) {
            throw new IllegalArgumentException("Exercise ID does not exist");
        } else {
            extensionExercises.setExerciseId(exerciseId);
            return trainingRepository.createExtensionOfExercises(extensionExercises);
        }
    }

    private ExtensionExercisesModel createExtension() {

        ExtensionExercisesModel extensionExercises = new ExtensionExercisesModel();

        extensionExercises.setExerciseType(exerciseViewModel.getExerciseType().getValue());
        extensionExercises.setNumberOfSeries(getNumericalValues(
                ExerciseViewModel.ExerciseIntegerFields.NUMBER_OF_SERIES)
        );
        extensionExercises.setVolume(getNumericalValues(
                ExerciseViewModel.ExerciseIntegerFields.VOLUME)
        );
        extensionExercises.setBreakLength(getNumericalValues(
                ExerciseViewModel.ExerciseIntegerFields.BREAK_LENGTH)
        );

        return extensionExercises;
    }

    private Integer getNumericalValues(ExerciseViewModel.ExerciseIntegerFields key) {
        return Objects.requireNonNull(exerciseViewModel.getNumericalValues().getValue())
                .getOrDefault(key, 0);
    }

    private String getTextValues(ExerciseViewModel.ExerciseTextFields key) {
        return Objects.requireNonNull(exerciseViewModel.getTextValues().getValue())
                .getOrDefault(key, "");
    }
}