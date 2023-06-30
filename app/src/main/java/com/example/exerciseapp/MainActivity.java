package com.example.exerciseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.exerciseapp.mClasses.StorageClass;
import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mDatabases.DBHelper;
import com.example.exerciseapp.mModels.AppearanceBlockModel;
import com.example.exerciseapp.mModels.TaskDateModel;
import com.example.exerciseapp.mModels.ThreeElementLinearListModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ContentBD contentBD;

    private Button welcomeBtn;
    private Button settingsBtn;
    private Button libraryBtn;
    private Button userBtn;
    private Button exerciseBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(MainActivity.this);
        contentBD = new ContentBD(MainActivity.this);
        initView();


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

        if (dbHelper.getCount("LANGUAGE") <= 0) {
            insertUnits();
        }

        if (contentBD.getCount("EQUIPMENT_TAB") <= 0){
            insertEquipment();
        }
        if (contentBD.getCount("APPEARANCE_TAB") <= 0) {
            insertContentAppearance();
        }

        if (contentBD.getCount("DATE_TAB") <= 0) {
            insertTaskDate();
        }

        initInternalFolders();
    }

    private void initView() {
        welcomeBtn = findViewById(R.id.aMain_welcome);
        settingsBtn = findViewById(R.id.aMain_settings);
        libraryBtn = findViewById(R.id.aMain_library);
        userBtn = findViewById(R.id.aMain_user);
        exerciseBtn = findViewById(R.id.aMain_exercise);
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
                R.drawable.ic_hexagon, "Dumbbells",0);
        ThreeElementLinearListModel resistanceRubber = new ThreeElementLinearListModel(-1,
                R.drawable.ic_block, "ResistanceRubber",0);
        ThreeElementLinearListModel paralletes = new ThreeElementLinearListModel(-1,
                R.drawable.ic_hexagon, "Paralletes",0);
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
