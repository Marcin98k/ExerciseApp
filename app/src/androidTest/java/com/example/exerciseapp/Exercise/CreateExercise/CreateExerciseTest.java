package com.example.exerciseapp.Exercise.CreateExercise;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.exerciseapp.mDatabases.Training.Tabels.Exercise.ExerciseModel;
import com.example.exerciseapp.mDatabases.Training.Tabels.ExtensionExercises.ExtensionExercisesModel;
import com.example.exerciseapp.mDatabases.Training.TrainingRepository;
import com.example.exerciseapp.mEnums.BodyParts;
import com.example.exerciseapp.mEnums.ExerciseType;
import com.example.exerciseapp.mEnums.FromWhere;
import com.example.exerciseapp.mEnums.Level;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CreateExerciseTest {

    private final TrainingRepository trainingRepository = mock(TrainingRepository.class);
    private final ExerciseViewModel exerciseViewModel = mock(ExerciseViewModel.class);
    private final CreateExercise createExercise = new CreateExercise(trainingRepository, exerciseViewModel);

    private final long EXPECTED_POSITIVE_EXERCISE_ID = 1L;
    private final long EXPECTED_NEGATIVE_EXERCISE_ID = -1L;

    @Test
    public void shouldAddExerciseToDatabaseWithCorrectBodyPartsId() throws ExecutionException,
            InterruptedException {

//        given
        setUp();

//        when
        when(createExercise.setBodyParts()).thenReturn(EXPECTED_POSITIVE_EXERCISE_ID);
        long bodyPartsId = createExercise.setBodyParts();
        when(trainingRepository.createExercise(any(ExerciseModel.class))).thenReturn(EXPECTED_POSITIVE_EXERCISE_ID);
        long actualExerciseId = createExercise.setExercise(bodyPartsId);

//        then
        verify(trainingRepository).createExercise(any(ExerciseModel.class));
        assertEquals(EXPECTED_POSITIVE_EXERCISE_ID, actualExerciseId);
    }

    @Test
    public void shouldThrowAnErrorWhenAddingAnExerciseWithTheWrongBodyPartsId() throws
            ExecutionException, InterruptedException {

//        given
        setUp();

//        when
        when(createExercise.setBodyParts()).thenReturn(EXPECTED_NEGATIVE_EXERCISE_ID);
        long bodyPartsId = createExercise.setBodyParts();

//        then
        assertThrows(IllegalArgumentException.class, () -> createExercise.setExercise(bodyPartsId));
    }

    @Test
    public void shouldAddExtensionToDatabaseWithCorrectExerciseId() throws ExecutionException,
            InterruptedException {

//        given
        setUp();

//        when
        when(createExercise.setBodyParts()).thenReturn(EXPECTED_POSITIVE_EXERCISE_ID);
        long bodyPartsId = createExercise.setBodyParts();
        when(createExercise.setExercise(bodyPartsId)).thenReturn(EXPECTED_POSITIVE_EXERCISE_ID);
        long exerciseId = createExercise.setExercise(bodyPartsId);
        when(trainingRepository.createExtensionOfExercises(any(ExtensionExercisesModel.class)))
                .thenReturn(EXPECTED_POSITIVE_EXERCISE_ID);
        long actualExtensionId = createExercise.setExtension(exerciseId);

//        then
        verify(trainingRepository).createExtensionOfExercises(any(ExtensionExercisesModel.class));
        assertEquals(EXPECTED_POSITIVE_EXERCISE_ID, actualExtensionId);
    }

    @Test
    public void shouldThrowAnErrorWhenAddingAnExtensionWithTheWrongExerciseId() throws
            ExecutionException, InterruptedException {

//        given
        setUp();

//        when
        when(createExercise.setBodyParts()).thenReturn(EXPECTED_POSITIVE_EXERCISE_ID);
        long bodyPartsId = createExercise.setBodyParts();
        when(createExercise.setExercise(bodyPartsId)).thenReturn(EXPECTED_NEGATIVE_EXERCISE_ID);
        long exerciseId = createExercise.setExercise(bodyPartsId);

//        then
        assertThrows(IllegalArgumentException.class, () -> createExercise.setExtension(exerciseId));
    }

    private void setUp() {

        Map<BodyParts, Boolean> bodyPartsMap = new HashMap<>();
        bodyPartsMap.put(BodyParts.CHEST, true);
        bodyPartsMap.put(BodyParts.ARMS, true);
        bodyPartsMap.put(BodyParts.ABS, true);
        bodyPartsMap.put(BodyParts.LEGS, true);

        LiveData<Map<BodyParts, Boolean>> bodyPartsLiveMock = new MutableLiveData<>(bodyPartsMap);
        when(exerciseViewModel.getBodyPartsValues()).thenReturn(bodyPartsLiveMock);


        Map<ExerciseViewModel.ExerciseTextFields, String> textValues = new HashMap<>();
        textValues.put(ExerciseViewModel.ExerciseTextFields.IMAGE, "Image");
        textValues.put(ExerciseViewModel.ExerciseTextFields.NAME, "Name");

        LiveData<Map<ExerciseViewModel.ExerciseTextFields, String>> textValuesLiveMock =
                new MutableLiveData<>(textValues);
        when(exerciseViewModel.getTextValues()).thenReturn(textValuesLiveMock);


        Map<ExerciseViewModel.ExerciseIntegerFields, Integer> numericalValues = new HashMap<>();
        numericalValues.put(ExerciseViewModel.ExerciseIntegerFields.NUMBER_OF_SERIES, 10);
        numericalValues.put(ExerciseViewModel.ExerciseIntegerFields.VOLUME, 5);

        LiveData<Map<ExerciseViewModel.ExerciseIntegerFields, Integer>> numericalLiveMock =
                new MutableLiveData<>(numericalValues);
        when(exerciseViewModel.getNumericalValues()).thenReturn(numericalLiveMock);

        LiveData<Level> levelLiveMock = new MutableLiveData<>(Level.ADVANCED);
        when(exerciseViewModel.getLevel()).thenReturn(levelLiveMock);

        LiveData<FromWhere> fromWhereLiveMock = new MutableLiveData<>(FromWhere.APPLICATION);
        when(exerciseViewModel.getFromWhere()).thenReturn(fromWhereLiveMock);

        LiveData<Integer> userId = new MutableLiveData<>(1);
        when(exerciseViewModel.getUserId()).thenReturn(userId);

        LiveData<ExerciseType> exerciseType = new MutableLiveData<>(ExerciseType.REPETITION);
        when(exerciseViewModel.getExerciseType()).thenReturn(exerciseType);
    }
}