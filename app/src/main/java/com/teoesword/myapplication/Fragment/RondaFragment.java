package com.teoesword.myapplication.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.teoesword.myapplication.Dao.RondaDao;
import com.teoesword.myapplication.Databases.DatabaseClient;
import com.teoesword.myapplication.R;

import java.util.List;

public class RondaFragment extends Fragment {

    private static final String TAG = "RondaFragment";
    private RondaDao rondaDao;

    // Constructor vacío requerido
    public RondaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño para este fragmento
        View rootView = inflater.inflate(R.layout.fragment_ronda, container, false);

        // Configurar las vistas y manejar eventos aquí si es necesario

        // Obtén la instancia del DAO de la base de datos
        rondaDao = DatabaseClient.getInstance(requireContext()).getRondaDatabase().rondaDao();

        // Ejecutar AsyncTask para obtener la lista de descripciones_cml
        new GetDescripcionCmlListAsyncTask().execute();

        return rootView;
    }

    private class GetDescripcionCmlListAsyncTask extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... voids) {
            // Obtén la lista de descripciones_cml en un hilo en segundo plano
            return rondaDao.getDescripcionCmlList();
        }



        @Override
        protected void onPostExecute(List<String> descripcionCmlList) {
            super.onPostExecute(descripcionCmlList);

            // Verificar si la lista está vacía
            if (descripcionCmlList.isEmpty()) {
                Log.d(TAG, "La lista de descripciones está vacía");
            } else {
                // Configurar el Spinner después de obtener la lista en el hilo principal
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, descripcionCmlList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner spinner = requireView().findViewById(R.id.spinner);
                spinner.setAdapter(adapter);

                Log.d(TAG, "Spinner configurado exitosamente");
            }
        }
    }
}
