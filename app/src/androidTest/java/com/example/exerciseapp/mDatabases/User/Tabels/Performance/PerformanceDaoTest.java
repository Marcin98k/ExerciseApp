package com.example.exerciseapp.mDatabases.User.Tabels.Performance;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.exerciseapp.mDatabases.User.Tabels.PerformanceModel;
import com.example.exerciseapp.mDatabases.User.UserDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class PerformanceDaoTest {

    private PerformanceDao performanceDao;
    private UserDatabase db;

    @Before
    public void createDb() {

        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase.class).build();
        performanceDao = db.performanceDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writePerformance() {

//        given
        PerformanceModel performance = createPerformance(1, 5, 10, 15, 20);
        performanceDao.createPerformance(performance);

//        when
        PerformanceModel performanceFromDb = performanceDao.getPerformanceById(1);

//        then
        assertThat(performanceFromDb, equalTo(performance));
    }

    private PerformanceModel createPerformance(int id, int push, int pull, int squad, int dip) {

        PerformanceModel performance = new PerformanceModel();
        performance.setId(id);
        performance.setPush(push);
        performance.setPull(pull);
        performance.setSquad(squad);
        performance.setDip(dip);

        return performance;
    }
}