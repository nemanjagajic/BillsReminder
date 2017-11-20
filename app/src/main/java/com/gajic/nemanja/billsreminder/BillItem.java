package com.gajic.nemanja.billsreminder;

/**
 * Simple class representing bill item
 */

public class BillItem {

    private String title;
    private String date;
    private String amount;
    private int itemsTitleColor;
    private int buttonsLeftPadding; // needed for recognizing if in BillsFragment, could be any attribute

    public BillItem(String title, String date, String amount) {
        this.title = title;
        this.date = date;
        this.amount = amount;
        itemsTitleColor = R.color.colorWhite;
        buttonsLeftPadding = 0;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }

    public int getItemsTitleColor() {
        return itemsTitleColor;
    }

    public int getButtonsLeftPadding() { return buttonsLeftPadding; }

    //Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setItemsTitleColor(int itemsTitleColor) {
        this.itemsTitleColor = itemsTitleColor;
    }

    public void setButtonsLeftPadding(int buttonsLeftPadding) {
        this.buttonsLeftPadding = buttonsLeftPadding;
    }

    @Override
    public String toString() {
        return "BillItem{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", amount='" + amount + '\'' +
                ", itemsTitleColor=" + itemsTitleColor +
                ", buttonsLeftPadding=" + buttonsLeftPadding +
                '}';
    }
}
