package Vista;

/**
 *
 * @author janpi
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazMaterial extends JFrame {
    
    private void abrirVentana1() {
        InterfazLibros interfaz = new InterfazLibros();
        interfaz.setVisible(true);
    }
    
    private void abrirVentana2() {
        InterfazTesis interfaz = new InterfazTesis();
        interfaz.setVisible(true);
    }
    
    private void abrirVentana3() {
        InterfazRevistas interfaz = new InterfazRevistas();
        interfaz.setVisible(true);
    }
    
    public InterfazMaterial() {
        // Configuración básica de la ventana
        setTitle("Interfaz con Menú de Botones");
        setSize(400, 300);
        setLocationRelativeTo(null); // Centrar la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear un panel para contener los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(2, 2, 10, 10));

        // Crear y agregar los botones
        JButton btn1 = new JButton("Libros");
        JButton btn2 = new JButton("Tesis");
        JButton btn3 = new JButton("Revistas");
        

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
