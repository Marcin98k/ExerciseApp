package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciseapp.mAdapters.FourElementLinearListAdapter;
import com.example.exerciseapp.mAdapters.ThreeElementLinearListAdapter;


import com.example.exerciseapp.mEnums.ListType;
import com.example.exerciseapp.mEnums.NumberOfItem;

import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.FourElementLinearListModel;
import com.example.exerciseapp.mModels.ThreeElementLinearListModel;

import java.util.ArrayList;
import java.util.List;

public class LinearListFragment extends Fragment {

    private ListView listView;
    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private FourElementLinearListAdapter adapter1;

    private List<ThreeElementLinearListModel> ThreeElementList = new ArrayList<>();
    private List<FourElementLinearListModel> FourElementList = new ArrayList<>();
    private List<FourElementLinearListModel> accountList = new ArrayList<>();

    private int currentlyPosition;


    private UpdateIntegersDB updateIntegersDB;
    private ListType listType;
    private NumberOfItem numberOfItem;


    public LinearListFragment() {
        // Required empty public constructor
    }

    interface SelectedItem {
        void item(String list, int position, int currentlyPosition);
    }

    private SelectedItem selectedItem;

    private String listName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listName = getArguments().getString("listName");
            if (listName.equals("tagTELL_main")) {
                ThreeElementList = getArguments().getParcelableArrayList("currentList");
            } else if (listName.equals("tagTELL_units")) {
                FourElementList = getArguments().getParcelableArrayList("currentList");
            } else if (listName.equals("tagTELL_account")) {
                accountList = getArguments().getParcelableArrayList("currentList");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_linear_list, container, false);

        listView = mView.findViewById(R.id.fThreeElementLinearList);

        if (listName.equals("tagTELL_main")) {
        adapter = new ThreeElementLinearListAdapter(requireContext(),
                R.layout.three_element_linear_block, ThreeElementList);
            for (int i = 0; i < ThreeElementList.size(); i++) {
                if (ThreeElementList.get(i).getAction() == R.drawable.ic_check_circle) {
                    currentlyPosition = ThreeElementList.get(i).getId();
                    Log.e("LinearListFragment", " " + currentlyPosition);
                } else {
                    currentlyPosition = -1;
                }
            }
            listView.setAdapter(adapter);
        }
        if (listName.equals("tagTELL_account")) {
            adapter1 = new FourElementLinearListAdapter(requireContext(), accountList,
                    "tagTELL_account", updateIntegersDB, listType, numberOfItem);
            Log.i(TAG, "onCreateView: - > tagTELL_account");
            recyclerView.setAdapter(adapter1);
        }

        listView.setOnItemClickListener((adapterView, view, i, l) -> selectedItem.item(listName, i, currentlyPosition));
        return mView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            selectedItem = (SelectedItem) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implements Selected Item");
        }
    }
}