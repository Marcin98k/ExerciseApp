package com.example.exerciseapp.mDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.exerciseapp.mModels.LanguageModel;

public class ApplicationRepository extends SQLiteOpenHelper {

    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String IMAGE = "IMAGE";
    private static final String USER_ID = "USER_ID";
    private static final String STATUS = "STATUS";

    //    Language table
    static final String LANGUAGE_TABLE = "LANGUAGE";
    private static final String PREFIX = "PREFIX";

    public ApplicationRepository(@Nullable Context context) {
        super(context, "GeneralProperties.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createLanguageTable());
    }

    private String createLanguageTable() {
        return "CREATE TABLE " + LANGUAGE_TABLE + " (" + ID + " INTEGER PRIMARY KEY, "
                + USER_ID + " INTEGER, " + NAME + " TEXT, " + IMAGE + " TEXT, " + PREFIX + " TEXT, "
                + STATUS + " INTEGER)";
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    boolean createLanguage(LanguageModel languageModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues cv = new ContentValues();

            cv.put(USER_ID, languageModel.getUserId());
            cv.put(NAME, languageModel.getName());
            cv.put(IMAGE, languageModel.getImage());
            cv.put(PREFIX, languageModel.getPrefix());
            cv.put(STATUS, languageModel.isStatus());

            return db.insert(LANGUAGE_TABLE, null, cv) != -1;
        }
    }

    public boolean switchLanguage(long oldLanguage, long newLanguage) {

        String cause = ID + " == ?";
        String[] oldArgs = {String.valueOf(oldLanguage)};
        String[] newArgs = {String.valueOf(newLanguage)};

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {

            ContentValues addVal = new ContentValues();
            ContentValues removeVal = new ContentValues();
            addVal.put(STATUS, 1);
            removeVal.put(STATUS, 0);
            int updateOld = db.update(LANGUAGE_TABLE, removeVal, cause, oldArgs);
            int updateNew = db.update(LANGUAGE_TABLE, addVal, cause, newArgs);

            db.setTransactionSuccessful();
            return (updateOld != -1 && updateNew != -1);
        } finally {
            db.endTransaction();
            db.close();
        }
    }

}
