package com.islasf.samaelmario.vista;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import model.AccesoDatos;
import model.Constantes;
import model.Contacto;

public class ListaContactosActivity extends AppCompatActivity implements FuncionalidadesComunes{

    private  FloatingActionButton fabAgregarContacto;
    private ListView lvListaContactos;
    boolean seleccion_multiple;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1 ;
    private ArrayList<Contacto> contactos;
    private ArrayList<Integer> contactos_seleccionados;
    private  int COLOR_SELECCION,COLOR_DESELECCION;
    private ArrayList<View> lista_views;
    private boolean alguno_seleccionado;
    private boolean lista_cargada;
    ContactosAdapter adaptador;
    AccesoDatos accesoDatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lista_views = new ArrayList<View>();
        accesoDatos = new AccesoDatos(this);
        //Cargamos los datos recibidos de la actividad llamante
        cargar_datos_intent();

        //Cargamos los componentes
        cargar_componentes();
        cargar_lista();

        seleccionar_contactos();


        if(contactos_seleccionados.size() > 0){
            alguno_seleccionado = true;
        }else{
            alguno_seleccionado = false;
        }




    }

    private void cargar_lista(){
        if(!lista_cargada){

            accesoDatos.ejecutar_carga_contactos(this);

        }else{
            crearListaDeContactos();
        }

    }

    private void seleccionar_contactos(){
        for(int i = 0 ; i < lista_views.size(); i++){
            lista_views.get(i).setBackgroundColor(COLOR_SELECCION);
        }
    }

    /**
     * Método encargado de cargar los datos recibidos de la actividad que invoca a ésta. Mediante
     * el método getIntent(), se obtiene la lista de contactos ya cargados, y la lista de los
     * contactos lista_cargada en el anterior acceso.
     */
    private void cargar_datos_intent(){

        Intent intent_recibido = getIntent();

        contactos = (ArrayList<Contacto>) intent_recibido.getSerializableExtra(Constantes.LISTADO_CONTACTOS_CARGADOS);
        contactos_seleccionados = (ArrayList<Integer>) intent_recibido.getSerializableExtra(Constantes.LISTADO_CONTACTOS_SELECCIONADOS);
        lista_cargada =  intent_recibido.getBooleanExtra(Constantes.LISTA_CARGADA,true);
        seleccion_multiple = intent_recibido.getBooleanExtra(Constantes.SELECCION_MULTIPLE,false);

    }



    /**
     * Método que se ejecuta cuando se pulsa el botón flotante(FloatingActionButton fabAgregarContactos)
     * cuya finalidad es devolver los contactos lista_cargada a la actividad llamante. Además devuelve
     * los contactos ya cargados
     * @param v
     */
    public void onFabGuardarContacto(View v){

        volver();
    }

    private void volver(){

        Intent intent_devuelto = new Intent();
        intent_devuelto.putExtra(Constantes.LISTADO_CONTACTOS_CARGADOS,contactos);
        intent_devuelto.putExtra(Constantes.LISTADO_CONTACTOS_SELECCIONADOS, contactos_seleccionados);
        setResult(Constantes.LISTA_CONTACTOS_ACTIVITY,intent_devuelto);

        if(seleccion_multiple){
            finish();
        }else if(contactos_seleccionados.size()<=1){
            finish();
        }else{
            Toast.makeText(this, "Por favor, selecicone un solo contacto.", Toast.LENGTH_SHORT).show();
        }

    }

    private void cargar_componentes(){

        fabAgregarContacto = (FloatingActionButton) findViewById(R.id.fabAgregarContactos);
        COLOR_SELECCION = Color.parseColor("#E0F2F1");
        COLOR_DESELECCION = Color.parseColor("#FAFAFA");
    }

    private void seleccionar_contacto(int codigo_listener,View v,Integer id_contacto){

        if(codigo_listener==0){//Si el código listener corresponde al onItemClickListener
            if(alguno_seleccionado == true){//Si ya hay contactos lista_cargada, se añaden a la selección
                if(contactos_seleccionados.contains(id_contacto)){//Si ya está en la lista de lista_cargada lo borra
                    contactos_seleccionados.remove(id_contacto);
                    v.setBackgroundColor(COLOR_DESELECCION);
                }else{//Si no está en la lista de lista_cargada, lo añade.
                    contactos_seleccionados.add(id_contacto);
                    v.setBackgroundColor(COLOR_SELECCION);
                }
            }else{//Si no hay contactos lista_cargada, se pasa un solo contacto a la actividad de envío.
                contactos_seleccionados.add(id_contacto);
                volver();
            }

        }else{//Si el código listener corresponde al onItemLongClickListener
            if(contactos_seleccionados.contains(id_contacto)){
                contactos_seleccionados.remove(id_contacto);
                v.setBackgroundColor(COLOR_DESELECCION);
            }else{
                contactos_seleccionados.add(id_contacto);
                v.setBackgroundColor(COLOR_SELECCION);
            }
        }
        if(contactos_seleccionados.size()==0){
            alguno_seleccionado = false;
        }else if(alguno_seleccionado != true){
            alguno_seleccionado = true;
        }
    }

    public void crearListaDeContactos(){
        lista_cargada = true;
        lvListaContactos = (ListView) findViewById(R.id.lv_lista_contactos);// Volcamos en el atributo 'lista' el listView definido en el xml con su id
        adaptador = new ContactosAdapter(this, contactos); // Instanciamos un objeto denominado 'adapter' de tipo TitularAdapter que recibe el contexto en el que se crea y un ArrayList

        lvListaContactos.setAdapter(adaptador);

        lvListaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int codigo_listener = 0;
                Contacto contacto = (Contacto) parent.getAdapter().getItem(position);
                seleccionar_contacto(codigo_listener,view,contacto.obtener_id());

            }
        });

        lvListaContactos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int codigo_listener;
                if(seleccion_multiple){
                    codigo_listener = 1;
                }else{
                    codigo_listener = 0;
                }
                Contacto contacto = (Contacto) parent.getAdapter().getItem(position);
                seleccionar_contacto(codigo_listener,view,contacto.obtener_id());
                return true;
            }
        });


    }

    @Override
    public void onBackPressed() {
        volver();
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

    @Override
    public void onAsyncTask(Object... objeto) {
        this.contactos = (ArrayList<Contacto>) objeto[0];
        crearListaDeContactos();
    }

    @Override
    public void onAlerta(Object... objeto) {

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

        // Contexto de tipo Context empleado para poder inflar el layout personalizado a modo de item.
        private Context contexto;

        // Lista de contactos que recibe el adaptador por el constructor para realizar el objeto ContactosAdapter
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
        public View getView(final int posicion, View convertView, ViewGroup parent) {

            View item = convertView;
            ViewHolder holder;

            // Siempre que exista algún layout que pueda ser reutilizado éste se va a recibir a través del parámetro convertView del método getView().
            // De esta forma, en los casos en que éste no sea null podremos obviar el trabajo de inflar el layout
            if (item == null) {

                LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                item = inflater.inflate(R.layout.layout_listview_listacontactos, null);

                holder = new ViewHolder();
                holder.nombre = (CheckedTextView) item.findViewById(R.id.tv_listView_Nombre);
                holder.datos = (TextView)item.findViewById(R.id.tv_listView_Datos);

                item.setTag(holder);
            }
            else{

                holder = (ViewHolder)item.getTag();

            }

            holder.nombre.setText(contactos_adapter.get(posicion).obtener_nombre() + " " + contactos_adapter.get(posicion).obtener_apellidos());
            holder.datos.setText(contactos_adapter.get(posicion).obtener_tfno_movil() + " | " + contactos_adapter.get(posicion).obtener_correo());

            if(contactos_seleccionados.contains(contactos_adapter.get(posicion).obtener_id())){
                item.setBackgroundColor(COLOR_SELECCION);
            }else{
                item.setBackgroundColor(COLOR_DESELECCION);
            }

            return item;
        }

        public class ViewHolder {
            CheckedTextView nombre;
            TextView datos;
        }
    }
}






