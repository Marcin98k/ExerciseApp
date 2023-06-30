package com.example.exerciseapp;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.mDatabases.ContentBD;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.TaskDateModel;

import java.util.List;

public class CalendarFragment extends Fragment implements DatePicker.OnDateChangedListener {

    //    Initializing widgets;
    private DatePicker datePicker;

    //    Initializing variables;
    private long id;

    //    Initializing instances;
    private ContentBD contentBD;

    //    Initializing interface;
    private UpdateIntegersDB updateIntegersDB;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_calendar, container, false);
        datePicker = mView.findViewById(R.id.frag_date_picker);

        contentBD = new ContentBD(requireActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener(this);
        }
        return mView;
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        List<TaskDateModel> taskDateList = contentBD.showTaskWithDate(
                year + "" + (month + 1) + "" + day);
        if (taskDateList.size() > 0) {
            id = taskDateList.get(0).getTaskId();
        } else {
            id = -1;
        }
        updateIntegersDB.values("calendarFragment", (int) id, 0, 0);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            updateIntegersDB = (UpdateIntegersDB) context;
        } catch (NullPointerException e) {
            throw new NullPointerException(context.toString() +
                    " must implement UpdateIntegerDB");
        }
    }
}