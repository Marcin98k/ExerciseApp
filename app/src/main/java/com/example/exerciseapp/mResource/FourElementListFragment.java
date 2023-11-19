package com.example.exerciseapp.mResource;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mAdapters.FourElementLinearListAdapter;


import com.example.exerciseapp.mEnums.ListType;
import com.example.exerciseapp.mEnums.NumberOfItem;
import com.example.exerciseapp.mInterfaces.ISendUserData;
import com.example.exerciseapp.mInterfaces.TitleChangeListener;

import com.example.exerciseapp.mInterfaces.UpdateStringsDB;
import com.example.exerciseapp.mModels.FourElementLinearListModel;

import java.util.ArrayList;
import java.util.List;

public class FourElementListFragment extends Fragment {


    private RecyclerView recyclerView;
    private FourElementLinearListAdapter adapter;


    private List<FourElementLinearListModel> list = new ArrayList<>();
    private String listName;
    private String fragmentName;
    private static final String TAG = "FourElementListFragment";


//    private UpdateIntegersDB updateIntegersDB;
//    private UpdateIntegersDB updateIntegersDB1;


    private UpdateStringsDB updateStrings;
    private UpdateStringsDB updateStrings1;
    private TitleChangeListener titleChangeListener;

    private ListType listType;
    private NumberOfItem numberOfItem;

    private ISendUserData iSendUserData;
    private ISendUserData iSendUserData1;


    public FourElementListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            updateStrings1 = (UpdateStringsDB) context;
        } catch (RuntimeException e) {
            throw new RuntimeException(context +
                    " must implement UpdateStringsDB");
        }

        try {
            titleChangeListener = (TitleChangeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context +
                    " must implement TitleChangeListener");
        }

        try {
            iSendUserData1 = (ISendUserData) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context +
                    " must implement ISendUserData");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (titleChangeListener != null) {
            titleChangeListener.title(fragmentName);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (titleChangeListener != null) {
            titleChangeListener.title("");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            listName = getArguments().getString("listName", "unknownListName");
            fragmentName = getArguments().getString("fragmentName", "NaN");

            list = getArguments().getParcelableArrayList("currentList");

            listName = getArguments().getString("listName");

            if (list == null) {
                list = new ArrayList<>();
            }

            listType = (ListType) getArguments().getSerializable("listType");
            if (listType == null) {
                listType = ListType.NO_ACTION;
            }

            numberOfItem = (NumberOfItem) getArguments().getSerializable("numberOfItem");
            if (numberOfItem == null) {
                numberOfItem = NumberOfItem.NULL;
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_four_element_list, container, false);
        initView(mView);

        updateStrings = (listName, position, id, firstVal) -> {
            adapter.notifyDataSetChanged();
            updateStrings1.strValues(listName, position, id, firstVal);
        };
//        updateIntegersDB = (listName, firstValue, secondValue, thirdValue) -> {
//            adapter.notifyDataSetChanged();
//            updateIntegersDB1.values(listName, firstValue, secondValue, thirdValue);
//        };

        iSendUserData = (listName, id, rowNames, value) ->
                iSendUserData1.sendData(listName, id, rowNames, value);

        if (listName.equals("tagTELL_account")) {
            Log.i(TAG, "onCreateView: ()four: if ACCOUNT_LIST_NAME");
            adapter = new FourElementLinearListAdapter(requireContext(), list, listName, iSendUserData,
                    listType, numberOfItem);
        } else {
            Log.i(TAG, "onCreateView: ()four: eles");
            adapter = new FourElementLinearListAdapter(requireContext(), list, listName, updateStrings,
                    listType, numberOfItem);
        }

        recyclerView.setAdapter(adapter);

        return mView;
    }

    private void initView(View v) {
        recyclerView = v.findViewById(R.id.fFourElementListFragment);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(),
                RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        updateStrings = (listName, position, id, firstVal) -> {
            adapter.notifyDataSetChanged();
            updateStrings1.strValues(listName, position, id, firstVal);
        };

//        updateIntegersDB = (listName, firstValue, secondValue, thirdValue) -> {
//            adapter.notifyDataSetChanged();
//            updateIntegersDB1.values(listName, firstValue, secondValue, thirdValue);
//        };
        adapter = new FourElementLinearListAdapter(requireContext(), list, listName,
                updateStrings, listType, numberOfItem);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(),
                RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
    }
}