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

    private boolean mIsAdmin;

    public User(String userName, String passWord, String discountId, boolean isAdmin) {
        mUserName = userName;
        mPassWord = passWord;
        mDiscountId = discountId;
        mIsAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return "UserId: " + mUserId + "\n" +
                "username: " + mUserName + "\n" +
                "password: " + mPassWord + "\n" +
                "discountId" + mDiscountId + "\n" +
                "mDiscountId" + mIsAdmin + "\n" +
                "=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=" + "\n";
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

    public boolean isAdmin() {
        return mIsAdmin;
    }

    public void setAdmin(boolean admin) {
        mIsAdmin = admin;
    }
}
