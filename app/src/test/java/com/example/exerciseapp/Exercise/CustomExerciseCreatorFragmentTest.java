package com.example.exerciseapp.Exercise;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.exerciseapp.mDatabases.ContentDB;
import com.example.exerciseapp.mModels.ExtensionExerciseModel;
import com.example.exerciseapp.mModels.TrainingModel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomExerciseCreatorFragmentTest {

    private ContentDB contentDB;

    @BeforeEach
    public void init() {
        contentDB = mock(ContentDB.class);
    }

    @AfterEach
    public void close() {
        contentDB.close();
    }

    @Test
    public void shouldThrowErrorWhenCreateExerciseTKWithEmptyName() {

//        given
        TrainingModel trainingModel = mock(TrainingModel.class);
        ExtensionExerciseModel extensionExerciseModel = mock(ExtensionExerciseModel.class);

//        when
//        then
        assertThrows(IllegalArgumentException.class,
                () -> new CustomExerciseCreatorFragment()
                        .createExerciseTK("", trainingModel, extensionExerciseModel));
    }

    @Test
    public void testDoesExerciseExist() {

//        given
        String exerciseName = "testExercise";
        CustomExerciseCreatorFragment fragment = mock(CustomExerciseCreatorFragment.class);

//        when
        when(fragment.doesExerciseExist(exerciseName)).thenReturn(true);
        when(contentDB.searchByName("EXERCISE_TAB", "NAME", exerciseName))
                .thenReturn(true);
        boolean result = fragment.doesExerciseExist(exerciseName);

//        then
        assertTrue(result);
    }

    @Test
    public void testDoesExerciseNoExist() {

//        given
        String exerciseName = "testExercise";
        CustomExerciseCreatorFragment fragment = mock(CustomExerciseCreatorFragment.class);

//        when
        when(fragment.doesExerciseExist(exerciseName)).thenReturn(false);
        when(contentDB.searchByName("EXERCISE_TAB", "NAME", exerciseName))
                .thenReturn(false);
        boolean result = fragment.doesExerciseExist(exerciseName);

//        then
        assertFalse(result);
    }
}