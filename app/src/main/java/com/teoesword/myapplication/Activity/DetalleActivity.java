package com.teoesword.myapplication.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.teoesword.myapplication.Fragment.RondaFragment;
import com.teoesword.myapplication.R;

public class DetalleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        // Obtener los datos pasados desde la actividad anterior
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String fecha = extras.getString("fecha");
            String coTarea = extras.getString("co_tarea");
            String descripcionTarea = extras.getString("descripcion_tarea");
            String idGrupoRonda = extras.getString("id_grupo_ronda");
            String descripcionGrupoRonda = extras.getString("descripcion_grupo_ronda");
            String secuenciaGrupo = extras.getString("secuencia_grupo");
            String idCml = extras.getString("id_cml");
            String equipo = extras.getString("equipo");
            String tagEquipo = extras.getString("tag_equipo");
            String utSistema = extras.getString("ut_sistema");
            String descripcionCml = extras.getString("descripcion_cml");
            String secuenciaCml = extras.getString("secuencia_cml");
            String valor = extras.getString("valor");
            String unit = extras.getString("unit");

            // Mostrar los datos en los TextViews correspondientes
            TextView textViewFecha = findViewById(R.id.textViewFecha);
            textViewFecha.setText("Fecha: " + fecha);

            TextView textViewCoTarea = findViewById(R.id.textViewCoTarea);
            textViewCoTarea.setText("Co Tarea: " + coTarea);

            TextView textViewDescripcionTarea = findViewById(R.id.textViewDescripcionTarea);
            textViewDescripcionTarea.setText("Descripción Tarea: " + descripcionTarea);

            TextView textViewIdGrupoRonda = findViewById(R.id.textViewIdGrupoRonda);
            textViewIdGrupoRonda.setText("ID Grupo Ronda: " + idGrupoRonda);

            TextView textViewDescripcionGrupoRonda = findViewById(R.id.textViewDescripcionGrupoRonda);
            textViewDescripcionGrupoRonda.setText("Descripción Grupo Ronda: " + descripcionGrupoRonda);

            TextView textViewSecuenciaGrupo = findViewById(R.id.textViewSecuenciaGrupo);
            textViewSecuenciaGrupo.setText("Secuencia Grupo: " + secuenciaGrupo);

            TextView textViewIdCml = findViewById(R.id.textViewIdCml);
            textViewIdCml.setText("ID CML: " + idCml);

            TextView textViewEquipo = findViewById(R.id.textViewEquipo);
            textViewEquipo.setText("Equipo: " + equipo);

            TextView textViewTagEquipo = findViewById(R.id.textViewTagEquipo);
            textViewTagEquipo.setText("Tag Equipo: " + tagEquipo);

            TextView textViewUtSistema = findViewById(R.id.textViewUtSistema);
            textViewUtSistema.setText("UT Sistema: " + utSistema);

            TextView textViewDescripcionCml = findViewById(R.id.textViewDescripcionCml);
            textViewDescripcionCml.setText("Descripción CML: " + descripcionCml);

            TextView textViewSecuenciaCml = findViewById(R.id.textViewSecuenciaCml);
            textViewSecuenciaCml.setText("Secuencia CML: " + secuenciaCml);

            TextView textViewValor = findViewById(R.id.textViewValor);
            textViewValor.setText("Valor: " + valor);

            TextView textViewUnit = findViewById(R.id.textViewUnit);
            textViewUnit.setText("Unidad: " + unit);
        }

        // Configurar el botón "Cerrar" para cerrar la actividad actual
        Button btnCerrar = findViewById(R.id.btnCerrar);
        btnCerrar.setOnClickListener(view -> finish());
    }
}
