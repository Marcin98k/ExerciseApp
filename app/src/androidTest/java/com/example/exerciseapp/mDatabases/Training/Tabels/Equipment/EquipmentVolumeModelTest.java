package com.example.exerciseapp.mDatabases.Training.Tabels.Equipment;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.exerciseapp.mDatabases.Training.TrainingDatabase;
import com.example.exerciseapp.mEnums.EquipmentType;
import com.example.exerciseapp.mEnums.ExerciseType;
import com.example.exerciseapp.mEnums.VolumeType;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class EquipmentVolumeModelTest {

    private EquipmentVolumeDao equipmentVolumeDao;
    private EquipmentDao equipmentDao;
    private VolumeDao volumeDao;
    private TrainingDatabase db;

    @Before
    public void createDb() {

        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TrainingDatabase.class).build();
        equipmentVolumeDao = db.equipmentVolumeDao();
        equipmentDao = db.equipmentDao();
        volumeDao = db.volumeDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeEquipmentVolume() {

//        given
        EquipmentModel equipmentModel = createEquipment(2, "name", "image",
                EquipmentType.FREE, VolumeType.WEIGHT);
        equipmentDao.createEquipment(equipmentModel);

        VolumeModel volumeModel = createVolume(3, 15);
        volumeDao.createVolume(volumeModel);

        EquipmentVolumeModel equipmentVolume = createEquipmentVolume(1, 2,3);
        equipmentVolumeDao.createEquipmentVolume(equipmentVolume);

//        when
        EquipmentVolumeModel equipmentVolumeFromDb = equipmentVolumeDao.getEquipmentVolumeById(1);

//        then
        assertThat(equipmentVolumeFromDb, equalTo(equipmentVolume));
    }


    private EquipmentVolumeModel createEquipmentVolume(int id, int equipmentId, int volumeId) {

        EquipmentVolumeModel equipmentVolume = new EquipmentVolumeModel();
        equipmentVolume.setId(id);
        equipmentVolume.setEquipmentId(equipmentId);
        equipmentVolume.setVolumeId(volumeId);

        return equipmentVolume;
    }

    private VolumeModel createVolume(int id, int volume) {

        VolumeModel volumeModel = new VolumeModel();
        volumeModel.setId(id);
        volumeModel.setVolume(volume);

        return volumeModel;
    }

    private EquipmentModel createEquipment(int id, String name, String image, EquipmentType equipmentType,
                                           VolumeType volumeType) {

        EquipmentModel equipment = new EquipmentModel();
        equipment.setId(id);
        equipment.setName(name);
        equipment.setImage(image);
        equipment.setEquipmentType(equipmentType);
        equipment.setVolumeType(volumeType);

        return equipment;
    }
}