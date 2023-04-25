package com.example.foodflash.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodflash.Item;
import com.example.foodflash.User;

import java.util.List;

@Dao
public interface ItemDAO {

    @Insert
    void insert(Item... items);

    @Update
    void update(Item... items);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM " + AppDataBase.ITEM_TABLE + " ORDER BY mItemId desc")
    List<Item> getMenuItems();

    @Query("SELECT * FROM " + AppDataBase.ITEM_TABLE + " WHERE mItemName = :itemName")
    Item getItemByName(String itemName);

    @Query("SELECT * FROM " + AppDataBase.ITEM_TABLE)
    Item getAll();
}
