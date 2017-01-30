package model;

/**
 * Created by Mario on 13/01/2017.
 */

public class Constantes {

    /**
     *  Constantes de los códigos de las actividades para los resultset.
     */
    public static final int LISTA_CONTACTOS_ACTIVITY = 0;
    public static final int ENVIO_MAIL_ACTIVITY = 1;
    public static final int ENVIO_SMS_ACTIVITY = 2;
    public static final int MAIN_ACTIVITY = 3;
    public static final int PERFIL_ACTIVITY = 4;
    public static final int PREFERENCIAS_ACTIVITY = 5;

    /**
     *  Constantes para los put & get extra de los intents
     */
    public static final String LISTADO_CONTACTOS_SELECCIONADOS = "contactos_seleccionados";
    public static final String LISTADO_CONTACTOS_CARGADOS = "contactos_cargados";
    public static final String SELECCION_MULTIPLE = "lista_editable";
    public static final String LISTA_CARGADA = "contactos_cargados_boolean";

    /**
     * Constantes de opciones de diálogo
     */
    public static final int DIALOGO_VACIO = 0;
    public static final int DIALOGO_ACEPTAR = 1;
    public static final int DIALOGO_DOS_OPCIONES = 2;

        /**
        * Constantes de resultados de los botones de los diálogos.
        */
    public static final int ACEPTAR = 2;
    public static final int CANCELAR = 10;
    public static final int IGNORAR_DIALOGO = 0;
    /**
     * Requestcodes
     */

    public static final int REQUEST_PERMISOS_CONTACTOS = 0;
    public static final int REQUEST_PERMISOS_SMS = 1;

    /**
     * Preferencias del perfil
     */

    public static final String PERFIL_NOMBRE = "perfil_nombre";
    public static final String PERFIL_APELLIDOS = "perfil_apellidos";
    public static final String PERFIL_TFNO_FIJO = "perfil_tfno_fijo";
    public static final String PERFIL_TFNO_MOVIL = "perfil_tfno_movil";
    public static final String PERFIL_CORREO = "perfil_correo";
    public static final String PERFIL_FECHA = "perfil_fecha";
}
