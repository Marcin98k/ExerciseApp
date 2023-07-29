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
import com.example.exerciseapp.mEnums.ListType;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.ThreeElementLinearListModel;

import java.util.ArrayList;
import java.util.List;

public class RadioButtonList extends Fragment {

    private RecyclerView recyclerView;
    private RadioButtonListAdapter adapter;
    private String listName;

    private List<ThreeElementLinearListModel> list = new ArrayList<>();

    private UpdateIntegersDB updateIntegersDB;
    private UpdateIntegersDB updateIntegersDB1;

    public RadioButtonList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list = getArguments().getParcelableArrayList("currentList");
            listName = getArguments().getString("listName");
        } else {
            ThreeElementLinearListModel model = new ThreeElementLinearListModel(-1, 0, "Null", 0);
            list.add(model);
            listName = "null";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_radio_button_list, container, false);

        recyclerView = mView.findViewById(R.id.fRadioButtonList_recyclerView);

        updateIntegersDB = (listName, firstValue, secondValue, thirdValue) -> {
            adapter.notifyDataSetChanged();
            updateIntegersDB1.values(listName, firstValue, secondValue, thirdValue);
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        adapter = new RadioButtonListAdapter(requireActivity(), listName, list, updateIntegersDB);
        recyclerView.setAdapter(adapter);

        return mView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            updateIntegersDB1 = (UpdateIntegersDB) context;
        } catch (RuntimeException e) {
            throw new RuntimeException(context.toString() + " just implement");
        }
    }
}