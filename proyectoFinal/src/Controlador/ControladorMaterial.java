package Controlador;

import static Controlador.ControladorEstudiante.cargarEstudiante;
import Modelo.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ControladorMaterial {
    


    public static <T> void guardarEstudiantes(T material) {
        String nombreArchivo = obtenerNombreArchivo(material);

        // Leemos la lista existente (si hay)
        List<T> lista = (List<T>) cargarMaterial(nombreArchivo);
        if (lista == null) {
            lista = new ArrayList<>();
        }

        // Agregamos el nuevo objeto
        lista.add(material);

        // Depuración: Verifica si llega aquí
        System.out.println("Antes del try para guardar en: " + nombreArchivo);

        // Guardamos la lista completa
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            out.writeObject(lista);
            System.out.println("Objeto guardado en: " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Cargar materiales desde un archivo
    public static List<?> cargarMaterial(String archivo) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
        return (List<Material>) in.readObject();
    } catch (Exception e) {
        return new ArrayList<>(); // devuelve lista vacía si ocurre error
    }
    }
    
    // Buscar material por título (o ID)
    public static Material buscarPorTitulo(String titulo) {
        String[] archivos = {"libros.dat", "tesis.dat", "revistas.dat"};

        for (String archivo : archivos) {
            List<?> lista = cargarMaterial(archivo);
            if (lista != null) {
                for (Object obj : lista) {
                    if (obj instanceof Material) {
                        Material persona = (Material) obj;
                        if (titulo.equals(persona.getTitulo())||titulo.equals(persona.getId())) {
                            return persona;
                        }
                    }
                }
            }
        }
        return null;
    }
    
    // Buscar materiales por autor
    public static List<Material> buscarPorAutor(String nombre) {
        String[] archivos = {"libros.dat", "tesis.dat", "revistas.dat"};
        List<Material> personas = new ArrayList<>();

        for (String archivo : archivos) {
            List<?> lista = cargarMaterial(archivo);
            if (lista != null) {
                for (Object obj : lista) {
                    if (obj instanceof Material) {
                        Material persona = (Material) obj;
                        if (persona.getAutor().toLowerCase().contains(nombre.toLowerCase())) {
                            personas.add(persona);
                        }
                    }
                }
            }
        }
        return personas;
    }
    
    // Eliminar material por título (o ID)
    public static boolean eliminarMaterial(String archivo, String cedula) {
        List<?> estudiantes = cargarMaterial(archivo);
        if (estudiantes == null) {
            return false;
        }
        List<Object> otraLista = new ArrayList<>();
        boolean eliminado = false;

        for (Object obj : estudiantes) {
            if (obj instanceof Material) {
                Material persona = (Material) obj;
                if (!persona.getTitulo().equals(cedula)) {
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
    

  // Actualizar material
    public static boolean actualizarMaterial(String archivo, Material estudianteActualizado) {
        List<?> estudiantes = cargarMaterial(archivo);
        if (estudiantes == null) {
            return false;
        }
        List<Object> otraLista = new ArrayList<>();
        boolean actualizado = false;
        for (Object obj : estudiantes) {
            if (obj instanceof Material) {
                Material material = (Material) obj;
                if (material.getTitulo().equals(estudianteActualizado.getTitulo())) {
                    otraLista.add(estudianteActualizado); // Reemplazo
                    actualizado = true;
                } else {
                    otraLista.add(material); // Mantenimiento
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

 // Obtener todos los materiales (sin nulos)
    public static List<Material> obtenerTodos(String archivo) {
        List<?> lista = cargarMaterial(archivo);
    List<Material> personas = new ArrayList<>();

    if (lista != null) {
        for (Object obj : lista) {
            if (obj instanceof Material) {
                personas.add((Material) obj);
            }
        }
    }

    return personas;
}

    // Nuevo método para guardar material en un archivo específico
    public static void guardarMaterial(String archivo, Material material) {
        List<Material> lista = (List<Material>) cargarMaterial(archivo);
        if (lista == null) {
            lista = new ArrayList<>();
        }
        lista.add(material);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Método para generar un ID único para el material
    public static int comprobarId(){

        int id = (int) (Math.random() * (1000000 - 10000000 + 1)) + 10000000; // Genera un ID aleatorio de 7 dígitos
        String[] archivos = {"libros.dat", "tesis.dat", "revistas.dat"};

        for (String archivo : archivos) {
            List<?> lista = cargarMaterial(archivo);
            if (lista != null) {
                for (Object obj : lista) {
                    if (obj instanceof Material) {
                        Material material = (Material) obj;
                        if (material.getId().equals(String.valueOf(id))) {
                            return comprobarId(); // Si el ID ya existe, generamos otro
                        }
                    }
                }
            }
        }
        return id;
    }

    // Método para obtener el nombre del archivo según el tipo de objeto

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

