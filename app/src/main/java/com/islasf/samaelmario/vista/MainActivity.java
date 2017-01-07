package com.islasf.samaelmario.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

public class MainActivity extends AppCompatActivity {
/*
TODO: los XML de Strings siempre ir haciéndolos conforme avanzan las activities.
TODO: Puesto que hay que poner preferencias, vamos a poner que una de ellas sea poder modificar el tema. Por lo que hay que modificar también el XML de colores para crear los nuestros propios( al menos 4 temas en total).
TODO: Lista de contactos agregados.
TODO: Lista de Mensajes enviados.
TODO: Actividades: Lista de contactos, lista de mensajes, preferencias (con preferences screen) , envío de mail, mostrar perfil, editar perfil, historial, agregar contacto, Acerca de
TODO:Comprobar perfil anterior con el siguiente en algún método de la clase PerfiLActivity.
 */

    /*
    Fechas:
    Calendar cal = Calendar.getInstance();
    cal.setDate(Objeto de tipo Date);
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //   Personalización del ActionBar   //
        cargar_actionBar();
        startActivity(new Intent(this,EnvioEmailActivity.class));


    }

    private void cargar_actionBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
