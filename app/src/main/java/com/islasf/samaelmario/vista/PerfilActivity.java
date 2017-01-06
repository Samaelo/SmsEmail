package com.islasf.samaelmario.vista;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

import model.Perfil;

public class PerfilActivity extends AppCompatActivity {

    private EditText etNombre, etApellidos, etTfnoFijo, etTfnoMovil, etCorreo, etFecha;
    private FloatingActionButton fabEditarContacto;

    private boolean edicion = false,editado=false;
    private Perfil perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Cargamos los componentes
        cargar_componentes();

        //ESTE PERFIL SE RECIBE MEDIANTE UNA BÚSQUEDA EN LA BBDD EN RELACIÓN AL PERFIL SELECCIONADO
        perfil = new Perfil();


    }
    public void onEditar(View v){
        if(edicion == true){
            activar_edicion(false);
        }else{
            activar_edicion(true);
        }


        Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void cambiar_iconoBoton(boolean activado){
        if(activado != true){
            fabEditarContacto.setImageResource(android.R.drawable.ic_menu_edit);
        }else{
            fabEditarContacto.setImageResource(android.R.drawable.ic_menu_save);
        }

    }


    private void activar_edicion(boolean activado){
        this.etNombre.setEnabled(activado);
        this.etApellidos.setEnabled(activado);
        this.etTfnoFijo.setEnabled(activado);
        this.etTfnoMovil.setEnabled(activado);
        this.etCorreo.setEnabled(activado);
        this.etFecha.setEnabled(activado);
        edicion = activado;
        cambiar_iconoBoton(edicion);
    }

    private void cargar_componentes(){
        this.etNombre = (EditText) findViewById(R.id.etNombre);
        this.etApellidos = (EditText) findViewById(R.id.etApellidos);
        this.etTfnoFijo = (EditText) findViewById(R.id.etNumFijo);
        this.etTfnoMovil = (EditText) findViewById(R.id.etNumMovil);
        this.etCorreo = (EditText) findViewById(R.id.etCorreo);
        this.etFecha = (EditText) findViewById(R.id.etFecha);
        this.fabEditarContacto = (FloatingActionButton) findViewById(R.id.fabEditarContacto);
    }
    public void onFecha(View v){
        DialogoFecha dialogo = new DialogoFecha();
        dialogo.setActivity(this);
        dialogo.show(getFragmentManager(),"datePicker");
    }

    public void establecer_fecha(Calendar fecha){
        this.perfil.establecer_fecha(fecha);
        this.etFecha.setText(fechaToString(fecha));
    }
    public String fechaToString(Calendar fecha){
        return fecha.get(Calendar.DAY_OF_YEAR) + "/" + fecha.get(Calendar.MONTH) + "/" + fecha.get(Calendar.YEAR);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (editado == true && edicion == false) {
            //Si se han llegado a editar los datos y se ha guardado al menos una vez, se procede a
            //comprobar si  los datos son iguales a los obtenidos antes.. De lo contrario,
            // no se ha llegado a hacer una modificación, los datos del perfil siguen como están.

        }
    }

    private void recoger_datos(){
        perfil.establecer_perfil(etNombre.getText().toString(),etApellidos.getText().toString(),
                etTfnoFijo.getText().toString(),etTfnoMovil.getText().toString(),etCorreo.getText().toString(),new Date());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);

    }
}
