package model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mario on 06/01/2017.
 */

public class Contacto implements Serializable {
    private Perfil perfil;

    public Contacto(Perfil perfil){
        this.perfil = perfil;
    }

    public String obtener_tfno_movil(){
        return this.perfil.getNum_telefono_movil();
    }
    public String obtener_tfno_fijo(){
        return this.perfil.getNum_telefono_fijo();
    }
    public String obtener_nombre(){
        return this.perfil.getNombre();
    }
    public String obtener_correo(){
        return this.perfil.getDireccion_correo();
    }
    public String obtener_apellidos(){
        return this.perfil.getApellidos();
    }
    public Date obtener_fecha(){
        return this.perfil.getFecha_nacimiento();
    }
    public Perfil obtener_perfil(){
        return this.perfil;
    }
}
