package model;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.islasf.samaelmario.vista.DialogoAlerta;
import com.islasf.samaelmario.vista.FuncionalidadesComunes;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Mario on 07/01/2017.
 */

public class AccesoDatos {


    private SQLiteDatabase db;

    private final String NOMBREBD="Agenda";
    private final String TABLE="Contactos";
    private ContactosSQLiteHelper contactosHelper;
    private Context contexto;
    private ArrayList<Contacto> lista_contactos;

    private DialogoAlerta dialogo;
    private Activity actividad_dialogo;
    public AccesoDatos(Context contexto){
        this.contexto = contexto;
        contactosHelper = new ContactosSQLiteHelper(contexto,NOMBREBD,null,1);
       // db = contactosHelper.getWritableDatabase();
    }

    //
    // Bases de datos
    //

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


    //
    //  Acceso a los contactos
    //





    public void ejecutar_carga_contactos(DialogoAlerta dialogo, FuncionalidadesComunes funcionalidadesComunes){
        this.dialogo = dialogo;
        this.actividad_dialogo = (Activity)funcionalidadesComunes;

        lista_contactos = new ArrayList<Contacto>();
        CargaContactos carga = new CargaContactos();
        carga.execute(funcionalidadesComunes);

    }


    class CargaContactos extends AsyncTask<FuncionalidadesComunes,Void,ArrayList<Contacto>> {

        private FuncionalidadesComunes funcionalidades_recibidas;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(dialogo!=null){
                dialogo.show(actividad_dialogo.getFragmentManager(), "alerta_cargando_contactos");
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Contacto> contactos) {
            super.onPostExecute(contactos);

            funcionalidades_recibidas.onAsyncTask(contactos);

            if(dialogo!=null){
                dialogo.dismiss();
            }
        }

        @Override
        protected ArrayList<Contacto> doInBackground(FuncionalidadesComunes... params) {


            Activity actividad_llamante = (Activity) params[0];
            funcionalidades_recibidas = params[0];

            String ID_contacto;
            String nombre="", apellidos = "", telefonos, telefonoFijo = "",  telefonoMovil = "", email = "",  fecha_contacto="";
            String[] splitNombre;
            Date fecha_nacimiento = null;
            boolean salir=false;
            Cursor posicionContacto = actividad_dialogo.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);

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


                lista_contactos.add(new Contacto(new Perfil(nombre,apellidos,telefonoFijo,telefonoMovil,email,fecha_nacimiento)));
                nombre = ""; apellidos =""; telefonoFijo = ""; telefonoMovil = ""; email = ""; fecha_nacimiento=null;


            }

            posicionContacto.close();
            return lista_contactos;
        }
    }


}
