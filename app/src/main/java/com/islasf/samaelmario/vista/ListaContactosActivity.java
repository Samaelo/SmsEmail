package com.islasf.samaelmario.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.islasf.samaelmario.vista.R;

public class ListaContactosActivity extends AppCompatActivity {

    private  FloatingActionButton fabAgregarContacto;

    boolean lista_editable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Cargamos los componentes
        cargar_componentes();

        //Obtenemos los datos del intent
        obtener_datos_intent();
    }

    private void aplicar_editable(){
        if(lista_editable == false){//Si no es editable, la pantalla irá destinada a escoger un
            // contacto al que enviar un mensaje, por lo que se cambia el icono al de guardar.

            fabAgregarContacto.setImageResource(android.R.drawable.ic_menu_save);
        }
    }

    private void obtener_datos_intent(){
        Intent intent = getIntent();
        lista_editable = intent.getBooleanExtra("EDITABLE",false);


    }
    private void cargar_componentes(){
        fabAgregarContacto = (FloatingActionButton) findViewById(R.id.fabAgregarContactos);
    }
}
