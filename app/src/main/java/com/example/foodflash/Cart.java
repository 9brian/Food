package com.example.foodflash;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.foodflash.DB.AppDataBase;

@Entity(tableName = AppDataBase.CART_TABLE)
public class Cart {

    @PrimaryKey(autoGenerate = true)
    private int mCartId;

    private int mMenuId;
    private int mUserId;

    public Cart(int menuId, int userId) {
        mMenuId = menuId;
        mUserId = userId;
    }

    @Override
    public String toString() {
        return
                "CartId:" + mCartId + "\n" +
                "MenuId:" + mMenuId + "\n" +
                "UserId:" + mUserId + "\n";
    }

    public int getCartId() {
        return mCartId;
    }

    public void setCartId(int cartId) {
        mCartId = cartId;
    }

    public int getMenuId() {
        return mMenuId;
    }

    public void setMenuId(int menuId) {
        mMenuId = menuId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }
}
