package model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Clase destinada a la obtención y al almacenado de las preferencias de la aplicación tales como
 * el tema y los permisos de acceso de lectura a los contactos y de envío de SMS.
 */
public class Preferencias {

    /**
     * Constante que de termina la etiqueta clave para el acceso a la preferencia del permiso de
     * lectura de contactos.
     */
    private final String PERMISOS_CONTACTOS = "p_contactos";

    /**
     * Constante que de termina la etiqueta clave para el acceso a la preferencia del permiso de
     * envío de SMS.
     */
    private final String PERMISOS_SMS = "p_sms";

    /**
     * Constante que de termina la etiqueta clave para el acceso a la preferencia del tema escogido
     * o no por el usuario.
     */
    private final String TEMA = "tema";

    /**
     * Atributo de tipo SharedPreferences que permite el acceso a las preferencias almacenadas.
     */
    private SharedPreferences preferencias;

    /**
     * Atributo de tipo SharedPreferences.Editor que mediante el atributo "preferencias" permite
     * modificar las preferencias o agregar nuevas.
     */
    SharedPreferences.Editor editor;

    /**
     * Método constructor que recibe por parámetro el contexto  del que vamos a obtener un objeto
     * de tipo SharedPreferences, el cual usamos para la obtención y el almacenado de las preferencias.
     * Además da valor al atributo "editor", que hace uso del atributo "preferencias".
     * @param contexto - Contexto en el que se apoya la clase para obtener las preferencias (objeto de
     *                 tipo SharedPreferences).
     */
    public Preferencias(Context contexto){
        this.preferencias = contexto.getSharedPreferences("preferencias",Context.MODE_PRIVATE);
        this.editor = this.preferencias.edit();
    }
    //////////
    //
    //  Establecer preferencias
    //
    //////////

    /**
     * Método empleado para establecer los permisos de acceso de lectura a los contactos del teléfono.
     * @param valor - Atributo de tipo boolean que da valor a la preferencia de permisos de acceso
     *              de lectura a los contactos.
     */
    public void establecerPermisos_Contactos(boolean valor){
        editor.putBoolean(PERMISOS_CONTACTOS, valor);
    }

    /**
     * Método empleado para establecer los permisos de envío de SMS.
     * @param valor - Atributo de tipo boolean que da valor a la preferencia de permisos de envío
     *              de SMS.
     */
    public void establecerPermisos_SMS(boolean valor){
        editor.putBoolean(PERMISOS_SMS,valor);
    }

    /**
     * Método empleado para establecer el tema de la aplicación.
     * @param valor - Atributo de tipo String que da valor a la preferencia del tema de la aplicación.
     */
    public void establecer_Tema(String valor){
        editor.putString(TEMA,valor);
    }

    //////////
    //
    //  Obtener preferencias
    //
    //////////

    /**
     * Método cuya función es extraer el valor de la preferencia asociada a los permisos de lectura
     * de los contactos.
     * @return - El valor booleano asociado al permiso de los contactos. True si el permiso está
     * garantizado. False si no lo está.
     */
    public boolean obtenerPermisos_Contactos(){
       return preferencias.getBoolean(PERMISOS_CONTACTOS,false);
    }
    /**
     * Método cuya función es extraer el valor de la preferencia asociada a los permisos de envío
     * de SMS.
     * @return - El valor booleano asociado al permiso del envío SMS. True si el permiso está
     * garantizado. False si no lo está.
     */
    public boolean obtenerPermisos_Mensajes(){
        return preferencias.getBoolean(PERMISOS_SMS,false);
    }

    /**
     * Método cuya función es extraer el valor de la preferencia asociada al tema de la aplicación.
     * @return - El valor string asociado al tema escogido o no por el usuario.
     * Por defecto es estandar.
     */
    public String obtener_Tema(){
        return preferencias.getString(TEMA,"estandar");
    }


}
