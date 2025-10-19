package Vista;

import Controlador.ControladorEstudiante;
import Controlador.ControladorMaterial;
import Modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InterfazRevistas extends JFrame {

    private JTable tablaRevistas;
    private DefaultTableModel modeloTabla;
    private JTextField txtTitulo, txtAutor, txtAnio, txtBuscar;

    public InterfazRevistas() {
        setTitle("Gestion de Revistas");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la Revista"));

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
        panelTabla.setBorder(BorderFactory.createTitledBorder("Listado de Revistas"));

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Titulo");
        modeloTabla.addColumn("Autor");
        modeloTabla.addColumn("Ano");

        tablaRevistas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaRevistas);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelBusqueda, BorderLayout.CENTER);
        panelPrincipal.add(panelTabla, BorderLayout.SOUTH);

        add(panelPrincipal);

        actualizarTabla(ControladorMaterial.obtenerTodos("revistas.dat"));

        btnAgregar.addActionListener(e -> agregarRevista());
        btnModificar.addActionListener(e -> modificarRevista());
        btnEliminar.addActionListener(e -> eliminarRevista());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnBuscar.addActionListener(e -> buscarRevistas());
        btnMostrarTodos.addActionListener(e -> actualizarTabla(ControladorMaterial.obtenerTodos("revistas.dat")));

        tablaRevistas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarRevistaSeleccionada();
            }
        });
    }

    private void actualizarTabla(List<Material> revistas) {
        modeloTabla.setRowCount(0);
        for (Material m : revistas) {
            if (m instanceof Revista) {
                Revista r = (Revista) m;
                modeloTabla.addRow(new Object[]{
                        r.getId(),
                        r.getTitulo(),
                        r.getAutor(),
                        r.getAno()
                });
            }
        }
    }

    private void agregarRevista() {
        try {
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            int anio = Integer.parseInt(txtAnio.getText().trim());
            boolean esta = true;
            String id = Integer.toString(ControladorMaterial.comprobarId());

            if (titulo.isEmpty() || autor.isEmpty() || txtAnio.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (ControladorMaterial.buscarPorTitulo(titulo) != null) {
                JOptionPane.showMessageDialog(this, "Ya existe una revista con este titulo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Revista nueva = new Revista(id, titulo, autor, anio, esta);
            // Guardar solo en revistas.dat
            ControladorMaterial.guardarMaterial("revistas.dat",nueva);
            actualizarTabla(ControladorMaterial.obtenerTodos("revistas.dat"));
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Revista agregada con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ano debe ser un numero valido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarRevista() {
        try {
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            int anio = Integer.parseInt(txtAnio.getText().trim());

            Material existente = ControladorMaterial.buscarPorTitulo(titulo);
            if (existente == null) {
                JOptionPane.showMessageDialog(this, "No existe una revista con este titulo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean esta = existente.getEsta();
            String id = existente.getId();

            Revista actualizado = new Revista(id, titulo, autor, anio, esta);
            if (ControladorMaterial.actualizarMaterial("revistas.dat", actualizado)) {
                actualizarTabla(ControladorMaterial.obtenerTodos("revistas.dat"));
                limpiarFormulario();
                JOptionPane.showMessageDialog(this, "Revista modificada con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ano debe ser un numero valido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarRevista() {
        String titulo = txtTitulo.getText().trim();
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una revista para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Esta seguro de eliminar esta revista?", "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (ControladorMaterial.eliminarMaterial("revistas.dat", titulo)) {
                actualizarTabla(ControladorMaterial.obtenerTodos("revistas.dat"));
                limpiarFormulario();
                JOptionPane.showMessageDialog(this, "Revista eliminada con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar la revista", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarRevistas() {
        String criterio = txtBuscar.getText().trim();
        if (criterio.isEmpty()) {
            actualizarTabla(ControladorMaterial.obtenerTodos("revistas.dat"));
            return;
        }

        Material r = ControladorMaterial.buscarPorTitulo(criterio);
        if (r != null && r instanceof Revista) {
            List<Material> listaUnica = new ArrayList<>();
            listaUnica.add(r);
            actualizarTabla(listaUnica);
            return;
        }

        List<Material> resultados = ControladorMaterial.buscarPorAutor(criterio);
        actualizarTabla(resultados);
    }

    private void cargarRevistaSeleccionada() {
        int filaSeleccionada = tablaRevistas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            txtTitulo.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtTitulo.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtAutor.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtAnio.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
        }
    }

    private void limpiarFormulario() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtAnio.setText("");
        tablaRevistas.clearSelection();
    }
}
