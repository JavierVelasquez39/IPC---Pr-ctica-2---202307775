package org.example;

import org.example.Interfaz.CargarRutaInterfaz;
import org.example.Interfaz.CarreraInterfaz;
import org.example.Interfaz.GenerarViajeInterfaz;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Carrera");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);

            JTabbedPane tabbedPane = new JTabbedPane();

            // Crear instancias de las interfaces o clases correspondientes
            CarreraInterfaz carreraInterfaz = new CarreraInterfaz();
            // Agregar pestañas al JTabbedPane
            tabbedPane.addTab("Inicio de Viaje", carreraInterfaz);

            JPanel panelCargarRutas = new CargarRutaInterfaz();
            tabbedPane.addTab("Cargar rutas", panelCargarRutas);

            JPanel panelGenerarViaje = new GenerarViajeInterfaz();
            tabbedPane.addTab("Generar Viaje", panelGenerarViaje);

            JPanel panelInicioViaje = new JPanel();
            tabbedPane.addTab("Historial de Viaje", panelInicioViaje);

            // Agregar el JTabbedPane al JFrame
            frame.add(tabbedPane);
            frame.setVisible(true);

            // Manejar el evento de cierre de la ventana
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    // Guardar el estado antes de salir
                    carreraInterfaz.guardarEstado();
                    // Salir del programa
                    System.exit(0);
                }
            });
        });
    }
}
