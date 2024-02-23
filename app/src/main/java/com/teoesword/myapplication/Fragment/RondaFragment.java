package com.teoesword.myapplication.Fragment;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.teoesword.myapplication.Databases.DBHelper;
import com.teoesword.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class RondaFragment extends Fragment {

    private static final String TAG = "RondaFragment";
    private DBHelper dbHelper;

    // Constructor vacío requerido
    public RondaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño para este fragmento
        View rootView = inflater.inflate(R.layout.fragment_ronda, container, false);

        // Configurar las vistas y manejar eventos aquí si es necesario

        // Obtén la instancia de DBHelper
        dbHelper = new DBHelper(requireContext());

        // Ejecutar AsyncTask para obtener la lista de descripciones_cml
        new GetDescripcionCmlListAsyncTask().execute();

        return rootView;
    }

    private class GetDescripcionCmlListAsyncTask extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... voids) {
            try {
                // Obtener la lista de descripciones_cml en un hilo en segundo plano
                List<String> descripcionCmlList = dbHelper.getDescripcionCmlList();

                // Log para verificar la lista obtenida
                Log.d(TAG, "Lista obtenida: " + descripcionCmlList);

                return descripcionCmlList;
            } catch (Exception e) {
                Log.e(TAG, "Error al obtener la lista de descripciones_cml", e);
                return new ArrayList<>();
            }
        }

        @Override
        protected void onPostExecute(List<String> descripcionCmlList) {
            super.onPostExecute(descripcionCmlList);

            // Verificar si la lista está vacía
            if (descripcionCmlList.isEmpty()) {
                Log.d(TAG, "La lista de descripciones está vacía");
            } else {
                Log.d(TAG, "Tamaño de la lista de descripciones: " + descripcionCmlList.size());

                // Configurar el Spinner después de obtener la lista en el hilo principal
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, descripcionCmlList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner spinner = requireView().findViewById(R.id.spinner);
                spinner.setAdapter(adapter);

                // Agregar un listener al Spinner para manejar la selección
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        // Obtener la descripción_cml seleccionada
                        String descripcionCmlSeleccionada = (String) parentView.getSelectedItem();

                        // Obtener la unidad asociada desde la base de datos
                        String unidadAsociada = dbHelper.obtenerUnidadAsociadaDesdeDB(descripcionCmlSeleccionada);

                        // Actualizar el TextView con la unidad asociada
                        TextView textViewUnidad = requireView().findViewById(R.id.textView2);
                        textViewUnidad.setText("Unidad Seleccionada: " + unidadAsociada);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // No se necesita hacer nada aquí
                    }
                });

                Log.d(TAG, "Spinner configurado exitosamente");
            }
        }
    }
}
