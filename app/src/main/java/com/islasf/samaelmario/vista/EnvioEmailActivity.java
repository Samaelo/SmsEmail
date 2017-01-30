package com.islasf.samaelmario.vista;

import java.lang.Exception;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

import model.AccesoDatos;
import model.Constantes;
import model.Contacto;
import model.EnvioMensajes;

public class EnvioEmailActivity extends AppCompatActivity implements FuncionalidadesComunes {

    private GestorMenus gestorMenus = new GestorMenus(this);
    private ArrayList<Contacto> lista_contactos;
    private ArrayList<Integer> contactos_seleccionados;
    private EnvioMensajes envioMensajes;
    private EditText et_Remitente, et_Destinatarios, et_Asunto, et_TextoEmail;
    private TextView tv_ContactoSuperior;
    private FloatingActionButton fabEnviar,fabSeleccionarContacto;

    private String [] toDestinatarios;
    private String ccRemitente, textoMail, asunto;
    private AccesoDatos accesoDatos;

    private boolean contactos_cargados;
    private Permisos permisos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_email);

        cargarComponentes();

        cargar_intent();
        contactos_cargados = false;
        FloatingActionButton fab_seleccionar_contactos = (FloatingActionButton)findViewById(R.id.fabSeleccionarContacto); // Instanciamos el botón Fab para seleccionar un contacto
        contactos_seleccionados = new ArrayList<Integer>();

        permisos = new Permisos(this);

    }

      ///////////////////////////////////
     //      Carga de elementos      //
    //////////////////////////////////

    /**
     *Método encargado de vaciar los campos de texto de la actividad.
     */
    public void limpiarTextos(){

        et_Remitente.setText("");
        et_Destinatarios.setText("");
    }

    /**
     * Carga los distintos componentes gráficos que se van a usar programáticamente a lo largo de
     * la vida de la actividad. Tiene como finalidad realizar el menor número de accesos a los
     * recursos de la aplicación  para el menor consumo de batería.
     *
     * Se invoca en el onCreate de la actividad.
     */
    public void cargarComponentes(){

        cargarActionBar();

        et_Remitente = (EditText)findViewById(R.id.et_Remitente);
        et_Destinatarios = (EditText)findViewById(R.id.et_Destinatarios);
        et_Asunto = (EditText)findViewById(R.id.et_Asunto);
        et_TextoEmail = (EditText)findViewById(R.id.et_TextoEmail);
        tv_ContactoSuperior = (TextView)findViewById(R.id.tv_ContactoSuperior);
        fabEnviar = (FloatingActionButton) findViewById(R.id.fabEnviarMail);
        fabSeleccionarContacto = (FloatingActionButton) findViewById(R.id.fabSeleccionarContacto);

        accesoDatos = new AccesoDatos(this);
    }

    /**
     *Método
     */
    public void cargarActionBar(){

        Toolbar toolbarEmail = (Toolbar)findViewById(R.id.tbEnvioEmail);

        setSupportActionBar(toolbarEmail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        toolbarEmail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    private void cargar_intent(){
        Intent intent = getIntent();
        this.lista_contactos = (ArrayList<Contacto>) intent.getSerializableExtra(Constantes.LISTADO_CONTACTOS_CARGADOS);
    }

    public void onEnviarMail(View v){

        envioMensajes = new EnvioMensajes(this);

        ccRemitente = new String(); // Creamos el String ccRemitente, cogiendo el texto del EditText del Remitente
        ccRemitente =  et_Remitente.getText().toString();
        textoMail = et_TextoEmail.getText().toString();
        asunto = et_Asunto.getText().toString();

        boolean camposCorrectos = comprobarCampos();

        if(camposCorrectos == true){

            try {
                toDestinatarios = recogerEmails(et_Destinatarios.getText().toString());

                String[] opciones =  {"Aceptar", "Cancelar"};
                if(asunto.toString().trim().isEmpty()){

                    mostrar_dialogo(2,opciones,"¿ Deseas enviar el mensaje sin asunto ?", "E-mail sin asunto");
                }else{
                    if(envioMensajes.enviar_email(ccRemitente, toDestinatarios, asunto, textoMail) == false){
                        Snackbar.make(fabEnviar, "Error al enviar el E-mail", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }else{
                        insertar_mensaje();
                    }
                }
            }
            catch (Exception e) {
                String[] opciones =  {"Aceptar"};
                mostrar_dialogo(1,opciones,"Alguna de las direcciones de correo no cumple un patrón válido. Por favor, revise los E-mails.", "E-mail no enviado");
            }


        }
    }

    private void insertar_mensaje(){
        accesoDatos = new AccesoDatos(this);
        accesoDatos.insertar(ccRemitente, toDestinatarios, asunto, textoMail);
    }

    public DialogoAlerta mostrar_dialogo(int opcion,String[] opciones, String mensaje, String titulo){
        DialogoAlerta dialogo = new DialogoAlerta();
        dialogo.setDialogo(this,mensaje, titulo, opciones, opcion);
        dialogo.show(getFragmentManager(),"dialogo_asunto");
        return dialogo;
    }

    @Override
    public void onAsyncTask(Object... objeto){
    }

    @Override
    public void onAlerta(Object... objeto){

        int opcion = (int) objeto[0];
        int boton_pulsado = (int) objeto[1];

        if(opcion == 2) {
            if(boton_pulsado == Constantes.ACEPTAR){
                this.asunto = "";
                if (envioMensajes.enviar_email(ccRemitente, toDestinatarios, asunto, textoMail) == false) {
                    Snackbar.make(fabEnviar, "Error al enviar el E-mail", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }else{
                    insertar_mensaje();
                }
            }else{

            }
        }
    }

    private String[] recogerEmails(String textoDestinatarios) throws Exception {

        String[] emails;

        String patronEmail = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$";

        if (textoDestinatarios.contains(",")) {

            StringTokenizer tokenizador = new StringTokenizer(textoDestinatarios, ",");

            int numeroEmails = tokenizador.countTokens(); // Contamos el número de Tokens que equivaldrá al número de emails y lo volcamos en la variable numeroEmails

            emails = new String[numeroEmails + contactos_seleccionados.size()]; // El array de Strings de los emails será igual al número de tokens

            /**
             * Rellenamos el array con los emails de los campos de texto.
             */
            // Mientras haya tokens después de un serparador por ","

            for (int i = 0; i < numeroEmails; i++) { // Para la longitud del array

                String email = tokenizador.nextToken();

                if (email.matches(patronEmail)) {

                    emails[i] = email; // La posición del email será igual a la posición del token
                } else throw new Exception();
            }

        } else if (textoDestinatarios.matches(patronEmail)) {

            emails = new String[1];
            emails[0] = textoDestinatarios;

        } else throw new Exception("Alguno de los emails no cumple un patrón correcto");

        /**
         * Rellenamos el array con los emails de los contactos que han sido seleccionados
         * en caso de que no haya habido un error.
         */
        for (int i = emails.length; i < contactos_seleccionados.size(); i++) {
            emails[i] = lista_contactos.get(contactos_seleccionados.get(i)).obtener_correo();

        }

        return emails;
    }



    public void onCancelar(View v){
        finish();
    }



    public void onSeleccionar_Contacto(View v){
        if(!permisos.verificarPermisos_Contactos()){
            Toast.makeText(this, "Para poder seleccionar contactos desde este botón, debes activar los permisos de acceso a los contactos en las preferencias.", Toast.LENGTH_SHORT).show();

        }
        else
            // Mediante el icono de la agenda de contactos, iremos a la actividad que contiene la lista de contactos
            iniciar_lista_contactos();
    }

    private void iniciar_lista_contactos(){

        Intent intent = new Intent(this, ListaContactosActivity.class);
        // this.lista_contactos = this.acceso.obtener_contactos();

        intent.putExtra(Constantes.LISTA_CARGADA,contactos_cargados);
        intent.putExtra(Constantes.LISTADO_CONTACTOS_SELECCIONADOS, contactos_seleccionados);
        intent.putExtra(Constantes.LISTADO_CONTACTOS_CARGADOS,lista_contactos);
        intent.putExtra(Constantes.SELECCION_MULTIPLE,true);
        startActivityForResult(intent, Constantes.LISTA_CONTACTOS_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Leemos los datos del intent recibido de la actividad llamada.
        if(resultCode==Constantes.LISTA_CONTACTOS_ACTIVITY){

            lista_contactos = (ArrayList<Contacto>) data.getSerializableExtra(Constantes.LISTADO_CONTACTOS_CARGADOS);
            contactos_seleccionados = (ArrayList<Integer>) data.getSerializableExtra(Constantes.LISTADO_CONTACTOS_SELECCIONADOS);
            contactos_cargados = true;

        /* Una vez seleccionamos el contacto de la lista de contactos, sustituimos el text view "Selecciona un contacto pulsando el icono de la parte superior", por el nombre del contacto
           que hemos elegido, y su número de teléfono */

            //String textoContacto = String.format(getResources().getString(R.string.contacto_seleccionado), contacto_seleccionado.obtener_nombre(), contacto_seleccionado.obtener_correo());

            String txt = "";

            for(int i = 0; i< contactos_seleccionados.size(); i++){
                if(!lista_contactos.get(contactos_seleccionados.get(i)).obtener_correo().trim().equals("")) {

                    if (!et_Destinatarios.getText().toString().contains(lista_contactos.get(contactos_seleccionados.get(i)).obtener_correo()))
                        et_Destinatarios.setText(et_Destinatarios.getText().toString() + "," + lista_contactos.get(contactos_seleccionados.get(i)).obtener_correo());

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
            tv_ContactoSuperior.setText(txt);
        }
    }

    /**
     * Este método comprueba que ni el Remitente ni el Destinatario están vacíos. Si alguno de los dos están vacíos se informa al usuario mediante un Toast, de que:
     * 1- Si el campo "remitente" está vacío debe introducir un remitente
     * 2- Si el campo "destinatarios" no tiene al menos un destinatario, debe introducir uno al menos.
     * 3- Si ambos campos están vacíos, debe introducir un remitente y al menos un destinatario
     */
    public boolean comprobarCampos(){

        String remitenteVacio = String.format(getResources().getString(R.string.remitenteVacio));
        String destinatariosVacio = String.format(getResources().getString(R.string.destinatariosVacio));
        String remitenteDestinatarioVacios = String.format(getResources().getString(R.string.remitenteDestinatariosVacio));

        if(et_Remitente.getText().toString().trim().isEmpty() && et_Destinatarios.getText().toString().trim().isEmpty()){
            Toast.makeText(this, remitenteDestinatarioVacios, Toast.LENGTH_LONG).show();
            return false;
        }
        else if(et_Remitente.getText().toString().trim().isEmpty()){
            Toast.makeText(this, remitenteVacio, Toast.LENGTH_LONG).show();
            return false;
        }
        else if(et_Destinatarios.getText().toString().trim().isEmpty()){
            Toast.makeText(this, destinatariosVacio, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}