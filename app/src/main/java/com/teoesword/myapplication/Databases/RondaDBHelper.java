package com.teoesword.myapplication.Databases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RondaDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ronda.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "ronda";
    public static final String COLUMN_DESCRIPCION_CML = "descripcion_cml";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_DESCRIPCION_CML + " TEXT);";

    private final Context context;

    public RondaDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Puedes implementar migraciones si es necesario
    }

    public Cursor getDescripcionCmlList() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_DESCRIPCION_CML};
        return db.query(TABLE_NAME, columns, null, null, null, null, null);
    }

    @SuppressLint("Range")
    public void copyDataFromPrecachedDatabase() {
        // Ruta de la base de datos precargada en la carpeta assets
        String precachedDbPath = "ronda.db";

        // Ruta de destino para la base de datos nueva
        String newDbPath = context.getDatabasePath(DATABASE_NAME).getPath();

        try {
            copyDatabase(precachedDbPath, newDbPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("Range")
    private void copyDatabase(String sourcePath, String destinationPath) throws IOException {
        InputStream inputStream = context.getAssets().open(sourcePath);
        OutputStream outputStream = new FileOutputStream(destinationPath);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }
}
