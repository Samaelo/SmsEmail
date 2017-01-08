package com.islasf.samaelmario.vista;

import java.lang.Exception;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.StringTokenizer;

import model.Contacto;
import model.EnvioMensajes;

public class EnvioEmailActivity extends AppCompatActivity implements Alerta{

    private EnvioMensajes envioMensajes;
    private Contacto contacto_seleccionado;
    private EditText et_Remitente, et_Destinatarios, et_Asunto, et_TextoEmail;
    private TextView tv_ContactoSuperior;
    private FloatingActionButton fabEnviar;

    private String [] toDestinatarios, ccRemitente;
    private String mensaje, asunto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_email);

        cargarComponentes();
        cargarActionBar();

    }

    public void onEnviar(View v){

        envioMensajes = new EnvioMensajes(this);


        ccRemitente = new String[1]; // Creamos el String ccRemitente, cogiendo el texto del EditText del Remitente
        ccRemitente[0] =  et_Remitente.getText().toString();
        mensaje = et_TextoEmail.getText().toString();
        asunto = et_Asunto.getText().toString();

        boolean camposCorrectos = comprobarCampos();

        if(camposCorrectos == true){

            try {
                toDestinatarios = recogerEmails(et_Destinatarios.getText().toString());

                if(asunto.toString().trim().isEmpty()){

                    DialogoAlerta dialogo = new DialogoAlerta();
                    String[] opciones =  {"Aceptar", "Cancelar"};
                    dialogo.setDialogo(this,"¿ Deseas enviar el mensaje sin asunto ?", "E-mail sin asunto", opciones, 2);
                    dialogo.show(getFragmentManager(), "tagAlerta");
                }else{
                    if(envioMensajes.enviar_email(toDestinatarios,ccRemitente,mensaje,asunto) == false){
                        Snackbar.make(fabEnviar, "Error al enviar el E-mail", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }


            }
            catch (Exception e) {
                DialogoAlerta dialogo = new DialogoAlerta();
                String[] opciones =  {"Aceptar"};
                dialogo.setDialogo(this,"Alguna de las direcciones de correo no cumple un patrón válido. Por favor, revise los E-mails.", "E-mail erróneo", opciones, 1);
                dialogo.show(getFragmentManager(), "tagAlerta");

            }
        }
    }


    @Override
    public void onAlerta(Object... objeto){
        if(envioMensajes.enviar_email(toDestinatarios,ccRemitente,mensaje,asunto) == false){
            Snackbar.make(fabEnviar, "Error al enviar el E-mail", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

    }



    public String[] recogerEmails(String textoDestinatarios) throws Exception {

        String[] emails;

        String patronEmail = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$";

        if(textoDestinatarios.contains(",")){

            StringTokenizer tokenizador = new StringTokenizer(textoDestinatarios, ",");

            int numeroEmails = tokenizador.countTokens(); // Contamos el número de Tokens que equivaldrá al número de emails y lo volcamos en la variable numeroEmails

            emails = new String[numeroEmails]; // El array de Strings de los emails será igual al número de tokens

            // Mientras haya tokens después de un serparador por ","

                for(int i = 0; i < emails.length; i++){ // Para la longitud del array

                    String email = tokenizador.nextToken();

                        if(email.matches(patronEmail)){

                            emails[i] = email; // La posición del email será igual a la posición del token
                        }
                        else throw new Exception();
                    }
        }

        else if(textoDestinatarios.matches(patronEmail)){

                emails = new String[1];
                emails[0] = textoDestinatarios;
            }
            else throw new Exception("Alguno de los emails no cumple un patrón correcto");

        return emails;
    }


    public void onCancelar(View v){
        finish();
    }

    public void limpiarTextos(){

        et_Remitente.setText("");
        et_Destinatarios.setText("");
    }

    public void cargarComponentes(){

        et_Remitente = (EditText)findViewById(R.id.et_Remitente);
        et_Destinatarios = (EditText)findViewById(R.id.et_Destinatarios);
        et_Asunto = (EditText)findViewById(R.id.et_Asunto);
        et_TextoEmail = (EditText)findViewById(R.id.et_TextoEmail);
        tv_ContactoSuperior = (TextView)findViewById(R.id.tv_ContactoSuperior);
        fabEnviar = (FloatingActionButton) findViewById(R.id.fabEnviarMail);
    }

    public void cargarActionBar(){

        Toolbar toolbarEmail = (Toolbar)findViewById(R.id.tbEnvioEmail);
        setSupportActionBar(toolbarEmail);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflamos el menú; añadimos los items al action bar si éste está presente.
        MenuInflater infladorMenu = getMenuInflater();
        infladorMenu.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void seleccionar_Contacto(View v){

        // Mediante el icono de la agenda de contactos, iremos a la actividad que contiene la lista de contactos
        Intent intent = new Intent(this, ListaContactosActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Obtenemos el contacto seleccionado
        contacto_seleccionado = (Contacto)data.getSerializableExtra("CONTACTO");

        /* Una vez seleccionamos el contacto de la lista de contactos, sustituimos el text view "Selecciona un contacto pulsando el icono de la parte superior", por el nombre del contacto
           que hemos elegido, y su número de teléfono */

        String textoContacto = String.format(getResources().getString(R.string.contacto_seleccionado), contacto_seleccionado.obtener_nombre(), contacto_seleccionado.obtener_tfno_movil());
        tv_ContactoSuperior.setText(textoContacto);
    }

    /**
     * Este método comprueba que ni el Remitente ni el Destinatario están vacíos. Si alguno de los dos están vacíos se informa al usuario mediante un Toast, de que:
     * 1- Si el campo "remitente" está vacío debe introducir un remitente
     * 2- Si el campo "destinatarios" no tiene al menos un destinatario, debe introducir uno al menos.
     * 3- Si ambos campos están vacíos, debe introducir un remitente y al menos un destinatario
     */
    public boolean comprobarCampos(){

        String remitenteVacio = String.format(getResources().getString(R.string.remitenteVacio));
        String destinatariosVacio = String.format(getResources().getString(R.string.destinatariosVacio));
        String remitenteDestinatarioVacios = String.format(getResources().getString(R.string.remitenteDestinatariosVacio));

        if(et_Remitente.getText().toString().trim().isEmpty() && et_Destinatarios.getText().toString().trim().isEmpty()){
            Toast.makeText(this, remitenteDestinatarioVacios, Toast.LENGTH_LONG).show();
            return false;
        }

        else if(et_Remitente.getText().toString().trim().isEmpty()){
            Toast.makeText(this, remitenteVacio, Toast.LENGTH_LONG).show();
            return false;
        }

        else if(et_Destinatarios.getText().toString().trim().isEmpty()){
            Toast.makeText(this, destinatariosVacio, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
