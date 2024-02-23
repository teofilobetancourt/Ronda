package com.teoesword.myapplication.Databases;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static com.teoesword.myapplication.Databases.RondaDBHelper.COLUMN_DESCRIPCION_CML;
import static com.teoesword.myapplication.Databases.RondaDBHelper.TABLE_NAME;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ronda.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla "ronda" con la estructura actual
        String createTableQuery = "CREATE TABLE IF NOT EXISTS ronda " +
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
        android.util.Log.v("BBDD",
                "BD actualizándose. Se perderán los datos antiguos");

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

}
