/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import static Controlador.ControladorEstudiante.cargarEstudiante;
import Modelo.Administrativo;
import Modelo.Docente;
import Modelo.Estudiante;
import Modelo.Libro;
import Modelo.Revista;
import Modelo.Tesis;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class ControladorMaterial {
    public static <T> void guardarEstudiantes(T material) {
        String nombreArchivo = obtenerNombreArchivo(material);

        // Leemos la lista existente (si hay)
        List<T> lista = (List<T>) cargarEstudiante(nombreArchivo);
        if (lista == null) {
            lista = new ArrayList<>();
        }

        // Agregamos el nuevo objeto
        lista.add(material);

        // Guardamos la lista completa
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            out.writeObject(lista);
            System.out.println("Objeto guardado en: " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
    
    
    
    private static <T> String obtenerNombreArchivo(T obj) {
        if (obj instanceof Estudiante) {
            return "estudiantes.dat";
        }
        if (obj instanceof Docente) {
            return "docentes.dat";
        }
        if (obj instanceof Administrativo) {
            return "administrativos.dat";
        }
        if (obj instanceof Libro) {
            return "libros.dat";
        }
        if (obj instanceof Revista) {
            return "revistas.dat";
        }
        if (obj instanceof Tesis) {
            return "tesis.dat";
        }
        return "otros.dat";
    }
}
