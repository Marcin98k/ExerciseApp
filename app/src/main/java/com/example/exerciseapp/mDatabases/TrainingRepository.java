package com.example.exerciseapp.mDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.exerciseapp.mClasses.InsertResult;
import com.example.exerciseapp.mEnums.BodyParts;
import com.example.exerciseapp.mEnums.ExerciseType;
import com.example.exerciseapp.mEnums.FromWhere;
import com.example.exerciseapp.mEnums.Level;
import com.example.exerciseapp.mModels.BodyPartsModel;
import com.example.exerciseapp.mModels.ExerciseModel;
import com.example.exerciseapp.mModels.ExtensionExerciseModel;
import com.example.exerciseapp.mModels.WorkoutModel;

import java.util.ArrayList;
import java.util.List;

public class TrainingRepository extends SQLiteOpenHelper {

    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String IMAGE = "IMAGE";
    private static final String BODY_PARTS = "BODY_PARTS";

    //    Exercise table
    static final String EXERCISE_TABLE = "EXERCISE";

    //    Workout table
    static final String WORKOUT_TABLE = "WORKOUT";
    private static final String EXERCISES = "EXERCISES";

    //    Exercise table && Workout table
    private static final String LEVEL = "LEVEL";
    private static final String EQUIPMENT = "EQUIPMENT";
    private static final String KCAL = "KCAL";
    private static final String DURATION = "DURATION";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String FROM_WHERE = "FROM_WHERE";
    private static final String USER_ID = "USER_ID";

    //    Exercise extension table
    static final String EXERCISE_EXTENSION_TABLE = "EXERCISE_EXTENSION";
    private static final String EXERCISE_ID = "EXERCISE_ID";
    private static final String TYPE = "TYPE";
    private static final String NUMBER_OF_SERIES = "SERIES";
    private static final String VOLUME = "VOLUME";
    private static final String BREAK_LENGTH = "BREAK_LENGTH";

//    Equipment

    //    Body parts table
    static final String BODY_PARTS_TABLE = "BODY_PARTS";
    private static final String CHEST = "CHEST";
    private static final String BACK = "BACK";
    private static final String SHOULDERS = "SHOULDERS";
    private static final String ARMS = "ARMS";
    private static final String ABS = "ABS";
    private static final String LEGS = "LEGS";


    public TrainingRepository(@Nullable Context context) {
        super(context, "Training.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(createExerciseTable());
        sqLiteDatabase.execSQL(createWorkoutTable());
        sqLiteDatabase.execSQL(createExerciseExtensionTable());
        sqLiteDatabase.execSQL(createBodyPartsTable());
    }

    private String createExerciseTable() {
        return "CREATE TABLE " + EXERCISE_TABLE + " (" + ID + " INTEGER PRIMARY KEY, "
                + NAME + " TEXT, " + IMAGE + " TEXT, " + LEVEL + " INTEGER, "
                + EQUIPMENT + " TEXT, " + KCAL + " INTEGER, " + DURATION + " INTEGER, "
                + DESCRIPTION + " TEXT, " + FROM_WHERE + " INTEGER, " + USER_ID + " INTEGER, "
                + BODY_PARTS + " INTEGER)";
    }

    private String createWorkoutTable() {
        return "CREATE TABLE " + WORKOUT_TABLE + " (" + ID + " INTEGER PRIMARY KEY, "
                + NAME + " TEXT, " + IMAGE + " TEXT, " + LEVEL + " INTEGER, "
                + EQUIPMENT + " TEXT, " + KCAL + " INTEGER, " + DURATION + " INTEGER, "
                + DESCRIPTION + " TEXT, " + FROM_WHERE + " INTEGER, " + USER_ID + " INTEGER, "
                + EXERCISES + " TEXT, " + BODY_PARTS + " TEXT)";
    }

    private String createExerciseExtensionTable() {
        return "CREATE TABLE " + EXERCISE_EXTENSION_TABLE + " (" + ID + " INTEGER PRIMARY KEY, "
                + EXERCISE_ID + " INTEGER, " + TYPE + " INTEGER, "
                + NUMBER_OF_SERIES + " INTEGER, " + VOLUME + " INTEGER, "
                + BREAK_LENGTH + " INTEGER)";
    }

    private String createBodyPartsTable() {
        return "CREATE TABLE " + BODY_PARTS_TABLE + " (" + ID + " INTEGER PRIMARY KEY, "
                + CHEST + " INTEGER, " + BACK + " INTEGER, " + SHOULDERS + " INTEGER, "
                + ARMS + " INTEGER, " + ABS + " INTEGER, " + LEGS + " INTEGER)";
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    boolean createExercise(ExerciseModel exerciseModel,
                           ExtensionExerciseModel extensionExerciseModel) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            InsertResult exercise = insertExercise(exerciseModel);
            InsertResult extension = insertExtension(exercise.getIndex(), extensionExerciseModel);

            db.setTransactionSuccessful();
            return exercise.isSuccess() && extension.isSuccess();
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException("No exercise added");
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    private InsertResult insertExercise(@NonNull ExerciseModel exerciseModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues cv = new ContentValues();

            cv.put(NAME, exerciseModel.getName());
            cv.put(IMAGE, exerciseModel.getImage());
            cv.put(LEVEL, exerciseModel.getLevel().ordinal());
            cv.put(EQUIPMENT, exerciseModel.getEquipmentIds());
            cv.put(KCAL, exerciseModel.getKcal());
            cv.put(DURATION, exerciseModel.getDuration());
            cv.put(DESCRIPTION, exerciseModel.getDescription());
            cv.put(FROM_WHERE, exerciseModel.getFromWhere().ordinal());
            cv.put(USER_ID, exerciseModel.getUserId());
            cv.put(BODY_PARTS, exerciseModel.getBodyParts());

            long exerciseIndex = db.insert(EXERCISE_TABLE, null, cv);
            boolean success = exerciseIndex != -1;
            return new InsertResult(exerciseIndex, success);
        }
    }

    private InsertResult insertExtension(long exerciseId,
                                         @NonNull ExtensionExerciseModel extensionExerciseModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues cv = new ContentValues();

            cv.put(EXERCISE_ID, exerciseId);
            cv.put(TYPE, extensionExerciseModel.getExerciseType().ordinal());
            cv.put(NUMBER_OF_SERIES, extensionExerciseModel.getNumberOfSeries());
            cv.put(VOLUME, extensionExerciseModel.getVolume());
            cv.put(BREAK_LENGTH, extensionExerciseModel.getBreakLength());

            long index = db.insert(EXERCISE_EXTENSION_TABLE, null, cv);
            boolean success = index != -1;

            return new InsertResult(index, success);
        }
    }

    boolean createWorkout(@NonNull WorkoutModel workoutModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues cv = new ContentValues();

            cv.put(NAME, workoutModel.getName());
            cv.put(IMAGE, workoutModel.getImage());
            cv.put(LEVEL, workoutModel.getLevel().ordinal());
            cv.put(EQUIPMENT, workoutModel.getEquipmentIds());
            cv.put(KCAL, workoutModel.getKcal());
            cv.put(DURATION, workoutModel.getDuration());
            cv.put(DESCRIPTION, workoutModel.getDescription());
            cv.put(FROM_WHERE, workoutModel.getFromWhere().ordinal());
            cv.put(USER_ID, workoutModel.getUserId());
            cv.put(EXERCISES, workoutModel.getExercisesIDs());
            cv.put(BODY_PARTS, workoutModel.getBodyPartsIDs());

            return db.insert(WORKOUT_TABLE, null, cv) != -1;
        }
    }

    boolean createBodyPartsCombination(@NonNull BodyPartsModel bodyPartsModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues cv = new ContentValues();

            cv.put(CHEST, bodyPartsModel.isChestStatus());
            cv.put(BACK, bodyPartsModel.isChestStatus());
            cv.put(SHOULDERS, bodyPartsModel.isChestStatus());
            cv.put(ARMS, bodyPartsModel.isChestStatus());
            cv.put(ABS, bodyPartsModel.isChestStatus());
            cv.put(LEGS, bodyPartsModel.isChestStatus());

            return db.insert(BODY_PARTS_TABLE, null, cv) != -1;
        }
    }

    List<ExerciseModel> getAllExercises() {

        String search = "SELECT * FROM " + EXERCISE_TABLE;
        List<ExerciseModel> result = new ArrayList<>();

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, null)) {

            while (cursor.moveToNext()) {
                result.add(new ExerciseModel(
                        cursor.getLong(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(NAME)),
                        getLevel(cursor),
                        cursor.getString(cursor.getColumnIndexOrThrow(EQUIPMENT)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(KCAL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DURATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)),
                        getFromWhere(cursor),
                        cursor.getLong(cursor.getColumnIndexOrThrow(USER_ID)),
                        cursor.getLong(cursor.getColumnIndexOrThrow(BODY_PARTS))
                ));
            }
        }
        return result;
    }

    ExerciseModel getExerciseById(long exerciseId) {

        String search = "SELECT * FROM " + EXERCISE_TABLE + " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(exerciseId)};

        ExerciseModel result;

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {

            if (cursor.moveToFirst()) {
                result = new ExerciseModel(
                        cursor.getLong(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)),
                        getLevel(cursor),
                        cursor.getString(cursor.getColumnIndexOrThrow(EQUIPMENT)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(KCAL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DURATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)),
                        getFromWhere(cursor),
                        cursor.getLong(cursor.getColumnIndexOrThrow(USER_ID)),
                        cursor.getLong(cursor.getColumnIndexOrThrow(BODY_PARTS))
                );
            } else {
                result = new ExerciseModel();
            }
        }
        return result;
    }

    List<WorkoutModel> getAllWorkouts() {

        String search = "SELECT * FROM " + WORKOUT_TABLE;
        List<WorkoutModel> result = new ArrayList<>();

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, null)) {

            while (cursor.moveToNext()) {
                result.add(new WorkoutModel(
                        cursor.getLong(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(NAME)),
                        getLevel(cursor),
                        cursor.getString(cursor.getColumnIndexOrThrow(EQUIPMENT)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(KCAL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DURATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)),
                        getFromWhere(cursor),
                        cursor.getLong(cursor.getColumnIndexOrThrow(USER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(EXERCISES)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BODY_PARTS))
                ));
            }
        }
        return result;
    }

    WorkoutModel getWorkoutById(long workoutId) {

        String search = "SELECT * FROM " + WORKOUT_TABLE + " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(workoutId)};

        WorkoutModel result;

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {

            if (cursor.moveToFirst()) {
                result = new WorkoutModel(
                        cursor.getLong(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(NAME)),
                        getLevel(cursor),
                        cursor.getString(cursor.getColumnIndexOrThrow(EQUIPMENT)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(KCAL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DURATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)),
                        getFromWhere(cursor),
                        cursor.getLong(cursor.getColumnIndexOrThrow(USER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(EXERCISES)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BODY_PARTS))
                );
            } else {
                result = new WorkoutModel();
            }
        }
        return result;
    }

    List<ExtensionExerciseModel> getAllExercisesExtensions() {

        String search = "SELECT * FROM " + EXERCISE_EXTENSION_TABLE;
        List<ExtensionExerciseModel> result = new ArrayList<>();

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, null)) {

            while (cursor.moveToNext()) {
                result.add(new ExtensionExerciseModel(
                        cursor.getLong(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getLong(cursor.getColumnIndexOrThrow(EXERCISE_ID)),
                        getExerciseType(cursor),
                        cursor.getInt(cursor.getColumnIndexOrThrow(NUMBER_OF_SERIES)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(VOLUME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(BREAK_LENGTH))
                ));
            }
        }
        return result;
    }

    List<ExtensionExerciseModel> getAllExerciseExtensionsById(long exerciseId) {

        String search = "SELECT * FROM " + EXERCISE_EXTENSION_TABLE
                + " WHERE " + EXERCISE_ID + " == ?";
        String[] args = {String.valueOf(exerciseId)};

        List<ExtensionExerciseModel> result = new ArrayList<>();

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {

            while (cursor.moveToNext()) {
                result.add(new ExtensionExerciseModel(
                        cursor.getLong(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getLong(cursor.getColumnIndexOrThrow(EXERCISE_ID)),
                        getExerciseType(cursor),
                        cursor.getInt(cursor.getColumnIndexOrThrow(NUMBER_OF_SERIES)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(VOLUME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(BREAK_LENGTH))
                ));
            }
        }
        return result;
    }

    List<ExtensionExerciseModel> getAllUserExerciseExtensionsById(long userId, long exerciseId) {

        String search = "SELECT * FROM " + EXERCISE_EXTENSION_TABLE
                + " WHERE " + USER_ID + " == ? AND " + EXERCISE_ID + " == ?";
        String[] args = {String.valueOf(userId), String.valueOf(exerciseId)};

        List<ExtensionExerciseModel> result = new ArrayList<>();

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {

            while (cursor.moveToNext()) {
                result.add(new ExtensionExerciseModel(
                        cursor.getLong(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getLong(cursor.getColumnIndexOrThrow(EXERCISE_ID)),
                        getExerciseType(cursor),
                        cursor.getInt(cursor.getColumnIndexOrThrow(NUMBER_OF_SERIES)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(VOLUME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(BREAK_LENGTH))
                ));
            }
        }
        return result;
    }

    ExtensionExerciseModel getExerciseExtensionById(long exerciseId) {

        String search = "SELECT * FROM " + EXERCISE_EXTENSION_TABLE + " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(exerciseId)};

        ExtensionExerciseModel result;

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {

            if (cursor.moveToFirst()) {
                result = new ExtensionExerciseModel(
                        cursor.getLong(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getLong(cursor.getColumnIndexOrThrow(EXERCISE_ID)),
                        getExerciseType(cursor),
                        cursor.getInt(cursor.getColumnIndexOrThrow(NUMBER_OF_SERIES)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(VOLUME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(BREAK_LENGTH))
                );
            } else {
                result = new ExtensionExerciseModel();
            }
        }
        return result;
    }

    List<BodyPartsModel> getAllBodyPartsCombinations() {

        String search = "SELECT * FROM " + BODY_PARTS_TABLE;

        List<BodyPartsModel> result = new ArrayList<>();

        try (SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(search, null)) {

            while (cursor.moveToNext()) {
                result.add(new BodyPartsModel(
                   cursor.getLong(cursor.getColumnIndexOrThrow(ID)),
                   cursor.getInt(cursor.getColumnIndexOrThrow(CHEST)) != 0,
                   cursor.getInt(cursor.getColumnIndexOrThrow(BACK)) != 0,
                   cursor.getInt(cursor.getColumnIndexOrThrow(SHOULDERS)) != 0,
                   cursor.getInt(cursor.getColumnIndexOrThrow(ARMS)) != 0,
                   cursor.getInt(cursor.getColumnIndexOrThrow(ABS)) != 0,
                   cursor.getInt(cursor.getColumnIndexOrThrow(LEGS)) != 0
                ));
            }
        }
        return result;
    }

    List<BodyPartsModel> getAllCombinationsBySpecificPart(@NonNull BodyParts part) {

        String search = "SELECT * FROM " + BODY_PARTS_TABLE + " WHERE " + part.name() + " == 1";

        List<BodyPartsModel> result = new ArrayList<>();

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, null)) {

            while (cursor.moveToNext()) {
                result.add(new BodyPartsModel(
                        cursor.getLong(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(CHEST)) != 0,
                        cursor.getInt(cursor.getColumnIndexOrThrow(BACK)) != 0,
                        cursor.getInt(cursor.getColumnIndexOrThrow(SHOULDERS)) != 0,
                        cursor.getInt(cursor.getColumnIndexOrThrow(ARMS)) != 0,
                        cursor.getInt(cursor.getColumnIndexOrThrow(ABS)) != 0,
                        cursor.getInt(cursor.getColumnIndexOrThrow(LEGS)) != 0
                ));
            }
        }
        return result;
    }

    BodyPartsModel getBodyPartsCombinationById(long bodyPartsCombinationId) {

        String search = "SELECT * FROM " + BODY_PARTS_TABLE + " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(bodyPartsCombinationId)};

        BodyPartsModel result;

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {

            if (cursor.moveToFirst()) {
                result = new BodyPartsModel(
                        cursor.getLong(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(CHEST)) != 0,
                        cursor.getInt(cursor.getColumnIndexOrThrow(BACK)) != 0,
                        cursor.getInt(cursor.getColumnIndexOrThrow(SHOULDERS)) != 0,
                        cursor.getInt(cursor.getColumnIndexOrThrow(ARMS)) != 0,
                        cursor.getInt(cursor.getColumnIndexOrThrow(ABS)) != 0,
                        cursor.getInt(cursor.getColumnIndexOrThrow(LEGS)) != 0
                );
            } else {
                result = new BodyPartsModel();
            }
        }
        return result;
    }

    private FromWhere getFromWhere(@NonNull Cursor cursor) {
        return FromWhere.values()[cursor.getInt(cursor.getColumnIndexOrThrow(FROM_WHERE))];
    }

    private Level getLevel(@NonNull Cursor cursor) {
        return Level.values()[cursor.getInt(cursor.getColumnIndexOrThrow(LEVEL))];
    }

    private ExerciseType getExerciseType(@NonNull Cursor cursor) {
        return ExerciseType.values()[cursor.getInt(cursor.getColumnIndexOrThrow(TYPE))];
    }
}
