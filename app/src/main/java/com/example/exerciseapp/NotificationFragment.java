package com.example.exerciseapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.mInterfaces.ITitleChangeListener;
import com.example.exerciseapp.mModels.IntegerModel;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment {

    private TextView textView;

    private List<IntegerModel> list = new ArrayList<>();
    private String listName;

    private String fragmentName;


    private ITitleChangeListener iTitleChangeListener;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            iTitleChangeListener = (ITitleChangeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context +
                    " must implement ITitleChangeListener");
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
        if (getArguments() != null) {
            listName = getArguments().getString("listName", "unknownListName");

            list = getArguments().getParcelableArrayList("currentList");
            if (list == null) {
                list = new ArrayList<>();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_notification, container, false);
        initView(mView);
        return mView;
    }

    private void initView(View v) {
        textView = v.findViewById(R.id.fNotification_tv);
        fragmentName = getString(R.string.notification);
    }
}