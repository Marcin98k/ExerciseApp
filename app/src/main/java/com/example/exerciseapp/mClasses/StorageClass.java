package com.example.exerciseapp.mClasses;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class StorageClass {

    private Context context;

    private String fileName;
    private String folderName;

    private String extension;
    File mPath = Environment.getExternalStorageDirectory();


    public StorageClass() {

    }

    @Override
    public String toString() {
        return "StorageClass{" +
                "context=" + context +
                ", fileName='" + fileName + '\'' +
                ", folderName='" + folderName + '\'' +
                ", mPath=" + mPath +
                '}';
    }

    public StorageClass(Context context, String folderName) {
        this.context = context;
        this.folderName = folderName;
    }

    public StorageClass(Context context, String fileName, String extension) {
        this.context = context;
        this.fileName = fileName;
        this.extension = extension;
    }

    public StorageClass(Context context, String folderName, String fileName, String extension) {
        this.context = context;
        this.folderName = folderName;
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }


    public void addFile() throws IOException {
        File create = new File(mPath, "/" + folderName + "/" + fileName);
        if (!create.exists()) {
            if (create.createNewFile());
        } else {
            Toast.makeText(context, fileName + " exists", Toast.LENGTH_SHORT).show();
        }
    }

    public void addFolderToInternalStorage() {
        File myDir = context.getFilesDir();
        String documents = "appFiles" + folderName;
        File documentsFolder = new File(myDir, documents);
        documentsFolder.mkdirs();

        Log.e("StorageClass", documentsFolder.toString());
    }

    public Bitmap setInternalBitmapFile() {

        if (fileName.isEmpty()) {
            return null;
        }
        if (extension.isEmpty()) {
            return null;
        }
        File file = new File(context.getFilesDir().getAbsolutePath(), fileName + extension);

        if (file.exists()) {
            return BitmapFactory.decodeFile(String.valueOf(file));
        } else {
            return null;
        }
    }

    public Bitmap setBitmapFile() {

        File file = new File(context.getExternalFilesDir(fileName + ".png").getAbsolutePath());

        if (file.exists()) {
//            PNG work, SVG not;
            Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(file));
            return bitmap;
        }

        return null;
    }

    public void addFolder() {
        File myDir = new File(context.getExternalFilesDir(fileName).getAbsolutePath());
        if (!myDir.exists()) {
            if (myDir.mkdir());
            Log.i("File_a", "Created " + myDir.getAbsolutePath());
        } else {
            Log.i("File_b", myDir.getAbsolutePath());
        }
    }
}
