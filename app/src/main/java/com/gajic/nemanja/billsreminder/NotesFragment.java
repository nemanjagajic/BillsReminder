package com.gajic.nemanja.billsreminder;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gajic.nemanja.billsreminder.data.BillsReminderDbHelper;
import com.gajic.nemanja.billsreminder.data.NoteContract;
import com.gajic.nemanja.billsreminder.data.NotesProvider;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends ListFragment {

    public static ArrayList<NoteItem> notes;
    public static AdapterNotes adapter;

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Initializing notes
        notes = new ArrayList<>();

        // Fill notes from database
        Cursor cursor = getContext().getContentResolver().query(NoteContract.NoteEntry.CONTENT_URI, null, null, null, null);
        int textIndex = cursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_NOTES_TEXT);
        while (cursor.moveToNext()) {
            notes.add(new NoteItem(cursor.getString(textIndex)));
        }
        cursor.close();

        // Creating simple adapter with one TextView
        adapter = new AdapterNotes(getContext(), notes);

        // Setting the adapter
        setListAdapter(adapter);

        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    public static void notifyChanges() {
        adapter.notifyDataSetChanged();
    }

}
