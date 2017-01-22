package com.islasf.samaelmario.vista;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Mario on 22/01/2017.
 */

public class PreferenciasFragment extends PreferenceFragment {

    public static class OpcionesFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.ventana_preferencias);
        }


    }
}
