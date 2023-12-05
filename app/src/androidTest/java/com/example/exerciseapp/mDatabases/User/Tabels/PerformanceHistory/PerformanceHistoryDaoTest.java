package com.example.exerciseapp.mDatabases.User.Tabels.PerformanceHistory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.exerciseapp.mDatabases.User.UserDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PerformanceHistoryDaoTest {

    private PerformanceHistoryDao performanceHistoryDao;
    private UserDatabase db;

    @Before
    public void createDb() {

        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase.class).build();
        performanceHistoryDao = db.performanceHistoryDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writePerformanceHistory() {

//        given
        PerformanceHistoryModel performance = createPerformanceHistory(1, 1, 5, 10, 15, 20);
        performanceHistoryDao.createPerformanceHistory(performance);

//        when
        PerformanceHistoryModel performanceFromDb = performanceHistoryDao.getPerformanceHistoryById(1);

//        then
        assertThat(performanceFromDb, equalTo(performance));
    }

    @Test
    public void shouldReturnAllPerformanceHistoryByUserId() {

//        given
        PerformanceHistoryModel performance1 = createPerformanceHistory(1, 1, 5, 10, 15, 20);
        performanceHistoryDao.createPerformanceHistory(performance1);

        PerformanceHistoryModel performance2 = createPerformanceHistory(2, 2, 5, 10, 15, 20);
        performanceHistoryDao.createPerformanceHistory(performance2);

        PerformanceHistoryModel performance3 = createPerformanceHistory(3, 1, 20, 15, 10, 5);
        performanceHistoryDao.createPerformanceHistory(performance3);

        List<PerformanceHistoryModel> expectedPerformanceHistoryList = Arrays.asList(
                performance1, performance3
        );

//        when
        List<PerformanceHistoryModel> performanceHistoryList = performanceHistoryDao
                .getAllPerformanceHistoryByUserId(1);

//        then
        assertThat(performanceHistoryList, containsInAnyOrder(expectedPerformanceHistoryList.toArray()));
    }

    private PerformanceHistoryModel createPerformanceHistory(int id, int userId, int push, int pull,
                                                             int squad, int dip) {

        PerformanceHistoryModel performance = new PerformanceHistoryModel();
        performance.setId(id);
        performance.setUserId(userId);
        performance.setPush(push);
        performance.setPull(pull);
        performance.setSquad(squad);
        performance.setDip(dip);

        return performance;
    }
}