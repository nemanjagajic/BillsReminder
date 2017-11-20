package com.gajic.nemanja.billsreminder;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Custom ArrayAdapter for bill items
 */

public class AdapterBills extends ArrayAdapter<BillItem> {


    public AdapterBills(@NonNull Context context, @Nullable ArrayList<BillItem> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.bill_item, parent, false);
        }

        BillItem currentBillItem = getItem(position);

        TextView title = (TextView)listItemView.findViewById(R.id.title);
        TextView date = (TextView)listItemView.findViewById(R.id.date);
        TextView amount = (TextView)listItemView.findViewById(R.id.amount);

        title.setText(currentBillItem.getTitle());
        date.setText(currentBillItem.getDate());
        amount.setText(currentBillItem.getAmount());

        // Setting description and padding used for finding position when deleting
        ImageButton deleteButton = (ImageButton)listItemView.findViewById(R.id.button_delete);
        deleteButton.setContentDescription(position + "");
        deleteButton.setPadding(currentBillItem.getButtonsLeftPadding(), 0, 0, 0);

        ImageButton payedButton = (ImageButton)listItemView.findViewById(R.id.button_payed);
        payedButton.setContentDescription(position + "");
        payedButton.setPadding(currentBillItem.getButtonsLeftPadding(), 0, 0, 0);

        // Setting color to title
        if (currentBillItem.getItemsTitleColor() == R.color.colorTitlePaid) {
            title.setTextColor(getContext().getResources().getColor(R.color.colorTitlePaid));
        }

        return listItemView;
    }
}
