package com.teoesword.myapplication.Databases;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

public class DatabaseClient {

    private final RondaDatabase rondaDatabase;

    private static DatabaseClient instance;

    private DatabaseClient(Context context) {
        rondaDatabase = Room.databaseBuilder(context, RondaDatabase.class, "ronda.db").build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    public RondaDatabase getRondaDatabase() {
        return rondaDatabase;
    }
}
