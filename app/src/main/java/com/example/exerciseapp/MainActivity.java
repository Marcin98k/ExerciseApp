package com.example.exerciseapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exerciseapp.mClasses.GlobalClass;
import com.example.exerciseapp.mClasses.StorageClass;
import com.example.exerciseapp.mDatabases.ContentDB;
import com.example.exerciseapp.mDatabases.DBHelper;
import com.example.exerciseapp.mModels.AppearanceBlockModel;
import com.example.exerciseapp.mModels.ExerciseDescriptionModel;
import com.example.exerciseapp.mModels.IntegerModel;
import com.example.exerciseapp.mModels.LanguageModelToChange;
import com.example.exerciseapp.mModels.StringModel;
import com.example.exerciseapp.mModels.TaskDateModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Button welcomeBtn;
    private long currentUserID = 1;

    private DBHelper dbHelper;
    private ContentDB contentDB;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(GlobalClass.initLanguage(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
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
        contentDB = new ContentDB(MainActivity.this);

        if (dbHelper.getCount("LANGUAGE") <= 0) {
            insertLanguage();
        }

        if (dbHelper.getCount("HEIGHT_TAB") <= 0) {
            insertUnitsHeight();
        }

        if (dbHelper.getCount("WEIGHT_TAB") <= 0) {
            insertUnitsWeight();
        }

        if (contentDB.getCount("EQUIPMENT_TAB") <= 0) {
            insertEquipment();
        }

        if (contentDB.getCount("APPEARANCE_TAB") <= 0) {
            insertContentAppearance();
        }

        if (contentDB.getCount("DATE_TAB") <= 0) {
            insertTaskDate();
        }

        if (contentDB.getCount("EXERCISE_TAB") <= 0) {
            insertExercise();
        }

        if (contentDB.getCount("WORKOUT_TAB") <= 0) {
            insertWorkout();
        }

        if (contentDB.getCount("EXERCISE_EXTENSIONS_TAB") <= 0) {
            insertExtensionExercise();
        }

        if (contentDB.getCount("BODY_PART_TAB") <= 0) {
            insertBodyParts();
        }

        if (contentDB.getCount("EQUIPMENT_TAB") <= 0) {
            insertEquipment();
        }
    }

    private void initView() {
        welcomeBtn = findViewById(R.id.aMain_welcome);

        welcomeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        });
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

//        id, sets, repetition(equipmentType = rep), time(equipmentType = time), rest
        IntegerModel extend1 = new IntegerModel(-1, 1, 8, 12, 0, 30);
        IntegerModel extend2 = new IntegerModel(-1, 2, 3, 0, 65, 45);
        IntegerModel extend3 = new IntegerModel(-1, 3, 4, 6, 0, 45);

        contentDB.insertExerciseExtend(extend1);
        contentDB.insertExerciseExtend(extend2);
        contentDB.insertExerciseExtend(extend3);
    }

    private void insertWorkout() {

        ExerciseDescriptionModel workout11 = new ExerciseDescriptionModel(-1, "Workout1 YTR", "", 2,
                1, "0", 10, 55, "descriptionWorkout1", "1", 0);
        ExerciseDescriptionModel workout22 = new ExerciseDescriptionModel(-1, "Workout2 URC", "", 1,
                2, "0", 21, 35, "descriptionWorkout2","1",
                0);
        ExerciseDescriptionModel workout33 = new ExerciseDescriptionModel(-1, "Workout3 YTN", "", 3,
                3, "0", 17, 15, "descriptionWorkout3","1", 0);

        contentDB.insertWorkout(workout11);
        contentDB.insertWorkout(workout22);
        contentDB.insertWorkout(workout33);
    }

    private void insertExercise() {

        ExerciseDescriptionModel exercise1 = new ExerciseDescriptionModel(-1, "Exercise1 ABC", "", 2,
                2, "3,2", 1, 5, 20, "description1", 1, 1);
        ExerciseDescriptionModel exercise2 = new ExerciseDescriptionModel(-1, "Exercise2 BGH", "", 1,
                2, "1", 2, 10, 25, "description2", 2, 1);
        ExerciseDescriptionModel exercise3 = new ExerciseDescriptionModel(-1, "Exercise3 BCI", "", 3,
                3, "2,1", 1, 15, 35, "description3", 3, 1);

        contentDB.insertExercise(exercise1);
        contentDB.insertExercise(exercise2);
        contentDB.insertExercise(exercise3);
    }

    private void insertBodyParts() {

        IntegerModel model1 = new IntegerModel(-1, 0, 0, 0, 1, 1, 0, 0);
        IntegerModel model2 = new IntegerModel(-1, 1, 0, 0, 1, 0, 0, 0);
        IntegerModel model3 = new IntegerModel(-1, 0, 0, 0, 0, 0, 0, 1);


        contentDB.insertBodyParts(model1);
        contentDB.insertBodyParts(model2);
        contentDB.insertBodyParts(model3);
    }

    private void insertTaskDate() {
        TaskDateModel one = new TaskDateModel(-1, 1, "2023625", 1, 0);
        TaskDateModel two = new TaskDateModel(-1, 1, "2023628", 3, 0);
        TaskDateModel three = new TaskDateModel(-1, 1, "2023623", 2, 1);

        contentDB.insertTaskWithDate(one);
        contentDB.insertTaskWithDate(two);
        contentDB.insertTaskWithDate(three);
    }

    private void insertContentAppearance() {
        AppearanceBlockModel chest = new AppearanceBlockModel(-1,
                R.drawable.ic_person, "Chest", "Chest description", "BodyParts");
        AppearanceBlockModel back = new AppearanceBlockModel(-1,
                R.drawable.ic_block, "Back", "Back description", "BodyParts");
        AppearanceBlockModel shoulders = new AppearanceBlockModel(-1,
                R.drawable.ic_person, "Shoulders", "Shoulders description", "BodyParts");
        AppearanceBlockModel arms = new AppearanceBlockModel(-1,
                R.drawable.ic_block, "Arms", "Arms description", "BodyParts");
        AppearanceBlockModel abs = new AppearanceBlockModel(-1,
                R.drawable.ic_person, "ABS", "ABS description", "BodyParts");
        AppearanceBlockModel legs = new AppearanceBlockModel(-1,
                R.drawable.ic_block, "Legs", "Legs description", "BodyParts");
        AppearanceBlockModel full_body = new AppearanceBlockModel(-1,
                R.drawable.ic_person, "Full body", "FullBody description", "BodyParts");
        contentDB.insertAppearance(chest);
        contentDB.insertAppearance(back);
        contentDB.insertAppearance(shoulders);
        contentDB.insertAppearance(arms);
        contentDB.insertAppearance(abs);
        contentDB.insertAppearance(legs);
        contentDB.insertAppearance(full_body);
    }

    private void insertEquipment() {
        StringModel item1 = new StringModel(-1, "Dumbbells", R.drawable.ic_hexagon);
        StringModel item2 = new StringModel(-1, "ResistanceRubber", R.drawable.ic_hexagon);
        StringModel item3 = new StringModel(-1, "Paralletes", R.drawable.ic_hexagon);
        contentDB.insertEquipment(item1);
        contentDB.insertEquipment(item2);
        contentDB.insertEquipment(item3);
    }

    private void insertLanguage() {

        LanguageModelToChange lan_pl = new LanguageModelToChange(-1, "Polish", false, "pl", "");
        LanguageModelToChange lan_en = new LanguageModelToChange(-1, "English", true, "en", "");
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