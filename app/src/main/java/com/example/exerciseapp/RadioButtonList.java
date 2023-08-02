package com.example.exerciseapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciseapp.mAdapters.RadioButtonListAdapter;

import com.example.exerciseapp.mInterfaces.ITitleChangeListener;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.ThreeElementLinearListModel;

import java.util.ArrayList;
import java.util.List;

public class RadioButtonList extends Fragment {

    private RecyclerView recyclerView;
    private RadioButtonListAdapter adapter;


    private String listName;
    private String fragmentName;
    private List<ThreeElementLinearListModel> list;


    private UpdateIntegersDB updateIntegersDB;
    private UpdateIntegersDB updateIntegersDB1;
    private ITitleChangeListener iTitleChangeListener;

    public RadioButtonList() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            updateIntegersDB1 = (UpdateIntegersDB) context;
            iTitleChangeListener = (ITitleChangeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context +
                    " must implement UpdateIntegersDB and/or ITitleChangeListener");
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
            list = getArguments().getParcelableArrayList("currentList");
            listName = getArguments().getString("listName", "UnknownListName");
            fragmentName = getArguments().getString("fragmentName", "NaN");
        }
        if (list == null) {
            list = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_radio_button_list, container, false);
        initView(mView);
        return mView;
    }

    private void initView(View v) {

        recyclerView = v.findViewById(R.id.fRadioButtonList_recyclerView);

        updateIntegersDB = (listName, firstValue, secondValue, thirdValue) -> {
            adapter.notifyDataSetChanged();
            updateIntegersDB1.values(listName, firstValue, secondValue, thirdValue);
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        adapter = new RadioButtonListAdapter(requireActivity(), listName, list, updateIntegersDB);
        recyclerView.setAdapter(adapter);
    }
}