package com.islasf.samaelmario.vista;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import model.Constantes;

/**
 * Clase destinada a la gestión de los permisos de la aplicación. Se encarga de verificar y solicitar
 * los permisos garantizados por el usuario a la aplicación. Además muestra al los usuarios más cabezotas
 * el por qué debería aceptar los permisos solicitados.
 */

public class Permisos {

    /**
     * Atributo de tipo Activity que hace referencia a la actividad que instancia un objeto de esta clase.
     */
    private Activity actividad_llamante;

    /**
     * Constante de tipo String que hace referencia al mensaje que mostrar en el método "mostrarRationale".
     * Este mensaje es la explicación al usuario de por qué debería garantizar los permisos de
     * acceso de lectura a los contactos del teléfono a la aplicación.
     */
    private final String RATIONALE_CONTACTOS="Para poder tener acceso a tus contactos y obtener sus" +
            "direcciones de correo electrónico o teléfono, necesitamos que aceptes los permisos.";

    /**
     * Constante de tipo String que hace referencia al mensaje que mostrar en el método "mostrarRationale".
     * Este mensaje es la explicación al usuario de por qué debería garantizar los permisos de
     * envío SMS a la aplicación.
     */
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
        boolean resultado = ActivityCompat.checkSelfPermission(actividad_llamante, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;

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
    public void solicitarPermisos_Contactos(){
            ActivityCompat.requestPermissions(actividad_llamante, new String[]{Manifest.permission.READ_CONTACTS}, Constantes.REQUEST_PERMISOS_CONTACTOS);
    }

    /**
     * Método encargado de mostrar la razón de por qué se deberían garantizar los permisos a la
     * aplicación. Este método debe ser llamado cuando el usuario ha denegado los permisos, pero no
     * ha hecho "check" en la opción "No preguntarme más".
     * @param actividad_dialogo - Actividad que llama al método, de la que se obtiene un objeto
     *                          de tipo FragmentManager mediante el método Activity.getFragmentManager.
     *                          Éste es empleado para invocar al diálogo de explicación.
     * @param opciones - Parámetro de tipo array primitivo de String que hace referencia a la
     *                 serie de botones que va a tener el diálogo mostrado. Por lo general son
     *                 "No permitir" e "Intentar de nuevo".
     */
    public void mostrar_RationaleContactos(FuncionalidadesComunes actividad_dialogo, String[] opciones){
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad_llamante,
                Manifest.permission.READ_CONTACTS)) {
            DialogoAlerta dialogo = new DialogoAlerta();
            dialogo.setDialogo(actividad_dialogo,RATIONALE_CONTACTOS,"Permisos de contactos",opciones, Constantes.DIALOGO_DOS_OPCIONES);
            dialogo.show(((Activity) actividad_dialogo).getFragmentManager(),"permisos_contactos");

        } else {
            solicitarPermisos_Contactos();
        }

    }

    /**
     *  Método de tipo boolean que verifica si están activados los permisos para el envío de SMS dentro de la aplicación. Su modo de empleo es el siguiente: si se desea
     *  verificar si la aplicación tiene permisos garantizados para solicitarlos o no, basta con emplear este método.
     * @return - Devuelve true si los permisos de acceso a los contactos están activados. False si no lo están.
     */
    public boolean verificarPermisos_SMS(){
        boolean resultado = ActivityCompat.checkSelfPermission(actividad_llamante, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;

        return resultado;
    }

    /**
     * Método que solicita los permisos para el envío de SMS dentro de la aplicación.
     * El modo de empleo es el siguiente: Se comprueba con el método verificarPermisos_SMS
     * si la aplicación tiene los permisos garantizados. En función del valor boolean que devuelva
     * invocamos al método para solicitar dichos permisos.
     *
     */
    public void solicitarPermisos_SMS(){
        ActivityCompat.requestPermissions(actividad_llamante, new String[]{Manifest.permission.SEND_SMS},Constantes.REQUEST_PERMISOS_SMS);
    }

    /**
     * Método encargado de mostrar la razón de por qué se deberían garantizar los permisos a la
     * aplicación para el envío de Smses. Este método debe ser llamado cuando el usuario ha denegado los permisos de envío de Smses, pero no
     * ha hecho "check" en la opción "No preguntarme más".
     * @param actividad_dialogo - Actividad que llama al método, de la que se obtiene un objeto
     *                          de tipo FragmentManager mediante el método Activity.getFragmentManager.
     *                          Éste es empleado para invocar al diálogo de explicación.
     * @param opciones - Parámetro de tipo array primitivo de String que hace referencia a la
     *                 serie de botones que va a tener el diálogo mostrado. Por lo general son
     *                 "No permitir" e "Intentar de nuevo".
     */
    public void mostrar_RationaleSMS(FuncionalidadesComunes actividad_dialogo, String[] opciones){
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad_llamante, Manifest.permission.SEND_SMS)) {

            DialogoAlerta dialogo = new DialogoAlerta();
            dialogo.setDialogo(actividad_dialogo,RATIONALE_SMS,"Permisos de SMS",opciones, Constantes.DIALOGO_DOS_OPCIONES);
            dialogo.show(((Activity) actividad_dialogo).getFragmentManager(),"permisos_Smses");

        } else {
            solicitarPermisos_Contactos();
        }
    }
}
