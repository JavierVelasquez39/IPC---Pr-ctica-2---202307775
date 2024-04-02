package org.example;

import org.example.Interfaz.CarreraInterfaz;
import org.example.Interfaz.VehiculoInterfaz;
import javax.swing.*;
import java.io.Serializable;

public class Carrera implements Runnable, Serializable {
    VehiculoInterfaz vehiculoInterfaz;
    JButton btnIniciarCarrera;

    public Carrera(VehiculoInterfaz vehiculoInterfaz, JButton btnIniciarCarrera) {
        this.vehiculoInterfaz = vehiculoInterfaz;
        this.btnIniciarCarrera = btnIniciarCarrera;
    }

    @Override
    public void run() {
        while (true) {
            if (!vehiculoInterfaz.isRetroceder() && vehiculoInterfaz.getX() < CarreraInterfaz.META_X) {
                vehiculoInterfaz.avanzar();
                if (vehiculoInterfaz.getX() >= CarreraInterfaz.META_X) {
                    // Detener el bucle cuando el vehículo llega a la meta
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            btnIniciarCarrera.setEnabled(true);
                        }
                    });
                    break;
                }
            } else if (vehiculoInterfaz.isRetroceder() && vehiculoInterfaz.getX() > CarreraInterfaz.INICIO_X) {
                vehiculoInterfaz.retroceder();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}







