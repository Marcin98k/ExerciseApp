package com.example.exerciseapp.mDatabases.General.Tabels.Units;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.exerciseapp.mDatabases.General.GeneralDatabase;
import com.example.exerciseapp.mEnums.HeightUnits;
import com.example.exerciseapp.mEnums.WeightUnits;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class AppUnitsDaoTest {

    private AppUnitsDao appUnitsDao;
    private GeneralDatabase db;

    @Before
    public void createDb() {

        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, GeneralDatabase.class).build();
        appUnitsDao = db.appUnitsDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUnits() {

//        given
        AppUnitsModel units = createUnits(1, 1, HeightUnits.CM, WeightUnits.KG);
        appUnitsDao.createAppUnits(units);

//        when
        AppUnitsModel unitsFromDb = appUnitsDao.getAppUnitsByUserId(1);

//        then
        assertThat(unitsFromDb, equalTo(units));
    }

    private AppUnitsModel createUnits(int id, int userId, HeightUnits height, WeightUnits weight) {

        AppUnitsModel units = new AppUnitsModel();
        units.setId(id);
        units.setUserId(userId);
        units.setHeight(height);
        units.setWeight(weight);

        return units;
    }
}