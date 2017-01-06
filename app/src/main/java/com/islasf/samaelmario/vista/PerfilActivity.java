package com.islasf.samaelmario.vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.islasf.samaelmario.vista.R;

/**
 * Clase destinada a la visualización la información de personas: tanto contactos como perfiles propios.
 */
public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
    }
}
