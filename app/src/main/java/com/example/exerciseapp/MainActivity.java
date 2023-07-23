package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exerciseapp.mClasses.StorageClass;
import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mDatabases.DBHelper;
import com.example.exerciseapp.mModels.AppearanceBlockModel;
import com.example.exerciseapp.mModels.ExerciseModel;
import com.example.exerciseapp.mModels.IntegerModel;
import com.example.exerciseapp.mModels.TaskDateModel;
import com.example.exerciseapp.mModels.ThreeElementLinearListModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ContentBD contentBD;

    private Button welcomeBtn;
    private Button settingsBtn;
    private Button libraryBtn;
    private Button userBtn;
    private Button exerciseBtn;
    private BottomNavigationView bottomNavigationView;


    private boolean isLogged = true;
    private long userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isLogged) {
            initView();
            initMenu();

            welcomeBtn.setOnClickListener(v -> {
                Intent intent = new Intent(this, WelcomeActivity.class);
                startActivity(intent);
            });
            settingsBtn.setOnClickListener(v -> {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
            });
            libraryBtn.setOnClickListener(v -> {
                Intent intent = new Intent(this, LibraryActivity.class);
                startActivity(intent);
            });
            userBtn.setOnClickListener(v -> {
                Intent intent = new Intent(this, UserActivity.class);
                startActivity(intent);
            });
            exerciseBtn.setOnClickListener(v -> {
                Intent intent = new Intent(this, ExerciseActivity.class);
                startActivity(intent);
            });
        } else {
            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
        }

        dbHelper = new DBHelper(MainActivity.this);
        contentBD = new ContentBD(MainActivity.this);

        if (dbHelper.getCount("LANGUAGE") <= 0) {
            insertUnits();
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

        if (dbHelper.getCount("APPEARANCE") <= 0) {
            insertUnitsAppearance();
        } else {
            Log.e(TAG, "onCreate: Appearance is filled");
        }
        if (dbHelper.getCount("FUTURE") <= 0) {
            dbHelper.insertFutureTab("Test@test");
        }
        initInternalFolders();
    }


    private void initView() {
        welcomeBtn = findViewById(R.id.aMain_welcome);
        settingsBtn = findViewById(R.id.aMain_settings);
        libraryBtn = findViewById(R.id.aMain_library);
        userBtn = findViewById(R.id.aMain_user);
        exerciseBtn = findViewById(R.id.aMain_exercise);
        bottomNavigationView = findViewById(R.id.act_main_bottom_nav_bar);
    }

    private void initMenu() {
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_bar_main);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            boolean executeFinally = true;
            try {
                switch (item.getItemId()) {
                    case (R.id.bottom_nav_bar_main):
                        return true;
                    case (R.id.bottom_nav_bar_workout):
                        startActivity(new Intent(getApplicationContext(), LibraryActivity.class));
                        return true;
                    case (R.id.bottom_nav_bar_profile):
                        startActivity(new Intent(getApplicationContext(), UserActivity.class));
                        return true;
                    case (R.id.bottom_nav_bar_settings):
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        executeFinally = false;
                        return true;
                }
            } finally {
                if (executeFinally) {
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
                    finish();
                }
            }
            return false;
        });
    }

    private void insertExtensionExercise() {

        IntegerModel extend1 = new IntegerModel(-1, 1, 8, 12, 0, 30);
        IntegerModel extend2 = new IntegerModel(-1, 2, 3, 0, 65, 45);
        IntegerModel extend3 = new IntegerModel(-1, 3, 4, 6, 0, 45);

        contentBD.insertExerciseExtend(extend1);
        contentBD.insertExerciseExtend(extend2);
        contentBD.insertExerciseExtend(extend3);
    }

    private void insertWorkout() {

        ExerciseModel workout1 = new ExerciseModel(-1, "Workout1 YTR", "", 2,
                "5", "21", 10, 55, "descriptionWorkout1",
                "2,3,1");
        ExerciseModel workout2 = new ExerciseModel(-1, "Workout2 URC", "", 1,
                "2", "54", 21, 35, "descriptionWorkout2",
                "1,2,3");
        ExerciseModel workout3 = new ExerciseModel(-1, "Workout3 YTN", "", 3,
                "4", "71", 17, 15, "descriptionWorkout3",
                "3,2,1");

        contentBD.insertWorkout(workout1);
        contentBD.insertWorkout(workout2);
        contentBD.insertWorkout(workout3);
    }

    private void insertExercise() {

        ExerciseModel exercise1 = new ExerciseModel(-1, "Exercise1 ABC", "", 2,
                "5", "21", 1, 5, 20, "description1", 1);
        ExerciseModel exercise2 = new ExerciseModel(-1, "Exercise2 BGH", "", 1,
                "2", "54", 2, 10, 25, "description2", 2);
        ExerciseModel exercise3 = new ExerciseModel(-1, "Exercise3 BCI", "", 3,
                "4", "71", 1, 15, 35, "description3", 3);

        contentBD.insertExercise(exercise1);
        contentBD.insertExercise(exercise2);
        contentBD.insertExercise(exercise3);
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
        ThreeElementLinearListModel dumbbells = new ThreeElementLinearListModel(-1,
                R.drawable.ic_hexagon, "Dumbbells", 0);
        ThreeElementLinearListModel resistanceRubber = new ThreeElementLinearListModel(-1,
                R.drawable.ic_block, "ResistanceRubber", 0);
        ThreeElementLinearListModel paralletes = new ThreeElementLinearListModel(-1,
                R.drawable.ic_hexagon, "Paralletes", 0);
        contentBD.insertEquipment(dumbbells);
        contentBD.insertEquipment(resistanceRubber);
        contentBD.insertEquipment(paralletes);
    }

    private void initInternalFolders() {
        new StorageClass(getApplicationContext(), "/Application_images").addFolderToInternalStorage();
        new StorageClass(getApplicationContext(), "/Muscle_Group_images").addFolderToInternalStorage();
        new StorageClass(getApplicationContext(), "/Exercise_images").addFolderToInternalStorage();
        new StorageClass(getApplicationContext(), "/Application_images/flags").addFolderToInternalStorage();
    }

    private void insertUnits() {

        ThreeElementLinearListModel lan_pl = new ThreeElementLinearListModel(-1, 0, "Polish", 1);
        ThreeElementLinearListModel lan_en = new ThreeElementLinearListModel(-1, 0, "English", 0);
        ThreeElementLinearListModel lan_rus = new ThreeElementLinearListModel(-1, 0, "Russian", 0);
        dbHelper.insertLanguage(lan_pl);
        dbHelper.insertLanguage(lan_en);
        dbHelper.insertLanguage(lan_rus);
    }

    private void insertUnitsAppearance() {


//        Gender;
        AppearanceBlockModel gender1 = new AppearanceBlockModel(
                -1, R.drawable.ic_male, "", getResources().getString(R.string.male),
                0, "", "Gender");
        AppearanceBlockModel gender2 = new AppearanceBlockModel(
                -1, R.drawable.ic_female, "", getResources().getString(R.string.female),
                0, "", "Gender");
        AppearanceBlockModel gender3 = new AppearanceBlockModel(
                -1, R.drawable.ic_block, "", getResources().getString(R.string.other),
                0, "", "Gender");

        dbHelper.insertAppearanceBlock(gender1);
        dbHelper.insertAppearanceBlock(gender2);
        dbHelper.insertAppearanceBlock(gender3);

//        Units;
        AppearanceBlockModel unit1 = new AppearanceBlockModel(
                -1, R.drawable.ic_hexagon, "", "Height",
                0, "", "Units");
        AppearanceBlockModel unit2 = new AppearanceBlockModel(
                -1, R.drawable.ic_hexagon, "", "Weight",
                0, "", "Units");

        dbHelper.insertAppearanceBlock(unit1);
        dbHelper.insertAppearanceBlock(unit2);


//        Units other table;
        AppearanceBlockModel model = new AppearanceBlockModel(-1, "cm");
        AppearanceBlockModel model1 = new AppearanceBlockModel(-1, "in");

        dbHelper.insertHeightTab(model);
        dbHelper.insertHeightTab(model1);


        AppearanceBlockModel model2 = new AppearanceBlockModel(-1, "kg");
        AppearanceBlockModel model3 = new AppearanceBlockModel(-1, "lbs");

        dbHelper.insertWeightTab(model2);
        dbHelper.insertWeightTab(model3);


//        Goals;
        AppearanceBlockModel goal1 = new AppearanceBlockModel(
                -1, 0, "", getResources().getString(R.string.strength),
                0, "", "Goal");
        AppearanceBlockModel goal2 = new AppearanceBlockModel(
                -1, 0, "", getResources().getString(R.string.muscle),
                0, "", "Goal");
        AppearanceBlockModel goal3 = new AppearanceBlockModel(
                -1, 0, "", getResources().getString(R.string.fatLose),
                0, "", "Goal");
        AppearanceBlockModel goal4 = new AppearanceBlockModel(
                -1, 0, "", getResources().getString(R.string.technique),
                0, "", "Goal");

        dbHelper.insertAppearanceBlock(goal1);
        dbHelper.insertAppearanceBlock(goal2);
        dbHelper.insertAppearanceBlock(goal3);
        dbHelper.insertAppearanceBlock(goal4);


//        Level;
        AppearanceBlockModel level1 = new AppearanceBlockModel(
                -1, R.drawable.ic_star_one, "", getResources().getString(R.string.beginner),
                0, "", "Level");
        AppearanceBlockModel level2 = new AppearanceBlockModel(
                -1, R.drawable.ic_star_two, "", getResources().getString(R.string.intermediate),
                0, "", "Level");
        AppearanceBlockModel level3 = new AppearanceBlockModel(
                -1, R.drawable.ic_star_three, "", getResources().getString(R.string.advanced),
                0, "", "Level");

        dbHelper.insertAppearanceBlock(level1);
        dbHelper.insertAppearanceBlock(level2);
        dbHelper.insertAppearanceBlock(level3);


//        Performance;
        AppearanceBlockModel performance1 = new AppearanceBlockModel(
                -1, 0, "", getResources().getString(R.string.push),
                0, "", "Performance");
        AppearanceBlockModel performance2 = new AppearanceBlockModel(
                -1, 0, "", getResources().getString(R.string.pull),
                0, "", "Performance");
        AppearanceBlockModel performance3 = new AppearanceBlockModel(
                -1, 0, "", getResources().getString(R.string.squad),
                0, "", "Performance");
        AppearanceBlockModel performance4 = new AppearanceBlockModel(
                -1, 0, "", getResources().getString(R.string.dip),
                0, "", "Performance");

        dbHelper.insertAppearanceBlock(performance1);
        dbHelper.insertAppearanceBlock(performance2);
        dbHelper.insertAppearanceBlock(performance3);
        dbHelper.insertAppearanceBlock(performance4);


//        User section ID{17 - 20};

        AppearanceBlockModel username = new AppearanceBlockModel(
                -1, R.drawable.ic_person, "", "Username",
                0, "", "User");
        AppearanceBlockModel email = new AppearanceBlockModel(
                -1, R.drawable.ic_email, "", "E-mail",
                0, "", "User");
        AppearanceBlockModel password = new AppearanceBlockModel(
                -1, R.drawable.ic_lock, "", "Password",
                0, "", "User");

        dbHelper.insertAppearanceBlock(username);
        dbHelper.insertAppearanceBlock(email);
        dbHelper.insertAppearanceBlock(password);

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
