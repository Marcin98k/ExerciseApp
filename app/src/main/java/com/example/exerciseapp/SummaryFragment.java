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

    private long id;
    private double duration;
    private String name;
    private long extensionId;
    private int fromWhere;


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
            extensionId = getArguments().getLong("extensionId", -1);
            duration = getArguments().getDouble("duration", 0);
            name = getArguments().getString("name", "Null");
            fromWhere = getArguments().getInt("fromWhere", -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_summary, container, false);
        initView(mView);
        return mView;
    }

    private void initView(View v) {
        TextView nameTV = v.findViewById(R.id.frag_summary_name);
        TextView timeTV = v.findViewById(R.id.frag_summary_time);

        nameTV.setText(name);

        new ClockClass(requireActivity()).setSecond((int) duration).dynamicIncreaseTime(timeTV);
    }

    @Override
    public void fragmentMessage() {
        if (name.equals("Null") || fromWhere < 0 || id < 0 || extensionId < 0 || duration < 0) {
            throw new IllegalStateException(getContext() + " -> Incorrect data");
        }
        iSummary.summaryMessage("summaryFragment", name, String.valueOf(duration), id,
                extensionId, fromWhere, true);
    }
}