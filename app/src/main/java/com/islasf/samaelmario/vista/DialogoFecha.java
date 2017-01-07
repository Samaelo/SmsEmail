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
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(anno,mes,dia);

        actividad.establecer_fecha(cal);
        try {
            dismiss();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
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
