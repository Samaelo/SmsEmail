package com.islasf.samaelmario.vista;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Samael on 07/01/2017.
 */

public class DialogoAlerta extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Alguna de las direcciones de correo no cumple un patrón válido. Por favor, revise los E-mails.")
                .setTitle("E-mail erróneo")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    public Dialog setDialogo(String mensaje, String titulo, String[]opciones, int codigo_tipo) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        switch (codigo_tipo) {

            case 1:
                builder.setMessage(mensaje)
                        .setTitle(titulo)
                        .setPositiveButton(opciones[0], new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                break;

            case 2:

                builder.setMessage(mensaje)
                        .setTitle(titulo)
                        .setPositiveButton(opciones[0], new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton(opciones[1], new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                break;
        }
        return builder.create();
    }
}
