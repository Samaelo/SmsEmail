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
 * Created by Mario on 04/01/2017.
 */
//
public class EnvioMensajes {

    private Activity actividad;
    public EnvioMensajes(Activity actividad){
        this.actividad = actividad;
    }

    public boolean enviar_email(String mensaje, String direccion){
        Log.i("Send email", "");
        String[] TO = {"miniftorres@hotmail.com"};
        String[] CC = {"mariogarciarb@gmail.com"};
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");


            this.actividad.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }
    public boolean enviar_SMS(String mensaje, String telefono){
        SmsManager emisor;

        try{
            emisor = SmsManager.getDefault();
            emisor.sendTextMessage("5556",null,mensaje,null,null);
        }catch(Exception e){
            return false;
        }
        return true;
    }
    public void verificar_permisos(Activity actividad){
        String PERMISOS_ENVIO_MENSAJES = Manifest.permission.SEND_SMS;

        int permisos = ActivityCompat.checkSelfPermission(actividad, PERMISOS_ENVIO_MENSAJES);
        if(permisos== PackageManager.PERMISSION_DENIED){
            solicitar_permisos(actividad, PERMISOS_ENVIO_MENSAJES);
        }
    }
    public void solicitar_permisos(Activity actividad, String PERMISOS_ENVIO_MENSAJES){
        String[] lista_permisos = {PERMISOS_ENVIO_MENSAJES};
        ActivityCompat.requestPermissions(actividad,lista_permisos,123);
    }
}
