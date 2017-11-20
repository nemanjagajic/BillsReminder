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
import android.util.Log;
import android.widget.Toast;

public class BillsProvider extends ContentProvider {

    private static final int BILLS = 200;
    private static final int BILLS_ID = 201;
    private static final int BILLS_TEXT = 202;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private BillsReminderDbHelper mDbHelper;

    static {
        sUriMatcher.addURI(BillContract.CONTENT_AUTHORITY, BillContract.PATH_BILLS, BILLS);
        sUriMatcher.addURI(BillContract.CONTENT_AUTHORITY, BillContract.PATH_BILLS + "/#", BILLS_ID);
        sUriMatcher.addURI(BillContract.CONTENT_AUTHORITY, BillContract.PATH_BILLS + "/*", BILLS_TEXT);
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
            case BILLS:
                cursor = db.query(BillContract.BillEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case BILLS_ID:
                selection = BillContract.BillEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = db.query(BillContract.BillEntry.TABLE_NAME, projection, selection, selectionArgs,
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
            case BILLS:
                return insertBill(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    // Helper method which adds note
    private Uri insertBill(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        long id = db.insert(BillContract.BillEntry.TABLE_NAME, null, values);

        if (id < 0) {
            Toast.makeText(getContext(), "Error occurred adding bill", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Bill added", Toast.LENGTH_SHORT).show();
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
            case BILLS:
                getContext().getContentResolver().notifyChange(uri, null);
                return db.delete(BillContract.BillEntry.TABLE_NAME, selection, selectionArgs);
            case BILLS_TEXT:
                // Delete a single row given by the TEXT in the URI
                selection = BillContract.BillEntry.COLUMN_BILLS_TITLE + "=?";

                String text = uri.getPath().replace("/bills/", ""); // remove /notes/ part from string

                selectionArgs = new String[] { text };

                Toast.makeText(getContext(), "Bill deleted", Toast.LENGTH_SHORT).show();

                // Notify all listeners that data has changed for the pet content uri
                getContext().getContentResolver().notifyChange(uri, null);
                return db.delete(BillContract.BillEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        switch (match) {
            case BILLS_TEXT:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = BillContract.BillEntry.COLUMN_BILLS_TITLE + "=?";

                String text = uri.getPath().replace("/bills/", "");

                selectionArgs = new String[] { text };

                return db.update(BillContract.BillEntry.TABLE_NAME, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
