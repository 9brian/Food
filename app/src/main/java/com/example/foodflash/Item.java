package com.example.foodflash;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.foodflash.DB.AppDataBase;

@Entity(tableName = AppDataBase.ITEM_TABLE)
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int mItemId;

    private String mItemName;
    private String mItemDescription;
    private Double mItemPrice;

    public Item(String itemName, String itemDescription, Double itemPrice) {
        mItemName = itemName;
        mItemDescription = itemDescription;
        mItemPrice = itemPrice;
    }

    @Override
    public String toString() {
        return "ItemId: " +  mItemId + "\n" +
                "ItemName: " + mItemName + '\n' +
                "Description: " + mItemDescription + '\n' +
                "Price: $" + mItemPrice + '\n' +
                "=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=" + "\n";
    }

    public int getItemId() {
        return mItemId;
    }

    public void setItemId(int itemId) {
        mItemId = itemId;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String itemName) {
        mItemName = itemName;
    }

    public String getItemDescription() {
        return mItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        mItemDescription = itemDescription;
    }

    public Double getItemPrice() {
        return mItemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        mItemPrice = itemPrice;
    }
}
