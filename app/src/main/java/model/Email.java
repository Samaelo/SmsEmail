package model;

import java.util.Date;

/**
 * Clase  destinada al almacenado de la información requerida para el envío de un email.
 */

public class Email {

    /**
     * Atributo de tipo String que hace referencia al remitente del mensaje.
     */
    private String remitente;

    /**
     * Atributo de tipo String que hace referencia al asunto del mensaje.
     */
    private  String asunto;

    /**
     * Atributo de tipo String que hace referencia al texto del mensaje.
     */
    private String texto;

    /**
     *Atributo de tipo Array primitivo de String que hace referencia al conjunto de destinatarios que
     * recibirán el mensaje.
     */
    private String[] destinatarios;

    /**
     *Atributo de tipo String que hace referencia a la fecha de envío del mensaje.
     */
    private String fecha_de_envio;

    /**
     * Método constructor que recibe por parámetro el remitente, el destinatario, el asunto, el texto del mensaje y la fecha de envío del mismo.
     * @param remitente - Parámetro que da valor al atributo remitente.
     * @param destinatarios - Parámetro que da valor al atributo destinatarios.
     * @param asunto - Parámetro que da valor al atributo asunto.
     * @param texto - Parámetro que da valor al atributo texto.
     * @param fecha_de_envio - Parámetro que da valor al atributo de fecha de envío.
     */
    public Email(String remitente, String[] destinatarios, String asunto, String texto, String fecha_de_envio){

        this.remitente = remitente;
        this.destinatarios = destinatarios;
        this.asunto = asunto;
        this.texto = texto;
        this.fecha_de_envio = fecha_de_envio;
    }

    public String getRemitente() {
        return remitente;
    }

    public String getAsunto() {
        return asunto;
    }

    public String getTexto() {
        return texto;
    }

    public String[] getDestinatarios() {

        return destinatarios;
    }

    public String getFecha_de_envio() {
        return fecha_de_envio;
    }





}
