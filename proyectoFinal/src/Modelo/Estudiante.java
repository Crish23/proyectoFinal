
package Modelo;


public class Estudiante extends Persona {
    
    private String carrera;
    public Estudiante(String cedula, String nombre, String apellido, int edad, String carrera, long multa) {
        super(cedula, nombre, apellido, edad, multa);
        this.carrera = carrera;
    }
    
    public void setCarrera(String carrera){
        this.carrera = carrera;
    }
    public String getCarrera(){
        return carrera;
    }
}
