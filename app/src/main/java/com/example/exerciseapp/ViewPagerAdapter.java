package com.example.exerciseapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.exerciseapp.mModels.FourElementsModel;

import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private final int tableLength;

    private final ViewPagerType pagerType;
    private List<FourElementsModel> firstList;
    private List<FourElementsModel> secondList;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, int tableLength,
                            List<FourElementsModel> firstList,
                            List<FourElementsModel> secondList) {
        super(fragmentActivity);
        this.tableLength = tableLength;
        this.firstList = firstList;
        this.secondList = secondList;
        pagerType = ViewPagerType.List;
    }

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, int tableLength) {
        super(fragmentActivity);
        this.tableLength = tableLength;
        pagerType = ViewPagerType.Counter;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (pagerType == ViewPagerType.List) {
            switch (position) {
                case 0:
                    return new SearchList("firstList", firstList);
                case 1:
                    return new SearchList("secondList", secondList);
                default:
                    return new EmptyFragment();
            }
        } else if (pagerType == ViewPagerType.Counter) {
            switch (position) {
                case 0:
                    return new CustomExerciseCounterFragment(ExerciseType.REPETITION);
                case 1:
                    return new CustomExerciseCounterFragment(ExerciseType.TIME);
                default:
                    return new EmptyFragment();
            }
        } else {
            return new EmptyFragment();
        }
    }

    @Override
    public int getItemCount() {
        return tableLength;
    }
}
