package Vista;

/**
 *
 * @author janpi
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interfaz extends JFrame {
    
    private void abrirVentana1() {
        InterfazMaterial interfaz = new InterfazMaterial();
        interfaz.setVisible(true);
    }
    
    private void abrirVentana2() {
        InterfazPersona interfaz = new InterfazPersona();
        interfaz.setVisible(true);
    }

    public Interfaz() {
        // Configuración básica de la ventana
        setTitle("Interfaz con Menú de Botones");
        setSize(400, 300);
        setLocationRelativeTo(null); // Centrar la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear un panel para contener los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(2, 2, 10, 10)); // 2 filas, 2 columnas con espacio

        // Crear y agregar los botones
        JButton btn1 = new JButton("Materiales Bibliograficos");
        JButton btn2 = new JButton("Gestión de usuarios");
        JButton btn3 = new JButton("Prestamos y devoluciones");
        JButton btn4 = new JButton("Busquedas y reportes");

        // Opcional: agregar acción a los botones
        btn1.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                abrirVentana1();
            }
        }
        );
        btn2.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                abrirVentana2();
            }
        }
        );
        btn3.addActionListener(e
                -> JOptionPane.showMessageDialog(this, "Presionaste el Botón 3"));
        btn4.addActionListener(e
                -> JOptionPane.showMessageDialog(this, "Presionaste el Botón 4"));

        panelBotones.add(btn1);

        panelBotones.add(btn2);

        panelBotones.add(btn3);

        panelBotones.add(btn4);

        // Margen alrededor del panel
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir el panel a la ventana
        add(panelBotones);

        setVisible(
                true);
    }
}
