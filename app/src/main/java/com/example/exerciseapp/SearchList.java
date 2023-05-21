package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.FourElementsModel;

import java.util.ArrayList;
import java.util.List;

public class SearchList extends Fragment {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private SearchAdapter searchAdapter;

    private List<FourElementsModel> list;
    private String listName;

    private UpdateIntegersDB updateIntegersDB;
    private UpdateIntegersDB updateIntegersDB1;


    public SearchList() {
        // Required empty public constructor
    }

    public SearchList(List<FourElementsModel> list, String listName) {
        this.list = list;
        this.listName = listName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_search_list, container, false);
        updateIntegersDB = (listName, firstValue, secondValue, thirdValue) ->
                updateIntegersDB1.values(listName, firstValue, secondValue, thirdValue);
        initView(mView);
        return mView;
    }

    private void initView(View v) {
        recyclerView = v.findViewById(R.id.frag_search_list_recycle_view);
        searchView = v.findViewById(R.id.frag_search_list_search_bar);
        searchAdapter = new SearchAdapter(requireContext(), list, listName, updateIntegersDB);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(),
                RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(searchAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.getFilter().filter(newText);
                return false;
            }
        });
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            updateIntegersDB1 = (UpdateIntegersDB) context;
        } catch (RuntimeException e) {
            throw new RuntimeException(context.toString() +
                    " must implement UpdateIntegersDB");
        }
    }
}