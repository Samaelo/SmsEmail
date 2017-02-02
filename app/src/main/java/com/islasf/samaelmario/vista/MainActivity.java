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

/**
 * Es el launcher de la aplicación. Hace referencia a la pantalla principal de la aplicación, es decir, la interfaz con la cual el usuario va a interactuar al iniciar la aplicación.
 */
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
    private boolean permisos_sms = true;

    /**
     * Método sobrescrito de la clase Activity, en la cual se crea la actividad. En este método, se la ActionBar mediante el método 'cargarActionBar()'. También se instancia un objeto de tipo
     * Permisos, al cual se le pasa por parámetro la Actividad (en este caso la Activity Main). Así mismo, al tratarse del inicio de la aplicación, si ésta carece de permisos de lectura de contactos
     * de los contactos propios de la base de datos donde Android los guarda, o no tien permisos para enviar un SMS, en ésta pantalla principal se solicitan dichos permisos.
     * Para ello se utilizan los métodos 'solicitarPermisos_Contactos()' y 'solicitarPermisos_Sms()'.
     *
     * @param savedInstanceState Objeto de la clase Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cargarActionBar();
        permisos = new Permisos(this);

       solicitarPermisos_Contactos();


    }

    /**
     * Este método se encarga de crear la ToolBar en la cual van a estar los iconos de la aplicación. Para ello instanciamos un objeto de tipo Toolbar y le aplicamos el toolbar que tenemos definido
     * como recurso en R.
     */
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
    /**
     * Este método sirve para dar funcionalidad a los botones del Menú. Para ello usamos un objeto de la clase GestorMenus, que tratará dicho evento mediante el método
     * 'onOptionsItemSelected(item)', el cual recibe por parámetro el ítem que ha sido pulsado.
     * @param item Objeto de tipo MenuItem que hace referencia al item del Menú
     * @return Retorna true
     */
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

    /**
     *
     * @param requestCode int: El código de solicitud aprobada en requestPermissions
     * @param permissions String: Los permisos solicitados. Nunca puede ser nulo.
     * @param grantResults int: Los resultados de subvención para los permisos correspondientes que haya sido obtenida PERMISSION_GRANTED o PERMISSION_DENIED. Nunca puede ser nulo.
     */
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

                                permisos_contactos = false;
                            }else{
                                mostrar_rationale_Contactos();
                            }
                        }
                    }

                    break;
                case Constantes.REQUEST_PERMISOS_SMS:
                    for (int i = 0; i < permissions.length; i++) {
                        permisos = permissions[i];
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            mostrar_rationale = shouldShowRequestPermissionRationale(permisos);

                            if(!mostrar_rationale){ //El usuario ha hecho "check" en  la opción "No mostrar de nuevo".
                                Toast.makeText(this, "No podrás enviar mensajes de texto.", Toast.LENGTH_LONG).show();

                                permisos_contactos = false;
                            }else{
                                mostrar_rationale_Contactos();
                            }
                        }
                    }
                    break;
            }
        }
    }

    /**
     * Instancia el ArrayList propio de la clase, con el objeto que recibe por parámetro (que se devuelve con el método onPostExecute() de la actividad en la cual se realiza el proceso en segundo
     * plano.
     * @param objeto - Parámetro que puede recibir de 0 a varios objetos de tipo Object.
     */
    @Override
    public void onAsyncTask(Object... objeto) {

       this.contactos = (ArrayList<Contacto>) objeto[0];

       Intent intent = new Intent(this,EnvioEmailActivity.class);
       intent.putExtra(Constantes.LISTADO_CONTACTOS_CARGADOS,this.contactos);
       startActivity(intent);
    }

    /**
     * Verifica si se tienen permisos para leer los contactos y si no se tienen, llama al método 'solicitarPermisos_SMS()' de la clase Permisos
     */
    private void solicitarPermisos_Contactos(){
        if(!permisos.verificarPermisos_Contactos()){
            permisos.solicitarPermisos_Contactos();
        }

    }

    /**
     * Contiene un Array de Strings que hace referencia a las opciones que va a contener el Diálogo en el cual se muestra al usuario cuando se le ofrece activar los permisos.
     * Se llama al método 'mostrar_RationaleContactos()'
     */
    private void mostrar_rationale_Contactos(){
        String[] opciones = {"Intentar de nuevo","No permitir"};
        permisos.mostrar_RationaleContactos(this,opciones);
   }

    /**
     * Verifica si se tienen permisos para enviar SMSes y si no se tienen, llama al método 'solicitarPermisos_SMS()' de la clase Permisos
     */
    private void solicitarPermisos_Sms(){
        if(!permisos.verificarPermisos_SMS()){
            permisos.solicitarPermisos_SMS();
        }
    }

    /**
     * Contiene un Array de Strings que hace referencia a las opciones que va a contener el Diálogo en el cual se muestra al usuario cuando se le ofrece activar los permisos.
     * Se llama al método 'mostrar_RationaleSMS()'
     */
    private void mostrar_rationale_SMS(){
        String[] opciones = {"Intentar de nuevo","No permitir"};
        permisos.mostrar_RationaleSMS(this,opciones);
    }
}
