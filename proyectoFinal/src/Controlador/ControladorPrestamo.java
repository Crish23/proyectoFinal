package Controlador;

import static Controlador.ControladorEstudiante.cargarEstudiante;
import static Controlador.ControladorEstudiante.guardarEstudiantes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Modelo.*;

public class ControladorPrestamo {
    
    public void multar(String persona){
        
        Persona e = ControladorEstudiante.buscarPorCedula(persona);
        Prestamo p = buscarPrestamo(persona);
        if(p.getFechaF().isBefore(p.getFechaReal())){
            e.setMulta(0);
        }else if(p.getFechaF().isAfter(p.getFechaReal())){
            e.setMulta(ChronoUnit.DAYS.between(p.getFechaF(), p.getFechaReal())*1250);
        }
    }

    public static Prestamo buscarPrestamo(String perosna) {
        String archivo = "prestamos.dat";
            List<?> lista = cargarPrestamo(archivo);
            if (lista != null) {
                for (Object obj : lista) {
                    if (obj instanceof Prestamo) {
                        Prestamo persona = (Prestamo) obj;
                        if (perosna.equals(persona.getPersona())) {
                            return persona;
                        }
                    }
                }
            }
        
        return null;
    }
    
    public static <T> void guardarPrestamo(T material) {
        
        // Leemos la lista existente (si hay)
        List<T> lista = (List<T>) ControladorMaterial.cargarMaterial("prestamos.dat");
        if (lista == null) {
            lista = new ArrayList<>();
        }

        // Agregamos el nuevo objeto
        lista.add(material);

        // Guardamos la lista completa
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("prestamos.dat"))) {
            out.writeObject(lista);
            System.out.println("Objeto guardado en: " + "prestamos.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean pagarMulta(String persona){
        Persona es = ControladorEstudiante.buscarPorCedula(persona);
        es.setMulta(0);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("prestamos.dat"))) {
            out.writeObject(es);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean actualizar(Prestamo estudianteActualizado) {
        List<?> estudiantes = cargarEstudiante("prestamos.dat");
        if (estudiantes == null) {
            return false;
        }
        List<Object> otraLista = new ArrayList<>();
        boolean actualizado = false;
        for (Object obj : estudiantes) {
            if (obj instanceof Prestamo) {
                Prestamo persona = (Prestamo) obj;
                if (persona.getPersona().equals(estudianteActualizado.getPersona())) {
                    persona.setFechaF(persona.getFechaF().plusDays(15)) ; // Reemplazo
                    actualizado = true;
                } else {
                    otraLista.add(persona); // Mantenimiento
                }
            }
        }
        if (actualizado) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("prestamos.dat"))) {
                out.writeObject(otraLista);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean eliminarPrestamo(String cedula) {
        List<?> estudiantes = cargarEstudiante("prestamos.dat");
        if (estudiantes == null) {
            return false;
        }
        List<Object> otraLista = new ArrayList<>();
        boolean eliminado = false;

        for (Object obj : estudiantes) {
            if (obj instanceof Prestamo) {
                Prestamo persona = (Prestamo) obj;
                if (!persona.getPersona().equals(cedula)) {
                    otraLista.add(obj);
                } else {
                    eliminado = true;
                }
            }
        }

        if (eliminado) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("prestamos.dat"))) {
                out.writeObject(otraLista);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static void agregarMaterial(Prestamo material) {
        guardarPrestamo(material);
    }

    public static List<Prestamo> obtenerTodos(String archivo) {
        List<?> lista = cargarEstudiante(archivo);
    List<Prestamo> personas = new ArrayList<>();

    if (lista != null) {
        for (Object obj : lista) {
            if (obj instanceof Prestamo) {
                personas.add((Prestamo) obj);
            }
        }
    }

    return personas;
}

    public static void prestar(Persona usuario, List<String> libros){
        if (libros.size() > 3) {
        System.out.println("Solo puedes prestar hasta 3 libros.");
        return;
    }

    if (usuario == null) {
        System.out.println("No se puede realizar el préstamo porque el usuario es nulo.");
        return;
    }

    String[] archivos = {"libros.dat", "revistas.dat", "peliculas.dat"};
    List<String> librosAprestar = new ArrayList<>();
    // Para actualizar solo los archivos donde se prestó algo
    List<String> archivosActualizados = new ArrayList<>();
    List<List<Material>> listasActualizadas = new ArrayList<>();

    // Buscar y marcar como prestados los materiales en los archivos correspondientes
    for (String archivo : archivos) {
        List<Material> listaMateriales = (List<Material>) ControladorMaterial.cargarMaterial(archivo);
        if (listaMateriales == null) {
            continue;
        }
        boolean actualizado = false;
        for (String id : libros) {
            Optional<Material> materialOpt = listaMateriales.stream().filter(l -> l.getId().equals(id)).findFirst();
            if (materialOpt.isPresent()) {
                Material material = materialOpt.get();
                if (material.getEsta()) {
                    librosAprestar.add(id);
                    material.setEsta(false); // marcar como prestado
                    actualizado = true;
                } else {
                    System.out.println("Material con ID " + id + " ya está prestado.");
                }
            }
        }
        if (actualizado) {
            archivosActualizados.add(archivo);
            listasActualizadas.add(listaMateriales);
        }
    }

    if (librosAprestar.isEmpty()) {
        System.out.println("No se pudo prestar ningun material.");
        return;
    }

    // Crear y guardar el prestamo
    LocalDate hoy = LocalDate.now();
    LocalDate fechaEstimada = hoy.plusDays(15);

    Prestamo prestamo = new Prestamo(librosAprestar, usuario.getCedula(), hoy, fechaEstimada, null);
    guardarPrestamo(prestamo);

    // Guardar solo las listas de materiales que fueron modificadas
    for (int i = 0; i < archivosActualizados.size(); i++) {
        String archivo = archivosActualizados.get(i);
        List<Material> listaMateriales = listasActualizadas.get(i);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(listaMateriales);
        } catch (IOException e) {
            System.out.println("Error al actualizar el estado de los materiales en " + archivo + ": " + e.getMessage());
        }
    }

    System.out.println("Prestamo registrado con exito.");
    }

    public static void devolver(Persona usuario){
        String archivoPrestamos = "prestamos.dat";
        if (usuario == null) {
        System.out.println("El usuario no existe.");
        return;
        }

        List<?> lista = cargarPrestamo(archivoPrestamos);
        if (lista == null) {
            System.out.println("No se pudo cargar el archivo de préstamos.");
          return;
        }

    // Convertimos la lista genérica a una lista de Préstamos
    @SuppressWarnings("unchecked")
    List<Prestamo> prestamos = (List<Prestamo>) lista;

    Prestamo encontrado = buscarPrestamo(usuario.getCedula());
    if (encontrado == null) {
        System.out.println("No se encontró ningún préstamo activo para el usuario.");
        return;
    }


    guardarPrestamos(prestamos);
    }

    public static List<?> cargarPrestamo(String archivo) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
        return (List<Material>) in.readObject();
    } catch (Exception e) {
        return new ArrayList<>(); // devuelve lista vacía si ocurre error
    }
    }

    public static void guardarPrestamos(List<Prestamo> prestamos) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("prestamos.dat"))) {
        oos.writeObject(prestamos);
    } catch (IOException e) {
        System.out.println("Error al guardar el archivo: " + e.getMessage());
    }
}

    
}
