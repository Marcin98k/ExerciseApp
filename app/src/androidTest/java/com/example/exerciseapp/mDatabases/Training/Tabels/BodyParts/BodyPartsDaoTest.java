package com.example.exerciseapp.mDatabases.Training.Tabels.BodyParts;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.exerciseapp.mDatabases.Training.TrainingDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class BodyPartsDaoTest {

    private BodyPartsDao bodyPartsDao;
    private TrainingDatabase db;

    @Before
    public void createDb() {

        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TrainingDatabase.class).build();
        bodyPartsDao = db.bodyPartsDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeBodyPartsCombination() {

//        given
        BodyPartsModel bodyParts = new BodyPartsModel();
        bodyParts.setId(1);
        bodyParts.setChestStatus(true);
        bodyParts.setBackStatus(false);
        bodyParts.setShouldersStatus(false);
        bodyParts.setArmsStatus(true);
        bodyParts.setABSStatus(false);
        bodyParts.setLegsStatus(false);
        bodyPartsDao.createBodyPartsCombination(bodyParts);

//        when
        BodyPartsModel bodyPartsFromDb = bodyPartsDao.getBodyPartsCombinationById(1);

//        then
        assertThat(bodyPartsFromDb, equalTo(bodyParts));
    }
}