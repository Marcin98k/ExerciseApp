package com.example.exerciseapp.mDatabases.User.Tabels.Units;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.exerciseapp.mDatabases.User.Tabels.UnitsModel;
import com.example.exerciseapp.mDatabases.User.UserDatabase;
import com.example.exerciseapp.mEnums.HeightUnits;
import com.example.exerciseapp.mEnums.WeightUnits;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class UnitsDaoTest {

    private UnitsDao unitsDao;
    private UserDatabase db;

    @Before
    public void createDb() {

        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase.class).build();
        unitsDao = db.unitsDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUnits() {

//        given
        long currentTime = getCurrentTime();

        UnitsModel units = createUnits(1, 70, 170, HeightUnits.CM, WeightUnits.KG, currentTime);
        unitsDao.createUnits(units);

//        when
        UnitsModel unitsFromDb = unitsDao.getUnitsByID(1);

//        then
        assertThat(unitsFromDb, equalTo(units));
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }

    private UnitsModel createUnits(int id, int height, int weight, HeightUnits unitHeight,
                                   WeightUnits unitWeight, long timestamp) {

        UnitsModel units = new UnitsModel();
        units.setId(id);
        units.setHeight(height);
        units.setWeight(weight);
        units.setUnitHeight(unitHeight);
        units.setUnitWeight(unitWeight);
        units.setTimestamp(timestamp);

        return units;
    }
}