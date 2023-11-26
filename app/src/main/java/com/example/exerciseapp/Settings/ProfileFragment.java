package com.example.exerciseapp.Settings;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mAdapters.FourElementLinearListAdapter;
import com.example.exerciseapp.mAdapters.RadioButtonListAdapter;
import com.example.exerciseapp.mEnums.ListType;
import com.example.exerciseapp.mEnums.NumberOfItem;
import com.example.exerciseapp.mInterfaces.TitleChangeListener;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mModels.FourElementLinearListModel;
import com.example.exerciseapp.mModels.ThreeElementLinearListModel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ProfileFragment extends Fragment {

    private final String INFORMATION = "userInformation";
    private final String GOALS = "userGoals";
    private final String PERFORMANCE = "userPerformance";
    private final String LEVEL = "userLevel";
    private final String GENDER = "userGender";

    private ToggleButton genderButton, dimensionsButton, goalsButton, performanceButton, levelButton;
    private RecyclerView genderList, dimensionsList, goalsList, performanceList, levelList;

    private String fragmentName;
    private List<FourElementLinearListModel> userInformationList;
    private List<FourElementLinearListModel> userGoalsList;
    private List<FourElementLinearListModel> userPerformanceList;
    private List<ThreeElementLinearListModel> userLevelList;
    private List<ThreeElementLinearListModel> userGenderList;
    private FourElementLinearListAdapter adapter;
    private RadioButtonListAdapter radioAdapter;

    private UpdateIntegersDB sendToFragment, getFromFragment;
    private TitleChangeListener titleChangeListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            getFromFragment = (UpdateIntegersDB) context;
            titleChangeListener = (TitleChangeListener) context;
        } catch (RuntimeException e) {
            throw new ClassCastException(context +
                    " must implement ValueDB and/or TitleChangeListener");
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
            userInformationList = getParcelableListOrDefault(INFORMATION, () -> fillStandardList(INFORMATION));
            userGoalsList = getParcelableListOrDefault(GOALS, () -> fillStandardList(GOALS));
            userPerformanceList = getParcelableListOrDefault(PERFORMANCE, () -> fillStandardList(PERFORMANCE));
            userGenderList = getParcelableListOrDefault(GENDER, () -> fillRadioList(GENDER));
            userLevelList = getParcelableListOrDefault(LEVEL, () -> fillRadioList(LEVEL));
        }
    }

    private <T extends Parcelable> ArrayList<T> getParcelableListOrDefault(
            String key, Supplier<ArrayList<T>> defaultSupplier) {
        ArrayList<T> list = getArguments().getParcelableArrayList(key);
        if (list == null || list.isEmpty()) {
            return defaultSupplier.get();
        } else {
            return list;
        }
    }

    private ArrayList<FourElementLinearListModel> fillStandardList(String name) {
        ArrayList<FourElementLinearListModel> emptyList = new ArrayList<>();
        FourElementLinearListModel model = new FourElementLinearListModel(
                -1, 0, name, "0", "");
        emptyList.add(model);
        return emptyList;
    }

    private ArrayList<ThreeElementLinearListModel> fillRadioList(String name) {
        ArrayList<ThreeElementLinearListModel> emptyRadioList = new ArrayList<>();
        ThreeElementLinearListModel model = new ThreeElementLinearListModel(
                -1, 0, name, 0);
        emptyRadioList.add(model);
        return emptyRadioList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_profile, container, false);
        initializeView(mView);

        setupLists();
        setupButtons();
        prepareView();

        return mView;
    }

    private void setupLists() {

        initRadioButtonList(genderList, userGenderList, GENDER);
        initRecyclerView(dimensionsList, userInformationList, INFORMATION,
                ListType.EXTERNAL_WINDOW_ACTION, NumberOfItem.TWO);
        initRecyclerView(performanceList, userPerformanceList, PERFORMANCE,
                ListType.INTEGER_INPUT_POPUP, NumberOfItem.ONE);
        initRecyclerView(goalsList, userGoalsList, GOALS,
                ListType.CHECKBOX_LIST, NumberOfItem.ZERO);
        initRadioButtonList(levelList, userLevelList, LEVEL);
    }

    private void setupButtons() {

        expandOrCollapseList(genderButton, genderList);
        expandOrCollapseList(dimensionsButton, dimensionsList);
        expandOrCollapseList(performanceButton, performanceList);
        expandOrCollapseList(goalsButton, goalsList);
        expandOrCollapseList(levelButton, levelList);
    }

    private void prepareView() {

        prepareView(genderList);
        prepareView(dimensionsList);
        prepareView(performanceList);
        prepareView(goalsList);
        prepareView(levelList);
    }

    private void initializeView(View v) {

        fragmentName = getString(R.string.profile);

        dimensionsButton = v.findViewById(R.id.frag_profile_toggle_button_user_info);
        dimensionsList = v.findViewById(R.id.fProfile_userInformation);

        goalsButton = v.findViewById(R.id.frag_profile_toggle_button_user_goals);
        goalsList = v.findViewById(R.id.fProfile_userGoals);

        performanceButton = v.findViewById(R.id.frag_profile_toggle_button_user_performance);
        performanceList = v.findViewById(R.id.fProfile_userPerformance);

        levelButton = v.findViewById(R.id.frag_profile_toggle_button_user_level);
        levelList = v.findViewById(R.id.fProfile_userLevel);

        genderButton = v.findViewById(R.id.frag_profile_toggle_button_user_gender);
        genderList = v.findViewById(R.id.fProfile_userGender);
    }

    private void prepareView(RecyclerView view) {
        view.setVisibility(View.GONE);
    }

    private void expandOrCollapseList(ToggleButton toggleButton, RecyclerView listView) {

        toggleButton.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            updateListVisibility(listView, isChecked);
            updateToggleButtonIcon(toggleButton, isChecked);
        });
    }

    private void updateListVisibility(RecyclerView listView, boolean isListVisible) {
        int newVisibility = isListVisible ? View.VISIBLE : View.GONE;
        listView.setVisibility(newVisibility);
    }

    private void updateToggleButtonIcon(ToggleButton toggleButton, boolean isListVisibility) {
        int iconResource = isListVisibility ? R.drawable.ic_double_arrow_up :
                R.drawable.ic_double_arrow_down;
        toggleButton.setCompoundDrawablesRelativeWithIntrinsicBounds(iconResource, 0,
                iconResource, 0);
    }

    private void setupRecyclerView(RecyclerView view) {

        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false));
    }

    private void initRadioButtonList(RecyclerView view, List<ThreeElementLinearListModel> list, String tag) {

        setupRecyclerView(view);

        radioAdapter = new RadioButtonListAdapter(requireContext(), tag, list, sendToFragment);
        sendToFragment = (listName, firstValue, secondValue, thirdValue, fourthValue) -> view.post(() -> {
            radioAdapter.notifyDataSetChanged();
            getFromFragment.values(listName, firstValue, secondValue, thirdValue, fourthValue);
        });

        view.setAdapter(radioAdapter);
    }

    private void initRecyclerView(RecyclerView view, List<FourElementLinearListModel> list,
                                  String tag, ListType listType, NumberOfItem numberOfItem) {

        setupRecyclerView(view);

        adapter = new FourElementLinearListAdapter(requireContext(),
                list, tag, sendToFragment, listType, numberOfItem);
        sendToFragment = (listName, firstValue, secondValue, thirdValue, fourthValue) -> view.post(() -> {
            adapter.notifyDataSetChanged();
            getFromFragment.values(listName, firstValue, secondValue, thirdValue, fourthValue);
        });

        view.setAdapter(adapter);
    }

}