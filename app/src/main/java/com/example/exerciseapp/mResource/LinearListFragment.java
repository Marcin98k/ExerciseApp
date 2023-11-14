package com.example.exerciseapp.mResource;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mAdapters.ThreeElementLinearListAdapter;
import com.example.exerciseapp.mModels.ThreeElementLinearListModel;

import java.util.ArrayList;
import java.util.List;

public class LinearListFragment extends Fragment {

    private ListView listView;
    private ListAdapter adapter;

    private List<ThreeElementLinearListModel> ThreeElementList = new ArrayList<>();

    public LinearListFragment() {
        // Required empty public constructor
    }

    public interface SelectedItem {
        void item(String list, int position);
    }

    private SelectedItem selectedItem;

    private String listName;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            selectedItem = (SelectedItem) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context
                    + " must implements SelectedItem");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listName = getArguments().getString("listName");
            ThreeElementList = getArguments().getParcelableArrayList("currentList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_linear_list, container, false);

        listView = mView.findViewById(R.id.fThreeElementLinearList);

        adapter = new ThreeElementLinearListAdapter(requireContext(),
                R.layout.three_element_linear_block, ThreeElementList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> selectedItem.item(listName, i));
        return mView;
    }
}