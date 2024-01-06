package com.example.exerciseapp.mDatabases.Training.Tabels.ExtensionExercises;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.exerciseapp.mDatabases.Training.Tabels.Exercise.ExerciseDao;
import com.example.exerciseapp.mDatabases.Training.Tabels.Exercise.ExerciseModel;
import com.example.exerciseapp.mDatabases.Training.TrainingDatabase;
import com.example.exerciseapp.mDatabases.Training.TrainingRepository;
import com.example.exerciseapp.mEnums.ExerciseType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ExtensionExercisesDaoTest {

    private ExtensionExercisesDao extensionExercisesDao;
    private ExerciseDao exerciseDao;
    private TrainingDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TrainingDatabase.class).build();
        extensionExercisesDao = db.extensionExercisesDao();
        exerciseDao = db.exerciseDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeExtension() throws Exception {

//        given
        ExerciseModel exerciseModel = new ExerciseModel();
        exerciseModel.setId(1);
        exerciseModel.setName("name");
        exerciseDao.createExercise(exerciseModel);

        ExtensionExercisesModel extension = new ExtensionExercisesModel();
        extension.setId(1);
        extension.setExerciseId(1);
        extension.setExerciseType(ExerciseType.REPETITION);
        extension.setNumberOfSeries(5);
        extension.setVolume(12);
        extension.setBreakLength(35);

        extensionExercisesDao.createExtensionOfExercises(extension);

//        when
        ExtensionExercisesModel extensionFromDb = extensionExercisesDao.getExtensionById(1);

//        then
        assertThat(extensionFromDb, equalTo(extension));
    }
}