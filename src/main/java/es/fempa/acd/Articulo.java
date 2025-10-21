
package es.fempa.acd;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entidad Articulo con título, anyo de publicación y número de palabras.
 * Es posible que existan dos artículos idénticos, para evitar problemas 
 * pondremos un id autogenerado.
 * @author franma
 */
@Entity
public class Articulo implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String titulo;
    private Integer anyo;
    private Integer numPalabras;

    public Articulo(String titulo, Integer anyo, Integer numPalabras) {
        this.titulo = titulo;
        this.anyo = anyo;
        this.numPalabras = numPalabras;
    }

    public Articulo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAnyo() {
        return anyo;
    }

    public void setAnyo(Integer anyo) {
        this.anyo = anyo;
    }

    public Integer getNumPalabras() {
        return numPalabras;
    }

    public void setNumPalabras(Integer numPalabras) {
        this.numPalabras = numPalabras;
    }

    @Override
    public String toString() {
        return "Articulo{" + "id=" + id + ", titulo=" + titulo + 
               ", a\u00f1o=" + anyo + ", numPalabras=" + numPalabras + '}';
    }
}
