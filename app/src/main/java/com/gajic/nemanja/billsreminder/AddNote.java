package com.gajic.nemanja.billsreminder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
    }

    public void addNote(View view) {
        EditText text = (EditText)findViewById(R.id.note_text);

        NotesFragment.notes.add(new NoteItem(text.getText().toString()));
        NotesFragment.notifyChanges();
        Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
    }
}
