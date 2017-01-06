package model;

/**
 * Created by Mario on 06/01/2017.
 */

public class Contacto {
    private Perfil perfil;

    public Contacto(Perfil perfil){
        this.perfil = perfil;
    }

    public String obtener_tfno_movil(){
        return this.perfil.getNum_telefono_movil();
    }
}
