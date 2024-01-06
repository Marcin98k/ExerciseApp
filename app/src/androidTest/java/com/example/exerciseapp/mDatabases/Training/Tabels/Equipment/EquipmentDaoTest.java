package com.example.exerciseapp.mDatabases.Training.Tabels.Equipment;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.exerciseapp.mDatabases.Training.TrainingDatabase;
import com.example.exerciseapp.mEnums.EquipmentType;
import com.example.exerciseapp.mEnums.VolumeType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EquipmentDaoTest {

    private EquipmentDao equipmentDao;
    private TrainingDatabase db;

    @Before
    public void createDb() {

        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TrainingDatabase.class).build();
        equipmentDao = db.equipmentDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeEquipment() {

//        given
        EquipmentModel equipment = createEquipment(1, "Equipment name NO.1", "Equipment image NO.1",
                EquipmentType.FREE, VolumeType.WEIGHT);
        equipmentDao.createEquipment(equipment);

//        when
        EquipmentModel equipmentFromDb = equipmentDao.getEquipmentById(1);

//        then
        assertThat(equipmentFromDb, equalTo(equipment));
    }

    @Test
    public void shouldReturnEquipmentListById() {

//        given
        int[] equipmentTab = {1, 3};

        List<EquipmentModel> equipmentList = equipmentList();
        List<EquipmentModel> expectedEquipmentList = new ArrayList<>();

        for (int i = 0; i < equipmentList.size(); i++) {
            equipmentDao.createEquipment(equipmentList.get(i));

            if (i == 1) {
                continue;
            }
            expectedEquipmentList.add(equipmentList.get(i));
        }

//        when
        List<EquipmentModel> equipmentListFromDb = equipmentDao.getListEquipmentByIds(equipmentTab);

//        then
        assertThat(expectedEquipmentList, containsInAnyOrder(equipmentListFromDb.toArray()));
    }

    @Test
    public void shouldReturnListEquipmentByType() {

//        given
        List<EquipmentModel> equipmentList = equipmentList();
        List<EquipmentModel> expectedEquipmentList = new ArrayList<>();

        for (int i = 0; i < equipmentList.size(); i++) {
            equipmentDao.createEquipment(equipmentList.get(i));

            if (i == 0) {
                continue;
            }
            expectedEquipmentList.add(equipmentList.get(i));
        }

//        when
        List<EquipmentModel> equipmentListFromDb = equipmentDao.getListEquipmentByType(EquipmentType.FREE);

//        then
        assertThat(expectedEquipmentList, containsInAnyOrder(equipmentListFromDb.toArray()));
    }

    @Test
    public void shouldReturnListEquipmentByVolumeType() {

//        given
        List<EquipmentModel> equipmentList = equipmentList();
        List<EquipmentModel> expectedEquipmentList = new ArrayList<>();

        for (int i = 0; i < equipmentList.size(); i++) {
            equipmentDao.createEquipment(equipmentList.get(i));

            if (i == 1) {
                continue;
            }
            expectedEquipmentList.add(equipmentList.get(i));
        }

//        when
        List<EquipmentModel> equipmentListFromDb = equipmentDao.getListEquipmentByVolumeType(VolumeType.WEIGHT);

//        then
        assertThat(expectedEquipmentList, containsInAnyOrder(equipmentListFromDb.toArray()));
    }

    private List<EquipmentModel> equipmentList() {

        EquipmentModel equipment1 = createEquipment(1, "Equipment name NO.1", "Equipment image NO.1",
                EquipmentType.STATIC, VolumeType.WEIGHT);

        EquipmentModel equipment2 = createEquipment(2, "Equipment name NO.2", "Equipment image NO.2",
                EquipmentType.FREE, VolumeType.SPEED);

        EquipmentModel equipment3 = createEquipment(3, "Equipment name NO.3", "Equipment image NO.3",
                EquipmentType.FREE, VolumeType.WEIGHT);

        return Arrays.asList(equipment1, equipment2, equipment3);
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