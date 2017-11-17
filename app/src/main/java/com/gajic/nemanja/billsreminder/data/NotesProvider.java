package com.gajic.nemanja.billsreminder.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.gajic.nemanja.billsreminder.data.NoteContract.NoteEntry;

public class NotesProvider extends ContentProvider {

    private static final int NOTES = 100;
    private static final int NOTES_ID = 101;
    private static final int NOTES_TEXT = 102;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private BillsReminderDbHelper mDbHelper;

    static {
        sUriMatcher.addURI(NoteContract.CONTENT_AUTHORITY, NoteContract.PATH_NOTES, NOTES);
        sUriMatcher.addURI(NoteContract.CONTENT_AUTHORITY, NoteContract.PATH_NOTES + "/#", NOTES_ID);
        sUriMatcher.addURI(NoteContract.CONTENT_AUTHORITY, NoteContract.PATH_NOTES + "/*", NOTES_TEXT);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new BillsReminderDbHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of query
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch(match) {
            case NOTES:
                cursor = db.query(NoteEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case NOTES_ID:
                selection = NoteEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = db.query(NoteEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // Notify all listeners that data has changed for the note content uri
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NOTES:
                return insertNote(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    // Helper method which adds note
    private Uri insertNote(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        long id = db.insert(NoteEntry.TABLE_NAME, null, values);

        if (id < 0) {
            Toast.makeText(getContext(), "Error occurred adding note", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Note added", Toast.LENGTH_SHORT).show();
        }

        // Notify all listeners that data has changed for the note content uri
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NOTES:
                getContext().getContentResolver().notifyChange(uri, null);
                return db.delete(NoteEntry.TABLE_NAME, selection, selectionArgs);
            case NOTES_TEXT:
                // Delete a single row given by the TEXT in the URI
                selection = NoteEntry.COLUMN_NOTES_TEXT + "=?";

                String text = uri.getPath().replace("/notes/", ""); // remove /notes/ part from string

                selectionArgs = new String[] { text };

                Toast.makeText(getContext(), "Note deleted", Toast.LENGTH_SHORT).show();

                // Notify all listeners that data has changed for the pet content uri
                getContext().getContentResolver().notifyChange(uri, null);
                return db.delete(NoteEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
