package com.islasf.samaelmario.vista;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Mario on 06/01/2017.
 */

public class DialogoFecha extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    int[] fecha = new int[3];
    private GregorianCalendar calendario;

    private PerfilActivity actividad;

    public void setActivity(PerfilActivity  actividad){
        this.actividad = actividad;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int anno = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, anno, mes, dia);
    }


    @Override
    public void onDateSet(DatePicker datePicker, int anno, int mes, int dia) {
        fecha[0] = dia;
        fecha[1] = mes+1;
        fecha[2] = anno;
        actividad.obtener_fecha(fecha);
    }



    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        try {
            finalize();
        } catch (Throwable throwable) {
        }
    }

}
