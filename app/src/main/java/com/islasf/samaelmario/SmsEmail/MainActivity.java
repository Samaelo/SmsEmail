package com.islasf.samaelmario.SmsEmail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EnvioMensajes envio = new EnvioMensajes(this);
        envio.verificar_permisos(this);
    }
    public void onSMS(View v){
        EnvioMensajes envio = new EnvioMensajes(this);
        envio.enviar_SMS("Enviado desde Android StudioeeEnviado\"Enviado desde Android StudioeeEnviado desde Android Studioeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee\"eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee d\"Envi\"Enviado desde Android StudioeeEnviado desde Android Studioeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee\"eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeado desde Android StudioeeEnviado desde Android Studioeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee\"eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeesde Android Studioeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee\"eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee","");
    }
    public void onMAIL(View v){
        EnvioMensajes envio = new EnvioMensajes(this);
        envio.enviar_email("desde android studio ermanox","");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
