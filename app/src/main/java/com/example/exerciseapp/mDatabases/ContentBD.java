package com.example.exerciseapp.mDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.exerciseapp.mClasses.InsertResult;
import com.example.exerciseapp.mModels.AppearanceBlockModel;
import com.example.exerciseapp.mModels.CustomExerciseModel;
import com.example.exerciseapp.mModels.CustomUserExerciseModel;
import com.example.exerciseapp.mModels.ExerciseModel;
import com.example.exerciseapp.mModels.IntegerModel;
import com.example.exerciseapp.mModels.StringModel;
import com.example.exerciseapp.mModels.TaskDateModel;
import com.example.exerciseapp.mModels.UserExercisePerformedModel;

import java.util.ArrayList;
import java.util.List;

public class ContentBD extends SQLiteOpenHelper {

    private static final String CLASS_TAG = "ContentDB";
    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String IMAGE = "IMAGE";
    private static final String ICON = "ICON";
    private static final String OBJ_DESCRIPTION = "OBJ_DESCRIPTION";
    private static final String TAG = "TAG";
    private static final String INT_VAL = "VALUE";
    private static final String USER_ID = "USER";


    private static final String BODY_PARTS_TAB = "BODY_PART_TAB";
    private static final String BODY_PARTS_CHEST = "CHEST";
    private static final String BODY_PARTS_BACK = "BACK";
    private static final String BODY_PARTS_SHOULDERS = "SHOULDERS";
    private static final String BODY_PARTS_ARMS = "ARMS";
    private static final String BODY_PARTS_ABS = "ABS";
    private static final String BODY_PARTS_LEGS = "LEGS";
    private static final String BODY_PARTS_FULL_BODY = "FULL_BODY";


    private static final String APPEARANCE_TAB = "APPEARANCE_TAB";


    private static final String WORKOUT_TAB = "WORKOUT_TAB";
    private static final String WORKOUT_LEVEL_ID = "LEVEL";
    private static final String WORKOUT_BODY_PARTS_ID = "BODY_PARTS";
    private static final String WORKOUT_EQUIPMENT = "EQUIPMENT";
    private static final String WORKOUT_KCAL = "KCAL";
    private static final String WORKOUT_DURATION = "DURATION";
    private static final String WORKOUT_DESCRIPTION = "DESCRIPTION";
    private static final String WORKOUT_EXERCISES_ID = "EXERCISES";


    private static final String EQUIPMENT_TAB = "EQUIPMENT_TAB";


    private static final String EXERCISE_TAB = "EXERCISE_TAB";
    private static final String EXERCISE_LEVEL_ID = "LEVEL";
    private static final String EXERCISE_BODY_PARTS_ID = "BODY_PARTS";
    private static final String EXERCISE_EQUIPMENT = "EQUIPMENT";
    private static final String EXERCISE_TYPE = "TYPE";
    private static final String EXERCISE_KCAL = "KCAL";
    private static final String EXERCISE_DURATION = "DURATION";
    private static final String EXERCISE_DESCRIPTION = "DESCRIPTION";
    private static final String EXERCISE_EXTENSIONS_ID = "EXERCISE_EXTENSIONS_ID";


    private static final String EXERCISE_EXTENSIONS_TAB = "EXERCISE_EXTENSIONS_TAB";
    private static final String EXERCISE_EXTENSIONS_SETS = "SETS";
    private static final String EXERCISE_EXTENSIONS_REPETITIONS = "REPETITIONS";
    private static final String EXERCISE_EXTENSIONS_TIME = "TIME";
    private static final String EXERCISE_EXTENSIONS_REST = "REST";
    private static final String EXERCISE_EXTENSIONS_EXERCISE_ID = "EXERCISE_EXTENSIONS_EXERCISE_ID";


    private static final String CUSTOM_USER_EXERCISE_TAB = "CUSTOM_USER_EXERCISE_TAB";
    private static final String CUSTOM_USER_EXERCISE_NAME = "CUSTOM_USER_EXERCISE_NAME";
    private static final String CUSTOM_USER_EXERCISE_TYPE = "CUSTOM_USER_EXERCISE_TYPE";
    private static final String CUSTOM_USER_EXERCISE_ID = "CUSTOM_USER_EXERCISE_ID";
    private static final String CUSTOM_USER_EXERCISE_EXERCISE_ID = "CUSTOM_USER_EXERCISE_EXERCISE_ID";
    private static final String CUSTOM_USER_EXERCISE_EXERCISE_EXTENSION_ID = "CUSTOM_USER_EXERCISE_EXERCISE_EXTENSION_ID";

    private static final String USER_EXERCISE_PERFORMED_TAB = "EXERCISE_PERFORMED_TAB";
    private static final String USER_EXERCISE_PERFORMED_DATE = "EXERCISE_PERFORMED_DATE";
    private static final String USER_EXERCISE_PERFORMED_MAIN_EXERCISE_ID = "EXERCISE_PERFORMED_MAIN_EXERCISE_ID";
    private static final String USER_EXERCISE_PERFORMED_SUM_TIME = "EXERCISE_PERFORMED_SUM_TIME";
    private static final String USER_EXERCISE_PERFORMED_EXTENSIONS_ID = "EXERCISE_PERFORMED_EXTENSION";


    private static final String DATE_TAB = "DATE_TAB";
    private static final String DATE_USER_ID = "USER_ID";
    private static final String DATE_NUMBER = "DATE";
    private static final String DATE_TASK_ID = "TASK_ID";
    private static final String DATE_STATUS = "STATUS";


    private static final String USER_BIO_TAB = "USER_BIO_TAB";
    private static final String USER_BIO_DATE = "USER_BIO_DATE";
    private static final String USER_BIO_HEIGHT = "USER_BIO_HEIGHT";
    private static final String USER_BIO_WEIGHT = "USER_BIO_WEIGHT";
    private static final String USER_BIO_ID = "USER_BIO_ID";

    public ContentBD(@Nullable Context context) {
        super(context, "ContentDatabase.db",
                null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createUserBioTab = "CREATE TABLE " + USER_BIO_TAB + " ("
                + USER_BIO_DATE + " INTEGER PRIMARY KEY , "
                + USER_BIO_HEIGHT + " INTEGER, " + USER_BIO_WEIGHT + " INTEGER, "
                + USER_BIO_ID + " INTEGER)";
        sqLiteDatabase.execSQL(createUserBioTab);

        String createBodyPartTab = "CREATE TABLE " + BODY_PARTS_TAB + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BODY_PARTS_CHEST + " INTEGER, " + BODY_PARTS_BACK + " INTEGER, "
                + BODY_PARTS_SHOULDERS + " INTEGER, " + BODY_PARTS_ARMS + " INTEGER, "
                + BODY_PARTS_ABS + " INTEGER, " + BODY_PARTS_LEGS + " INTEGER, "
                + BODY_PARTS_FULL_BODY + " INTEGER)";
        sqLiteDatabase.execSQL(createBodyPartTab);

        String createWorkoutTab = "CREATE TABLE " + WORKOUT_TAB + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT, " + IMAGE + " TEXT, "
                + WORKOUT_LEVEL_ID + " INTEGER, "
                + WORKOUT_BODY_PARTS_ID + " TEXT, "
                + WORKOUT_EQUIPMENT + " TEXT, "
                + WORKOUT_KCAL + " INTEGER, "
                + WORKOUT_DURATION + " INTEGER, "
                + WORKOUT_DESCRIPTION + " TEXT,"
                + WORKOUT_EXERCISES_ID + " INTEGER)";
        sqLiteDatabase.execSQL(createWorkoutTab);

        String createEquipmentTab = "CREATE TABLE " + EQUIPMENT_TAB + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT, " + ICON + " TEXT)";
        sqLiteDatabase.execSQL(createEquipmentTab);

        String createExerciseTab = "CREATE TABLE " + EXERCISE_TAB + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT, " + IMAGE + " TEXT, "
                + EXERCISE_LEVEL_ID + " INTEGER, "
                + EXERCISE_BODY_PARTS_ID + " INTEGER, "
                + EXERCISE_EQUIPMENT + " TEXT, "
                + EXERCISE_TYPE + " INTEGER,"
                + EXERCISE_KCAL + " INTEGER, "
                + EXERCISE_DURATION + " INTEGER, "
                + EXERCISE_DESCRIPTION + " TEXT, "
                + EXERCISE_EXTENSIONS_ID + " INTEGER)";
        sqLiteDatabase.execSQL(createExerciseTab);

        String createExerciseExtensionTab = "CREATE TABLE " + EXERCISE_EXTENSIONS_TAB + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EXERCISE_EXTENSIONS_EXERCISE_ID + " INTEGER, "
                + EXERCISE_EXTENSIONS_SETS + " INTEGER, "
                + EXERCISE_EXTENSIONS_REPETITIONS + " INTEGER, "
                + EXERCISE_EXTENSIONS_TIME + " INTEGER, "
                + EXERCISE_EXTENSIONS_REST + " INTEGER)";
        sqLiteDatabase.execSQL(createExerciseExtensionTab);

        String createUserExercise = "CREATE TABLE " + USER_EXERCISE_PERFORMED_TAB + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_ID + " INTEGER, "
                + USER_EXERCISE_PERFORMED_DATE + " TEXT, "
                + USER_EXERCISE_PERFORMED_MAIN_EXERCISE_ID + " INTGER, "
                + USER_EXERCISE_PERFORMED_EXTENSIONS_ID + " INTEGER, "
                + USER_EXERCISE_PERFORMED_SUM_TIME + " TEXT)";
        sqLiteDatabase.execSQL(createUserExercise);

        String createAppearanceTab = "CREATE TABLE " + APPEARANCE_TAB + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT, " + ICON + " INTEGER ,"
                + INT_VAL + " INTEGER, " + OBJ_DESCRIPTION + " TEXT, "
                + TAG + " TEXT)";
        sqLiteDatabase.execSQL(createAppearanceTab);

        String createDateTab = "CREATE TABLE " + DATE_TAB + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE_USER_ID + " INTEGER, " + DATE_NUMBER + " TEXT, "
                + DATE_TASK_ID + " INTEGER, " + DATE_STATUS + " INTEGER)";
        sqLiteDatabase.execSQL(createDateTab);

        String createCustomUserExerciseTab = "CREATE TABLE " + CUSTOM_USER_EXERCISE_TAB + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CUSTOM_USER_EXERCISE_NAME + " TEXT, "
                + CUSTOM_USER_EXERCISE_TYPE + " INTEGER, "
                + CUSTOM_USER_EXERCISE_ID + " INTEGER, "
                + CUSTOM_USER_EXERCISE_EXERCISE_ID + " INTEGER, "
                + CUSTOM_USER_EXERCISE_EXERCISE_EXTENSION_ID + " INTEGER)";
        sqLiteDatabase.execSQL(createCustomUserExerciseTab);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertUserBio(IntegerModel integerModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(USER_BIO_DATE, integerModel.getId());
            values.put(USER_BIO_HEIGHT, integerModel.getFirstValue());
            values.put(USER_BIO_WEIGHT, integerModel.getSecondValue());
            values.put(USER_BIO_ID, integerModel.getThirdValue());

            return db.insert(USER_BIO_TAB, null, values) != -1;
        }
    }

    public boolean updateUserBioWeight(long userID, int date, int weight) {

        String cause = USER_BIO_ID + " = ? AND " + USER_BIO_DATE + " = ?";
        String[] args = {String.valueOf(userID), String.valueOf(date)};

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(USER_BIO_WEIGHT, weight);

            return db.update(USER_BIO_TAB, values, cause, args) != -1;
        }
    }

    public boolean updateUserBioHeight(long userID, int date, int height) {

        String cause = USER_BIO_ID + " = ? AND " + USER_BIO_DATE + " = ?";
        String[] args = {String.valueOf(userID), String.valueOf(date)};

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(USER_BIO_HEIGHT, height);

            return db.update(USER_BIO_TAB, values, cause, args) != -1;
        }
    }

    public List<IntegerModel> getUserBioWeight(int value) {

        List<IntegerModel> result = new ArrayList<>();
        String search = "SELECT " + USER_BIO_DATE + "," + USER_BIO_WEIGHT + " FROM " +
                USER_BIO_TAB + " WHERE " + USER_BIO_ID + " = ? AND " + USER_BIO_WEIGHT +
                " > 0 ORDER BY " + USER_BIO_DATE + " ASC LIMIT 7";
        String[] selectedArgs = {String.valueOf(value)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, selectedArgs)) {
            while (cursor.moveToNext()) {
                IntegerModel integerModel = new IntegerModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_BIO_DATE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_BIO_WEIGHT)));
                result.add(integerModel);
            }
        }
        return result;
    }

    public boolean insertCustomUserExercise(CustomUserExerciseModel customUserExerciseModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(CUSTOM_USER_EXERCISE_NAME, customUserExerciseModel.getName());
            values.put(CUSTOM_USER_EXERCISE_TYPE, customUserExerciseModel.getType());
            values.put(CUSTOM_USER_EXERCISE_ID, customUserExerciseModel.getUserId());
            values.put(CUSTOM_USER_EXERCISE_EXERCISE_ID, customUserExerciseModel.getExerciseID());
            values.put(CUSTOM_USER_EXERCISE_EXERCISE_EXTENSION_ID, customUserExerciseModel
                    .getExerciseExtensionID());

            return db.insert(CUSTOM_USER_EXERCISE_TAB, null, values) != -1;
        }
    }

    public List<ExerciseModel> showUserExercise() {

        String search = "SELECT * FROM " + CUSTOM_USER_EXERCISE_TAB;
        List<ExerciseModel> result = new ArrayList<>();
        CustomExerciseModel customExerciseModel = null;

        synchronized (this) {
            try (SQLiteDatabase db = getReadableDatabase();
                 Cursor cursor = db.rawQuery(search, null)) {
                while (cursor.moveToNext()) {
                    customExerciseModel = new CustomExerciseModel(
                            cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(CUSTOM_USER_EXERCISE_NAME)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(CUSTOM_USER_EXERCISE_TYPE)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(CUSTOM_USER_EXERCISE_ID)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(CUSTOM_USER_EXERCISE_EXERCISE_ID)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(CUSTOM_USER_EXERCISE_EXERCISE_EXTENSION_ID)));

                    String exercise = "SELECT * FROM " + EXERCISE_TAB +
                            " WHERE " + ID + " == " + customExerciseModel.getMainExerciseId();

                    ExerciseModel mainExercise = null;
                    if (customExerciseModel.getMainExerciseId() != 0) {
                        try (SQLiteDatabase mainDB = getReadableDatabase();
                             Cursor mainCursor = mainDB.rawQuery(exercise, null)) {
                            if (mainCursor.moveToFirst()) {
                                mainExercise = new ExerciseModel(
                                        mainCursor.getInt(mainCursor.getColumnIndexOrThrow(ID)),
                                        mainCursor.getString(mainCursor.getColumnIndexOrThrow(NAME)),
                                        mainCursor.getString(mainCursor.getColumnIndexOrThrow(IMAGE)),
                                        mainCursor.getInt(mainCursor.getColumnIndexOrThrow(EXERCISE_LEVEL_ID)),
                                        mainCursor.getInt(mainCursor.getColumnIndexOrThrow(EXERCISE_BODY_PARTS_ID)),
                                        mainCursor.getString(mainCursor.getColumnIndexOrThrow(EXERCISE_EQUIPMENT)),
                                        mainCursor.getInt(mainCursor.getColumnIndexOrThrow(EXERCISE_TYPE)),
                                        mainCursor.getInt(mainCursor.getColumnIndexOrThrow(EXERCISE_KCAL)),
                                        mainCursor.getInt(mainCursor.getColumnIndexOrThrow(EXERCISE_DURATION)),
                                        mainCursor.getString(mainCursor.getColumnIndexOrThrow(EXERCISE_DESCRIPTION)),
                                        mainCursor.getInt(mainCursor.getColumnIndexOrThrow(EXERCISE_EXTENSIONS_ID)),
                                        1);
                            }
                        }
                    } else {
                        mainExercise = new ExerciseModel();
                    }
                    String extension = "SELECT * FROM " + EXERCISE_EXTENSIONS_TAB +
                            " WHERE " + ID + " == " + customExerciseModel.getExerciseExtensionId();

                    IntegerModel extensionModel = null;
                    if (customExerciseModel.getExerciseExtensionId() != 0) {
                        try (SQLiteDatabase extensionDB = getReadableDatabase();
                             Cursor extensionCursor = extensionDB.rawQuery(extension, null)) {
                            if (extensionCursor.moveToFirst()) {
                                extensionModel = new IntegerModel(
                                        extensionCursor.getInt(extensionCursor.getColumnIndexOrThrow(ID)),
                                        extensionCursor.getInt(extensionCursor.getColumnIndexOrThrow(EXERCISE_EXTENSIONS_EXERCISE_ID)),
                                        extensionCursor.getInt(extensionCursor.getColumnIndexOrThrow(EXERCISE_EXTENSIONS_SETS)),
                                        extensionCursor.getInt(extensionCursor.getColumnIndexOrThrow(EXERCISE_EXTENSIONS_REPETITIONS)),
                                        extensionCursor.getInt(extensionCursor.getColumnIndexOrThrow(EXERCISE_EXTENSIONS_TIME)),
                                        extensionCursor.getInt(extensionCursor.getColumnIndexOrThrow(EXERCISE_EXTENSIONS_REST)));
                            }
                        }
                    }
                    if (customExerciseModel.getMainExerciseId() <= 0) {
                        result.add(new ExerciseModel(
                                (int) customExerciseModel.getId(),
                                customExerciseModel.getName(),
                                "",
                                0,
                                0,
                                "",
                                customExerciseModel.getType(),
                                0,
                                0,
                                "",
                                (int) customExerciseModel.getExerciseExtensionId(),
                                1));
                    } else {
                        result.add(new ExerciseModel(
                                (int) customExerciseModel.getId(),
                                customExerciseModel.getName(),
                                mainExercise.getImage(),
                                mainExercise.getLevel(),
                                mainExercise.getBodyParts(),
                                mainExercise.getEquipment(),
                                customExerciseModel.getType(),
                                mainExercise.getKcal(),
                                mainExercise.getDuration(),
                                mainExercise.getDescription(),
                                (int) customExerciseModel.getExerciseExtensionId(),
                                1));
                    }
                }
            }
        }
        return result;
    }

    public List<ExerciseModel> showUserExerciseById(long id) {

        String search = "SELECT * FROM " + CUSTOM_USER_EXERCISE_TAB +
                " WHERE " + ID + " ==?";
        String[] args = {String.valueOf(id)};
        List<ExerciseModel> result = new ArrayList<>();
        CustomExerciseModel customExerciseModel = null;


        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
            if (cursor.moveToFirst()) {
                customExerciseModel = new CustomExerciseModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(CUSTOM_USER_EXERCISE_NAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(CUSTOM_USER_EXERCISE_TYPE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(CUSTOM_USER_EXERCISE_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(CUSTOM_USER_EXERCISE_EXERCISE_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(CUSTOM_USER_EXERCISE_EXERCISE_EXTENSION_ID)));

                List<ExerciseModel> mainExercise = null;
                if (customExerciseModel.getMainExerciseId() > 0) {
                    mainExercise = showExerciseById(customExerciseModel.getMainExerciseId());
                } else {
                    mainExercise = new ArrayList<>();
                    mainExercise.add(new ExerciseModel(
                            (int) customExerciseModel.getId(),
                            customExerciseModel.getName(),
                            "Undefined",
                            -1,
                            -1,
                            "Undefined",
                            customExerciseModel.getType(),
                            -1,
                            -1,
                            "Undefined",
                            (int) customExerciseModel.getExerciseExtensionId(),
                            1));
                }
                result.add(new ExerciseModel(
                        (int) customExerciseModel.getId(),
                        customExerciseModel.getName(),
                        mainExercise.get(0).getImage(),
                        mainExercise.get(0).getLevel(),
                        mainExercise.get(0).getBodyParts(),
                        mainExercise.get(0).getEquipment(),
                        customExerciseModel.getType(),
                        mainExercise.get(0).getKcal(),
                        mainExercise.get(0).getKcal(),
                        mainExercise.get(0).getDescription(),
                        (int) customExerciseModel.getExerciseExtensionId(),
                        1));
            }
            return result;
        }
    }

    public boolean insertTaskWithDate(TaskDateModel taskDateModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(DATE_USER_ID, taskDateModel.getUserId());
            values.put(DATE_NUMBER, taskDateModel.getDate());
            values.put(DATE_TASK_ID, taskDateModel.getTaskId());
            values.put(DATE_STATUS, taskDateModel.getStatus());

            return db.insert(DATE_TAB, null, values) != -1;
        }
    }

    public List<TaskDateModel> showTaskWithDate(String value) {

        List<TaskDateModel> result = new ArrayList<>();
        String search = "SELECT * FROM " + DATE_TAB +
                " WHERE " + DATE_NUMBER + " == ?";
        String[] args = {String.valueOf(value)};
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
            if (cursor.moveToFirst()) {
                TaskDateModel taskDateModel = new TaskDateModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DATE_USER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DATE_NUMBER)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DATE_TASK_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DATE_STATUS)));
                result.add(taskDateModel);
            }
        }
        return result;
    }

    public boolean insertBodyParts(IntegerModel integerModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(BODY_PARTS_CHEST, integerModel.getFirstValue());
            values.put(BODY_PARTS_BACK, integerModel.getSecondValue());
            values.put(BODY_PARTS_SHOULDERS, integerModel.getThirdValue());
            values.put(BODY_PARTS_ARMS, integerModel.getForthValue());
            values.put(BODY_PARTS_ABS, integerModel.getFifthValue());
            values.put(BODY_PARTS_LEGS, integerModel.getSixthValue());
            values.put(BODY_PARTS_FULL_BODY, integerModel.getSeventhValue());

            return db.insert(BODY_PARTS_TAB, null, values) != -1;
        }
    }

    public List<String> showBodyPartsById(long id) {
        List<String> selectedParts = new ArrayList<>();
        String cause = "SELECT * FROM " + BODY_PARTS_TAB +
                " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(id)};

        String[] bodyPartsStr = {"Chest", "Back", "Shoulders", "Arms", "ABS", "Legs", "Full body"};
        String[] bodyPartsColumns = {BODY_PARTS_CHEST, BODY_PARTS_BACK, BODY_PARTS_SHOULDERS,
                BODY_PARTS_ARMS, BODY_PARTS_ABS, BODY_PARTS_LEGS, BODY_PARTS_FULL_BODY};
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(cause, args)) {
            if (cursor.moveToFirst()) {
                for (int i = 0; i < bodyPartsColumns.length; i++) {
                    if (cursor.getInt(cursor.getColumnIndexOrThrow(bodyPartsColumns[i])) == 1) {
                        selectedParts.add(bodyPartsStr[i]);
                    }
                }
            }
        }
        return selectedParts;
    }

    public boolean insertExerciseExtend(IntegerModel integerModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(EXERCISE_EXTENSIONS_EXERCISE_ID, integerModel.getFirstValue());
            values.put(EXERCISE_EXTENSIONS_SETS, integerModel.getSecondValue());
            values.put(EXERCISE_EXTENSIONS_REPETITIONS, integerModel.getThirdValue());
            values.put(EXERCISE_EXTENSIONS_TIME, integerModel.getForthValue());
            values.put(EXERCISE_EXTENSIONS_REST, integerModel.getFifthValue());

            return db.insert(EXERCISE_EXTENSIONS_TAB, null, values) != -1;
        }
    }

    public InsertResult insertExerciseExtension(IntegerModel integerModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(EXERCISE_EXTENSIONS_EXERCISE_ID, integerModel.getFirstValue());
            values.put(EXERCISE_EXTENSIONS_SETS, integerModel.getSecondValue());
            values.put(EXERCISE_EXTENSIONS_REPETITIONS, integerModel.getThirdValue());
            values.put(EXERCISE_EXTENSIONS_TIME, integerModel.getForthValue());
            values.put(EXERCISE_EXTENSIONS_REST, integerModel.getFifthValue());

            long insert = db.insert(EXERCISE_EXTENSIONS_TAB, null, values);
            boolean success = insert != -1;

            return new InsertResult(insert, success);
        }
    }

    public boolean insertAppearance(AppearanceBlockModel appearanceBlockModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(NAME, appearanceBlockModel.getName());
            values.put(ICON, appearanceBlockModel.getIcon());
            values.put(INT_VAL, appearanceBlockModel.getValue());
            values.put(OBJ_DESCRIPTION, appearanceBlockModel.getDescription());
            values.put(TAG, appearanceBlockModel.getTag());

            return db.insert(APPEARANCE_TAB, null, values) != -1;
        }
    }

    public boolean insertUserSummaryExercise(UserExercisePerformedModel performedModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(USER_ID, performedModel.getId());
            values.put(USER_EXERCISE_PERFORMED_DATE, performedModel.getDate());
            values.put(USER_EXERCISE_PERFORMED_MAIN_EXERCISE_ID, performedModel.getExerciseID());
            values.put(USER_EXERCISE_PERFORMED_EXTENSIONS_ID, performedModel.getExtensionID());
            values.put(USER_EXERCISE_PERFORMED_SUM_TIME, performedModel.getSumTime());

            return db.insert(USER_EXERCISE_PERFORMED_TAB, null, values) != -1;
        }
    }

    public boolean insertEquipment(StringModel stringModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(NAME, stringModel.getName());
            values.put(ICON, stringModel.getIntValue());

            return db.insert(EQUIPMENT_TAB, null, values) != -1;
        }
    }

    public StringModel showEquipment(long id) {

        StringModel stringModel = null;
        String search = "SELECT * FROM " + EQUIPMENT_TAB +
                " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(id)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
            if (cursor.moveToFirst()) {
                stringModel = new StringModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(NAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(ICON)));
            }
        }
        return stringModel;
    }

    public boolean insertExercise(ExerciseModel exerciseModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(NAME, exerciseModel.getName());
            values.put(IMAGE, exerciseModel.getImage());
            values.put(EXERCISE_LEVEL_ID, exerciseModel.getLevel());
            values.put(EXERCISE_BODY_PARTS_ID, exerciseModel.getBodyParts());
            values.put(EXERCISE_EQUIPMENT, exerciseModel.getEquipment());
            values.put(EXERCISE_TYPE, exerciseModel.getType());
            values.put(EXERCISE_KCAL, exerciseModel.getKcal());
            values.put(EXERCISE_DURATION, exerciseModel.getDuration());
            values.put(EXERCISE_DESCRIPTION, exerciseModel.getDescription());
            values.put(EXERCISE_EXTENSIONS_ID, exerciseModel.getExtension());

            return db.insert(EXERCISE_TAB, null, values) != -1;
        }
    }

    public boolean insertWorkout(ExerciseModel exerciseModel) {

        try (SQLiteDatabase db = getWritableDatabase()) {

            ContentValues values = new ContentValues();
            values.put(NAME, exerciseModel.getName());
            values.put(IMAGE, exerciseModel.getImage());
            values.put(WORKOUT_LEVEL_ID, exerciseModel.getLevel());
            values.put(WORKOUT_BODY_PARTS_ID, exerciseModel.getBodyParts());
            values.put(WORKOUT_EQUIPMENT, exerciseModel.getEquipment());
            values.put(WORKOUT_KCAL, exerciseModel.getKcal());
            values.put(WORKOUT_DURATION, exerciseModel.getDuration());
            values.put(WORKOUT_DESCRIPTION, exerciseModel.getDescription());
            values.put(WORKOUT_EXERCISES_ID, exerciseModel.getExerciseId());

            return db.insert(WORKOUT_TAB, null, values) != -1;
        }
    }

    public long getCount(String table) {
        synchronized (this) {
            try (SQLiteDatabase db = getReadableDatabase()) {
                return DatabaseUtils.queryNumEntries(db, table);
            }
        }
    }

    public boolean searchByName(String tableName, String columnName, String name) {

        String[] columns = {columnName};
        String search = columnName + " = ?";
        String[] args = {String.valueOf(name)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.query(tableName, columns, search, args, null, null,
                     null, null)) {
            return cursor.getCount() == 0;
        }
    }

    public List<ExerciseModel> showExercise() {

        List<ExerciseModel> result = new ArrayList<>();
        String search = "SELECT * FROM " + EXERCISE_TAB;

        synchronized (this) {
            try (SQLiteDatabase db = getReadableDatabase();
                 Cursor cursor = db.rawQuery(search, null)) {
                while (cursor.moveToNext()) {
                    ExerciseModel exerciseModel = new ExerciseModel(
                            cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(NAME)),
                            cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_LEVEL_ID)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_BODY_PARTS_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(EXERCISE_EQUIPMENT)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_TYPE)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_KCAL)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_DURATION)),
                            cursor.getString(cursor.getColumnIndexOrThrow(EXERCISE_DESCRIPTION)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_EXTENSIONS_ID)),
                            0);
                    result.add(exerciseModel);
                }
            }
        }
        return result;
    }

    public String showExercisesFromWorkout(long values) {

        String search = "SELECT " + WORKOUT_EXERCISES_ID +
                " FROM " + WORKOUT_TAB +
                " WHERE " + ID + " = ?";
        String[] args = {String.valueOf(values)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
            if (cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndexOrThrow(WORKOUT_EXERCISES_ID));
            }
        }
        return "";
    }

    public List<ExerciseModel> showExerciseByIdFromWorkout(long value) {

        List<ExerciseModel> result = new ArrayList<>();
        String search = "SELECT * FROM " + EXERCISE_TAB
                + " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(value)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {
            if (cursor.moveToFirst()) {
                ExerciseModel exerciseModel = new ExerciseModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_TYPE)));
                result.add(exerciseModel);
            }
        }
        return result;
    }

    public List<ExerciseModel> showExerciseById(long value) {

        List<ExerciseModel> result = new ArrayList<>();
        String search = "SELECT * FROM " + EXERCISE_TAB + " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(value)};

        synchronized (this) {
            try (SQLiteDatabase db = getReadableDatabase();
                 Cursor cursor = db.rawQuery(search, args)) {

                if (cursor.moveToFirst()) {
                    ExerciseModel exerciseModel = new ExerciseModel(
                            cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(NAME)),
                            cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_LEVEL_ID)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_BODY_PARTS_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(EXERCISE_EQUIPMENT)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_TYPE)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_KCAL)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_DURATION)),
                            cursor.getString(cursor.getColumnIndexOrThrow(EXERCISE_DESCRIPTION)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_EXTENSIONS_ID)),
                            0);
                    result.add(exerciseModel);
                }
            }
        }
        return result;
    }

    public List<IntegerModel> showExerciseExtensionId(long value) {

        List<IntegerModel> result = new ArrayList<>();
        String search = "SELECT * FROM " + EXERCISE_EXTENSIONS_TAB +
                " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(value)};

        synchronized (this) {
            try (SQLiteDatabase db = getReadableDatabase();
                 Cursor cursor = db.rawQuery(search, args)) {
                if (cursor.moveToFirst()) {
                    IntegerModel integerModel = new IntegerModel(
                            cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_EXTENSIONS_EXERCISE_ID)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_EXTENSIONS_SETS)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_EXTENSIONS_REPETITIONS)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_EXTENSIONS_TIME)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_EXTENSIONS_REST)));
                    result.add(integerModel);
                }
            }
        }
        return result;
    }

    public List<ExerciseModel> showWorkout() {

        List<ExerciseModel> result = new ArrayList<>();
        String search = "SELECT * FROM " + WORKOUT_TAB;

        synchronized (this) {
            try (SQLiteDatabase db = getReadableDatabase();
                 Cursor cursor = db.rawQuery(search, null)) {
                while (cursor.moveToNext()) {
                    ExerciseModel exerciseModel = new ExerciseModel(
                            cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(NAME)),
                            cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(WORKOUT_LEVEL_ID)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(WORKOUT_BODY_PARTS_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(WORKOUT_EQUIPMENT)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(WORKOUT_KCAL)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(WORKOUT_DURATION)),
                            cursor.getString(cursor.getColumnIndexOrThrow(WORKOUT_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndexOrThrow(WORKOUT_EXERCISES_ID)),
                            0);
                    result.add(exerciseModel);
                }
            }
        }
        return result;
    }

    public List<ExerciseModel> showWorkoutById(long value) {

        List<ExerciseModel> result = new ArrayList<>();
        String search = "SELECT * FROM " + WORKOUT_TAB + " WHERE " + ID + " == ?";
        String[] args = {String.valueOf(value)};

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(search, args)) {

            if (cursor.moveToFirst()) {
                ExerciseModel exerciseModel = new ExerciseModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(WORKOUT_LEVEL_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(WORKOUT_BODY_PARTS_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(WORKOUT_EQUIPMENT)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(WORKOUT_KCAL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(WORKOUT_DURATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(WORKOUT_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(WORKOUT_EXERCISES_ID)),
                        0);
                result.add(exerciseModel);
            }
        }
        return result;
    }
}
