package Vista;

import Controlador.ControladorEstudiante;
import Modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InterfazEstudiantes extends JFrame {

    private JTable tablaEstudiantes;
    private DefaultTableModel modeloTabla;
    private JTextField txtCedula, txtNombre, txtApellido,
            txtEdad, txtCarrera, txtBuscar;

    public InterfazEstudiantes() {
        setTitle("Gestión de Estudiantes");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Estudiante"));

        panelFormulario.add(new JLabel("Cédula:"));
        txtCedula = new JTextField();
        panelFormulario.add(txtCedula);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panelFormulario.add(txtApellido);

        panelFormulario.add(new JLabel("Edad:"));
        txtEdad = new JTextField();
        panelFormulario.add(txtEdad);

        panelFormulario.add(new JLabel("Carrera:"));
        txtCarrera = new JTextField();
        panelFormulario.add(txtCarrera);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnAgregar = new JButton("Agregar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new BorderLayout(5, 5));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Buscar"));

        txtBuscar = new JTextField();
        JButton btnBuscar = new JButton("Buscar");
        JButton btnMostrarTodos = new JButton("Mostrar Todos");

        JPanel panelBusquedaBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBusquedaBotones.add(btnBuscar);
        panelBusquedaBotones.add(btnMostrarTodos);

        panelBusqueda.add(txtBuscar, BorderLayout.CENTER);
        panelBusqueda.add(panelBusquedaBotones, BorderLayout.EAST);

        // Panel superior (formulario + botones)
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        // Panel de tabla
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Listado de Estudiantes"));

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Cédula");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellido");
        modeloTabla.addColumn("Edad");
        modeloTabla.addColumn("Carrera");

        tablaEstudiantes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaEstudiantes);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        // Agregar componentes al panel principal
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelBusqueda, BorderLayout.CENTER);
        panelPrincipal.add(panelTabla, BorderLayout.SOUTH);

        add(panelPrincipal);

        // Cargar datos iniciales
        actualizarTabla(ControladorEstudiante.obtenerTodos("estudiantes.dat"));

        // Listeners
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEstudiante();
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarEstudiante();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEstudiante();
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarEstudiantes();
            }
        });

        btnMostrarTodos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTabla(ControladorEstudiante.obtenerTodos("estudiantes.dat"));
            }
        });

        tablaEstudiantes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarEstudianteSeleccionado();
            }
        });
    }

// Método actualizarTabla modificado
    private void actualizarTabla(List<Persona> estudiantes) {
        modeloTabla.setRowCount(0);
        for (Persona p : estudiantes) {
            if (p instanceof Estudiante) {
                Estudiante e = (Estudiante) p;
                modeloTabla.addRow(new Object[]{
                    e.getCedula(),
                    e.getNombre(),
                    e.getApellido(),
                    e.getEdad(),
                    e.getCarrera()
                });
            }
        }
    }

    private void agregarEstudiante() {
        try {
            String cedula = txtCedula.getText().trim();
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            int edad = Integer.parseInt(txtEdad.getText().trim());
            String carrera = txtCarrera.getText().trim();
            long multa = 0;

            if (cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || carrera.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (ControladorEstudiante.buscarPorCedula(cedula) != null) {
                JOptionPane.showMessageDialog(this, "Ya existe un usuario con esta cédula", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Estudiante nuevo = new Estudiante(cedula, nombre, apellido, edad, carrera, multa);
            ControladorEstudiante.agregarEstudiante(nuevo);
            actualizarTabla(ControladorEstudiante.obtenerTodos("estudiantes.dat"));
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Estudiante agregado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarEstudiante() {
        try {
            String cedula = txtCedula.getText().trim();
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            int edad = Integer.parseInt(txtEdad.getText().trim());
            String carrera = txtCarrera.getText().trim();

            if (cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || carrera.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Persona existente = ControladorEstudiante.buscarPorCedula(cedula);
            if (existente == null) {
                JOptionPane.showMessageDialog(this, "No existe un usuario con esta cédula", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            long multa = existente.getMulta();

            Estudiante actualizado = new Estudiante(cedula, nombre, apellido, edad, carrera, multa);
            if (ControladorEstudiante.actualizarEstudiante("estudiantes.dat", actualizado)) {
                actualizarTabla(ControladorEstudiante.obtenerTodos("estudiantes.dat"));
                limpiarFormulario();
                JOptionPane.showMessageDialog(this, "usuario modificado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarEstudiante() {
        String cedula = txtCedula.getText().trim();
        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este usuario?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (ControladorEstudiante.eliminarEstudiante("estudiantes.dat", cedula)) {
                actualizarTabla(ControladorEstudiante.obtenerTodos("estudiantes.dat"));
                limpiarFormulario();
                JOptionPane.showMessageDialog(this, "Usuario eliminado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarEstudiantes() {
        String criterio = txtBuscar.getText().trim();
        if (criterio.isEmpty()) {
            actualizarTabla(ControladorEstudiante.obtenerTodos("estudiantes.dat"));
            return;
        }

        // Buscar por cédula (si el criterio parece una cédula)
        if (criterio.matches("\\d+")) {
            Estudiante e = (Estudiante) ControladorEstudiante.buscarPorCedula(criterio);
            if (e != null) {
                List<Persona> estudianteUnico = new ArrayList<>();
                estudianteUnico.add(e);
                actualizarTabla(estudianteUnico);

                return;
            }
        }

        // Buscar por nombre
        List<Persona> resultados = ControladorEstudiante.buscarPorNombre(criterio);
        actualizarTabla(resultados);
    }

    private void cargarEstudianteSeleccionado() {
        int filaSeleccionada = tablaEstudiantes.getSelectedRow();
        if (filaSeleccionada >= 0) {
            txtCedula.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtApellido.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtEdad.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            txtCarrera.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
        }
    }

    private void limpiarFormulario() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEdad.setText("");
        txtCarrera.setText("");
        tablaEstudiantes.clearSelection();
    }

}
