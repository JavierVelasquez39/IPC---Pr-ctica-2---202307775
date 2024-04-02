package org.example.Interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import java.io.File;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class GenerarViajeInterfaz extends JPanel {
    private JComboBox<String> puntoInicialComboBox;
    private JComboBox<String> puntoFinalComboBox;
    private JComboBox<String> tipoTransporteComboBox;
    private JButton generarViajeButton;
    private JLabel mensajeLabel;
    private Map<String, VehiculoInterfaz> vehiculos;

    public GenerarViajeInterfaz() {
        this.vehiculos = vehiculos;
        initComponents();
        initListeners();
        cargarDatosDesdeCSV("C:\\Users\\Javier\\Desktop\\IPC - Práctica 2 - 202307775\\rutas.csv", puntoInicialComboBox, 1); // Columna "Inicio"
        cargarDatosDesdeCSV("C:\\Users\\Javier\\Desktop\\IPC - Práctica 2 - 202307775\\rutas.csv", puntoFinalComboBox, 2); // Columna "Fin"
        cargarDatosDesdeCSV("C:\\Users\\Javier\\Desktop\\IPC - Práctica 2 - 202307775\\carros.csv", tipoTransporteComboBox, 0);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        puntoInicialComboBox = new JComboBox<>();
        puntoFinalComboBox = new JComboBox<>();
        tipoTransporteComboBox = new JComboBox<>();
        generarViajeButton = new JButton("Generar Viaje");
        mensajeLabel = new JLabel();

        // Agregar componentes al panel
        panel.add(new JLabel("Punto Inicial:"));
        panel.add(puntoInicialComboBox);
        panel.add(new JLabel("Punto Final:"));
        panel.add(puntoFinalComboBox);
        panel.add(new JLabel("Tipo de Transporte:"));
        panel.add(tipoTransporteComboBox);
        panel.add(generarViajeButton);
        panel.add(mensajeLabel);

        add(panel);
    }

    private void initListeners() {
        generarViajeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validar disponibilidad de pilotos
                boolean pilotosDisponibles = validarPilotosDisponibles();
                if (pilotosDisponibles) {
                    // Aquí se generaría el viaje
                    JOptionPane.showMessageDialog(null, "Viaje generado exitosamente.");

                    // Cambiar la imagen del vehículo seleccionado
                    String nombreVehiculoSeleccionado = (String) tipoTransporteComboBox.getSelectedItem();
                    VehiculoInterfaz vehiculoSeleccionado = vehiculos.get(nombreVehiculoSeleccionado);
                    try {
                        vehiculoSeleccionado.setImagen(ImageIO.read(new File("ruta/a/la/nueva/imagen.png")));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    // Mostrar mensaje de error
                    mensajeLabel.setText("No hay pilotos disponibles en este momento.");
                    mensajeLabel.setForeground(Color.RED);
                }
            }
        });
    }

    private boolean validarPilotosDisponibles() {
        // Aquí se realizaría la lógica para validar si hay pilotos disponibles
        // Simplemente devolvemos true para este ejemplo
        return true;
    }

    private void cargarDatosDesdeCSV(String rutaArchivo, JComboBox<String> comboBox, int columnIndex) {
        try {
            CSVReader reader = new CSVReader(new FileReader(rutaArchivo));
            List<String[]> datos = reader.readAll();
            // Saltar la primera fila (encabezados)
            for (int i = 1; i < datos.size(); i++) {
                String[] fila = datos.get(i);
                // Ignorar las filas con valores vacíos
                if (fila.length > columnIndex && !fila[columnIndex].isEmpty()) {
                    comboBox.addItem(fila[columnIndex]);
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
}