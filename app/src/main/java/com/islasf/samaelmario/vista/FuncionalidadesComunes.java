package com.islasf.samaelmario.vista;

/**
 * Created by Mario on 08/01/2017.
 */

public interface FuncionalidadesComunes {
    /**
     * Método enfocado a ser ejecutado en las alertas invocadas por la actividad que implemente
     * esta interfaz.
     *
     * @param objeto - Parámetro que puede recibir de 0 a varios objetos de tipo Object.
     */
    public void onAlerta(Object... objeto);

    /**
     * Método enfocado a ser ejecutado en el método doInBackground() de la clase que herede de
     * AsyncTask invocada por la actividad que implemente esta interfaz.
     *
     * @param objeto - Parámetro que puede recibir de 0 a varios objetos de tipo Object.
     */
    public void onAsyncTask(Object... objeto);

}
