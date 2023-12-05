package com.example.exerciseapp.mDatabases.Training.Tabels.Workout;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.exerciseapp.mDatabases.Training.TrainingDatabase;
import com.example.exerciseapp.mEnums.FromWhere;
import com.example.exerciseapp.mEnums.Level;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class WorkoutDaoTest {

    private WorkoutDao workoutDao;
    private TrainingDatabase db;

    @Before
    public void createDb() {

        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TrainingDatabase.class).build();
        workoutDao = db.workoutDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeWorkoutAndReadIntList() throws Exception {

//        given
        WorkoutModel workout = new WorkoutModel();
        workout.setId(1);
        workout.setName("Workout name");
        workout.setImage("img_url");
        workout.setLevel(Level.BEGINNER);
        workout.setEquipmentIds("3,5,2");
        workout.setKcal(25);
        workout.setDuration(25);
        workout.setDescription("desc_for_work");
        workout.setFromWhere(FromWhere.USER);
        workout.setUserId(1);
        workout.setExercisesIDs("1,23,5");
        workout.setBodyPartsIDs("1,2,8");
        workoutDao.createWorkout(workout);

//        when
        WorkoutModel workoutFromDb = workoutDao.getWorkoutById(1);

//        then
        assertThat(workout, equalTo(workoutFromDb));
    }
}