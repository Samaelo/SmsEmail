package com.islasf.samaelmario.vista;

import android.content.Context;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.view.View;
import android.widget.Toast;

import model.Preferencias;

/**
 * Created by Mario on 22/01/2017.
 */

public class PreferenciasFragment extends PreferenceFragment {

    private Preferencias preferencias;
    private SwitchPreference switchSMS,switchContactos;
    private ListPreference listPreferenceTema;
    private Context contexto;

    public void establecer_contexto(Context contexto){
        this.contexto = contexto;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.ventana_preferencias);
        preferencias = new Preferencias(contexto);
        cargar_componentes();

    }

    /**
     * Método encargado de cargar los componentes gráficos de las preferencias.
     */
    private void cargar_componentes(){
        boolean permisos_contactos = preferencias.obtenerPermisos_Contactos();
        boolean permisos_SMS = preferencias.obtenerPermisos_Mensajes();

        this.switchContactos = (SwitchPreference) findPreference("switch_preferenciasContactos");
        this.switchSMS = (SwitchPreference) findPreference("switch_preferenciasSMS");


        switchContactos.setChecked(permisos_contactos);
        switchContactos.setEnabled(!permisos_contactos);
        switchContactos.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                onSwitch_Contactos();
                return false;
            }
        });

        switchContactos.setChecked(permisos_SMS);
        switchContactos.setEnabled(!permisos_SMS);
        switchSMS.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                onSwitch_SMS();
                return false;
            }
        });
    }


    public void onSwitch_Contactos(){
        preferencias.establecerPermisos_Contactos(switchContactos.isChecked());
    }

    public void onSwitch_SMS(){
        preferencias.establecerPermisos_SMS(switchSMS.isChecked());
    }

}
