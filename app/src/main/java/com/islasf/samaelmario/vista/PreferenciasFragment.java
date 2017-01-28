package com.islasf.samaelmario.vista;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

/**
 * Created by Mario on 22/01/2017.
 */

public class PreferenciasFragment extends PreferenceFragment {

    private SwitchPreference switchSMS,switchContactos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.ventana_preferencias);

        cargar_componentes();
    }


    /**
     * Método encargado de cargar los componentes gráficos de las preferencias.
     */
    private void cargar_componentes(){
        this.switchContactos = (SwitchPreference) findPreference("switchContactos");
        this.switchSMS = (SwitchPreference) findPreference("switchSMS");
    }

    private void onSwitch_Contactos(){
        if(switchContactos.isChecked()){
            //Pedir permisos. si pulsa que no, lo desmarcamos de nuevo.
        }else{
            //
        }
    }
}
