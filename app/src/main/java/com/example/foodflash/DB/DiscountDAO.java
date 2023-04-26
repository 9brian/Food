package com.example.foodflash.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.foodflash.Discount;

@Dao
public interface DiscountDAO {

    @Insert
    void insert(Discount... discounts);

    @Update
    void update(Discount... discounts);

    @Delete
    void delete(Discount discount);
}
