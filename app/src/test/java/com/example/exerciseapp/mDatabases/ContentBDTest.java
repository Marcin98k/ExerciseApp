package com.example.exerciseapp.mDatabases;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.exerciseapp.mEnums.FromWhere;
import com.example.exerciseapp.mEnums.Level;
import com.example.exerciseapp.mModels.TrainingModel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ContentBDTest {

    @Mock
    private SQLiteDatabase mockDatabase;

    @Mock
    private ContentDB contentDB;

    @Mock
    private Context context;

    @Test
    void testInsertWorkoutTK() {

        TrainingModel trainingModel = new TrainingModel("Image", "Name",
                Level.INTERMEDIATE, "1,2,3", 25, 15,
                "description", FromWhere.USER, 1);

        ContentDB yourClassInstance = new ContentDB(context);

        when(mockDatabase.insert(anyString(), (String) isNull(), any(ContentValues.class))).thenReturn(1L);

        boolean isInserted = yourClassInstance.insertWorkoutTK(trainingModel);

        assertTrue(isInserted);

        verify(mockDatabase).insert(anyString(), (String) isNull(), any(ContentValues.class));
    }

}