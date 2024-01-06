package com.example.exerciseapp.Exercise.CreateExercise;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.example.exerciseapp.mEnums.BodyParts;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicExerciseInformationTest {

    private final BasicExerciseInformation basicExerciseInformation = new BasicExerciseInformation();

    @Test
    public void shouldReturnFilledMap() {

//        given
        List<Integer> selectedPositions = Arrays.asList(0, 3, 2);

        Map<BodyParts, Boolean> expectedMap = new HashMap<>();
        expectedMap.put(BodyParts.CHEST, true);
        expectedMap.put(BodyParts.BACK, false);
        expectedMap.put(BodyParts.SHOULDERS, true);
        expectedMap.put(BodyParts.ARMS, true);
        expectedMap.put(BodyParts.ABS, false);
        expectedMap.put(BodyParts.LEGS, false);
        expectedMap.put(BodyParts.FULL_BODY, false);

//        when
        Map<BodyParts, Boolean> actualMap = basicExerciseInformation.setBodyPartsMap(selectedPositions);

//        then
        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void shouldThrowAnErrorWhenNumberOnTheListIsOffTheScale() {

//        given
        List<Integer> selectedPositions = Arrays.asList(0, 12, 2);

//        when
//        then
        assertThrows(IllegalArgumentException.class, () ->
                basicExerciseInformation.setBodyPartsMap(selectedPositions));
    }

    @Test
    public void shouldThrowAnErrorWhenPassingListIsNull() {

//        given
//        when
//        then
        assertThrows(IllegalArgumentException.class, () ->
                basicExerciseInformation.setBodyPartsMap(null));
    }
}