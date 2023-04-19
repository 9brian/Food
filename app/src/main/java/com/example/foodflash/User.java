package com.example.foodflash;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.foodflash.DB.AppDataBase;

@Entity(tableName = AppDataBase.USER_TABLE)
public class User {
    @PrimaryKey(autoGenerate = true)
    private int mUserId;

    private String mUserName;
    private String mPassWord;
    private String mDiscountId;

    public User(String userName, String passWord, String discountId) {
        mUserName = userName;
        mPassWord = passWord;
        mDiscountId = discountId;
    }

    @Override
    public String toString() {
        return "User{" +
                "mUserId='" + mUserId + '\'' +
                "mUserName='" + mUserName + '\'' +
                ", mPassWord='" + mPassWord + '\'' +
                ", mDiscountId='" + mDiscountId + '\'' +
                '}';
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassWord() {
        return mPassWord;
    }

    public void setPassWord(String passWord) {
        mPassWord = passWord;
    }

    public String getDiscountId() {
        return mDiscountId;
    }

    public void setDiscountId(String discountId) {
        mDiscountId = discountId;
    }
}
