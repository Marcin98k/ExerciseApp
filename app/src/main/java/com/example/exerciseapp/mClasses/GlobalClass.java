package com.example.exerciseapp.mClasses;

import android.content.Context;
import android.content.res.Configuration;

import com.example.exerciseapp.mDatabases.DBHelper;
import com.example.exerciseapp.mModels.LanguageModel;

import java.util.Locale;

public class GlobalClass {

    public static final int MY_PERMISSIONS_REQUEST_POST_NOTIFICATIONS = 1;
    public static final int DEFAULT_EXERCISE_TIME = 60;
    public static String userID = "userID";
    public static final int FOURTH_VAL = 0;

    public static Context initLanguage(Context context) {

        String languagePrefix;
        try (DBHelper dbHelper = new DBHelper(context)) {
            languagePrefix = dbHelper.showLanguage().stream()
                    .filter(LanguageModel::getStatus)
                    .map(LanguageModel::getPrefix)
                    .findFirst()
                    .orElse("en");
        }

        Locale locale = new Locale(languagePrefix);
        Locale.setDefault(locale);
        Configuration config = context.getResources().getConfiguration();
        config.setLocale(locale);
        return context.createConfigurationContext(config);
    }
}
