package com.islasf.samaelmario.vista;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import model.Contacto;
import model.Perfil;

public class ListaContactosActivity extends AppCompatActivity {

    private  FloatingActionButton fabAgregarContacto;
    ListView lvListaContactos;
    boolean lista_editable;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1 ;
    ArrayList<Contacto> contactos = new ArrayList<Contacto>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_lista_contactos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Cargamos los componentes
        cargar_componentes();

        ContactosAdapter listadoDeContactos = new ContactosAdapter(this, cargarAgenda());
    }

    private void aplicar_editable(){

        if(lista_editable == false){//Si no es editable, la pantalla irá destinada a escoger un
            // contacto al que enviar un mensaje, por lo que se cambia el icono al de guardar.

            fabAgregarContacto.setImageResource(android.R.drawable.ic_menu_save);
        }
    }

    private void cargar_componentes(){
        fabAgregarContacto = (FloatingActionButton) findViewById(R.id.fabAgregarContactos);
        crearListaDeContactos();
    }

    private ArrayList<Contacto> cargarAgenda(){

        ArrayList<Contacto> lista_contactos = new ArrayList<Contacto>();
        String ID_contacto;
        String nombre, apellidos = "", telefonos, telefonoFijo = "",  telefonoMovil = "", email = "",  fecha_contacto="";
        String[] splitNombre;
        Date fecha_nacimiento = null;

        Cursor posicionContacto = this.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);

        while(posicionContacto.moveToNext()){

            ID_contacto = posicionContacto.getString(posicionContacto.getColumnIndex(ContactsContract.Contacts._ID));
            apellidos = posicionContacto.getString(posicionContacto.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_ALTERNATIVE));
            telefonos = posicionContacto.getString(posicionContacto.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            // -- TRATAR FECHA DE NACIMIENTO -- //

             // 1º Obtenemos la fecha de nacimiento a través de la constante START_DATE que es de tipo String

            Cursor cursor_fecha = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, null, null, null);
            if (cursor_fecha.getCount() > 0) {
                while (cursor_fecha.moveToNext()) {

                }
            }
            cursor_fecha.close();

            // Para obtener la fecha de nacimiento, debemos hacer un SimpleDateFormat
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/mm/yyyy"); // 2º Con la clase SimpleDateFormat creamos el formato de la fecha que deseemos, en nuestro caso -> dia/mes/año

            if(!fecha_contacto.matches("dd/MM/yyyy")) {

                fecha_nacimiento = null;
            }

            else{

                try {
                    fecha_nacimiento = formatoDeFecha.parse(fecha_contacto);
                }
                catch (ParseException e) {
                    /* Como siempre se va a comprobar primero si la fecha que obtenemos de la constante START_DATE es correcto, esta excepción nunca se va a generar, ya que el compilador sólo entraría en
                       esta sección, si intentase parsear algo que no fuese del formato ' dd/MM/yyyy ' */
                }
            }

            if(Integer.parseInt(telefonos) == 1){

                Cursor cursor_Telefono = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "='"+ ID_contacto +"'",null, null);

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

            Cursor cursor_Email = this.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,new String[]{ ContactsContract.CommonDataKinds.Email.DATA,
                    ContactsContract.CommonDataKinds.Email.TYPE},ContactsContract.CommonDataKinds.Email.CONTACT_ID + "='" + ID_contacto + "'", null, null);

            while(cursor_Email.moveToNext()){

                  email = cursor_Email.getString(cursor_Email.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));


                }
            cursor_Email.close();

            /* Añadimos un contacto a la lista de contactos. Para ello creamos un Contacto, cuyo Perfil que recibe en su constructor, está compuesto por los atributos que hemos obtenidos consultando los datos
               de dicho contacto en la agenda del teléfono */

            splitNombre = apellidos.split(",");
            apellidos = splitNombre[0];
            nombre = splitNombre[1];

            lista_contactos.add(new Contacto(new Perfil(nombre,apellidos,telefonoFijo,telefonoMovil,email,fecha_nacimiento)));

            }

        posicionContacto.close();
        return lista_contactos;
    } // Fin de cargarAgenda()

    public void crearListaDeContactos(){

        lvListaContactos = (ListView) findViewById(R.id.lv_lista_contactos);// Volcamos en el atributo 'lista' el listView definido en el xml con su id

        ArrayList<Contacto> listado = cargarAgenda(); // Creamos un ArrayList denominado 'listado' de tipo Titular (hace referencia a la clase 'Titular'

        ContactosAdapter adaptador = new ContactosAdapter(this,listado); // Instanciamos un objeto denominado 'adapter' de tipo TitularAdapter que recibe el contexto en el que se crea y un ArrayList

        lvListaContactos.setAdapter(adaptador); // Rellenamos el ListView denominado 'lista' con el contenido del adaptador, que tendrá los valores del ArrayList

        lvListaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() { // Acción para cuando pulsamos algún ítem del TextView
            public void onItemClick(AdapterView adapter, View view, int position, long id) {


            }

        });

    }

    // --- PETICIÓN DE PERMISOS PARA ACCEDER A LA LISTA DE CONTACTOS DEL TELÉFONO --- //

    public void requestPermissions(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)){
                /* Aquí se mostrará la explicación al usuario de porqué es necesario el uso de un determinado permiso, pudiéndose mostrar de manera asíncrona, o lo que es lo mismo, desde un hilo secundario,
                sin bloquear el hilo principal, y a la espera de que el usuario concede el permiso necesario tras visualizar la explicación.*/
            }
            else{
                /* Se realiza la petición del permiso. En este caso permisos para leer los contactos.*/
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // Si el resultado es 'cancelar', los arrays resultantes están vacíos.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
                return;
            }
        }
    }
}

class ContactosAdapter extends ArrayAdapter<Contacto> {

    private Context contexto; // Atributo 'contexto' de tipo Context
    private ArrayList<Contacto> contactos; // Atributo 'titulares' de tipo ArrayList

    /**
     * Constructor de la clase TitularAdapter. Recibe un contexto(donde se va a implementar) y un ArrayList(que contiene la información de los datos que se van a mostrar)
     * @param contexto Hace referencia al contexto en el cual se va a implementar el TitularAdapter
     * @param contactos Hace referencia al modelo de datos que recibe el TitularAdapter, que contiene la información que será mostrada en el ListView
     */
    public ContactosAdapter(Context contexto, ArrayList<Contacto> contactos) {

        super(contexto, -1, contactos); // Llamamos al constructor del padre y le pasamos el contexto que queremos y el ArrayList
        this.contactos = contactos;
        this.contexto = contexto;
    }

    /**
     * Sobreescribimos el método getView para poder adaptar
     * @param posicion
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int posicion, View convertView, ViewGroup parent) {

        View item = convertView;
        ViewHolder holder;

        // Siempre que exista algún layout que pueda ser reutilizado éste se va a recibir a través del parámetro convertView del método getView().
        // De esta forma, en los casos en que éste no sea null podremos obviar el trabajo de inflar el layout
        if (item == null) {

            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            item = inflater.inflate(R.layout.layout_listview_listacontactos, null);
            holder = new ViewHolder();
            holder.nombre = (TextView)item.findViewById(R.id.tv_listView_Nombre);
            holder.datos = (TextView)item.findViewById(R.id.tv_listView_Datos);

            item.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)item.getTag();
        }

        holder.nombre.setText(contactos.get(posicion).obtener_nombre() + " " + contactos.get(posicion).obtener_apellidos());
        holder.datos.setText(contactos.get(posicion).obtener_tfno_movil() + " | " + contactos.get(posicion).obtener_correo());

        return item;
    }

    public class ViewHolder {

        TextView nombre;
        TextView datos;
    }
}



