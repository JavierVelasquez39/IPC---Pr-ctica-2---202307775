package org.example;

import java.io.Serializable;

public class Ruta implements Serializable {
    private int id;
    private String inicio;
    private String fin;
    private int distancia;

    public Ruta(int id, String inicio, String fin, int distancia) {
        this.id = id;
        this.inicio = inicio;
        this.fin = fin;
        this.distancia = distancia;
    }

    public int getId() {
        return id;
    }

    public String getInicio() {
        return inicio;
    }

    public String getFin() {
        return fin;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    @Override
    public String toString() {
        return "Ruta{" +
                "id=" + id +
                ", inicio='" + inicio + '\'' +
                ", fin='" + fin + '\'' +
                ", distancia=" + distancia +
                '}';
    }
}

