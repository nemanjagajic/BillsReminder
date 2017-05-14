package com.gajic.nemanja.billsreminder;

/**
 * Simple class representing bill item
 */

public class BillItem {

    private String title;
    private String date;
    private String amount;
    private int itemsColorResourceLeft;
    private int itemsColorResourceRight;
    private int buttonsLeftPadding; // needed for recognizing if in BillsFragment, could be any attribute

    public BillItem(String title, String date, String amount) {
        this.title = title;
        this.date = date;
        this.amount = amount;
        itemsColorResourceLeft = R.color.colorPrimary;
        itemsColorResourceRight = R.color.colorPrimaryDark;
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

    public int getItemsColorResourceLeft() { return itemsColorResourceLeft;}
    public int getItemsColorResourceRight() { return itemsColorResourceRight;}

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

    public void setItemsColorResourceLeft(int itemsColorResourceLeft) { this.itemsColorResourceLeft = itemsColorResourceLeft; }

    public void setItemsColorResourceRight(int itemsColorResourceRight) { this.itemsColorResourceRight = itemsColorResourceRight; }

    public void setButtonsLeftPadding(int buttonsLeftPadding) {
        this.buttonsLeftPadding = buttonsLeftPadding;
    }

    //toString
    @Override
    public String toString() {
        return "BillItem{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                '}';
    }
}
