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

/**
 * Esta clase hace referencia a la interfaz de usuario en la cual el usuario va a poder enviar un E-mail. En ella el usuario va a poder seleccionar contactos de la lista de contactos del móvil,
 * que son cargados en un listView personalizado, pero también puede escribir los destinatarios uno a uno en un campo de texto que hace referencia a los destinatarios, en el cual se volcarán
 * los E-mails de aquellos destinatarios que se hayan seleccionado a partir del boton 'fabSeleccionarContacto'. La interfaz también cuenta con un campo de texto para el remitente, y un campo
 * de texto para escribir el E-mail. La interfaz también incorpora dos botones, uno rojo y uno verde, con los cuales puede proceder a cancelar el envío del E-mail o por el contrario a enviarlo.
 */
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

    /**
     * Este método crea la Activity EnvioEmailActivity. En él se cargan los componentes mediante el método 'cargarComponentes()', se carga el un intent que contiene la lista de los contactos
     * seleccionados ( en caso de que se haya seleccionado algún contacto de la lista) mediante el método 'cargar_intent()'. Se instancia un ArrayList de contactos seleccionados y un objeto de
     * la clase Permisos.
     * @param savedInstanceState Objeto de tipo Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_email);

        cargarComponentes();

        cargar_intent();
        contactos_cargados = false;
        fabSeleccionarContacto = (FloatingActionButton)findViewById(R.id.fabSeleccionarContacto); // Instanciamos el botón Fab para seleccionar un contacto
        contactos_seleccionados = new ArrayList<Integer>();

        permisos = new Permisos(this);

    }

      ///////////////////////////////////
     //      Carga de elementos      //
    //////////////////////////////////

    /**
     * Carga los distintos componentes gráficos que se van a usar programáticamente a lo largo de la vida de la actividad, así como llama al método 'cargarActionBar()'. Tiene como finalidad realizar el menor número de accesos a los recursos
     * de la aplicación  para el menor consumo de batería. Se invoca en el onCreate de la actividad.
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
     * Carga el ActionBar en la Toolbar del menú. Se usa en el método 'cargarComponentes()'.
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
     * Devuelve un valor booleano para crear el los iconos del menú. Crea el menú de la ToolBar, el cual contiene el icono de Emails, bien para acceder al envío de un E-mail o a mostrar la lista
     * de los mismos, y el icono de SMS's bien para acceder al envío de un SMS o a mostrar la lista de los mismos. Se gestiona a través del método 'onCreateOptionsMenu()' de la clase GestorMenus
     * @param menu Objeto de la clase Menu
     * @return Retorna true para crear el menú
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        gestorMenus.onCreateOptionsMenu(menu);
        return true;
    }

    /**
     * Retorna un valor de tipo booleano, que pasa a true cuando un item del menú es pulsado. Este método da funcionalidad a los botones del Menú. Para ello se llama al método onOptionsItemSelected,
     * de la clase GestorMenus, al cual se le pasa un objeto item de la clase MenuItem.
     * @return Retorna un booleano que pasa a
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        gestorMenus.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    /**
     * Este método sirve para recoger la lista de contactos que han sido seleccionados en la Actividad "ListaContactos".
     */
    private void cargar_intent(){
        Intent intent = getIntent();
        this.lista_contactos = (ArrayList<Contacto>) intent.getSerializableExtra(Constantes.LISTADO_CONTACTOS_CARGADOS);
    }

    /**
     * Este método consiste en enviar un E-mail. Para ello, se recogen los textos que contienen los campos en los cuales el usuario va a introducir los datos del remitente, el/os destinatario/s,
     * el asundo del E-mail, y el texto del mismo. Primero se comprueban que los campos son correctos, y si son correctos se procede a llamar al método 'enviar_Email()' de la clase EnvioMensajes.     * @param vista Objeto de la clase View que hace referencia al botón que va a ser pulsado cuando queramos enviar un E-mail.
     * Si alguno de los campos de los E-mails no es correcto, se indica al usuario mediante un Dialog que alguno de los E-mails introducidos no son correctos.
     */
    public void onEnviarMail(View vista){

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

    /**
     * Este método consiste en indicar a la clase AccesoDatos que debe guardar los datos de un E-mail que ha sido ennviado. Para ello se instancia un objeto de la clase AccesoDatos,
     * mediante el cual, invocando al método 'insertar()', y pasándole los datos correspondientes al E-mail, que son: remitente, destinatarios, asunto y texto, será el responsable de insertar
     * los datos en la base de datos.
     */
    private void insertar_mensaje(){
        accesoDatos = new AccesoDatos(this);
        accesoDatos.insertar(ccRemitente, toDestinatarios, asunto, textoMail);
    }

    /**
     * Este método muestra un diálogo para darle al usuario la oportunidad de aceptar o rechazar una propuesta. Para ello, se instancia un objeto de la clase DialogoAlerta, y mediante el método
     * 'setDialogo()' le pasamos la actividad, un mensaje, un título, el array de Opciones, y la opción elegida. Posteriormente se muestra el diálogo mediante el método 'show()'.
     * @param opcion Variable de tipo Integer que hace referencia a la opción que elige el usuario cuando le sale el diálogo
     * @param opciones Variable de tipo Array de String que contiene la cantidad de opciones que se muestran al usuario en el diálogo
     * @param mensaje Variable de tipo String que muestra el mensaje correspondiente que se debe mostrar al usuario
     * @param titulo Variable de tipo String que muestra el título del mensaje correspondiente que se debe mostrar al usuario
     * @return Devuelve un objeto de tipo DialogoAlerta
     */
    public DialogoAlerta mostrar_dialogo(int opcion, String[] opciones, String mensaje, String titulo){

        DialogoAlerta dialogo = new DialogoAlerta();
        dialogo.setDialogo(this,mensaje, titulo, opciones, opcion);
        dialogo.show(getFragmentManager(),"dialogo_asunto");
        return dialogo;
    }

    /**
     * Método sobrescrito de la Interfaz FuncionalidadesComunes. En esta clase este método está vacío, porque no realiza ninguna función. Simplemente está declarado porque es necesario implementar
     * todos los métodos de la interfaz.
     * @param objeto - Parámetro que puede recibir de 0 a varios objetos de tipo Object.
     */
    @Override
    public void onAsyncTask(Object... objeto){
    }

    /**
     * Este método manda un Alerta al usuario en caso de que el E-mail que vaya a enviar no tenga asunto. En la alerta aparecen dos opciones: aceptar y cancelar. Si el usuario pulsa cancelar,
     * sale un Snackbar informando de que el E-mail no ha sido enviado porque no tenía asunto, en caso contrario, si el usuario pulsa aceptar, el E-mail será enviado sin asunto.
     * @param objeto - Parámetro que puede recibir de 0 a varios objetos de tipo Object.
     */
    @Override
    public void onAlerta(Object... objeto){

        int opcion = (int) objeto[0];
        int boton_pulsado = (int) objeto[1];

        if(opcion == 2) {
            if(boton_pulsado == Constantes.ACEPTAR){

                this.asunto = "";

                if (envioMensajes.enviar_email(ccRemitente, toDestinatarios, asunto, textoMail) == false) {
                    Snackbar.make(fabEnviar, "El E-mail no se ha enviado porque no tenía asunto", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }else{
                    insertar_mensaje();
                }
            }
        }
    }

    /**
     * Este método consiste en recoger los E-Mails de los destinatarios. Para ello, primero se define el patrón que debe cumplir un E-mail. Si el campo de los destinatarios contiene el símbolo
     * "," procedemos a tokenizar por dicho símbolo para poder extraer los E-mails. Una vez que tenemos todos los tokens individualizados (cada token hace referencia a un e-mail), procedemos
     * a guardarlos en un array de String. Si el e-mail no contiene el símbolo "," se da por hecho que dicho campo sólo contiene un e-mail
     * @param textoDestinatarios Variable de tipo Array de String que contiene el texto del campo Destinatarios del E-mail
     * @return Retorna un Array de String que contiene cada uno de los destinatarios a los cuales se desea enviar el E-mail
     * @throws Exception Se genera cuando se intenta verificar el patrón de un E-mail y éste no cumple dicho patrón.
     */
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

    /**
     * Método que finaliza la Actividad y vuelve a la Activity de la que fue llamado.
     * @param fabCancelar Objeto de tipo View que hace referencia al objeto que va a provocar que la Activity finalice, en este caso se hace referencia al boton fabCandelar
     */
    public void onCancelar(View fabCancelar){
        finish();
    }

    /**
     * Este método se ejecuta cuando se pulsa el botón fab_SeleccionarContacto, e invoca al método 'iniciar_lista_contactos()'
     * @param fab_SeleccionarContacto Objeto te tipo View que hace referencia al objeto que va a provocar que se llame al método de la clase 'niciar_lista_contactos()'.
     *                                En este caso se hace referencia al boton fabSeleccionarContacto.
     */
    public void onSeleccionar_Contacto(View fab_SeleccionarContacto){
        if(!permisos.verificarPermisos_Contactos()){
            Toast.makeText(this, "Para poder seleccionar contactos desde este botón, debes activar los permisos de acceso a los contactos en las preferencias.", Toast.LENGTH_SHORT).show();

        }
        else
            // Mediante el icono de la agenda de contactos, iremos a la actividad que contiene la lista de contactos
            iniciar_lista_contactos();
    }

    /**
     * Este método utiliza un objeto de la clase Intent para pasar a la clase ListaContactosActivity. Al pasar a la actividad, mediante el método putExtra se pasan los siguientes datos de la
     * clase Constantes: LISTA_CARGADA,contactos_cargados, LISTADO_CONTACTOS_SELECCIONADOS, contactos_seleccionados, LISTADO_CONTACTOS_CARGADOS,lista_contactos, SELECCION_MULTIPLE,
     * LISTA_CONTACTOS_ACTIVITY.
     */
    private void iniciar_lista_contactos(){

        Intent intent = new Intent(this, ListaContactosActivity.class);

        intent.putExtra(Constantes.LISTA_CARGADA,contactos_cargados);
        intent.putExtra(Constantes.LISTADO_CONTACTOS_SELECCIONADOS, contactos_seleccionados);
        intent.putExtra(Constantes.LISTADO_CONTACTOS_CARGADOS,lista_contactos);
        intent.putExtra(Constantes.SELECCION_MULTIPLE,true);
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