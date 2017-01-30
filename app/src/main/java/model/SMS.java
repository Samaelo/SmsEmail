package model;


/**
 * Clase cuya finalidad es el almacenado de los datos de un SMS.
 */

public class SMS {

    /**
     * Atributo de tipo String que hace referencia al destinatario que recibirá el mensaje.
     */
    private String destinatario;

    /**
     * Atributo de tipo String que hace referencia al contenido del mensaje.
     */
    private String texto;

    /**
     * Atributo de tipo String que hace referencia a la fecha de envío.
     */
    private String fecha_de_envio;

    /**
     * Método constructor.
     * @param destinatario
     * @param texto
     * @param fecha_de_envio
     */
    public SMS(String destinatario, String texto, String fecha_de_envio){

        this.destinatario = destinatario;
        this.texto = texto;
        this.fecha_de_envio = fecha_de_envio;
    }

    public String getTexto() {
        return texto;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getFecha_de_envio() {
        return fecha_de_envio;
    }
}
