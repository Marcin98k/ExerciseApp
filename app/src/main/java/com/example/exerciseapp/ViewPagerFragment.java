package com.example.exerciseapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.exerciseapp.mModels.FourElementsModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class ViewPagerFragment extends Fragment {

    private String[] fragmentTitles;
    private List<FourElementsModel> firstList;
    private List<FourElementsModel> secondList;


    public ViewPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentTitles = getArguments().getStringArray("titles");
            firstList = getArguments().getParcelableArrayList("firstList");
            secondList = getArguments().getParcelableArrayList("secondList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_view_pager, container, false);
        initView(mView);
        return mView;
    }

    private void initView(View v) {
        TabLayout tabLayout = v.findViewById(R.id.act_library_tab_layout);
        ViewPager2 viewPager2 = v.findViewById(R.id.act_library_view_pager);
        ViewPagerAdapter viewPagerAdapter;
        if (firstList != null || secondList != null) {
            viewPagerAdapter = new ViewPagerAdapter(requireActivity(), fragmentTitles.length,
                    firstList, secondList);
        } else {
            viewPagerAdapter = new ViewPagerAdapter(requireActivity(), fragmentTitles.length);
        }
        viewPager2.setOffscreenPageLimit(1);
        viewPager2.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(fragmentTitles[position])).attach();
    }
}