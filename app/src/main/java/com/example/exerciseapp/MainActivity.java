package com.example.exerciseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.exerciseapp.Models.ThreeElementLinearListModel;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(MainActivity.this);

        if (dbHelper.getCount("LANGUAGE") <= 0) {
            insertUnits();
        }

        initInternalFolders();
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
