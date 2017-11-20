package com.gajic.nemanja.billsreminder;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gajic.nemanja.billsreminder.data.BillContract;
import com.gajic.nemanja.billsreminder.data.NoteContract;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillsFragment extends ListFragment {

    // ArrayList of bill items
    public static ArrayList<BillItem> billItems = new ArrayList<>();

    public static AdapterBills adapter;

    public BillsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fill notes from database
        Cursor cursor = getContext().getContentResolver().query(BillContract.BillEntry.CONTENT_URI, null, null, null, null);
        int titleIndex = cursor.getColumnIndex(BillContract.BillEntry.COLUMN_BILLS_TITLE);
        int dateIndex = cursor.getColumnIndex(BillContract.BillEntry.COLUMN_BILLS_DATE);
        int amountIndex = cursor.getColumnIndex(BillContract.BillEntry.COLUMN_BILLS_AMOUNT);
        int titleColor = cursor.getColumnIndex(BillContract.BillEntry.COLUMN_BILLS_TITLE_COLOR);
        while (cursor.moveToNext()) {
            BillItem currentBill = new BillItem(cursor.getString(titleIndex), cursor.getString(dateIndex), cursor.getString(amountIndex));
            currentBill.setItemsTitleColor(cursor.getInt(titleColor));

            // Add items to bill or history fragments
            if (currentBill.getItemsTitleColor() == R.color.colorTitlePaid) {
                currentBill.setButtonsLeftPadding(1);
                HistoryFragment.historyList.add(currentBill);
            } else {
                billItems.add(currentBill);
            }
        }
        cursor.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Making an adapter
        adapter = new AdapterBills(getContext(), billItems);

        // Setting the adapter
        setListAdapter(adapter);

        return inflater.inflate(R.layout.fragment_bills, container, false);
    }

    public static void notifyChanges() {
        adapter.notifyDataSetChanged();
    }

}
