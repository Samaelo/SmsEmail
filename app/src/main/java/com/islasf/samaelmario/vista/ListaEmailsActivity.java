package com.islasf.samaelmario.vista;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import model.AccesoDatos;
import model.Email;

/**
 * Esta clase hace referencia a la Actividad donde se muestra la interfaz de usuario, para que éste vea el listado de Emails.
 */
public class ListaEmailsActivity extends AppCompatActivity implements FuncionalidadesComunes {

    private ListView listaEmails;
    AccesoDatos accesoDatos;
    EmailAdapter emailAdapter;
    private ArrayList<Email> listado_emails;
    private GestorMenus gestorMenus = new GestorMenus(this);

// ---- onCreate ListaEmails ---- //

    /**
     * Este método crea la Activity. Dentro se crea el Toolbar que contiene los iconos del Menú, y se instancia un objeto de la clase AccesoDatos, al cual se le pasa la Actividad
     * ListaEmailsActivity (haciendo referencia con la palabra reservada "this") y se ejecuta el método de dicha clase 'ejecutar_carga_emails(this)'.
     * @param savedInstanceState Objeto de tipo Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_emails);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        accesoDatos = new AccesoDatos(this);
        accesoDatos.ejecutar_carga_emails(this);
    }

// ---- onAsyncTask ---- //

    /**
     * Este método vuelca el valor del parámetro en el ArrayList que hace referencia al atributo de la clase. Posteriormente se invoca al método 'crearListaDeEmails()'
     * @param objeto - Parámetro que puede recibir de 0 a varios objetos de tipo Object.
     */
    @Override
    public void onAsyncTask(Object... objeto) {
        this.listado_emails = (ArrayList<Email>) objeto[0];
        crearListaDeEmails();
    }

    /**
     * En esta clase este método no hace nada. Pertenece a la clase porque esta implementa la interfaz FuncionalidadesComunes y es obligatorio implementarla.
     * @param objeto - Parámetro que puede recibir de 0 a varios objetos de tipo Object.
     */
    @Override
    public void onAlerta(Object... objeto) {

    }

    /**
     * Este método crea un ListView donde se vuelcan los E-mails. Primero se comprueba si el Array con el cual se quiere llenar el ListView está vacío. Si está vacío, no se infla el ListView
     * sino, se infla el ListView.
     */
    public void crearListaDeEmails(){

        listaEmails = (ListView)findViewById(R.id.lv_lista_emails); // Volcamos en el ListView "listaEmails", el listView definido en el xml con su id denominado "lvLista_mensajes"

        emailAdapter = new EmailAdapter(this, listado_emails); // Instanciamos un objeto denominado 'adapter' de tipo TitularAdapter que recibe el contexto en el que se crea y un ArrayList

        if(listado_emails != null){
            listaEmails.setAdapter(emailAdapter); // Rellenamos el ListView denominado 'lista' con el contenido del adaptador, que tendrá los valores del ArrayList
        }
    }

    /**
     * Este método instancia un Intent que lleva a la Activity EnvioEmailActivity
     * @param botonFab Botón de tipo Floating Action Button
     */
    public void nuevoEmailFab(View botonFab){

        Intent intent = new Intent(this, EnvioEmailActivity.class);
        startActivity(intent);
    }

    /**
     * Esta clase sirve para crear un ArrayAdapter referente a la lista de E-mails
     */
    class EmailAdapter extends ArrayAdapter<Email> {

        private Context contexto; // Atributo 'contexto' de tipo Context
        private ArrayList<Email> emails; // Atributo 'titulares' de tipo ArrayList

        /**
         * Constructor de la clase EmailAdapter. Recibe un contexto(donde se va a implementar) y un ArrayList(que contiene la información de los datos que se van a mostrar)
         * @param contexto Hace referencia al contexto en el cual se va a implementar el EmailAdapter
         * @param emails Hace referencia al modelo de datos que recibe el EmailAdapter, que contiene la información de los Emails que será mostrada en el ListView
         */
        public EmailAdapter(Context contexto, ArrayList<Email> emails) {

            super(contexto, -1, emails); // Llamamos al constructor del padre y le pasamos el contexto que queremos y el ArrayList
            this.emails = emails;
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
        public View getView(int posicion, View convertView, ViewGroup parent) {

            View item = convertView;
            ViewHolder holder;

            // Siempre que exista algún layout que pueda ser reutilizado éste se va a recibir a través del parámetro convertView del método getView().
            // De esta forma, en los casos en que éste no sea null podremos obviar el trabajo de inflar el layout
            if (item == null) {

                LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                item = inflater.inflate(R.layout.layout_listview_listaemails, null);

                holder = new ViewHolder();
                holder.remitente = (TextView)item.findViewById(R.id.tv_listView_Remitente);
                holder.destinatarios = (TextView)item.findViewById(R.id.tv_listView_Destinatarios);
                holder.fecha = (TextView)item.findViewById(R.id.tv_listView_Fecha);
                holder.texto = (TextView)item.findViewById(R.id.tv_listView_Texto);

                item.setTag(holder);
            }
            else {
                holder = (ViewHolder)item.getTag();
            }

            holder.remitente.setText("De: " + emails.get(posicion).getRemitente());
            holder.destinatarios.setText("Para : " + emails.get(posicion).getDestinatarios()[0]);
            holder.fecha.setText("Fecha: " + emails.get(posicion).getFecha_de_envio());

            if(emails.get(posicion).getTexto().length() >= 20)
                holder.texto.setText("Texto: " + emails.get(posicion).getTexto().substring(0, 20) + "....");
            else
                holder.texto.setText("Texto: " + emails.get(posicion).getTexto());

            return item;
        }

        public class ViewHolder {

            TextView remitente;
            TextView destinatarios;
            TextView fecha;
            TextView texto;
        }
    }

    /**
     * Crea el menú de la ToolBar, que contiene el icono para acceder a la lista de Emails y el icono para acceder a la lista de SMS's
     * @param menu Objeto de la clase Menu
     * @return Retorna true si se puede crear el menú
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        gestorMenus.onCreateOptionsMenu(menu);
        return true;
    }

    // --- Método para dar funcionalidad a los botones del Menú
    /**
     * Este método llama al objeto GestorMenus instanciado en el onCreate, para ejecutar su método onOptionsItemSelected(item), el cual recibe el item del Menú que recibe el propio método por
     * parámetro.
     * @param item Item del Menu
     * @return Retorna un valor de tipo booleano
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        gestorMenus.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }
}
