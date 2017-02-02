package com.islasf.samaelmario.vista;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Esta clase sirve para mostrar un calendario al usuario para que pueda elegir una fecha de cumpleaños, etc...
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
     * Este método muestra un Diálogo en el cual se muestra un calendario donde el usuario podrá elegir su fecha de nacimiento.
     * @param savedInstanceState Variable de tipo Bundle que guarda la información de la actividad cuando se crea un diálogo.
     * @return Retorna un objeto de la clase DatePicxkerDialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int anno = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);

         // Creamos una nueva instancia de PickerDialog y la retornamos
        return new DatePickerDialog(getActivity(), this, anno, mes, dia);
    }

    /**
     * Método sobrescrito de la clase DatePickerDialog que se encarga de recoger la fecha del calendario
     * @param datePicker Objeto de la clase DatePicker
     * @param anno Variable de tipo integer que hace referencia al año del calendario
     * @param mes Variable de tipo integer que hace referencia al mes del calendario
     * @param dia Variable de tipo integer que hace referencia al dia del calendario
     */
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

    /**
     * Método sobrescrito de la clase DatePickerDialog, el cual cierra el diálogo que muestra el calendario.
     * @param dialog Variable de tipo DialogInterface
     */
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        try {
            finalize();
        } catch (Throwable throwable) {
        }
    }
}