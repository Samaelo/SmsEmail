package com.islasf.samaelmario.vista;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import model.Preferencias;

/**
 * Created by Mario on 22/01/2017.
 */

public class PreferenciasActivity extends AppCompatActivity {

    private PreferenciasFragment preferenciasFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenciasFragment = new PreferenciasFragment();
        preferenciasFragment.establecer_contexto(this);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, preferenciasFragment)
                .commit();


    }







}
