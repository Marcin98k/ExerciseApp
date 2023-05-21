package com.example.exerciseapp.mDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.exerciseapp.mEnums.RowNames;
import com.example.exerciseapp.mModels.StringModel;
import com.example.exerciseapp.mModels.AppearanceBlockModel;
import com.example.exerciseapp.mModels.IntegerModel;
import com.example.exerciseapp.mModels.ThreeElementLinearListModel;
import com.example.exerciseapp.mModels.UserInformationModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String ID = "ID";

    private static final String STRING_VAL = "STR_VALUE";
    private static final String INT_VAL = "INT_VALUE";

    private static final String APPEARANCE = "APPEARANCE";

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

    private static final String LANGUAGE_TAB = "LANGUAGE";


    public DBHelper(@Nullable Context context) {
        super(context, "ExerciseApp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createAppearanceTab = "CREATE TABLE " + APPEARANCE_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + APPEARANCE_ICON + " INTEGER, " + APPEARANCE_IMAGE + " TEXT, "
                + APPEARANCE_NAME + " TEXT, " + APPEARANCE_ACTION + " INTEGER, "
                + APPEARANCE_DESCRIPTION + " TEXT, "+ APPEARANCE_TAG + " TEXT);";
        sqLiteDatabase.execSQL(createAppearanceTab);

        String createFutureTab = "CREATE TABLE " + FUTURE_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FUTURE_DESCRIPTION + " TEXT)";
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


//        !!!#
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
                + MIDDLE_TAB + " (" + ID + "));";;
        sqLiteDatabase.execSQL(createLevelTab);

        String createMiddleTab = "CREATE TABLE " + MIDDLE_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + INT_VAL + " INTEGER, " + MIDDLE_APPEARANCE + " INTEGER) ";
//                + " FOREIGN KEY (" + MIDDLE_APPEARANCE + ") REFERENCES "
//                + APPEARANCE_TAB + " (" + ID + "));";;
        sqLiteDatabase.execSQL(createMiddleTab);









        //        STRING_VAL -> LANGUAGE USER_NAME, INT_VAL -> IS ACTIVE;
        String createLanguageTab = "CREATE TABLE " + LANGUAGE_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + STRING_VAL + " TEXT, " + INT_VAL + " INTEGER, "
                + IMG_PATH + " INTEGER);";
        sqLiteDatabase.execSQL(createLanguageTab);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertMiddleTab(IntegerModel integerModel) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(INT_VAL, integerModel.getFirstValue());
        values.put(MIDDLE_APPEARANCE, integerModel.getSecondValue());

        long insert = db.insert(MIDDLE_TAB, null, values);

        return insert != -1;
    }

    public boolean insertFutureTab(String value) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FUTURE_DESCRIPTION, value);

        long insert = db.insert(FUTURE_TAB, null, values);

        return insert != -1;
    }

    public List<StringModel> showFutureTab() {

        List<StringModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + FUTURE_TAB;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                String value = cursor.getString(1);

                StringModel model = new StringModel(id, value);
                list.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    public List<IntegerModel> showMiddleTab() {

        List<IntegerModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + MIDDLE_TAB;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                int value = cursor.getInt(1);
                int appearance = cursor.getInt(2);

                IntegerModel model = new IntegerModel(id, value, appearance);
                list.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }
    public List<IntegerModel> showMiddleIndex(int param) {

        List<IntegerModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + MIDDLE_TAB + " WHERE " + ID + " = " + param;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

                int id = cursor.getInt(0);
                int value = cursor.getInt(1);
                int appearance = cursor.getInt(2);

                IntegerModel model = new IntegerModel(id, value, appearance);
                list.add(model);
        }

        cursor.close();
        db.close();

        return list;
    }


    public boolean insertGenderTab(IntegerModel integerModel) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_MALE, integerModel.getFirstValue());
        values.put(USER_FEMALE, integerModel.getSecondValue());
        values.put(USER_OTHER, integerModel.getThirdValue());

        long insert = db.insert(GENDER_TAB, null, values);

        return insert != -1;
    }

    public List<IntegerModel> showLastGender() {

        List<IntegerModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + GENDER_TAB;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                int male = cursor.getInt(1);
                int female = cursor.getInt(2);
                int other = cursor.getInt(3);

                if (cursor.isLast()) {
                    IntegerModel model = new IntegerModel(id, male, female, other);
                    list.add(model);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    public boolean insertHeightTab(AppearanceBlockModel appearanceBlockModel) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(UNIT, appearanceBlockModel.getName());

        long insert = db.insert(UNIT_HEIGHT_TAB, null, values);

        return insert != -1;
    }

    public boolean insertWeightTab(AppearanceBlockModel appearanceBlockModel) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(UNIT, appearanceBlockModel.getName());

        long insert = db.insert(UNIT_WEIGHT_TAB, null, values);

        return insert != -1;
    }

    public boolean insertUnitsTab(IntegerModel integerModel) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_HEIGHT, integerModel.getFirstValue());
        values.put(USER_WEIGHT, integerModel.getSecondValue());
        values.put(USER_UNIT_HEIGHT, integerModel.getThirdValue());
        values.put(USER_UNIT_WEIGHT, integerModel.getForthValue());

        long insert = db.insert(USER_UNITS_TAB, null, values);

        return insert != -1;
    }

    public List<IntegerModel> showUnitsTab() {

        List<IntegerModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + USER_UNITS_TAB;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            int id = cursor.getInt(0);
            int height = cursor.getInt(1);
            int weight = cursor.getInt(2);
            int unitHeight = cursor.getInt(3);
            int unitWeight = cursor.getInt(4);

            IntegerModel model = new IntegerModel(
                    id, height, weight, unitHeight, unitWeight);
            list.add(model);
        }

        cursor.close();
        db.close();

        return list;
    }

    public List<IntegerModel> showUserUnit(int value) {

        List<IntegerModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + USER_UNITS_TAB + " WHERE ID = " + value;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                int height = cursor.getInt(1);
                int weight = cursor.getInt(2);
                int unit_height = cursor.getInt(3);
                int unit_weight = cursor.getInt(4);

                IntegerModel model = new IntegerModel(id, height, weight, unit_height, unit_weight);
                list.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    public List<StringModel> showUnitHeight(int value) {

        List<StringModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + UNIT_HEIGHT_TAB + " WHERE ID = " + value;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                String unit_height = cursor.getString(1);

                StringModel model = new StringModel(id, unit_height);
                list.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    public List<StringModel> showUnitWeight(int value) {

        List<StringModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + UNIT_WEIGHT_TAB + " WHERE ID = " + value;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                String unit_height = cursor.getString(1);

                StringModel model = new StringModel(id, unit_height);
                list.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }


    public boolean insertAppearanceBlock(AppearanceBlockModel appearanceBlockModel) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(APPEARANCE_ICON, appearanceBlockModel.getIcon());
        values.put(APPEARANCE_IMAGE, appearanceBlockModel.getImage());
        values.put(APPEARANCE_NAME, appearanceBlockModel.getName());
        values.put(APPEARANCE_ACTION, appearanceBlockModel.getAction());
        values.put(APPEARANCE_DESCRIPTION, appearanceBlockModel.getDescription());
        values.put(APPEARANCE_TAG, appearanceBlockModel.getTag());

        long insert = db.insert(APPEARANCE_TAB, null, values);

        return insert != -1;
    }

    public List<AppearanceBlockModel> showAppearanceBlock(int param) {

        List<AppearanceBlockModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + APPEARANCE_TAB + " WHERE " + ID + " = " + param;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            int id = cursor.getInt(0);
            int icon = cursor.getInt(1);
            String image = cursor.getString(2);
            String name = cursor.getString(3);
            int action = cursor.getInt(4);
            String description = cursor.getString(5);
            String tag = cursor.getString(6);


            AppearanceBlockModel model = new AppearanceBlockModel(
                    id, icon, image, name, action, description, tag);
            list.add(model);
        }

        cursor.close();
        db.close();

        return list;
    }


    public boolean insertLevelTab(IntegerModel integerModel) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LEVEL_BEGINNER, integerModel.getFirstValue());
        values.put(LEVEL_INTERMEDIATE, integerModel.getSecondValue());
        values.put(LEVEL_ADVANCED, integerModel.getThirdValue());

        long insert = db.insert(LEVEL_TAB, null, values);

        return insert != -1;
    }

    public boolean switchLevel(int oldPos, int newPos) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues addVal = new ContentValues();
        ContentValues removeVal = new ContentValues();
        addVal.put(INT_VAL, 1);
        removeVal.put(INT_VAL, 0);
        int updateOld = db.update(MIDDLE_TAB, removeVal, ID + " = " + oldPos , null);
        int updateNew = db.update(MIDDLE_TAB, addVal, ID + " = " + newPos, null);

        return (updateOld != -1 && updateNew != -1);
    }

    public boolean updateHeight(int id, int secondId, int firstValue, int secondValue) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues numberVal = new ContentValues();
        ContentValues unitVal = new ContentValues();
        numberVal.put(INT_VAL, firstValue);
        unitVal.put(USER_UNIT_HEIGHT, secondValue);
        int updateNumber = db.update(MIDDLE_TAB, numberVal, ID + " = " + secondId, null);
        int updateUnit = db.update(USER_UNITS_TAB, unitVal, ID + " = " + id, null);

        return (updateNumber != -1 && updateUnit != -1);
    }

    public boolean updateWeight(int id, int secondId, int firstValue, int secondValue) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues numberVal = new ContentValues();
        ContentValues unitVal = new ContentValues();
        numberVal.put(INT_VAL, firstValue);
        unitVal.put(USER_UNIT_WEIGHT, secondValue);
        int updateNumber = db.update(MIDDLE_TAB, numberVal, ID + " = " + secondId, null);
        int updateUnit = db.update(USER_UNITS_TAB, unitVal, ID + " = " + id, null);

        return (updateNumber != -1 && updateUnit != -1);
    }
    public boolean updatePerformance(int id, int firstValue) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues numberVal = new ContentValues();
        numberVal.put(INT_VAL, firstValue);
        int updateNumber = db.update(MIDDLE_TAB, numberVal, ID + " = " + id, null);

        return (updateNumber != -1);
    }

    public boolean updateGoals(int id, int firstValue) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues numberVal = new ContentValues();
        numberVal.put(INT_VAL, firstValue);
        int updateNumber = db.update(MIDDLE_TAB, numberVal, ID + " = " + id, null);

        return (updateNumber != -1);
    }


    public boolean switchGender(int oldPos, int newPos) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues addVal = new ContentValues();
        ContentValues removeVal = new ContentValues();
        addVal.put(INT_VAL, 1);
        removeVal.put(INT_VAL, 0);
        int updateOld = db.update(MIDDLE_TAB, removeVal, ID + " = " + oldPos, null);
        int updateNew = db.update(MIDDLE_TAB, addVal, ID + " = " + newPos, null);

        return (updateOld != -1 && updateNew != -1);
    }



    public List<IntegerModel> showLastLevel() {

        List<IntegerModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + LEVEL_TAB;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            int id = cursor.getInt(0);
            int beginner = cursor.getInt(1);
            int intermediate = cursor.getInt(2);
            int advanced = cursor.getInt(3);

            if (cursor.isLast()) {
                IntegerModel model = new IntegerModel(id, beginner, intermediate, advanced);
                list.add(model);
            }
        }

        cursor.close();
        db.close();

        return list;
    }

    public boolean updateUser(RowNames rowName, int id, String value){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues updateVal = new ContentValues();
        updateVal.put(String.valueOf(rowName), value);
        int updateNew = db.update(USER_INFORMATION_TAB, updateVal, ID + " = " + id, null);

        return updateNew != -1;
    }
    public boolean insertUserInformation(UserInformationModel userInformationModel) {

        SQLiteDatabase db = getWritableDatabase();
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

        long insert = db.insert(USER_INFORMATION_TAB, null, values);

        return insert != -1;
    }

    public List<UserInformationModel> showInformationUser(int param) {

        List<UserInformationModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + USER_INFORMATION_TAB + " WHERE " + ID + " = " + param;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String email = cursor.getString(2);
            String password = cursor.getString(3);
            int gender = cursor.getInt(4);
            int units = cursor.getInt(5);
            int performance = cursor.getInt(6);
            int goals = cursor.getInt(7);
            int level = cursor.getInt(8);
            int notification = cursor.getInt(9);

            UserInformationModel model = new UserInformationModel(
                    id, name, email, password, gender, units, performance, goals, level, notification);
            list.add(model);
        }

        cursor.close();
        db.close();

        return list;
    }


    public boolean insertGoalsTab(IntegerModel integerModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_GOAL_STRENGTH, integerModel.getFirstValue());
        values.put(USER_GOAL_MUSCLE, integerModel.getSecondValue());
        values.put(USER_GOAL_FAT_LOSE, integerModel.getThirdValue());
        values.put(USER_GOAL_TECHNIQUE, integerModel.getForthValue());

        long insert = db.insert(USER_GOALS_TAB, null, values);

        return insert != -1;
    }

    public List<IntegerModel> showLevel(int param) {

        List<IntegerModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + LEVEL_TAB + " WHERE " + ID + " = " + param;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                int beginner = cursor.getInt(1);
                int intermediate = cursor.getInt(2);
                int advanced = cursor.getInt(3);

                IntegerModel model = new IntegerModel(id, beginner, intermediate, advanced);
                list.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    public List<IntegerModel> showUserGoals(int value) {

        List<IntegerModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + USER_GOALS_TAB + " WHERE ID = " + value;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                int strength = cursor.getInt(1);
                int muscle = cursor.getInt(2);
                int fatLose = cursor.getInt(3);
                int technique = cursor.getInt(4);

                IntegerModel model = new IntegerModel(id, strength, muscle, fatLose, technique);
                list.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    public List<IntegerModel> showLastUserGoals() {

        List<IntegerModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + USER_GOALS_TAB;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                int strength = cursor.getInt(1);
                int muscle = cursor.getInt(2);
                int fatLose = cursor.getInt(3);
                int technique = cursor.getInt(4);

                if (cursor.isLast()) {
                    IntegerModel model = new IntegerModel(id, strength, muscle, fatLose, technique);
                    list.add(model);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    public boolean insertUserNotifications(IntegerModel integerModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NOTIFICATIONS_STATUS, integerModel.getFirstValue());
        values.put(NOTIFICATION_DAYS, integerModel.getSecondValue());
        values.put(NOTIFICATION_HOURS, integerModel.getThirdValue());
        values.put(NOTIFICATION_WORKOUT_ID, integerModel.getForthValue());

        long insert = db.insert(NOTIFICATIONS_TAB, null, values);

        return insert != -1;
    }

    public List<IntegerModel> showUserNotifications(int value) {

        List<IntegerModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + NOTIFICATIONS_TAB + " WHERE ID = " + value;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                int status = cursor.getInt(1);
                int days = cursor.getInt(2);
                int hour = cursor.getInt(3);
                int workoutID = cursor.getInt(4);

                IntegerModel model = new IntegerModel(id, status, days, hour, workoutID);
                list.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    public List<IntegerModel> showLastUserNotification() {

        List<IntegerModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + NOTIFICATIONS_TAB;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                int status = cursor.getInt(1);
                int days = cursor.getInt(2);
                int hour = cursor.getInt(3);
                int workoutID = cursor.getInt(4);

                if (cursor.isLast()) {
                    IntegerModel model = new IntegerModel(id, status, days, hour, workoutID);
                    list.add(model);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }


    public boolean insertUserPerformance(IntegerModel integerModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PERFORMANCE_PUSH, integerModel.getFirstValue());
        values.put(PERFORMANCE_PULL, integerModel.getSecondValue());
        values.put(PERFORMANCE_SQUAD, integerModel.getThirdValue());
        values.put(PERFORMANCE_DIP, integerModel.getForthValue());

        long insert = db.insert(USER_PERFORMANCE_TAB, null, values);

        return insert != -1;
    }

    public List<IntegerModel> showPerformance(int param) {

        List<IntegerModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + USER_PERFORMANCE_TAB + " WHERE " + ID + " = " + param;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                int push = cursor.getInt(1);
                int pull = cursor.getInt(2);
                int squad = cursor.getInt(3);
                int dip = cursor.getInt(4);

                IntegerModel model = new IntegerModel(id, push, pull, squad, dip);
                list.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    public List<IntegerModel> showGender(int param) {

        List<IntegerModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + GENDER_TAB + " WHERE " + ID + " = " + param;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                int male = cursor.getInt(1);
                int female = cursor.getInt(2);
                int other = cursor.getInt(3);

                IntegerModel model = new IntegerModel(id, male, female, other);
                list.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }
    public List<IntegerModel> showUserPerformance(int value) {

        List<IntegerModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + USER_PERFORMANCE_TAB + " WHERE ID = " + value;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                int push = cursor.getInt(0);
                int pull = cursor.getInt(0);
                int squad = cursor.getInt(0);
                int dip = cursor.getInt(0);

                IntegerModel model = new IntegerModel(id, push, pull, squad, dip);
                list.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    public List<IntegerModel> showLastUserPerformance() {

        List<IntegerModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + USER_PERFORMANCE_TAB;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                int push = cursor.getInt(0);
                int pull = cursor.getInt(0);
                int squad = cursor.getInt(0);
                int dip = cursor.getInt(0);

                if (cursor.isLast()) {
                    IntegerModel model = new IntegerModel(id, push, pull, squad, dip);
                    list.add(model);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }


    public boolean insertLanguage(ThreeElementLinearListModel threeElementLinearListModel) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(STRING_VAL, threeElementLinearListModel.getName());
        cv.put(INT_VAL, threeElementLinearListModel.getAction());
        cv.put(IMG_PATH, threeElementLinearListModel.getIcon());

        long insert = db.insert(LANGUAGE_TAB, null, cv);

        return insert != -1;
    }

    public List<ThreeElementLinearListModel> showLanguage() {

        SQLiteDatabase db = getReadableDatabase();
        List<ThreeElementLinearListModel> list = new ArrayList<>();
        String search = "SELECT * FROM " + LANGUAGE_TAB;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int action = cursor.getInt(2);
                int icon = cursor.getInt(3);

                ThreeElementLinearListModel model = new ThreeElementLinearListModel(id, icon, name, action);
                list.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    public boolean switchLanguage(int oldPos, int newPos) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues addVal = new ContentValues();
        ContentValues removeVal = new ContentValues();
        addVal.put(INT_VAL, 1);
        removeVal.put(INT_VAL, 0);
        int updateOld = db.update(LANGUAGE_TAB, removeVal, ID + " = " + (oldPos + 1), null);
        int updateNew = db.update(LANGUAGE_TAB, addVal, ID + " = " + (newPos + 1), null);

        return (updateOld != -1 && updateNew != -1);
    }

    public Integer searchByKey(String table, String column) {

        int value = -1;
        SQLiteDatabase db = getWritableDatabase();
        String search = "SELECT " + column + " FROM " + table;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            do {
                value = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        cursor.close();
        ;
        db.close();
        return value;
    }

    public long getCount(String table) {

        SQLiteDatabase db = getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, table);
        db.close();
        return count;
    }

    private String setStringRow(int id) {

        String value = "";
//        SQLiteDatabase db = getReadableDatabase();
//        String search = "SELECT l." + STRING_VAL
//                + " FROM " + LANGUAGE_TAB + " l, " + USER_UNITS_TAB + " u"
//                + " WHERE l." + id + " = u." + STRING_VAL;
//        Cursor cursor = db.rawQuery(search, null);
//        value = cursor.getString(0);
//        cursor.close();
//        ;
//        db.close();
        return value;
    }
}
