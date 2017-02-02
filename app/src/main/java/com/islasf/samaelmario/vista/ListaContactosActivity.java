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

/**
 * Esta clase hace referencia a la Interfaz donde se muestra la lista de contactos que se recogen de la base de datos del móvil y se cargan en un listview personalizado.
 */
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

    /**
     * Método sobrescrito de la clase Activity, en la cual se crea la actividad. En este método, se hace referencia a la Toolbar con la cual va a poder interactuar el usuario, recogiendo la id
     * de la misma de la clase R. También se cargan los componentes mediante el método 'cargar_componentes()', se cargan los datos del intent )que hace referencia a los datos de los contactos
     * en caso de que ya se haya seleccionado algún contacto y estén reflejados en la Activity de la cual es llamda esta actividad.
     *
     * @param savedInstanceState Objeto de la clase Bundle
     */
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

        if(contactos_seleccionados!=null){
            if(contactos_seleccionados.size() > 0){
                alguno_seleccionado = true;
            }else{
                alguno_seleccionado = false;
            }
        }
    }

    /**
     * Este método se encarga de cargar la lista de los contactos. Para ello se usa el método 'ejecutar_carga_contactos(Activity activity)' de la clase AccesoDatos, el cual recibe la actividad
     * de la cual ha sido llamado.
     */
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
     * @param fabGuardar Hace referencia al FloatingActionButton fabAgregarContactos
     */
    public void onFabGuardarContacto(View fabGuardar){

        volver();
    }

    /**
     * Método encargado de volver a la Actividad anterior de la que ha sido llamada.
     */
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

    /**
     * Método que carga los componentes de la Activity.
     */
    private void cargar_componentes(){

        fabAgregarContacto = (FloatingActionButton) findViewById(R.id.fabAgregarContactos);
        COLOR_SELECCION = Color.parseColor("#E0F2F1");
        COLOR_DESELECCION = Color.parseColor("#FAFAFA");
    }

    /**
     * Este método selecciona un contacto cuando el usuario interactúa con el ListView que contiene los contacots. En él se comprueba si el contacto está seleccionado. Si no lo está, se cambia el
     * color de dicho ítem del list view y se agrega a un Array de contactos.
     * @param codigo_listener
     * @param vista
     * @param id_contacto
     */
    private void seleccionar_contacto(int codigo_listener,View vista,Integer id_contacto){

        if(codigo_listener==0){//Si el código listener corresponde al onItemClickListener
            if(alguno_seleccionado == true){//Si ya hay contactos lista_cargada, se añaden a la selección
                if(contactos_seleccionados.contains(id_contacto)){//Si ya está en la lista de lista_cargada lo borra
                    contactos_seleccionados.remove(id_contacto);
                    vista.setBackgroundColor(COLOR_DESELECCION);
                }else{//Si no está en la lista de lista_cargada, lo añade.
                    contactos_seleccionados.add(id_contacto);
                    vista.setBackgroundColor(COLOR_SELECCION);
                }
            }else{//Si no hay contactos lista_cargada, se pasa un solo contacto a la actividad de envío.
                contactos_seleccionados.add(id_contacto);
                volver();
            }

        }else{//Si el código listener corresponde al onItemLongClickListener
            if(contactos_seleccionados.contains(id_contacto)){
                contactos_seleccionados.remove(id_contacto);
                vista.setBackgroundColor(COLOR_DESELECCION);
            }else{
                contactos_seleccionados.add(id_contacto);
                vista.setBackgroundColor(COLOR_SELECCION);
            }
        }
        if(contactos_seleccionados.size()==0){
            alguno_seleccionado = false;
        }else if(alguno_seleccionado != true){
            alguno_seleccionado = true;
        }
    }

    /**
     * Este método crea la lista de contactos. Para ello se crea un ListView recogiendo el id del ListView de R que hace referencia al ListView y de dicha Actividad.
     * Posteriormente, mediante un adaptador, al cual pasamos la Actividad en la que estamos y el Array de contactos (que recogemos mediante el método 'cargar_datos_intent()'. Este método tiene dos
     * opciones: 1.- Si se hace click en el ítem, se obtiene el contacto de la vista padre "parent" mediante el método getAdapter().getItem(posicion)" al cual le pasamos la posicion del item del listview pulsado.
     *              Después de haber obtenido el contacto, simplemente seleccionamos la posición equivalente a la id del mismo (así evitamos que se seleccione siempre el mismo item, debido al view holder).
     *
     * 2.- Dependiendo de la variable booleana "seleccion_multiple" (recibida al iniciar la actividad mediante un intent), se enviará el contacto de vuelta a la actividad de envío llamante (en caso de
     *      que ésta sea el envioSMS, puesto que sólo se puede seleccionar un contacto) o se seleccionará el contacto en el que se mantuvo pulsado el click para dar opción a seleccionar más contactos.
     */
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

    /**
     * Método sobrescrito cuando se pulsa la tecla atrás del móvil. Se invoca al método 'volver()' de la propia clase.
     */
    @Override
    public void onBackPressed() {
        volver();
    }

    // --- PETICIÓN DE PERMISOS PARA ACCEDER A LA LISTA DE CONTACTOS DEL TELÉFONO --- //

    /**
     * Este método comprueba si la aplicación tiene permisos garantizados. Rn caso de que no los tenga, se llama al método onRequestPermissionsResult.
     *
     */
    public void requestPermissions(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)){
            }
            else{
                /* Se realiza la petición del permiso. En este caso permisos para leer los contactos.*/
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }

    /**
     * Devolución de llamada para el resultado de solicitar los permisos. Este método se invoca para cada llamada en el método 'requestPermissions()'
     * @param requestCode Variable de tipo int que corresponde al código que pasámos a través del método 'requestPermissions()'
     * @param permissions Variable de tipo Array de String que hace referencia a los permisos de la aplicación. Nunca pueden ser nulos.
     * @param grantResults Variable de tipo Array de int que recoge el resultado de los permisos correspondientes
     */
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
     * Este método da el valor al ArrayList
     * @param objeto - Parámetro que puede recibir de 0 a varios objetos de tipo Object.
     */
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
         * Sobreescribimos el método getView para poder adaptar los datos que queremos mostrar, al listView que hemos personalizado.
         * Este método sirve para obtener una vista que muestra los datos en la posición especificada en el conjunto de datos.
         * @param posicion La posición del elemento dentro de los datos del sistema de adaptador de la tarea cuyos vista que queremos.
         * @param convertView Hace referencia a la vista vieja para su reutilización, si es posible. Nota: Se debe comprobar que este punto de vista no es nulo y de un tipo adecuado antes de usar.
         *                    Si no es posible convertir esta vista para mostrar los datos correctos, este método puede crear una nueva vista
         * @param parent El padre que finalmente, se adjuntará a este punto de vista
         * @return Una vista correspondiente a los datos en la posición especificada
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






