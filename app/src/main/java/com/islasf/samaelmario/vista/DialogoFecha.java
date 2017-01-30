package com.islasf.samaelmario.vista;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Mario García Ramos y Samael Picazo Navarrete. Fecha: 06/01/2017.
 */

public class DialogoFecha extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private PerfilActivity perfil_Activity; // Variable de tipo PerfilActivity

    /**
     * Método setter al cual igualamos la actividad en la cual se va a trabajar en la clase DialogoFecha a la actividad que recibe dicho método por parámetro.
     * @param actividad Variable del tipo PerfilActivity
     */
    public void setActivity(PerfilActivity  actividad){
        this.perfil_Activity = actividad;
    }

    /**
     * Este método muestra un Diálogo en el cual
     * @param savedInstanceState Variable de tipo Bundle que guarda la información de la actividad cuando se crea un diálogo.
     * @return
     */
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

        perfil_Activity.establecer_fecha(cal);
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
