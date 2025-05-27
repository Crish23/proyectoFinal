
package Modelo;

import java.io.Serializable;

public class Libro extends Material{
    
    public Libro(String id, String titulo, String autor, long ano, boolean esta) {
        super(id, titulo, autor, ano, esta);
    }
    
}
