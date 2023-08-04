package com.example.exerciseapp.mDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.exerciseapp.mEnums.RowNames;
import com.example.exerciseapp.mModels.AppearanceBlockModel;
import com.example.exerciseapp.mModels.IntegerModel;
import com.example.exerciseapp.mModels.LanguageModel;
import com.example.exerciseapp.mModels.StringModel;
import com.example.exerciseapp.mModels.UserInformationModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String ID = "ID";
    private static final String STRING_VAL = "STR_VALUE";
    private static final String INT_VAL = "INT_VALUE";
    private static final String IMG_PATH = "IMAGE_PATH";


    private static final String USER_INFORMATION_TAB = "USER_INFORMATION";
    private static final String USER_NAME = "NAME";
    private static final String USER_EMAIL = "EMAIL";
    private static final String USER_PASSWORD = "PASSWORD";
    private static final String USER_GENDER_ID = "GENDER";
    private static final String USER_UNITS_ID = "USER_UNITS";
    private static final String USER_PERFORMANCE_ID = "PERFORMANCE";
    private static final String USER_GOALS_ID = "GOALS";
    private static final String USER_LEVEL_ID = "LEVEL";
    private static final String USER_NOTIFICATION_ID = "NOTIFICATION";


    private static final String USER_GOALS_TAB = "GOALS";
    //    Boolean (INTEGER 0/1);
    private static final String USER_GOAL_STRENGTH = "STRENGTH";
    private static final String USER_GOAL_MUSCLE = "MUSCLE";
    private static final String USER_GOAL_FAT_LOSE = "FAT_LOSE";
    private static final String USER_GOAL_TECHNIQUE = "TECHNIQUE";


    private static final String USER_PERFORMANCE_TAB = "USER_PERFORMANCE";
    private static final String PERFORMANCE_PUSH = "PUSH";
    private static final String PERFORMANCE_PULL = "PULL";
    private static final String PERFORMANCE_SQUAD = "SQUAD";
    private static final String PERFORMANCE_DIP = "DIP";


    private static final String APPEARANCE_TAB = "APPEARANCE";
    private static final String APPEARANCE_ICON = "APP_ICON";
    private static final String APPEARANCE_IMAGE = "APP_IMAGE";
    private static final String APPEARANCE_NAME = "APP_NAME";
    private static final String APPEARANCE_ACTION = "APP_ACTION";
    private static final String APPEARANCE_DESCRIPTION = "DESCRIPTION";
    private static final String APPEARANCE_TAG = "TAG";


    private static final String GENDER_TAB = "GENDER";
    private static final String USER_MALE = "MALE";
    private static final String USER_FEMALE = "FEMALE";
    private static final String USER_OTHER = "OTHER";


    private static final String MIDDLE_TAB = "MIDDLE";
    private static final String MIDDLE_APPEARANCE = "APPEARANCE";


    private static final String USER_UNITS_TAB = "USER_UNITS";
    private static final String USER_HEIGHT = "HEIGHT";
    private static final String USER_WEIGHT = "WEIGHT";
    private static final String USER_UNIT_HEIGHT = "UNIT_HEIGHT";
    private static final String USER_UNIT_WEIGHT = "UNIT_WEIGHT";


    private static final String UNIT_HEIGHT_TAB = "HEIGHT_TAB";

    private static final String UNIT_WEIGHT_TAB = "WEIGHT_TAB";

    private static final String UNIT = "UNIT";


    private static final String LEVEL_TAB = "LEVEL";
    private static final String LEVEL_BEGINNER = "BEGINNER";
    private static final String LEVEL_INTERMEDIATE = "INTERMEDIATE";
    private static final String LEVEL_ADVANCED = "ADVANCED";


    private static final String NOTIFICATIONS_TAB = "NOTIFICATIONS";
    private static final String NOTIFICATIONS_STATUS = "STATUS";
    private static final String NOTIFICATION_DAYS = "DAYS";
    private static final String NOTIFICATION_HOURS = "HOURS";
    private static final String NOTIFICATION_WORKOUT_ID = "WORKOUT";


    private static final String FUTURE_TAB = "FUTURE";
    private static final String FUTURE_DESCRIPTION = "DESCRIPTION";
    //    0 -> Not ready, 1 -> In the process, 2 -> Ready;
    private static final String FUTURE_STATUS = "STATUS";


    private static final String LANGUAGE_TAB = "LANGUAGE";
    private static final String LANGUAGE_PREFIX = "LANGUAGE_PREFIX";


    public DBHelper(@Nullable Context context) {
        super(context, "ExerciseApp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createAppearanceTab = "CREATE TABLE " + APPEARANCE_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + APPEARANCE_ICON + " INTEGER, " + APPEARANCE_IMAGE + " TEXT, "
                + APPEARANCE_NAME + " TEXT, " + APPEARANCE_ACTION + " INTEGER, "
                + APPEARANCE_DESCRIPTION + " TEXT, " + APPEARANCE_TAG + " TEXT);";
        sqLiteDatabase.execSQL(createAppearanceTab);

        String createFutureTab = "CREATE TABLE " + FUTURE_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FUTURE_DESCRIPTION + " TEXT," + FUTURE_STATUS + " INTEGER)";
        sqLiteDatabase.execSQL(createFutureTab);

        String createNotificationsTab = "CREATE TABLE " + NOTIFICATIONS_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NOTIFICATIONS_STATUS + " INTEGER, " + NOTIFICATION_DAYS + " INTEGER, "
                + NOTIFICATION_HOURS + " INTEGER, " + NOTIFICATION_WORKOUT_ID + " INTEGER)";
        sqLiteDatabase.execSQL(createNotificationsTab);

        String createUserInformationTab = "CREATE TABLE " + USER_INFORMATION_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_NAME + " TEXT, " + USER_EMAIL + " TEXT, " + USER_PASSWORD + " TEXT, "
                + USER_GENDER_ID + " INTEGER, " + USER_UNITS_ID + " INTEGER, "
                + USER_PERFORMANCE_ID + " INTEGER, " + USER_GOALS_ID + " INTEGER, "
                + USER_LEVEL_ID + " INTEGER, " + USER_NOTIFICATION_ID + " INTEGER, "
                + " FOREIGN KEY (" + USER_GENDER_ID + ") REFERENCES "
                + MIDDLE_TAB + " (" + ID + "), "
                + " FOREIGN KEY (" + USER_UNITS_ID + ") REFERENCES "
                + USER_UNITS_TAB + " (" + ID + "), "
                + " FOREIGN KEY (" + USER_GOALS_ID + ") REFERENCES "
                + USER_GOALS_TAB + " (" + ID + "), "
                + " FOREIGN KEY (" + USER_LEVEL_ID + ") REFERENCES "
                + LEVEL_TAB + " (" + ID + "), "
                + " FOREIGN KEY (" + USER_NOTIFICATION_ID + ") REFERENCES "
                + NOTIFICATIONS_TAB + " (" + ID + "));";
        sqLiteDatabase.execSQL(createUserInformationTab);

        String createGenderTab = "CREATE TABLE " + GENDER_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_MALE + " INTEGER, " + USER_FEMALE + " INTEGER, "
                + USER_OTHER + " INTEGER);";
        sqLiteDatabase.execSQL(createGenderTab);

        String createGoalsTab = "CREATE TABLE " + USER_GOALS_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_GOAL_STRENGTH + " INTEGER, " + USER_GOAL_MUSCLE + " INTEGER, "
                + USER_GOAL_FAT_LOSE + " INTEGER, " + USER_GOAL_TECHNIQUE + " INTEGER, "
                + " FOREIGN KEY (" + USER_GOAL_STRENGTH + ") REFERENCES "
                + MIDDLE_TAB + " (" + ID + "), "
                + " FOREIGN KEY (" + USER_GOAL_MUSCLE + ") REFERENCES "
                + MIDDLE_TAB + " (" + ID + "), "
                + " FOREIGN KEY (" + USER_GOAL_FAT_LOSE + ") REFERENCES "
                + MIDDLE_TAB + " (" + ID + "), "
                + " FOREIGN KEY (" + USER_GOAL_TECHNIQUE + ") REFERENCES "
                + MIDDLE_TAB + " (" + ID + "));";
        sqLiteDatabase.execSQL(createGoalsTab);

        String createUserPerformanceTab = "CREATE TABLE " + USER_PERFORMANCE_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PERFORMANCE_PUSH + " INTEGER, " + PERFORMANCE_PULL + " INTEGER, "
                + PERFORMANCE_SQUAD + " INTEGER, " + PERFORMANCE_DIP + " INTEGER, "
                + " FOREIGN KEY (" + PERFORMANCE_PUSH + ") REFERENCES "
                + MIDDLE_TAB + " (" + ID + "), "
                + " FOREIGN KEY (" + PERFORMANCE_PULL + ") REFERENCES "
                + MIDDLE_TAB + " (" + ID + "), "
                + " FOREIGN KEY (" + PERFORMANCE_SQUAD + ") REFERENCES "
                + MIDDLE_TAB + " (" + ID + "), "
                + " FOREIGN KEY (" + PERFORMANCE_DIP + ") REFERENCES "
                + MIDDLE_TAB + " (" + ID + "));";
        sqLiteDatabase.execSQL(createUserPerformanceTab);

        String createUserUnitsTab = "CREATE TABLE " + USER_UNITS_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_HEIGHT + " INTEGER, "
                + USER_WEIGHT + " INTEGER, "
                + USER_UNIT_HEIGHT + " INTEGER, "
                + USER_UNIT_WEIGHT + " INTEGER, "
                + " FOREIGN KEY (" + USER_HEIGHT + ") REFERENCES "
                + MIDDLE_TAB + " (" + ID + "), "
                + " FOREIGN KEY (" + USER_WEIGHT + ") REFERENCES "
                + MIDDLE_TAB + " (" + ID + "), "
                + " FOREIGN KEY (" + USER_UNIT_HEIGHT + ") REFERENCES "
                + UNIT_HEIGHT_TAB + " (" + ID + "), "
                + " FOREIGN KEY (" + USER_UNIT_WEIGHT + ") REFERENCES "
                + UNIT_WEIGHT_TAB + " (" + ID + "));";
        sqLiteDatabase.execSQL(createUserUnitsTab);

        String createUnitHeightTab = "CREATE TABLE " + UNIT_HEIGHT_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UNIT + " TEXT);";
        sqLiteDatabase.execSQL(createUnitHeightTab);

        String createUnitWeightTab = "CREATE TABLE " + UNIT_WEIGHT_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UNIT + " TEXT);";
        sqLiteDatabase.execSQL(createUnitWeightTab);

        String createLevelTab = "CREATE TABLE " + LEVEL_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LEVEL_BEGINNER + " INTEGER, " + LEVEL_INTERMEDIATE + " INTEGER, "
                + LEVEL_ADVANCED + " INTEGER, "
                + " FOREIGN KEY (" + LEVEL_BEGINNER + ") REFERENCES "
                + MIDDLE_TAB + " (" + ID + "), "
                + " FOREIGN KEY (" + LEVEL_INTERMEDIATE + ") REFERENCES "
                + MIDDLE_TAB + " (" + ID + "), "
                + " FOREIGN KEY (" + LEVEL_ADVANCED + ") REFERENCES "
                + MIDDLE_TAB + " (" + ID + "));";
        ;
        sqLiteDatabase.execSQL(createLevelTab);

        String createMiddleTab = "CREATE TABLE " + MIDDLE_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + INT_VAL + " INTEGER, " + MIDDLE_APPEARANCE + " INTEGER) ";
        sqLiteDatabase.execSQL(createMiddleTab);

        //        STRING_VAL -> LANGUAGE USER_NAME, INT_VAL -> IS ACTIVE;
        String createLanguageTab = "CREATE TABLE " + LANGUAGE_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + STRING_VAL + " TEXT, " + INT_VAL + " INTEGER, "
                + LANGUAGE_PREFIX + " TEXT, " + IMG_PATH + " TEXT);";
        sqLiteDatabase.execSQL(createLanguageTab);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertMiddleTab(IntegerModel integerModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(INT_VAL, integerModel.getFirstValue());
            values.put(MIDDLE_APPEARANCE, integerModel.getSecondValue());

            return db.insert(MIDDLE_TAB, null, values) != -1;
        }
    }

    public List<IntegerModel> showMiddleTab() {

        List<IntegerModel> list = new ArrayList<>();
        String search = "SELECT * FROM " + MIDDLE_TAB;

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, null)) {
            while (cursor.moveToNext()) {
                IntegerModel integerModel = new IntegerModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(INT_VAL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(MIDDLE_APPEARANCE)));
                list.add(integerModel);
            }
        }
        return list;
    }

    public List<IntegerModel> getMiddleIndex(int id) {

        List<IntegerModel> result = new ArrayList<>();
        String search = "SELECT * FROM " + MIDDLE_TAB +
                " WHERE " + ID + " ==  ?";
        String[] args = {String.valueOf(id)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
            if (cursor.moveToFirst()) {
                IntegerModel integerModel = new IntegerModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(INT_VAL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(MIDDLE_APPEARANCE)));
                result.add(integerModel);
            }
        }
        return result;
    }


    public boolean insertFutureTab(String value, int status) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(FUTURE_DESCRIPTION, value);
            values.put(FUTURE_STATUS, status);

            return db.insert(FUTURE_TAB, null, values) != -1;
        }
    }

    public List<StringModel> showFutureTab() {

        List<StringModel> result = new ArrayList<>();
        String search = "SELECT * FROM " + FUTURE_TAB;

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, null)) {
            while (cursor.moveToNext()) {
                StringModel stringModel = new StringModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(FUTURE_DESCRIPTION)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(FUTURE_STATUS)));
                result.add(stringModel);
            }
        }
        return result;
    }


    public boolean insertGenderTab(IntegerModel integerModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(USER_MALE, integerModel.getFirstValue());
            values.put(USER_FEMALE, integerModel.getSecondValue());
            values.put(USER_OTHER, integerModel.getThirdValue());

            return db.insert(GENDER_TAB, null, values) != -1;
        }
    }

    public List<IntegerModel> getGender(int id) {

        List<IntegerModel> result = new ArrayList<>();
        String search = "SELECT * FROM " + GENDER_TAB +
                " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(id)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
            if (cursor.moveToFirst()) {
                IntegerModel integerModel = new IntegerModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_MALE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_FEMALE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_OTHER)));
                result.add(integerModel);
            }
        }
        return result;
    }

    public boolean switchGender(int oldPos, int newPos) {

        String cause = ID + " == ?";
        String[] oldArgs = {String.valueOf(oldPos)};
        String[] newArgs = {String.valueOf(newPos)};

        try (SQLiteDatabase db = getWritableDatabase()) {
            db.beginTransaction();
            try {
                ContentValues addVal = new ContentValues();
                ContentValues removeVal = new ContentValues();
                addVal.put(INT_VAL, 1);
                removeVal.put(INT_VAL, 0);
                int updateOld = db.update(MIDDLE_TAB, removeVal, cause, oldArgs);
                int updateNew = db.update(MIDDLE_TAB, addVal, cause, newArgs);

                db.setTransactionSuccessful();
                return (updateOld != -1 && updateNew != -1);
            } finally {
                db.endTransaction();
            }
        }
    }


    public boolean insertHeightTab(AppearanceBlockModel appearanceBlockModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(UNIT, appearanceBlockModel.getName());

            return db.insert(UNIT_HEIGHT_TAB, null, values) != -1;
        }
    }

    public String getUnitHeight(int id) {

        String result = "";
        String search = "SELECT * FROM " + UNIT_HEIGHT_TAB +
                " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(id)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
            if (cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndexOrThrow(UNIT));
            }
        }
        return result;
    }


    public boolean insertWeightTab(AppearanceBlockModel appearanceBlockModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(UNIT, appearanceBlockModel.getName());

            return db.insert(UNIT_WEIGHT_TAB, null, values) != -1;
        }
    }

    public String getUnitWeight(int id) {

        String result = "";
        String search = "SELECT * FROM " + UNIT_WEIGHT_TAB
                + " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(id)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
            if (cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndexOrThrow(UNIT));
            }
        }
        return result;
    }


    public boolean updateHeight(int unitID, int numberID, int numberValue, int unitValue) {

        String cause = ID + " == ?";
        String[] unitArgs = {String.valueOf(unitID)};
        String[] numberArgs = {String.valueOf(numberID)};

        try (SQLiteDatabase db = getWritableDatabase()) {
            db.beginTransaction();
            try {
                ContentValues numberVal = new ContentValues();
                ContentValues unitVal = new ContentValues();
                numberVal.put(INT_VAL, numberValue);
                unitVal.put(USER_UNIT_HEIGHT, unitValue);
                int updateNumber = db.update(MIDDLE_TAB, numberVal, cause, numberArgs);
                int updateUnit = db.update(USER_UNITS_TAB, unitVal, cause, unitArgs);

                db.setTransactionSuccessful();
                return (updateNumber != -1 && updateUnit != -1);
            } finally {
                db.endTransaction();
            }
        }
    }

    public boolean updateWeight(int unitID, int numberID, int numberValue, int unitValue) {

        String cause = ID + " == ?";
        String[] unitArgs = {String.valueOf(unitID)};
        String[] numberArgs = {String.valueOf(numberID)};

        try (SQLiteDatabase db = getWritableDatabase()) {
            db.beginTransaction();
            try {
                ContentValues numberVal = new ContentValues();
                ContentValues unitVal = new ContentValues();
                numberVal.put(INT_VAL, numberValue);
                unitVal.put(USER_UNIT_WEIGHT, unitValue);
                int updateNumber = db.update(MIDDLE_TAB, numberVal, cause, numberArgs);
                int updateUnit = db.update(USER_UNITS_TAB, unitVal, cause, unitArgs);

                db.setTransactionSuccessful();
                return (updateNumber != -1 && updateUnit != -1);
            } finally {
                db.endTransaction();
            }
        }
    }


    public boolean insertUnitsTab(IntegerModel integerModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(USER_HEIGHT, integerModel.getFirstValue());
            values.put(USER_WEIGHT, integerModel.getSecondValue());
            values.put(USER_UNIT_HEIGHT, integerModel.getThirdValue());
            values.put(USER_UNIT_WEIGHT, integerModel.getForthValue());

            return db.insert(USER_UNITS_TAB, null, values) != -1;
        }
    }

    public List<IntegerModel> getUserUnit(int value) {

        List<IntegerModel> result = new ArrayList<>();
        String search = "SELECT * FROM " + USER_UNITS_TAB +
                " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(value)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
            if (cursor.moveToFirst()) {
                IntegerModel integerModel = new IntegerModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_HEIGHT)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_WEIGHT)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_UNIT_HEIGHT)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_UNIT_WEIGHT)));
                result.add(integerModel);
            }
        }
        return result;
    }


    public boolean insertAppearanceBlock(AppearanceBlockModel appearanceBlockModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(APPEARANCE_ICON, appearanceBlockModel.getIcon());
            values.put(APPEARANCE_IMAGE, appearanceBlockModel.getImage());
            values.put(APPEARANCE_NAME, appearanceBlockModel.getName());
            values.put(APPEARANCE_ACTION, appearanceBlockModel.getAction());
            values.put(APPEARANCE_DESCRIPTION, appearanceBlockModel.getDescription());
            values.put(APPEARANCE_TAG, appearanceBlockModel.getTag());

            return db.insert(APPEARANCE_TAB, null, values) != -1;
        }
    }

    public List<AppearanceBlockModel> getAppearanceBlock(int id) {

        List<AppearanceBlockModel> result = new ArrayList<>();
        String search = "SELECT * FROM " + APPEARANCE_TAB +
                " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(id)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
            if (cursor.moveToFirst()) {
                AppearanceBlockModel appearanceBlockModel = new AppearanceBlockModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(APPEARANCE_ICON)),
                        cursor.getString(cursor.getColumnIndexOrThrow(APPEARANCE_IMAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(APPEARANCE_NAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(APPEARANCE_ACTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(APPEARANCE_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(APPEARANCE_TAG)));
                result.add(appearanceBlockModel);
            }
        }
        return result;
    }


    public boolean insertLevelTab(IntegerModel integerModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(LEVEL_BEGINNER, integerModel.getFirstValue());
            values.put(LEVEL_INTERMEDIATE, integerModel.getSecondValue());
            values.put(LEVEL_ADVANCED, integerModel.getThirdValue());

            return db.insert(LEVEL_TAB, null, values) != -1;
        }
    }

    public List<IntegerModel> getLevel(int param) {

        List<IntegerModel> result = new ArrayList<>();
        String search = "SELECT * FROM " + LEVEL_TAB +
                " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(param)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
            if (cursor.moveToFirst()) {
                IntegerModel integerModel = new IntegerModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(LEVEL_BEGINNER)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(LEVEL_INTERMEDIATE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(LEVEL_ADVANCED)));
                result.add(integerModel);
            }
        }
        return result;
    }

    public boolean switchLevel(int oldPos, int newPos) {

        String cause = ID + " = ?";
        String[] oldArgs = {String.valueOf(oldPos)};
        String[] newArgs = {String.valueOf(newPos)};

        try (SQLiteDatabase db = getWritableDatabase()) {
            db.beginTransaction();
            try {
                ContentValues addVal = new ContentValues();
                ContentValues removeVal = new ContentValues();
                addVal.put(INT_VAL, 1);
                removeVal.put(INT_VAL, 0);
                int updateOld = db.update(MIDDLE_TAB, removeVal, cause, oldArgs);
                int updateNew = db.update(MIDDLE_TAB, addVal, cause, newArgs);

                db.setTransactionSuccessful();
                return (updateOld != -1 && updateNew != -1);
            } finally {
                db.endTransaction();
            }
        }
    }


    public boolean insertUserInformation(UserInformationModel userInformationModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(USER_NAME, userInformationModel.getName());
            values.put(USER_EMAIL, userInformationModel.getEmail());
            values.put(USER_PASSWORD, userInformationModel.getPassword());
            values.put(USER_GENDER_ID, userInformationModel.getGender());
            values.put(USER_UNITS_ID, userInformationModel.getUnits());
            values.put(USER_PERFORMANCE_ID, userInformationModel.getPerformance());
            values.put(USER_GOALS_ID, userInformationModel.getGoals());
            values.put(USER_LEVEL_ID, userInformationModel.getLevel());
            values.put(USER_NOTIFICATION_ID, userInformationModel.getNotification());

            return db.insert(USER_INFORMATION_TAB, null, values) != -1;
        }
    }

    public List<UserInformationModel> getInformationUser(int id) {

        List<UserInformationModel> result = new ArrayList<>();
        String search = "SELECT * FROM " + USER_INFORMATION_TAB +
                " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(id)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {

            if (cursor.moveToFirst()) {
                UserInformationModel userInformationModel = new UserInformationModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(USER_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(USER_PASSWORD)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_GENDER_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_UNITS_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_PERFORMANCE_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_GOALS_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_LEVEL_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_NOTIFICATION_ID)));
                result.add(userInformationModel);
            }
        }
        return result;
    }

    public boolean updateUser(RowNames rowName, int id, String value) {

        String cause = ID + " == ?";
        String[] args = {String.valueOf(id)};

        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues updateVal = new ContentValues();
            updateVal.put(String.valueOf(rowName), value);
            return db.update(USER_INFORMATION_TAB, updateVal, cause, args) != -1;
        }
    }


    public boolean insertGoalsTab(IntegerModel integerModel) {

        try (SQLiteDatabase db = this.getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(USER_GOAL_STRENGTH, integerModel.getFirstValue());
            values.put(USER_GOAL_MUSCLE, integerModel.getSecondValue());
            values.put(USER_GOAL_FAT_LOSE, integerModel.getThirdValue());
            values.put(USER_GOAL_TECHNIQUE, integerModel.getForthValue());

            return db.insert(USER_GOALS_TAB, null, values) != -1;
        }
    }

    public List<IntegerModel> getGoals(int id) {

        List<IntegerModel> result = new ArrayList<>();
        String search = "SELECT * FROM " + USER_GOALS_TAB +
                " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(id)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
            if (cursor.moveToFirst()) {
                IntegerModel integerModel = new IntegerModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_GOAL_STRENGTH)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_GOAL_MUSCLE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_GOAL_FAT_LOSE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_GOAL_TECHNIQUE)));
                result.add(integerModel);
            }
        }
        return result;
    }

    public boolean updateGoals(int id, int value) {

        String cause = ID + " == ?";
        String[] args = {String.valueOf(id)};

        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues numberVal = new ContentValues();
            numberVal.put(INT_VAL, value);
            return db.update(MIDDLE_TAB, numberVal, cause, args) != -1;
        }
    }


    public boolean insertNotifications(IntegerModel integerModel) {

        try (SQLiteDatabase db = this.getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(NOTIFICATIONS_STATUS, integerModel.getFirstValue());
            values.put(NOTIFICATION_DAYS, integerModel.getSecondValue());
            values.put(NOTIFICATION_HOURS, integerModel.getThirdValue());
            values.put(NOTIFICATION_WORKOUT_ID, integerModel.getForthValue());

            return db.insert(NOTIFICATIONS_TAB, null, values) != -1;
        }
    }

    public List<IntegerModel> getNotifications(int id) {

        List<IntegerModel> result = new ArrayList<>();
        String search = "SELECT * FROM " + NOTIFICATIONS_TAB +
                " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(id)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
            if (cursor.moveToFirst()) {
                IntegerModel integerModel = new IntegerModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(NOTIFICATIONS_STATUS)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(NOTIFICATION_DAYS)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(NOTIFICATION_HOURS)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(NOTIFICATION_WORKOUT_ID)));
                result.add(integerModel);
            }
        }
        return result;
    }


    public boolean insertPerformance(IntegerModel integerModel) {

        try (SQLiteDatabase db = this.getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(PERFORMANCE_PUSH, integerModel.getFirstValue());
            values.put(PERFORMANCE_PULL, integerModel.getSecondValue());
            values.put(PERFORMANCE_SQUAD, integerModel.getThirdValue());
            values.put(PERFORMANCE_DIP, integerModel.getForthValue());

            return db.insert(USER_PERFORMANCE_TAB, null, values) != -1;
        }
    }

    public List<IntegerModel> getPerformance(int id) {

        List<IntegerModel> list = new ArrayList<>();
        String search = "SELECT * FROM " + USER_PERFORMANCE_TAB +
                " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(id)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {

            if (cursor.moveToFirst()) {
                IntegerModel integerModel = new IntegerModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(PERFORMANCE_PUSH)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(PERFORMANCE_PULL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(PERFORMANCE_SQUAD)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(PERFORMANCE_DIP)));
                list.add(integerModel);
            }
        }
        return list;
    }

    public boolean updatePerformance(int id, int value) {

        String cause = ID + " == ?";
        String[] args = {String.valueOf(id)};

        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues numberVal = new ContentValues();
            numberVal.put(INT_VAL, value);
            return db.update(MIDDLE_TAB, numberVal, cause, args) != -1;
        }
    }


    public boolean insertLanguage(LanguageModel languageModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(STRING_VAL, languageModel.getName());
            values.put(INT_VAL, languageModel.getStatus() ? 1 : 0);
            values.put(LANGUAGE_PREFIX, languageModel.getPrefix());
            values.put(IMG_PATH, languageModel.getImage());

            return db.insert(LANGUAGE_TAB, null, values) != -1;
        }
    }

    public List<LanguageModel> showLanguage() {

        List<LanguageModel> result = new ArrayList<>();
        String search = "SELECT * FROM " + LANGUAGE_TAB;

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, null)) {

            if (cursor.moveToFirst()) {
                LanguageModel languageModel = new LanguageModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(STRING_VAL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(INT_VAL)) == 1,
                        cursor.getString(cursor.getColumnIndexOrThrow(LANGUAGE_PREFIX)),
                        cursor.getString(cursor.getColumnIndexOrThrow(IMG_PATH)));
                result.add(languageModel);
            }
        }
        return result;
    }

    public boolean switchLanguage(int oldPos, int newPos) {

        String cause = ID + " == ?";
        String[] oldArgs = {String.valueOf(oldPos)};
        String[] newArgs = {String.valueOf(newPos)};

        try (SQLiteDatabase db = getWritableDatabase()) {
            db.beginTransaction();
            try {
                ContentValues addVal = new ContentValues();
                ContentValues removeVal = new ContentValues();
                addVal.put(INT_VAL, 1);
                removeVal.put(INT_VAL, 0);
                int updateOld = db.update(LANGUAGE_TAB, removeVal, cause, oldArgs);
                int updateNew = db.update(LANGUAGE_TAB, addVal, cause, newArgs);

                db.setTransactionSuccessful();
                return (updateOld != -1 && updateNew != -1);
            } finally {
                db.endTransaction();
            }
        }
    }


    public int getLastID(String tableName) {
        int lastID = -1;
        String search = "SELECT * FROM " + tableName;
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, null)) {
            if (cursor.moveToLast()) {
                lastID = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
            }
        }
        return lastID;
    }

    public long getCount(String table) {
        try (SQLiteDatabase db = getReadableDatabase()) {
            return DatabaseUtils.queryNumEntries(db, table);
        }
    }
}
