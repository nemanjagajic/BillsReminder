package com.gajic.nemanja.billsreminder;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.gajic.nemanja.billsreminder.data.BillContract;
import com.gajic.nemanja.billsreminder.data.NoteContract;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting view pager
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);

        // Setting tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    // Sets up view pager adding all our fragments
    private void setupViewPager(ViewPager viewPager) {
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BillsFragment(), "Bills");
        adapter.addFragment(new HistoryFragment(), "History");
        adapter.addFragment(new NotesFragment(), "Notes");
        viewPager.setAdapter(adapter);
    }

    // Called when fab from fragment_bills is clicked
    public void fabBillClicked(View view) {
        Intent intent = new Intent(this, AddBill.class);
        startActivity(intent);
    }

    // Called when fab from fragment_notes is clicked
    public void fabNotesClicked(View view) {
        Intent intent = new Intent(this, AddNote.class);
        startActivity(intent);
    }

    // Called when delete fab from fragment_notes is clicked
    public void fabDeleteNotesClicked(View view) {
        NotesFragment.notes.removeAll(NotesFragment.notes);
        NotesFragment.notifyChanges();
        Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();

        // Delete from database
        getContentResolver().delete(NoteContract.NoteEntry.CONTENT_URI, null, null);
    }

    // Called when delete button of item is clicked
    public void deleteItemClicked(View view) {
        int position = Integer.parseInt(view.getContentDescription().toString());
        int paddingLeft = view.getPaddingLeft(); // if 0 it's in BillsFragment, 1 HistoryFragment
        String billTitle;

        if (paddingLeft == 0) {
            billTitle = BillsFragment.billItems.get(position).getTitle();
            BillsFragment.billItems.remove(position);
            BillsFragment.notifyChanges();
        } else {
            billTitle = HistoryFragment.historyList.get(position).getTitle();
            HistoryFragment.historyList.remove(position);
            HistoryFragment.notifyChanges();
        }

        // Remove from database
        Uri uriToDelete = Uri.withAppendedPath(BillContract.BillEntry.CONTENT_URI, billTitle);
        getContentResolver().delete(uriToDelete, null, null);
    }

    // Called when delete button of note item is clicked
    public void deleteNoteClicked(View view) {
        int position = Integer.parseInt(view.getContentDescription().toString());
        // Remove from list
        NoteItem removedNote = NotesFragment.notes.remove(position);
        NotesFragment.notifyChanges();

        // Remove from db
        // We find item by comparing column values,
        // even though it's not a good practice, with this logic it works fine
        Uri uri = Uri.withAppendedPath(NoteContract.NoteEntry.CONTENT_URI, removedNote.getText());
        getContentResolver().delete(uri, null, null);
    }

    // Called when payed button of item is clicked
    public void moveItemToHistory(View view) {
        int position = Integer.parseInt(view.getContentDescription().toString());
        int paddingLeft = view.getPaddingLeft(); // if 0 it's in BillsFragment, 1 HistoryFragment
        String billTitle;

        if (paddingLeft == 0) {
            // Adding item to HistoryFragment
            BillItem item = BillsFragment.billItems.get(position);
            billTitle = item.getTitle();
            item.setButtonsLeftPadding(1);
            HistoryFragment.historyList.add(item);

            // Update database title color
            ContentValues values = new ContentValues();
            values.put(BillContract.BillEntry.COLUMN_BILLS_TITLE, item.getTitle());
            values.put(BillContract.BillEntry.COLUMN_BILLS_DATE, item.getDate());
            values.put(BillContract.BillEntry.COLUMN_BILLS_AMOUNT, item.getAmount());
            values.put(BillContract.BillEntry.COLUMN_BILLS_TITLE_COLOR, R.color.colorTitlePaid);
            Uri uri = Uri.withAppendedPath(BillContract.BillEntry.CONTENT_URI, item.getTitle());
            getContentResolver().update(uri, values, null, null);

            HistoryFragment.notifyChanges();

            // Setting title color
            item.setItemsTitleColor(R.color.colorTitlePaid);
            // Deleting item from BillsFragment
            BillsFragment.billItems.remove(position);
            BillsFragment.notifyChanges();

            Toast.makeText(this, "Bill \"" + billTitle + "\" moved to history", Toast.LENGTH_SHORT).show();
        } else {
            billTitle = HistoryFragment.historyList.get(position).getTitle();
            Toast.makeText(this, "Bill \"" + billTitle + "\" is already paid", Toast.LENGTH_SHORT).show();
        }
    }

}
