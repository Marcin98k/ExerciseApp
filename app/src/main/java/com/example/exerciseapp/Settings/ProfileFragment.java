package com.example.exerciseapp.Settings;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

    private Button genderButton;
    private RecyclerView genderList;

    private Button informationButton;
    private RecyclerView informationList;

    private Button goalsButton;
    private RecyclerView goalsList;

    private Button performanceButton;
    private RecyclerView performanceList;

    private Button levelButton;
    private RecyclerView levelList;


    private String fragmentName;
    private List<FourElementLinearListModel> userInformationList;
    private List<FourElementLinearListModel> userGoalsList;
    private List<FourElementLinearListModel> userPerformanceList;
    private List<ThreeElementLinearListModel> userLevelList;
    private List<ThreeElementLinearListModel> userGenderList;
    private List<FourElementLinearListModel> emptyList;
    private List<ThreeElementLinearListModel> emptyRadioList;
    private FourElementLinearListAdapter adapter;
    private RadioButtonListAdapter radioAdapter;

    private UpdateIntegersDB valueDB, valueDB1;
    private TitleChangeListener titleChangeListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            valueDB1 = (UpdateIntegersDB) context;
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
        emptyList = new ArrayList<>();
        FourElementLinearListModel model = new FourElementLinearListModel(
                -1, 0, name, "0", "");
        emptyList.add(model);
        return (ArrayList<FourElementLinearListModel>) emptyList;
    }

    private ArrayList<ThreeElementLinearListModel> fillRadioList(String name) {
        emptyRadioList = new ArrayList<>();
        ThreeElementLinearListModel model = new ThreeElementLinearListModel(
                -1, 0, name, 0);
        emptyRadioList.add(model);
        return (ArrayList<ThreeElementLinearListModel>) emptyRadioList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_profile, container, false);
        initializeView(mView);

        initRadioButtonList(genderList, userGenderList, GENDER);
        initRecyclerView(informationList, userInformationList, INFORMATION,
                ListType.SELECTABLE_BUTTONS, NumberOfItem.TWO);
        initRecyclerView(performanceList, userPerformanceList, PERFORMANCE,
                ListType.SELECTABLE_BUTTONS_INT, NumberOfItem.ONE);
        initRecyclerView(goalsList, userGoalsList, GOALS,
                ListType.MULTIPLE_CHOICE_BUTTONS, NumberOfItem.NULL);
        initRadioButtonList(levelList, userLevelList, LEVEL);

        expandOrCollapseList(genderButton, genderList);
        expandOrCollapseList(informationButton, informationList);
        expandOrCollapseList(performanceButton, performanceList);
        expandOrCollapseList(goalsButton, goalsList);
        expandOrCollapseList(levelButton, levelList);

        prepareView(genderList);
        prepareView(informationList);
        prepareView(performanceList);
        prepareView(goalsList);
        prepareView(levelList);

        return mView;
    }

    private void initializeView(View v) {

        fragmentName = getString(R.string.profile);

        informationButton = v.findViewById(R.id.frag_profile_toggle_button_user_info);
        informationList = v.findViewById(R.id.fProfile_userInformation);

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

    private void expandOrCollapseList(Button btn, RecyclerView view) {

        btn.setOnClickListener(v -> {
            if (view.getVisibility() == View.GONE) {
                view.setVisibility(View.VISIBLE);
                btn.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_double_arrow_up, 0,
                        R.drawable.ic_double_arrow_up, 0);
            } else {
                view.setVisibility(View.GONE);
                btn.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_double_arrow_down, 0,
                        R.drawable.ic_double_arrow_down, 0);
            }
        });
    }

    private void initRadioButtonList(RecyclerView view, List<ThreeElementLinearListModel> list, String tag) {

        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false));

        radioAdapter = new RadioButtonListAdapter(requireContext(), tag, list, valueDB);

        valueDB = (listName, firstValue, secondValue, thirdValue, fourthValue) -> view.post(() -> {
            radioAdapter.notifyDataSetChanged();
            valueDB1.values(listName, firstValue, secondValue, thirdValue, fourthValue);
        });

        radioAdapter = new RadioButtonListAdapter(requireContext(), tag, list, valueDB);
        view.setAdapter(radioAdapter);
    }

    private void initRecyclerView(RecyclerView view, List<FourElementLinearListModel> list,
                                  String tag, ListType listType, NumberOfItem numberOfItem) {

        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false));

        valueDB = (listName, firstValue, secondValue, thirdValue, fourthValue) -> view.post(() -> {
            adapter.notifyDataSetChanged();
            valueDB1.values(listName, firstValue, secondValue, thirdValue, fourthValue);
        });

        adapter = new FourElementLinearListAdapter(requireContext(),
                list, tag, valueDB, listType, numberOfItem);
        view.setAdapter(adapter);
    }
}