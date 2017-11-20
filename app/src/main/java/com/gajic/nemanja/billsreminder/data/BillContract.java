package com.gajic.nemanja.billsreminder.data;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class BillContract {
    public static final String CONTENT_AUTHORITY = "com.gajic.nemanja.billsreminder.bill";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_BILLS = "bills";

    public static final class BillEntry {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BILLS);

        // Mime types for a list of notes and for one note
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BILLS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BILLS;

        // Table and columns
        public static final String TABLE_NAME = "bills";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_BILLS_TITLE = "title";
        public static final String COLUMN_BILLS_DATE = "date";
        public static final String COLUMN_BILLS_AMOUNT = "amount";
        public static final String COLUMN_BILLS_TITLE_COLOR = "titleColor";
    }
}
