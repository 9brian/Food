package com.example.foodflash;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.foodflash.DB.AppDataBase;

@Entity(tableName = AppDataBase.DISCOUNT_TABLE)
public class Discount {
    @PrimaryKey(autoGenerate = true)
    private int mDiscountId;

    private String mDiscountCode;
    private double mDiscountPercentage;

    public Discount(String discountCode, double discountPercentage) {
        mDiscountCode = discountCode;
        mDiscountPercentage = discountPercentage * 10.0;
    }

    @Override
    public String toString() {
        return
                "Congrats on your discount! Enjoy " + mDiscountPercentage + "% off your purchases.";
//                "DiscountId: " + mDiscountId + "\n" +
//                "DiscountCode: " + mDiscountCode + "\n" +
//                "DiscountPercentage: " + mDiscountPercentage + "\n"
//                        + "=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=" + "\n";
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

    public double getDiscountPercentage() {
        return mDiscountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        mDiscountPercentage = discountPercentage;
    }

}
