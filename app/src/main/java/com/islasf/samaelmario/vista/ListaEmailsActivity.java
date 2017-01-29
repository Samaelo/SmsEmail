package com.islasf.samaelmario.vista;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
 * Created by Samael on 25/01/2017.
 */
public class ListaEmailsActivity extends AppCompatActivity implements FuncionalidadesComunes {

    private ListView listaEmails;
    AccesoDatos accesoDatos;
    EmailAdapter emailAdapter;
    private ArrayList<Email> listado_emails;

// ---- onCreate ListaEmails ---- //
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_emails);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab_nuevo_email = (FloatingActionButton)findViewById(R.id.fab_nuevo_email);

        accesoDatos = new AccesoDatos(this);
        accesoDatos.ejecutar_carga_emails(this);
    }

// ---- onAsyncTask ---- //
    @Override
    public void onAsyncTask(Object... objeto) {
        this.listado_emails = (ArrayList<Email>) objeto[0];
        crearListaDeEmails();
    }

    @Override
    public void onAlerta(Object... objeto) {

    }

    public void crearListaDeEmails(){

        listaEmails = (ListView)findViewById(R.id.lv_lista_emails); // Volcamos en el ListView "listaEmails", el listView definido en el xml con su id denominado "lvLista_mensajes"

        emailAdapter = new EmailAdapter(this, listado_emails); // Instanciamos un objeto denominado 'adapter' de tipo TitularAdapter que recibe el contexto en el que se crea y un ArrayList

        if(listado_emails != null){
            listaEmails.setAdapter(emailAdapter); // Rellenamos el ListView denominado 'lista' con el contenido del adaptador, que tendrá los valores del ArrayList

            listaEmails.setOnItemClickListener(new AdapterView.OnItemClickListener() { // Acción para cuando pulsamos algún ítem del TextView
                public void onItemClick(AdapterView adapter, View view, int position, long id) {

                    // MENU CONTEXTUAAAAAAAAAAAL <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<----------------------------------------------------------------------
                }
            });
        }
    }

    public void nuevoEmailFab(View botonFab){

        Intent intent = new Intent(this, EnvioEmailActivity.class);
        startActivity(intent);
    }

    class EmailAdapter extends ArrayAdapter<Email> {

        private Context contexto; // Atributo 'contexto' de tipo Context
        private ArrayList<Email> emails; // Atributo 'titulares' de tipo ArrayList

        /**
         * Constructor de la clase TitularAdapter. Recibe un contexto(donde se va a implementar) y un ArrayList(que contiene la información de los datos que se van a mostrar)
         * @param contexto Hace referencia al contexto en el cual se va a implementar el TitularAdapter
         * @param emails Hace referencia al modelo de datos que recibe el EmailAdapter, que contiene la información de los Emails que será mostrada en el ListView
         */
        public EmailAdapter(Context contexto, ArrayList<Email> emails) {

            super(contexto, -1, emails); // Llamamos al constructor del padre y le pasamos el contexto que queremos y el ArrayList
            this.emails = emails;
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
}
