package com.example.foodflash.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodflash.Discount;
import com.example.foodflash.Item;

@Dao
public interface DiscountDAO {

    @Insert
    void insert(Discount... discounts);

    @Update
    void update(Discount... discounts);

    @Delete
    void delete(Discount discount);

    @Query("SELECT * FROM " + AppDataBase.DISCOUNT_TABLE + " WHERE mDiscountCode = :discountCode")
    Discount getDiscountByCode(String discountCode);

    @Query("SELECT * FROM " + AppDataBase.DISCOUNT_TABLE + " WHERE mDiscountId = :discountId")
    Discount getDiscountById(int discountId);
}
