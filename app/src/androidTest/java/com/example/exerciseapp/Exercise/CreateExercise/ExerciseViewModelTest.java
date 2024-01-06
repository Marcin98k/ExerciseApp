package com.example.exerciseapp.Exercise.CreateExercise;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.exerciseapp.mEnums.BodyParts;
import com.example.exerciseapp.mEnums.ExerciseType;
import com.example.exerciseapp.mEnums.FromWhere;
import com.example.exerciseapp.mEnums.Level;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExerciseViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private ExerciseViewModel exerciseViewModel;

    @Before
    public void init() {
        ViewModelStoreOwner storeOwner = Mockito.mock(ViewModelStoreOwner.class);

        when(storeOwner.getViewModelStore()).thenReturn(new ViewModelStore());
        exerciseViewModel = new ViewModelProvider(storeOwner).get(ExerciseViewModel.class);
    }

    @After
    public void removeViewModel() {
        exerciseViewModel = null;
    }

    @Test
    public void shouldSetAndGetLevelValue() {

//        given
        Level level = Level.BEGINNER;
        exerciseViewModel.setLevel(level);

//        when
        Level levelFromViewModel = exerciseViewModel.getLevel().getValue();

//        then
        assertEquals(level, levelFromViewModel);
    }

    @Test
    public void shouldSetAndGetFromWhereValue() {

//        given
        FromWhere fromWhere = FromWhere.USER;
        exerciseViewModel.setFromWhere(fromWhere);

//        when
        FromWhere fromWhereFromViewModel = exerciseViewModel.getFromWhere().getValue();

//        then
        assertEquals(fromWhere, fromWhereFromViewModel);
    }

    @Test
    public void shouldSetAndGetExerciseTypeValue() {

//        given
        ExerciseType exerciseType = ExerciseType.REPETITION;
        exerciseViewModel.setExerciseType(exerciseType);

//        when
        ExerciseType exerciseTypeFromViewModel = exerciseViewModel.getExerciseType().getValue();

//        then
        assertEquals(exerciseType, exerciseTypeFromViewModel);
    }

    @Test
    public void shouldSetAndGetEquipmentIds() {

//        given
        List<Integer> equipmentIds = Arrays.asList(1, 2, 5);
        exerciseViewModel.setEquipmentListIds(equipmentIds);

//        when
        List<Integer> equipmentIdsFromViewModel = exerciseViewModel.getEquipmentIds().getValue();

//        then
        assertEquals(equipmentIds, equipmentIdsFromViewModel);
    }

    @Test
    public void shouldReturnNullWhenEnterAnEmptyList() {

//        given
        List<Integer> equipmentIds = Collections.emptyList();
        exerciseViewModel.setEquipmentListIds(equipmentIds);

//        when
        List<Integer> equipmentIdsFromViewModel = exerciseViewModel.getEquipmentIds().getValue();

//        then
        assertNull(equipmentIdsFromViewModel);
    }

    @Test
    public void shouldReturnEquipmentListVolume() {

//        given
        Map<Integer, Double> equipmentMapVolume = new HashMap<Integer, Double>() {{
            put(1, 2.2);
            put(5, 3.5);
            put(3, 1.00);
        }};
        exerciseViewModel.setEquipmentListVolume(equipmentMapVolume);

//        when
        Map<Integer, Double> equipmentMapVolumeFromViewModel = exerciseViewModel.getEquipmentMapVolume();

//        then
        assertEquals(equipmentMapVolume, equipmentMapVolumeFromViewModel);
    }

    @Test
    public void shouldThrowExceptionWhenValueIsNegativeInEquipmentVolume() {

//        given
        Map<Integer, Double> equipmentMapVolume = new HashMap<Integer, Double>() {{
            put(1, 2.2);
            put(5, -3.5);
            put(3, 1.00);
        }};

//        when
//        then
        assertThrows(IllegalArgumentException.class,
                () -> exerciseViewModel.setEquipmentListVolume(equipmentMapVolume));
    }

    @Test
    public void shouldReturnTextMap() {

//        given
        Map<ExerciseViewModel.ExerciseTextFields, String> expectedTextValues =
                new HashMap<>();
        expectedTextValues.put(ExerciseViewModel.ExerciseTextFields.IMAGE, "");
        expectedTextValues.put(ExerciseViewModel.ExerciseTextFields.NAME, "Name");
        expectedTextValues.put(ExerciseViewModel.ExerciseTextFields.EQUIPMENT, "1,2,3");
        expectedTextValues.put(ExerciseViewModel.ExerciseTextFields.DESCRIPTION, "Description");

        for (Map.Entry<ExerciseViewModel.ExerciseTextFields, String> entry :
                expectedTextValues.entrySet()) {
            exerciseViewModel.setTextValues(entry.getKey(), entry.getValue());
        }

//        when
        Map<ExerciseViewModel.ExerciseTextFields, String> textValuesFromViewModel =
                exerciseViewModel.getTextValues().getValue();

//        then
        assertEquals(expectedTextValues, textValuesFromViewModel);
    }

    @Test
    public void shouldAddNullValuesWhenTextIsNotFilled() {

//        given
        Map<ExerciseViewModel.ExerciseTextFields, String> textValues =
                new HashMap<>();
        textValues.put(ExerciseViewModel.ExerciseTextFields.IMAGE, "Image");
        textValues.put(ExerciseViewModel.ExerciseTextFields.NAME, "Name");

        for (Map.Entry<ExerciseViewModel.ExerciseTextFields, String> entry :
                textValues.entrySet()) {
            exerciseViewModel.setTextValues(entry.getKey(), entry.getValue());
        }

        Map<ExerciseViewModel.ExerciseTextFields, String> expectedTextValues =
                new HashMap<>();
        expectedTextValues.put(ExerciseViewModel.ExerciseTextFields.IMAGE, "Image");
        expectedTextValues.put(ExerciseViewModel.ExerciseTextFields.NAME, "Name");
        expectedTextValues.put(ExerciseViewModel.ExerciseTextFields.EQUIPMENT, null);
        expectedTextValues.put(ExerciseViewModel.ExerciseTextFields.DESCRIPTION, null);

//        when
        Map<ExerciseViewModel.ExerciseTextFields, String> textValuesFromViewModel =
                exerciseViewModel.getTextValues().getValue();

//        then
        assertEquals(expectedTextValues, textValuesFromViewModel);
    }

    @Test
    public void shouldReturnNumericalMap() {

//        given
        Map<ExerciseViewModel.ExerciseIntegerFields, Integer> expectedNumericalValues =
                new HashMap<>();
        expectedNumericalValues.put(ExerciseViewModel.ExerciseIntegerFields.NUMBER_OF_SERIES, 10);
        expectedNumericalValues.put(ExerciseViewModel.ExerciseIntegerFields.VOLUME, 5);
        expectedNumericalValues.put(ExerciseViewModel.ExerciseIntegerFields.BREAK_LENGTH, 15);
        expectedNumericalValues.put(ExerciseViewModel.ExerciseIntegerFields.KCAL, 12);
        expectedNumericalValues.put(ExerciseViewModel.ExerciseIntegerFields.DURATION, 50);
        expectedNumericalValues.put(ExerciseViewModel.ExerciseIntegerFields.USER_ID, 1);

        for (Map.Entry<ExerciseViewModel.ExerciseIntegerFields, Integer> entry :
                expectedNumericalValues.entrySet()) {
            exerciseViewModel.setNumericalValues(entry.getKey(), entry.getValue());
        }

//        when
        Map<ExerciseViewModel.ExerciseIntegerFields, Integer> numericalValuesFromViewModel =
                exerciseViewModel.getNumericalValues().getValue();

//        then
        assertEquals(expectedNumericalValues, numericalValuesFromViewModel);
    }

    @Test
    public void shouldThrowExceptionWhenValueIsNegativeInNumericalValues() {

//        given
        Map<ExerciseViewModel.ExerciseIntegerFields, Integer> expectedNumericalValues =
                new HashMap<>();
        expectedNumericalValues.put(ExerciseViewModel.ExerciseIntegerFields.NUMBER_OF_SERIES, 10);
        expectedNumericalValues.put(ExerciseViewModel.ExerciseIntegerFields.VOLUME, 5);
        expectedNumericalValues.put(ExerciseViewModel.ExerciseIntegerFields.BREAK_LENGTH, -15);

//        when
//        then
        assertThrows(IllegalArgumentException.class, () -> {
            for (Map.Entry<ExerciseViewModel.ExerciseIntegerFields, Integer> entry :
                    expectedNumericalValues.entrySet()) {
                exerciseViewModel.setNumericalValues(entry.getKey(), entry.getValue());
            }
        });
    }

    @Test
    public void shouldReturnBodyPartsMap() {

//        given
        Map<BodyParts, Boolean> expectedBodyPartsValues = new HashMap<>();
        expectedBodyPartsValues.put(BodyParts.CHEST, true);
        expectedBodyPartsValues.put(BodyParts.BACK, false);
        expectedBodyPartsValues.put(BodyParts.SHOULDERS, false);
        expectedBodyPartsValues.put(BodyParts.ARMS, true);
        expectedBodyPartsValues.put(BodyParts.ABS, false);
        expectedBodyPartsValues.put(BodyParts.LEGS, false);
        expectedBodyPartsValues.put(BodyParts.FULL_BODY, false);

        for (Map.Entry<BodyParts, Boolean> entry :
                expectedBodyPartsValues.entrySet()) {
            exerciseViewModel.setBodyPartsValues(entry.getKey(), entry.getValue());
        }

//        when
        Map<BodyParts, Boolean> bodyPartsValuesFromViewModel =
                exerciseViewModel.getBodyPartsValues().getValue();

//        then
        assertEquals(expectedBodyPartsValues, bodyPartsValuesFromViewModel);
    }

    @Test
    public void shouldAddFalseValuesWhenBodyPartsIsNotChecked() {

//        given
        Map<BodyParts, Boolean> bodyPartsValues = new HashMap<>();
        bodyPartsValues.put(BodyParts.BACK, true);
        bodyPartsValues.put(BodyParts.SHOULDERS, true);
        bodyPartsValues.put(BodyParts.ARMS, true);

        for (Map.Entry<BodyParts, Boolean> entry : bodyPartsValues.entrySet()) {
            exerciseViewModel.setBodyPartsValues(entry.getKey(), entry.getValue());
        }

        Map<BodyParts, Boolean> expectedBodyPartsValues = new HashMap<>();
        expectedBodyPartsValues.put(BodyParts.CHEST, false);
        expectedBodyPartsValues.put(BodyParts.BACK, true);
        expectedBodyPartsValues.put(BodyParts.SHOULDERS, true);
        expectedBodyPartsValues.put(BodyParts.ARMS, true);
        expectedBodyPartsValues.put(BodyParts.ABS, false);
        expectedBodyPartsValues.put(BodyParts.LEGS, false);
        expectedBodyPartsValues.put(BodyParts.FULL_BODY, false);

//        when
        Map<BodyParts, Boolean> bodyPartsValuesFromViewModel =
                exerciseViewModel.getBodyPartsValues().getValue();

//        then
        assertEquals(expectedBodyPartsValues, bodyPartsValuesFromViewModel);
    }
}