package com.islasf.samaelmario.vista;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import model.Constantes;

/**
 * Creado por Mario García Ramos y Samael Picazo Navarrete. Fecha : 07/01/2017.
 */

public class DialogoAlerta extends DialogFragment {

    private String mensaje,titulo;
    private String[] opciones;
    private int codigo_tipo;
    private FuncionalidadesComunes actividad; // Objeto a través del cual accedemos a los métodos de la interfaz FuncionalidadesComunes

    AlertDialog.Builder builder; // Objeto de tipo AlertDialog

    /**
     * Este método (sobrescrito) sirve para crear un diálogo. En él se instancia un objeto de tipo AlertDialog.Builder(Activity). Posteriormente se llama al método 'crear_dialogo()'.
     * @param savedInstanceState Objeto de la clase Bundle que guarda el estado de la aplición cuando surge algún otro evento.
     * @return Retorna un objeto de tipo Dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        builder = new AlertDialog.Builder(getActivity());
        crear_dialogo();
        return builder.create();
    }

    /**
     * Método encargado de sustituir al constructor por defecto (que debe estar vacío).
     * @param mensaje Variable de tipo String que hace referencia al mensaje que se va a mostrar en el AlertDialog
     * @param titulo Variable de tipo String que hace referencia al título que se va a mostrar en el AlertDialog
     * @param opciones Variable de tipo Array de String que hace referencia a las opciones que se muestran en el AlertDialog para que el usuario interactúe con él.
     * @param codigo_tipo Variable de tipo Integer que hace referencia al tipo de mensaje que se va a enviar. En caso de que sea 0, se muestra un alert dialog con un mensaje y un título.
     *                    En caso de que el código sea 1, se muestra un AlertDialog con un mensaje, un título y con una única opción, Aceptar.
     *                    Enc aso de que el código sea 2, se muestra un AlertDialog con un mensaje, un título y con dos opciones, Aceptar y Cancelar.
     */
    public void setDialogo(FuncionalidadesComunes actividad, String mensaje, String titulo, String[]opciones, int codigo_tipo) {
        this.mensaje = mensaje;
        this.titulo = titulo;
        this.opciones = opciones;
        this.actividad = actividad;
        this.codigo_tipo = codigo_tipo;
    }

    /**
     * Este método consiste en crear un diálago dependiendo del mensaje, el título del mismo, las opciones que se van a mostrar al usuario en el AlertDialog (en este caso, Aceptar o Cancelar)
     */
    private void crear_dialogo(){

        switch (codigo_tipo) {
            case 0:
                builder.setMessage(mensaje).setTitle(titulo);
                break;
            case 1:
                builder.setMessage(mensaje)
                        .setTitle(titulo)
                        .setPositiveButton(opciones[0], new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                actividad.onAlerta(1, Constantes.ACEPTAR);
                                dialog.cancel();
                            }
                        });
                break;

            case 2:

                builder.setMessage(mensaje)
                        .setTitle(titulo)
                        .setPositiveButton(opciones[0], new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                actividad.onAlerta(2,Constantes.ACEPTAR);
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton(opciones[1], new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                actividad.onAlerta(2,Constantes.CANCELAR);
                                dialog.cancel();
                            }
                        });
                break;
        }
    }

    /**
     * Este método consiste en cerrar el AlertDialog.
     * @param dialog Objeto de la clase DialogInterface
     */
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        actividad.onAlerta(0,Constantes.IGNORAR_DIALOGO);
    }
}
