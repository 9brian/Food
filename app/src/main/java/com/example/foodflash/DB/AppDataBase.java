package com.example.foodflash.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodflash.Cart;
import com.example.foodflash.Discount;
import com.example.foodflash.Item;
import com.example.foodflash.User;

@Database(entities = {User.class, Item.class, Discount.class, Cart.class}, version = 5)
// To add versions, device manager, arrow, wipe data
public abstract class AppDataBase extends RoomDatabase {
    public static final String DATABASE_NAME = "User.db";
    public static final String USER_TABLE = "user_table";
    public static final String ITEM_TABLE = "item_table";
    public static final String DISCOUNT_TABLE = "discount_table";
    public static final String CART_TABLE = "cart_table";

    private static volatile AppDataBase instance;
    private static final Object LOCK = new Object();

    public abstract UserDAO UserDAO();
    public abstract ItemDAO ItemDAO();
    public abstract DiscountDAO DiscountDAO();
    public abstract CartDAO CartDAO();

    public static AppDataBase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if (instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
