package com.islasf.samaelmario.vista;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import model.AccesoDatos;
import model.Constantes;
import model.Contacto;
import model.EnvioMensajes;

/**
 * Esta clase hace referencia a la interfaz de usuario en la cual el usuario va a poder enviar un SMS. En ella el usuario va a poder seleccionar contactos de la lista de contactos del móvil,
 * que son cargados en un listView personalizado, pero también puede escribir el destinatario un campo de texto que hace referencia al destinatario del SMS, en el cual se volcarán
 * el número de aquel destinatario que se hayan seleccionado a partir del boton 'fabSeleccionarContacto'. La interfaz también cuenta un campo de texto para escribir el mensaje del SMS.
 * La interfaz también incorpora dos botones, uno rojo y uno verde, con los cuales puede proceder a cancelar el envío del E-mail o por el contrario a enviarlo.
 */
public class EnvioSMSActivity extends AppCompatActivity {

    private EditText etTextoMensaje, etDestinatarioSMS;
    private TextView txtContacto;
    private Contacto contacto_seleccionado;
    private EnvioMensajes envio;
    private GestorMenus gestorMenus = new GestorMenus(this);

    private ArrayList<Contacto> lista_contactos;
    private ArrayList<Integer> contactos_seleccionados = new ArrayList<Integer>();
    private boolean contactos_cargados;
    private Permisos permisos;

    /**
     * Este método sobrescrito de la clase Activity, se encarga de crear la Actividad. En él se cargan los componentes mediante un método llamado 'cargar_componentes()' y también se carga el
     * ActionBar, mediante un método propio de la clase denominado 'cargar_actionBar()'. Se instancia un objeto de la clase EnvioMensajes y otro de la clase Permisos, mediante los cuales
     * se gestionará el envío de los SMS y se comprobará si el usuario tiene permiso para enviar SMSes.
     * @param savedInstanceState Objeto de la clase Bundle que guarda el estado de la Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_sms);

        cargar_componentes();
        cargar_actionBar();

        envio = new EnvioMensajes(this);
        permisos = new Permisos(this);
        lista_contactos = new ArrayList<Contacto>();
        contactos_cargados = false;
    }

    /**
     * Este método se encarga de cargar la Toolbar en el Menú. Para ello se instancia un objeto de la clase Toolbar, recogiendo de los recursos de R el identificador de la toolbar a la cual
     * se quiere hacer referencia, y posteriormente mediante el método 'setSupportActionBar(toolbar)', al cual le pasamos el objeto instanciado, se aplica la toolbar al ActionBar.
     */
    private void cargar_actionBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbEnvioSMS);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("");
    }

    /**
     * Este método se encarga de cargar los componentes a través de los cuales el usuario va a poder interactuar con la aplicación. Para ello se obtienen las vistas de los elementos mediante
     * la identificación de estos que está declarada en R
     */
    private void cargar_componentes(){

        etTextoMensaje = (EditText) findViewById(R.id.etMensaje);
        etDestinatarioSMS = (EditText) findViewById(R.id.et_DestinatarioSMS);
        txtContacto = (TextView) findViewById(R.id.txtContacto);
    }

    //Métodos para los listeners de los botones

    /**
     * Método que hace referencia al listener que va a tener el boton fabEnviar de la clase EnvioSMS. En él se comprueba si alguno de los campos está vacío, y si es así, se envía un mensaje de
     * error al usuario informándole de que el error no ha podido ser enviado por algún error.
     * @param fabEnvioSMS Hace referencia a la vista del objeto (en este caso un botón) que va a ser el causante de que se produzca el envío del SMS
     */
    public void onEnviar(View fabEnvioSMS){

        String mensaje_resultado;
        String destinatario = etDestinatarioSMS.getText().toString();
        String textoSMS = etTextoMensaje.getText().toString();

        if(!etDestinatarioSMS.getText().toString().trim().equals("")){
            if(envio.enviar_SMS(textoSMS,destinatario)){
                mensaje_resultado = "El mensaje ha sido enviado con éxito.";
                insertar_mensaje(destinatario,textoSMS);
            }else{
                mensaje_resultado = "El mensaje no ha sido enviado por algún error.";
            }
            Toast.makeText(this,mensaje_resultado,Toast.LENGTH_LONG).show();
        }else if(etTextoMensaje.getText().toString().trim().equals("")){
            Snackbar.make(fabEnvioSMS, "Por favor, rellene el campo de texto del mensaje.", Snackbar.LENGTH_LONG)
                    .show();
        }else{
            Snackbar.make(fabEnvioSMS, "Por favor, seleccione un contacto o añada un número de teléfono de forma manual.", Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * Este método consiste en llamar al método 'insertar()' de la clase AccesoDatos, el cual se va a encargar de guardar dicho mensaje en la base de datos. Para ello, se instancia un objeto
     * de la clase AccesoDatos y mediante dicho método, se le pasa por parámetro el texto del destinatario y el texto propio del mensaje del SMS
     * @param destinatario Variable de tipo String que hace referencia al campo del destinatario al cual se le desea enviar un mensaje
     * @param textoSMS Variable de tipo String que hace referencia al campo del destinatario
     */
    private void insertar_mensaje(String destinatario, String textoSMS){

        AccesoDatos accesoDatos = new AccesoDatos(this); // Instanciamos un objeto de tipo AccesoDatos para poder guardar la información del mensaje en la base de datos
        accesoDatos.insertar(destinatario, textoSMS); // Guardamos el SMS con el destinatario y el contenido del mensaje
    }

    /**
     * Método que finaliza la Actividad y vuelve a la Activity de la que fue llamado.
     * @param fabCancelar Objeto de tipo View que hace referencia al objeto que va a provocar que la Activity finalice, en este caso se hace referencia al boton fabCandelar
     */
    public void onCancelar(View fabCancelar){
        finish();
    }

    public void onSeleccionar_contacto(View v){
        if(!permisos.verificarPermisos_Contactos()){
            Toast.makeText(this, "Para poder seleccionar contactos desde este botón, debes activar los permisos de acceso a los contactos en las preferencias.", Toast.LENGTH_LONG).show();

        }else{
            iniciar_listaContactos();
        }
    }

    /**
     * Este método utiliza un objeto de la clase Intent para pasar a la clase ListaContactosActivity. Al pasar a la actividad, mediante el método putExtra se pasan los siguientes datos de la
     * clase Constantes: LISTA_CARGADA,contactos_cargados, LISTADO_CONTACTOS_SELECCIONADOS, contactos_seleccionados, LISTADO_CONTACTOS_CARGADOS,lista_contactos, SELECCION_MULTIPLE,
     * LISTA_CONTACTOS_ACTIVITY.
     */
    private void iniciar_listaContactos(){
        Intent intent = new Intent(this, ListaContactosActivity.class);

        intent.putExtra(Constantes.LISTA_CARGADA,contactos_cargados);
        intent.putExtra(Constantes.LISTADO_CONTACTOS_SELECCIONADOS, contactos_seleccionados);
        intent.putExtra(Constantes.LISTADO_CONTACTOS_CARGADOS,lista_contactos);
        intent.putExtra(Constantes.SELECCION_MULTIPLE,false);
        startActivityForResult(intent, Constantes.LISTA_CONTACTOS_ACTIVITY);
    }

    /**
     * Método sobrescrito de la clase Activity, que se produce cuando se devuelve un resultado de otra actividad. En este caso, primero leemos los datos del intent que recibimos de la actividad
     * llamdada. Para ello, recogemos los datos en un ArrayList<Contacto> que hace referencia a los contactos cargados, un ArrayList<Integer> que hace referencia a las Id's de los
     * contactos seleccionados,y una variable de tipo booleana que hace referencia a que los contactos ya han sido cargados para no volver a cargarlos.
     * @param requestCode Variable de tipo Int que hace referencia al código que manda la actividad llamante
     * @param resultCode Variable de tipo Int que hace referencia al código que devuelve la actividad que ha sido llamada
     * @param data Objeto de tipo Intent que contiene los datos que devuelve la Activity que ha sido llamada
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Leemos los datos del intent recibido de la actividad llamada.
        if(resultCode== Constantes.LISTA_CONTACTOS_ACTIVITY){

            lista_contactos = (ArrayList<Contacto>) data.getSerializableExtra(Constantes.LISTADO_CONTACTOS_CARGADOS);
            contactos_seleccionados = (ArrayList<Integer>) data.getSerializableExtra(Constantes.LISTADO_CONTACTOS_SELECCIONADOS);
            contactos_cargados = true;
            contacto_seleccionado = lista_contactos.get(0);

        /* Una vez seleccionamos el contacto de la lista de contactos, sustituimos el text view "Selecciona un contacto pulsando el icono de la parte superior", por el nombre del contacto
           que hemos elegido, y su número de teléfono */

            String txt = "";

            for(int i = 0; i< contactos_seleccionados.size(); i++){
                if(!lista_contactos.get(contactos_seleccionados.get(i)).obtener_tfno_movil().trim().equals("")) {
                    etDestinatarioSMS.setText(lista_contactos.get(contactos_seleccionados.get(i)).obtener_tfno_movil());

                    if (i != 0) {
                        txt = txt + " , " + lista_contactos.get(contactos_seleccionados.get(i)).obtener_nombre() + " " + lista_contactos.get(contactos_seleccionados.get(i)).obtener_apellidos();
                    } else {
                        txt += lista_contactos.get(contactos_seleccionados.get(i)).obtener_nombre() + " " + lista_contactos.get(contactos_seleccionados.get(i)).obtener_apellidos();
                    }
                    if (txt.length() >= 45) {
                        txt += "...";
                        i = contactos_seleccionados.size();
                    }
                }
            }
            txtContacto.setText(txt);
        }
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
}
