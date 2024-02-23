package com.teoesword.myapplication.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseClient {

    private final DBHelper dbHelper;
    private SQLiteDatabase database;

    private static DatabaseClient instance;

    private DatabaseClient(Context context) {
        dbHelper = new DBHelper(context);
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}
