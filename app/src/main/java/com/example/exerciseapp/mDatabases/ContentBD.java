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
import com.example.exerciseapp.mModels.AppearanceBlockModel;
import com.example.exerciseapp.mModels.CustomUserExerciseModel;
import com.example.exerciseapp.mModels.ExerciseModel;
import com.example.exerciseapp.mModels.IntegerModel;
import com.example.exerciseapp.mModels.TaskDateModel;
import com.example.exerciseapp.mModels.ThreeElementLinearListModel;

import java.util.ArrayList;
import java.util.List;

public class ContentBD extends SQLiteOpenHelper {

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
    private static final String CUSTOM_USER_EXERCISE_EXERCISE_ID = "CUSTOM_USER_EXERCISE_EXERCISE_ID";
    private static final String CUSTOM_USER_EXERCISE_EXERCISE_EXTENSION_ID = "CUSTOM_USER_EXERCISE_EXERCISE_EXTENSION_ID";

    private static final String USER_EXERCISE_TAB = "USER_EXERCISE_TAB";
    private static final String USER_EXERCISE_EXTENSIONS_ID = "EXERCISE_EXTENSION";


    private static final String MIDDLE_TAB = "MIDDLE_TAB";
    private static final String APPEARANCE_ID = "APPEARANCE";


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
        super(context, "ContentDatabase.db", null, 1);
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
                + EXERCISE_BODY_PARTS_ID + " TEXT, "
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

        String createUserExercise = "CREATE TABLE " + USER_EXERCISE_TAB + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_ID + " INTEGER, "
                + USER_EXERCISE_EXTENSIONS_ID + " INTEGER)";
        sqLiteDatabase.execSQL(createUserExercise);

        String createAppearanceTab = "CREATE TABLE " + APPEARANCE_TAB + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT, " + ICON + " INTEGER ,"
                + INT_VAL + " INTEGER, " + OBJ_DESCRIPTION + " TEXT, "
                + TAG + " TEXT)";
        sqLiteDatabase.execSQL(createAppearanceTab);

        String createMiddleTab = "CREATE TABLE " + MIDDLE_TAB + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + INT_VAL + " INTEGER, " + APPEARANCE_ID + " INTEGER)";
        sqLiteDatabase.execSQL(createMiddleTab);

        String createDateTab = "CREATE TABLE " + DATE_TAB + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE_USER_ID + " INTEGER, " + DATE_NUMBER + " TEXT, "
                + DATE_TASK_ID + " INTEGER, " + DATE_STATUS + " INTEGER)";
        sqLiteDatabase.execSQL(createDateTab);

        String createCustomUserExerciseTab = "CREATE TABLE " + CUSTOM_USER_EXERCISE_TAB + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CUSTOM_USER_EXERCISE_NAME + " TEXT, "
                + CUSTOM_USER_EXERCISE_EXERCISE_ID + " INTEGER, "
                + CUSTOM_USER_EXERCISE_EXERCISE_EXTENSION_ID + " INTEGER)";
        sqLiteDatabase.execSQL(createCustomUserExerciseTab);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public InsertResult insertUserBio(IntegerModel integerModel) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_BIO_DATE, integerModel.getId());
        values.put(USER_BIO_HEIGHT, integerModel.getFirstValue());
        values.put(USER_BIO_WEIGHT, integerModel.getSecondValue());
        values.put(USER_BIO_ID, integerModel.getThirdValue());

        long insert = db.insert(USER_BIO_TAB, null, values);
        boolean success = insert != -1;
        InsertResult result = new InsertResult(insert, success);

        if (result.isSuccess()) {
            return result;
        } else {
            return null;
        }
    }

    public InsertResult updateUserBioWeight(int date, int weight) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_BIO_WEIGHT, weight);
        String valueID = USER_BIO_DATE + " = ?";
        String[] args = {String.valueOf(date)};
        int update = db.update(USER_BIO_TAB, values, valueID, args);

        boolean success = update != -1;
        Log.i(TAG, "updateUserBioWeight: " + success);
        InsertResult result = new InsertResult(update, success);

        if (result.isSuccess()) {
            Log.i(TAG, "searchUserBio: sendToDB ");
            return result;
        } else {
            return null;
        }
    }

    public InsertResult searchUserBio(int date) {

        SQLiteDatabase db = getWritableDatabase();
        String valueID = USER_BIO_DATE + " = ?";
        String[] args = {String.valueOf(date)};
        Cursor cursor = db.query(USER_BIO_TAB, null, valueID, args,
                null, null, null);

        InsertResult result = new InsertResult(cursor.moveToFirst());

        cursor.close();
        db.close();

        if (result.isSuccess()) {
            return result;
        } else {
            return null;
        }
    }

    public int searchUserBioHighestWeight(int date) {

        SQLiteDatabase db = getWritableDatabase();
        String search = "SELECT " + USER_BIO_WEIGHT + " FROM " + USER_BIO_TAB +
                " WHERE " + USER_BIO_DATE + " < ?";

//        Clean SQL
        /* SELECT USER_BIO_WEIGHT FROM USER_BIO_TAB WHERE USER_BIO_DATE < ?(VALUE)
         AND USER_BIO_WEIGHT > 0 ORDER BY USER_BIO_DATE DESC LIMIT 1
         */

        String[] args = {String.valueOf(date)};
        Cursor cursor = db.rawQuery(search, args);

        int lastValue = -1;
        if (cursor.moveToLast()) {
            do {
                if (cursor.getInt(0) > 0){
                    lastValue = cursor.getInt(0);
                    break;
                }
            } while (cursor.moveToPrevious());
        }

        cursor.close();
        db.close();
        return lastValue;
    }

    public InsertResult updateUserBioHeight(int date, int height) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_BIO_HEIGHT, height);
        String valueID = "id = ?";
        String[] args = {String.valueOf(date)};
        int update = db.update(USER_BIO_TAB, values, valueID, args);

        boolean success = update != -1;
        InsertResult result = new InsertResult(update, success);

        if (result.isSuccess()) {
            return result;
        } else {
            return null;
        }
    }

    public List<IntegerModel> getUserBio() {
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + USER_BIO_TAB;
        Cursor cursor = db.rawQuery(search, null);
        List<IntegerModel> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int date = cursor.getInt(0);
                int height = cursor.getInt(1);
                int weight = cursor.getInt(2);
                int user_id = cursor.getInt(3);

                IntegerModel model = new IntegerModel(0, date, height, weight, user_id);
                list.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return list;
    }

    public List<IntegerModel> getUserBioWeight(int userID) {

        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT " + USER_BIO_DATE + "," + USER_BIO_WEIGHT +
                " FROM " + USER_BIO_TAB + " WHERE " + USER_BIO_ID + " = ? AND "+ USER_BIO_WEIGHT +
                " > 0 ORDER BY " + USER_BIO_DATE + " ASC LIMIT 7" ;

        String[] selectedArgs = {String.valueOf(userID)};
        Cursor cursor = db.rawQuery(search, selectedArgs);
        List<IntegerModel> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int date = cursor.getInt(0);
                int weight = cursor.getInt(1);

                IntegerModel model = new IntegerModel(0, date, weight);
                list.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return list;
    }

    public InsertResult insertCustomUserExercise(CustomUserExerciseModel customUserExerciseModel) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CUSTOM_USER_EXERCISE_NAME, customUserExerciseModel.getName());
        values.put(CUSTOM_USER_EXERCISE_EXERCISE_ID, customUserExerciseModel.getExerciseID());
        values.put(CUSTOM_USER_EXERCISE_EXERCISE_EXTENSION_ID, customUserExerciseModel.getExerciseExtensionID());

        long insert = db.insert(CUSTOM_USER_EXERCISE_TAB, null, values);
        boolean success = insert != -1;
        InsertResult result = new InsertResult(insert, success);
        if (result.isSuccess()) {
            return result;
        } else {
            return null;
        }
    }

    public boolean insertTaskWithDate(TaskDateModel taskDateModel) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DATE_USER_ID, taskDateModel.getUserId());
        values.put(DATE_NUMBER, taskDateModel.getDate());
        values.put(DATE_TASK_ID, taskDateModel.getTaskId());
        values.put(DATE_STATUS, taskDateModel.getStatus());

        long insert = db.insert(DATE_TAB, null, values);

        return insert != -1;
    }

    public List<TaskDateModel> showTaskWithDate(String date) {

        List<TaskDateModel> model = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String search = "SELECT * FROM " + DATE_TAB;
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int userId = cursor.getInt(1);
                String day = cursor.getString(2);
                int task = cursor.getInt(3);
                int status = cursor.getInt(4);

                if (date.equals(day)) {
                    TaskDateModel result = new TaskDateModel(id, userId, day, task, status);
                    model.add(result);
                    break;
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return model;
    }

    public boolean insertBodyParts(IntegerModel integerModel) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BODY_PARTS_CHEST, integerModel.getFirstValue());
        values.put(BODY_PARTS_BACK, integerModel.getSecondValue());
        values.put(BODY_PARTS_SHOULDERS, integerModel.getThirdValue());
        values.put(BODY_PARTS_ARMS, integerModel.getForthValue());
        values.put(BODY_PARTS_ABS, integerModel.getFifthValue());
        values.put(BODY_PARTS_LEGS, integerModel.getSixthValue());
        values.put(BODY_PARTS_FULL_BODY, integerModel.getSeventhValue());

        long insert = db.insert(BODY_PARTS_TAB, null, values);

        return insert != -1;
    }

    public boolean insertExerciseExtend(IntegerModel integerModel) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EXERCISE_EXTENSIONS_EXERCISE_ID, integerModel.getFirstValue());
        values.put(EXERCISE_EXTENSIONS_SETS, integerModel.getSecondValue());
        values.put(EXERCISE_EXTENSIONS_REPETITIONS, integerModel.getThirdValue());
        values.put(EXERCISE_EXTENSIONS_TIME, integerModel.getForthValue());
        values.put(EXERCISE_EXTENSIONS_REST, integerModel.getFifthValue());

        long insert = db.insert(EXERCISE_EXTENSIONS_TAB, null, values);

        return insert != -1;
    }

    public InsertResult insertExerciseExtension(IntegerModel integerModel) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EXERCISE_EXTENSIONS_EXERCISE_ID, integerModel.getFirstValue());
        values.put(EXERCISE_EXTENSIONS_SETS, integerModel.getSecondValue());
        values.put(EXERCISE_EXTENSIONS_REPETITIONS, integerModel.getThirdValue());
        values.put(EXERCISE_EXTENSIONS_TIME, integerModel.getForthValue());
        values.put(EXERCISE_EXTENSIONS_REST, integerModel.getFifthValue());

        long insert = db.insert(EXERCISE_EXTENSIONS_TAB, null, values);
        boolean success = insert != -1;
        InsertResult result = new InsertResult(insert, success);
        if (result.isSuccess()) {
            return result;
        } else {
            return null;
        }
    }

    public boolean insertAppearance(AppearanceBlockModel appearanceBlockModel) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME, appearanceBlockModel.getName());
        values.put(ICON, appearanceBlockModel.getIcon());
        values.put(INT_VAL, appearanceBlockModel.getValue());
        values.put(OBJ_DESCRIPTION, appearanceBlockModel.getDescription());
        values.put(TAG, appearanceBlockModel.getTag());

        long insert = db.insert(APPEARANCE_TAB, null, values);

        return insert != -1;
    }

    public boolean insertUserExercise(IntegerModel integerModel) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_ID, integerModel.getFirstValue());
        values.put(USER_EXERCISE_EXTENSIONS_ID, integerModel.getSecondValue());

        long insert = db.insert(USER_EXERCISE_TAB, null, values);

        return insert != -1;
    }

    public boolean insertEquipment(ThreeElementLinearListModel threeElementLinearListModel) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME, threeElementLinearListModel.getName());
        values.put(ICON, threeElementLinearListModel.getImage());

        long insert = db.insert(EQUIPMENT_TAB, null, values);

        return insert != -1;
    }

    public boolean insertExercise(ExerciseModel exerciseModel) {

        SQLiteDatabase db = getWritableDatabase();
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

        long insert = db.insert(EXERCISE_TAB, null, values);

        return insert != -1;
    }

    public boolean insertWorkout(ExerciseModel exerciseModel) {

        SQLiteDatabase db = getWritableDatabase();
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

        long insert = db.insert(WORKOUT_TAB, null, values);

        return insert != -1;
    }

    public boolean insertMiddle(IntegerModel integerModel) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(INT_VAL, integerModel.getFirstValue());
        values.put(APPEARANCE_ID, integerModel.getSecondValue());

        long insert = db.insert(MIDDLE_TAB, null, values);

        return insert != -1;
    }

    public long getCount(String table) {

        SQLiteDatabase db = getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, table);
        db.close();
        return count;
    }

    public List<ExerciseModel> showExercise() {

        List<ExerciseModel> show = new ArrayList<>();
        String search = "SELECT * FROM " + EXERCISE_TAB;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String image = cursor.getString(2);
                int level = cursor.getInt(3);
                String bodyPart = cursor.getString(4);
                String equipment = cursor.getString(5);
                int type = cursor.getInt(6);
                int kcal = cursor.getInt(7);
                int duration = cursor.getInt(8);
                String description = cursor.getString(9);
                int extension = cursor.getInt(10);

                ExerciseModel model = new ExerciseModel(id, name, image, level,
                        bodyPart, equipment, type, kcal, duration, description, extension);
                show.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return show;
    }

    public List<ExerciseModel> showExerciseById(long value) {

        List<ExerciseModel> show = new ArrayList<>();
        String search = "SELECT * FROM " + EXERCISE_TAB + " WHERE " + ID + " == " + value;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String image = cursor.getString(2);
            int level = cursor.getInt(3);
            String bodyPart = cursor.getString(4);
            String equipment = cursor.getString(5);
            int type = cursor.getInt(6);
            int kcal = cursor.getInt(7);
            int duration = cursor.getInt(8);
            String description = cursor.getString(9);
            int extension = cursor.getInt(10);

            ExerciseModel model = new ExerciseModel(id, name, image, level,
                    bodyPart, equipment, type, kcal, duration, description, extension);
            show.add(model);
        }

        cursor.close();
        db.close();

        return show;
    }

    public List<IntegerModel> showExerciseExtensionId(long value) {

        List<IntegerModel> show = new ArrayList<>();
        String search = "SELECT * FROM " + EXERCISE_EXTENSIONS_TAB + " WHERE " + ID + " == " + value;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            int exercise_id = cursor.getInt(1);
            int sets = cursor.getInt(2);
            int repetition = cursor.getInt(3);
            int time = cursor.getInt(4);
            int rest = cursor.getInt(5);

            IntegerModel model = new IntegerModel(id, exercise_id, sets, repetition, time, rest);
            show.add(model);
        }

        cursor.close();
        db.close();

        return show;
    }

    public List<ExerciseModel> showWorkout() {

        List<ExerciseModel> show = new ArrayList<>();
        String search = "SELECT * FROM " + WORKOUT_TAB;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String image = cursor.getString(2);
                int level = cursor.getInt(3);
                String bodyPart = cursor.getString(4);
                String equipment = cursor.getString(5);
                int kcal = cursor.getInt(6);
                int duration = cursor.getInt(7);
                String description = cursor.getString(8);
                String exerciseId = cursor.getString(9);

                ExerciseModel model = new ExerciseModel(id, name, image, level,
                        bodyPart, equipment, kcal, duration, description, exerciseId);
                show.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return show;
    }

    public List<ExerciseModel> showWorkoutById(long value) {

        List<ExerciseModel> show = new ArrayList<>();
        String search = "SELECT * FROM " + WORKOUT_TAB + " WHERE " + ID + " == " + value;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(search, null);

        if (cursor.moveToFirst()) {

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String image = cursor.getString(2);
            int level = cursor.getInt(3);
            String bodyPart = cursor.getString(4);
            String equipment = cursor.getString(5);
            int kcal = cursor.getInt(6);
            int duration = cursor.getInt(7);
            String description = cursor.getString(8);
            String exerciseId = cursor.getString(9);

            ExerciseModel model = new ExerciseModel(id, name, image, level,
                    bodyPart, equipment, kcal, duration, description, exerciseId);
            show.add(model);
        }

        cursor.close();
        db.close();

        return show;
    }

}
