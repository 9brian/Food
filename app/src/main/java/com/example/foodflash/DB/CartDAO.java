package com.example.foodflash.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodflash.Cart;
import com.example.foodflash.Item;
import com.example.foodflash.User;

import java.util.List;

@Dao
public interface CartDAO {
    @Insert
    void insert(Cart... carts);

    @Update
    void update(Cart... carts);

    @Delete
    void delete(Cart cart);

    @Query("SELECT * FROM " + AppDataBase.CART_TABLE + " WHERE mUserId = :userId")
    List<Cart> getItemsByUserId(int userId);
}
