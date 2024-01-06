package com.example.exerciseapp.mDatabases.Training;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.exerciseapp.mDatabases.Training.Tabels.Equipment.EquipmentDao;
import com.example.exerciseapp.mDatabases.Training.Tabels.Equipment.EquipmentModel;
import com.example.exerciseapp.mDatabases.Training.Tabels.Equipment.EquipmentVolumeDao;
import com.example.exerciseapp.mDatabases.Training.Tabels.Equipment.EquipmentVolumeModel;
import com.example.exerciseapp.mDatabases.Training.Tabels.Equipment.VolumeDao;
import com.example.exerciseapp.mDatabases.Training.Tabels.Equipment.VolumeModel;
import com.example.exerciseapp.mEnums.EquipmentType;
import com.example.exerciseapp.mEnums.VolumeType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TrainingRepositoryTest {

    private static final int EXPECTED_ERROR_NUMBER = 1;
    private EquipmentDao equipmentDao;
    private VolumeDao volumeDao;
    private EquipmentVolumeDao equipmentVolumeDao;
    private TrainingDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TrainingDatabase.class).build();
        equipmentDao = db.equipmentDao();
        volumeDao = db.volumeDao();
        equipmentVolumeDao = db.equipmentVolumeDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void testCreateEquipmentVolume() {

//        given
        EquipmentModel equipmentModel = createEquipment(2, "name", "image",
                EquipmentType.FREE, VolumeType.WEIGHT);
        equipmentDao.createEquipment(equipmentModel);

        VolumeModel volumeModel = createVolume(3, 15);
        volumeDao.createVolume(volumeModel);

        EquipmentVolumeModel equipmentVolume = createEquipmentVolume(1, 2, 3);
        equipmentVolumeDao.createEquipmentVolume(equipmentVolume);

//        when
        EquipmentVolumeModel equipmentVolumeFromDb = equipmentVolumeDao.getEquipmentVolumeById(1);

//        then
        assertThat(equipmentVolumeFromDb, equalTo(equipmentVolume));
    }

    @Test
    public void shouldReturnErrorWhenMissingForeignKeyCreateEquipmentVolume() {

//        given
        EquipmentModel equipmentModel = createEquipment(2, "name", "image",
                EquipmentType.FREE, VolumeType.WEIGHT);
        equipmentDao.createEquipment(equipmentModel);

        int number = 0;
        EquipmentVolumeModel equipmentVolume = createEquipmentVolume(1, 2, 3);

//        when
        try {
            equipmentVolumeDao.createEquipmentVolume(equipmentVolume);
        }
//        then
        catch (SQLiteConstraintException e) {
            number = EXPECTED_ERROR_NUMBER;
        }

        assertEquals(EXPECTED_ERROR_NUMBER, number);
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

    private VolumeModel createVolume(int id, int volume) {

        VolumeModel volumeModel = new VolumeModel();
        volumeModel.setId(id);
        volumeModel.setVolume(volume);

        return volumeModel;
    }

    private EquipmentVolumeModel createEquipmentVolume(int id, int equipmentId, int volumeId) {

        EquipmentVolumeModel equipmentVolume = new EquipmentVolumeModel();
        equipmentVolume.setId(id);
        equipmentVolume.setEquipmentId(equipmentId);
        equipmentVolume.setVolumeId(volumeId);

        return equipmentVolume;
    }
}