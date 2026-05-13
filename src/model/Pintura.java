package model;

public class Pintura {
    private int id;
    private String nombre;
    private String color;
    private String tipo;
    private double precio;
    private int stock;

    public Pintura() {}

    public Pintura(int id, String nombre, String color, String tipo, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.color = color;
        this.tipo = tipo;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return nombre + " - " + color;
    }
}
