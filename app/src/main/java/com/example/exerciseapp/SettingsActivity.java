package com.example.exerciseapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.mClasses.GlobalClass;
import com.example.exerciseapp.mClasses.SharedViewModel;
import com.example.exerciseapp.mDatabases.DBHelper;
import com.example.exerciseapp.mEnums.FragmentAction;
import com.example.exerciseapp.mEnums.ListType;
import com.example.exerciseapp.mEnums.NumberOfItem;
import com.example.exerciseapp.mEnums.RowNames;
import com.example.exerciseapp.mInterfaces.ITitleChangeListener;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mInterfaces.UpdateStringsDB;
import com.example.exerciseapp.mModels.AppearanceBlockModel;
import com.example.exerciseapp.mModels.FourElementLinearListModel;
import com.example.exerciseapp.mModels.IntegerModel;
import com.example.exerciseapp.mModels.ThreeElementLinearListModel;
import com.example.exerciseapp.mModels.UserInformationModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SettingsActivity extends AppCompatActivity implements
        LinearListFragment.SelectedItem, UpdateIntegersDB, UpdateStringsDB, ITitleChangeListener {

    private TextView fragmentTitle;


    private int unitsID;
    private int numbersID;
    private List<Integer> tab;
    private final int USER_ID = 1;


    private static final String TAG = "SettingsActivity";
    private final String tagHeight = "tagHeight";
    private final String tagWeight = "tagWeight";
    private static final int PERMISSION_REQUEST_CODE = 1;



    //    LinearListFragment == TELL
    private static final String tagMainList = "tagTELL_main";
    private static final String tagLanguageList = "tagTELL_language";
    private static final String tagAccountList = "tagTELL_account";
    private static final String tagNotificationList = "tagTELL_notification";
    private static final String tagProfileLists = "tagTELL_profile";
    private static final String informationName = "userInformation";
    private static final String goalsName = "userGoals";
    private static final String performanceName = "userPerformance";
    private static final String levelName = "userLevel";
    private static final String genderName = "userGender";


    private Bundle bundle;
    private DBHelper dbHelper;
    private SelectHeightFragment selectHeightFragment;
    private SelectWeightFragment selectWeightFragment;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(GlobalClass.initLanguage(newBase));
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


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView(savedInstanceState);
        initMenu();
        dbHelper = new DBHelper(this);

        tab = new ArrayList<>();
        SharedViewModel sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.getSharedInt().observe(this, tab::add);
    }

    private void initView(Bundle savedInstanceState) {

        TextView firstPartOfTitle = findViewById(R.id.act_settings_title_part_one);
        fragmentTitle = findViewById(R.id.act_settings_title_part_two);

        firstPartOfTitle.setText(getString(R.string.settings));

        if (findViewById(R.id.act_settings_mainContainer) != null) {

            if (savedInstanceState != null) {
                return;
            }
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            LinearListFragment linearListFragment = new LinearListFragment();
            bundle = new Bundle();
            bundle.putParcelableArrayList("currentList", (ArrayList<? extends Parcelable>) mainList());
            bundle.putString("listName", tagMainList);
            linearListFragment.setArguments(bundle);
            ft.addToBackStack(tagMainList);
            ft.add(R.id.act_settings_mainContainer, linearListFragment, tagMainList);
            ft.commit();
        }
    }

    private void initMenu() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.act_settings_bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_bar_settings);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case (R.id.bottom_nav_bar_main):
                    startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                    finish();
                    return true;
                case (R.id.bottom_nav_bar_workout):
                    startActivity(new Intent(getApplicationContext(), LibraryActivity.class));
                    finish();
                    return true;
                case (R.id.bottom_nav_bar_profile):
                    startActivity(new Intent(getApplicationContext(), UserActivity.class));
                    finish();
                    return true;
                case (R.id.bottom_nav_bar_settings):
                    return true;
            }
            return false;
        });
    }



    private void FragmentOperation(Fragment fragment, FragmentAction action, boolean addToBackStack,
                                   String tag, List<?> list, String fragmentName, ListType listType,
                                   NumberOfItem numberOfItem) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("currentList", (ArrayList<? extends Parcelable>) list);
        bundle.putString("fragmentName", fragmentName);
        bundle.putString("listName", tag);
        bundle.putSerializable("listType", listType);
        bundle.putSerializable("numberOfItem", numberOfItem);
        fragment.setArguments(bundle);
        ft.setReorderingAllowed(true);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        switch (action) {
            case ADD:
                ft.add(R.id.act_settings_mainContainer, fragment, tag);
                break;
            case REPLACE:
                ft.replace(R.id.act_settings_mainContainer, fragment, tag);
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
                ft.add(R.id.act_settings_mainContainer, fragment, tag);
                break;
            case REPLACE:
                ft.replace(R.id.act_settings_mainContainer, fragment, tag);
                break;
            case REMOVE:
                ft.remove(fragment);
                break;
            default:
                Log.e("SettingsActivity!", " -> FragmentActionSample: switch(enum) == default");
        }
        ft.commit();
    }



    private ThreeElementLinearListModel fillList(int icon,
                                                 String name, int action) {
        return new ThreeElementLinearListModel(-1, icon, name, action);
    }

    private List<UserInformationModel> userInformationList() {
        return dbHelper.getInformationUser(USER_ID);
    }

    private List<ThreeElementLinearListModel> languageListModel() {
        return dbHelper.showLanguage()
                .stream().map(languageModel -> new ThreeElementLinearListModel(
                        (int) languageModel.getId(), languageModel.getName(),
                        languageModel.getStatus() ? 1 : 0, languageModel.getPrefix(),
                        languageModel.getImage()
                )).collect(Collectors.toList());
    }

    private List<ThreeElementLinearListModel> mainList() {

        List<ThreeElementLinearListModel> list = new ArrayList<>();

        list.add(fillList(R.drawable.ic_person, getString(R.string.profile), R.drawable.ic_arrow_right));
        list.add(fillList(R.drawable.ic_lock, getString(R.string.account), R.drawable.ic_arrow_right));
        list.add(fillList(R.drawable.ic_notifications, getString(R.string.notification), R.drawable.ic_arrow_right));
        list.add(fillList(R.drawable.ic_flag, getString(R.string.language), R.drawable.ic_arrow_right));
        list.add(fillList(R.drawable.ic_question_mark, getString(R.string.questions), R.drawable.ic_arrow_right));
        list.add(fillList(R.drawable.ic_lock, getString(R.string.future_functions), R.drawable.ic_arrow_right));
        list.add(fillList(R.drawable.ic_email, getString(R.string.about), R.drawable.ic_arrow_right));

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
                id, R.drawable.ic_person, getString(R.string.username), "", username);
        FourElementLinearListModel emailModel = new FourElementLinearListModel(
                id, R.drawable.ic_email, getString(R.string.e_mail), "", email);
        FourElementLinearListModel passwordModel = new FourElementLinearListModel(
                id, R.drawable.ic_lock, getString(R.string.password), "", password);
        list.add(usernameModel);
        list.add(emailModel);
        list.add(passwordModel);

        return list;
    }

    private List<FourElementLinearListModel> userUnit() {
        List<FourElementLinearListModel> show = new ArrayList<>();
        int units = userInformationList().get(0).getUnits();

        int height = dbHelper.getUserUnit(units).get(0).getFirstValue();
        int weight = dbHelper.getUserUnit(units).get(0).getSecondValue();

        int unitHeight = dbHelper.getUserUnit(units).get(0).getThirdValue();
        int unitWeight = dbHelper.getUserUnit(units).get(0).getForthValue();

        int mHeight1 = dbHelper.getMiddleIndex(height).get(0).getFirstValue();
        int mHeight11 = dbHelper.getMiddleIndex(height).get(0).getSecondValue();

        int mWeight1 = dbHelper.getMiddleIndex(weight).get(0).getFirstValue();
        int mWeight11 = dbHelper.getMiddleIndex(weight).get(0).getSecondValue();

        List<AppearanceBlockModel> appearanceBlockModelList = dbHelper.getAppearanceBlock(mHeight11);
        List<AppearanceBlockModel> appearanceBlockModelList1 = dbHelper.getAppearanceBlock(mWeight11);

        FourElementLinearListModel modelHeight = new FourElementLinearListModel(
                units,
                appearanceBlockModelList.get(0).getIcon(),
                appearanceBlockModelList.get(0).getName(),
                String.valueOf(mHeight1),
                dbHelper.getUnitHeight(unitHeight),
                height);

        FourElementLinearListModel modelWeight = new FourElementLinearListModel(
                units,
                appearanceBlockModelList1.get(0).getIcon(),
                appearanceBlockModelList1.get(0).getName(),
                String.valueOf(mWeight1),
                dbHelper.getUnitWeight(unitWeight),
                weight);
        show.add(modelHeight);
        show.add(modelWeight);

        return show;
    }

    private List<FourElementLinearListModel> userPerformance() {

        List<FourElementLinearListModel> show = new ArrayList<>();

        int performance = userInformationList().get(0).getPerformance();

        int per1 = dbHelper.getPerformance(performance).get(0).getFirstValue();
        int per2 = dbHelper.getPerformance(performance).get(0).getSecondValue();
        int per3 = dbHelper.getPerformance(performance).get(0).getThirdValue();
        int per4 = dbHelper.getPerformance(performance).get(0).getForthValue();

        int mPer1 = dbHelper.getMiddleIndex(per1).get(0).getFirstValue();
        int mPer11 = dbHelper.getMiddleIndex(per1).get(0).getSecondValue();

        int mPer2 = dbHelper.getMiddleIndex(per2).get(0).getFirstValue();
        int mPer22 = dbHelper.getMiddleIndex(per2).get(0).getSecondValue();

        int mPer3 = dbHelper.getMiddleIndex(per3).get(0).getFirstValue();
        int mPer33 = dbHelper.getMiddleIndex(per3).get(0).getSecondValue();

        int mPer4 = dbHelper.getMiddleIndex(per4).get(0).getFirstValue();
        int mPer44 = dbHelper.getMiddleIndex(per4).get(0).getSecondValue();

        List<AppearanceBlockModel> appearanceBlockList1 = dbHelper.getAppearanceBlock(mPer11);
        List<AppearanceBlockModel> appearanceBlockList2 = dbHelper.getAppearanceBlock(mPer22);
        List<AppearanceBlockModel> appearanceBlockList3 = dbHelper.getAppearanceBlock(mPer33);
        List<AppearanceBlockModel> appearanceBlockList4 = dbHelper.getAppearanceBlock(mPer44);

        FourElementLinearListModel model1 = new FourElementLinearListModel(
                dbHelper.getMiddleIndex(per1).get(0).getId(),
                appearanceBlockList1.get(0).getIcon(),
                appearanceBlockList1.get(0).getName(),
                String.valueOf(mPer1),
                "");

        FourElementLinearListModel model2 = new FourElementLinearListModel(
                dbHelper.getMiddleIndex(per2).get(0).getId(),
                appearanceBlockList2.get(0).getIcon(),
                appearanceBlockList2.get(0).getName(),
                String.valueOf(mPer2),
                "");

        FourElementLinearListModel model3 = new FourElementLinearListModel(
                dbHelper.getMiddleIndex(per3).get(0).getId(),
                appearanceBlockList3.get(0).getIcon(),
                appearanceBlockList3.get(0).getName(),
                String.valueOf(mPer3),
                "");

        FourElementLinearListModel model4 = new FourElementLinearListModel(
                dbHelper.getMiddleIndex(per4).get(0).getId(),
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

    private List<IntegerModel> notificationList() {

        List<IntegerModel> list = new ArrayList<>();

        int notification = userInformationList().get(0).getNotification();

        int id = dbHelper.getNotifications(notification).get(0).getId();
        int status = dbHelper.getNotifications(notification).get(0).getFirstValue();
        int days = dbHelper.getNotifications(notification).get(0).getSecondValue();
        int hour = dbHelper.getNotifications(notification).get(0).getThirdValue();
        int workoutId = dbHelper.getNotifications(notification).get(0).getForthValue();

        IntegerModel model = new IntegerModel(id, status, days, hour, workoutId);
        list.add(model);
        return list;
    }

    private List<FourElementLinearListModel> userGoals() {

        List<FourElementLinearListModel> show = new ArrayList<>();

        int goals = userInformationList().get(0).getGoals();

        int goal1 = dbHelper.getGoals(goals).get(0).getFirstValue();
        int goal2 = dbHelper.getGoals(goals).get(0).getSecondValue();
        int goal3 = dbHelper.getGoals(goals).get(0).getThirdValue();
        int goal4 = dbHelper.getGoals(goals).get(0).getForthValue();

        int mGoal1 = dbHelper.getMiddleIndex(goal1).get(0).getFirstValue();
        int mGoal11 = dbHelper.getMiddleIndex(goal1).get(0).getSecondValue();

        int mGoal2 = dbHelper.getMiddleIndex(goal2).get(0).getFirstValue();
        int mGoal22 = dbHelper.getMiddleIndex(goal2).get(0).getSecondValue();

        int mGoal3 = dbHelper.getMiddleIndex(goal3).get(0).getFirstValue();
        int mGoal33 = dbHelper.getMiddleIndex(goal3).get(0).getSecondValue();

        int mGoal4 = dbHelper.getMiddleIndex(goal4).get(0).getFirstValue();
        int mGoal44 = dbHelper.getMiddleIndex(goal4).get(0).getSecondValue();


        List<AppearanceBlockModel> appearanceBlockList1 = dbHelper.getAppearanceBlock(mGoal11);
        List<AppearanceBlockModel> appearanceBlockList2 = dbHelper.getAppearanceBlock(mGoal22);
        List<AppearanceBlockModel> appearanceBlockList3 = dbHelper.getAppearanceBlock(mGoal33);
        List<AppearanceBlockModel> appearanceBlockList4 = dbHelper.getAppearanceBlock(mGoal44);

        FourElementLinearListModel model1 = new FourElementLinearListModel(
                dbHelper.getMiddleIndex(goal1).get(0).getId(),
                appearanceBlockList1.get(0).getIcon(),
                appearanceBlockList1.get(0).getName(),
                String.valueOf(mGoal1),
                "");

        FourElementLinearListModel model2 = new FourElementLinearListModel(
                dbHelper.getMiddleIndex(goal2).get(0).getId(),
                appearanceBlockList2.get(0).getIcon(),
                appearanceBlockList2.get(0).getName(),
                String.valueOf(mGoal2),
                "");

        FourElementLinearListModel model3 = new FourElementLinearListModel(
                dbHelper.getMiddleIndex(goal3).get(0).getId(),
                appearanceBlockList3.get(0).getIcon(),
                appearanceBlockList3.get(0).getName(),
                String.valueOf(mGoal3),
                "");

        FourElementLinearListModel model4 = new FourElementLinearListModel(
                dbHelper.getMiddleIndex(goal4).get(0).getId(),
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

        int lvl1 = dbHelper.getLevel(level).get(0).getFirstValue();
        int lvl2 = dbHelper.getLevel(level).get(0).getSecondValue();
        int lvl3 = dbHelper.getLevel(level).get(0).getThirdValue();

        int mLvl1 = dbHelper.getMiddleIndex(lvl1).get(0).getFirstValue();
        int mLvl2 = dbHelper.getMiddleIndex(lvl1).get(0).getSecondValue();

        int mLvl11 = dbHelper.getMiddleIndex(lvl2).get(0).getFirstValue();
        int mLvl22 = dbHelper.getMiddleIndex(lvl2).get(0).getSecondValue();

        int mLvl111 = dbHelper.getMiddleIndex(lvl3).get(0).getFirstValue();
        int mLvl222 = dbHelper.getMiddleIndex(lvl3).get(0).getSecondValue();

        List<AppearanceBlockModel> appearanceBlockList1 = dbHelper.getAppearanceBlock(mLvl2);
        List<AppearanceBlockModel> appearanceBlockList2 = dbHelper.getAppearanceBlock(mLvl22);
        List<AppearanceBlockModel> appearanceBlockList3 = dbHelper.getAppearanceBlock(mLvl222);

        ThreeElementLinearListModel model1 = new ThreeElementLinearListModel(
                dbHelper.getMiddleIndex(lvl1).get(0).getId(),
                appearanceBlockList1.get(0).getIcon(),
                appearanceBlockList1.get(0).getName(),
                mLvl1);

        ThreeElementLinearListModel model2 = new ThreeElementLinearListModel(
                dbHelper.getMiddleIndex(lvl2).get(0).getId(),
                appearanceBlockList2.get(0).getIcon(),
                appearanceBlockList2.get(0).getName(),
                mLvl11);

        ThreeElementLinearListModel model3 = new ThreeElementLinearListModel(
                dbHelper.getMiddleIndex(lvl3).get(0).getId(),
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

        int male = dbHelper.getGender(gender).get(0).getFirstValue();
        int female = dbHelper.getGender(gender).get(0).getSecondValue();
        int other = dbHelper.getGender(gender).get(0).getThirdValue();

        int male1 = dbHelper.getMiddleIndex(male).get(0).getFirstValue();
        int male2 = dbHelper.getMiddleIndex(male).get(0).getSecondValue();

        int female1 = dbHelper.getMiddleIndex(female).get(0).getFirstValue();
        int female2 = dbHelper.getMiddleIndex(female).get(0).getSecondValue();

        int other1 = dbHelper.getMiddleIndex(other).get(0).getFirstValue();
        int other2 = dbHelper.getMiddleIndex(other).get(0).getSecondValue();


        List<AppearanceBlockModel> maleModel = dbHelper.getAppearanceBlock(male2);
        List<AppearanceBlockModel> femaleModel = dbHelper.getAppearanceBlock(female2);
        List<AppearanceBlockModel> otherModel = dbHelper.getAppearanceBlock(other2);

        ThreeElementLinearListModel model1 = new ThreeElementLinearListModel(
                dbHelper.getMiddleIndex(male).get(0).getId(),
                maleModel.get(0).getIcon(),
                maleModel.get(0).getName(),
                male1);

        ThreeElementLinearListModel model2 = new ThreeElementLinearListModel(
                dbHelper.getMiddleIndex(female).get(0).getId(),
                femaleModel.get(0).getIcon(),
                femaleModel.get(0).getName(),
                female1);

        ThreeElementLinearListModel model3 = new ThreeElementLinearListModel(
                dbHelper.getMiddleIndex(other).get(0).getId(),
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
        if (listName.equals(tagAccountList)) {
            if (position == 0) {
                dbHelper.updateUser(RowNames.NAME, id, firstVal);
            } else if (position == 1) {
                dbHelper.updateUser(RowNames.EMAIL, id, firstVal);
            } else if (position == 2) {
                dbHelper.updateUser(RowNames.PASSWORD, id, firstVal);
            } else {
                Log.e(TAG, "strValues:  listName --> default");
            }
            Log.e(TAG, "strValues: " + accountList());
        }
    }

    @Override
    public void values(String listName, int firstValue, int secondValue, int thirdValue) {

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
                    ft.replace(R.id.act_settings_mainContainer, selectHeightFragment, tagHeight);
                    ft.commit();
                } else if (firstValue == 1) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    selectWeightFragment = new SelectWeightFragment();
                    ft.setReorderingAllowed(true);
                    ft.addToBackStack(tagWeight);
                    ft.replace(R.id.act_settings_mainContainer, selectWeightFragment, tagWeight);
                    ft.commit();
                }
                break;
            case performanceName:
                dbHelper.updatePerformance(firstValue, secondValue);
                break;
            case goalsName:
                dbHelper.updateGoals(firstValue, secondValue);
                break;
            case levelName:
                dbHelper.switchLevel(firstValue, secondValue);
                break;
            case genderName:
                dbHelper.switchGender(firstValue, secondValue);
                break;
            case tagLanguageList:
                Log.i(TAG, "values: " + firstValue + " sec: " + secondValue);
                dbHelper.switchLanguage(firstValue, secondValue);
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                FragmentManager fragmentManager = getSupportFragmentManager();
                int count = fragmentManager.getBackStackEntryCount();
                for (int i = 0; i < count; i++) {
                    fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(i).getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    Log.i(TAG, "values(BackStack): " + fragmentManager.getBackStackEntryAt(i).getName() + "; \n");
                    Log.i(TAG, "values(BackStack): Empty");
                }
                break;
            default:
                Log.e(TAG, "values:  listName --> default");
                break;
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
                    ft.replace(R.id.act_settings_mainContainer, profileFragment, tagProfileLists);
                    ft.commit();
                    break;
                case 1:
                    FragmentOperation(new FourElementListFragment(), FragmentAction.REPLACE,
                            true, tagAccountList, accountList(),
                            getString(R.string.account), ListType.SELECTABLE_BUTTONS,
                            NumberOfItem.TWO);
                    break;
                case 2:
                    FragmentOperation(new NotificationFragment(), FragmentAction.REPLACE,
                            true, tagNotificationList, notificationList(),
                            getString(R.string.notification), ListType.SELECTABLE_BUTTONS,
                            NumberOfItem.ONE);
                    break;
                case 3:
                    FragmentOperation(new RadioButtonList(), FragmentAction.REPLACE,
                            true, tagLanguageList, languageListModel(),
                            getString(R.string.language), ListType.RADIO_BUTTONS, NumberOfItem.ONE);
                    break;
                case 4:
                    FragmentOperation(new ContactFragment(), FragmentAction.REPLACE,
                            true, ContactFragment.class.getName());
                    break;
                case 5:
                    FragmentOperation(new TimeBreakFragment(), FragmentAction.REPLACE,
                            true, "TagTimeBreak");
                    break;
            }
        }
    }

    @Override
    public void title(String value) {
        fragmentTitle.setText(value);
    }
}