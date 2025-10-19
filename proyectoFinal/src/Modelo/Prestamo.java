package Modelo;

import java.time.LocalDate;
import java.util.List;
import java.time.LocalDate;
import Modelo.*;

public class Prestamo {
    private List<String> libro;
    private String persona;
    private LocalDate fechaInicio;
    private LocalDate fechaF;
    private LocalDate fechaReal;
    
    public Prestamo(List<String> libro, String persona, LocalDate fechaInicio,LocalDate fechaF, LocalDate fechaReal){
        this.libro = libro;
        this.persona = persona;
        this.fechaInicio = fechaInicio;
        this.fechaF = fechaF;
        this.fechaReal = null;
    }

    public List<String> getLibro() {
        return libro;
    }

    public void setLibro(List<String> libro){
        this.libro = libro;
    }

    public String getPersona(){
        return persona;
    }

    public void setPersona(String persona){
        this.persona = persona;
    }

    public LocalDate getFechaInicio(){
        return fechaInicio;
    }
    
    public void setFechaInicio(LocalDate fechaInicio){
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaF(){
        return fechaF;
    }

    public void setFechaF(LocalDate fechaF){
        this.fechaF = fechaF;
    }

    public LocalDate getFechaReal(){
        return fechaReal;
    }

    public void setFechaReal(LocalDate fechaReal){
        this.fechaReal = fechaReal;
    }

    @Override
    public String toString() {
        return "Libro: " + libro + ", Persona: " + persona + "Fecha de inicio: " + fechaInicio + 
               ", Fecha Final: " + fechaF + ", Fecha Real: " + (fechaReal != null ? fechaReal : "pendiente");
    }
}
