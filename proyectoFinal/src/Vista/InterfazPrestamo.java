package Vista;

import Controlador.ControladorPrestamo;
import Modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class InterfazPrestamo extends JFrame {

    private JTable tablaPrestamos;
    private DefaultTableModel modeloTabla;
    private JTextField txtCedulaFormulario, txtLibros, txtCedulaBusqueda;
    private JButton btnPrestar, btnDevolver, btnBuscar, btnMostrarTodos;

    public InterfazPrestamo() {
        setTitle("Gestión de Préstamos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Panel formulario
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Préstamo"));

        panelFormulario.add(new JLabel("Cédula del Usuario:"));
        txtCedulaFormulario = new JTextField();
        panelFormulario.add(txtCedulaFormulario);

        panelFormulario.add(new JLabel("IDs de Libros (separados por coma):"));
        txtLibros = new JTextField();
        panelFormulario.add(txtLibros);

        btnPrestar = new JButton("Registrar Préstamo");
        btnDevolver = new JButton("Devolver");

        panelFormulario.add(btnPrestar);
        panelFormulario.add(btnDevolver);

        // Tabla de préstamos
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Cédula");
        modeloTabla.addColumn("Libros");
        modeloTabla.addColumn("Fecha Inicio");
        modeloTabla.addColumn("Fecha Fin");
        modeloTabla.addColumn("Fecha Real");

        tablaPrestamos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaPrestamos);

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Lista de Préstamos"));
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        // Panel búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtCedulaBusqueda = new JTextField(15);
        btnBuscar = new JButton("Buscar");
        btnMostrarTodos = new JButton("Mostrar Todos");

        panelBusqueda.add(new JLabel("Cédula:"));
        panelBusqueda.add(txtCedulaBusqueda);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnMostrarTodos);

        // Ensamblar paneles
        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);
        panelPrincipal.add(panelBusqueda, BorderLayout.CENTER);
        panelPrincipal.add(panelTabla, BorderLayout.SOUTH);

        add(panelPrincipal);

        // Listeners
        btnPrestar.addActionListener(e -> registrarPrestamo());
        btnDevolver.addActionListener(e -> devolverPrestamo());
        btnBuscar.addActionListener(e -> buscarPrestamo());
        btnMostrarTodos.addActionListener(e -> cargarTabla(ControladorPrestamo.obtenerTodos("prestamos.dat")));

        // Cargar préstamos al iniciar
        cargarTabla(ControladorPrestamo.obtenerTodos("prestamos.dat"));
    }

    private void registrarPrestamo() {
        String cedula = txtCedulaFormulario.getText().trim();
        String librosTxt = txtLibros.getText().trim();

        System.out.println("Buscando usuario con cédula: '" + cedula + "'");

        if (cedula.isEmpty() || librosTxt.length() < 1) {
            JOptionPane.showMessageDialog(this, "Debe llenar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Persona usuario = Controlador.ControladorEstudiante.buscarPorCedula(cedula);
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }else if(usuario != null) {
            System.out.println("Usuario encontrado: " + usuario);
        }

        List<String> libros = List.of(librosTxt.split("\\s*,\\s*"));

        ControladorPrestamo.prestar(usuario, libros);
        cargarTabla(ControladorPrestamo.obtenerTodos("prestamos.dat"));
    }

    private void devolverPrestamo() {
        String cedula = txtCedulaBusqueda.getText();
        System.out.println("Valor en txtCedulaBusqueda: '" + cedula + "'");
        cedula = cedula.trim();
        // Si el campo de búsqueda está vacío, intenta usar el del formulario
        if (cedula.isEmpty()) {
            cedula = txtCedulaFormulario.getText().trim();
            System.out.println("Valor alternativo en txtCedulaFormulario: '" + cedula + "'");
        }

        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una cédula", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Persona usuario = Controlador.ControladorEstudiante.buscarPorCedula(cedula);
        if (usuario != null) {
            ControladorPrestamo.devolver(usuario);
            cargarTabla(ControladorPrestamo.obtenerTodos("prestamos.dat"));
        } else {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarPrestamo() {
        String cedula = txtCedulaBusqueda.getText().trim();
        if (cedula.isEmpty()) return;

        Prestamo p = ControladorPrestamo.buscarPrestamo(cedula);
        if (p != null) {
            cargarTabla(List.of(p));
        } else {
            JOptionPane.showMessageDialog(this, "Préstamo no encontrado", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void cargarTabla(List<Prestamo> prestamos) {
        modeloTabla.setRowCount(0);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Prestamo p : prestamos) {
            modeloTabla.addRow(new Object[]{
                p.getPersona(),
                String.join(",", p.getLibro()),
                p.getFechaInicio().format(formato),
                p.getFechaF().format(formato),
                (p.getFechaReal() != null ? p.getFechaReal().format(formato) : "Pendiente")
            });
        }
    }
}