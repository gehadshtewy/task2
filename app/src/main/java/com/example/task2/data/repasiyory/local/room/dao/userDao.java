package com.example.task2.data.repasiyory.local.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.task2.data.repasiyory.local.room.entity.user;

import java.util.List;

@Dao
public interface userDao {
    @Insert
    void insert(user user);

    @Query("SELECT * FROM users")
    List<user> getAllUsers();

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    user getUserByEmail(String email);

    @Query("DELETE FROM users")
    void deleteAllUsers();


}
