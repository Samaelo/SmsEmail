package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Clase destinada al almacenado de la información que contiene el objeto Contacto para facilitar
 * el trato de los atributos del mismo.
 */

public class Perfil implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Atributo de tipo String destinado a almacenar el nombre del contacto.
     */
    private String nombre;

    /**
     *Atributo de tipo String destinado a almacenar los apellidos del contacto.
     */
    private String apellidos;

    /**
     *Atributo de tipo String destinado a almacenar el número de teléfono fijo del contacto.
     */
    private String num_telefono_fijo;

    /**
     *Atributo de tipo String destinado a almacenar el número de teléfono móvil del contacto.
     */
    private String num_telefono_movil;

    /**
     * Atributo de tipo String destinado a almacenar la dirección de correo electrónico del contacto.
     */
    private String direccion_correo;

    /**
     * Atributo de tipo String destinado a almacenar la fecha de nacimiento del Contacto.
     */
    private Date fecha_nacimiento;


    /**
     * Método constructor.
     * @param nombre
     * @param apellidos
     * @param num_telefono_fijo
     * @param num_telefono_movil
     * @param direccion_correo
     * @param fecha_nacimiento
     */
    public Perfil(String nombre, String apellidos, String num_telefono_fijo, String num_telefono_movil, String direccion_correo, Date fecha_nacimiento) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.num_telefono_fijo = num_telefono_fijo;
        this.num_telefono_movil = num_telefono_movil;
        this.direccion_correo = direccion_correo;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    /**
     * Método constructor.
     */
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