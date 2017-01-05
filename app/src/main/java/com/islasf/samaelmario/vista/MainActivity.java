package com.islasf.samaelmario.vista;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
/*
TODO: los XML de Strings siempre ir haciéndolos conforme avanzan las activities.
TODO: Puesto que hay que poner preferencias, vamos a poner que una de ellas sea poder modificar el tema. Por lo que hay que modificar también el XML de colores para crear los nuestros propios( al menos 4 temas en total).

 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //   Personalización del ActionBar   //
        cargar_actionBar();

        startActivity(new Intent(this, ActivitySMS.class));


    }

    private void cargar_actionBar(){
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
