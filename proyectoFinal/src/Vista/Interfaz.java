package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interfaz extends JFrame {

    private void abrirSubmenuMateriales() {
        JPopupMenu submenu = new JPopupMenu();

        JMenuItem btnTesis = new JMenuItem("Tesis");
        btnTesis.addActionListener(e -> {
            InterfazTesis interfaz = new InterfazTesis();
            interfaz.setVisible(true);
        });
        

        JMenuItem btnLibros = new JMenuItem("Libros");
        btnLibros.addActionListener(e -> {
            InterfazLibros interfaz = new InterfazLibros();
            interfaz.setVisible(true);
        });
        
        JMenuItem btnRevistas = new JMenuItem("Revistas");
        btnRevistas.addActionListener(e -> {
            InterfazRevistas interfaz = new InterfazRevistas();
            interfaz.setVisible(true);
        });
        

        submenu.add(btnTesis);
        submenu.add(btnLibros);
        submenu.add(btnRevistas);

        submenu.show(this, getWidth() / 2, getHeight() / 2);
    }
    

    private void abrirSubmenuPersonas() {
        JPopupMenu submenu = new JPopupMenu();

        JMenuItem btnDocentes = new JMenuItem("Docentes");
        btnDocentes.addActionListener(e -> {
            InterfazDocentes interfaz = new InterfazDocentes();
            interfaz.setVisible(true);
        });
        

        JMenuItem btnEstudiantes = new JMenuItem("Estudiantes");
        btnEstudiantes.addActionListener(e -> {
            InterfazEstudiantes interfaz = new InterfazEstudiantes();
            interfaz.setVisible(true);
        });

        JMenuItem btnAdministrativos = new JMenuItem("Administrativos");
        btnAdministrativos.addActionListener(e -> {
            InterfazAdministrativos interfaz = new InterfazAdministrativos();
            interfaz.setVisible(true);
        });

        submenu.add(btnDocentes);
        submenu.add(btnEstudiantes);
        submenu.add(btnAdministrativos);

        submenu.show(this, getWidth() / 2, getHeight() / 2);
    }

    private void abrirVentana3() {
        InterfazPrestamo interfaz = new InterfazPrestamo();
        interfaz.setVisible(true);
    }

    private void abrirSubmenuReportes() {
        JPopupMenu submenu = new JPopupMenu();

        JMenu submenuMateriales = new JMenu("Materiales disponibles");
        JMenuItem btnLibros = new JMenuItem("Libros");
        JMenuItem btnTesis = new JMenuItem("Tesis");
        JMenuItem btnRevistas = new JMenuItem("Revistas");

        btnLibros.addActionListener(e -> mostrarMaterialesDisponibles("libros.dat"));
        btnTesis.addActionListener(e -> mostrarMaterialesDisponibles("tesis.dat"));
        btnRevistas.addActionListener(e -> mostrarMaterialesDisponibles("revistas.dat"));

        submenuMateriales.add(btnLibros);
        submenuMateriales.add(btnTesis);
        submenuMateriales.add(btnRevistas);

        JMenuItem btnPrestamosActivos = new JMenuItem("Préstamos activos");
        btnPrestamosActivos.addActionListener(e -> mostrarPrestamosActivos());

        JMenuItem btnMaterialesVencidos = new JMenuItem("Materiales vencidos");
        btnMaterialesVencidos.addActionListener(e -> mostrarMaterialesVencidos());

        JMenuItem btnReportes = new JMenuItem("Reportes estadísticos");
        btnReportes.addActionListener(e -> mostrarReportesEstadisticos());

        submenu.add(submenuMateriales);
        submenu.add(btnPrestamosActivos);
        submenu.add(btnMaterialesVencidos);
        submenu.add(btnReportes);

        submenu.show(this, getWidth() / 2, getHeight() / 2);
    }

    private void mostrarMaterialesDisponibles(String archivo) {
        java.util.List<Modelo.Material> lista = Controlador.ControladorMaterial.obtenerTodos(archivo);
        StringBuilder sb = new StringBuilder("Materiales disponibles en " + archivo + ":\n\n");
        for (Modelo.Material m : lista) {
            if (m.getEsta()) {
                sb.append(m.getTitulo()).append(" (ID: ").append(m.getId()).append(")\n");
            }
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void mostrarPrestamosActivos() {
        java.util.List<Modelo.Prestamo> prestamos = Controlador.ControladorPrestamo.obtenerTodos("prestamos.dat");
        StringBuilder sb = new StringBuilder("Préstamos activos:\n\n");
        for (Modelo.Prestamo p : prestamos) {
            if (p.getFechaReal() == null) {
                sb.append("Usuario: ").append(p.getPersona())
                  .append(" | Libros: ").append(p.getLibro()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void mostrarMaterialesVencidos() {
        java.util.List<Modelo.Prestamo> prestamos = Controlador.ControladorPrestamo.obtenerTodos("prestamos.dat");
        StringBuilder sb = new StringBuilder("Materiales vencidos:\n\n");
        java.time.LocalDate hoy = java.time.LocalDate.now();
        for (Modelo.Prestamo p : prestamos) {
            if (p.getFechaReal() == null && p.getFechaF().isBefore(hoy)) {
                sb.append("Usuario: ").append(p.getPersona())
                  .append(" | Libros: ").append(p.getLibro())
                  .append(" | Vencido desde: ").append(p.getFechaF()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void mostrarReportesEstadisticos() {
        JOptionPane.showMessageDialog(this, "Funcionalidad de reportes estadísticos en desarrollo.");
    }

    public Interfaz() {
        setTitle("Biblioteca C.J.G");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(2, 2, 10, 10));

        JButton btn1 = new JButton("Materiales Bibliográficos");
        JButton btn2 = new JButton("Gestión de usuarios");
        JButton btn3 = new JButton("Préstamos y devoluciones");
        JButton btn4 = new JButton("Búsquedas y reportes");

        btn1.addActionListener(e -> abrirSubmenuMateriales());
        btn2.addActionListener(e -> abrirSubmenuPersonas());
        btn3.addActionListener(e -> abrirVentana3());
        btn4.addActionListener(e -> abrirSubmenuReportes());

        panelBotones.add(btn1);
        panelBotones.add(btn2);
        panelBotones.add(btn3);
        panelBotones.add(btn4);

        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(panelBotones);
        setVisible(true);
    }
}