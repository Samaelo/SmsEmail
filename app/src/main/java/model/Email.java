package model;

import java.util.Date;

/**
 * Created by Samael on 22/01/2017.
 */

public class Email {

    private String remitente, asunto, texto;
    private String[] destinatarios;
    private String fecha_de_envio;

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
