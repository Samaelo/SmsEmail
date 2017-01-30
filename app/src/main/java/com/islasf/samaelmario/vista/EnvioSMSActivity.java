package com.islasf.samaelmario.vista;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
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
import model.Perfil;

public class EnvioSMSActivity extends AppCompatActivity {

    private EditText etTextoMensaje, etDestinatarioSMS;
    private TextView txtContacto;
    private Contacto contacto_seleccionado;
    private EnvioMensajes envio;
    private GestorMenus gestorMenus = new GestorMenus(this);

    private ArrayList<Contacto> lista_contactos;
    private ArrayList<Integer> contactos_seleccionados;
    private boolean contactos_cargados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_sms);
        //   Personalización del ActionBar   //
        cargar_componentes();
        cargar_actionBar();

        envio = new EnvioMensajes(this);
        lista_contactos = new ArrayList<Contacto>();
        contactos_cargados = false;

    }

    private void cargar_actionBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbEnvioSMS);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("");
    }

    private void cargar_componentes(){
        etTextoMensaje = (EditText) findViewById(R.id.etMensaje);
        etDestinatarioSMS = (EditText) findViewById(R.id.et_DestinatarioSMS);
        txtContacto = (TextView) findViewById(R.id.txtContacto);
    }

    //Métodos para los listeners de los botones

    public void onEnviar(View v){

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
            Snackbar.make(v, "Por favor, rellene el campo de texto del mensaje.", Snackbar.LENGTH_LONG)
                    .show();
        }else{
            Snackbar.make(v, "Por favor, seleccione un contacto o añada un número de teléfono de forma manual.", Snackbar.LENGTH_LONG)
                    .show();
        }


    }

    private void insertar_mensaje(String destinatario, String textoSMS){
        AccesoDatos accesoDatos = new AccesoDatos(this); // Instanciamos un objeto de tipo AccesoDatos para poder guardar la información del mensaje en la base de datos

        accesoDatos.insertar(destinatario, textoSMS); // Guardamos el SMS con el destinatario y el contenido del mensaje
    }

    public void onCancelar(View v){
        finish();
    }

    public void onSeleccionar_contacto(View v){
        iniciar_listaContactos();

    }

    private void iniciar_listaContactos(){
        Intent intent = new Intent(this, ListaContactosActivity.class);

        intent.putExtra(Constantes.LISTA_CARGADA,contactos_cargados);
        intent.putExtra(Constantes.LISTADO_CONTACTOS_SELECCIONADOS, contactos_seleccionados);
        intent.putExtra(Constantes.LISTADO_CONTACTOS_CARGADOS,lista_contactos);
        intent.putExtra(Constantes.SELECCION_MULTIPLE,false);
        startActivityForResult(intent, Constantes.LISTA_CONTACTOS_ACTIVITY);
    }

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

            //String textoContacto = String.format(getResources().getString(R.string.contacto_seleccionado), contacto_seleccionado.obtener_nombre(), contacto_seleccionado.obtener_correo());

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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        gestorMenus.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

}
