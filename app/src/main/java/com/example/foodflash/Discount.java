package com.example.foodflash;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.foodflash.DB.AppDataBase;

@Entity(tableName = AppDataBase.DISCOUNT_TABLE)
public class Discount {
    @PrimaryKey(autoGenerate = true)
    private int mDiscountId;

    private String mDiscountCode;
    private String mDiscountPercentage;

    public Discount(String discountCode, String discountPercentage) {
        mDiscountCode = discountCode;
        mDiscountPercentage = discountPercentage;
    }

    @Override
    public String toString() {
        return
                "DiscountId: " + mDiscountId + "\n" +
                "DiscountCode: " + mDiscountCode + "\n" +
                "DiscountPercentage: " + mDiscountPercentage + "\n"
                        + "=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=" + "\n";
    }

    public int getDiscountId() {
        return mDiscountId;
    }

    public void setDiscountId(int discountId) {
        mDiscountId = discountId;
    }

    public String getDiscountCode() {
        return mDiscountCode;
    }

    public void setDiscountCode(String discountCode) {
        mDiscountCode = discountCode;
    }

    public String getDiscountPercentage() {
        return mDiscountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        mDiscountPercentage = discountPercentage;
    }
}
