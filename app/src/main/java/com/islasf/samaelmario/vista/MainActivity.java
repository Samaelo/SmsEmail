package com.islasf.samaelmario.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import model.AccesoDatos;
import model.Constantes;
import model.Contacto;
import model.Email;
import model.SMS;

public class MainActivity extends AppCompatActivity implements  FuncionalidadesComunes{
/*
TODO: los XML de Strings siempre ir haciéndolos conforme avanzan las activities.
TODO: Puesto que hay que poner preferencias, vamos a poner que una de ellas sea poder modificar el tema. Por lo que hay que modificar también el XML de colores para crear los nuestros propios( al menos 4 temas en total).
TODO: Lista de contactos agregados.
TODO: Lista de Mensajes enviados.
TODO: Actividades: Lista de contactos, lista de mensajes, preferencias (con preferences screen) , envío de mail, mostrar perfil, editar perfil, historial, agregar contacto, Acerca de
TODO: Comprobar perfil anterior con el siguiente en algún método de la clase PerfiLActivity.
TODO: Añadir botón de refresh para volver a cargar contactos.
TODO: Comprobación de que si el array de contactos del intent del startactivity está vacío, que los cargue de partida.
TODO: Operación de cargar contactos en AsyncTask
TODO: Operación de cargar mensajes en AsyncTask
TODO: No mostrar usuarios que tengan mail no válido( en la lista de elección de contactos de envío de email)
TODO: No mostrar usuarios que tengan un teléfono no válido (en la lista de elección de contactos de envio de SMS)
TODO: Indicar en al how-to que hemos hecho el campo de direcciones de mail multivalor por comodidad, que somos conscientes de que habría que crear una tabla, pero lo importante de ésto es aprender android.
TODO:
*/

    private ArrayList<Contacto> contactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cargarActionBar();

    }

    public void cargarActionBar(){

        Toolbar toolbarEmail = (Toolbar)findViewById(R.id.tbMain);
        setSupportActionBar(toolbarEmail);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("");
    }

    /**
     * Crea el menú de la ToolBar, que contiene el icono para acceder a la lista de Emails y el icono para acceder a la lista de SMS's
     * @param menu Objeto de la clase Menu
     * @return Retorna true si se puede crear el menú
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // --- Método para dar funcionalidad a los botones del Menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.item_Mostrar_Emails:
                abrirPopupEmail(this.findViewById(R.id.item_Mostrar_Emails));
                break;
            case R.id.item_Mostrar_SMS:
                abrirPopupSms(this.findViewById(R.id.item_Mostrar_SMS));
                break;
            case R.id.itemPreferencias:
                startActivity(new Intent(this,PreferenciasActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // -- POP UP
    public void abrirPopupEmail(View iconoEmails) {

        PopupMenu popupMenu = new PopupMenu(this, iconoEmails);
        popupMenu.inflate(R.menu.menu_contextual_email);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.NuevoMail) {
                    irEnvioEmailActivity();
                }
                else if (item.getItemId() == R.id.ListarMails) {

                    try {
                        irListaEmailsActivity();
                    }catch(Exception e){}
                }
                return true;
            }
        });

        MenuPopupHelper menuHelper = new MenuPopupHelper(this, (MenuBuilder) popupMenu.getMenu(), iconoEmails);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();
    }

    public void abrirPopupSms(View iconoSms) {

        PopupMenu popupMenu = new PopupMenu(this, iconoSms);
        popupMenu.inflate(R.menu.menu_contextual_sms);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.NuevoSms) {
                    irEnvioSmsActivity();
                }
                else if (item.getItemId() == R.id.ListarSmses) {
                    irListaSmsActivity();
                }
                return true;
            }
        });

        MenuPopupHelper menuHelper = new MenuPopupHelper(this, (MenuBuilder) popupMenu.getMenu(), iconoSms);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();
    }

    public void irEnvioEmailActivity(){

        Intent intent = new Intent(this, EnvioEmailActivity.class);
        startActivity(intent);
    }

    public void irListaEmailsActivity(){

        Intent intent = new Intent(this, ListaEmailsActivity.class);
        startActivity(intent);
    }

    public void irEnvioSmsActivity(){

        Intent intent = new Intent(this, EnvioSMSActivity.class);
        startActivity(intent);
    }

    public void irListaSmsActivity(){

        Intent intent = new Intent(this, ListaSmsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onAlerta(Object... objeto) {

    }

    @Override
    public void onAsyncTask(Object... objeto) {

       this.contactos = (ArrayList<Contacto>) objeto[0];

       Intent intent = new Intent(this,EnvioEmailActivity.class);
       intent.putExtra(Constantes.LISTADO_CONTACTOS_CARGADOS,this.contactos);
       startActivity(intent);
    }
}
