package com.islasf.samaelmario.vista;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import model.Preferencias;

/**
 * Clase destinada a la gestión de los componentes de la actividad de las preferencias, las cuales
 * constan de los permisos necesarios para el correcto funcionamiento de la aplicación.
 */

public class PreferenciasFragment extends PreferenceFragment {

    /**
     * Atributo de tipo Permisos cuya finalidad es verificar y solicitar los permisos de los contactos y de envío SMS.
     */
    private Permisos permisos;

    /**
     * Atributo de tipo SwitchPreference que hace referencia al componente SwitchPreference de SMS de la interfaz.
     */
    private SwitchPreference switchSMS;

    /**
     * Atributo de tipo SwitchPreference que hace referencia al componente SwitchPreference de Contactos de la interfaz.
     */
    private SwitchPreference switchContactos;

    /**
     * Atributo de tipo Activity que es necesario para instanciar el atributo de permisos.
     */
    private Activity actividad;

    /**
     * Atributo de tipo boolean destinado a tomar el valor de la verificación de los permisos de los contactos,
     * de manera que se pueda comprobar de forma cómoda si los switches deben estar activados o no y "checkeados" o no.
     */
    private boolean permisos_contactos;

    /**
     * Atributo de tipo boolean destinado a tomar el valor de la verificación de los permisos del envío de SMS,
     * de manera que se pueda comprobar de forma cómoda si los switches deben estar activados o no y "checkeados" o no.
     */
    private boolean permisos_SMS;


    public void establecer_contexto(Activity actividad){
        this.actividad = actividad;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.ventana_preferencias);
        permisos = new Permisos(actividad);

        permisos_contactos = permisos.verificarPermisos_Contactos();
        permisos_SMS = permisos.verificarPermisos_SMS();

        cargar_componentes();
    }

    /**
     * Método encargado de cargar los componentes gráficos de las preferencias.
     * Además, establece por cada permiso, que el componente asociado esté desactivado y "checkeado"
     * si el permiso está garantizado, o lo contrario si el permiso ha sido denegado.
     */
    private void cargar_componentes(){

        this.switchContactos = (SwitchPreference) findPreference("switch_preferenciasContactos");
        this.switchSMS = (SwitchPreference) findPreference("switch_preferenciasSMS");

        this.switchContactos.setChecked(permisos_contactos);
        this.switchSMS.setChecked(permisos_SMS);

        this.switchContactos.setEnabled(!permisos_contactos);
        this.switchSMS.setEnabled(!permisos_SMS);

        switchContactos.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                onSwitch_Contactos();
                return false;
            }
        });

        switchSMS.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                onSwitch_SMS();
                return false;
            }
        });
    }

    /**
     * Método encargado de solicitar los permisos de acceso de lectura a los contactos del teléfono en caso de ser necesario. Además, se encarga
     * de checkear y desactivar el componente.
     */
    public void onSwitch_Contactos(){
        permisos.solicitarPermisos_Contactos();
        switchContactos.setEnabled(false);
        switchContactos.setChecked(true);
    }

    /**
     * Método encargado de solicitar los permisos de envío de SMS en caso de ser necesario. Además, se encarga
     * de checkear y desactivar el componente.
     */
    public void onSwitch_SMS(){
        permisos.solicitarPermisos_SMS();
        switchSMS.setChecked(true);
        switchSMS.setEnabled(false);
    }

}

