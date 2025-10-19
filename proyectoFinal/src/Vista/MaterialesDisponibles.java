package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MaterialesDisponibles extends JFrame {
    
    private void abrirVentana1() {
        InterfazAdministrativos interfaz = new InterfazAdministrativos();
        interfaz.setVisible(true);
    }
    
    private void abrirVentana2() {
        InterfazEstudiantes interfaz = new InterfazEstudiantes();
        interfaz.setVisible(true);
    }
    
    private void abrirVentana3() {
        InterfazDocentes interfaz = new InterfazDocentes();
        interfaz.setVisible(true);
    }
    
    public MaterialesDisponibles() {
        // Configuración básica de la ventana
        setTitle("Interfaz con Menú de Botones");
        setSize(400, 300);
        setLocationRelativeTo(null); // Centrar la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear un panel para contener los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(2, 2, 10, 10));

        // Crear y agregar los botones
        JButton btn1 = new JButton("Administrativos");
        JButton btn2 = new JButton("Estudiantes");
        JButton btn3 = new JButton("Docentes");
        

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
        btn3.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                abrirVentana3();
            }
        }
        );

        panelBotones.add(btn1);

        panelBotones.add(btn2);
        
        panelBotones.add(btn3);

        

        // Margen alrededor del panel
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir el panel a la ventana
        add(panelBotones);

        setVisible(
                true);
    }
}


