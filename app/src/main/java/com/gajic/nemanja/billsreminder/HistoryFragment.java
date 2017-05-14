package com.gajic.nemanja.billsreminder;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends ListFragment {

    // ArrayList of history bill fragment
    public static ArrayList<BillItem> historyList = new ArrayList<>();

    public static AdapterBills adapter;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Making an adapter
        adapter = new AdapterBills(getContext(), historyList);

        // Setting the adapter
        setListAdapter(adapter);

        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    public static void notifyChanges() {
        adapter.notifyDataSetChanged();
    }

}
