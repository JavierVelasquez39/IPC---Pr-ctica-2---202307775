package org.example.Interfaz;

import org.example.Ruta;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CargarRutaInterfaz extends JPanel {
    private JTable table;
    private JButton loadButton;
    private JButton editButton;
    private JButton saveButton;
    private List<Ruta> rutas;
    private String filePath;

    public CargarRutaInterfaz() {
        setLayout(new BorderLayout());

        table = new JTable();
        loadButton = new JButton("Cargar CSV");
        editButton = new JButton("Editar distancia");
        saveButton = new JButton("Guardar cambios");

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirArchivoCSV();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarDistancia();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambios();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loadButton);
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.NORTH);
    }

    private void abrirArchivoCSV() {
        JFileChooser fileChooser = new JFileChooser();
        int selection = fileChooser.showOpenDialog(this);

        if (selection == JFileChooser.APPROVE_OPTION) {
            filePath = fileChooser.getSelectedFile().getAbsolutePath();
            rutas = leerRutasDesdeCSV(filePath);
            mostrarRutasEnTabla(rutas);
        }
    }

    private List<Ruta> leerRutasDesdeCSV(String filePath) {
        List<Ruta> rutas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linea;
            br.readLine(); // saltamos la primera línea, es encabezado
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length != 4) {
                    System.err.println("Línea mal formateada: " + linea);
                    continue;
                }
                int id = Integer.parseInt(partes[0].trim());
                String inicio = partes[1].trim();
                String fin = partes[2].trim();
                int distancia = Integer.parseInt(partes[3].trim());
                rutas.add(new Ruta(id, inicio, fin, distancia));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rutas;
    }

    private void mostrarRutasEnTabla(List<Ruta> rutas) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de agregar datos
        model.setColumnIdentifiers(new Object[]{"ID", "Inicio", "Fin", "Distancia"});

        for (Ruta ruta : rutas) {
            model.addRow(new Object[]{String.format("%03d", ruta.getId()), ruta.getInicio(), ruta.getFin(), ruta.getDistancia()});
        }
    }

    private void editarDistancia() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String newDistance = JOptionPane.showInputDialog("Ingrese la nueva distancia:");
            table.setValueAt(newDistance, selectedRow, 3);
            rutas.get(selectedRow).setDistancia(Integer.parseInt(newDistance));
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una fila para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void guardarCambios() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("ID,Inicio,Fin,Distancia");
            for (Ruta ruta : rutas) {
                pw.println(String.format("%03d,%s,%s,%d", ruta.getId(), ruta.getInicio(), ruta.getFin(), ruta.getDistancia()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Cargar Ruta Interfaz");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new CargarRutaInterfaz());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}

