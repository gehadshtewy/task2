package com.example.task2.data.repasiyory.local.room.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.task2.data.repasiyory.local.room.dao.userDao;
import com.example.task2.data.repasiyory.local.room.entity.user;

@Database(entities = {user.class}, version = 1)
public abstract class database extends RoomDatabase {
    private static database instance;
    public static synchronized database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            database.class,
                            "users_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract userDao userDao();
}

