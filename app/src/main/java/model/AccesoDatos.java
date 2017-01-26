package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateTimePatternGenerator;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Mario on 07/01/2017.
 */

public class AccesoDatos {

    private SQLiteDatabase db;

    private final String NOMBRE_BD ="Mensajes";
    private final String TABLA_SMS ="Mensajes_SMS";
    private final String TABLA_EMAILS ="Mensajes_Email";
    private MensajesSQLiteHelper mensajesSQLiteHelper;
    private String cadenaFecha;
    private Context contexto;

    private SQLiteDatabase base_datos;

    public AccesoDatos(Context contexto){
        this.contexto = contexto;
        mensajesSQLiteHelper = new MensajesSQLiteHelper(contexto, NOMBRE_BD, null, 1);
        base_datos = mensajesSQLiteHelper.getWritableDatabase();

    }

    /**
     * Método para guardar un mensaje de tipo SMS
     * @param destinatario Variable de tipo String que hace referencia al nombre del contacto al cual se desea enviar el SMS
     * @param textoSMS Variable de tipo String que hace referencia al contenido del mensaje del SMS
     */
    public void insertar(String destinatario, String textoSMS){
        ContentValues fila = new ContentValues();
        fila.put("destinatario", destinatario);
        fila.put("textoSMS", textoSMS);

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

        ContentValues fila = new ContentValues();

        String nombres_destinatarios = "";

        for(int i = 0; i < destinatarios.length; i++){

            if(destinatarios.length == 1)
                nombres_destinatarios += destinatarios[i];
            else
                nombres_destinatarios += ", " + destinatarios[i];
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
    public ArrayList<Email> recoger_Emails(){

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
        ArrayList<SMS> lista_de_Sms = null; // Iniciamos un ArrayList de Email
        Date fecha_transformada = null; // Iniciamos a null la fecha que vamos a transformar

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
