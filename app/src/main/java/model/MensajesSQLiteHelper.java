package model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;

/**
 * Clase destinada a la creación y actualización de la base de datos que permite un acceso
 * sencillo a la misma.
 */

public class MensajesSQLiteHelper extends SQLiteOpenHelper {

    // Creamos los Strings de creación y eliminación de las tablas que hacen referencia al contenido de los SMS's y de los Emails
    /**
     * Atributo de tipo String que contiene la sentenica SQL a ejecutar para crear la tabla de SMS.
     */
    private String createTableMensajesSMS = "create table Mensajes_sms(_id INTEGER PRIMARY KEY autoincrement, Destinatario varchar(200) not null, Texto varchar(200) not null, Fecha_de_Envio datetime not null)";

    /**
     * Atributo de tipo String que contiene la sentencia SQL a ejecutar para crear la tabla de emails.
     */
    private String createTableEmails = "create table Mensajes_Email(_id INTEGER PRIMARY KEY autoincrement, Remitente varchar(20) not null, Destinatarios varchar(200) not null, Asunto varchar(50) not null, " +
                                       "Texto varchar(200) not null, Fecha_de_Envio datetime unique not null)";

    /**
     * Atributo de tipo String que contiene la sentencia SQL a ejecutar para eliminar la tabla de SMS.
     */
    private String borrarTablaSMSs = "DROP TABLE IF EXISTS Mensajes_SMS";

    /**
     * Atributo de tipo String que contiene la sentencia SQL a ejecutar para eliminar la tabla de emails.
     */
    private String borrarTablaEmails = "DROP TABLE IF EXISTS Mensajes_Email";

    /**
     * Método constructor que es empleado para dar valor a los atributos contexto,nombreBD,cursor y versión.
     * @param contexto
     * @param nombreBD
     * @param cursor
     * @param version
     */
    public MensajesSQLiteHelper(Context contexto, String nombreBD, SQLiteDatabase.CursorFactory cursor, int version){
        super(contexto, nombreBD, cursor, version);

    }

    /**
     * Este método ejecuta las sentencias sql para crear la tabla donde se almacenarán los mensajes, tanto de los SMS como de los E-mails.
     * @param db Objeto de la clase SQLiteDatabase que hace referencia a la base de datos con la cual se está trabajando
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(createTableMensajesSMS);
            db.execSQL(createTableEmails);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Este método realiza una actualización de las tablas en las cuales se almacenan los datos de los SMS y de los Emails. Para ello, primero se borran las tablas y se vuelven a crear.
     * @param db Objeto de la clase SQLiteDatabase que hace referencia a la base de datos con la cual se está trabajando
     * @param oldVersion Variable de tipo entero que hace referencia a la versión antigua de la tabla
     * @param newVersion Variable de tipo entero que hace referencia a la nueva versión que va a adquirir dla tabla actualizada
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Se eliminan las versiones anteriores de las tablas
           db.execSQL(borrarTablaSMSs);
           db.execSQL(borrarTablaEmails);

        // Se crean las nuevas versiones de las tablas
           db.execSQL(createTableMensajesSMS);
           db.execSQL(createTableEmails);
    }
}
