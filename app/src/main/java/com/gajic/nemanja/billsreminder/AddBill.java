package com.gajic.nemanja.billsreminder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddBill extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
    }

    // Called when fab from activity_add_bill is clicked
    public void addBill(View view) {
        EditText title = (EditText)findViewById(R.id.title_input);
        EditText date = (EditText)findViewById(R.id.date_input);
        EditText amount = (EditText)findViewById(R.id.amount_input);

        BillItem billItem = new BillItem(title.getText().toString(),
                date.getText().toString(), amount.getText().toString());

        BillsFragment.billItems.add(billItem);
        BillsFragment.notifyChanges();
        Toast.makeText(this, "Bill added", Toast.LENGTH_SHORT).show();

        finish();
    }

}
