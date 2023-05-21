package com.example.exerciseapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.exerciseapp.mModels.FourElementsModel;

import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private int tableLength;
    private List<FourElementsModel> firstList;
    private List<FourElementsModel> secondList;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, int tableLength,
                            List<FourElementsModel> firstList,
                            List<FourElementsModel> secondList) {
        super(fragmentActivity);
        this.tableLength = tableLength;
        this.firstList = firstList;
        this.secondList = secondList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new SearchList(firstList, "firstList");
            case 1:
                return new SearchList(secondList, "secondList");
            default:
                return new EmptyFragment();
        }
    }

    @Override
    public int getItemCount() {
        return tableLength;
    }
}
