package com.example.exerciseapp.mDatabases.User.Tabels.BasicInformation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.exerciseapp.mDatabases.User.UserDatabase;
import com.example.exerciseapp.mEnums.AccountStatus;
import com.example.exerciseapp.mEnums.Gender;
import com.example.exerciseapp.mEnums.Level;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class BasicUserInformationDaoTest {

    private BasicUserInformationDao basicUserInformationDao;
    private UserDatabase db;

    @Before
    public void createDb() {

        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase.class).build();
        basicUserInformationDao = db.basicInformationDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeBasicUserInformation() {

//        given
        BasicUserInformationModel basicUserInformation = createBasicUserInformationModel(
                1, "Username NO. 1", "Image NO. 1", "Email NO. 1", "Password No. 1",
                "Token NO. 1", Gender.ELSE, 1, 1, Level.BEGINNER, false,
                AccountStatus.ACTIVE
        );

//        when
        basicUserInformationDao.createBasicInformation(basicUserInformation);

        BasicUserInformationModel basicUserInformationFromDb = basicUserInformationDao.getBasicUserInformation(1);

//        then
        assertThat(basicUserInformationFromDb, equalTo(basicUserInformation));
    }

    private BasicUserInformationModel createBasicUserInformationModel(int id, String name,
                                                                      String image, String email,
                                                                      String password, String token,
                                                                      Gender gender, int units,
                                                                      int performance, Level level,
                                                                      boolean notification,
                                                                      AccountStatus accountStatus) {
        BasicUserInformationModel information = new BasicUserInformationModel();
        information.setId(id);
        information.setName(name);
        information.setImage(image);
        information.setEmail(email);
        information.setPassword(password);
        information.setToken(token);
        information.setGender(gender);
        information.setUnits(units);
        information.setPerformance(performance);
        information.setLevel(level);
        information.setNotification(notification);
        information.setAccountStatus(accountStatus);
        return information;
    }
}