package com.example.exerciseapp.mDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.exerciseapp.mModels.AppearanceBlockModel;
import com.example.exerciseapp.mModels.ExerciseModel;
import com.example.exerciseapp.mModels.IntegerModel;
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


    private static final String EXERCISE_EXTENSIONS_TAB = "EXERCISE_EXTENSIONS_TAB";
    private static final String EXERCISE_EXTENSIONS_EXERCISE_ID = "EXERCISE_EXTENSIONS_EXERCISE_ID";
    private static final String EXERCISE_EXTENSIONS_SETS = "SETS";
    private static final String EXERCISE_EXTENSIONS_REPETITIONS = "REPETITIONS";
    private static final String EXERCISE_EXTENSIONS_TIME = "TIME";
    private static final String EXERCISE_EXTENSIONS_REST = "REST";


    private static final String USER_EXERCISE_TAB = "USER_EXERCISE_TAB";
    private static final String USER_EXERCISE_EXTENSIONS_ID = "EXERCISE_EXTENSION";


    private static final String MIDDLE_TAB = "MIDDLE_TAB";
    private static final String APPEARANCE_ID = "APPEARANCE";

    public ContentBD(@Nullable Context context) {
        super(context, "ContentDatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

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
                + WORKOUT_KCAL +" INTEGER, "
                + WORKOUT_DURATION +" INTEGER, "
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
                + EXERCISE_KCAL +" INTEGER, "
                + EXERCISE_DURATION +" INTEGER, "
                + EXERCISE_DESCRIPTION + " TEXT)";
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

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

    public boolean insertExerciseExtension(IntegerModel integerModel) {

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

                ExerciseModel model = new ExerciseModel(id, name, image, level,
                        bodyPart, equipment, type, kcal, duration, description);
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

                ExerciseModel model = new ExerciseModel(id, name, image, level,
                        bodyPart, equipment, type, kcal, duration, description);
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
