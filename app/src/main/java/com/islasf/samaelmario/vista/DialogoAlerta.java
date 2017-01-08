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
    private String mensaje,titulo;
    private String[] opciones;
    private int codigo_tipo;
    private Alerta actividad;

    AlertDialog.Builder builder;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        builder = new AlertDialog.Builder(getActivity());

        crear_dialogo();
        return builder.create();
    }

    /**
     * Método encargado de sustituir al constructor por defecto (que debe estar vacío).
     * @param mensaje
     * @param titulo
     * @param opciones
     * @param codigo_tipo
     */
    public void setDialogo(Alerta actividad,String mensaje, String titulo, String[]opciones, int codigo_tipo) {
        this.mensaje = mensaje;
        this.titulo = titulo;
        this.opciones = opciones;
        this.codigo_tipo = codigo_tipo;
        this.actividad = actividad;

    }

    private void crear_dialogo(){
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
                                actividad.onAlerta();
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
    }
}
