package com.islasf.samaelmario.vista;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import model.Preferencias;

/**
 * Created by Mario on 22/01/2017.
 */

public class PreferenciasFragment extends PreferenceFragment {

    private Preferencias preferencias;
    private Permisos permisos;
    private SwitchPreference switchSMS,switchContactos;
    private Activity actividad;
    private boolean permisos_contactos;
    private boolean permisos_SMS;


    public void establecer_contexto(Activity actividad){
        this.actividad = actividad;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.ventana_preferencias);
        preferencias = new Preferencias(actividad);
        permisos = new Permisos(actividad);

        permisos_contactos = permisos.verificarPermisos_Contactos();
        permisos_SMS = permisos.verificarPermisos_SMS();

        cargar_componentes();
    }

    /**
     * Método encargado de cargar los componentes gráficos de las preferencias.
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

    public void onSwitch_Contactos(){
        permisos.solicitarPermisos_Contactos();
        switchContactos.setEnabled(false);
        switchContactos.setChecked(true);
    }

    public void onSwitch_SMS(){
        permisos.solicitarPermisos_SMS();
        switchSMS.setChecked(true);
        switchSMS.setEnabled(false);
    }

}

