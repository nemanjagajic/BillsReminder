package com.gajic.nemanja.billsreminder;


public class NoteItem {

    private String text;

    public NoteItem(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "NoteItem{" +
                "text='" + text + '\'' +
                '}';
    }
}
