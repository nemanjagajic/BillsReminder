package com.gajic.nemanja.billsreminder.data;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class NoteContract {

    public static final String CONTENT_AUTHORITY = "com.gajic.nemanja.billsreminder";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_NOTES = "notes";

    public static final class NoteEntry {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_NOTES);

        // Mime types for a list of notes and for one note
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTES;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTES;

        // Table and columns
        public static final String TABLE_NAME = "notes";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NOTES_TEXT = "text";
    }

}
