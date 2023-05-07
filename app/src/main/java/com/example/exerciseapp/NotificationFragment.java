package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.exerciseapp.Models.IntegerModel;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment {

    private TextView textView;

    private List<IntegerModel> list = new ArrayList<>();
    private String listName;


    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list = getArguments().getParcelableArrayList("currentList");
            listName = getArguments().getString("listName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_notification, container, false);
        initView(mView);
        Log.i(TAG, "onCreateView(Notification): " + list.toString());
        return mView;
    }

    private void initView(View v) {
        textView = v.findViewById(R.id.fNotification_tv);
    }
}