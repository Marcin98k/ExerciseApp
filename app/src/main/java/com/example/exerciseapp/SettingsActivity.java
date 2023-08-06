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
            Log.i(TAG, "onBackPressed: " + unitsID + " " + tab.get(0) + " " + tab.get(1));
            dbHelper.updateHeight(USER_ID, tab.get(0), tab.get(1));
            tab.clear();
            selectHeightFragment = null;
        } else if (selectWeightFragment != null) {
            selectWeightFragment.fragmentMessage();
            dbHelper.updateWeight(USER_ID, tab.get(0), tab.get(1));
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
        return dbHelper.getUserUnit(userInformationList().get(0).getUnits());
    }

    private List<FourElementLinearListModel> userPerformance() {
        return dbHelper.getPerformance(userInformationList().get(0).getPerformance());
    }

    private List<FourElementLinearListModel> userGoals() {
        return dbHelper.getGoals(userInformationList().get(0).getGoals());
    }

    private List<ThreeElementLinearListModel> userLevel() {
        int value = dbHelper.getInformationUser(USER_ID).get(0).getLevel();
        List<ThreeElementLinearListModel> result = new ArrayList<>();
        String[] names = {getString(R.string.beginner),
                getString(R.string.intermediate), getString(R.string.advanced)};
        for (int i = 0; i < names.length; i++) {
            result.add(new ThreeElementLinearListModel(i, "",
                    names[i], value == i ? 1 : 0));
        }
        return result;
    }

    private List<ThreeElementLinearListModel> userGender() {
        int value = dbHelper.getInformationUser(USER_ID).get(0).getGender();
        List<ThreeElementLinearListModel> result = new ArrayList<>();
        String[] gender = {getString(R.string.male),
                getString(R.string.female), getString(R.string.other)};
        for (int i = 0; i < gender.length; i++) {
            result.add(new ThreeElementLinearListModel(i, "",
                    gender[i], value == i ? 1 : 0));
        }
        return result;
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

    @Override
    public void strValues(String listName, int position, int id, String firstVal) {
        if (listName.equals(tagAccountList)) {
            switch (position) {
                case 0:
                    dbHelper.updateUser(RowNames.NAME, id, firstVal);
                    break;
                case 1:
                    dbHelper.updateUser(RowNames.EMAIL, id, firstVal);
                    break;
                case 2:
                    dbHelper.updateUser(RowNames.PASSWORD, id, firstVal);
                    break;
                default:
                    Log.e(TAG, "strValues:  listName --> default");
                    break;
            }
            Log.e(TAG, "strValues: " + accountList());
        }
    }

    @Override
    public void values(String listName, int firstValue, int secondValue, int thirdValue) {

        switch (listName) {
            case informationName:
                unitsID = secondValue;
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
                dbHelper.updateUserPerformance(USER_ID, firstValue, secondValue);
                break;
            case goalsName:
                dbHelper.updateUserGoals(USER_ID, firstValue, secondValue);
                break;
            case levelName:
                Log.i(TAG, "values: switch Level" + firstValue + " " + secondValue + " " + thirdValue);
                dbHelper.switchUserLevel(USER_ID, secondValue);
                break;
            case genderName:
                Log.i(TAG, "values: switch Gender");
                dbHelper.switchUserGender(USER_ID, secondValue);
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