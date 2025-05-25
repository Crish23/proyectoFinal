/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Usuario
 */
public class Estudiante extends Persona {
    
    private String carrera;
    public Estudiante(String cedula, String nombre, String apellido, int edad, String carrera) {
        super(cedula, nombre, apellido, edad);
        this.carrera = carrera;
    }
    
    public void setCarrera(String carrera){
        this.carrera = carrera;
    }
    public String getCarrera(){
        return carrera;
    }
}
