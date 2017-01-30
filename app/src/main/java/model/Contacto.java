package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Clase cuya finalidad es el almacenado de la información de los contactos leídos del teléfono,
 * mediante un objeto de la clase "Perfil".
 */

public class Contacto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Atributo de tipo entero empleado para la selección correcta de los contactos seleccionados
     * en el listview.
     */
    private int id;

    /**
     * Perfil empleado para almacenar los datos del contacto dentro del mismo.
     */
    private Perfil perfil;

    /**
     * Método constructor que recibe por parámetro los valores que van a tomar la id y el perfil.
     * @param id - Parámetro destinado a dar valor al atributo id.
     * @param perfil - Parámetro destinado a dar valor al atributo perfil.
     */
    public Contacto(int id,Perfil perfil){
        this.id = id;
        this.perfil = perfil;
    }

    /**
     *
     * @return
     */
    public int obtener_id(){
        return this.id;
    }

    /**
     *
     * @return
     */
    public String obtener_tfno_movil(){
        return this.perfil.getNum_telefono_movil();
    }

    /**
     *
     * @return
     */
    public String obtener_tfno_fijo(){
        return this.perfil.getNum_telefono_fijo();
    }

    /**
     *
     * @return
     */
    public String obtener_nombre(){
        return this.perfil.getNombre();
    }

    /**
     *
     * @return
     */
    public String obtener_correo(){
        return this.perfil.getDireccion_correo();
    }

    /**
     *
     * @return
     */
    public String obtener_apellidos(){
        return this.perfil.getApellidos();
    }

    /**
     *
     * @return
     */
    public Date obtener_fecha(){
        return this.perfil.getFecha_nacimiento();
    }

    /**
     *
     * @return
     */
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