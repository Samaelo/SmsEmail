package com.islasf.samaelmario.vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import model.EnvioMensajes;

public class EnvioSMSActivity extends AppCompatActivity {
    EnvioMensajes envio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_sms);
        //   Personalización del ActionBar   //
        cargar_actionBar();
        envio = new EnvioMensajes(this);


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


    //Métodos para los listeners de los botones

    public void onEnviar(View v){
        String mensaje_resultado;

        if(envio.enviar_SMS("","")){
            mensaje_resultado = "El mensaje ha sido enviado con éxito.";
        }else{
            mensaje_resultado = "El mensaje no ha sido enviado por algún error.";
        }
        Toast.makeText(this,mensaje_resultado,Toast.LENGTH_LONG).show();
    }

    public void onCancelar(View v){
        finish();
    }
    public void onSeleccionar_contacto(View v){
        Toast.makeText(this,"joder",Toast.LENGTH_LONG).show();
    }
}
