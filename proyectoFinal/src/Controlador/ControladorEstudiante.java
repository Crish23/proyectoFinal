package Controlador;

import Modelo.*;
import java.io.*;
import java.util.*;

public class ControladorEstudiante {

    // Guardar array de estudiantes en archivo
    public static <T> void guardarEstudiantes(T persona) {
        String nombreArchivo = obtenerNombreArchivo(persona);

        // Leemos la lista existente (si hay)
        List<T> lista = (List<T>) cargarEstudiante(nombreArchivo);
        if (lista == null) {
            lista = new ArrayList<>();
        }

        // Agregamos el nuevo objeto
        lista.add(persona);

        // Guardamos la lista completa
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            out.writeObject(lista);
            System.out.println("Objeto guardado en: " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar el objeto en: " + nombreArchivo);
        }
    }

    // Cargar array de estudiantes desde archivo
    public static List<?> cargarEstudiante(String archivo) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<?>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
            return null;
        }
    }

    // Agregar un nuevo estudiante
    public static void agregarEstudiante(Persona persona) {
        guardarEstudiantes(persona);
        persona.setMulta(0);
    }

    // Buscar estudiante por cédula
    public static Persona buscarPorCedula(String cedula) {
        String[] archivos = {"estudiantes.dat", "docentes.dat", "administrativos.dat"};

        for (String archivo : archivos) {
            List<?> lista = cargarEstudiante(archivo);
            if (lista != null) {
                for (Object obj : lista) {
                    if (obj instanceof Persona) {
                        Persona persona = (Persona) obj;
                        if (cedula.equals(persona.getCedula())) {
                            return persona;
                        }
                    }
                }
            }
        }
        return null;
    }

    // Buscar estudiantes por nombre
    public static List<Persona> buscarPorNombre(String nombre) {
        String[] archivos = {"estudiantes.dat", "docentes.dat", "administrativos.dat"};
        List<Persona> personas = new ArrayList<>();

        for (String archivo : archivos) {
            List<?> lista = cargarEstudiante(archivo);
            if (lista != null) {
                for (Object obj : lista) {
                    if (obj instanceof Persona) {
                        Persona persona = (Persona) obj;
                        if (persona.getNombre().toLowerCase().contains(nombre.toLowerCase())||persona.getApellido().toLowerCase().contains(nombre.toLowerCase())) {
                            personas.add(persona);
                        }
                    }
                }
            }
        }
        return personas;
    }

    // Eliminar estudiante por cédula
    public static boolean eliminarEstudiante(String archivo, String cedula) {
        List<?> estudiantes = cargarEstudiante(archivo);
        if (estudiantes == null) {
            return false;
        }
        List<Object> otraLista = new ArrayList<>();
        boolean eliminado = false;

        for (Object obj : estudiantes) {
            if (obj instanceof Persona) {
                Persona persona = (Persona) obj;
                if (!persona.getCedula().equals(cedula)) {
                    otraLista.add(obj);
                } else {
                    eliminado = true;
                }
            }
        }

        if (eliminado) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
                out.writeObject(otraLista);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // Actualizar estudiante
    public static boolean actualizarEstudiante(String archivo, Persona estudianteActualizado) {
        List<?> estudiantes = cargarEstudiante(archivo);
        if (estudiantes == null) {
            return false;
        }
        List<Object> otraLista = new ArrayList<>();
        boolean actualizado = false;
        for (Object obj : estudiantes) {
            if (obj instanceof Persona) {
                Persona persona = (Persona) obj;
                if (persona.getCedula().equals(estudianteActualizado.getCedula())) {
                    otraLista.add(estudianteActualizado); // Reemplazo
                    actualizado = true;
                } else {
                    otraLista.add(persona); // Mantenimiento
                }
            }
        }
        if (actualizado) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
                out.writeObject(otraLista);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // Obtener todos los estudiantes (sin nulos)
    public static List<Persona> obtenerTodos(String archivo) {
        List<?> lista = cargarEstudiante(archivo);
    List<Persona> personas = new ArrayList<>();

    if (lista != null) {
        for (Object obj : lista) {
            if (obj instanceof Persona) {
                personas.add((Persona) obj);
            }
        }
    }

    return personas;
}

    private static <T> String obtenerNombreArchivo(T obj) {
        if (obj instanceof Estudiante) {
            return "estudiantes.dat";
        }
        else if (obj instanceof Docente) {
            return "docentes.dat";
        }
        else if (obj instanceof Administrativo) {
            return "administrativos.dat";
        }
        else if (obj instanceof Libro) {
            return "libros.dat";
        }
        else if (obj instanceof Revista) {
            return "revistas.dat";
        }
        else if (obj instanceof Tesis) {
            return "tesis.dat";
        }
        return "otros.dat";
    }
}
