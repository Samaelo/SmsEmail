package com.islasf.samaelmario.vista;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import model.AccesoDatos;
import model.SMS;

/**
 * Creado por Samael Picazo Navarrete y Mario Garcia el 25/01/2017.
 */
public class ListaSmsActivity extends AppCompatActivity implements FuncionalidadesComunes{

    private ListView listaSms;
    private AccesoDatos accesoDatos;
    SmsAdapter smsAdapter;
    private ArrayList<SMS> smses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_lista_sms);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            listaSms = (ListView) findViewById(R.id.lv_lista_SMS);
            accesoDatos = new AccesoDatos(this);

            accesoDatos.ejecutar_carga_smses(this); // Le pasamos la actividad pero hacemos referencia a la interfaz FuncionalidadesComunes, que es con la que trabajamos en la clase AccesoAdatos

        }

     public void crearListaDeSmses(){

             // Volcamos en el ListView "listaEmails", el listView definido en el xml con su id denominado "lvLista_mensajes"

            smsAdapter = new SmsAdapter(this,smses); // Instanciamos un objeto denominado 'adapter' de tipo TitularAdapter que recibe el contexto en el que se crea y un ArrayList

            if(this.smses != null){

                listaSms.setAdapter(smsAdapter); // Rellenamos el ListView denominado 'lista' con el contenido del adaptador, que tendrá los valores del ArrayList

            }
     }

    @Override
    public void onAlerta(Object... objeto) {

    }

    @Override
    public void onAsyncTask(Object... objeto) {
        this.smses = (ArrayList<SMS>)objeto[0];
        crearListaDeSmses();
    }

    class SmsAdapter extends ArrayAdapter<SMS> {

            private Context contexto; // Atributo 'contexto' de tipo Context
            private ArrayList<SMS> smses; // Atributo 'titulares' de tipo ArrayList

            /**
             * Constructor de la clase TitularAdapter. Recibe un contexto(donde se va a implementar) y un ArrayList(que contiene la información de los datos que se van a mostrar)
             * @param contexto Hace referencia al contexto en el cual se va a implementar el TitularAdapter
             * @param smses Hace referencia al modelo de datos que recibe el SmsAdapter, que contiene la información de los Smsese que será mostrada en el ListView
             */
            public SmsAdapter(Context contexto, ArrayList<SMS> smses) {

                super(contexto, -1, smses); // Llamamos al constructor del padre y le pasamos el contexto que queremos y el ArrayList
                this.smses = smses;
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
                    item = inflater.inflate(R.layout.layout_listview_listasmses, null);

                    holder = new ViewHolder();

                    holder.destinatario = (TextView)item.findViewById(R.id.tv_listView_DestinatarioSMS);
                    holder.fecha = (TextView)item.findViewById(R.id.tv_listView_FechaSMS);
                    holder.texto = (TextView)item.findViewById(R.id.tv_listView_TextoSMS);

                    item.setTag(holder);
                }
                else {
                    holder = (ViewHolder)item.getTag();
                }

                holder.destinatario.setText(" Para : " + smses.get(posicion).getDestinatario());
                holder.fecha.setText("Fecha: " + smses.get(posicion).getFecha_de_envio());

                if(smses.get(posicion).getTexto().length() >= 20)
                    holder.texto.setText("Texto: " + smses.get(posicion).getTexto().substring(0, 20) + "....");
                else
                    holder.texto.setText("Texto: " + smses.get(posicion).getTexto());

                return item;
            }

            public class ViewHolder {

                TextView destinatario;
                TextView fecha;
                TextView texto;
            }
     }
}
