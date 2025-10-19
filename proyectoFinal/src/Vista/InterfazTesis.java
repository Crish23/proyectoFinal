package Vista;

import Controlador.ControladorMaterial;
import Modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InterfazTesis extends JFrame {

    private JTable tablaTesis;
    private DefaultTableModel modeloTabla;
    private JTextField txtTitulo, txtAutor, txtAnio, txtBuscar;

    public InterfazTesis() {
        setTitle("Gestión de Tesis");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la Tesis"));

        panelFormulario.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        panelFormulario.add(txtAutor);

        panelFormulario.add(new JLabel("Año:"));
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
        panelTabla.setBorder(BorderFactory.createTitledBorder("Listado de Tesis"));

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Título");
        modeloTabla.addColumn("Autor");
        modeloTabla.addColumn("Año");

        tablaTesis = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaTesis);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelBusqueda, BorderLayout.CENTER);
        panelPrincipal.add(panelTabla, BorderLayout.SOUTH);

        add(panelPrincipal);

        actualizarTabla(ControladorMaterial.obtenerTodos("tesis.dat"));

        btnAgregar.addActionListener(e -> agregarTesis());
        btnModificar.addActionListener(e -> modificarTesis());
        btnEliminar.addActionListener(e -> eliminarTesis());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnBuscar.addActionListener(e -> buscarTesis());
        btnMostrarTodos.addActionListener(e -> actualizarTabla(ControladorMaterial.obtenerTodos("tesis.dat")));

        tablaTesis.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarTesisSeleccionada();
            }
        });
    }

    private void actualizarTabla(List<Material> tesis) {
        modeloTabla.setRowCount(0);
        for (Material m : tesis) {
            if (m instanceof Tesis) {
                Tesis t = (Tesis) m;
                modeloTabla.addRow(new Object[]{
                        t.getId(),
                        t.getTitulo(),
                        t.getAutor(),
                        t.getAno()
                });
            }
        }
    }

    private void agregarTesis() {
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
                JOptionPane.showMessageDialog(this, "Ya existe una tesis con este titulo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Tesis nueva = new Tesis(id, titulo, autor, anio, esta);
            ControladorMaterial.guardarMaterial("tesis.dat", nueva);
            actualizarTabla(ControladorMaterial.obtenerTodos("tesis.dat"));
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Tesis agregada con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ano debe ser un numero valido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarTesis() {
        try {
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            int anio = Integer.parseInt(txtAnio.getText().trim());

            Material existente = ControladorMaterial.buscarPorTitulo(titulo);
            if (existente == null) {
                JOptionPane.showMessageDialog(this, "No existe una tesis con este titulo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean esta = existente.getEsta();
            String id = existente.getId();

            Tesis actualizado = new Tesis(id, titulo, autor, anio, esta);
            if (ControladorMaterial.actualizarMaterial("tesis.dat", actualizado)) {
                actualizarTabla(ControladorMaterial.obtenerTodos("tesis.dat"));
                limpiarFormulario();
                JOptionPane.showMessageDialog(this, "Tesis modificada con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ano debe ser un numero valido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarTesis() {
        String titulo = txtTitulo.getText().trim();
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una tesis para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Esta seguro de eliminar esta tesis?", "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (ControladorMaterial.eliminarMaterial("tesis.dat", titulo)) {
                actualizarTabla(ControladorMaterial.obtenerTodos("tesis.dat"));
                limpiarFormulario();
                JOptionPane.showMessageDialog(this, "Tesis eliminada con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar la tesis", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarTesis() {
        String criterio = txtBuscar.getText().trim();
        if (criterio.isEmpty()) {
            actualizarTabla(ControladorMaterial.obtenerTodos("tesis.dat"));
            return;
        }

        Material t = ControladorMaterial.buscarPorTitulo(criterio);
        if (t != null && t instanceof Tesis) {
            List<Material> listaUnica = new ArrayList<>();
            listaUnica.add(t);
            actualizarTabla(listaUnica);
            return;
        }

        List<Material> resultados = ControladorMaterial.buscarPorAutor(criterio);
        List<Material> soloTesis = new ArrayList<>();
        for (Material m : resultados) {
            if (m instanceof Tesis) {
                soloTesis.add(m);
            }
        }
        actualizarTabla(soloTesis);
    }

    private void cargarTesisSeleccionada() {
        int filaSeleccionada = tablaTesis.getSelectedRow();
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
        tablaTesis.clearSelection();
    }
}
