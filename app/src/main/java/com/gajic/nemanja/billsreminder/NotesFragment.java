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
public class NotesFragment extends ListFragment {

    public static ArrayList<NoteItem> notes = new ArrayList<>();
    public static AdapterNotes adapter;


    public NotesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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
