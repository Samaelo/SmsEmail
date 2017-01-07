package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Mario on 07/01/2017.
 */

public class AccesoDatos {


    private SQLiteDatabase db;

    private final String NOMBREBD="Agenda";
    private final String TABLE="Contactos";
    private ContactosSQLiteHelper contactosHelper;
    public AccesoDatos(Context c){
        contactosHelper = new ContactosSQLiteHelper(c,NOMBREBD,null,1);
        db = contactosHelper.getWritableDatabase();
    }

    /**
     * Método encargado de insertar contactos.
     * @param nombreContacto - El nombre del contacto a añadir.
     */
    public boolean insertar(String nombreContacto){
        ContentValues fila = new ContentValues();

        //La id es autoincrement
        fila.put("nombre",nombreContacto);//La id no se introduce porque es autoincrement.

        if (db.insert(TABLE,null,fila) > -1){
            return true;
        }
        return false;
    }

    /**
     * Método encargado de insertar contactos.
     * @param nombreContacto - El nombre del contacto a añadir.
     * @return  - Devuelve verdadero si ha sido borrado
     */
    public boolean eliminar(String nombreContacto){

        String parametros[] = {nombreContacto};

        //Podemos omitir el where poniendo null. Se borrarían todos los registros.
        if (db.delete(TABLE,"nombre = ?" , parametros) == 0){
            return false;
        }

        return true;
    }

    public boolean actualizar(String nombreContacto){

        String parametros[] = {nombreContacto};

        ContentValues fila = new ContentValues();
        fila.put("nombre",nombreContacto);

        //Podemos omitir el where poniendo null. Se borrarían todos los registros.

        if (db.update(TABLE, fila, "nombre = ?" , parametros) == 0){
            return false;
        }

        return true;
    }

    public Cursor ejecutar_select(int _id){
        Cursor  cursor;
        String[] columnas = {"_id","nombre"};
        String where =  columnas[0] + " =?";
        String[] parametros ={_id+""};

        cursor = db.query(TABLE, columnas, where,parametros,null,null,null);

        return cursor;
    }
    public Cursor ejecutar_select(String nombre){
        Cursor  cursor;
        String[] columnas = {"_id","nombre"};
        String where =  columnas[1] + " =?";
        String[] parametros ={nombre};

        cursor = db.query(TABLE, columnas, where,parametros,null,null,null);

        return cursor;
    }
    public Cursor ejecutar_select_tabla(){
        Cursor  cursor;
        String[] columnas = {"_id","nombre"};
        String where =  columnas[1] + " =?";


        cursor = db.query(TABLE, columnas, null,null,null,null,null);

        return cursor;
    }

}
