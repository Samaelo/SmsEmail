package model;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * Clase destinada al envío de los mensajes email y SMS.
 */
public class EnvioMensajes {

    /**
     * Atributo que hace referencia a la actividad que instancia un objeto de esta clase.
     */
    private Activity actividad;

    /**
     * Método constructor que recibe por parámetro el valor que debe tomar el atributo actividad.
     * @param actividad - Parámetro que da valor al atributo actividad.
     */
    public EnvioMensajes(Activity actividad){
        this.actividad = actividad;
    }

    /**
     * Método encargado de enviar un email mediante un intent que llama a la aplicación gestora de mensajería
     * mail por defecto del teléfono. Recibe por parámetro los datos necesarios para ello.
     *
     * @param ccRemitente - Parámetro que hace referencia a la lista de remitentes del mensaje.
     * @param ToCorreos - Parámetro que hace referencia a la lista de destinatarios.
     * @param asunto - Parámetro que hace referencia al asunto del mensaje.
     * @param mensaje - Parámetro que hace referencia al contenido del mensaje.
     * @return - Devuelve true si el mensaje ha sido enviado, false si no.
     */
    public boolean enviar_email(String ccRemitente,String[] ToCorreos,String asunto, String mensaje){

        Log.i("Send email", "");
        //String[] ToCorreos = {"miniftorres@hotmail.com"};
       // String[] ccRemitente = {"mariogarciarb@gmail.com"};

        try {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, ToCorreos);
            emailIntent.putExtra(Intent.EXTRA_CC, ccRemitente);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
            emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);

            this.actividad.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

        } catch (Exception e) {
            Log.i("q", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Método encargado de enviar mensajes SMS desde la aplicación propia, gracias a la clase
     * SMSManager, que permite enviar mensajes de forma directa.
     * @param mensaje - Parámetro que da valor al contenido del mensaje.
     * @param telefono - Parámetro que da valor al número de teléfono de
     * @return - Devuelve true si el mensaje se ha enviado. Devuelve false si se ha enviado.
     */
    public boolean enviar_SMS(String mensaje, String telefono){
        SmsManager emisor;

        try{
            emisor = SmsManager.getDefault();
            emisor.sendTextMessage(telefono,null,mensaje,null,null);
        }catch(Exception e){
            return false;
        }
        return true;
    }
}
