package com.example.exerciseapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exerciseapp.mClasses.GlobalClass;
import com.example.exerciseapp.mClasses.StorageClass;
import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mDatabases.DBHelper;
import com.example.exerciseapp.mDatabases.SimulatedExternalDatabase;
import com.example.exerciseapp.mModels.AppearanceBlockModel;
import com.example.exerciseapp.mModels.ExerciseDescriptionModel;
import com.example.exerciseapp.mModels.IntegerModel;
import com.example.exerciseapp.mModels.LanguageModel;
import com.example.exerciseapp.mModels.StringModel;
import com.example.exerciseapp.mModels.TaskDateModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private long currentUserID = 1;

    private DBHelper dbHelper;
    private ContentBD contentBD;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(GlobalClass.initLanguage(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new SimulatedExternalDatabase();

        Intent intent = getIntent();
        currentUserID = intent.getLongExtra(GlobalClass.userID, -1);
        initInternalFolders();

        if (currentUserID != -1) {
            initView();
            initMenu();
        } else {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
//            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
        }
        dbHelper = new DBHelper(MainActivity.this);
        contentBD = new ContentBD(MainActivity.this);

        if (dbHelper.getCount("LANGUAGE") <= 0) {
            insertLanguage();
        }

        if (dbHelper.getCount("HEIGHT_TAB") <= 0) {
            insertUnitsHeight();
        }

        if (dbHelper.getCount("WEIGHT_TAB") <= 0) {
            insertUnitsWeight();
        }

        if (contentBD.getCount("EQUIPMENT_TAB") <= 0) {
            insertEquipment();
        }

        if (contentBD.getCount("APPEARANCE_TAB") <= 0) {
            insertContentAppearance();
        }

        if (contentBD.getCount("DATE_TAB") <= 0) {
            insertTaskDate();
        }

        if (contentBD.getCount("EXERCISE_TAB") <= 0) {
            insertExercise();
        }

        if (contentBD.getCount("WORKOUT_TAB") <= 0) {
            insertWorkout();
        }

        if (contentBD.getCount("EXERCISE_EXTENSIONS_TAB") <= 0) {
            insertExtensionExercise();
        }

        if (contentBD.getCount("BODY_PART_TAB") <= 0) {
            insertBodyParts();
        }

        if (contentBD.getCount("EQUIPMENT_TAB") <= 0) {
            insertEquipment();
        }
    }

    private void initView() {
    }

    private void initMenu() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.act_main_bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_bar_main);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case (R.id.bottom_nav_bar_main):
                    recreate();
                    return true;
                case (R.id.bottom_nav_bar_workout):
                    startNewActivity(LibraryActivity.class);
                    return true;
                case (R.id.bottom_nav_bar_profile):
                    startNewActivity(UserActivity.class);
                    return true;
                case (R.id.bottom_nav_bar_settings):
                    startNewActivity(SettingsActivity.class);
                    return true;
            }
            return false;
        });
    }

    private void startNewActivity(Class<?> activity) {
        Intent intent = new Intent(getApplicationContext(), activity);
        intent.putExtra(GlobalClass.userID, currentUserID);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
        startActivity(intent);
        finish();
    }

    private void initInternalFolders() {
        new StorageClass(getApplicationContext(), "/Application_images").addFolderToInternalStorage();
        new StorageClass(getApplicationContext(), "/Muscle_Group_images").addFolderToInternalStorage();
        new StorageClass(getApplicationContext(), "/Exercise_images").addFolderToInternalStorage();
        new StorageClass(getApplicationContext(), "/Application_images/flags").addFolderToInternalStorage();
    }


    private void insertExtensionExercise() {

//        id, sets, repetition(type = rep), time(type = time), rest
        IntegerModel extend1 = new IntegerModel(-1, 1, 8, 12, 0, 30);
        IntegerModel extend2 = new IntegerModel(-1, 2, 3, 0, 65, 45);
        IntegerModel extend3 = new IntegerModel(-1, 3, 4, 6, 0, 45);

        contentBD.insertExerciseExtend(extend1);
        contentBD.insertExerciseExtend(extend2);
        contentBD.insertExerciseExtend(extend3);
    }

    private void insertWorkout() {

        ExerciseDescriptionModel workout1 = new ExerciseDescriptionModel(-1, "Workout1 YTR", "", 2,
                1, "0", 10, 55, "descriptionWorkout1",
                "2,3,1", 0);
        ExerciseDescriptionModel workout2 = new ExerciseDescriptionModel(-1, "Workout2 URC", "", 1,
                2, "0", 21, 35, "descriptionWorkout2",
                "1,3", 0);
        ExerciseDescriptionModel workout3 = new ExerciseDescriptionModel(-1, "Workout3 YTN", "", 3,
                3, "0", 17, 15, "descriptionWorkout3",
                "3,2,1", 0);

        contentBD.insertWorkout(workout1);
        contentBD.insertWorkout(workout2);
        contentBD.insertWorkout(workout3);
    }

    private void insertExercise() {

        ExerciseDescriptionModel exercise1 = new ExerciseDescriptionModel(-1, "Exercise1 ABC", "", 2,
                2, "3,2", 1, 5, 20, "description1", 1, 1);
        ExerciseDescriptionModel exercise2 = new ExerciseDescriptionModel(-1, "Exercise2 BGH", "", 1,
                2, "1", 2, 10, 25, "description2", 2, 1);
        ExerciseDescriptionModel exercise3 = new ExerciseDescriptionModel(-1, "Exercise3 BCI", "", 3,
                3, "2,1", 1, 15, 35, "description3", 3, 1);

        contentBD.insertExercise(exercise1);
        contentBD.insertExercise(exercise2);
        contentBD.insertExercise(exercise3);
    }

    private void insertBodyParts() {

        IntegerModel model1 = new IntegerModel(-1, 0, 0, 0, 1, 1, 0, 0);
        IntegerModel model2 = new IntegerModel(-1, 1, 0, 0, 1, 0, 0, 0);
        IntegerModel model3 = new IntegerModel(-1, 0, 0, 0, 0, 0, 0, 1);


        contentBD.insertBodyParts(model1);
        contentBD.insertBodyParts(model2);
        contentBD.insertBodyParts(model3);
    }

    private void insertTaskDate() {
        TaskDateModel one = new TaskDateModel(-1, 1, "2023625", 1, 0);
        TaskDateModel two = new TaskDateModel(-1, 1, "2023628", 3, 0);
        TaskDateModel three = new TaskDateModel(-1, 1, "2023623", 2, 1);

        contentBD.insertTaskWithDate(one);
        contentBD.insertTaskWithDate(two);
        contentBD.insertTaskWithDate(three);
    }

    private void insertContentAppearance() {
        AppearanceBlockModel chest = new AppearanceBlockModel(-1,
                R.drawable.ic_person, "Chest", "Chest description", "BodyPart");
        AppearanceBlockModel back = new AppearanceBlockModel(-1,
                R.drawable.ic_block, "Back", "Back description", "BodyPart");
        AppearanceBlockModel shoulders = new AppearanceBlockModel(-1,
                R.drawable.ic_person, "Shoulders", "Shoulders description", "BodyPart");
        AppearanceBlockModel arms = new AppearanceBlockModel(-1,
                R.drawable.ic_block, "Arms", "Arms description", "BodyPart");
        AppearanceBlockModel abs = new AppearanceBlockModel(-1,
                R.drawable.ic_person, "ABS", "ABS description", "BodyPart");
        AppearanceBlockModel legs = new AppearanceBlockModel(-1,
                R.drawable.ic_block, "Legs", "Legs description", "BodyPart");
        AppearanceBlockModel full_body = new AppearanceBlockModel(-1,
                R.drawable.ic_person, "Full body", "FullBody description", "BodyPart");
        contentBD.insertAppearance(chest);
        contentBD.insertAppearance(back);
        contentBD.insertAppearance(shoulders);
        contentBD.insertAppearance(arms);
        contentBD.insertAppearance(abs);
        contentBD.insertAppearance(legs);
        contentBD.insertAppearance(full_body);
    }

    private void insertEquipment() {
        StringModel item1 = new StringModel(-1, "Dumbbells", R.drawable.ic_hexagon);
        StringModel item2 = new StringModel(-1, "ResistanceRubber", R.drawable.ic_hexagon);
        StringModel item3 = new StringModel(-1, "Paralletes", R.drawable.ic_hexagon);
        contentBD.insertEquipment(item1);
        contentBD.insertEquipment(item2);
        contentBD.insertEquipment(item3);
    }

    private void insertLanguage() {

        LanguageModel lan_pl = new LanguageModel(-1, "Polish", false, "pl", "");
        LanguageModel lan_en = new LanguageModel(-1, "English", true, "en", "");
        dbHelper.insertLanguage(lan_pl);
        dbHelper.insertLanguage(lan_en);

    }

    private void insertUnitsHeight() {

        StringModel model2 = new StringModel(-1, "cm");
        StringModel model3 = new StringModel(-1, "in");
        dbHelper.insertUnitHeight(model2);
        dbHelper.insertUnitHeight(model3);
    }

    private void insertUnitsWeight() {

        StringModel model = new StringModel(-1, "kg");
        StringModel model1 = new StringModel(-1, "lbs");
        dbHelper.insertUnitWeight(model);
        dbHelper.insertUnitWeight(model1);
    }

}

//        if(!checkPermission()) {
//            requestPermission();
//        }

//    private boolean checkPermission() {
//        if (SDK_INT >= Build.VERSION_CODES.R) {
//            return Environment.isExternalStorageManager();
//        } else {
//            int result = ContextCompat.checkSelfPermission(SettingsActivity.this, READ_EXTERNAL_STORAGE);
//            int result1 = ContextCompat.checkSelfPermission(SettingsActivity.this, WRITE_EXTERNAL_STORAGE);
//            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
//        }
//    }
//
//    private void requestPermission() {
//        if (SDK_INT >= Build.VERSION_CODES.R) {
//            try {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
//                intent.addCategory("android.intent.category.DEFAULT");
//                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
//                startActivityForResult(intent, 2296);
//            } catch (Exception e) {
//                Intent intent = new Intent();
//                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                startActivityForResult(intent, 2296);
//            }
//        } else {
//            //below android 11
//            ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
//        }
//    }
