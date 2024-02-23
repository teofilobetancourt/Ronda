package com.teoesword.myapplication.Databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.teoesword.myapplication.Dao.RondaDao;

@Database(entities = {RondaEntity.class}, version = 2, exportSchema = false)
public abstract class RondaDatabase extends RoomDatabase {

    public abstract RondaDao rondaDao();

    // Singleton para asegurar una única instancia de la base de datos
    private static volatile RondaDatabase INSTANCE;

    public static synchronized RondaDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RondaDatabase.class, "ronda.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}

