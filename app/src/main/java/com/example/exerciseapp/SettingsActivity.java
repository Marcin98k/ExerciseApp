package com.example.exerciseapp;

import static android.content.ContentValues.TAG;
import static com.example.exerciseapp.mClasses.GlobalClass.*;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseapp.Settings.ContactFragment;
import com.example.exerciseapp.Settings.ProfileFragment;
import com.example.exerciseapp.SignInANDSingUp.SelectHeightFragment;
import com.example.exerciseapp.SignInANDSingUp.SelectWeightFragment;
import com.example.exerciseapp.mClasses.GlobalClass;
import com.example.exerciseapp.mClasses.SharedViewModel;
import com.example.exerciseapp.mDatabases.DBHelper;
import com.example.exerciseapp.mEnums.FragmentAction;
import com.example.exerciseapp.mEnums.ListType;
import com.example.exerciseapp.mEnums.NumberOfItem;
import com.example.exerciseapp.mEnums.UserDatabaseColumns;
import com.example.exerciseapp.mInterfaces.INotificationCallback;
import com.example.exerciseapp.mInterfaces.ISendUserData;
import com.example.exerciseapp.mInterfaces.TitleChangeListener;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mInterfaces.UpdateStringsDB;
import com.example.exerciseapp.mModels.FourElementLinearListModel;
import com.example.exerciseapp.mModels.IntegerModel;
import com.example.exerciseapp.mModels.ThreeElementLinearListModel;
import com.example.exerciseapp.mModels.UserInformationModel;
import com.example.exerciseapp.mResource.FourElementListFragment;
import com.example.exerciseapp.mResource.LinearListFragment;
import com.example.exerciseapp.mResource.NotificationFragment;
import com.example.exerciseapp.mResource.RadioButtonList;
import com.example.exerciseapp.mResource.ReminderBroadcast;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SettingsActivity extends AppCompatActivity implements
        LinearListFragment.SelectedItem, UpdateIntegersDB, UpdateStringsDB, TitleChangeListener,
        INotificationCallback, ISendUserData {

    public static String NOTIFICATION_CHANNEL_ID = "999";
    public static String defaultNotificationId = "default";
    private static final String NOTIFICATION_CONTENT_TEXT = "Time to train";

    private TextView fragmentTitle;

    private boolean reminderStatus;
    private long exerciseReminderInMilli;

    private List<Integer> tab;
    private long currentUserID;


    private Bundle bundle;
    private DBHelper dbHelper;
    private SelectHeightFragment selectHeightFragment;
    private SelectWeightFragment selectWeightFragment;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(GlobalClass.initLanguage(newBase));
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView(savedInstanceState);
        initMenu();

        dbHelper = new DBHelper(this);
        tab = new ArrayList<>(2);

        initializeSharedViewModel();
    }

    private void initView(Bundle savedInstanceState) {
        initializeViews();

        Intent intent = getIntent();
//        currentUserID = intent.getLongExtra(GlobalClass.userID, -1);
        currentUserID = 1;

        if (findViewById(R.id.act_settings_mainContainer) != null) {

            if (savedInstanceState != null) {
                return;
            }
            setupMainSettingsList();
        }
    }

    private void initializeViews() {
        TextView firstPartOfTitle = findViewById(R.id.act_settings_title_part_one);
        fragmentTitle = findViewById(R.id.act_settings_title_part_two);

        firstPartOfTitle.setText(getString(R.string.settings));
    }

    private void setupMainSettingsList() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        LinearListFragment linearListFragment = new LinearListFragment();
        bundle = new Bundle();
        bundle.putParcelableArrayList("currentList", (ArrayList<? extends Parcelable>) mainList());
        bundle.putString("listName", MAIN_LIST_NAME);
        linearListFragment.setArguments(bundle);
        ft.addToBackStack(MAIN_LIST_NAME);
        ft.add(R.id.act_settings_mainContainer, linearListFragment, MAIN_LIST_NAME);
        ft.commit();
    }

    private void initMenu() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.act_settings_bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_bar_settings);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case (R.id.bottom_nav_bar_main):
                    startNewActivity(MainActivity.class);
                    return true;
                case (R.id.bottom_nav_bar_workout):
                    startNewActivity(LibraryActivity.class);
                    return true;
                case (R.id.bottom_nav_bar_profile):
                    startNewActivity(UserActivity.class);
                    return true;
                case (R.id.bottom_nav_bar_settings):
                    recreate();
                    return true;
            }
            return false;
        });
    }

    private void initializeSharedViewModel() {
        SharedViewModel sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.getSharedInt().observe(this, tab::add);
    }

    private void startNewActivity(Class<?> activity) {
        Intent intent = new Intent(getApplicationContext(), activity);
        intent.putExtra(GlobalClass.userID, currentUserID);
        startActivity(intent);
        finish();
    }

    private void performFragmentOperation(Fragment fragment, FragmentAction action, boolean addToBackStack,
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
        performAction(ft, fragment, action, tag);
    }

    private void performAction(FragmentTransaction ft, Fragment fragment, FragmentAction action,
                               String tag) {
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
                throw new IllegalStateException("Option does not exist");
        }
        ft.commit();
    }

    private ThreeElementLinearListModel fillList(int icon, String name, int action) {
        return new ThreeElementLinearListModel(-1, icon, name, action);
    }

    private List<UserInformationModel> userInformationList() {
        return dbHelper.getInformationUser(currentUserID);
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
        list.add(fillList(R.drawable.ic_logout, getString(R.string.log_out), 0));

        return list;
    }

    private List<FourElementLinearListModel> accountList() {
        List<FourElementLinearListModel> list = new ArrayList<>();

        int id = userInformationList().get(0).getId();
        String username = userInformationList().get(0).getName();
        String email = userInformationList().get(0).getEmail();


        FourElementLinearListModel usernameModel = new FourElementLinearListModel(
                id, R.drawable.ic_person, getString(R.string.username), "", username);
        FourElementLinearListModel emailModel = new FourElementLinearListModel(
                id, R.drawable.ic_email, getString(R.string.e_mail), "", email);
        FourElementLinearListModel passwordModel = new FourElementLinearListModel(
                id, R.drawable.ic_lock, getString(R.string.password), "", "password");
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
        int value = dbHelper.getInformationUser(currentUserID).get(0).getLevel();
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
        int value = dbHelper.getInformationUser(currentUserID).get(0).getGender();
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
        return new ArrayList<>(0);
    }

    private void notificationSchedule(Notification notification) {
        Intent intent = new Intent(this, ReminderBroadcast.class);
        intent.putExtra(ReminderBroadcast.NOTIFICATION_ID, 1);
        intent.putExtra(ReminderBroadcast.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        if (reminderStatus) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    exerciseReminderInMilli,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent);
        }
    }

    private Notification getNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                defaultNotificationId)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(NOTIFICATION_CONTENT_TEXT)
                .setAutoCancel(true)
                .setChannelId(NOTIFICATION_CHANNEL_ID);
        return builder.build();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (selectHeightFragment != null) {
            updateHeight();
        } else if (selectWeightFragment != null) {
            updateWeight();
        }
    }

    private void updateHeight() {
        selectHeightFragment.fragmentMessage();
        Log.i(TAG, "onBackPressed: tabs : " + tab.get(0) + " " + tab.get(1));

        dbHelper.updateHeight(currentUserID, tab.get(0), tab.get(1));
        clearData(selectHeightFragment);
    }

    private void updateWeight() {
        selectWeightFragment.fragmentMessage();
        Log.i(TAG, "onBackPressed: tabs : " + tab.get(0) + " " + tab.get(1));

        dbHelper.updateWeight(currentUserID, tab.get(0), tab.get(1));
        clearData(selectWeightFragment);
    }

    private void clearData(Fragment fragment) {
        tab.clear();
        fragment = null;
    }

    @Override
    public void strValues(String listName, int position, int id, String firstVal) {
        if (listName.equals(GlobalClass.ACCOUNT_LIST_NAME)) {
            UserDatabaseColumns userDatabaseColumns = getRowNames(position);

            if (userDatabaseColumns != null) {
                dbHelper.updateUser(userDatabaseColumns, id, firstVal);
            } else {
                throw new IllegalStateException("Option does not exist");
            }
        }
    }

    private UserDatabaseColumns getRowNames(int position) {
        switch (position) {
            case 0:
                return UserDatabaseColumns.NAME;
            case 1:
                return UserDatabaseColumns.EMAIL;
            case 2:
                return UserDatabaseColumns.PASSWORD;
            default:
                return null;
        }
    }

    @Override
    public void values(String listName, int firstValue, int secondValue, int thirdValue,
                       int fourthValue) {
        switch (listName) {
            case INFORMATION_NAME:
                Log.i(TAG, "values: dimensionSettings" + listName + " f " + firstValue +
                        " s: " + secondValue + " t: " + thirdValue + " fo " + fourthValue);
                handleInformationFragment(firstValue);
                break;
            case PERFORMANCE_NAME:
                dbHelper.updateUserPerformance(currentUserID, firstValue, secondValue);
                break;
            case GOALS_NAME:
                dbHelper.updateUserGoals(currentUserID, firstValue, secondValue);
                break;
            case LEVEL_NAME:
                dbHelper.switchUserLevel(currentUserID, secondValue);
                break;
            case GENDER_NAME:
                dbHelper.switchUserGender(currentUserID, secondValue);
                break;
            case LANGUAGE_LIST_NAME:
                handleLanguageValue(firstValue, secondValue);
                break;
            default:
                throw new IllegalStateException("listName does not exist");
        }
    }

    private void handleInformationFragment(int firstValue) {
        if (firstValue == 0 || firstValue == 1) {
            Fragment fragment = (firstValue == 0) ? (selectHeightFragment = new SelectHeightFragment()) :
                    (selectWeightFragment = new SelectWeightFragment());
            replaceFragmentInMainContainer(fragment, fragment.getTag());
        }
    }

    private void replaceFragmentInMainContainer(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setReorderingAllowed(true);
        ft.addToBackStack(tag);
        ft.replace(R.id.act_settings_mainContainer, fragment, tag);
        ft.commit();
    }

    private void handleLanguageValue(int firstValue, int secondValue) {
        dbHelper.switchLanguage(firstValue, secondValue);
        restartMainActivity();
    }

    private void restartMainActivity() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        clearBackStack();
    }

    private void clearBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < count; i++) {
            fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(i).getName(),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void item(String list, int position) {

        if (Objects.equals(list, MAIN_LIST_NAME)) {
            switch (position) {
                case 0:
                    openProfileFragment();
                    break;
                case 1:
                    performFragmentOperation(new FourElementListFragment(), FragmentAction.REPLACE,
                            true, ACCOUNT_LIST_NAME, accountList(), getString(R.string.account),
                            ListType.STRING_INPUT_POPUP, NumberOfItem.TWO);
                    break;
                case 2:
                    performFragmentOperation(new NotificationFragment(), FragmentAction.REPLACE,
                            true, NOTIFICATION_LIST_NAME, notificationList(),
                            getString(R.string.notification), ListType.EXTERNAL_WINDOW_ACTION,
                            NumberOfItem.ONE);
                    break;
                case 3:
                    performFragmentOperation(new RadioButtonList(), FragmentAction.REPLACE,
                            true, LANGUAGE_LIST_NAME, languageListModel(),
                            getString(R.string.language), ListType.RADIO_BUTTONS_LIST, NumberOfItem.ONE);
                    break;
                case 4:
                    performFragmentOperation(new ContactFragment(), FragmentAction.REPLACE,
                            true, ContactFragment.class.getName(), new ArrayList<>(),
                            ContactFragment.class.getName(), ListType.NO_ACTION, NumberOfItem.NULL);
                    break;
                case 5:
                    showLogoutDialog();
                    break;
            }
        }
    }

    private void openProfileFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ProfileFragment profileFragment = new ProfileFragment();
        bundle = new Bundle();
        bundle.putParcelableArrayList(INFORMATION_NAME,
                (ArrayList<? extends Parcelable>) userUnit());
        bundle.putParcelableArrayList(GOALS_NAME,
                (ArrayList<? extends Parcelable>) userGoals());
        bundle.putParcelableArrayList(PERFORMANCE_NAME,
                (ArrayList<? extends Parcelable>) userPerformance());
        bundle.putParcelableArrayList(LEVEL_NAME,
                (ArrayList<? extends Parcelable>) userLevel());
        bundle.putParcelableArrayList(GENDER_NAME,
                (ArrayList<? extends Parcelable>) userGender());

        profileFragment.setArguments(bundle);
        ft.setReorderingAllowed(true);
        ft.addToBackStack(PROFILE_LIST_NAME);
        ft.replace(R.id.act_settings_mainContainer, profileFragment, PROFILE_LIST_NAME);
        ft.commit();
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.log_out);

        builder.setPositiveButton(R.string.yes, (dialogInterface, i) -> {
            dbHelper.accountStatus(currentUserID, false);
            currentUserID = -1;
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(GlobalClass.userID, currentUserID);
            startActivity(intent);
        });

        builder.setNegativeButton(R.string.no, ((dialogInterface, i) -> dialogInterface.dismiss()));

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void title(String value) {
        fragmentTitle.setText(value);
    }

    @Override
    public void reminder(boolean status, long time) {
        reminderStatus = status;
        exerciseReminderInMilli = time;
        notificationSchedule(getNotification());
    }

    @Override
    public void sendData(String listName, long id, UserDatabaseColumns userDatabaseColumns, String value) {
        if (userDatabaseColumns == UserDatabaseColumns.NAME || userDatabaseColumns == UserDatabaseColumns.EMAIL || userDatabaseColumns == UserDatabaseColumns.PASSWORD) {
            dbHelper.updateUser(userDatabaseColumns, id, value);
        } else {
            throw new IllegalStateException("Option does not exist");
        }
    }
}