package com.gajic.nemanja.billsreminder;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterNotes extends ArrayAdapter<NoteItem> {

    public AdapterNotes(@NonNull Context context, @Nullable ArrayList<NoteItem> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.note_item, parent, false);
        }

        NoteItem currentBillItem = getItem(position);

        TextView text = (TextView)listItemView.findViewById(R.id.text);

        text.setText(currentBillItem.getText());

        // Setting description and padding used for finding position when deleting
        ImageButton deleteButton = (ImageButton)listItemView.findViewById(R.id.button_delete_note);
        deleteButton.setContentDescription(position + "");

        return listItemView;
    }

}
