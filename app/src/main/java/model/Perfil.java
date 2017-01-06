package model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mario on 04/01/2017.
 */

public class Perfil {
//
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


    public void establecer_perfil(String nombre, String apellidos, String num_telefono_fijo, String num_telefono_movil, String direccion_correo, Date fecha_nacimiento){
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.num_telefono_fijo = num_telefono_fijo;
        this.num_telefono_movil = num_telefono_movil;
        this.direccion_correo = direccion_correo;
        this.fecha_nacimiento = fecha_nacimiento;
    }
    public void establecer_fecha(Calendar fecha_nueva){
        Date fecha = fecha_nueva.getTime();
    }
}
