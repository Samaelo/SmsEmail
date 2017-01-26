package model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mario on 04/01/2017.
 */

public class Perfil implements Serializable {

    private String nombre;
    private String apellidos;
    private String num_telefono_fijo;
    private String num_telefono_movil;
    private String direccion_correo;
    private Date fecha_nacimiento;

    public Perfil(String nombre, String apellidos, String num_telefono_fijo, String num_telefono_movil, String direccion_correo, Date fecha_nacimiento) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.num_telefono_fijo = num_telefono_fijo;
        this.num_telefono_movil = num_telefono_movil;
        this.direccion_correo = direccion_correo;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public Perfil(){

    }

    public void establecer_perfil(String nombre, String apellidos, String num_telefono_fijo, String num_telefono_movil, String direccion_correo){
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.num_telefono_fijo = num_telefono_fijo;
        this.num_telefono_movil = num_telefono_movil;
        this.direccion_correo = direccion_correo;
    }
    public void establecer_perfil(String nombre, String apellidos, String num_telefono_fijo, String num_telefono_movil, String direccion_correo, Date fecha_nacimiento){
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.num_telefono_fijo = num_telefono_fijo;
        this.num_telefono_movil = num_telefono_movil;
        this.direccion_correo = direccion_correo;
        this.fecha_nacimiento = fecha_nacimiento;
    }
    public void establecer_fecha(Calendar fecha_nueva){
        this.fecha_nacimiento = fecha_nueva.getTime();
    }

    public String getNum_telefono_movil() {
        return num_telefono_movil;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getNum_telefono_fijo() {
        return num_telefono_fijo;
    }

    public String getDireccion_correo() {
        return direccion_correo;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }
}
