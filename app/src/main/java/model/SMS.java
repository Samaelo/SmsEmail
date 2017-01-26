package model;

import java.util.Date;

/**
 * Created by Samael on 22/01/2017.
 */

public class SMS {

    private String destinatario, texto;
    private String fecha_de_envio;

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
