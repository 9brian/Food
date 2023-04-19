package com.example.foodflash.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.foodflash.User;

@Dao
public interface UserDAO {
    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);
}
