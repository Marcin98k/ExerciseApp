package com.example.exerciseapp.mClasses;

import android.content.Context;
import android.content.res.Configuration;

import com.example.exerciseapp.mDatabases.DBHelper;
import com.example.exerciseapp.mModels.LanguageModelToChange;

import java.util.Locale;

public class GlobalClass {

    public static final int MY_PERMISSIONS_REQUEST_POST_NOTIFICATIONS = 1;
    public static final int DEFAULT_EXERCISE_TIME = 60;
    public static String userID = "userID";
    public static final int FOURTH_VAL = 0;

    //    LinearListFragment == TELL
    public static final String MAIN_LIST_NAME = "tagTELL_main";
    public static final String LANGUAGE_LIST_NAME = "tagTELL_language";
    public static final String ACCOUNT_LIST_NAME = "tagTELL_account";
    public static final String NOTIFICATION_LIST_NAME = "tagTELL_notification";
    public static final String PROFILE_LIST_NAME = "tagTELL_profile";
    public static final String INFORMATION_NAME = "userInformation";
    public static final String GOALS_NAME = "userGoals";
    public static final String PERFORMANCE_NAME = "userPerformance";
    public static final String LEVEL_NAME = "userLevel";
    public static final String GENDER_NAME = "userGender";

    public static Context initLanguage(Context context) {
        String languagePrefix = getLanguagePrefix(context);
        return updateLocale(context, languagePrefix);
    }

    private static String getLanguagePrefix(Context context) {
        try (DBHelper dbHelper = new DBHelper(context)) {
            return dbHelper.showLanguage().stream()
                    .filter(LanguageModelToChange::getStatus)
                    .map(LanguageModelToChange::getPrefix)
                    .findFirst()
                    .orElse("en");
        }
    }

    private static Context updateLocale(Context context, String languagePrefix) {
        Locale locale = new Locale(languagePrefix);
        Locale.setDefault(locale);
        Configuration config = context.getResources().getConfiguration();
        config.setLocale(locale);
        return context.createConfigurationContext(config);
    }
}
