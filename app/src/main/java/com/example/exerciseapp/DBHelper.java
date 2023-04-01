package com.example.exerciseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String STRING_VAL = "STR_VALUE";
    private static final String INT_VAL = "INT_VALUE";

    private static final String USER_DATA_TAB = "USER_DATA";
    private static final String USER_EMAIL = "EMAIL";
    private static final String USER_TOKEN = "TOKEN";

    private static final String USER_INFORMATION_TAB = "USER_INFORMATION";
    private static final String USER_GENDER = "GENDER";
    private static final String USER_HEIGHT = "HEIGHT";
//    Get id form other table(unit_height_tab);
    private static final String USER_UNIT_HEIGHT = "UNIT_HEIGHT";
    private static final String USER_WEIGHT = "WEIGHT";
//    Get id from other table(unit_weight_tab);
    private static final String USER_UNIT_WEIGHT = "UNIT_WEIGHT";
//    Get id from other table(level_tab);
    private static final String USER_LEVEL = "LEVEL";


    private static final String USER_GOALS_TAB = "GOALS";

//    Boolean (INTEGER 0/1);
    private static final String USER_GOAL_STRENGTH = "STRENGTH";
    private static final String USER_GOAL_MUSCLE ="MUSCLE";
    private static final String USER_GOAL_FAT_LOSE ="FAT_LOSE";
    private static final String USER_GOAL_TECHNIQUE ="TECHNIQUE";


    private static final String USER_PERFORMANCE_TAB = "USER_PERFORMANCE";
    private static final String PERFORMANCE_PUSH = "PUSH";
    private static final String PERFORMANCE_PULL = "PULL";
    private static final String PERFORMANCE_SQUAD = "SQUAD";
    private static final String PERFORMANCE_DIP = "DIP";

    public DBHelper(@Nullable Context context) {
        super(context, "ExerciseApp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createUserDataTab = "CREATE TABLE " + USER_DATA_TAB
                + " ("+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT, " + USER_EMAIL + " TEXT, " + USER_TOKEN + " TEXT)";
        sqLiteDatabase.execSQL(createUserDataTab);

        String createUserInformationTab = "CREATE TABLE " + USER_INFORMATION_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT, " + USER_GENDER + " INTEGER, "
                + USER_HEIGHT + " INTEGER, " + USER_UNIT_HEIGHT + " INTEGER, "
                + USER_WEIGHT + " INTEGER, " + USER_UNIT_WEIGHT + " INTEGER, "
                + USER_LEVEL + " INTEGER)";
        sqLiteDatabase.execSQL(createUserInformationTab);

        String createGoalsTab = "CREATE TABLE " + USER_GOALS_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_GOAL_STRENGTH + " INTEGER, "
                + USER_GOAL_MUSCLE + " INTEGER, "
                + USER_GOAL_FAT_LOSE + " INTEGER, "
                + USER_GOAL_TECHNIQUE + " INTEGER)";
        sqLiteDatabase.execSQL(createGoalsTab);

        String createUserPerformanceTab = "CREATE TABLE " + USER_PERFORMANCE_TAB
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PERFORMANCE_PUSH + " INTEGER, " + PERFORMANCE_PULL + " INTEGER, "
                + PERFORMANCE_SQUAD + " INTEGER, " + PERFORMANCE_DIP + " INTEGER)";
        sqLiteDatabase.execSQL(createUserPerformanceTab);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public boolean insertUserInformation(UserInformationModel userInformationModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME, userInformationModel.getName());
        values.put(USER_GENDER, userInformationModel.getGender());
        values.put(USER_HEIGHT, userInformationModel.getHeight());
        values.put(USER_UNIT_HEIGHT, userInformationModel.getUnitHeight());
        values.put(USER_WEIGHT, userInformationModel.getWeight());
        values.put(USER_UNIT_WEIGHT, userInformationModel.getUnitWeight());
        values.put(USER_LEVEL, userInformationModel.getLevel());

        long insert = db.insert(USER_INFORMATION_TAB, null, values);

        return insert != -1;
    }

    public boolean insertUserGoals(IntegerModel integerModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_GOAL_STRENGTH, integerModel.getFirstValue());
        values.put(USER_GOAL_MUSCLE, integerModel.getSecondValue());
        values.put(USER_GOAL_FAT_LOSE, integerModel.getThirdValue());
        values.put(USER_GOAL_TECHNIQUE, integerModel.getForthValue());

        long insert = db.insert(USER_GOALS_TAB, null, values);

        return insert != -1;
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
}
