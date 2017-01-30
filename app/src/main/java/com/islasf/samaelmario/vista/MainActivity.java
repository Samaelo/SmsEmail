package com.islasf.samaelmario.vista;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import model.Constantes;
import model.Contacto;

public class MainActivity extends AppCompatActivity implements  FuncionalidadesComunes{
/*
TODO: los XML de Strings siempre ir haciéndolos conforme avanzan las activities.
TODO: Puesto que hay que poner preferencias, vamos a poner que una de ellas sea poder modificar el tema. Por lo que hay que modificar también el XML de colores para crear los nuestros propios( al menos 4 temas en total).
TODO: Lista de contactos agregados.
TODO: Lista de Mensajes enviados.
TODO: Actividades: Lista de contactos, lista de mensajes, preferencias (con preferences screen) , envío de mail, mostrar perfil, editar perfil, historial, agregar contacto, Acerca de
TODO: Comprobar perfil anterior con el siguiente en algún método de la clase PerfiLActivity.
TODO: Añadir botón de refresh para volver a cargar contactos.
TODO: Comprobación de que si el array de contactos del intent del startactivity está vacío, que los cargue de partida.
TODO: Operación de cargar contactos en AsyncTask
TODO: Operación de cargar mensajes en AsyncTask
TODO: No mostrar usuarios que tengan mail no válido( en la lista de elección de contactos de envío de email)
TODO: No mostrar usuarios que tengan un teléfono no válido (en la lista de elección de contactos de envio de SMS)
TODO: Indicar en al how-to que hemos hecho el campo de direcciones de mail multivalor por comodidad, que somos conscientes de que habría que crear una tabla, pero lo importante de ésto es aprender android.
TODO:
*/

    private GestorMenus gestorMenus = new GestorMenus(this);
    private ArrayList<Contacto> contactos;
    private Permisos permisos;
    private boolean permisos_contactos = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cargarActionBar();
        permisos = new Permisos(this);

        solicitarPermisos_Contactos();

    }

    public void cargarActionBar(){

        Toolbar toolbarEmail = (Toolbar)findViewById(R.id.tbMain);
        setSupportActionBar(toolbarEmail);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("");
    }

    /**
     * Crea el menú de la ToolBar, que contiene el icono para acceder a la lista de Emails y el icono para acceder a la lista de SMS's
     * @param menu Objeto de la clase Menu
     * @return Retorna true si se puede crear el menú
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        gestorMenus.onCreateOptionsMenu(menu);
        return true;
    }

    // --- Método para dar funcionalidad a los botones del Menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        gestorMenus.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onAlerta(Object... objeto) {
        int tipo_dialogo = (int) objeto[0];
        int boton_presionado = (int) objeto[1];
        if(tipo_dialogo == Constantes.DIALOGO_DOS_OPCIONES){
            if(boton_presionado == Constantes.ACEPTAR){
               solicitarPermisos_Contactos();
            }else{
                permisos_contactos = false;
                Toast.makeText(this, "No podrás escoger contactos a la hora de enviar mensajes.", Toast.LENGTH_LONG).show();
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String permisos;
        boolean mostrar_rationale;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (requestCode) {
                case Constantes.REQUEST_PERMISOS_CONTACTOS:
                    for (int i = 0; i < permissions.length; i++) {
                        permisos = permissions[i];
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            mostrar_rationale = shouldShowRequestPermissionRationale(permisos);

                            if(!mostrar_rationale){ //El usuario ha hecho "check" en  la opción "No mostrar de nuevo".
                                Toast.makeText(this, "No podrás escoger contactos a la hora de enviar mensajes.", Toast.LENGTH_LONG).show();
                                //Desactivar funcionalidades
                            }else{
                                mostrar_rationale_Contactos();
                            }
                        }
                    }
                    break;
            }
        }
    }


    @Override
    public void onAsyncTask(Object... objeto) {

       this.contactos = (ArrayList<Contacto>) objeto[0];

       Intent intent = new Intent(this,EnvioEmailActivity.class);
       intent.putExtra(Constantes.LISTADO_CONTACTOS_CARGADOS,this.contactos);
       startActivity(intent);
    }

    private void solicitarPermisos_Contactos(){
        if(!permisos.verificarPermisos_Contactos()){
            permisos.solicitarPermisos_Contactos();
        }

    }

    private void mostrar_rationale_Contactos(){
        String[] opciones = {"Intentar de nuevo","No permitir"};
        permisos.mostrar_RationaleContactos(this,opciones);
   }
}
