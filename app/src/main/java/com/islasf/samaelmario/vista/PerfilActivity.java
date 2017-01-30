package com.islasf.samaelmario.vista;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import model.Constantes;
import model.Contacto;
import model.Perfil;
import model.Preferencias;

public class PerfilActivity extends AppCompatActivity {

    private GestorMenus gestorMenus;
    private EditText etNombre, etApellidos, etTfnoFijo, etTfnoMovil, etCorreo, etFecha;
    private FloatingActionButton fabEditarContacto;

    private boolean edicion = false,editable;
    private Contacto contacto_nuevo,contacto_antiguo;
    private Perfil perfil_nuevo;

    private String nombre, apellidos, tfnoFijo, tfnoMovil, correo, fecha;

    Date fecha_escogida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        perfil_nuevo = new Perfil();
        contacto_nuevo = new Contacto(-1, perfil_nuevo);
        fecha_escogida = new Date();


        //Cargamos el actionBar
        cargar_actionBar();
        //Cargamos los componentes
        cargar_componentes();

        //Cargamos el perfil
        cargar_perfil();

    }

    private void cargar_campos(){
        etNombre.setText(nombre);
        etApellidos.setText(apellidos);
        etTfnoFijo.setText(tfnoFijo);
        etTfnoMovil.setText(tfnoMovil);
        etCorreo.setText(correo);
        etFecha.setText(fecha);

    }

   private void cargar_perfil(){
       Preferencias preferencias = new Preferencias(getApplicationContext());
       SimpleDateFormat format;
       Date fecha_formateada = new Date();

       nombre = preferencias.obtener_cadena_generica(Constantes.PERFIL_NOMBRE);
       apellidos = preferencias.obtener_cadena_generica(Constantes.PERFIL_APELLIDOS);
       tfnoFijo = preferencias.obtener_cadena_generica(Constantes.PERFIL_TFNO_FIJO);
       tfnoMovil = preferencias.obtener_cadena_generica(Constantes.PERFIL_TFNO_MOVIL);
       fecha = preferencias.obtener_cadena_generica(Constantes.PERFIL_FECHA);
       correo = preferencias.obtener_cadena_generica(Constantes.PERFIL_CORREO);

       cargar_campos();

       format = new SimpleDateFormat("dd/MM/yyyy");
       try{
           fecha_formateada = format.parse(fecha);
       }catch(Exception e){

       }

       contacto_antiguo = new Contacto(-1,new Perfil());
       contacto_antiguo.obtener_perfil().establecer_perfil(nombre,apellidos,tfnoFijo,tfnoMovil,correo,fecha_formateada);


   }
    public void onEditar(View v){
        String mensaje;
        if(edicion == true){
            activar_edicion(false);
            mensaje = "Cambios guardados.";
        }else{
            activar_edicion(true);
            mensaje = "Edición habilitada.";
        }

        Snackbar.make(v, mensaje, Snackbar.LENGTH_SHORT)
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

    private void cargar_actionBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbPerfil);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
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
        dialogo.show(getFragmentManager(),"datePicker");//
    }

    public void establecer_fecha(Calendar fecha){
        fecha_escogida = fecha.getTime();
        this.perfil_nuevo.establecer_fecha(fecha);
        this.etFecha.setText(fechaToString(fecha));
    }

    public String fechaToString(Calendar fecha){
        return fecha.get(Calendar.DAY_OF_MONTH) + "/" + (fecha.get(Calendar.MONTH)+1) + "/" + fecha.get(Calendar.YEAR);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        salir();
    }
    private void salir(){
        if (comprobar_cambios() && !edicion) {//Se han detectado cambios
        Preferencias preferencias = new Preferencias(getApplicationContext());
            preferencias.establecer_cadena_generica(Constantes.PERFIL_NOMBRE,etNombre.getText().toString());
            preferencias.establecer_cadena_generica(Constantes.PERFIL_APELLIDOS,etApellidos.getText().toString());
            preferencias.establecer_cadena_generica(Constantes.PERFIL_TFNO_FIJO,etTfnoFijo.getText().toString());
            preferencias.establecer_cadena_generica(Constantes.PERFIL_TFNO_MOVIL,etTfnoMovil.getText().toString());
            preferencias.establecer_cadena_generica(Constantes.PERFIL_FECHA,etFecha.getText().toString());
            preferencias.establecer_cadena_generica(Constantes.PERFIL_CORREO,etCorreo.getText().toString());



        }
        //Finalizamos la activity
        finish();
    }
    private void recoger_datos(){
        contacto_nuevo.obtener_perfil().establecer_perfil(etNombre.getText().toString(),etApellidos.getText().toString(),
                etTfnoFijo.getText().toString(),etTfnoMovil.getText().toString(),etCorreo.getText().toString(),fecha_escogida);
    }

    /**
     * Método que comprueba si se ha modificado algún dato del contacto que aparece en pantalla.
     * @return - True ha detectado cambios o False si el perfil no ha sufrido modificaciones (mediante los campos de texto).
     */
    public boolean comprobar_cambios(){
        recoger_datos();
        if(contacto_nuevo.obtener_nombre().equals(contacto_antiguo.obtener_nombre())
                && contacto_nuevo.obtener_apellidos().equals(contacto_antiguo.obtener_apellidos())
                && contacto_nuevo.obtener_tfno_fijo().equals(contacto_antiguo.obtener_tfno_fijo())
                && contacto_nuevo.obtener_tfno_movil().equals(contacto_antiguo.obtener_tfno_movil())
                && contacto_nuevo.obtener_correo().equals(contacto_antiguo.obtener_correo())
                && contacto_nuevo.obtener_fecha().equals(contacto_antiguo.obtener_fecha())){
            return false;
        }else{
            return true;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);

    }
}