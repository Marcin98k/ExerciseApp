package com.example.exerciseapp;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.Enums.FragmentAction;
import com.example.exerciseapp.Enums.RowNames;
import com.example.exerciseapp.Interfaces.UpdateStringsDB;
import com.example.exerciseapp.Interfaces.UpdateValueDB;
import com.example.exerciseapp.Models.AppearanceBlockModel;
import com.example.exerciseapp.Models.FourElementLinearListModel;
import com.example.exerciseapp.Models.IntegerModel;
import com.example.exerciseapp.Models.StringModel;
import com.example.exerciseapp.Models.ThreeElementLinearListModel;
import com.example.exerciseapp.Models.UserInformationModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity implements
        LinearListFragment.SelectedItem, UpdateValueDB, UpdateStringsDB {

    private static final int PERMISSION_REQUEST_CODE = 1;

    private static final String TAG = "SettingsActivity";
    //    LinearListFragment == TELL
    private final String tagMainList = "tagTELL_main";
    private final String tagLanguageList = "tagTELL_language";
    private final String tagAccountList = "tagTELL_account";
    private final String tagNotificationList = "tagTELL_notification";
    private final String tagUnitsList = "tagTELL_units";
    private final String tagProfileLists = "tagTELL_profile";
    private final String informationName = "userInformation";
    private final String goalsName = "userGoals";
    private final String performanceName = "userPerformance";
    private final String levelName = "userLevel";
    private final String genderName = "userGender";

    LinearListFragment linearListFragment;
    SharedViewModel sharedViewModel;
    List<Integer> tab;
    SelectHeightFragment selectHeightFragment;
    SelectWeightFragment selectWeightFragment;
    ProfileFragment profileFragment;
    private final int USER_ID = 1;
    private Bundle bundle;
    private DBHelper dbHelper;
    private final HashMap<String, String> stringHashMap = new HashMap<>();
    private final HashMap<String, Integer> integerHashMap = new HashMap<>();
    private FragmentManager fragmentManager;
    private int unitsID;
    private int numbersID;
    private final String tagHeight = "tagHeight";
    private final String tagWeight = "tagWeight";

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dbHelper = new DBHelper(this);

        tab = new ArrayList<>();
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.getSharedInt().observe(this, tab::add);

        fragmentManager = getSupportFragmentManager();

        if (findViewById(R.id.aSettings_mainContainer) != null) {

            if (savedInstanceState != null) {
                return;
            }
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            linearListFragment = new LinearListFragment();
            bundle = new Bundle();
            bundle.putParcelableArrayList("currentList", (ArrayList<? extends Parcelable>) mainList());
            bundle.putString("listName", tagMainList);
            linearListFragment.setArguments(bundle);
            ft.addToBackStack(tagMainList);
            ft.add(R.id.aSettings_mainContainer, linearListFragment, tagMainList);
            ft.commit();
        }
    }

//    !@!@! TO-DO-JAVA overloading method FragmentActionSample;


    private void FragmentOperation(Fragment fragment, FragmentAction action,
                                   boolean addToBackStack, String tag, List<?> list) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("currentList", (ArrayList<? extends Parcelable>) list);
        bundle.putString("listName", tag);
        fragment.setArguments(bundle);
        ft.setReorderingAllowed(true);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        switch (action) {
            case ADD:
                ft.add(R.id.aSettings_mainContainer, fragment, tag);
                break;
            case REPLACE:
                ft.replace(R.id.aSettings_mainContainer, fragment, tag);
                break;
            case REMOVE:
                ft.remove(fragment);
                break;
            default:
                Log.e("SettingsActivity!", " -> FragmentActionSample: switch(enum) == default");
        }
        ft.commit();
    }

    private void FragmentOperation(Fragment fragment, FragmentAction action,
                                   boolean addToBackStack, String tag) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setReorderingAllowed(true);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        switch (action) {
            case ADD:
                ft.add(R.id.aSettings_mainContainer, fragment, tag);
                break;
            case REPLACE:
                ft.replace(R.id.aSettings_mainContainer, fragment, tag);
                break;
            case REMOVE:
                ft.remove(fragment);
                break;
            default:
                Log.e("SettingsActivity!", " -> FragmentActionSample: switch(enum) == default");
        }
        ft.commit();
    }

    private void refreshFragment(String tag, FragmentAction action) {

        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment frag;
        frag = (Fragment) fragmentManager.findFragmentByTag(tag);

        if (frag != null) {
            switch (action) {
                case ATTACH:
                    ft.attach(frag);
                    break;
                case DETACH:
                    ft.detach(frag);
                    break;
                case DETATT:
                    ft.detach(frag);
                    ft.attach(frag);
                    break;
                default:
                    Log.e("SettingsActivity!", " -> refreshFragment: switch(enum) == default ");
            }
            ft.commit();
        } else {
            Log.e("SettingsActivity!", " -> refreshFragment: frag !=null ");
        }
    }

    @Override
    public void item(String list, int position, int currentlyPosition) {

        if (Objects.equals(list, tagMainList)) {
            switch (position) {
                case 0:
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ProfileFragment profileFragment = new ProfileFragment();
                    bundle = new Bundle();
                    bundle.putParcelableArrayList(informationName, (ArrayList<? extends Parcelable>) userUnit());
                    bundle.putParcelableArrayList(goalsName, (ArrayList<? extends Parcelable>) userGoals());
                    bundle.putParcelableArrayList(performanceName, (ArrayList<? extends Parcelable>) userPerformance());
                    bundle.putParcelableArrayList(levelName, (ArrayList<? extends Parcelable>) userLevel());
                    bundle.putParcelableArrayList(genderName, (ArrayList<? extends Parcelable>) userGender());
                    profileFragment.setArguments(bundle);
                    ft.setReorderingAllowed(true);
                    ft.addToBackStack(tagProfileLists);
                    ft.replace(R.id.aSettings_mainContainer, profileFragment, tagProfileLists);
                    ft.commit();
                    break;
                case 1:
                   FragmentOperation(new FourElementListFragment(), FragmentAction.REPLACE,
                           true, tagAccountList, accountList());
                    break;
                case 2:
                    FragmentOperation(new NotificationFragment(), FragmentAction.REPLACE,
                            true, tagNotificationList, notificationList());
                    break;
                case 3:
                    FragmentOperation(new RadioButtonList(), FragmentAction.REPLACE,
                            true, tagLanguageList, languageListModel());
                    break;
                case 4:
                    FragmentOperation(new ContactFragment(), FragmentAction.REPLACE,
                            true, ContactFragment.class.getName());
                    break;
                case 5:
                    Log.i(TAG, "item(5): " + showFutureTab().get(0).getName());
                    break;
            }
        }
    }

    private List<UserInformationModel> userInformationList() {
        return dbHelper.showInformationUser(USER_ID);
    }

    private List<ThreeElementLinearListModel> languageListModel() {
        return dbHelper.showLanguage();
    }

    private List<StringModel> showFutureTab() {
        return dbHelper.showFutureTab();
    }

    private List<ThreeElementLinearListModel> mainList() {

        List<ThreeElementLinearListModel> list = new ArrayList<>();

        list.add(fillList(-1, R.drawable.ic_person, "Profile", R.drawable.ic_arrow_right));
        list.add(fillList(-1, R.drawable.ic_lock, "Account", R.drawable.ic_arrow_right));
        list.add(fillList(-1, R.drawable.ic_notifications, "Notifications", R.drawable.ic_arrow_right));
        list.add(fillList(-1, R.drawable.ic_flag, "Language", R.drawable.ic_arrow_right));
        list.add(fillList(-1, R.drawable.ic_question_mark, "Questions", R.drawable.ic_arrow_right));
        list.add(fillList(-1, R.drawable.ic_lock, "Feature request", R.drawable.ic_arrow_right));
        list.add(fillList(-1, R.drawable.ic_email, "About", R.drawable.ic_arrow_right));

        return list;
    }

    private List<IntegerModel> notificationList() {
        List<IntegerModel> list = new ArrayList<>();

        int notification = userInformationList().get(0).getNotification();

        int id = dbHelper.showUserNotifications(notification).get(0).getId();
        int status = dbHelper.showUserNotifications(notification).get(0).getFirstValue();
        int days = dbHelper.showUserNotifications(notification).get(0).getSecondValue();
        int hour = dbHelper.showUserNotifications(notification).get(0).getThirdValue();
        int workoutId = dbHelper.showUserNotifications(notification).get(0).getForthValue();

        IntegerModel model = new IntegerModel(id, status, days, hour, workoutId);
        list.add(model);
        return list;
    }

    private List<FourElementLinearListModel> accountList() {
        List<FourElementLinearListModel> list = new ArrayList<>();

        int id = userInformationList().get(0).getId();
        String username = userInformationList().get(0).getName();
        String email = userInformationList().get(0).getEmail();

//        !@!@! TO-DO-JAVA convert to char[] - safety !!!;
        String password = userInformationList().get(0).getPassword();

        FourElementLinearListModel usernameModel = new FourElementLinearListModel(
                id, 0, "Username", username, ""
        );
        FourElementLinearListModel emailModel = new FourElementLinearListModel(
                id, 0, "Email", email, ""
        );
        FourElementLinearListModel passwordModel = new FourElementLinearListModel(
                id, 0, "Password", password, ""
        );
        list.add(usernameModel);
        list.add(emailModel);
        list.add(passwordModel);

        return list;
    }

    private ThreeElementLinearListModel fillList(int id, int icon,
                                                 String name, int action) {
        return new ThreeElementLinearListModel(id, icon, name, action);
    }

    private List<FourElementLinearListModel> userUnit() {
        List<FourElementLinearListModel> show = new ArrayList<>();
        int units = userInformationList().get(0).getUnits();

        int height = dbHelper.showUserUnit(units).get(0).getFirstValue();
        int weight = dbHelper.showUserUnit(units).get(0).getSecondValue();

        int unitHeight = dbHelper.showUserUnit(units).get(0).getThirdValue();
        int unitWeight = dbHelper.showUserUnit(units).get(0).getForthValue();

        int mHeight1 = dbHelper.showMiddleIndex(height).get(0).getFirstValue();
        int mHeight11 = dbHelper.showMiddleIndex(height).get(0).getSecondValue();

        int mWeight1 = dbHelper.showMiddleIndex(weight).get(0).getFirstValue();
        int mWeight11 = dbHelper.showMiddleIndex(weight).get(0).getSecondValue();

        List<AppearanceBlockModel> appearanceBlockModelList = dbHelper.showAppearanceBlock(mHeight11);
        List<AppearanceBlockModel> appearanceBlockModelList1 = dbHelper.showAppearanceBlock(mWeight11);

        FourElementLinearListModel modelHeight = new FourElementLinearListModel(
                units,
                appearanceBlockModelList.get(0).getIcon(),
                appearanceBlockModelList.get(0).getName(),
                String.valueOf(mHeight1),
                dbHelper.showUnitHeight(unitHeight).get(0).getName(),
                height);

        FourElementLinearListModel modelWeight = new FourElementLinearListModel(
                units,
                appearanceBlockModelList1.get(0).getIcon(),
                appearanceBlockModelList1.get(0).getName(),
                String.valueOf(mWeight1),
                dbHelper.showUnitWeight(unitWeight).get(0).getName(),
                weight);
        show.add(modelHeight);
        show.add(modelWeight);

        return show;
    }

    private List<FourElementLinearListModel> userPerformance() {

        List<FourElementLinearListModel> show = new ArrayList<>();

        int performance = userInformationList().get(0).getPerformance();

        int per1 = dbHelper.showPerformance(performance).get(0).getFirstValue();
        int per2 = dbHelper.showPerformance(performance).get(0).getSecondValue();
        int per3 = dbHelper.showPerformance(performance).get(0).getThirdValue();
        int per4 = dbHelper.showPerformance(performance).get(0).getForthValue();

        int mPer1 = dbHelper.showMiddleIndex(per1).get(0).getFirstValue();
        int mPer11 = dbHelper.showMiddleIndex(per1).get(0).getSecondValue();

        int mPer2 = dbHelper.showMiddleIndex(per2).get(0).getFirstValue();
        int mPer22 = dbHelper.showMiddleIndex(per2).get(0).getSecondValue();

        int mPer3 = dbHelper.showMiddleIndex(per3).get(0).getFirstValue();
        int mPer33 = dbHelper.showMiddleIndex(per3).get(0).getSecondValue();

        int mPer4 = dbHelper.showMiddleIndex(per4).get(0).getFirstValue();
        int mPer44 = dbHelper.showMiddleIndex(per4).get(0).getSecondValue();

        List<AppearanceBlockModel> appearanceBlockList1 = dbHelper.showAppearanceBlock(mPer11);
        List<AppearanceBlockModel> appearanceBlockList2 = dbHelper.showAppearanceBlock(mPer22);
        List<AppearanceBlockModel> appearanceBlockList3 = dbHelper.showAppearanceBlock(mPer33);
        List<AppearanceBlockModel> appearanceBlockList4 = dbHelper.showAppearanceBlock(mPer44);

        FourElementLinearListModel model1 = new FourElementLinearListModel(
                dbHelper.showMiddleIndex(per1).get(0).getId(),
                appearanceBlockList1.get(0).getIcon(),
                appearanceBlockList1.get(0).getName(),
                String.valueOf(mPer1),
                "");

        FourElementLinearListModel model2 = new FourElementLinearListModel(
                dbHelper.showMiddleIndex(per2).get(0).getId(),
                appearanceBlockList2.get(0).getIcon(),
                appearanceBlockList2.get(0).getName(),
                String.valueOf(mPer2),
                "");

        FourElementLinearListModel model3 = new FourElementLinearListModel(
                dbHelper.showMiddleIndex(per3).get(0).getId(),
                appearanceBlockList3.get(0).getIcon(),
                appearanceBlockList3.get(0).getName(),
                String.valueOf(mPer3),
                "");

        FourElementLinearListModel model4 = new FourElementLinearListModel(
                dbHelper.showMiddleIndex(per4).get(0).getId(),
                appearanceBlockList4.get(0).getIcon(),
                appearanceBlockList4.get(0).getName(),
                String.valueOf(mPer4),
                "");

        show.add(model1);
        show.add(model2);
        show.add(model3);
        show.add(model4);

        return show;
    }

    private List<FourElementLinearListModel> userGoals() {

        List<FourElementLinearListModel> show = new ArrayList<>();

        int goals = userInformationList().get(0).getGoals();

        int goal1 = dbHelper.showUserGoals(goals).get(0).getFirstValue();
        int goal2 = dbHelper.showUserGoals(goals).get(0).getSecondValue();
        int goal3 = dbHelper.showUserGoals(goals).get(0).getThirdValue();
        int goal4 = dbHelper.showUserGoals(goals).get(0).getForthValue();

        int mGoal1 = dbHelper.showMiddleIndex(goal1).get(0).getFirstValue();
        int mGoal11 = dbHelper.showMiddleIndex(goal1).get(0).getSecondValue();

        int mGoal2 = dbHelper.showMiddleIndex(goal2).get(0).getFirstValue();
        int mGoal22 = dbHelper.showMiddleIndex(goal2).get(0).getSecondValue();

        int mGoal3 = dbHelper.showMiddleIndex(goal3).get(0).getFirstValue();
        int mGoal33 = dbHelper.showMiddleIndex(goal3).get(0).getSecondValue();

        int mGoal4 = dbHelper.showMiddleIndex(goal4).get(0).getFirstValue();
        int mGoal44 = dbHelper.showMiddleIndex(goal4).get(0).getSecondValue();


        List<AppearanceBlockModel> appearanceBlockList1 = dbHelper.showAppearanceBlock(mGoal11);
        List<AppearanceBlockModel> appearanceBlockList2 = dbHelper.showAppearanceBlock(mGoal22);
        List<AppearanceBlockModel> appearanceBlockList3 = dbHelper.showAppearanceBlock(mGoal33);
        List<AppearanceBlockModel> appearanceBlockList4 = dbHelper.showAppearanceBlock(mGoal44);

        FourElementLinearListModel model1 = new FourElementLinearListModel(
                dbHelper.showMiddleIndex(goal1).get(0).getId(),
                appearanceBlockList1.get(0).getIcon(),
                appearanceBlockList1.get(0).getName(),
                String.valueOf(mGoal1),
                "");

        FourElementLinearListModel model2 = new FourElementLinearListModel(
                dbHelper.showMiddleIndex(goal2).get(0).getId(),
                appearanceBlockList2.get(0).getIcon(),
                appearanceBlockList2.get(0).getName(),
                String.valueOf(mGoal2),
                "");

        FourElementLinearListModel model3 = new FourElementLinearListModel(
                dbHelper.showMiddleIndex(goal3).get(0).getId(),
                appearanceBlockList3.get(0).getIcon(),
                appearanceBlockList3.get(0).getName(),
                String.valueOf(mGoal3),
                "");

        FourElementLinearListModel model4 = new FourElementLinearListModel(
                dbHelper.showMiddleIndex(goal4).get(0).getId(),
                appearanceBlockList4.get(0).getIcon(),
                appearanceBlockList4.get(0).getName(),
                String.valueOf(mGoal4),
                "");

        show.add(model1);
        show.add(model2);
        show.add(model3);
        show.add(model4);

        return show;
    }

    private List<ThreeElementLinearListModel> userLevel() {

        List<ThreeElementLinearListModel> showLvl = new ArrayList<>();

        int level = userInformationList().get(0).getLevel();

        int lvl1 = dbHelper.showLevel(level).get(0).getFirstValue();
        int lvl2 = dbHelper.showLevel(level).get(0).getSecondValue();
        int lvl3 = dbHelper.showLevel(level).get(0).getThirdValue();

        int mLvl1 = dbHelper.showMiddleIndex(lvl1).get(0).getFirstValue();
        int mLvl2 = dbHelper.showMiddleIndex(lvl1).get(0).getSecondValue();

        int mLvl11 = dbHelper.showMiddleIndex(lvl2).get(0).getFirstValue();
        int mLvl22 = dbHelper.showMiddleIndex(lvl2).get(0).getSecondValue();

        int mLvl111 = dbHelper.showMiddleIndex(lvl3).get(0).getFirstValue();
        int mLvl222 = dbHelper.showMiddleIndex(lvl3).get(0).getSecondValue();

        List<AppearanceBlockModel> appearanceBlockList1 = dbHelper.showAppearanceBlock(mLvl2);
        List<AppearanceBlockModel> appearanceBlockList2 = dbHelper.showAppearanceBlock(mLvl22);
        List<AppearanceBlockModel> appearanceBlockList3 = dbHelper.showAppearanceBlock(mLvl222);

        ThreeElementLinearListModel model1 = new ThreeElementLinearListModel(
                dbHelper.showMiddleIndex(lvl1).get(0).getId(),
                appearanceBlockList1.get(0).getIcon(),
                appearanceBlockList1.get(0).getName(),
                mLvl1);

        ThreeElementLinearListModel model2 = new ThreeElementLinearListModel(
                dbHelper.showMiddleIndex(lvl2).get(0).getId(),
                appearanceBlockList2.get(0).getIcon(),
                appearanceBlockList2.get(0).getName(),
                mLvl11);

        ThreeElementLinearListModel model3 = new ThreeElementLinearListModel(
                dbHelper.showMiddleIndex(lvl3).get(0).getId(),
                appearanceBlockList3.get(0).getIcon(),
                appearanceBlockList3.get(0).getName(),
                mLvl111);

        showLvl.add(model1);
        showLvl.add(model2);
        showLvl.add(model3);

        return showLvl;
    }

    private List<ThreeElementLinearListModel> userGender() {

        List<ThreeElementLinearListModel> show = new ArrayList<>();

        int gender = userInformationList().get(0).getGender();

        int male = dbHelper.showGender(gender).get(0).getFirstValue();
        int female = dbHelper.showGender(gender).get(0).getSecondValue();
        int other = dbHelper.showGender(gender).get(0).getThirdValue();

        int male1 = dbHelper.showMiddleIndex(male).get(0).getFirstValue();
        int male2 = dbHelper.showMiddleIndex(male).get(0).getSecondValue();

        int female1 = dbHelper.showMiddleIndex(female).get(0).getFirstValue();
        int female2 = dbHelper.showMiddleIndex(female).get(0).getSecondValue();

        int other1 = dbHelper.showMiddleIndex(other).get(0).getFirstValue();
        int other2 = dbHelper.showMiddleIndex(other).get(0).getSecondValue();


        List<AppearanceBlockModel> maleModel = dbHelper.showAppearanceBlock(male2);
        List<AppearanceBlockModel> femaleModel = dbHelper.showAppearanceBlock(female2);
        List<AppearanceBlockModel> otherModel = dbHelper.showAppearanceBlock(other2);

        ThreeElementLinearListModel model1 = new ThreeElementLinearListModel(
                dbHelper.showMiddleIndex(male).get(0).getId(),
                maleModel.get(0).getIcon(),
                maleModel.get(0).getName(),
                male1);

        ThreeElementLinearListModel model2 = new ThreeElementLinearListModel(
                dbHelper.showMiddleIndex(female).get(0).getId(),
                femaleModel.get(0).getIcon(),
                femaleModel.get(0).getName(),
                female1);

        ThreeElementLinearListModel model3 = new ThreeElementLinearListModel(
                dbHelper.showMiddleIndex(other).get(0).getId(),
                otherModel.get(0).getIcon(),
                otherModel.get(0).getName(),
                other1);

        show.add(model1);
        show.add(model2);
        show.add(model3);

        return show;
    }

    @Override
    public void strValues(String listName, int position, int id, String firstVal) {
        if(listName.equals(tagAccountList)) {
            if (position == 0) {
                dbHelper.updateUser(RowNames.NAME, id, firstVal);
            } else if (position == 1) {
                dbHelper.updateUser(RowNames.EMAIL, id, firstVal);
            } else if (position == 2) {
                dbHelper.updateUser(RowNames.PASSWORD, id, firstVal);
            } else {
                Log.e(TAG, "strValues:  listName --> default");
            }
            refreshFragment(tagAccountList, FragmentAction.DETATT);
            Log.e(TAG, "strValues: " + accountList().toString());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (selectHeightFragment != null) {
            selectHeightFragment.fragmentMessage();
            dbHelper.updateHeight(unitsID, numbersID, tab.get(0), tab.get(1));
            tab.clear();
            selectHeightFragment = null;
        } else if (selectWeightFragment != null) {
            selectWeightFragment.fragmentMessage();
            dbHelper.updateWeight(unitsID, numbersID, tab.get(0), tab.get(1));
            tab.clear();
            selectWeightFragment = null;
        } else {
            Log.i(TAG, "onBackPressed : null ");
        }
    }

    @Override
    public void values(String listName, int firstValue,
                       int secondValue, int thirdValue) {

        switch (listName) {
            case informationName:
                unitsID = secondValue;
                numbersID = thirdValue;
                if (firstValue == 0) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    selectHeightFragment = new SelectHeightFragment();
                    ft.setReorderingAllowed(true);
                    ft.addToBackStack(tagHeight);
                    ft.replace(R.id.aSettings_mainContainer, selectHeightFragment, tagHeight);
                    ft.commit();
                } else if (firstValue == 1) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    selectWeightFragment = new SelectWeightFragment();
                    ft.setReorderingAllowed(true);
                    ft.addToBackStack(tagWeight);
                    ft.replace(R.id.aSettings_mainContainer, selectWeightFragment, tagWeight);
                    ft.commit();
                }
                refreshFragment(tagProfileLists, FragmentAction.DETATT);
                break;
            case performanceName:
                dbHelper.updatePerformance(firstValue, secondValue);
                refreshFragment(tagProfileLists, FragmentAction.DETATT);
                break;
            case goalsName:
                dbHelper.updateGoals(firstValue, secondValue);
                refreshFragment(tagProfileLists, FragmentAction.DETATT);
                break;
            case levelName:
                dbHelper.switchLevel(firstValue, secondValue);
                refreshFragment(tagProfileLists, FragmentAction.DETATT);
                break;
            case genderName:
                dbHelper.switchGender(firstValue, secondValue);
                refreshFragment(tagProfileLists, FragmentAction.DETATT);
                break;
            case tagLanguageList:
                dbHelper.switchLanguage(firstValue, secondValue);
                refreshFragment(tagLanguageList, FragmentAction.DETATT);
                break;
            default:
                Log.e(TAG, "values:  listName --> default");
                break;
        }
    }
}