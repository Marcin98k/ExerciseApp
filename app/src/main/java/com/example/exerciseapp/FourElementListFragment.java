package com.example.exerciseapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.exerciseapp.Adapters.FourElementLinearListAdapter;
import com.example.exerciseapp.Interfaces.UpdateStringsDB;
import com.example.exerciseapp.Interfaces.UpdateValueDB;
import com.example.exerciseapp.Models.FourElementLinearListModel;

import java.util.ArrayList;
import java.util.List;

public class FourElementListFragment extends Fragment {


    private RecyclerView recyclerView;
    private FourElementLinearListAdapter adapter;

    private List<FourElementLinearListModel> list = new ArrayList<>();
    private String listName;


    UpdateValueDB updateValueDB;
    UpdateValueDB updateValueDB1;

    UpdateStringsDB updateStrings;
    UpdateStringsDB updateStrings1;


    public FourElementListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            list = getArguments().getParcelableArrayList("currentList");
            listName = getArguments().getString("listName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_four_element_list, container, false);
        initView(mView);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        updateStrings = new UpdateStringsDB() {
            @Override
            public void strValues(String listName, int position, int id, String firstVal) {
                updateStrings1.strValues(listName, position, id, firstVal);
            }
        };
        updateValueDB = new UpdateValueDB() {
            @Override
            public void values(String listName, int firstValue, int secondValue, int thirdValue) {
                adapter.notifyDataSetChanged();
                updateValueDB1.values(listName, firstValue, secondValue, thirdValue);
            }
        };
        adapter = new FourElementLinearListAdapter(requireContext(), list, listName, updateStrings);

        recyclerView.setAdapter(adapter);

        return mView;
    }

    private void initView(View v) {
        recyclerView = v.findViewById(R.id.fFourElementListFragment);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            updateValueDB1 = (UpdateValueDB) context;
        } catch (RuntimeException e) {
            throw new RuntimeException(context.toString() +
                    " must implement UpdateValueDB");
        }

        try {
            updateStrings1 = (UpdateStringsDB) context;
        } catch (RuntimeException e) {
            throw new RuntimeException(context.toString() +
                    " must implement UpdateStringDB");
        }
    }
}