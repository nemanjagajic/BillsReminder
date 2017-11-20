package com.gajic.nemanja.billsreminder.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gajic.nemanja.billsreminder.data.NoteContract.NoteEntry;
import com.gajic.nemanja.billsreminder.data.BillContract.BillEntry;


public class BillsReminderDbHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "billsreminder.db";
    private final static int DATABASE_VERSION = 1;

    public BillsReminderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create notes table
        String SQL_CREATE_NOTES_TABLE = "CREATE TABLE " + NoteEntry.TABLE_NAME + " (" +
                NoteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NoteEntry.COLUMN_NOTES_TEXT + " TEXT);";

        db.execSQL(SQL_CREATE_NOTES_TABLE);

        // Creating bills table
        String SQL_CREATE_BILLS_TABLE = "CREATE TABLE " + BillEntry.TABLE_NAME + " (" +
                BillEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BillEntry.COLUMN_BILLS_TITLE + " TEXT, " +
                BillEntry.COLUMN_BILLS_DATE + " TEXT, " +
                BillEntry.COLUMN_BILLS_AMOUNT + " TEXT, " +
                BillEntry.COLUMN_BILLS_TITLE_COLOR + " INTEGER);";

        db.execSQL(SQL_CREATE_BILLS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
