package com.example.foodflash.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.foodflash.Cart;

@Dao
public interface CartDAO {
    @Insert
    void insert(Cart... carts);

    @Update
    void update(Cart... carts);

    @Delete
    void delete(Cart cart);
}
