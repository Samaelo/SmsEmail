package model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mario on 07/01/2017.
 */

public class ContactosSQLiteHelper extends SQLiteOpenHelper {

    private final String CTABLE = "create table Contactos(_id int not null AUTO_INCREMENT, nombre varchar(15) not null, constraint pk_id PRIMARY KEY (_id))";


    public ContactosSQLiteHelper(Context contexto, String nombreBD, SQLiteDatabase.CursorFactory cursor, int version){
        super(contexto, nombreBD, cursor, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CTABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Contactos");

        //Se crea la nueva versión de la tabla
        db.execSQL(CTABLE);
    }
}
