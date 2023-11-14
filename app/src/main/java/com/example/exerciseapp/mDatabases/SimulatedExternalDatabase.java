package com.example.exerciseapp.mDatabases;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mModels.AppearanceBlockModel;
import com.example.exerciseapp.mModels.ExerciseDescriptionModel;
import com.example.exerciseapp.mModels.IntegerModel;
import com.example.exerciseapp.mModels.LanguageModel;
import com.example.exerciseapp.mModels.StringModel;
import com.example.exerciseapp.mModels.TaskDateModel;

public class SimulatedExternalDatabase extends AppCompatActivity {


    private DBHelper dbHelper;
    private ContentBD contentBD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        ExerciseDescriptionModel workout1 = new ExerciseDescriptionModel(-1, "Workout1 YTR", "", 2,
                5, "21", 10, 55, "descriptionWorkout1",
                "2,3,1", 0);
        ExerciseDescriptionModel workout2 = new ExerciseDescriptionModel(-1, "Workout2 URC", "", 1,
                2, "54", 21, 35, "descriptionWorkout2",
                "1,2,3", 0);
        ExerciseDescriptionModel workout3 = new ExerciseDescriptionModel(-1, "Workout3 YTN", "", 3,
                4, "71", 17, 15, "descriptionWorkout3",
                "3,2,1", 0);

        contentBD.insertWorkout(workout1);
        contentBD.insertWorkout(workout2);
        contentBD.insertWorkout(workout3);
    }

    private void insertExercise() {

        ExerciseDescriptionModel exercise1 = new ExerciseDescriptionModel(-1, "Exercise1 ABC", "", 2,
                5, "21", 1, 5, 20, "description1",
                1, 0);
        ExerciseDescriptionModel exercise2 = new ExerciseDescriptionModel(-1, "Exercise2 BGH", "", 1,
                2, "54", 2, 10, 25, "description2",
                2, 0);
        ExerciseDescriptionModel exercise3 = new ExerciseDescriptionModel(-1, "Exercise3 BCI", "", 3,
                4, "71", 1, 15, 35, "description3",
                3, 0);

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
