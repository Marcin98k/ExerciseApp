package com.example.exerciseapp.mDatabases.General.Tabels.Language;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.exerciseapp.mDatabases.General.GeneralDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class LanguageDaoTest {


    private LanguageDao languageDao;
    private GeneralDatabase db;

    @Before
    public void createDb() {

        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, GeneralDatabase.class).build();
        languageDao = db.languageDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeLanguageAndReadIntList() throws Exception {

//        given
        LanguageModel language = createLanguageModel(1, 1, "English",
                "image_url", "en", true);
        languageDao.createLanguage(language);

//        when
        LanguageModel byId = languageDao.getLanguageById(1);

//        then
        assertThat(byId, equalTo(language));
    }

    @Test
    public void shouldSwitchLanguage() {

//        given
        LanguageModel createActiveLanguage = createLanguageModel(1, 1, "English",
                "image_url_en", "en", true);
        languageDao.createLanguage(createActiveLanguage);

        LanguageModel createInactiveLanguage = createLanguageModel(2, 1, "Polish",
                "image_url_pl", "pl", false);
        languageDao.createLanguage(createInactiveLanguage);

        LanguageModel createdActiveLanguage = languageDao.getLanguageById(1);
        LanguageModel createdInactiveLanguage = languageDao.getLanguageById(2);

        assertThat(createActiveLanguage, equalTo(createdActiveLanguage));
        assertThat(createInactiveLanguage, equalTo(createdInactiveLanguage));

//        when
        languageDao.switchLanguage(createActiveLanguage.getId(), createInactiveLanguage.getId());

//        then
        LanguageModel finallyInactiveLanguage = languageDao.getLanguageById(1);
        LanguageModel finallyActiveLanguage = languageDao.getLanguageById(2);

        assertFalse(finallyInactiveLanguage.isStatus());
        assertTrue(finallyActiveLanguage.isStatus());
    }

    @Test
    public void shouldThrowAnExceptionWhenTheLanguageWithTheGivenIdDoesNotExist() {

//        given
        LanguageModel createActiveLanguage = createLanguageModel(1, 1, "English",
                "image_url_en", "en", true);
        languageDao.createLanguage(createActiveLanguage);

        LanguageModel createInactiveLanguage = createLanguageModel(2, 1, "Polish",
                "image_url_pl", "pl", false);
        languageDao.createLanguage(createInactiveLanguage);

        LanguageModel createdActiveLanguage = languageDao.getLanguageById(1);
        LanguageModel createdInactiveLanguage = languageDao.getLanguageById(2);

        assertThat(createActiveLanguage, equalTo(createdActiveLanguage));
        assertThat(createInactiveLanguage, equalTo(createdInactiveLanguage));

//        when
//        then
        assertThrows(IllegalArgumentException.class, () -> {
            languageDao.switchLanguage(createActiveLanguage.getId(), -1);
        });
    }

    private LanguageModel createLanguageModel(int id, int userId, String name, String image,
                                              String prefix, boolean status) {
        LanguageModel language = new LanguageModel();
        language.setId(id);
        language.setUserId(userId);
        language.setName(name);
        language.setImage(image);
        language.setPrefix(prefix);
        language.setStatus(status);

        return language;
    }
}