package com.example.foodflash;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.foodflash.DB.AppDataBase;

@Entity(tableName = AppDataBase.USER_TABLE)
public class User {
    // This should work so far
    @PrimaryKey(autoGenerate = true)
    private int mUserId;

    private String mUserName;
    private String mPassWord;
    private int mDiscountId;

    private boolean mIsAdmin;

    public User(String userName, String passWord, int discountId, boolean isAdmin) {
        mUserName = userName;
        mPassWord = passWord;
        mDiscountId = discountId;
        mIsAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return
//                "UserId: " + mUserId + "\n" +
                "Username: " + mUserName + "\n" +
//                "password: " + mPassWord + "\n" +
                "DiscountId: " + mDiscountId + "\n" +
                "Admin: " + mIsAdmin + "\n" +
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

    public int getDiscountId() {
        return mDiscountId;
    }

    public void setDiscountId(int discountId) {
        mDiscountId = discountId;
    }

    public boolean isAdmin() {
        return mIsAdmin;
    }

    public void setAdmin(boolean admin) {
        mIsAdmin = admin;
    }
}
