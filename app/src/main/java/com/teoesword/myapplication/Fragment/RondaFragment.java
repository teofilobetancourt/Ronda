package com.teoesword.myapplication.Fragment;

import android.content.Intent;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.teoesword.myapplication.Activity.DetalleActivity;
import com.teoesword.myapplication.Databases.DBHelper;
import com.teoesword.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
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
        setupSpinner(rootView, spinner, textViewUnidadSeleccionada);

        // Agrega un OnClickListener al botón "Aceptar" para manejar el clic
        Button btnAceptar = rootView.findViewById(R.id.btnAceptar);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarValorEnBaseDeDatos();
            }
        });

        // Configurar el botón flotante para abrir DetalleActivity
        FloatingActionButton fab = rootView.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
            // Obtén la descripción_cml seleccionada en el Spinner
            Spinner spinner1 = rootView.findViewById(R.id.spinner);
            String descripcionCmlSeleccionada = (String) spinner1.getSelectedItem();

            // Obtén todos los datos asociados a la descripción_cml seleccionada desde tu base de datos
            HashMap<String, String> datosAsociados = obtenerDatosAsociados(descripcionCmlSeleccionada);

            // Verifica si se encontraron datos asociados
            if (!datosAsociados.isEmpty()) {
                // Obtén los datos específicos de la base de datos en el orden correcto
                String fecha = datosAsociados.get("fecha");
                String coTarea = datosAsociados.get("co_tarea");
                String descripcionTarea = datosAsociados.get("descripcion_tarea");
                String idGrupoRonda = datosAsociados.get("id_grupo_ronda");
                String descripcionGrupoRonda = datosAsociados.get("descripcion_grupo_ronda");
                String secuenciaGrupo = datosAsociados.get("secuencia_grupo");
                String idCml = datosAsociados.get("id_cml");
                String equipo = datosAsociados.get("equipo");
                String tagEquipo = datosAsociados.get("tag_equipo");
                String utSistema = datosAsociados.get("ut_sistema");
                String descripcionCml = datosAsociados.get("descripcion_cml");
                String secuenciaCml = datosAsociados.get("secuencia_cml");
                String valor = datosAsociados.get("valor");
                String unit = datosAsociados.get("unit");

                // Inicia DetalleActivity y pasa todos los datos necesarios como extras
                Intent intent = new Intent(getActivity(), DetalleActivity.class);
                intent.putExtra("id_cml", idCml);
                intent.putExtra("descripcion_cml", descripcionCml);
                intent.putExtra("fecha", fecha);
                intent.putExtra("co_tarea", coTarea);
                intent.putExtra("descripcion_tarea", descripcionTarea);
                intent.putExtra("id_grupo_ronda", idGrupoRonda);
                intent.putExtra("descripcion_grupo_ronda", descripcionGrupoRonda);
                intent.putExtra("secuencia_grupo", secuenciaGrupo);
                intent.putExtra("equipo", equipo);
                intent.putExtra("tag_equipo", tagEquipo);
                intent.putExtra("ut_sistema", utSistema);
                intent.putExtra("secuencia_cml", secuenciaCml);
                intent.putExtra("valor", valor);
                intent.putExtra("unit", unit);

                startActivity(intent);
            } else {
                // Manejar el caso en el que no se encontraron datos asociados
                Toast.makeText(getActivity(), "No se encontraron datos para la descripción seleccionada", Toast.LENGTH_SHORT).show();
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
            String selectedItem = (String) spinner.getSelectedItem();

            // Dividir el elemento seleccionado en id_cml y descripcion_cml
            String[] parts = selectedItem.split(" - ");
            String idCmlSeleccionado = parts[0];
            String descripcionCmlSeleccionada = parts[1];

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

    private void setupSpinner(View rootView, Spinner spinner, TextView textViewUnidadSeleccionada) {
        // Ejecutar AsyncTask para obtener la lista de descripciones_cml
        new GetDescripcionCmlListAsyncTask(spinner, textViewUnidadSeleccionada).execute();
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
                List<HashMap<String, String>> cmlList = dbHelper.getCmlListWithIdAndDescription();

                // Log para verificar la lista obtenida
                Log.d(TAG, "Lista obtenida: " + cmlList);

                List<String> displayList = new ArrayList<>();

                for (HashMap<String, String> cmlMap : cmlList) {
                    // Añadir directamente la descripción_cml a la lista
                    String descripcionCml = cmlMap.get("descripcion_cml");
                    displayList.add(descripcionCml);
                }

                return displayList;
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

                        // Agregar logs para depuración
                        Log.d(TAG, "Descripción_cml seleccionada: " + descripcionCmlSeleccionada);
                        Log.d(TAG, "Unidad asociada: " + unidadAsociada);
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

    private HashMap<String, String> obtenerDatosAsociados(String descripcionCml) {
        HashMap<String, String> datosAsociados = new HashMap<>();
        try {
            // Obtener los datos asociados a la descripción_cml seleccionada
            datosAsociados = dbHelper.obtenerDatosAsociados(descripcionCml);

            // Agregar logs para depuración
            if (!datosAsociados.isEmpty()) {
                Log.d(TAG, "Datos asociados encontrados: " + datosAsociados);
            } else {
                Log.d(TAG, "No se encontraron datos asociados para la descripción_cml: " + descripcionCml);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al obtener datos asociados", e);
        }
        return datosAsociados;
    }
}
