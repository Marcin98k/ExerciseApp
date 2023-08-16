package com.example.exerciseapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.mClasses.ClockClass;
import com.example.exerciseapp.mInterfaces.FragmentRespond;
import com.example.exerciseapp.mInterfaces.ISummary;

public class SummaryFragment extends Fragment implements FragmentRespond {

    private TextView nameTV;
    private TextView timeTV;


    private long id;
    private double duration;
    private String name;
    private long extensionId;


    private ISummary iSummary;


    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            iSummary = (ISummary) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context +
                    " must implement ISummary");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id", -1);

            duration = getArguments().getDouble("duration", 0);
            name = getArguments().getString("name", "Null");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_summary, container, false);
        initView(mView);
        nameTV.setText(name);
        new ClockClass(requireActivity()).setSecond((int) duration).dynamicIncreaseTime(timeTV);
        return mView;
    }

    private void initView(View v) {
        nameTV = v.findViewById(R.id.frag_summary_name);
        timeTV = v.findViewById(R.id.frag_summary_time);
    }

    @Override
    public void fragmentMessage() {
        iSummary.summaryMessage("summaryFragment",
                String.valueOf(duration), (int) id, true);
    }
}