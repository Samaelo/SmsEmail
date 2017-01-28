package com.islasf.samaelmario.vista;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Mario on 27/01/2017.
 */

public class Permisos {

    final int REQUEST_CONTACTOS = 0;
    final int REQUEST_SMS = 1;
    private Activity actividad_llamante;

    public Permisos(Activity actividad){
        this.actividad_llamante = actividad;
    }
    public boolean verificarPermisos_Contactos(){
        if (ActivityCompat.checkSelfPermission(actividad_llamante, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now


            if (ActivityCompat.shouldShowRequestPermissionRationale(actividad_llamante,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Display UI and wait for user interaction
            } else {
                ActivityCompat.requestPermissions(
                        actividad_llamante, new String[]{Manifest.permission.READ_CONTACTS},
                        REQUEST_CONTACTOS);
            }
        } else {

        }
        return true;
    }
}
