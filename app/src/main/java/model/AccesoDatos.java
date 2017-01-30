package model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.view.View;

import com.islasf.samaelmario.vista.DialogoAlerta;
import com.islasf.samaelmario.vista.FuncionalidadesComunes;
import com.islasf.samaelmario.vista.ListaContactosActivity;

/**
 * Created by Mario on 07/01/2017.
 */

public class AccesoDatos {

    private ArrayList<Contacto> lista_contactos;
    private ArrayList<SMS> lista_smses;
    private ArrayList<Email> lista_emails;

    private DialogoAlerta dialogo;
    private Activity actividad_dialogo;

    private final String NOMBRE_BD ="Mensajes";
    private final String TABLA_SMS ="Mensajes_SMS";
    private final String TABLA_EMAILS ="Mensajes_Email";
    private MensajesSQLiteHelper mensajesSQLiteHelper;
    private SQLiteDatabase base_datos;
    private Context contexto;

    ProgressDialog dialogoProgreso;


    public AccesoDatos(Context contexto){
        this.contexto = contexto;
        mensajesSQLiteHelper = new MensajesSQLiteHelper(contexto,NOMBRE_BD, null, 1);
        base_datos = mensajesSQLiteHelper.getWritableDatabase();
    }

    //
    // Bases de datos
    //

    /**
     * Método para guardar un mensaje de tipo SMS
     * @param destinatario Variable de tipo String que hace referencia al nombre del contacto al cual se desea enviar el SMS
     * @param textoSMS Variable de tipo String que hace referencia al contenido del mensaje del SMS
     */
    public void insertar(String destinatario, String textoSMS){
        ContentValues fila = new ContentValues();
        fila.put("Destinatario", destinatario);
        fila.put("Texto", textoSMS);
        fila.put("Fecha_de_Envio",getDateTime());
        base_datos.insert(TABLA_SMS, null, fila);
        base_datos.close();
    }

    /**
     * Método para guardar un mensaje de tipo E-mail
     * @param remitente Variabel de tipo String que hace referencia al nombre del remitente que desea enviar el E-Mail
     * @param destinatarios Variable de tipo Array (de tipo String) que hace referencia al conjunto de contactos a los cuales se desea enviar el E-mail
     * @param asunto Variable de tipo String que hace referencia al asunto del E-mail
     * @param textoEmail Variable de tipo String que hace referencia al contenido del mensaje del E-mail
     */
    public void insertar(String remitente, String[] destinatarios, String asunto, String textoEmail){
        String nombres_destinatarios = "";
        String[] destinatarios_a_guardar = {""};

        if(destinatarios != null){
               destinatarios_a_guardar = destinatarios;
        }

        for(int i = 0; i < destinatarios_a_guardar.length; i++){

            if(destinatarios_a_guardar.length == 1)
                nombres_destinatarios += destinatarios_a_guardar[i];
            else
                nombres_destinatarios += ", " + destinatarios_a_guardar[i];
        }

       base_datos.execSQL("insert into Mensajes_Email(Remitente,Destinatarios,Asunto,Texto,Fecha_de_Envio) values ('" + remitente + "','" + nombres_destinatarios + "','" + asunto + "','" + textoEmail +"','"
                         + getDateTime() + "')");
         base_datos.close();
    }

    private String getDateTime() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void eliminar_Mensaje(int indice, String tabla){

         String[] argumentos = {indice+""};
         base_datos.delete(tabla,"_id = ?" , argumentos);
    }

    /**
     * Sentencia que se ejecuta cuando queremos buscar los datos de la base de datos 'Mensajes', concretamente de la tabla Mensajes_Email
     */
    public Cursor select_buscar_Emails(){

        Cursor  cursor_Email;
        String[] columnas = {"_id","Remitente","Destinatarios","Asunto","Texto","Fecha_de_Envio"};

        cursor_Email = base_datos.query(TABLA_EMAILS, columnas, null,null,null,null,null);

        return cursor_Email;
    }

    /**
     * Sentencia que se ejecuta cuando queremos buscar los datos de la base de datos 'Mensajes', concretamente de la tabla Mensajes_Sms
     */
    public Cursor select_buscar_Sms(){

        Cursor cursor_SMS;
        String[] columnas = {"_id","Destinatario","Texto","Fecha_de_Envio"};

        cursor_SMS = base_datos.query(TABLA_SMS, columnas, null,null,null,null,null);

        return cursor_SMS;
    }


    // ------------ MÉTODOS ASYNCTASK ------------ //

    public void ejecutar_carga_smses(FuncionalidadesComunes funcionalidadesComunes){

        dialogoProgreso = new ProgressDialog(contexto);
        dialogoProgreso.setMessage("Cargando la lista de SMS...");
        dialogoProgreso.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialogoProgreso.setIndeterminate(false);
        dialogoProgreso.setProgress(0);
        this.actividad_dialogo = (Activity)funcionalidadesComunes;

        lista_smses = new ArrayList<SMS>();
        CargaSMS cargaSMS = new CargaSMS();
        cargaSMS.execute(funcionalidadesComunes);
    }

    //
    //  Acceso a los Emails
    //
    public void ejecutar_carga_emails(FuncionalidadesComunes funcionalidadesComunes){
        dialogoProgreso = new ProgressDialog(contexto);
        dialogoProgreso.setMessage("Cargando la lista de emails...");
        dialogoProgreso.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialogoProgreso.setIndeterminate(false);
        dialogoProgreso.setProgress(0);

        this.actividad_dialogo = (Activity)funcionalidadesComunes;

        lista_emails = new ArrayList<Email>();
        CargaEmails cargaEmails = new CargaEmails();
        cargaEmails.execute(funcionalidadesComunes);
    }

    //
    //  Acceso a los contactos
    //
    public void ejecutar_carga_contactos(FuncionalidadesComunes funcionalidadesComunes){
        dialogoProgreso = new ProgressDialog(contexto);
        dialogoProgreso.setMessage("Cargando contactos de la agenda...");
        dialogoProgreso.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialogoProgreso.setIndeterminate(false);
        dialogoProgreso.setProgress(0);
        dialogoProgreso.setSecondaryProgress(0);


        this.actividad_dialogo = (Activity)funcionalidadesComunes;

        lista_contactos = new ArrayList<Contacto>();
        CargaContactos cargaContactos = new CargaContactos();
        cargaContactos.execute(funcionalidadesComunes);
    }

    class CargaSMS extends AsyncTask<FuncionalidadesComunes,Void,ArrayList<SMS>> {
//
        private FuncionalidadesComunes funcionalidadesComunes;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(dialogo!=null){
                dialogo.show(actividad_dialogo.getFragmentManager(), "alerta_cargando_smses");
            }
        }

        @Override
        protected ArrayList<SMS> doInBackground(FuncionalidadesComunes... params) {
            this.funcionalidadesComunes =  params[0];
            return recoger_SMS();
        }

        @Override
        protected void onPostExecute(ArrayList<SMS> smses) {
            super.onPostExecute(smses);

            if(dialogo!=null){
                dialogo.dismiss();
            }
            funcionalidadesComunes.onAsyncTask(smses);
        }
    }

    class CargaEmails extends AsyncTask<FuncionalidadesComunes,Void,ArrayList<Email>> {

        private FuncionalidadesComunes funcionalidadesComunes;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(dialogo!=null){
                dialogo.show(actividad_dialogo.getFragmentManager(), "alerta_cargando_emails");
            }
        }

        @Override
        protected ArrayList<Email> doInBackground(FuncionalidadesComunes... params) {
            this.funcionalidadesComunes = params[0];
            return recoger_Emails();

        }

        @Override
        protected void onPostExecute(ArrayList<Email> emails) {
            super.onPostExecute(emails);

            if(dialogo!=null){
                dialogo.dismiss();
            }
            funcionalidadesComunes.onAsyncTask(emails);
        }
    }

    class CargaContactos extends AsyncTask<FuncionalidadesComunes,Integer,ArrayList<Contacto>> {

        FuncionalidadesComunes funcionalidadesComunes;
        int numeroContactos = 0,numContactos_total,aumento = numContactos_total/100;

        @Override
        protected void onPreExecute() {
           super.onPreExecute();

            dialogoProgreso.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            dialogoProgreso.setProgress(values[0].intValue());
        }

        @Override
        protected ArrayList<Contacto> doInBackground(FuncionalidadesComunes... params) {

            this.funcionalidadesComunes = params[0];

            return recogerContactos(funcionalidadesComunes);
        }

        @Override
        protected void onPostExecute(ArrayList<Contacto> contactos) {
           super.onPostExecute(contactos);
             if(dialogoProgreso!=null){
                 dialogoProgreso.dismiss();
             }

            funcionalidadesComunes.onAsyncTask(contactos);
        }

        /**
         *
         * @param params
         * @return
         */
    public ArrayList<Contacto> recogerContactos(FuncionalidadesComunes... params){

        Activity actividad_llamante = (Activity)params[0];
        String ID_contacto;
        String nombre = "", apellidos = "", telefonos, telefonoFijo = "",  telefonoMovil = "", email = "",  fecha_contacto = "";
        String[] splitNombre;
        Date fecha_nacimiento = null;
        boolean salir=false;
        Cursor posicionContacto = actividad_llamante.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
        numContactos_total = posicionContacto.getCount();

        dialogoProgreso.setMax(numContactos_total);
        dialogoProgreso.incrementProgressBy(1);

        dialogoProgreso.incrementSecondaryProgressBy(aumento);

        while(posicionContacto.moveToNext()){

            ID_contacto = posicionContacto.getString(posicionContacto.getColumnIndex(ContactsContract.Contacts._ID));
            apellidos = posicionContacto.getString(posicionContacto.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_ALTERNATIVE));
            telefonos = posicionContacto.getString(posicionContacto.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            Cursor cursor_fecha = actividad_llamante.getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, null, null, null);

            if (cursor_fecha.getCount() > 0) {
                while (cursor_fecha.moveToNext() && salir == false) {
                    fecha_contacto = cursor_fecha.getString(cursor_fecha.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
                    if(fecha_contacto!=null && fecha_contacto.matches("dd/MM/yyyy")){
                        salir = true;
                    }
                }
                cursor_fecha.close();
            }

            // Para obtener la fecha de nacimiento, debemos hacer un SimpleDateFormat
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/mm/yyyy"); // 2º Con la clase SimpleDateFormat creamos el formato de la fecha que deseemos, en nuestro caso -> dia/mes/año

            if(fecha_contacto!=null && !fecha_contacto.matches("dd/MM/yyyy")) {
                fecha_nacimiento = null;
            }

            else{
                try {
                    fecha_nacimiento = formatoDeFecha.parse(fecha_contacto);
                }
                catch (ParseException e) {
                    /* Como siempre se va a comprobar primero si la fecha que obtenemos de la constante START_DATE es correcto, esta excepción nunca se va a generar, ya que el compilador sólo entraría en
                       esta sección, si intentase parsear algo que no fuese del formato ' dd/MM/yyyy ' */
                }catch(Exception e){

                }
            }

            if(Integer.parseInt(telefonos) == 1){

                Cursor cursor_Telefono = actividad_llamante.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "='"+ ID_contacto +"'",null, null);

                while(cursor_Telefono.moveToNext()){

                    String tipo_numero = cursor_Telefono.getString(cursor_Telefono.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));

                    switch(Integer.parseInt(tipo_numero)){

                        // Insertamos switch cases para manejar todos los tipos de números de teléfono.

                        case ContactsContract.CommonDataKinds.Phone.TYPE_HOME :
                            telefonoFijo = cursor_Telefono.getString(cursor_Telefono.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            break;

                        case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE :
                            telefonoMovil = cursor_Telefono.getString(cursor_Telefono.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            break;

                        default:
                            telefonoFijo = cursor_Telefono.getString(cursor_Telefono.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            break;
                    }

                }
                cursor_Telefono.close();
            }
            else{
                telefonoFijo = "";
                telefonoMovil = "";
            }

            Cursor cursor_Email = actividad_llamante.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,new String[]{ ContactsContract.CommonDataKinds.Email.DATA,
                    ContactsContract.CommonDataKinds.Email.TYPE},ContactsContract.CommonDataKinds.Email.CONTACT_ID + "='" + ID_contacto + "'", null, null);

            while(cursor_Email.moveToNext()){

                email = cursor_Email.getString(cursor_Email.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));


            }
            cursor_Email.close();

            /* Añadimos un contacto a la lista de contactos. Para ello creamos un Contacto, cuyo Perfil que recibe en su constructor, está compuesto por los atributos que hemos obtenidos consultando los datos
               de dicho contacto en la agenda del teléfono */
            if(apellidos!=null){
                splitNombre = apellidos.split(",");
                if(splitNombre.length>1){
                    apellidos = splitNombre[0];
                    nombre = splitNombre[1];
                }
            }

            lista_contactos.add(new Contacto(numeroContactos,new Perfil(nombre,apellidos,telefonoFijo,telefonoMovil,email,fecha_nacimiento)));
            nombre = ""; apellidos =""; telefonoFijo = ""; telefonoMovil = ""; email = ""; fecha_nacimiento=null;

            numeroContactos ++;

            publishProgress(numeroContactos);


        }

        posicionContacto.close();
        return lista_contactos;
    }
    }

    /**
     * Este método recoge un objeto Cursor a partir del método 'select_buscar_Emails()'. Cuando obtenemos el cursor, lo recorremos, y a medida que lo recorremos, obtenemos los datos que contiene dicho cursor.
     * En este caso, contiene toda la información de los Emails, por lo que los recogemos en forma de String (que son los tipos de datos que recoge el objeto de tipo Email, excepto 2 datos: los destinatarios y la fecha).
     * Existen dos peculiaridades en este método:
     * 1- Uno de los parámetros que recibe un Email, es un "String[]", que hace referencia al número de destinatarios, por lo que es necesario recoger ese dato a través del método 'split()', que nos devuelve un
     * "String[]". Es necesario saber, que la posicion del dato de un Email que contiene la información de los destinatarios es la posición 2. Vease el ejemplo:
     * String[] destinatarios = cursorEmails.getString(2).split(",");
     * 2- El segundo parámetro peculiar que recibe un Email, es la fecha, que en este caso es un objeto de tipo Date. Para ello, es necesario establecer un formato de fecha mediante un objeto de la clase
     * SimpleDateFormat. En este caso, como el almacenamiento de Emails se hace mediante el método 'curTime()' que guarda la fecha en formato "yyyy/MM/dd H:mm:ss", debemos hacer lo siguiente:
     * SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy/MM/dd H:mm:ss");. Posteriormente, cuando recogemos la información de la fecha, debemos realizar un parseo del String que hemos recogido, sería así:
     * Date fecha_transformada = formatoDeFecha.parse(fecha_de_envio);
     * El método 'parse()' (que recordemos que recibe como parámetro un String que se supone que hace referencia a la fecha que hemos recogido del objeto Cursor), genera una excepción de tipo 'ParseException()', que es
     * tratada dentro del método. Cabe destacar, que esta excepción nunca se va a generar, ya que los Emails se guardan con el mismo formato siempre, por lo que a la hora de recoger la información de la fecha de los
     * Emails, el formato siempre va a ser "yyyy/MM/dd H:mm:ss", pero es necesario controlar la Excepción.
     * Finalmente instanciamos un objeto Email y le pasamos al constructor todos los datos(con el orden correcto) que hemos recogido(teniendo en cuenta el "String[]" que hace referencia a los destinatarios, y la fecha
     * transformada en tipo Datey añadimos dicho Email instanciado al ArrayList<Email> que debe devolver el método.
     * @return Retorna un ArrayList de tipo Email (ArrayList<Email>)
     */
    public ArrayList<Email> recoger_Emails(FuncionalidadesComunes... params){

        Cursor cursorEmails =  select_buscar_Emails(); // Obtenemos un cursor denominado 'cursorEmails' a partir del cursor que nos devuelve el método 'select_buscar_Emails()'
        ArrayList<Email> lista_de_Emails = new ArrayList<Email>(); // Iniciamos un ArrayList de Email

        /* Cuando recorremos el cursor de los Emails, lo que recogemos es un String, por tanto necesitamos establecer un formato de fecha para poder convertir el String fecha_de_envío, que hará referencia al
           método 'curTime()', a una variable de tipo Date; */
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy/MM/dd H:mm:ss");

        while(cursorEmails.moveToNext()){

            String remitente = cursorEmails.getString(1);
            String[] destinatarios ={cursorEmails.getString(2)}; /* Como guardamos los destinatarios separados por comas, para recogerlos, hacemos un split por el caracter "," y nos devuelve un Array de
                                                                              Strings con los nombres de todos los destinatarios */
            String asunto = cursorEmails.getString(3);
            String textoMail = cursorEmails.getString(4);
            String fecha_de_envio = cursorEmails.getString(5); // Recogemos la fecha de tipo Date, y con el método 'toString()' pasamos la fecha a tipo String

            Email email = new Email(remitente,destinatarios,asunto,textoMail,fecha_de_envio); // Instanciamos un nuevo Email con los datos del cursor iterado

            lista_de_Emails.add(email); // Lo añadimos al ArrayList de Emails
        }

        return lista_de_Emails;
    }

    /**
     * Este método recoge un objeto Cursor a partir del método 'select_buscar_Sms()'. Cuando obtenemos el cursor, lo recorremos, y a medida que lo recorremos, obtenemos los datos que contiene dicho cursor.
     * En este caso, contiene toda la información de los SMS's, por lo que los recogemos en forma de String (que son los tipos de datos que recoge el objeto de tipo SMS, excepto 1 dato: la fecha.
     * Existe una peculiaridad en este método:
     * Un parámetro peculiar que recibe un SMS, es la fecha, que en este caso es un objeto de tipo Date. Para ello, es necesario establecer un formato de fecha mediante un objeto de la clase
     * SimpleDateFormat. En este caso, como el almacenamiento de SMS's se hace mediante el método 'curTime()' que guarda la fecha en formato "yyyy/MM/dd H:mm:ss", debemos hacer lo siguiente:
     * SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy/MM/dd H:mm:ss");. Posteriormente, cuando recogemos la información de la fecha, debemos realizar un parseo del String que hemos recogido,
     * sería así: Date fecha_transformada = formatoDeFecha.parse(fecha_de_envio);
     * El método 'parse()' (que recordemos que recibe como parámetro un String que se supone que hace referencia a la fecha que hemos recogido del objeto Cursor), genera una excepción de tipo 'ParseException()',
     * que es tratada dentro del método. Cabe destacar, que esta excepción nunca se va a generar, ya que los SMS's se guardan con el mismo formato siempre, por lo que a la hora de recoger la información de la
     * fecha de los SMS's, el formato siempre va a ser "yyyy/MM/dd H:mm:ss", pero es necesario controlar la Excepción.
     * Finalmente instanciamos un objeto SMS y le pasamos al constructor todos los datos(con el orden correcto) que hemos recogido(teniendo en cuenta la fecha transformada en tipo Date y añadimos dicho
     * SMS instanciado al ArrayList<SMS> que debe devolver el método.
     * @return Retorna un ArrayList de tipo SMS (ArrayList<SMS>)
     */

    public ArrayList<SMS> recoger_SMS(){

        Cursor cursorSMS =  select_buscar_Sms(); // Obtenemos un cursor denominado 'cursorEmails' a partir del cursor que nos devuelve el método 'select_buscar_Emails()'
        ArrayList<SMS> lista_de_Sms = new ArrayList<SMS>(); // Iniciamos un ArrayList de Email

       /* Cuando recorremos el cursor de los Emails, lo que recogemos es un String, por tanto necesitamos establecer un formato de fecha para poder convertir el String fecha_de_envío, que hará referencia al
          método 'curTime()', a una variable de tipo Date; */
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy/MM/dd H:mm:ss");

        while(cursorSMS.moveToNext()){

            String destinatario = cursorSMS.getString(1);
            String textoSMS = cursorSMS.getString(2);
            String fecha_de_envio = cursorSMS.getString(3); // Recogemos la fecha de tipo Date, y con el método 'toString()' pasamos la fecha a tipo String

            SMS sms = new SMS(destinatario,textoSMS,fecha_de_envio); // Instanciamos un nuevo Email con los datos del cursor iterado

            lista_de_Sms.add(sms); // Lo añadimos al ArrayList de Emails
        }

        return lista_de_Sms;
    }
}
