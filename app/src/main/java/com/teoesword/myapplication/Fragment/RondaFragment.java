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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
        View rootView = inflater.inflate(R.layout.fragment_ronda, container, false);

        dbHelper = new DBHelper(requireContext());

        // Configurar el Spinner y TextView
        Spinner spinner = rootView.findViewById(R.id.spinner);
        TextView textViewUnidadSeleccionada = rootView.findViewById(R.id.textView2);

        // Configurar el Spinner y el TextView
        setupSpinner(rootView);

        // Agrega un OnClickListener al botón "Aceptar" para manejar el clic
        Button btnAceptar = rootView.findViewById(R.id.btnAceptar);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarValorEnBaseDeDatos();
            }
        });

        return rootView;
    }

    private void guardarValorEnBaseDeDatos() {
        // Obtiene el valor ingresado en el EditText
        EditText editText = requireView().findViewById(R.id.editTextText);
        String valorIngresado = editText.getText().toString().trim();

        // Verifica si se ingresó un valor
        if (!valorIngresado.isEmpty()) {
            // Obtiene la descripción_cml seleccionada en el Spinner
            Spinner spinner = requireView().findViewById(R.id.spinner);
            String descripcionCmlSeleccionada = (String) spinner.getSelectedItem();

            // Actualiza el valor en la base de datos solo para la descripción_cml seleccionada
            dbHelper.insertarValor(descripcionCmlSeleccionada, valorIngresado);

            // Limpia el EditText después de agregar el valor
            editText.getText().clear();

            // Puedes mostrar un mensaje de éxito al usuario si lo deseas
            Toast.makeText(requireContext(), "Valor actualizado con éxito", Toast.LENGTH_SHORT).show();
        } else {
            // Muestra un mensaje si no se ingresó ningún valor
            Toast.makeText(requireContext(), "Ingrese un valor antes de actualizar", Toast.LENGTH_SHORT).show();
        }
    }


    private void setupSpinner(View rootView) {
        // Obtén el Spinner y TextView
        Spinner spinner = rootView.findViewById(R.id.spinner);
        TextView textViewUnidadSeleccionada = rootView.findViewById(R.id.textView2);

        // Ejecutar AsyncTask para obtener la lista de descripciones_cml
        new GetDescripcionCmlListAsyncTask(spinner, textViewUnidadSeleccionada).execute();

        // Configurar un OnItemSelectedListener para el Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Obtén la descripción_cml seleccionada
                String descripcionCmlSeleccionada = parentView.getItemAtPosition(position).toString();

                // Obtén la unidad asociada de la base de datos
                String unidadAsociada = dbHelper.obtenerUnidadAsociadaDesdeDB(descripcionCmlSeleccionada);

                // Actualiza el TextView con la unidad seleccionada
                textViewUnidadSeleccionada.setText("Unidad Seleccionada: " + unidadAsociada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Maneja el caso en el que no se haya seleccionado nada en el Spinner (si es necesario)
            }
        });
    }

    private class GetDescripcionCmlListAsyncTask extends AsyncTask<Void, Void, List<String>> {
        private Spinner spinner;
        private TextView textViewUnidadSeleccionada;

        // Constructor que acepta un Spinner y un TextView como parámetros
        public GetDescripcionCmlListAsyncTask(Spinner spinner, TextView textViewUnidadSeleccionada) {
            this.spinner = spinner;
            this.textViewUnidadSeleccionada = textViewUnidadSeleccionada;
        }

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
                        textViewUnidadSeleccionada.setText("Unidad Seleccionada: " + unidadAsociada);
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
