package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mario on 06/01/2017.
 */

public class Contacto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private Perfil perfil;
    public Contacto(int id,Perfil perfil){
        this.id = id;
        this.perfil = perfil;
    }

    public int obtener_id(){
        return this.id;
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

/*
    protected Contacto(Parcel in) {
        perfil = (Perfil) in.readValue(Perfil.class.getClassLoader());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(perfil);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Contacto> CREATOR = new Parcelable.Creator<Contacto>() {
        @Override
        public Contacto createFromParcel(Parcel in) {
            return new Contacto(in);
        }

        @Override
        public Contacto[] newArray(int size) {
            return new Contacto[size];
        }
    };
     */

}