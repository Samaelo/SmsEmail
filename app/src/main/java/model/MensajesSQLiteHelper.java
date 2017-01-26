package model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;

/**
 * Created by Mario on 07/01/2017.
 */

public class MensajesSQLiteHelper extends SQLiteOpenHelper {

    // Creamos los Strings de creación y eliminación de las tablas que hacen referencia al contenido de los SMS's y de los Emails

    private String createTableMensajesSMS = "create table Mensajes_sms(_id INTEGER PRIMARY KEY autoincrement, Destinatario varchar(200) not null, Texto varchar(200) not null, Fecha_de_Envio datetime not null)";
    private String createTableEmails = "create table Mensajes_Email(_id INTEGER PRIMARY KEY autoincrement, Remitente varchar(20) not null, Destinatarios varchar(200) not null, Asunto varchar(50) not null, " +
                                       "Texto varchar(200) not null, Fecha_de_Envio datetime unique not null)";

    private String borrarTablaSMSs = "DROP TABLE IF EXISTS Mensajes_SMS";
    private String borrarTablaEmails = "DROP TABLE IF EXISTS Mensajes_Email";

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
