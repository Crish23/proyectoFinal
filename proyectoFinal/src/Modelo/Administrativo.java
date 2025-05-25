/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Usuario
 */
public class Administrativo extends Persona{
    private String facultad;
    public Administrativo(String cedula, String nombre, String apellido, int edad, String facultad) {
        super(cedula, nombre, apellido, edad);
        this.facultad = facultad;
    }
    public void setFacultad(String facultad){
        this.facultad = facultad;
    }
    public String getFacultad(){
        return facultad;
    }
}
