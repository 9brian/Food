package com.example.foodflash;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.foodflash.DB.AppDataBase;

@Entity(tableName = AppDataBase.ITEM_TABLE)
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int mItemId;

    private String mItemName;
    private String mItemIngredients;
    private Double mItemPrice;

    public Item(String itemName, String itemIngredients, Double itemPrice) {
        mItemName = itemName;
        mItemIngredients = itemIngredients;
        mItemPrice = itemPrice;
    }

    @Override
    public String toString() {
        return "ItemId: " +  mItemId + "\n" +
                "ItemName: " + mItemName + '\n' +
                "Ingredients: " + mItemIngredients + '\n' +
                "Price: " + mItemPrice + '\n' +
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

    public String getItemIngredients() {
        return mItemIngredients;
    }

    public void setItemIngredients(String itemIngredients) {
        mItemIngredients = itemIngredients;
    }

    public Double getItemPrice() {
        return mItemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        mItemPrice = itemPrice;
    }
}
