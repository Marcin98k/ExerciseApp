package com.example.exerciseapp.mDatabases.Training.Tabels.Equipment;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.exerciseapp.mDatabases.Training.TrainingDatabase;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class VolumeModelTest extends TestCase {

    private VolumeDao volumeDao;
    private TrainingDatabase db;

    @Before
    public void createDb() {

        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TrainingDatabase.class).build();
        volumeDao = db.volumeDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeVolume() {

//        given
        VolumeModel volume = createVolume(1, 12);
        volumeDao.createVolume(volume);

//        when
        VolumeModel volumeFromDb = volumeDao.getVolumeById(1);

//        then
        assertThat(volumeFromDb, equalTo(volume));
    }

    private VolumeModel createVolume(int id, int volume) {

        VolumeModel volumeModel = new VolumeModel();
        volumeModel.setId(id);
        volumeModel.setVolume(volume);

        return volumeModel;
    }
}