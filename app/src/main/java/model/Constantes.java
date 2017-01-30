package model;

/**
 * Clase que contiene las constantes empleadas en la aplicación.
 */

public class Constantes {

    /**
     *  Constante que hace referencia a la actividad de la lista de los contactos.
     */
    public static final int LISTA_CONTACTOS_ACTIVITY = 0;

    /**
     *  Constante empleada para el paso de la lista de contactos seleccionados entre las
     *  actividades de envío mail/envio sms y de la lista de contactos.
     */
    public static final String LISTADO_CONTACTOS_SELECCIONADOS = "contactos_seleccionados";

    /**
     *  Constante empleada para el paso de la lista de contactos cargados entre las
     *  actividades de envío mail/envio sms y de la lista de contactos.
     */
    public static final String LISTADO_CONTACTOS_CARGADOS = "contactos_cargados";

    /**
     *  Constante empleada para el paso de la variable booleana  que indica si se puede
     *  seleccionar más de un contacto o no, entre las
     *  actividades de envío mail/envio sms y de la lista de contactos.
     */
    public static final String SELECCION_MULTIPLE = "lista_editable";

    /**
     *  Constante empleada para el paso de la variable que indica si la lista ya ha sido cargada entre las
     *  actividades de envío mail/envio sms y de la lista de contactos.
     */
    public static final String LISTA_CARGADA = "contactos_cargados_boolean";

    /**
     * Constante de tipo entero que indica qué tipo de diálogo es el que queremos al llamarlo.
     */
    public static final int DIALOGO_DOS_OPCIONES = 2;

        /**
        * Constante que hace referencia al resultado en caso en el que se acepte en el diálogo mostrado.
        */
    public static final int ACEPTAR = 2;

    /**
     * Constante que hace referencia al resultado en caso en el que se cancele en el diálogo mostrado.
     */
    public static final int CANCELAR = 10;

    /**
     * Constante que hace referencia al resultado en caso en el que se ignore el diálogo mostrado.
     */
    public static final int IGNORAR_DIALOGO = 0;

    /**
     * Constante de tipo entero que hace referencia al código de solicitud de permisos acceso
     * de lectura a los contactos del teléfono.
     */

    public static final int REQUEST_PERMISOS_CONTACTOS = 0;

    /**
     * Constante de tipo entero que hace referencia al código de solicitud de permisos acceso
     * al envío de SMS.
     */
    public static final int REQUEST_PERMISOS_SMS = 1;

    /**
     * Constante de tipo String que hace referencia al nombre del perfil del usuario a guardar en las preferencias.
     */
    public static final String PERFIL_NOMBRE = "perfil_nombre";

    /**
     * Constante de tipo String que hace referencia a los apellidos del perfil del usuario a guardar en las preferencias.
     */
    public static final String PERFIL_APELLIDOS = "perfil_apellidos";

    /**
     * Constante de tipo String que hace referencia al teléfono fijo del perfil del usuario a guardar en las preferencias.
     */
    public static final String PERFIL_TFNO_FIJO = "perfil_tfno_fijo";

    /**
     * Constante de tipo String que hace referencia al teléfono móviil del perfil del usuario a guardar en las preferencias.
     */
    public static final String PERFIL_TFNO_MOVIL = "perfil_tfno_movil";

    /**
     * Constante de tipo String que hace referencia al correo electrónico del perfil del usuario a guardar en las preferencias.
     */
    public static final String PERFIL_CORREO = "perfil_correo";

    /**
     * Constante de tipo String que hace referencia a la fecha de nacimiento del perfil del usuario a guardar en las preferencias.
     */
    public static final String PERFIL_FECHA = "perfil_fecha";
}
