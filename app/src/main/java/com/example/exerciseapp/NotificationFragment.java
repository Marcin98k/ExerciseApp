package com.example.exerciseapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.mInterfaces.INotificationCallback;
import com.example.exerciseapp.mInterfaces.ITitleChangeListener;
import com.example.exerciseapp.mModels.IntegerModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class NotificationFragment extends Fragment {

    private ToggleButton exerciseBtn;
    private TimePicker exerciseTimePicker;
    private Button confirmBtn;

    private String fragmentName;


    private ITitleChangeListener iTitleChangeListener;
    private INotificationCallback iNotificationCallback;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            iTitleChangeListener = (ITitleChangeListener) context;
            iNotificationCallback = (INotificationCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context +
                    " must implement ITitleChangeListener and/or INotificationCallback");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (iTitleChangeListener != null) {
            iTitleChangeListener.title(fragmentName);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (iTitleChangeListener != null) {
            iTitleChangeListener.title("");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_notification, container, false);
        initView(mView);

        exerciseBtn.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                exerciseTimePicker.setVisibility(View.VISIBLE);
            } else {
                exerciseTimePicker.setVisibility(View.GONE);
            }
        });
        confirmBtn.setOnClickListener(v -> {
            int hour = exerciseTimePicker.getHour();
            int minute = exerciseTimePicker.getMinute();

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);

            long millis = calendar.getTimeInMillis();
            iNotificationCallback.reminder(true, millis);
            exerciseBtn.setChecked(false);
            exerciseTimePicker.setVisibility(View.GONE);
        });

        return mView;
    }

    private void initView(View v) {
        exerciseBtn = v.findViewById(R.id.frag_notification_exercise_btn);
        exerciseTimePicker = v.findViewById(R.id.frag_notification_exercise_time_picker);
        confirmBtn = v.findViewById(R.id.frag_notification_confirm_btn);
        fragmentName = "/" + getString(R.string.notification);

        exerciseTimePicker.setVisibility(View.GONE);
    }
}