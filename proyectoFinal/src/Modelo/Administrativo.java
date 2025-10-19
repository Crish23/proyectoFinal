
package Modelo;

public class Administrativo extends Persona{
    private String facultad;
    public Administrativo(String cedula, String nombre, String apellido, int edad, String facultad, long multa) {
        super(cedula, nombre, apellido, edad, multa);
        this.facultad = facultad;
    }
    public void setFacultad(String facultad){
        this.facultad = facultad;
    }
    public String getFacultad(){
        return facultad;
    }
}
