package Modelo;

import java.io.Serializable;

public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String cedula;
    private String nombre;
    private String apellido;
    private int edad;
    private int multa;
    
    public Persona(String cedula, String nombre, String apellido, int edad) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.multa = multa;
    }
    
    
    
    // Getters y Setters
    public String getCedula() {
        return cedula;
    }
    public void setCedula() {
        this.cedula = cedula;
    }
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public int getEdad() {
        return edad;
    }
    
    public void setEdad(int edad) {
        this.edad = edad;
    }
    
    public void setMulta(int multa){
        this.multa = multa;
    }
    
    @Override
    public String toString() {
        return "Cédula: " + cedula + ", Nombre: " + nombre + " " + apellido + 
               ", Edad: " + edad;
    }
}
