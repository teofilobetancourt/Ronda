package com.teoesword.myapplication.Databases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.teoesword.myapplication.Databases.RondaDBHelper.COLUMN_DESCRIPCION_CML;
import static com.teoesword.myapplication.Databases.RondaDBHelper.TABLE_NAME;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";
    private static final String DATABASE_NAME = "ronda.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla "ronda" con la estructura actual
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " " +
                "(fecha INTEGER, " +
                "co_tarea TEXT, " +
                "descripcion_tarea TEXT, " +
                "id_grupo_ronda TEXT, " +
                "descripcion_grupo_ronda TEXT, " +
                "secuencia_grupo TEXT, " +
                "id_cml TEXT, " +
                "equipo TEXT, " +
                "tag_equipo TEXT, " +
                "ut_sistema TEXT, " +
                "descripcion_cml TEXT, " +
                "secuencia_cml TEXT, " +
                "valor TEXT, " +
                "unit TEXT)";

        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v("BBDD", "BD actualizándose. Se perderán los datos antiguos");
        onCreate(db);
    }

    // Método para obtener la lista de descripciones_cml
    public List<String> getDescripcionCmlList() {
        List<String> descripcionCmlList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_DESCRIPCION_CML};

        // Imprime la ruta de la base de datos (puedes comentar esto después de verificar)
        Log.d(TAG, "Database path: " + db.getPath());

        // Reemplaza "ronda" por el nombre real de tu tabla
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    // Asumiendo que la columna de la descripción es la primera en el resultado
                    String descripcionCml = cursor.getString(0);
                    descripcionCmlList.add(descripcionCml);
                    // Imprime cada valor (puedes comentar esto después de verificar)
                    Log.d(TAG, "DescripcionCml value: " + descripcionCml);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        return descripcionCmlList;
    }

    @SuppressLint("Range")
    public String obtenerUnidadAsociadaDesdeDB(String descripcionCml) {
        String unidadAsociada = "";

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"unit"}; // Reemplaza "unidad_columna" con el nombre real de la columna de unidades

        // Realiza una consulta para obtener la unidad asociada a la descripción_cml seleccionada
        Cursor cursor = db.query(TABLE_NAME, columns, COLUMN_DESCRIPCION_CML + " = ?", new String[]{descripcionCml}, null, null, null);

        // Verifica si hay al menos una fila en el cursor
        if (cursor.moveToFirst()) {
            unidadAsociada = cursor.getString(cursor.getColumnIndex("unit")); // Reemplaza "unidad_columna" con el nombre real de la columna de unidades
        }

        // Cierra el cursor y la base de datos
        cursor.close();
        db.close();

        return unidadAsociada;
    }

    public void insertarValor(String descripcionCml, String nuevoValor) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("valor", nuevoValor);

        // Actualizar el valor asociado a la descripción_cml específica
        int numRowsAffected = db.update(TABLE_NAME, values, COLUMN_DESCRIPCION_CML + " = ?", new String[]{descripcionCml});

        if (numRowsAffected == 0) {
            // Si no se actualizó ninguna fila, entonces no existía una fila con esa descripción_cml
            // Puedes optar por insertar una nueva fila si lo deseas
            // db.insert(TABLE_NAME, null, values);
        }

        db.close();
    }
    public List<HashMap<String, String>> getCmlListWithIdAndDescription() {
        List<HashMap<String, String>> cmlListWithIdAndDescription = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id_cml", COLUMN_DESCRIPCION_CML}; // Agrega el id_cml y la descripción_cml

        // Reemplaza "ronda" por el nombre real de tu tabla
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    // Obtén el id_cml y la descripción_cml de cada fila
                    @SuppressLint("Range") int idCml = cursor.getInt(cursor.getColumnIndex("id_cml"));
                    @SuppressLint("Range") String descripcionCml = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPCION_CML));

                    // Crea un HashMap con el id_cml y la descripción_cml y agrégalo a la lista
                    HashMap<String, String> cmlMap = new HashMap<>();
                    cmlMap.put("id_cml", String.valueOf(idCml));
                    cmlMap.put("descripcion_cml", descripcionCml);
                    cmlListWithIdAndDescription.add(cmlMap);

                    // Imprime cada valor (puedes comentar esto después de verificar)
                    Log.d(TAG, "ID_CML: " + idCml + ", DescripcionCml value: " + descripcionCml);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        return cmlListWithIdAndDescription;
    }

}
