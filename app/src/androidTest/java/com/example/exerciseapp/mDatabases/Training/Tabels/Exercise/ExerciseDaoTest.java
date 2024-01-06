package com.example.exerciseapp.mDatabases.Training.Tabels.Exercise;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.exerciseapp.mDatabases.Training.TrainingDatabase;
import com.example.exerciseapp.mEnums.FromWhere;
import com.example.exerciseapp.mEnums.Level;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ExerciseDaoTest {

    private ExerciseDao exerciseDao;
    private TrainingDatabase db;

    @Before
    public void createDb() {

        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TrainingDatabase.class).build();
        exerciseDao = db.exerciseDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeExercise() throws Exception {

//        given
        ExerciseModel exercise = getExerciseModel(1, "Exercise name", "Exercise image",
                Level.BEGINNER, "1,2,3", 25, 35, "Exercise description",
                FromWhere.USER, 1, 5L);
        exerciseDao.createExercise(exercise);

//        when
        ExerciseModel exerciseFromDb = exerciseDao.getExerciseById(1);

//        then
        assertThat(exerciseFromDb, equalTo(exercise));
    }

    @Test
    public void shouldReturnAllExercises() {

//        given
        ExerciseModel exerciseNO1 = getExerciseModel(1, "Exercise name NO.1", "Exercise image NO. 1",
                Level.BEGINNER, "3,2,1", 3, 50, "Exercise description NO. 1",
                FromWhere.USER, 2, 5L);

        ExerciseModel exerciseNO2 = getExerciseModel(2, "Exercise name NO.2", "Exercise image NO. 2",
                Level.ADVANCED, "1,2,3", 25, 35, "Exercise description NO. 2",
                FromWhere.APPLICATION, 2, 3L);

        List<ExerciseModel> exerciseList = Arrays.asList(exerciseNO1, exerciseNO2);

        exerciseDao.createExercise(exerciseNO1);
        exerciseDao.createExercise(exerciseNO2);

//        when
        List<ExerciseModel> exerciseListFromDb = exerciseDao.getAll();

//        then
        assertThat(exerciseListFromDb, containsInAnyOrder(exerciseList.toArray()));
        assertEquals(exerciseListFromDb.size(), exerciseList.size());
    }

    @Test
    public void shouldRemoveTheExercise() {

//        given
        ExerciseModel exerciseNO1 = getExerciseModel(1, "Exercise name NO.1", "Exercise image NO. 1",
                Level.BEGINNER, "3,2,1", 3, 50, "Exercise description NO. 1",
                FromWhere.USER, 2, 5L);

        ExerciseModel exerciseNO2 = getExerciseModel(2, "Exercise name NO.2", "Exercise image NO. 2",
                Level.ADVANCED, "1,2,3", 25, 35, "Exercise description NO. 2",
                FromWhere.APPLICATION, 2, 3L);

        ExerciseModel exerciseNO3 = getExerciseModel(3, "Exercise name NO.3", "Exercise image NO. 3",
                Level.ADVANCED, "1,2,3", 25, 35, "Exercise description NO. 3",
                FromWhere.APPLICATION, 2, 3L);

        List<ExerciseModel> exerciseList = Arrays.asList(exerciseNO2, exerciseNO3);

        exerciseDao.createExercise(exerciseNO1);
        exerciseDao.createExercise(exerciseNO2);
        exerciseDao.createExercise(exerciseNO3);

//        when
        exerciseDao.delete(exerciseNO1);

//        then
        List<ExerciseModel> exerciseListFromDb = exerciseDao.getAll();
        assertThat(exerciseListFromDb, containsInAnyOrder(exerciseList.toArray()));
    }

    @NonNull
    private ExerciseModel getExerciseModel(int id, String name, String image, Level level,
                                           String equipmentIds, int kcal, int duration,
                                           String description, FromWhere fromWhere, Integer userId,
                                           Long bodyParts) {
        ExerciseModel exercise = new ExerciseModel();
        exercise.setId(id);
        exercise.setName(name);
        exercise.setImage(image);
        exercise.setLevel(level);
        exercise.setEquipmentIds(equipmentIds);
        exercise.setKcal(kcal);
        exercise.setDuration(duration);
        exercise.setDescription(description);
        exercise.setFromWhere(fromWhere);
        exercise.setUserId(userId);
        exercise.setBodyParts(bodyParts);
        return exercise;
    }
}