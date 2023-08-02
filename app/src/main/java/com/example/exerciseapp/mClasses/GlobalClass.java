package com.example.exerciseapp.mClasses;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.exerciseapp.mDatabases.DBHelper;
import com.example.exerciseapp.mModels.LanguageModel;

import java.util.Locale;

public class GlobalClass {
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
