package com.example.exerciseapp.mDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.exerciseapp.mEnums.AccountStatus;
import com.example.exerciseapp.mEnums.Gender;
import com.example.exerciseapp.mEnums.Level;
import com.example.exerciseapp.mModels.GoalsModel;
import com.example.exerciseapp.mModels.PerformanceModel;
import com.example.exerciseapp.mModels.UnitsModel;
import com.example.exerciseapp.mModels.UserModel;

public class UserRepository extends SQLiteOpenHelper {

    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String IMAGE = "IMAGE";
    private static final String USER_ID = "USER_ID";
    private static final String STATUS = "STATUS";

    //    User table
    static final String USER_TABLE = "USER";
    private static final String EMAIL = "EMAIL";
    private static final String PASSWORD = "PASSWORD";
    private static final String TOKEN = "TOKEN";
    private static final String GENDER = "GENDER";
    private static final String UNITS = "UNITS";
    private static final String PERFORMANCE = "PERFORMANCE";
    private static final String LEVEL = "LEVEL";
    private static final String NOTIFICATION = "NOTIFICATION";

    //    Goals table
    static final String GOALS_TABLE = "GOALS";
    private static final String STRENGTH = "STRENGTH";
    private static final String MUSCLE = "MUSCLE";
    private static final String FAT_LOSE = "FAT_LOSE";
    private static final String TECHNIQUE = "TECHNIQUE";

    //    Performance table && Performance history table;
    static final String PERFORMANCE_TABLE = "PERFORMANCE";
    static final String PERFORMANCE_HISTORY_TABLE = "PERFORMANCE_HISTORY";
    private static final String PUSH = "PUSH";
    private static final String PULL = "PULL";
    private static final String SQUAD = "SQUAD";
    private static final String DIP = "DIP";

    //    Units table && Units history table;
    static final String UNITS_TABLE = "UNITS";
    static final String UNITS_HISTORY_TABLE = "UNITS_HISTORY";
    private static final String HEIGHT = "HEIGHT";
    private static final String WEIGHT = "WEIGHT";
    private static final String HEIGHT_UNIT = "HEIGHT_UNIT";
    private static final String WEIGHT_UNIT = "WEIGHT_UNIT";

    public UserRepository(@Nullable Context context) {
        super(context, "User.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createUserInformationTable());
        sqLiteDatabase.execSQL(createGoalTable());
        sqLiteDatabase.execSQL(createPerformanceTable());
        sqLiteDatabase.execSQL(createPerformanceHistoryTable());
        sqLiteDatabase.execSQL(createUnitTable());
        sqLiteDatabase.execSQL(createUnitHistoryTable());
    }

    private String createUserInformationTable() {
        return "CREATE TABLE " + USER_TABLE + " (" + ID + " INTEGER PRIMARY KEY, "
                + NAME + " TEXT, " + IMAGE + " TEXT, " + EMAIL + " TEXT, " + PASSWORD + " TEXT,"
                + TOKEN + " TEXT, " + GENDER + " INTEGER, " + UNITS + " INTEGER, "
                + PERFORMANCE + " INTEGER, " + LEVEL + " INTEGER, " + NOTIFICATION + " INTEGER,"
                + STATUS + " INTEGER)";
    }

    private String createGoalTable() {
        return "CREATE TABLE " + GOALS_TABLE + " (" + ID + " INTEGER PRIMARY KEY, "
                + STRENGTH + " INTEGER, " + MUSCLE + " INTEGER, " + FAT_LOSE + " INTEGER, "
                + TECHNIQUE + " INTEGER)";
    }

    private String createPerformanceTable() {
        return "CREATE TABLE " + PERFORMANCE_TABLE + " (" + ID + " INTEGER PRIMARY KEY, "
                + PUSH + " INTEGER," + PULL + " INTEGER, " + SQUAD + " INTEGER, "
                + DIP + " INTEGER)";
    }

    private String createPerformanceHistoryTable() {
        return "CREATE TABLE " + PERFORMANCE_HISTORY_TABLE + " (" + ID + " INTEGER PRIMARY KEY, "
                + USER_ID + " INTEGER, " + PUSH + " INTEGER," + PULL + " INTEGER, "
                + SQUAD + " INTEGER, " + DIP + " INTEGER)";
    }

    private String createUnitTable() {
        return "CREATE TABLE " + UNITS_TABLE + " (" + ID + " INTEGER PRIMARY KEY, "
                + HEIGHT + " INTEGER, " + WEIGHT + " INTEGER, " + HEIGHT_UNIT + " INTEGER, "
                + WEIGHT_UNIT + "INTEGER)";
    }

    private String createUnitHistoryTable() {
        return "CREATE TABLE " + UNITS_HISTORY_TABLE + " (" + ID + " INTEGER PRIMARY KEY, "
                + USER_ID + "INTEGER, " + HEIGHT + " INTEGER, " + WEIGHT + " INTEGER, "
                + HEIGHT_UNIT + " INTEGER, " + WEIGHT_UNIT + "INTEGER)";
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    boolean createUser(UserModel userModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues cv = new ContentValues();

            cv.put(NAME, userModel.getName());
            cv.put(IMAGE, userModel.getImage());
            cv.put(EMAIL, userModel.getEmail());
            cv.put(PASSWORD, userModel.getPassword());
            cv.put(TOKEN, userModel.getToken());
            cv.put(GENDER, userModel.getGender().ordinal());
            cv.put(UNITS, userModel.getUnits());
            cv.put(PERFORMANCE, userModel.getPerformance());
            cv.put(LEVEL, userModel.getLevel().ordinal());
            cv.put(NOTIFICATION, userModel.isNotification());
            cv.put(STATUS, userModel.getAccountStatus().ordinal());

            return db.insert(USER_TABLE, null, cv) != -1;
        }
    }

    boolean createGoals(GoalsModel goalsModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues cv = new ContentValues();

            cv.put(STRENGTH, goalsModel.isStrength());
            cv.put(MUSCLE, goalsModel.isMuscle());
            cv.put(FAT_LOSE, goalsModel.isFatLose());
            cv.put(TECHNIQUE, goalsModel.isTechnique());

            return db.insert(GOALS_TABLE, null, cv) != -1;
        }
    }

    boolean updateGoals(GoalsModel goalsModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues cv = new ContentValues();

            cv.put(STRENGTH, goalsModel.isStrength());
            cv.put(MUSCLE, goalsModel.isMuscle());
            cv.put(FAT_LOSE, goalsModel.isFatLose());
            cv.put(TECHNIQUE, goalsModel.isTechnique());

            return db.update(GOALS_TABLE, cv, ID + " == ?",
                    new String[]{String.valueOf(goalsModel.getId())}) != 0;
        }
    }

    boolean createPerformance(PerformanceModel performanceModel) {

        try(SQLiteDatabase db = getWritableDatabase()) {

            ContentValues cv = new ContentValues();

            cv.put(PUSH, performanceModel.getPush());
            cv.put(PULL, performanceModel.getPull());
            cv.put(SQUAD, performanceModel.getSquad());
            cv.put(DIP, performanceModel.getDip());

            return db.insert(PERFORMANCE_TABLE, null, cv) != -1;
        }
    }

    boolean changePerformance(PerformanceModel oldPerformance, PerformanceModel newPerformance) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {

            addToPerformanceHistory(oldPerformance, db);
            updatePerformance(oldPerformance, newPerformance, db);
            db.setTransactionSuccessful();
            return true;

        } catch (SQLException e) {
            throw new SQLException("Fail to change performance");
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    private void addToPerformanceHistory(PerformanceModel oldPerformance, SQLiteDatabase db) {

        ContentValues cv = new ContentValues();

        cv.put(USER_ID, oldPerformance.getUserId());
        cv.put(PUSH, oldPerformance.getPush());
        cv.put(PULL, oldPerformance.getPull());
        cv.put(SQUAD, oldPerformance.getSquad());
        cv.put(DIP, oldPerformance.getDip());

        long insertPerformance = db.insert(PERFORMANCE_TABLE, null, cv);
        if (insertPerformance == -1) {
            throw new SQLException("Fail to insert performance");
        }
    }

    private void updatePerformance(PerformanceModel oldPerformance, PerformanceModel newPerformance,
                                   SQLiteDatabase db) {

        ContentValues cv = new ContentValues();

        cv.put(PUSH, newPerformance.getPush());
        cv.put(PULL, newPerformance.getPull());
        cv.put(SQUAD, newPerformance.getSquad());
        cv.put(DIP, newPerformance.getDip());

        long updatePerformance = db.update(PERFORMANCE_TABLE, cv, ID + " == ?",
                new String[]{String.valueOf(oldPerformance.getId())});
        if (updatePerformance == 0) {
            throw new SQLException("Fail to update performance");
        }
    }

    boolean createUnits(UnitsModel unitsModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues cv = new ContentValues();

            cv.put(HEIGHT, unitsModel.getHeight());
            cv.put(WEIGHT, unitsModel.getWeight());
            cv.put(HEIGHT_UNIT, unitsModel.getHeight_unit());
            cv.put(WEIGHT_UNIT, unitsModel.getWeight_unit());

            return db.insert(UNITS_TABLE, null, cv) != -1;
        }
    }

    boolean changeUnits(UnitsModel oldUnits, UnitsModel newUnits) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {

            addToUnitHistory(oldUnits, db);
            updateUnit(oldUnits, newUnits, db);
            db.setTransactionSuccessful();
            return true;

        } catch (SQLException e) {
            throw new SQLException("Fail to change units");
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    private void addToUnitHistory(UnitsModel oldUnits, SQLiteDatabase db) {

        ContentValues cv = new ContentValues();

        cv.put(USER_ID, oldUnits.getUserId());
        cv.put(HEIGHT, oldUnits.getHeight());
        cv.put(WEIGHT, oldUnits.getWeight());
        cv.put(HEIGHT_UNIT, oldUnits.getHeight_unit());
        cv.put(WEIGHT_UNIT, oldUnits.getWeight_unit());

        long insertUnits = db.insert(UNITS_HISTORY_TABLE, null, cv);
        if (insertUnits == -1) {
            throw new SQLException("Failed to insert units");
        }
    }

    private void updateUnit(UnitsModel oldUnits, UnitsModel newUnits, SQLiteDatabase db) {

        ContentValues cv = new ContentValues();

        cv.put(HEIGHT, newUnits.getHeight());
        cv.put(WEIGHT, newUnits.getWeight());
        cv.put(HEIGHT_UNIT, newUnits.getHeight_unit());
        cv.put(WEIGHT_UNIT, newUnits.getWeight_unit());

        int updateUnits = db.update(UNITS_TABLE, cv, ID + " == ?",
                new String[]{String.valueOf(oldUnits.getId())});
        if (updateUnits == 0) {
            throw new SQLException("Failed to update units");
        }
    }

    UserModel getUserById(long userId) {

        String search = "SELECT * FROM " + USER_TABLE + " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(userId)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
            return new UserModel(
                    cursor.getLong(cursor.getColumnIndexOrThrow(ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PASSWORD)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TOKEN)),
                    getGender(cursor),
                    cursor.getLong(cursor.getColumnIndexOrThrow(UNITS)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(PERFORMANCE)),
                    getLevel(cursor),
                    cursor.getInt(cursor.getColumnIndexOrThrow(NOTIFICATION)) != 0,
                    getAccountStatus(cursor)
            );
        }
    }

    GoalsModel getGoalsById(long goalsId) {

        String search = "SELECT * FROM " + GOALS_TABLE + " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(goalsId)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
            return new GoalsModel(
                    cursor.getLong(cursor.getColumnIndexOrThrow(ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(STRENGTH)) != 0,
                    cursor.getInt(cursor.getColumnIndexOrThrow(MUSCLE)) != 0,
                    cursor.getInt(cursor.getColumnIndexOrThrow(FAT_LOSE)) != 0,
                    cursor.getInt(cursor.getColumnIndexOrThrow(TECHNIQUE)) != 0
            );
        }
    }

    PerformanceModel getPerformanceById(long performanceId) {

        String search = "SELECT * FROM " + PERFORMANCE_TABLE + " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(performanceId)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
           return new PerformanceModel(
                   cursor.getLong(cursor.getColumnIndexOrThrow(ID)),
                   cursor.getInt(cursor.getColumnIndexOrThrow(PULL)),
                   cursor.getInt(cursor.getColumnIndexOrThrow(PUSH)),
                   cursor.getInt(cursor.getColumnIndexOrThrow(SQUAD)),
                   cursor.getInt(cursor.getColumnIndexOrThrow(DIP))
           );
        }
    }

    UnitsModel getUnitsById(long unitsId) {

        String search = "SELECT * FROM " + UNITS_TABLE + " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(unitsId)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
            return new UnitsModel(
                    cursor.getLong(cursor.getColumnIndexOrThrow(ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(HEIGHT)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(WEIGHT)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(HEIGHT_UNIT)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(WEIGHT_UNIT))
            );
        }
    }

    private Gender getGender(@NonNull Cursor cursor) {
        return Gender.values()[cursor.getInt(cursor.getColumnIndexOrThrow(GENDER))];
    }
    private Level getLevel(@NonNull Cursor cursor) {
        return Level.values()[cursor.getInt(cursor.getColumnIndexOrThrow(LEVEL))];
    }

    private AccountStatus getAccountStatus(@NonNull Cursor cursor) {
        return AccountStatus.values()[cursor.getInt(cursor.getColumnIndexOrThrow(STATUS))];
    }
}
