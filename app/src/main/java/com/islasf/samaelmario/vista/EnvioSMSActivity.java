package com.islasf.samaelmario.vista;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import model.Contacto;
import model.EnvioMensajes;
import model.Perfil;

public class EnvioSMSActivity extends AppCompatActivity {

    private EditText etTextoMensaje;
    private TextView txtContacto;
    private Contacto contacto_seleccionado;
    EnvioMensajes envio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_sms);
        //   Personalización del ActionBar   //
        cargar_componentes();
        cargar_actionBar();
        envio = new EnvioMensajes(this);

    }

    private void cargar_actionBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbEnvioSMS);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("");
    }

    private void cargar_componentes(){
        etTextoMensaje = (EditText) findViewById(R.id.etMensaje);
        txtContacto = (TextView) findViewById(R.id.txtContacto);
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

        if(contacto_seleccionado!=null){
            if(envio.enviar_SMS(etTextoMensaje.getText().toString(),contacto_seleccionado.obtener_tfno_movil())){
                mensaje_resultado = "El mensaje ha sido enviado con éxito.";
            }else{
                mensaje_resultado = "El mensaje no ha sido enviado por algún error.";
            }
            Toast.makeText(this,mensaje_resultado,Toast.LENGTH_LONG).show();
        }else{
            Snackbar.make(v, "Por favor, seleccione un contacto.", Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    public void onCancelar(View v){
        finish();
    }

    public void onSeleccionar_contacto(View v){
        Toast.makeText(this,"joder",Toast.LENGTH_LONG).show();
        //Esperamos que nos devuelva el objeto contacto para poder extraer el número de teléfono de éste.
        Intent intent = new Intent(this,ListaContactosActivity.class);

        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        contacto_seleccionado = (Contacto) data.getSerializableExtra("CONTACTO");
        //Poner nombre en un textview
        String textoContacto = String.format(getResources().getString(R.string.contacto_seleccionado), contacto_seleccionado.obtener_nombre(),contacto_seleccionado.obtener_tfno_movil());
        txtContacto.setText(textoContacto);
    }
}
