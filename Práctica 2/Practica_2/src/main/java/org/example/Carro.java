package org.example;

public class Carro {
    private String nombre;
    private int velocidad;
    private int galonesDisponibles;

    public Carro(String nombre, int velocidad, int galonesDisponibles) {
        this.nombre = nombre;
        this.velocidad = velocidad;
        this.galonesDisponibles = galonesDisponibles;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getGalonesDisponibles() {
        return galonesDisponibles;
    }

    public void setGalonesDisponibles(int galonesDisponibles) {
        this.galonesDisponibles = galonesDisponibles;
    }

    @Override
    public String toString() {
        return "Carro{" +
                "nombre='" + nombre + '\'' +
                ", velocidad=" + velocidad +
                ", galonesDisponibles=" + galonesDisponibles +
                '}';
    }
}
