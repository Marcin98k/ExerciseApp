package com.example.exerciseapp.mDatabases.User.Tabels.Goals;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.exerciseapp.mDatabases.User.UserDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class GoalsDaoTest {

    private GoalsDao goalsDao;
    private UserDatabase db;

    @Before
    public void createDb() {

        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase.class).build();
        goalsDao = db.goalsDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeGoals() {

//        given
        GoalsModel goal = createGoals(1, true, false, false, true);
        goalsDao.createGoals(goal);

//        when
        GoalsModel goalFromDb = goalsDao.getGoalsById(1);

//        then
        assertThat(goalFromDb, equalTo(goal));
    }

    private GoalsModel createGoals(int id, boolean strength, boolean muscle, boolean fatLose,
                                   boolean technique) {

        GoalsModel goals = new GoalsModel();
        goals.setId(id);
        goals.setStrength(strength);
        goals.setMuscle(muscle);
        goals.setFatLose(fatLose);
        goals.setTechnique(technique);

        return goals;
    }
}