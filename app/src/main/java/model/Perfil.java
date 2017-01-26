package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mario on 04/01/2017.
 */
public class Perfil implements Serializable {

    private static final long serialVersionUID = 1L;
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

    /*
    protected Perfil(Parcel in) {
        nombre = in.readString();
        apellidos = in.readString();
        num_telefono_fijo = in.readString();
        num_telefono_movil = in.readString();
        direccion_correo = in.readString();
        long tmpFecha_nacimiento = in.readLong();
        fecha_nacimiento = tmpFecha_nacimiento != -1 ? new Date(tmpFecha_nacimiento) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeString(num_telefono_fijo);
        dest.writeString(num_telefono_movil);
        dest.writeString(direccion_correo);
        dest.writeLong(fecha_nacimiento != null ? fecha_nacimiento.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Perfil> CREATOR = new Parcelable.Creator<Perfil>() {
        @Override
        public Perfil createFromParcel(Parcel in) {
            return new Perfil(in);
        }

        @Override
        public Perfil[] newArray(int size) {
            return new Perfil[size];
        }
    };

    */
}