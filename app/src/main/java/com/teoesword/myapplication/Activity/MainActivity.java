package com.teoesword.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.teoesword.myapplication.Fragment.RondaFragment;
import com.teoesword.myapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtén una referencia al botón en tu diseño
        Button rondaButton = findViewById(R.id.button);

        // Establece un listener para el clic del botón
        rondaButton.setOnClickListener(view -> {
            // Crea una instancia del nuevo fragmento
            RondaFragment rondaFragment = new RondaFragment();

            // Obtén el FragmentManager y realiza la transacción
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, rondaFragment)  // Reemplaza el contenido del activity
                    .addToBackStack(null)  // Agrega la transacción a la pila de retroceso
                    .commit();
        });

        // Agrega un OnBackStackChangedListener para manejar la visibilidad del botón
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            // Verifica si hay fragmentos en la pila de retroceso
            boolean hasFragments = getSupportFragmentManager().getBackStackEntryCount() > 0;

            // Muestra u oculta el botón según si hay fragmentos en la pila de retroceso
            rondaButton.setVisibility(hasFragments ? View.GONE : View.VISIBLE);
        });
    }


}
