package com.example.exerciseapp.mDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.exerciseapp.mClasses.InsertResult;
import com.example.exerciseapp.mEnums.RowNames;
import com.example.exerciseapp.mModels.FourElementLinearListModel;
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
    private static final String USER_AUTHORIZATION_TOKEN = "AUTHORIZATION_TOKEN";
    private static final String USER_NEWS_TOKEN = "ACCOUNT_TOKEN";
    private static final String USER_GENDER = "GENDER";
    private static final String USER_UNITS_ID = "USER_UNITS";
    private static final String USER_PERFORMANCE_ID = "PERFORMANCE";
    private static final String USER_GOALS_ID = "GOALS";
    private static final String USER_LEVEL = "LEVEL";
    private static final String USER_NOTIFICATION_ID = "NOTIFICATION";
    private static final String USER_ACCOUNT_STATUS = "STATUS";


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


    private static final String LANGUAGE_TAB = "LANGUAGE";
    private static final String LANGUAGE_PREFIX = "LANGUAGE_PREFIX";


    public DBHelper(@Nullable Context context) {
        super(context, "ExerciseApp.db",
                null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createUserInformationTab = "CREATE TABLE " + USER_INFORMATION_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_NAME + " TEXT, " + USER_EMAIL + " TEXT, " + USER_PASSWORD + " TEXT, "
                + USER_AUTHORIZATION_TOKEN + " TEXT, " + USER_NEWS_TOKEN + " TEXT, "
                + USER_GENDER + " INTEGER, " + USER_UNITS_ID + " INTEGER, "
                + USER_PERFORMANCE_ID + " INTEGER, " + USER_GOALS_ID + " INTEGER, "
                + USER_LEVEL + " INTEGER, " + USER_NOTIFICATION_ID + " INTEGER, "
                + USER_ACCOUNT_STATUS + " INTEGER, "
                + " FOREIGN KEY (" + USER_UNITS_ID + ") REFERENCES "
                + USER_UNITS_TAB + " (" + ID + "), "
                + " FOREIGN KEY (" + USER_GOALS_ID + ") REFERENCES "
                + USER_GOALS_TAB + " (" + ID + "), "
                + " FOREIGN KEY (" + USER_NOTIFICATION_ID + ") REFERENCES "
                + NOTIFICATIONS_TAB + " (" + ID + "));";
        sqLiteDatabase.execSQL(createUserInformationTab);

        String createGoalsTab = "CREATE TABLE " + USER_GOALS_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_GOAL_STRENGTH + " INTEGER, " + USER_GOAL_MUSCLE + " INTEGER, "
                + USER_GOAL_FAT_LOSE + " INTEGER, " + USER_GOAL_TECHNIQUE + " INTEGER)";
        sqLiteDatabase.execSQL(createGoalsTab);

        String createUserPerformanceTab = "CREATE TABLE " + USER_PERFORMANCE_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PERFORMANCE_PUSH + " INTEGER, " + PERFORMANCE_PULL + " INTEGER, "
                + PERFORMANCE_SQUAD + " INTEGER, " + PERFORMANCE_DIP + " INTEGER)";
        sqLiteDatabase.execSQL(createUserPerformanceTab);

        String createUserUnitsTab = "CREATE TABLE " + USER_UNITS_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_HEIGHT + " INTEGER, "
                + USER_WEIGHT + " INTEGER, "
                + USER_UNIT_HEIGHT + " INTEGER, "
                + USER_UNIT_WEIGHT + " INTEGER, "
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
                + LEVEL_ADVANCED + " INTEGER)";
        sqLiteDatabase.execSQL(createLevelTab);

        String createNotificationsTab = "CREATE TABLE " + NOTIFICATIONS_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NOTIFICATIONS_STATUS + " INTEGER, " + NOTIFICATION_DAYS + " INTEGER, "
                + NOTIFICATION_HOURS + " INTEGER, " + NOTIFICATION_WORKOUT_ID + " INTEGER)";
        sqLiteDatabase.execSQL(createNotificationsTab);

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

    public List<FourElementLinearListModel> getUserUnit(int value) {

        String search = "SELECT * FROM " + USER_UNITS_TAB +
                " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(value)};
        IntegerModel integerModel;
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
            if (cursor.moveToFirst()) {
                integerModel = new IntegerModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_HEIGHT)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_WEIGHT)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_UNIT_HEIGHT)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_UNIT_WEIGHT)));
            } else {
                integerModel = new IntegerModel();
            }
        }

        List<FourElementLinearListModel> units = new ArrayList<>();
        String[] unitsName = {"Height", "Weight"};
        int[] valuesTab = {integerModel.getFirstValue(), integerModel.getSecondValue()};
        String[] unitsTab = {this.getUnitHeight(integerModel.getThirdValue()),
                this.getUnitWeight(integerModel.getForthValue())};

//        Log.i("TAG", "getUserUnit: " + this.getUnitHeight(integerModel.getThirdValue()) +
//                " :) " + integerModel.getForthValue());
//
//        FourElementLinearListModel model1 = new FourElementLinearListModel(1, 0, unitsName[0],
//                String.valueOf(integerModel.getFirstValue()), getUnitHeight(integerModel.getThirdValue()),
//                0);
//        units.add(model1);
//        FourElementLinearListModel model2 = new FourElementLinearListModel(2, 0, unitsName[1],
//                String.valueOf(integerModel.getSecondValue()), getUnitWeight(integerModel.getForthValue()),
//                0);
//        units.add(model2);
        for (int i = 0; i < unitsName.length; i++) {
            FourElementLinearListModel model = new FourElementLinearListModel(i, 0, unitsName[i],
                    String.valueOf(valuesTab[i]), unitsTab[i], 0);
            units.add(model);
        }

        return units;
    }

    public boolean insertUnitHeight(StringModel stringModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(UNIT, stringModel.getName());

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

    public boolean updateHeight(long userID, int numberValue, int unitValue) {

        int units = getInformationUser(userID).get(0).getUnits();
        String cause = ID + " == " + units;

        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(USER_HEIGHT, numberValue);
            values.put(USER_UNIT_HEIGHT, unitValue);
            int update = db.update(USER_UNITS_TAB, values, cause, null);
            return update != -1;
        }
    }

    public boolean insertUnitWeight(StringModel stringModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(UNIT, stringModel.getName());

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

    public boolean updateWeight(long unitID, int numberValue, int unitValue) {

        String cause = ID + " == ?";
        String[] args = {String.valueOf(unitID)};

        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(USER_WEIGHT, numberValue);
            values.put(USER_UNIT_WEIGHT, unitValue);
            int update = db.update(USER_UNITS_TAB, values, cause, args);
            return update != -1;
        }
    }

    public InsertResult logIn(String email, byte[] password) {

        String search = "SELECT " + ID + " FROM " + USER_INFORMATION_TAB +
                " WHERE " + USER_EMAIL + " == ? AND " + USER_PASSWORD + " == ?";

        StringBuilder builder = new StringBuilder();

        for (byte b : password) {
            builder.append(String.format("%02x", b));
        }
        String hexPassword = builder.toString();
        String[] args = {email, hexPassword};
        int userId;
        boolean success;
        try (SQLiteDatabase db = getWritableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
            if (cursor.moveToFirst()) {
                userId = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
                success = true;
            } else {
                userId = -1;
                success = false;
            }
        }
        Log.i("TAG", "logIn(userId): " + userId);
        return new InsertResult(userId, success);
    }

    public boolean accountStatus(long id, boolean status) {

        String search = ID + " == ?";
        String[] args = {String.valueOf(id)};

        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(USER_ACCOUNT_STATUS, status ? 1 : 0);
            int update = db.update(USER_INFORMATION_TAB, values, search, args);
            return update != -1;
        }
    }

    public InsertResult insertUserInformation(UserInformationModel userInformationModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(USER_NAME, userInformationModel.getName());
            values.put(USER_EMAIL, userInformationModel.getEmail());
            values.put(USER_PASSWORD, userInformationModel.getPassword());
            values.put(USER_AUTHORIZATION_TOKEN, userInformationModel.getPassword());
            values.put(USER_NEWS_TOKEN, userInformationModel.getPassword());
            values.put(USER_GENDER, userInformationModel.getGender());
            values.put(USER_UNITS_ID, userInformationModel.getUnits());
            values.put(USER_PERFORMANCE_ID, userInformationModel.getPerformance());
            values.put(USER_GOALS_ID, userInformationModel.getGoals());
            values.put(USER_LEVEL, userInformationModel.getLevel());
            values.put(USER_NOTIFICATION_ID, userInformationModel.getNotification());
            values.put(USER_ACCOUNT_STATUS, userInformationModel.getStatus());

            long insert = db.insert(USER_INFORMATION_TAB, null, values);
            boolean success = insert != -1;
            return new InsertResult(insert, success);
        }
    }

    public List<UserInformationModel> getInformationUser(long id) {

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
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_GENDER)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_UNITS_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_PERFORMANCE_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_GOALS_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_LEVEL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_NOTIFICATION_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_ACCOUNT_STATUS)));
                result.add(userInformationModel);
            }
        }
        return result;
    }

    public boolean switchUserGender(long userID, int param) {
        String cause = ID + " == ?";
        String[] args = {String.valueOf(userID)};
        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(USER_GENDER, param);
            int update = db.update(USER_INFORMATION_TAB, values, cause, args);
            return update != -1;
        }
    }

    public boolean switchUserLevel(long userID, int param) {
        String cause = ID + " == ?";
        String[] args = {String.valueOf(userID)};
        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(USER_LEVEL, param);
            int update = db.update(USER_INFORMATION_TAB, values, cause, args);
            return update != -1;
        }
    }

    public boolean updateUser(RowNames rowName, long id, String value) {

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

    public List<FourElementLinearListModel> getGoals(long id) {

        String search = "SELECT * FROM " + USER_GOALS_TAB +
                " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(id)};
        IntegerModel integerModel;
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
            if (cursor.moveToFirst()) {
                integerModel = new IntegerModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_GOAL_STRENGTH)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_GOAL_MUSCLE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_GOAL_FAT_LOSE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_GOAL_TECHNIQUE)));
            } else {
                integerModel = new IntegerModel();
            }
        }
        List<FourElementLinearListModel> goals = new ArrayList<>();
        String[] goalsName = {"Strength", "Muscle", "Fat lose", "Technique"};
        int[] ints = {integerModel.getFirstValue(), integerModel.getSecondValue(),
                integerModel.getThirdValue(), integerModel.getForthValue()};
        for (int i = 0; i < goalsName.length; i++) {
            FourElementLinearListModel model = new FourElementLinearListModel(i, goalsName[i],
                    String.valueOf(ints[i]), 0);
            goals.add(model);
        }

        return goals;
    }

    public boolean updateUserGoals(long userID, int column, int param) {
        int goals = getInformationUser(userID).get(0).getGoals();
        String cause = ID + " == " + goals;
        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            switch (column) {
                case 0:
                    values.put(USER_GOAL_STRENGTH, param);
                    break;
                case 1:
                    values.put(USER_GOAL_MUSCLE, param);
                    break;
                case 2:
                    values.put(USER_GOAL_FAT_LOSE, param);
                    break;
                case 3:
                    values.put(USER_GOAL_TECHNIQUE, param);
                    break;
            }
            return db.update(USER_GOALS_TAB, values, cause, null) != -1;
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

    public List<FourElementLinearListModel> getPerformance(int id) {

        String search = "SELECT * FROM " + USER_PERFORMANCE_TAB +
                " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(id)};
        IntegerModel integerModel;
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {

            if (cursor.moveToFirst()) {
                integerModel = new IntegerModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(PERFORMANCE_PUSH)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(PERFORMANCE_PULL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(PERFORMANCE_SQUAD)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(PERFORMANCE_DIP)));
            } else {
                integerModel = new IntegerModel();
            }
        }

        List<FourElementLinearListModel> performance = new ArrayList<>();
        String[] performanceName = {"Push", "Pull", "Squad", "Dip"};
        int[] ints = {integerModel.getFirstValue(), integerModel.getSecondValue(),
                integerModel.getThirdValue(), integerModel.getForthValue()};
        for (int i = 0; i < performanceName.length; i++) {
            FourElementLinearListModel model = new FourElementLinearListModel(i,
                    performanceName[i], String.valueOf(ints[i]), 0);
            performance.add(model);
        }
        return performance;
    }

    public boolean updateUserPerformance(long userID, int column, int param) {
        int performance = getInformationUser(userID).get(0).getPerformance();
        String cause = ID + " == " + performance;
        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            switch (column) {
                case 0:
                    values.put(PERFORMANCE_PUSH, param);
                    break;
                case 1:
                    values.put(PERFORMANCE_PULL, param);
                    break;
                case 2:
                    values.put(PERFORMANCE_SQUAD, param);
                    break;
                case 3:
                    values.put(PERFORMANCE_DIP, param);
                    break;
            }
            return db.update(USER_PERFORMANCE_TAB, values, cause, null) != -1;
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
