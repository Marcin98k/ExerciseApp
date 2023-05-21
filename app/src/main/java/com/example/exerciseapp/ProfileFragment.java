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

import com.example.exerciseapp.mAdapters.FourElementLinearListAdapter;
import com.example.exerciseapp.mAdapters.RadioButtonListAdapter;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.FourElementLinearListModel;
import com.example.exerciseapp.mModels.ThreeElementLinearListModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private RecyclerView informationList;
    private RecyclerView goalsList;
    private RecyclerView performanceList;
    private RecyclerView levelList;
    private RecyclerView genderList;


    private List<FourElementLinearListModel> userInformationList = new ArrayList<>();
    private List<FourElementLinearListModel> userGoalsList = new ArrayList<>();
    private List<FourElementLinearListModel> userPerformanceList = new ArrayList<>();
    private List<ThreeElementLinearListModel> userLevelList = new ArrayList<>();
    private List<ThreeElementLinearListModel> userGenderList = new ArrayList<>();
    private final List<FourElementLinearListModel> emptyList = new ArrayList<>();
    private final List<ThreeElementLinearListModel> emptyRadioList = new ArrayList<>();
    FourElementLinearListAdapter adapter;
    RadioButtonListAdapter radioAdapter;


    private final String INFORMATION = "userInformation";
    private final String GOALS = "userGoals";
    private final String PERFORMANCE = "userPerformance";
    private final String LEVEL = "userLevel";
    private final String GENDER = "userGender";

    UpdateIntegersDB valueDB;
    UpdateIntegersDB valueDB1;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            if (getArguments().getParcelableArrayList("userInformation").isEmpty() ||
                    getArguments().getParcelableArrayList("userInformation") == null) {
                userInformationList = fillList("userInformation");
            } else {
                userInformationList = getArguments().getParcelableArrayList("userInformation");
            }
            if (getArguments().getParcelableArrayList("userGoals") == null ||
                    getArguments().getParcelableArrayList("userGoals").isEmpty()) {
                userGoalsList = fillList("userGoals");
            } else {
                userGoalsList = getArguments().getParcelableArrayList("userGoals");
            }
            if (getArguments().getParcelableArrayList("userPerformance").isEmpty() ||
                    getArguments().getParcelableArrayList("userPerformance") == null) {
                userPerformanceList = fillList("userPerformance");
            } else {
                userPerformanceList = getArguments().getParcelableArrayList("userPerformance");
            }
            if (getArguments().getParcelableArrayList("userLevel").isEmpty() ||
                    getArguments().getParcelableArrayList("userLevel") == null) {
                userLevelList = fillRadioList("userLevel");
            } else {
            userLevelList = getArguments().getParcelableArrayList("userLevel");
            }
            if (getArguments().getParcelableArrayList("userGender").isEmpty() ||
                    getArguments().getParcelableArrayList("userGender") == null) {
                userGenderList = fillRadioList("userGender");
            } else {
            userGenderList = getArguments().getParcelableArrayList("userGender");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_profile, container, false);
        initView(mView);

        initRecyclerView(informationList, userInformationList, INFORMATION);
        initRadioButtonList(genderList, userGenderList, GENDER);
        initRadioButtonList(levelList, userLevelList, LEVEL);
        initRecyclerView(performanceList, userPerformanceList, PERFORMANCE);
        initRecyclerView(goalsList, userGoalsList, GOALS);

        return mView;
    }

    private List<FourElementLinearListModel> fillList(String name) {
        FourElementLinearListModel model = new FourElementLinearListModel(-1, 0, name, "0", "");
        emptyList.add(model);
        return emptyList;
    }
    private List<ThreeElementLinearListModel> fillRadioList(String name) {
        ThreeElementLinearListModel model = new ThreeElementLinearListModel(
                -1, 0, "name", 0);
        emptyRadioList.add(model);
        return emptyRadioList;
    }

    private void initRadioButtonList(RecyclerView view, List<ThreeElementLinearListModel> list, String tag) {

        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        radioAdapter = new RadioButtonListAdapter(requireContext()
                , tag, list, valueDB);

        valueDB = (listName, firstValue, secondValue, thirdValue) -> view.post(() -> {
            radioAdapter.notifyDataSetChanged();
            valueDB1.values(listName, firstValue, secondValue, thirdValue);
        });

        view.setAdapter(radioAdapter);
    }

    private void initView(View mView) {

        informationList = mView.findViewById(R.id.fProfile_userInformation);
        goalsList = mView.findViewById(R.id.fProfile_userGoals);
        performanceList = mView.findViewById(R.id.fProfile_userPerformance);
        levelList = mView.findViewById(R.id.fProfile_userLevel);
        genderList = mView.findViewById(R.id.fProfile_userGender);
    }

    private void initRecyclerView(RecyclerView view, List<FourElementLinearListModel> list, String tag) {

        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        valueDB = (listName, firstValue, secondValue, thirdValue) -> view.post(() -> {
            adapter.notifyDataSetChanged();
            valueDB1.values(listName, firstValue, secondValue, thirdValue);
        });
        adapter = new FourElementLinearListAdapter(requireContext(),
                list, tag, valueDB);
        view.setAdapter(adapter);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            valueDB1 = (UpdateIntegersDB) context;
        } catch (RuntimeException e) {
            throw new RuntimeException(context.toString() +
                    " must implement ValueDB");
        }
    }
}