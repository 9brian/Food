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
public interface UserDAO {
    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);


//    All users based on userId
    @Query("SELECT * FROM " + AppDataBase.USER_TABLE + " WHERE mUserId = :userId")
    List<User> getUserById(int userId);



    @Query("SELECT * FROM " + AppDataBase.USER_TABLE + " WHERE mUserName = :userName")
    User getUserByName(String userName);

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE)
    List<User> getAllUsers();

    // Order History 6
    // Cancel 7
}
