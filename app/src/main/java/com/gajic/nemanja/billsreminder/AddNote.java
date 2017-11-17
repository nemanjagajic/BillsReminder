package com.gajic.nemanja.billsreminder;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gajic.nemanja.billsreminder.data.NoteContract;
import com.gajic.nemanja.billsreminder.data.NotesProvider;

public class AddNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
    }

    public void addNote(View view) {
        EditText text = (EditText)findViewById(R.id.note_text);
        ContentValues values = new ContentValues();
        values.put(NoteContract.NoteEntry.COLUMN_NOTES_TEXT, text.getText().toString());
        getContentResolver().insert(NoteContract.NoteEntry.CONTENT_URI, values);

        NotesFragment.notes.add(new NoteItem(text.getText().toString()));
        NotesFragment.notifyChanges();

        finish();
    }
}
