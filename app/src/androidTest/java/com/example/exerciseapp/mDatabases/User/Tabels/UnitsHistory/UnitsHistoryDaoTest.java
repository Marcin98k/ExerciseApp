package com.example.exerciseapp.mDatabases.User.Tabels.UnitsHistory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.exerciseapp.mDatabases.User.UserDatabase;
import com.example.exerciseapp.mEnums.HeightUnits;
import com.example.exerciseapp.mEnums.WeightUnits;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class UnitsHistoryDaoTest {


    private UnitsHistoryDao unitsHistoryDao;
    private UserDatabase db;

    @Before
    public void createDb() {

        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase.class).build();
        unitsHistoryDao = db.unitsHistoryDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUnitsHistory() {

//        given
        long currentTime = getCurrentTime();

        UnitsHistoryModel units = createUnitHistory(1, 1, 70, 170, HeightUnits.CM, WeightUnits.KG, currentTime);
        unitsHistoryDao.createUnitsHistory(units);

//        when
        UnitsHistoryModel unitsFromDb = unitsHistoryDao.getUnitHistoryById(1);

//        then
        assertThat(unitsFromDb, equalTo(units));
    }

    @Test
    public void shouldReturnAllUnitsHistoryByUserId() {

//        given
        long currentTime = getCurrentTime();

        UnitsHistoryModel units1 = createUnitHistory(1, 1, 70, 170, HeightUnits.IN, WeightUnits.KG, currentTime);
        unitsHistoryDao.createUnitsHistory(units1);

        UnitsHistoryModel units2 = createUnitHistory(2, 2, 75, 150, HeightUnits.CM, WeightUnits.LB, currentTime);
        unitsHistoryDao.createUnitsHistory(units2);

        UnitsHistoryModel units3 = createUnitHistory(3, 2, 90, 190, HeightUnits.FT, WeightUnits.G, currentTime);
        unitsHistoryDao.createUnitsHistory(units3);

        List<UnitsHistoryModel> expectedUnitsHistoryList = Arrays.asList(units2, units3);

//        when
        List<UnitsHistoryModel> unitsHistoryList = unitsHistoryDao.getAllUnitsHistoryByUserId(2);

//        then
        assertThat(unitsHistoryList, containsInAnyOrder(expectedUnitsHistoryList.toArray()));
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }

    private UnitsHistoryModel createUnitHistory(int id, int userId, int height, int weight,
                                                HeightUnits unitHeight, WeightUnits unitWeight,
                                                long timestamp) {

        UnitsHistoryModel units = new UnitsHistoryModel();
        units.setId(id);
        units.setUserId(userId);
        units.setHeight(height);
        units.setWeight(weight);
        units.setUnitHeight(unitHeight);
        units.setUnitWeight(unitWeight);
        units.setTimestamp(timestamp);

        return units;
    }

}