package org.example.Interfaz;

import org.example.Carrera;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CarreraInterfaz extends JPanel {
    private Map<String, VehiculoInterfaz> vehiculos;
    private Map<String, Carrera> carreras = new HashMap<>();
    private Map<String, Point> posicionesGuardadas;
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 400;
    private final int VEHICLE_WIDTH = 50;
    private final int VEHICLE_HEIGHT = 50;
    public static final int META_X = 700;
    public static final int INICIO_X = 10;

    private JButton btnIniciarCarrera;
    private JButton btnRetrocederTodos;

    public CarreraInterfaz() {
        vehiculos = new HashMap<>();
        posicionesGuardadas = new HashMap<>();

        Map<String, VehiculoInterfaz> estadoInicial = cargarEstado();
        System.out.println("Estado inicial: " + estadoInicial);

        initComponents(estadoInicial);
    }

    public void initComponents(Map<String, VehiculoInterfaz> estadoInicial) {
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setLayout(null);

        btnIniciarCarrera = new JButton("Realizar viaje");
        btnIniciarCarrera.setBounds(WINDOW_WIDTH / 2 - 75, WINDOW_HEIGHT - 75, 150, 30);
        btnIniciarCarrera.setEnabled(true);
        btnIniciarCarrera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (VehiculoInterfaz vehiculo : vehiculos.values()) {
                    vehiculo.setRetroceder(false); // Añade esta línea
                    // Crear e iniciar un nuevo hilo para cada vehículo
                    Carrera carrera = new Carrera(vehiculo, btnIniciarCarrera);
                    Thread thread = new Thread(carrera);
                    thread.start();
                    carreras.put(vehiculo.getNombre(), carrera); // Asumiendo que VehiculoInterfaz tiene un método getNombre()
                }
                btnIniciarCarrera.setEnabled(false);// Deshabilitar el botón después de iniciar la carrera
                btnRetrocederTodos.setEnabled(true);
            }
        });
        add(btnIniciarCarrera);

        btnRetrocederTodos = new JButton("Retroceder todos");
        btnRetrocederTodos.setBounds(WINDOW_WIDTH / 2 - 75, WINDOW_HEIGHT - 45, 150, 30);
        btnRetrocederTodos.setEnabled(false);
        btnRetrocederTodos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (VehiculoInterfaz vehiculo : vehiculos.values()) {
                    vehiculo.setRetroceder(true);
                }
                btnRetrocederTodos.setEnabled(false);
            }
        });
        add(btnRetrocederTodos);



        if (!estadoInicial.isEmpty()) {
            vehiculos = estadoInicial;
            btnIniciarCarrera.setEnabled(true);
        } else {
            // Si no hay estado inicial, crear nuevos vehículos
            VehiculoInterfaz vehiculo1 = new VehiculoInterfaz("Vehiculo1", Color.RED, VEHICLE_WIDTH, VEHICLE_HEIGHT, 0, 0, 0.1, 6);
            VehiculoInterfaz vehiculo2 = new VehiculoInterfaz("Vehiculo2", Color.GREEN, VEHICLE_WIDTH, VEHICLE_HEIGHT, 0, 50, 0.3, 10);
            VehiculoInterfaz vehiculo3 = new VehiculoInterfaz("Vehiculo3", Color.BLUE, VEHICLE_WIDTH, VEHICLE_HEIGHT, 0, 100, 0.45, 12);
            vehiculos.put("Vehiculo1", vehiculo1);
            vehiculos.put("Vehiculo2", vehiculo2);
            vehiculos.put("Vehiculo3", vehiculo3);
        }

        int buttonY = 0; // Posición inicial de los botones
        for (VehiculoInterfaz vehiculo : vehiculos.values()) {
            VehiculoInterfaz currentVehicle = vehiculo; // Create a final variable for the current vehicle

            JButton btnIniciarViaje = new JButton("Iniciar viaje");
            btnIniciarViaje.setBounds(WINDOW_WIDTH + 20, buttonY + 10, 150, 30);
            btnIniciarViaje.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentVehicle.setRetroceder(false); // Use currentVehicle instead of vehiculo
                    Carrera carrera = new Carrera(currentVehicle, btnIniciarViaje); // Use currentVehicle instead of vehiculo
                    Thread thread = new Thread(carrera);
                    thread.start();
                    carreras.put(currentVehicle.getNombre(), carrera); // Use currentVehicle instead of vehiculo
                    btnIniciarViaje.setEnabled(false);
                }
            });
            add(btnIniciarViaje);

            JButton btnRetroceder = new JButton("Retroceder");
            btnRetroceder.setBounds(WINDOW_WIDTH + 170, buttonY, 150, 30);
            btnRetroceder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentVehicle.setRetroceder(true); // Use currentVehicle instead of vehiculo
                    btnIniciarViaje.setEnabled(true);
                }
            });
            add(btnRetroceder);

            buttonY += 40; // Increase the Y position for the next button
            add(vehiculo);
        }

        for (VehiculoInterfaz vehiculo : vehiculos.values()) {
            JButton btnRecargar = new JButton("Recargar");
            btnRecargar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    vehiculo.recargarCombustible();
                }
            });
            add(vehiculo);
            add(btnRecargar);
        }
    }

    public void guardarEstado() {
        // Guardar las posiciones y direcciones actuales de los vehículos
        for (Map.Entry<String, VehiculoInterfaz> entry : vehiculos.entrySet()) {
            String nombreVehiculo = entry.getKey();
            VehiculoInterfaz vehiculo = entry.getValue();
            Point posicion = new Point(vehiculo.getX(), vehiculo.getY());
            posicionesGuardadas.put(nombreVehiculo, posicion);
            // No necesitas obtener el estado de retroceso de la carrera
        }

        // Guardar el estado de los vehículos
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("estado.dat"))) {
            oos.writeObject(vehiculos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, VehiculoInterfaz> cargarEstado() {
        Map<String, VehiculoInterfaz> estado = new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("estado.dat"))) {
            estado = (Map<String, VehiculoInterfaz>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return estado;
    }
}
                                


                 