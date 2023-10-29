package com.example.exerciseapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.exerciseapp.mClasses.GlobalClass;
import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mEnums.Side;
import com.example.exerciseapp.mInterfaces.ISummary;
import com.example.exerciseapp.mInterfaces.ITitleChangeListener;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.IntegerModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class UserActivity extends AppCompatActivity implements UpdateIntegersDB, ISummary,
        ITitleChangeListener {

    private final DateTimeFormatter LONG_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
    private final DateTimeFormatter SHORT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMdd");
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");

    private final int USER_ID = 1;
    private static long currentUserID;

    private LineChart lineChartWeight;
    private Button selectDate;
    private EditText userWeight;
    private Button updateWeightBtn;
    private DatePickerDialog datePickerDialog;
    private TextView activityTitle;
    private TextView fragmentTitle;

    private int selectedDateToDB;
    private LocalDate today;

    private ContentBD contentBD;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(GlobalClass.initLanguage(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        contentBD = new ContentBD(this);
        today = LocalDate.now();

        initView(savedInstanceState);
        initMenu();

        initDatePickerDialog(selectDate);
        fillDB();
        fillChart();
        updateWeightInDB(updateWeightBtn);
        changeDate();
    }

    private void initView(Bundle savedInstanceState) {
        initializeViews();
        setActivityTitle();

        Intent intent = getIntent();
        currentUserID = intent.getLongExtra(GlobalClass.userID, -1);

        if (findViewById(R.id.act_user_calendar) != null) {

            if (savedInstanceState != null) {
                return;
            }
            addCalenderFragment();
        }
        initializeLineChartWeight();
    }

    private void initializeViews() {
        selectDate = findViewById(R.id.act_user_edit_text_date);
        userWeight = findViewById(R.id.act_user_edit_text_user_weight);
        updateWeightBtn = findViewById(R.id.act_user_btn_update_weight);
        activityTitle = findViewById(R.id.act_user_title_part_one);
        fragmentTitle = findViewById(R.id.act_user_title_part_two);
        lineChartWeight = findViewById(R.id.act_line_chart_weight);
    }

    private void setActivityTitle() {
        String activityName = getString(R.string.profile);
        activityTitle.setText(activityName);
    }

    private void addCalenderFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setReorderingAllowed(true);
        ft.add(R.id.act_user_calendar, new CalendarFragment(), "calendarTag");
        ft.commit();
    }

    private void initializeLineChartWeight() {
        String current = today.format(LONG_DATE_TIME_FORMATTER);
        String showDate = today.format(DATE_TIME_FORMATTER);
        int currentDate = Integer.parseInt(current);
        selectedDateToDB = currentDate;
        selectDate.setText(String.valueOf(showDate));
    }

    private void initMenu() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.act_user_bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_bar_profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case (R.id.bottom_nav_bar_main):
                    startNewActivity(MainActivity.class, Side.LEFT);
                    return true;
                case (R.id.bottom_nav_bar_workout):
                    startNewActivity(LibraryActivity.class, Side.LEFT);
                    return true;
                case (R.id.bottom_nav_bar_profile):
                    return true;
                case (R.id.bottom_nav_bar_settings):
                    startNewActivity(SettingsActivity.class, Side.RIGHT);
                    return true;
            }
            return false;
        });
    }

    private void initDatePickerDialog(Button btn) {

        DatePickerDialog.OnDateSetListener onDateSetListener = (datePicker, year, month, day) -> {
            String formattedDate = formatSelectedDate(year, month, day);
            btn.setText(formattedDate);
            selectedDateToDB = convertDateToInt(year, month, day);
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this,
                android.R.style.Theme_Material_Light_Dialog,
                onDateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private String formatSelectedDate(int year, int month, int day) {
        LocalDate selectedDate = LocalDate.of(year, month, day).plusMonths(1);
        return selectedDate.format(DATE_TIME_FORMATTER);
    }

    private int convertDateToInt(int year, int month, int day) {
        month = month + 1;
        String yearStr = String.valueOf(year).substring(2, 4);
        String monthStr = formatSingleDigit(month);
        String dayStr = formatSingleDigit(day);

        String concatenateInts = yearStr + monthStr + dayStr;
        return Integer.parseInt(concatenateInts);
    }

    private String formatSingleDigit(int number) {
        return number < 9 ? "0" + number : String.valueOf(number);
    }

    private void startNewActivity(Class<?> activity, Side side) {
        Intent intent = new Intent(getApplicationContext(), activity);
        intent.putExtra(GlobalClass.userID, currentUserID);
        if (side == Side.LEFT) {
            overridePendingTransition(R.anim.slide_to_right, R.anim.slide_from_right);
        } else {
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
        }
        startActivity(intent);
        finish();
    }

    private void changeDate() {
        selectDate.setOnClickListener(v -> datePickerDialog.show());
    }

    private void updateWeightInDB(Button btn) {

        btn.setOnClickListener(v -> {
            String weightStr = userWeight.getText().toString().trim();
            if (isValidateDate(weightStr)) {
                showToast("Not update weight");
            } else {
                int weight = Integer.parseInt(weightStr);
                contentBD.updateUserBioWeight(USER_ID, selectedDateToDB, weight);
                showToast("Added to database \n day: " + selectedDateToDB + "\n weight: " + weight);
            }
        });
    }

    private boolean isValidateDate(String weightStr) {
        return !weightStr.isEmpty() || TextUtils.isDigitsOnly(weightStr);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

//    TO-DO Test values(block of code to change)
    private void fillDB() {
        if (contentBD.getCount("USER_BIO_TAB") <= 0) {
            int monthLength = today.lengthOfMonth();
            LocalDate firstDayOfMonth = today.withDayOfMonth(1);
            String firstDay = firstDayOfMonth.format(LONG_DATE_TIME_FORMATTER);
            int iteratorForDate = Integer.parseInt(firstDay);
            for (int i = 0; i < monthLength; i++) {
                IntegerModel model;
                if (i == 15) {
                    model = new IntegerModel(iteratorForDate, 0, 112, USER_ID);
                } else if (i == 22) {
                    model = new IntegerModel(iteratorForDate, 0, 95, USER_ID);
                } else {
                    model = new IntegerModel(iteratorForDate, 0, 0, USER_ID);
                }
                contentBD.insertUserBio(model);
                iteratorForDate++;
            }
        }
    }

    private void fillChart() {

        List<IntegerModel> contentDBUserBioWeight = contentBD.getUserBioWeight(USER_ID);
        List<Entry> weightValues = createWeightValues(contentDBUserBioWeight);
        List<String> dateValues = createDateList(contentDBUserBioWeight);

        setupLineCharWeight(contentDBUserBioWeight, weightValues, dateValues);
    }

    private List<Entry> createWeightValues(List<IntegerModel> contentDBUserBioWeight) {
        List<Entry> weightValues = new ArrayList<>();
        float temp = 0f;
        Entry entry;

        for (IntegerModel integerModel : contentDBUserBioWeight) {
            entry = new Entry(temp, integerModel.getSecondValue());
            weightValues.add(entry);
            temp++;
        }

        return weightValues;
    }

    private List<String> createDateList(List<IntegerModel> contentDBUserBioWeight) {
        List<Integer> dateFromDBDate = new ArrayList<>();
        for (IntegerModel i : contentDBUserBioWeight) {
            dateFromDBDate.add(i.getFirstValue());
        }

        return dateFromDBDate.stream()
                .map(i -> i % 10000)
                .map(i -> String.format(i.toString(), SHORT_DATE_TIME_FORMATTER))
                .map(s -> s.length() == 3 ? "0" + s.charAt(0) + "-" + s.substring(1, 3) :
                        s.length() == 4 ? s.substring(0, 2) + "-" + s.substring(2, 4) :
                                "")
                .collect(Collectors.toList());
    }

    private void setupLineCharWeight(List<IntegerModel> contentDBUserBioWeight,
                                     List<Entry> weightValues,
                                     List<String> dateValues) {
        LineDataSet lineDataSet = new LineDataSet(weightValues, "Weight");

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();

        iLineDataSets.add(lineDataSet);
        LineData lineData = new LineData(iLineDataSets);
        lineChartWeight.setData(lineData);
        lineChartWeight.invalidate();

        XAxis xAxis = lineChartWeight.getXAxis();
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(contentDBUserBioWeight.size());

        xAxis.setValueFormatter(new IndexAxisValueFormatter(dateValues));
    }

    @Override
    public void values(String listName, int firstValue, int secondValue, int thirdValue,
                       int fourthValue) {

        if (listName.equals("detailsFragment")) {
            handleDetailsFragment(firstValue, secondValue);
        } else if (listName.equals("calendarFragment")) {
            handleCalendarFragment(firstValue);
        }
    }

    private void handleDetailsFragment(int firstValue, int secondValue) {
        Intent intent = new Intent(UserActivity.this, ExerciseActivity.class);
        intent.putExtra("id", (long) firstValue);
        intent.putExtra("type", (byte) secondValue);
        startActivity(intent);
    }

    private void handleCalendarFragment(int firstValue) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setReorderingAllowed(true);
        if (firstValue <= 0) {
            ft.replace(R.id.act_user_calendar_details, new EmptyFragment(), "emptyTag");
        } else {
            DetailsFragment detailsFragment = new DetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("id", firstValue);
            bundle.putString("btnText", "Goo");
            detailsFragment.setArguments(bundle);
            ft.replace(R.id.act_user_calendar_details, detailsFragment, "exerciseTag");
        }
        ft.commit();
    }

    @Override
    public void summaryMessage(String name, String exerciseName, String duration, long exerciseId,
                               long extensionId, int fromWhere, boolean conditionVal) {

    }

    @Override
    public void title(String value) {
        fragmentTitle.setText(value);
    }
}