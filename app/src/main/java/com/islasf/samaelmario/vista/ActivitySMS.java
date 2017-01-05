package com.islasf.samaelmario.vista;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class ActivitySMS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        //Cargamos el actionBar


    }
    private void cargar_actionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onEnviar(View v) {
        Snackbar.make(v, "Aquí va el mensaje de enviado o no.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
    public void onCancelar(View v) {
        Snackbar.make(v, "Aquí va el mensaje de \"Mensaje cancelado\".", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}