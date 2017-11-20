package com.gajic.nemanja.billsreminder;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gajic.nemanja.billsreminder.data.BillContract;
import com.gajic.nemanja.billsreminder.data.NoteContract;

public class AddBill extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
    }

    // Called when fab from activity_add_bill is clicked
    public void addBill(View view) {
        EditText titleText = (EditText)findViewById(R.id.title_input);
        EditText dateText = (EditText)findViewById(R.id.date_input);
        EditText amountText = (EditText)findViewById(R.id.amount_input);

        String title = titleText.getText().toString();
        String date = dateText.getText().toString();
        String amount = amountText.getText().toString();

        // Title check
        if (title.equals("")) {
            title = "empty title";
        } else if (isNumeric(title)) {
            title = "number " + title;
        }

        BillItem billItem = new BillItem(title, date, amount);

        // Add to database
        ContentValues values = new ContentValues();
        values.put(BillContract.BillEntry.COLUMN_BILLS_TITLE, title);
        values.put(BillContract.BillEntry.COLUMN_BILLS_DATE, date);
        values.put(BillContract.BillEntry.COLUMN_BILLS_AMOUNT, amount);
        getContentResolver().insert(BillContract.BillEntry.CONTENT_URI, values);

        // Add to list
        BillsFragment.billItems.add(billItem);
        BillsFragment.notifyChanges();

        finish();
    }

    private boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

}
