package Vista;

import Controlador.ControladorMaterial;
import Modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InterfazLibros extends JFrame {

    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;
    private JTextField txtId, txtTitulo, txtAutor, txtAnio, txtBuscar;

    public InterfazLibros() {
        setTitle("Gestion de Libros");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Libro"));

        panelFormulario.add(new JLabel("Titulo:"));
        txtTitulo = new JTextField();
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        panelFormulario.add(txtAutor);

        panelFormulario.add(new JLabel("Ano:"));
        txtAnio = new JTextField();
        panelFormulario.add(txtAnio);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnAgregar = new JButton("Agregar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

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

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Listado de Libros"));

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Id");
        modeloTabla.addColumn("Titulo");
        modeloTabla.addColumn("Autor");
        modeloTabla.addColumn("Ano");

        tablaLibros = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaLibros);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelBusqueda, BorderLayout.CENTER);
        panelPrincipal.add(panelTabla, BorderLayout.SOUTH);

        add(panelPrincipal);

        actualizarTabla(ControladorMaterial.obtenerTodos("libros.dat"));

        btnAgregar.addActionListener(e -> agregarLibro());
        btnModificar.addActionListener(e -> modificarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnBuscar.addActionListener(e -> buscarLibros());
        btnMostrarTodos.addActionListener(e -> actualizarTabla(ControladorMaterial.obtenerTodos("libros.dat")));

        tablaLibros.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarLibroSeleccionado();
            }
        });
    }

    private void actualizarTabla(List<Material> libros) {
        modeloTabla.setRowCount(0);
        for (Material m : libros) {
            if (m instanceof Libro) {
                Libro l = (Libro) m;
                modeloTabla.addRow(new Object[]{
                        l.getId(),
                        l.getTitulo(),
                        l.getAutor(),
                        l.getAno()
                });
            }
        }
    }

    private void agregarLibro() {
        try {
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            int anio = Integer.parseInt(txtAnio.getText().trim());
            boolean esta = true;
            String id = Integer.toString(ControladorMaterial.comprobarId());

            if (titulo.isEmpty() || autor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (ControladorMaterial.buscarPorTitulo(titulo) != null) {
                JOptionPane.showMessageDialog(this, "Ya existe un libro con este titulo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Libro nuevo = new Libro(id, titulo, autor, anio, esta);
            // Guardar solo en libros.dat
            ControladorMaterial.guardarMaterial("libros.dat", nuevo);
            actualizarTabla(ControladorMaterial.obtenerTodos("libros.dat"));
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Libro agregado con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ano debe ser un numero valido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarLibro() {
        try {
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            int anio = Integer.parseInt(txtAnio.getText().trim());
            

            Material existente = ControladorMaterial.buscarPorTitulo(titulo);
            if (existente == null) {
                JOptionPane.showMessageDialog(this, "No existe un libro con este titulo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean esta = existente.getEsta();
            String id = existente.getId();

            Libro actualizado = new Libro(id, titulo, autor, anio, esta);
            if (ControladorMaterial.actualizarMaterial("libros.dat", actualizado)) {
                actualizarTabla(ControladorMaterial.obtenerTodos("libros.dat"));
                limpiarFormulario();
                JOptionPane.showMessageDialog(this, "Libro modificado con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ano debe ser un numero valido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarLibro() {
        String titulo = txtTitulo.getText().trim();
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un libro para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Esta seguro de eliminar este libro?", "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (ControladorMaterial.eliminarMaterial("libros.dat", titulo)) {
                actualizarTabla(ControladorMaterial.obtenerTodos("libros.dat"));
                limpiarFormulario();
                JOptionPane.showMessageDialog(this, "Libro eliminado con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el libro", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarLibros() {
        String criterio = txtBuscar.getText().trim();
        if (criterio.isEmpty()) {
            actualizarTabla(ControladorMaterial.obtenerTodos("libros.dat"));
            return;
        }

        Material l = ControladorMaterial.buscarPorTitulo(criterio);
        if (l != null && l instanceof Libro) {
            List<Material> listaUnica = new ArrayList<>();
            listaUnica.add(l);
            actualizarTabla(listaUnica);
            return;
        }

        List<Material> resultados = ControladorMaterial.buscarPorAutor(criterio);
        List<Material> soloLibros = new ArrayList<>();
        for (Material m : resultados) {
            if (m instanceof Libro) {
                soloLibros.add(m);
            }
        }
        actualizarTabla(soloLibros);
    }

    private void cargarLibroSeleccionado() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        if (filaSeleccionada >= 0) {
            txtTitulo.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtAutor.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtAnio.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
        }
    }

    private void limpiarFormulario() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtAnio.setText("");
        tablaLibros.clearSelection();
    }
}
