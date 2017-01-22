package com.islasf.samaelmario.vista;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import model.Constantes;
import model.Contacto;

public class ListaContactosActivity extends AppCompatActivity {

    private  FloatingActionButton fabAgregarContacto;
    private ListView lvListaContactos;
    boolean lista_editable;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1 ;
    private ArrayList<Contacto> contactos;
    private ArrayList<Contacto> contactos_seleccionados;
    private  int COLOR_SELECCION,COLOR_DESELECCION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Cargamos los datos recibidos de la actividad llamante
        cargar_datos_intent();

        //Cargamos los componentes
        cargar_componentes();

    }

    /**
     * Método encargado de cargar los datos recibidos de la actividad que invoca a ésta. Mediante
     * el método getIntent(), se obtiene la lista de contactos ya cargados, y la lista de los
     * contactos seleccionados en el anterior acceso.
     */
    private void cargar_datos_intent(){
        Intent intent_recibido = getIntent();

        contactos = (ArrayList<Contacto>) intent_recibido.getSerializableExtra(Constantes.LISTADO_CONTACTOS_CARGADOS);
        contactos_seleccionados = (ArrayList<Contacto>) intent_recibido.getSerializableExtra(Constantes.LISTADO_CONTACTOS_SELECCIONADOS);
        lista_editable = intent_recibido.getBooleanExtra(Constantes.LISTA_EDITABLE,false);

    }


    private void aplicar_editable(){

        if(!lista_editable){//Si no es editable, la pantalla irá destinada a escoger un
            // contacto al que enviar un mensaje, por lo que se cambia el icono al de guardar.

            fabAgregarContacto.setImageResource(android.R.drawable.ic_menu_save);
        }
    }

    /**
     * Método que se ejecuta cuando se pulsa el botón flotante(FloatingActionButton fabAgregarContactos)
     * cuya finalidad es devolver los contactos seleccionados a la actividad llamante. Además devuelve
     * los contactos ya cargados
     * @param v
     */
    public void onFabGuardarContacto(View v){
        volver();
    }

    private void volver(){

        Intent intent_devuelto = new Intent();
        intent_devuelto.putExtra(Constantes.LISTADO_CONTACTOS_SELECCIONADOS,contactos_seleccionados);
        intent_devuelto.putExtra(Constantes.LISTADO_CONTACTOS_CARGADOS,contactos);
        setResult(Constantes.LISTA_CONTACTOS_ACTIVITY,intent_devuelto);

        finish();
    }

    private void cargar_componentes(){

        fabAgregarContacto = (FloatingActionButton) findViewById(R.id.fabAgregarContactos);
        aplicar_editable();
        COLOR_SELECCION = getResources().getColor(R.color.verde_claro,null);
        COLOR_DESELECCION = Color.parseColor("#FAFAFA");
        crearListaDeContactos();

    }

    private void seleccionar_contacto(View view, int posicion, int listener){

        LinearLayout layout;
        Contacto contacto_seleccionado = contactos.get(posicion);

        if(listener == 1){//onItemLongClickListener
            layout = (LinearLayout)view;

            if(contactos_seleccionados.contains(contacto_seleccionado)){
                contactos_seleccionados.remove(contacto_seleccionado);
                layout.setBackgroundColor(COLOR_DESELECCION);
                Toast.makeText(null,contacto_seleccionado + "", Toast.LENGTH_SHORT).show();
            }else{
                contactos_seleccionados.add(contacto_seleccionado);
                layout.setBackgroundColor(COLOR_SELECCION);
            }
        }else{//OnItemClickListener
            if(!contactos_seleccionados.isEmpty()){

                layout = (LinearLayout)view;

                if(contactos_seleccionados.contains(contacto_seleccionado)){
                    contactos_seleccionados.remove(contacto_seleccionado);
                    layout.setBackgroundColor(COLOR_DESELECCION);
                }else{
                    contactos_seleccionados.add(contacto_seleccionado);
                    layout.setBackgroundColor(COLOR_SELECCION);
                }
            }else{
                contactos_seleccionados.add(contacto_seleccionado);
                volver();
            }

        }

    }

    public void crearListaDeContactos(){

        lvListaContactos = (ListView) findViewById(R.id.lv_lista_contactos);// Volcamos en el atributo 'lista' el listView definido en el xml con su id

        ContactosAdapter adaptador = new ContactosAdapter(this, contactos); // Instanciamos un objeto denominado 'adapter' de tipo TitularAdapter que recibe el contexto en el que se crea y un ArrayList


        lvListaContactos.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvListaContactos.setAdapter(adaptador); // Rellenamos el ListView denominado 'lista' con el contenido del adaptador, que tendrá los valores del ArrayList

        lvListaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int posicion, long id) {
                seleccionar_contacto(view,posicion,0);
            }
        });
        lvListaContactos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int posicion, long id) {
                seleccionar_contacto(view,posicion,1);
                return true;
            }
        });

    }

    // --- PETICIÓN DE PERMISOS PARA ACCEDER A LA LISTA DE CONTACTOS DEL TELÉFONO --- //

    /**
     * Este método comprueba si la aplicación tiene permisos garantizados, si es así
     *
     */
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(lista_editable){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu,menu);
        }

        return super.onCreateOptionsMenu(menu);
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



    /**
     * Clase que hereda de ArrayAdapter de tipo Contacto, que está destinada a actuar como modelo
     * de datos de la listview o lista de contactos.
     *
     * Cada Item de la lista se infla sólo si no ha sido
     * inflado antes. De esta manera reducimos el uso del procesador, debido a que los items ya creados
     * anteriormente son guardados en memoria y mostrados mediante referencias, gracias al ViewHolder.
     */
    class ContactosAdapter extends ArrayAdapter<Contacto> {

        /**
         * Contexto de tipo Context empleado para poder inflar el layout personalizado a modo de item.
         */
        private Context contexto;

        /**
         * Lista de contactos que recibe el adaptador por el constructor para
         */
        private ArrayList<Contacto> contactos_adapter;

        /**
         * Constructor de la clase TitularAdapter. Recibe un contexto(donde se va a implementar) y un ArrayList(que contiene la información de los datos que se van a mostrar)
         * @param contexto Hace referencia al contexto en el cual se va a implementar el TitularAdapter
         * @param contactos_adapter Hace referencia al modelo de datos que recibe el TitularAdapter, que contiene la información que será mostrada en el ListView
         */
        public ContactosAdapter(Context contexto, ArrayList<Contacto> contactos_adapter) {


            super(contexto, -1, contactos_adapter); // Llamamos al constructor del padre y le pasamos el contexto que queremos y el ArrayList
            this.contactos_adapter = contactos_adapter;
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

                Toast.makeText(contexto, contactos_seleccionados + "", Toast.LENGTH_SHORT).show();
                Toast.makeText(contexto, contactos + "", Toast.LENGTH_SHORT).show();
                if(contactos_seleccionados.contains(contactos.get(posicion))){

                    item.setBackgroundColor(COLOR_SELECCION);
                }else{
                    item.setBackgroundColor(COLOR_DESELECCION);
                }

                holder = new ViewHolder();
                holder.nombre = (TextView)item.findViewById(R.id.tv_listView_Nombre);
                holder.datos = (TextView)item.findViewById(R.id.tv_listView_Datos);

                item.setTag(holder);
            }
            else{
                holder = (ViewHolder)item.getTag();
            }

            holder.nombre.setText(contactos_adapter.get(posicion).obtener_nombre() + " " + contactos_adapter.get(posicion).obtener_apellidos());
            holder.datos.setText(contactos_adapter.get(posicion).obtener_tfno_movil() + " | " + contactos_adapter.get(posicion).obtener_correo());

            return item;
        }

        public class ViewHolder {
            TextView nombre;
            TextView datos;
        }


    }


}



