package org.example.Interfaz;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Random;


public class VehiculoInterfaz extends JPanel implements Serializable {
    private static final long serialVersionUID = -5489905882971434716L;
    private String nombre;
    private Color color;
    private int carWidth, carHeight;
    private int positionX, positionY;
    private int velocidad;
    private int galonesDisponibles;
    private int avance;
    private boolean retroceder = false;
    private double combustible;
    private JLabel combustibleLabel;
    private double capacidadCombustible;

    VehiculoInterfaz(String nombre, Color color, int width, int height, double x, double y, double velocidad, double galonesDisponibles) {
        this.nombre = nombre;
        this.color = color;
        this.carWidth = width;
        this.carHeight = height;
        this.positionX = (int) x;
        this.positionY = (int) y;
        this.combustible = capacidadCombustible;
        this.capacidadCombustible = capacidadCombustible;
        this.combustibleLabel = new JLabel("Combustible: " + combustible);
        add(combustibleLabel);

        initComponents();
    }


    public void initComponents() {
        setPreferredSize(new Dimension(this.carWidth, this.carHeight));
        setBounds(this.positionX, this.positionY, this.carWidth, this.carHeight);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(this.color);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawString(this.nombre, 5, getHeight()/2);
    }

    public void avanzar() {
        Random random = new Random();
        this.avance = random.nextInt(10) + 1; // Almacena el valor de avance
        if (this.positionX + this.avance > CarreraInterfaz.META_X) {
            this.positionX = CarreraInterfaz.META_X;
        } else {
            this.positionX += this.avance;
        }
        setLocation(this.positionX, this.positionY);
    }

    public void retroceder() {
        System.out.println("Posición antes de retroceder: " + this.positionX); // Añade esta línea
        Random random = new Random();
        int retroceso = random.nextInt(10) + 1;
        if (this.positionX - retroceso < CarreraInterfaz.INICIO_X) {
            this.positionX = CarreraInterfaz.INICIO_X;
        } else {
            this.positionX -= retroceso;
        }
        System.out.println("Posición después de retroceder: " + this.positionX); // Añade esta línea
        setLocation(this.positionX, this.positionY);
    }

    public boolean isRetroceder() {
        return retroceder;
    }

    public void recargarCombustible() {
        // Recargar el combustible y actualizar la visualización
        combustible = capacidadCombustible;
        actualizarCombustible();
    }

    public void actualizarCombustible() {
        combustibleLabel.setText("Combustible: " + combustible);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getCarWidth() {
        return carWidth;
    }

    public void setCarWidth(int carWidth) {
        this.carWidth = carWidth;
    }

    public int getCarHeight() {
        return carHeight;
    }

    public void setCarHeight(int carHeight) {
        this.carHeight = carHeight;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public void setGalonesDisponibles(int galonesDisponibles) {
        this.galonesDisponibles = galonesDisponibles;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }


    public void setRetroceder(boolean retroceder) {
        this.retroceder = retroceder;
    }

    private Image imagen;
    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }
    public Image getImagen() {
        return imagen;
    }

}



