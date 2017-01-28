package com.islasf.samaelmario.vista;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import model.Constantes;

/**
 * Created by Mario on 27/01/2017.
 */

public class Permisos {

    private int REQUEST_CONTACTOS = 0;
    private final int REQUEST_SMS = 1;
    private Activity actividad_llamante;
    private final String RATIONALE_CONTACTOS="Para poder tener acceso a tus contactos y obtener sus" +
            "direcciones de correo electrónico o teléfono, necesitamos que aceptes los permisos.";
    private final String RATIONALE_SMS="Para poder realizar envíos SMS, es necesario que nos des" +
            "permisos de envío SMS.";

    public Permisos(Activity actividad){
        this.actividad_llamante = actividad;
    }

    /**
     * Método de tipo boolean que verifica si están activados los permisos para el acceso en modo lectura a los
     *  contactos del móvil para la propia aplicación. Su modo de empleo es el siguiente: si se desea
     *  verificar si la aplicación tiene permisos garantizados para solicitarlos o no, basta con
     *  emplear este método.
     *
     * @return - Devuelve true si los permisos de acceso a los contactos están activados. False si
     * no lo están.
     */
    public boolean verificarPermisos_Contactos(){

        boolean resultado = ActivityCompat.checkSelfPermission(actividad_llamante, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED;


        return resultado;
    }

    /**
     * Método que solicita los permisos de acceso de lectura a los contactos del móvil, de manera
     * que la aplicación pueda obtener información de éstos.
     * El modo de empleo es el siguiente: Se comprueba con el método verificarPermisos_Contactos
     * si la aplicación tiene los permisos garantizados. En función del valor boolean que devuelva
     * invocamos al método para solicitar dichos permisos.
     *
     */
    public void solicitarPermisos_Contactos(FuncionalidadesComunes actividad_dialogo){
        String[] opciones = {"No permitir","Intentar de nuevo"};
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad_llamante,
                Manifest.permission.READ_CONTACTS)) {
            DialogoAlerta dialogo = new DialogoAlerta();
            dialogo.setDialogo(actividad_dialogo,RATIONALE_CONTACTOS,"Permisos de contactos",opciones, Constantes.DIALOGO_DOS_OPCIONES);
            dialogo.show(((Activity) actividad_dialogo).getFragmentManager(),"permisos_contactos");
        } else {
            ActivityCompat.requestPermissions(
                    actividad_llamante, new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_CONTACTOS);
        }
    }
}
