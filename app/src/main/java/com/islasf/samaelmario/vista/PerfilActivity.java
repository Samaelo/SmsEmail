package com.islasf.samaelmario.vista;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PerfilActivity extends AppCompatActivity {

    EditText etNombre, etApellidos, etTfnoFijo, etTfnoMovil, etCorreo, etFecha;
    private PerfilActivity actividad = this;
    private int[] fecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cargar_campos();




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabEditarContacto);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public void onEditar(View v){

    }

    private void desactivar_campos(){
        this.etNombre.setEnabled(false);
        this.etApellidos.setEnabled(false);
        this.etTfnoFijo.setEnabled(false);
        this.etTfnoMovil.setEnabled(false);
        this.etCorreo.setEnabled(false);
        this.etFecha.setEnabled(false);
    }
    private void activar_campos(){
        this.etNombre.setEnabled(true);
        this.etApellidos.setEnabled(true);
        this.etTfnoFijo.setEnabled(true);
        this.etTfnoMovil.setEnabled(true);
        this.etCorreo.setEnabled(true);
        this.etFecha.setEnabled(true);
    }

    private void cargar_campos(){
        this.etNombre = (EditText) findViewById(R.id.etNombre);
        this.etApellidos = (EditText) findViewById(R.id.etApellidos);
        this.etTfnoFijo = (EditText) findViewById(R.id.etNumFijo);
        this.etTfnoMovil = (EditText) findViewById(R.id.etNumMovil);
        this.etCorreo = (EditText) findViewById(R.id.etCorreo);
        this.etFecha = (EditText) findViewById(R.id.etFecha);

    }
    public void onFecha(View v){
        DialogoFecha dialogo = new DialogoFecha();
        dialogo.setActivity(this);
        dialogo.show(getFragmentManager(),"datePicker");
    }

    public void obtener_fecha(int[] fecha){
        this.fecha = fecha;

    }
}
