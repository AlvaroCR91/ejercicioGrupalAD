package es.fempa.acd;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import jakarta.persistence.*;

/**
 * Entidad Periodista con nombre, dni y teléfono. Además tendrá una lista
 * de artículos.
 * @author franma
 */
@Entity
public class Periodista implements Serializable {
    private String nombre;
    @Id
    private String dni; //clave
    private String numTel;
    
    //esto es un ejemplo de una relación 1:N unidireccional
    @OneToMany(cascade = CascadeType.ALL) //la operación que se aplique a un  
            //Periodista, se aplica en cascada a los artículos
    private List<Articulo> articulos;

    public Periodista(String nombre, String dni, String numTel) {
        this.nombre = nombre;
        this.dni = dni;
        this.numTel = numTel;
        this.articulos = new LinkedList<>(); //creamos la lista de artículos
    }

    public Periodista() {
    }

    /**
     * Permite anyadir un nuevo artículo a la lista de los escritos por este 
     * periodista
     */
    public void addArticulo(Articulo a) {
        articulos.add(a);
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    
    @Override
    //En el método toString solo se usan los datos del periodista, no la lista
    //de sus artículos.
    public String toString() {
        return "Periodista{" + "nombre=" + nombre + ", dni=" + dni + 
               ", numTel=" + numTel + '}';
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }
}
